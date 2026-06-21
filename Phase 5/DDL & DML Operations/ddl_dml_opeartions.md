# JDBC DDL & DML Operations

---

## Table of Contents
1. [Overview](#overview)
2. [Key Characteristics](#key-characteristics)
3. [DDL vs DML — Execution Differences in JDBC](#ddl-vs-dml--execution-differences-in-jdbc)
4. [Class & Interface Hierarchy](#class--interface-hierarchy)
5. [Core Execution Methods](#core-execution-methods)
6. [Approach 1: DDL Execution via Statement](#approach-1-ddl-execution-via-statement)
7. [Approach 2: DML Execution via Statement](#approach-2-dml-execution-via-statement)
8. [Approach 3: Parameterized DML via PreparedStatement](#approach-3-parameterized-dml-via-preparedstatement)
9. [Approach 4: Batch DML Execution](#approach-4-batch-dml-execution)
10. [Approach 5: DML with Generated Keys Retrieval](#approach-5-dml-with-generated-keys-retrieval)
11. [Approach 6: Transactional DML (Multi-Statement Atomicity)](#approach-6-transactional-dml-multi-statement-atomicity)
12. [Approach 7: Dynamic DDL/DML via CallableStatement (Stored Procedure)](#approach-7-dynamic-ddldml-via-callablestatement-stored-procedure)
13. [Comparison of Approaches](#comparison-of-approaches)
14. [Execution Flow Diagram](#execution-flow-diagram)
15. [Common Exceptions](#common-exceptions)
16. [Important Notes](#important-notes)

---

## Overview

DDL (`CREATE`, `ALTER`, `DROP`, `TRUNCATE`) and DML (`INSERT`, `UPDATE`, `DELETE`) statements are both executed through JDBC's `Statement` family, but they differ in **return value semantics**, **transactional behavior**, and **recommended execution method**. This document focuses on the **different execution approaches** rather than a single end-to-end program.

---

## Key Characteristics

| Feature | Detail |
|---|---|
| DDL statements | `CREATE`, `ALTER`, `DROP`, `TRUNCATE` — define/modify schema |
| DML statements | `INSERT`, `UPDATE`, `DELETE` — manipulate data |
| Execution interfaces | `Statement`, `PreparedStatement`, `CallableStatement` |
| Return type (DDL) | `int` (usually `0`, no rows affected) |
| Return type (DML) | `int` (number of rows affected) |
| Auto-commit | DDL often triggers **implicit commit** on many databases (DB-dependent) |
| Rollback support | DML — yes, within a transaction; DDL — varies by database (often NOT rollback-able) |

---

## DDL vs DML — Execution Differences in JDBC

| Aspect | DDL | DML |
|---|---|---|
| Method used | `executeUpdate(String)` or `execute(String)` | `executeUpdate(String)` (preferred) |
| Return value meaning | Typically `0` — DDL doesn't affect "rows" | Number of rows affected |
| Parameterizable (`?`) | **No** — table/column names cannot be bind parameters | **Yes** — values can be bind parameters |
| Transaction behavior | Often auto-commits implicitly (MySQL, Oracle); some DBs (PostgreSQL) allow DDL in transactions | Fully transactional — can commit/rollback |
| Typical statement type | `Statement` (static SQL) | `PreparedStatement` (dynamic/parameterized) |
| Generated keys | Not applicable | Applicable for `INSERT` (`getGeneratedKeys()`) |

---

## Class & Interface Hierarchy

```
java.sql.Statement  (interface)
        │
        ├── java.sql.PreparedStatement  (extends Statement)
        │           │
        │           └── java.sql.CallableStatement  (extends PreparedStatement)
        │
        └── used directly for static DDL/DML (no parameters)

Connection.createStatement()          → Statement
Connection.prepareStatement(sql)      → PreparedStatement
Connection.prepareCall(sql)           → CallableStatement
```

---

## Core Execution Methods

| Method | Use Case | Returns |
|---|---|---|
| `executeUpdate(String sql)` | DDL or DML — single statement | `int` (rows affected, or 0 for DDL) |
| `execute(String sql)` | Unknown statement type (DDL/DML/query) | `boolean` (`true` if ResultSet, `false` if update count) |
| `executeQuery(String sql)` | `SELECT` only — **not** for DDL/DML | `ResultSet` |
| `addBatch(String sql)` | Queue a statement for batch execution | `void` |
| `executeBatch()` | Run all queued statements together | `int[]` (per-statement update counts) |
| `getGeneratedKeys()` | Retrieve auto-generated key(s) after `INSERT` | `ResultSet` |
| `getUpdateCount()` | Used after `execute()` to get affected-row count | `int` |

---

## Approach 1: DDL Execution via Statement

**When to use:** Schema creation/modification — table not parameterizable, so plain `Statement` is sufficient (and `PreparedStatement` offers no benefit here).

```java
Statement st = con.createStatement();

// CREATE TABLE
st.executeUpdate("CREATE TABLE ...");   // returns 0 typically

// ALTER TABLE
st.executeUpdate("ALTER TABLE ... ADD COLUMN ...");

// DROP TABLE
st.executeUpdate("DROP TABLE ...");
```

**Trade-off:** Simple and direct, but DDL strings are typically built via string concatenation — never accept table/column names from untrusted input (no parameter binding available for identifiers).

---

## Approach 2: DML Execution via Statement

**When to use:** One-off, static DML with no external/user-supplied values (e.g., fixed seed data, admin scripts).

```java
Statement st = con.createStatement();

int rowsInserted = st.executeUpdate("INSERT INTO ... VALUES (...)");
int rowsUpdated  = st.executeUpdate("UPDATE ... SET ... WHERE ...");
int rowsDeleted  = st.executeUpdate("DELETE FROM ... WHERE ...");
// each returns number of affected rows
```

**Trade-off:** Avoid when values come from user input — string concatenation here is a **SQL injection risk**. Prefer Approach 3 for any externally sourced values.

---

## Approach 3: Parameterized DML via PreparedStatement

**When to use:** Standard practice for all DML involving variable/user-supplied data — default approach in real applications.

```java
String sql = "INSERT INTO employee (id, name, salary) VALUES (?, ?, ?)";
PreparedStatement ps = con.prepareStatement(sql);

ps.setInt(1, id);            // bind parameter 1 (1-indexed, like ResultSet)
ps.setString(2, name);       // bind parameter 2
ps.setDouble(3, salary);     // bind parameter 3

int rows = ps.executeUpdate();   // executes with bound values, returns affected-row count
```

**Trade-off:** Slightly more setup than `Statement`, but provides **SQL injection protection**, type safety, and **precompiled query reuse** — the clear default choice for DML.

---

## Approach 4: Batch DML Execution

**When to use:** Inserting/updating many rows efficiently — avoids one round-trip per statement.

```java
PreparedStatement ps = con.prepareStatement("INSERT INTO employee (id, name) VALUES (?, ?)");

for (/* each record in dataset */) {
    ps.setInt(1, /* id */);
    ps.setString(2, /* name */);
    ps.addBatch();           // queue this parameter set, don't execute yet
}

int[] results = ps.executeBatch();   // executes all queued statements in one round-trip
// results[i] = rows affected by i-th statement (or Statement.SUCCESS_NO_INFO)
```

**Trade-off:** Major performance gain for bulk operations (fewer network round-trips), but error handling is different — a failure mid-batch throws `BatchUpdateException`, and partial success behavior is driver-dependent.

---

## Approach 5: DML with Generated Keys Retrieval

**When to use:** `INSERT` into a table with an auto-increment/identity primary key, when the generated ID is needed immediately (e.g., to insert into a related child table).

```java
String sql = "INSERT INTO employee (name) VALUES (?)";
PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

ps.setString(1, name);
ps.executeUpdate();

ResultSet keys = ps.getGeneratedKeys();   // returns ResultSet of generated key(s)
if (keys.next()) {
    int generatedId = keys.getInt(1);     // typically the only/first column
}
```

**Trade-off:** Avoids a separate `SELECT LAST_INSERT_ID()`-style query, but column name in the generated-keys `ResultSet` is driver-specific (often not the actual PK column name) — access by index (`1`) is more portable than by label.

---

## Approach 6: Transactional DML (Multi-Statement Atomicity)

**When to use:** Multiple related DML statements that must all succeed or all fail together (e.g., debit one account, credit another).

```java
con.setAutoCommit(false);           // disable auto-commit, start manual transaction
try {
    ps1.executeUpdate();            // first DML
    ps2.executeUpdate();            // second DML — related to first

    con.commit();                   // commit only if BOTH succeeded
} catch (SQLException e) {
    con.rollback();                 // undo both on any failure
} finally {
    con.setAutoCommit(true);        // restore default mode
}
```

**Trade-off:** Ensures atomicity, but **DDL inside the same transaction is risky** — many databases (MySQL, Oracle) implicitly commit on DDL, which silently breaks the rollback guarantee for DML that ran before it.

---

## Approach 7: Dynamic DDL/DML via CallableStatement (Stored Procedure)

**When to use:** DDL/DML logic encapsulated in a database-side stored procedure, called from Java.

```java
CallableStatement cs = con.prepareCall("{call insertEmployee(?, ?)}");

cs.setInt(1, id);
cs.setString(2, name);
cs.execute();   // or executeUpdate() depending on procedure design
```

**Trade-off:** Moves logic into the database (centralizes business rules, can improve performance for complex multi-step operations), but reduces portability across database vendors and moves logic outside version-controlled Java code.

---

## Comparison of Approaches

| Approach | Parameterizable | Injection-Safe | Use Case |
|---|---|---|---|
| 1. DDL via Statement | No (not applicable) | N/A | Schema creation/modification |
| 2. DML via Statement | No | **No** | Static, trusted, fixed-value DML only |
| 3. Parameterized DML | Yes | **Yes** | Default for all real-world DML |
| 4. Batch DML | Yes | Yes | Bulk insert/update operations |
| 5. Generated Keys | Yes | Yes | INSERT needing auto-generated PK back |
| 6. Transactional DML | Yes | Yes | Multi-statement atomic operations |
| 7. CallableStatement | Yes (procedure params) | Yes | DB-side stored procedure logic |

---

## Execution Flow Diagram

```
                    JDBC DDL/DML Execution
                            │
                            ▼
              ┌─────────────────────────┐
              │ Is SQL parameterized?   │
              └─────────────────────────┘
                  │                │
                 No                Yes
                  ▼                ▼
       ┌────────────────────┐   ┌───────────────────────┐
       │   Statement        │   │  PreparedStatement    │
       │ (DDL or static DML)│   │ (DML with bind values)│
       └────────────────────┘   └───────────────────────┘
                  │                │
                  ▼                ▼
       ┌───────────────────┐  ┌──────────────────────┐
       │ executeUpdate(sql)│  │ setXxx(index, value) │
       └───────────────────┘  │ executeUpdate()      │
                  │           └──────────────────────┘
                  │                │
                  └───────┬────────┘
                          ▼
              ┌─────────────────────────┐
              │  Multiple related DML?  │
              └─────────────────────────┘
                  │                    │
                 No                   Yes
                  ▼                    ▼
       ┌──────────────────────┐     ┌────────────────────────────┐
       │ Auto-commit (default)│     │ setAutoCommit(false)       │
       │ each statement final │     │ ... run statements ...     │
       └──────────────────────┘     │ commit() / rollback()      │
                                    └────────────────────────────┘
                          │
                          ▼
              ┌─────────────────────────┐
              │ Need generated key?     │
              │ → RETURN_GENERATED_KEYS │
              │   + getGeneratedKeys()  │
              └─────────────────────────┘
```

---

## Common Exceptions

| Exception | Cause | Fix |
|---|---|---|
| `SQLException: Table already exists` | `CREATE TABLE` run on existing table | Use `CREATE TABLE IF NOT EXISTS` or `DROP` first |
| `SQLSyntaxErrorException` | Malformed DDL/DML syntax | Validate SQL against target DB dialect |
| `SQLIntegrityConstraintViolationException` | DML violates PK/FK/unique/check constraint | Validate data before insert/update; handle conflict explicitly |
| `BatchUpdateException` | One statement in `executeBatch()` failed | Use `getUpdateCounts()` to identify which one failed |
| `SQLException: Cannot issue data manipulation statements with executeQuery()` | Using `executeQuery()` for `INSERT`/`UPDATE`/`DELETE` | Use `executeUpdate()` instead |
| `SQLException: Cannot issue executeUpdate() for SELECT` | Using `executeUpdate()` for a `SELECT` | Use `executeQuery()` instead |
| `SQLFeatureNotSupportedException` | `RETURN_GENERATED_KEYS` not supported for the statement/driver | Check driver docs; fall back to separate `SELECT` for last ID |
| `SQLException` on DDL inside transaction | Database auto-commits on DDL, breaking expected rollback | Avoid mixing DDL with DML in the same transaction; run DDL separately |

---

## Important Notes

1. **`executeUpdate()` vs `execute()` vs `executeQuery()`** — `executeQuery()` is strictly for `SELECT`; using it for DDL/DML throws an exception. `executeUpdate()` is the standard for DDL/DML. `execute()` is used only when the statement type is unknown at compile time (e.g., dynamically built SQL).

2. **DDL is rarely parameterizable** — table names, column names, and other SQL **identifiers** cannot be bound via `?` placeholders in `PreparedStatement`; only **values** can be parameterized. Identifier substitution must be done via safe string-building (e.g., whitelisting allowed names), never via raw user input.

3. **DDL often auto-commits implicitly** — on databases like MySQL and Oracle, executing a DDL statement (`CREATE`, `ALTER`, `DROP`) **implicitly commits** the current transaction, even if `setAutoCommit(false)` was set. PostgreSQL is a notable exception that supports transactional DDL. Always verify behavior per target database.

4. **Always prefer `PreparedStatement` for DML with variable data** — beyond SQL injection protection, it gives better performance for repeated execution (the query plan can be cached/reused by the database).

5. **Batch execution doesn't guarantee atomicity by itself** — `executeBatch()` runs multiple statements efficiently, but whether a failure rolls back prior successful statements in the batch depends on whether the batch is wrapped in an explicit transaction (`setAutoCommit(false)` + `commit()`/`rollback()`).

6. **`getGeneratedKeys()` requires explicit opt-in** — pass `Statement.RETURN_GENERATED_KEYS` when creating the `Statement`/`PreparedStatement`; without it, `getGeneratedKeys()` returns an empty `ResultSet`.

7. **Row-affected counts can be `0` even on success** — DDL statements typically return `0` from `executeUpdate()` since no "rows" are affected — this is normal and not an error.

8. **Mixing DDL and DML in one transaction is risky** — because DDL frequently triggers implicit commits, any DML executed before a DDL statement in the same transaction may become **permanently committed early**, defeating the purpose of a later `rollback()`. Keep DDL operations in separate transactions/scripts from transactional DML where possible.