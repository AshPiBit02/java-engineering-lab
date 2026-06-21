# SQL Injection

---

## Table of Contents
1. [General Overview (Brief)](#general-overview-brief)
2. [Key Characteristics](#key-characteristics)
3. [Why JDBC `Statement` Is Vulnerable](#why-jdbc-statement-is-vulnerable)
4. [Why `PreparedStatement` Prevents It](#why-preparedstatement-prevents-it)
5. [Class & Interface Relevance](#class--interface-relevance)
6. [Attack Flow Diagram](#attack-flow-diagram)
7. [Approach 1: Vulnerable — String Concatenation with Statement](#approach-1-vulnerable--string-concatenation-with-statement)
8. [Approach 2: Safe — Parameterized PreparedStatement](#approach-2-safe--parameterized-preparedstatement)
9. [Approach 3: Vulnerable — Dynamic Identifiers (Table/Column Names)](#approach-3-vulnerable--dynamic-identifiers-tablecolumn-names)
10. [Approach 4: Safe — Whitelisting Identifiers](#approach-4-safe--whitelisting-identifiers)
11. [Approach 5: Vulnerable — LIKE Clause Concatenation](#approach-5-vulnerable--like-clause-concatenation)
12. [Approach 6: Safe — Parameterized LIKE Clause](#approach-6-safe--parameterized-like-clause)
13. [Approach 7: Defense-in-Depth — Input Validation + Least Privilege](#approach-7-defense-in-depth--input-validation--least-privilege)
14. [Comparison of Approaches](#comparison-of-approaches)
15. [Common Exceptions / Symptoms](#common-exceptions--symptoms)
16. [Important Notes](#important-notes)

---

## General Overview (Brief)

SQL Injection is an attack where untrusted input is concatenated directly into a SQL query string, allowing an attacker to alter the query's logic (e.g., bypass authentication, exfiltrate data, or modify/delete records). It exploits the fact that the database cannot distinguish **attacker-supplied data** from **intended SQL code** once they're merged into a single string before execution.

---

## Key Characteristics

| Feature | Detail |
|---|---|
| Root cause | Untrusted input treated as executable SQL, not as data |
| Primary JDBC-vulnerable class | `java.sql.Statement` (with string-concatenated SQL) |
| Primary JDBC-safe class | `java.sql.PreparedStatement` (with bind parameters) |
| Vulnerable surfaces | `WHERE` clauses, `LIKE` patterns, `ORDER BY`, table/column names, `IN (...)` lists |
| Defense mechanism | Separation of SQL code from data via parameter binding |
| Not fully solved by PreparedStatement | Dynamic identifiers (table/column names) — cannot be bound as `?` |

---

## Why JDBC `Statement` Is Vulnerable

`Statement.executeQuery(String sql)` / `executeUpdate(String sql)` sends the **entire string as-is** to the database. If that string was built using `+` concatenation with user input, the database parses whatever the attacker injected as **part of the SQL grammar** — not as a literal value.

```java
// User input becomes part of the SQL text itself — no separation between code and data
String sql = "SELECT * FROM users WHERE username = '" + userInput + "'";
```

---

## Why `PreparedStatement` Prevents It

`PreparedStatement` sends the SQL **template** (with `?` placeholders) to the database **first**, where it is compiled/parsed into an execution plan. Bound values are sent **separately afterward** and substituted only as literal data — they are never re-parsed as SQL syntax, so injected SQL metacharacters (`'`, `--`, `;`) have no special meaning.

```java
// Template compiled first; input is bound as DATA, never re-interpreted as SQL syntax
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement ps = con.prepareStatement(sql);
ps.setString(1, userInput);   // bound as a literal value, regardless of content
```

---

## Class & Interface Relevance

```
java.sql.Statement                    ← String built via concatenation = RISK
        │
        ▼ (extends)
java.sql.PreparedStatement            ← Precompiled template + bind params = SAFE for VALUES
        │
        ▼ (extends)
java.sql.CallableStatement            ← Same binding safety, for stored procedure calls
```

> **Important distinction:** `PreparedStatement` protects **values** (`WHERE col = ?`), not **identifiers** (table/column names, `ORDER BY` column, sort direction). Those require separate handling (Approach 4).

---

## Attack Flow Diagram

```
        Vulnerable Code Path                       Safe Code Path
   ┌───────────────────────────┐          ┌───────────────────────────┐
   │  User Input: ' OR '1'='1  │          │  User Input: ' OR '1'='1  │
   └───────────────────────────┘          └───────────────────────────┘
                │                                       │
                ▼                                       ▼
   ┌───────────────────────────┐          ┌────────────────────────────┐
   │ String concatenation:     │          │ Sent as bind PARAMETER     │
   │ "...WHERE user='" + input │          │ via setString(1, input)    │
   │  + "'"                    │          │ (template already compiled)│
   └───────────────────────────┘          └────────────────────────────┘
                │                                       │
                ▼                                       ▼
   ┌───────────────────────────┐          ┌────────────────────────────┐
   │ DB parses ENTIRE string   │          │ DB matches literal string  │
   │ as SQL → WHERE clause     │          │ "' OR '1'='1" against the  │
   │ logic altered → returns   │          │ username column → NO MATCH │
   │ ALL rows (auth bypass)    │          │ → query behaves safely     │
   └───────────────────────────┘          └────────────────────────────┘
```

---

## Approach 1: Vulnerable — String Concatenation with Statement

**Why it fails:** Input becomes part of the SQL grammar itself.

```java
Statement st = con.createStatement();
// DO NOT DO THIS — attacker input can close the quote and inject logic
String sql = "SELECT * FROM users WHERE username='" + user + "' AND password='" + pass + "'";
ResultSet rs = st.executeQuery(sql);
// input like:  ' OR '1'='1  →  query returns all rows, bypassing authentication
```

---

## Approach 2: Safe — Parameterized PreparedStatement

**When to use:** Default approach for ALL queries involving any external/user-supplied value.

```java
String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
PreparedStatement ps = con.prepareStatement(sql);
ps.setString(1, user);   // bound as literal data
ps.setString(2, pass);   // bound as literal data
ResultSet rs = ps.executeQuery();
// malicious input is treated as a literal string value, never re-parsed as SQL
```

---

## Approach 3: Vulnerable — Dynamic Identifiers (Table/Column Names)

**Why it's a separate problem:** `?` placeholders only work for **values**, not for table/column names or SQL keywords (`ASC`/`DESC`).

```java
// Cannot parameterize identifiers — this is STILL injectable even "by habit" of using PreparedStatement wrong
String sql = "SELECT * FROM " + tableName + " ORDER BY " + sortColumn;
// PreparedStatement.setString() cannot bind tableName/sortColumn — they are SQL syntax, not data
```

---

## Approach 4: Safe — Whitelisting Identifiers

**When to use:** Whenever table names, column names, or sort directions come from user-controlled input (e.g., a dropdown for "sort by").

```java
// Validate against a fixed, known-safe set BEFORE building the SQL string
Set<String> allowedColumns = Set.of("name", "salary", "date_joined");
if (!allowedColumns.contains(sortColumn)) {
    throw new IllegalArgumentException("Invalid sort column");
}
String sql = "SELECT * FROM employee ORDER BY " + sortColumn;  // now safe — value is whitelisted
```

---

## Approach 5: Vulnerable — LIKE Clause Concatenation

**Why it fails:** Wildcard characters and quotes inside `LIKE` patterns are easy to mishandle with raw concatenation.

```java
String sql = "SELECT * FROM products WHERE name LIKE '%" + keyword + "%'";
// input like:  %' OR '1'='1  →  breaks out of the LIKE pattern entirely
```

---

## Approach 6: Safe — Parameterized LIKE Clause

**When to use:** Any search/filter feature using partial string matching.

```java
String sql = "SELECT * FROM products WHERE name LIKE ?";
PreparedStatement ps = con.prepareStatement(sql);
ps.setString(1, "%" + keyword + "%");   // wildcards added to the VALUE, not the SQL text
ResultSet rs = ps.executeQuery();
// keyword is bound as data — special characters in it have no SQL meaning
```

---

## Approach 7: Defense-in-Depth — Input Validation + Least Privilege

**When to use:** As a supplementary layer alongside (never instead of) `PreparedStatement`.

```java
// Layer 1: Input validation (reject unexpected formats early)
if (!input.matches("[a-zA-Z0-9_]+")) {
    throw new IllegalArgumentException("Invalid input format");
}

// Layer 2: Database account with minimum required privileges
// (application's DB user should not have DROP/ALTER rights if only SELECT/INSERT is needed)

// Layer 3: Still use PreparedStatement for actual query execution
```

---

## Comparison of Approaches

| Approach | Protects Against | Limitation |
|---|---|---|
| 1. Statement + concatenation | Nothing | **Vulnerable** — never use with external input |
| 2. PreparedStatement (values) | Value-based injection | Doesn't protect identifiers |
| 3. Identifier concatenation | Nothing | **Vulnerable** — `?` cannot bind table/column names |
| 4. Whitelisting identifiers | Identifier-based injection | Requires maintaining an allowed-list |
| 5. LIKE concatenation | Nothing | **Vulnerable** — wildcard/quote injection |
| 6. Parameterized LIKE | LIKE-pattern injection | None significant |
| 7. Input validation + least privilege | Limits blast radius | Supplementary only — not a substitute for binding |

---

## Common Exceptions / Symptoms

| Symptom | Likely Cause |
|---|---|
| Query returns unexpectedly all rows | Classic `' OR '1'='1` style injection via `Statement` |
| `SQLSyntaxErrorException` after odd input (e.g., a name with an apostrophe like `O'Brien`) | Unescaped quote breaking string concatenation — sign that `PreparedStatement` is not in use |
| Application logs show altered/unexpected SQL text | Concatenated query string was manipulated |
| Data appears modified/deleted without corresponding application action | Injected `UPDATE`/`DELETE` appended via stacked queries (where supported) |
| `SQLException: Invalid column/table name` from user-supplied identifier | Dynamic identifier injection attempt (or invalid whitelist check) |

---

## Important Notes

1. **`PreparedStatement` alone does not make an application fully injection-proof** — it protects **bound values**, not **identifiers** (table names, column names, `ORDER BY` targets) or SQL keywords. Those require explicit whitelisting (Approach 4).

2. **Parameter binding works because compilation happens before data arrives** — the database parses the SQL template into an execution plan first; bound values are substituted afterward purely as literal data, so injected SQL syntax characters lose all special meaning.

3. **Never trust `Statement` with any external input** — if a query has zero variable parts (fully static SQL, e.g., DDL or fixed seed data), `Statement` is fine; the moment any value originates from a user, request, file, or another system, switch to `PreparedStatement`.

4. **`LIKE` wildcards must be added to the bound value, not the SQL text** — e.g., `ps.setString(1, "%" + keyword + "%")`, never `"... LIKE '%" + keyword + "%'"`.

5. **Least privilege limits damage even if injection occurs** — an application's database account should only have the minimum permissions it needs (e.g., no `DROP TABLE` rights for a read-mostly reporting app); this doesn't prevent injection but reduces its impact.

6. **Stored procedures via `CallableStatement` are not automatically immune** — if the procedure itself builds dynamic SQL internally using concatenation (common anti-pattern), it can still be injectable even though the Java call uses bound parameters.

7. **Error messages can leak schema information** — verbose database error messages echoed back to users (e.g., exposing table/column names from a syntax error) aid attackers in crafting further injection attempts; log details server-side, show generic messages to users.

8. **ORM/framework query builders are not a silver bullet** — they reduce risk by encouraging parameterization by default, but raw/native query escape hatches (e.g., string-based custom queries) reintroduce the same risk if misused.