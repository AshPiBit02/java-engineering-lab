# JDBC SQL Escapes

---

## Table of Contents
1. [Part A: JDBC Escape Syntax](#part-a-jdbc-escape-syntax)
   - [Overview](#overview)
   - [Key Characteristics](#key-characteristics)
   - [Escape Syntax Reference Table](#escape-syntax-reference-table)
   - [Approach 1: Date/Time Literals](#approach-1-datetime-literals)
   - [Approach 2: Scalar Function Escape](#approach-2-scalar-function-escape)
   - [Approach 3: Stored Procedure Call Escape](#approach-3-stored-procedure-call-escape)
   - [Approach 4: LIKE Escape Character](#approach-4-like-escape-character)
   - [Approach 5: Outer Join Escape (Legacy)](#approach-5-outer-join-escape-legacy)
2. [Part B: Character Escaping](#part-b-character-escaping)
   - [Overview](#overview-1)
   - [Why Manual Escaping Is Discouraged](#why-manual-escaping-is-discouraged)
   - [Approach 6: Manual Quote Escaping (Anti-Pattern)](#approach-6-manual-quote-escaping-anti-pattern)
   - [Approach 7: PreparedStatement Auto-Handling (Correct)](#approach-7-preparedstatement-auto-handling-correct)
   - [Special Characters Reference](#special-characters-reference)
3. [Comparison Summary](#comparison-summary)
4. [Flow Diagram](#flow-diagram)
5. [Common Exceptions](#common-exceptions)
6. [Important Notes](#important-notes)

---

# Part A: JDBC Escape Syntax

## Overview

JDBC defines a **driver-independent escape syntax**, written as `{keyword ...}`, that lets you write date/time literals, function calls, and procedure calls in a database-agnostic form. The JDBC driver translates this into the target database's native SQL dialect before sending it.

## Key Characteristics

| Feature | Detail |
|---|---|
| Purpose | Write portable SQL fragments independent of DB vendor syntax differences |
| Syntax form | `{keyword parameters}` |
| Processed by | The JDBC driver itself, before reaching the database |
| Applies to | Date/time literals, scalar functions, stored procedure calls, LIKE escape char, outer joins |
| Not related to | String/character escaping (that's a separate concern — see Part B) |

## Escape Syntax Reference Table

| Escape | Purpose | Example |
|---|---|---|
| `{d 'yyyy-mm-dd'}` | Date literal | `{d '2026-06-21'}` |
| `{t 'hh:mm:ss'}` | Time literal | `{t '14:30:00'}` |
| `{ts 'yyyy-mm-dd hh:mm:ss.f'}` | Timestamp literal | `{ts '2026-06-21 14:30:00.0'}` |
| `{fn FUNCTION(...)}` | Scalar function call | `{fn UCASE(name)}` |
| `{call procedure_name(?, ?)}` | Stored procedure call | `{call getEmployee(?)}` |
| `{? = call procedure_name(?)}` | Stored function call with return value | `{? = call computeBonus(?)}` |
| `{escape 'char'}` | Defines escape character for LIKE wildcards | `LIKE '50%' {escape ''}` |
| `{oj table1 LEFT OUTER JOIN table2 ON ...}` | Outer join (legacy, rarely needed now) | — |

## Approach 1: Date/Time Literals

**When to use:** Writing date/time literal values portably across DB vendors (avoids vendor-specific date format quirks).

```java
String sql = "SELECT * FROM employee WHERE join_date = {d '2026-06-21'}";
// driver translates {d '...'} into the native date literal syntax for the target DB
```

> **Preferred modern alternative:** use `PreparedStatement.setDate(index, java.sql.Date)` instead of embedding date literals in SQL text — safer and equally portable.

## Approach 2: Scalar Function Escape

**When to use:** Calling built-in SQL functions whose names/behavior differ across databases (e.g., string/date functions).

```java
String sql = "SELECT {fn UCASE(name)} FROM employee";
// {fn ...} lets the driver map UCASE to the equivalent function in the target DB
// (e.g., UPPER() in some databases)
```

## Approach 3: Stored Procedure Call Escape

**When to use:** Calling a stored procedure/function via `CallableStatement` in a vendor-neutral way.

```java
CallableStatement cs = con.prepareCall("{call getEmployee(?)}");
cs.setInt(1, empId);
ResultSet rs = cs.executeQuery();

// With return value (function-style call):
CallableStatement cs2 = con.prepareCall("{? = call computeBonus(?)}");
cs2.registerOutParameter(1, Types.DOUBLE);
cs2.setInt(2, empId);
cs2.execute();
double bonus = cs2.getDouble(1);
```

## Approach 4: LIKE Escape Character

**When to use:** Searching for literal `%` or `_` characters inside a `LIKE` pattern (since these are normally wildcards).

```java
// Searching for a literal underscore in data, e.g., "50_OFF"
String sql = "SELECT * FROM promo WHERE code LIKE ? {escape ''}";
PreparedStatement ps = con.prepareStatement(sql);
ps.setString(1, "50\\_OFF");   // backslash marks '_' as literal, not wildcard
```

## Approach 5: Outer Join Escape (Legacy)

**When to use:** Rarely needed today — older databases lacked standard `LEFT/RIGHT OUTER JOIN` syntax, so JDBC provided `{oj ...}` as a portable form. Modern SQL standard `JOIN` syntax is preferred.

```java
String sql = "SELECT * FROM {oj employee LEFT OUTER JOIN department ON employee.dept_id = department.id}";
// Legacy — prefer standard ANSI JOIN syntax in modern code:
// "SELECT * FROM employee LEFT OUTER JOIN department ON employee.dept_id = department.id"
```

---

# Part B: Character Escaping

## Overview

Character escaping refers to handling special characters (single quotes `'`, backslashes, wildcards `%`/`_`) that have **syntactic meaning** in SQL strings. This is a **different concern** from JDBC escape syntax — it's about safely representing data values, not writing portable SQL fragments.

## Why Manual Escaping Is Discouraged

Manually escaping quotes by doubling them (`'` → `''`) or replacing characters is **error-prone and incomplete** — it does not account for all DB-specific quoting rules, multi-byte encoding issues, or every injection vector. `PreparedStatement` makes this entire category of problem unnecessary by binding values separately from SQL text.

## Approach 6: Manual Quote Escaping (Anti-Pattern)

**Why it's discouraged:** Shown for awareness only — this is the kind of workaround `PreparedStatement` exists to eliminate.

```java
// Doubling single quotes to "escape" them — fragile, incomplete, NOT recommended
String safeName = userInput.replace("'", "''");
String sql = "SELECT * FROM users WHERE name = '" + safeName + "'";
// Still vulnerable to other injection vectors (backslashes, encoding tricks, etc.)
```

## Approach 7: PreparedStatement Auto-Handling (Correct)

**When to use:** Always — the standard, correct way to handle any value containing special characters.

```java
String sql = "SELECT * FROM users WHERE name = ?";
PreparedStatement ps = con.prepareStatement(sql);
ps.setString(1, userInput);   // quotes, backslashes, anything — handled automatically, safely
ResultSet rs = ps.executeQuery();
// no manual escaping needed; the driver handles correct representation for the target DB
```

## Special Characters Reference

| Character | Meaning in SQL | Handling |
|---|---|---|
| `'` (single quote) | String delimiter | Auto-handled by `PreparedStatement`; manual doubling (`''`) is the legacy workaround |
| `%` | `LIKE` wildcard (any sequence) | Use `{escape}` clause or driver-specific escape char if literal `%` is needed |
| `_` | `LIKE` wildcard (single character) | Same as above |
| `\` (backslash) | Escape char in some DBs (e.g., MySQL) | Avoid manual handling — let `PreparedStatement` manage it |
| `;` | Statement separator (in multi-statement contexts) | Not typically an issue with parameterized single-statement execution |
| `--`, `/* */` | SQL comment markers | Irrelevant when value is bound as data, not concatenated into SQL text |

---

## Comparison Summary

| Aspect | JDBC Escape Syntax (`{}`) | Character Escaping |
|---|---|---|
| Purpose | Portable SQL fragments (dates, functions, calls) | Safely represent special characters in data |
| Processed by | JDBC driver, translated to native SQL | Database engine (via proper binding) |
| Solves portability? | Yes | No — unrelated concern |
| Solves SQL injection? | No | Yes, when done via `PreparedStatement` |
| Recommended modern practice | Use `setDate()`/`setTimestamp()` over `{d}`/`{ts}` where possible | Always use `PreparedStatement`, never manual escaping |

---

## Flow Diagram

```
              SQL Text Containing Escapes
                         │
                         ▼
          ┌─────────────────────────────┐
          │ Contains {keyword ...}?     │
          └─────────────────────────────┘
              │                   │
             Yes                  No
              ▼                   │
   ┌────────────────────────┐     │
   │ JDBC driver translates │     │
   │ to native DB syntax    │     │
   │ ({d},{t},{ts},{fn},    │     │
   │  {call},{escape},{oj}) │     │
   └────────────────────────┘     │
              │                   │
              └─────────┬─────────┘
                         ▼
          ┌─────────────────────────────┐
          │ Does query include values   │
          │ from external/user input?   │
          └─────────────────────────────┘
              │                   │
             Yes                  No
              ▼                   ▼
   ┌─────────────────────┐  ┌─────────────────────┐
   │ Bind via            │  │ Static SQL — execute│
   │ PreparedStatement   │  │ directly            │
   │ setXxx() (Part B)   │  └─────────────────────┘
   └─────────────────────┘
              │
              ▼
   Driver sends value as literal DATA — no manual
   quote-escaping needed, no injection risk
```

---

## Common Exceptions

| Exception | Cause | Fix |
|---|---|---|
| `SQLSyntaxErrorException` | Malformed `{}` escape syntax (typo, mismatched braces) | Verify escape keyword and closing `}` |
| `SQLFeatureNotSupportedException` | Driver doesn't support a specific escape (e.g., `{oj}`) | Use standard ANSI SQL syntax instead |
| `SQLException: Unterminated string` | Unescaped quote inside manually concatenated SQL | Switch to `PreparedStatement` — eliminates this category of error |
| `SQLException: Unknown function` | `{fn FUNCTION_NAME(...)}` not mapped by driver | Check JDBC driver's supported scalar function list |

---

## Important Notes

1. **JDBC escape syntax (`{}`) is about portability, not security** — it lets the same Java code work across different database vendors; it does **not** protect against SQL injection. These are two separate, unrelated purposes that share the word "escape."

2. **Prefer typed setter methods over date/time escape literals** — `ps.setDate(1, sqlDate)` / `ps.setTimestamp(1, ts)` is generally preferred over `{d '...'}` / `{ts '...'}` in modern code, since it avoids string formatting issues entirely.

3. **Never manually escape quotes for security** — doubling `'` to `''` is a legacy, incomplete technique. Always use `PreparedStatement` parameter binding instead; it is both simpler and strictly safer.

4. **`{escape}` clause is specifically for `LIKE` wildcards** — needed only when searching for literal `%` or `_` characters within data; unrelated to general string escaping.

5. **`{oj ...}` is largely obsolete** — virtually all modern databases support standard ANSI `LEFT/RIGHT/FULL OUTER JOIN` syntax directly; this escape exists mainly for legacy compatibility.

6. **Driver support for escapes varies** — not every driver implements every escape keyword; check `DatabaseMetaData.getExtraNameCharacters()` / driver documentation if portability across multiple DB vendors is a hard requirement.