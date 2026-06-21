# JDBC PreparedStatement

---

## Table of Contents
1. [Overview](#overview)
2. [Key Characteristics](#key-characteristics)
3. [Class Hierarchy](#class-hierarchy)
4. [Statement vs PreparedStatement](#statement-vs-preparedstatement)
5. [Important Methods](#important-methods)
6. [Working Sequence](#working-sequence)
7. [Flow Diagram](#flow-diagram)
8. [Approach 1: Basic Parameterized Query](#approach-1-basic-parameterized-query)
9. [Approach 2: Reused PreparedStatement (Loop)](#approach-2-reused-preparedstatement-loop)
10. [Approach 3: Batch Execution](#approach-3-batch-execution)
11. [Common Exceptions](#common-exceptions)
12. [Important Notes](#important-notes)

---

## Overview

`PreparedStatement` is a precompiled SQL statement with `?` placeholders for values, bound separately via `setXxx()` methods. It is the standard, recommended way to execute parameterized DML/queries in JDBC.

---

## Key Characteristics

| Feature | Detail |
|---|---|
| Package | `java.sql.PreparedStatement` |
| Compilation | SQL template compiled once by the DB before execution |
| Parameters | `?` placeholders, **1-indexed** |
| Reusability | Same object can be executed multiple times with different bound values |
| Safety | Bound values treated as literal data — prevents SQL injection |
| Performance | DB can cache/reuse the execution plan on repeated calls |

---

## Class Hierarchy

```
java.sql.Statement
        │
        ▼
java.sql.PreparedStatement
        │
        ▼
java.sql.CallableStatement   (for stored procedure calls)
```

---

## Statement vs PreparedStatement

| Aspect | Statement | PreparedStatement |
|---|---|---|
| SQL compilation | Each execution | Once, reused |
| Parameters | Concatenated manually | Bound via `setXxx()` |
| Injection-safe | No | Yes (for values) |
| Best for | Static, no-input SQL | Any SQL with variable values |

---

## Important Methods

| Method | Description |
|---|---|
| `setInt(index, value)` / `setString(...)` / `setDouble(...)` etc. | Bind a typed value to a `?` placeholder |
| `setNull(index, sqlType)` | Bind SQL `NULL` explicitly |
| `executeQuery()` | Run a `SELECT` → returns `ResultSet` |
| `executeUpdate()` | Run `INSERT`/`UPDATE`/`DELETE`/DDL → returns affected-row count |
| `addBatch()` | Queue current bound parameters for batch execution |
| `executeBatch()` | Execute all queued parameter sets |
| `clearParameters()` | Reset all bound values (object stays reusable) |
| `getMetaData()` | Returns `ResultSetMetaData` for the query without executing it |

---

## Working Sequence

```
1. con.prepareStatement(sql)   → SQL template sent & compiled by DB
2. setXxx(index, value)         → bind each "?" placeholder
3. executeQuery() / executeUpdate()  → run with bound values
4. (Optional) clearParameters() + re-bind for next execution
5. close()                      → release resources
```

---

## Flow Diagram

```
   prepareStatement(sql with ?)
            │
            ▼
   ┌────────────────────────┐
   │  DB compiles template  │
   └────────────────────────┘
            │
            ▼
   setXxx(1, val1) ... setXxx(n, valN)
            │
            ▼
   ┌────────────────────────┐
   │ execute / executeUpdate│
   └────────────────────────┘
            │
            ▼
   values sent as DATA only — never re-parsed as SQL
            │
            ▼
   (reuse) → clearParameters() → bind new values → execute again
```

---

## Approach 1: Basic Parameterized Query

```java
String sql = "SELECT * FROM employee WHERE id = ?";
PreparedStatement ps = con.prepareStatement(sql);
ps.setInt(1, empId);              // bind value
ResultSet rs = ps.executeQuery();  // execute with bound value
```

---

## Approach 2: Reused PreparedStatement (Loop)

```java
PreparedStatement ps = con.prepareStatement("INSERT INTO log (msg) VALUES (?)");
for (String msg : messages) {
    ps.setString(1, msg);   // rebind for each iteration
    ps.executeUpdate();     // executes immediately, one round-trip each
}
```

---

## Approach 3: Batch Execution

```java
PreparedStatement ps = con.prepareStatement("INSERT INTO log (msg) VALUES (?)");
for (String msg : messages) {
    ps.setString(1, msg);
    ps.addBatch();          // queue instead of executing immediately
}
int[] results = ps.executeBatch();   // single round-trip for all
```

---

## Common Exceptions

| Exception | Cause | Fix |
|---|---|---|
| `SQLException: Parameter index out of range` | `?` count mismatch with `setXxx()` calls | Match bound parameter count to placeholders |
| `SQLException: No value specified for parameter` | A `?` left unbound before execution | Bind all placeholders before executing |
| `SQLSyntaxErrorException` | Invalid SQL template | Validate SQL string before preparing |
| `SQLException: Statement closed` | Reusing `ps` after `close()` | Re-prepare a new statement |

---

## Important Notes

1. **Parameter indices are 1-based**, same convention as `ResultSet` columns.
2. **Protects values, not identifiers** — table/column names cannot be bound via `?`.
3. **Reuse for performance** — preparing once and executing many times (loop/batch) avoids repeated SQL parsing overhead.
4. **Always prefer over `Statement`** when any value originates from user/external input.