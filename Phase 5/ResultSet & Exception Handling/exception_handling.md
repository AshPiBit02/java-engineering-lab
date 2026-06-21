# JDBC Exception Handling

---

## Table of Contents
1. [Overview](#overview)
2. [Key Characteristics](#key-characteristics)
3. [SQLException Class Hierarchy](#sqlexception-class-hierarchy)
4. [Important Fields & Methods of SQLException](#important-fields--methods-of-sqlexception)
5. [Approach 1: Single try-catch with SQLException](#approach-1-single-try-catch-with-sqlexception)
6. [Approach 2: Chained Exception Traversal (getNextException)](#approach-2-chained-exception-traversal-getnextexception)
7. [Approach 3: Multi-catch by Specific SQLException Subclass](#approach-3-multi-catch-by-specific-sqlexception-subclass)
8. [Approach 4: try-with-resources (Auto-Closeable)](#approach-4-try-with-resources-auto-closeable)
9. [Approach 5: finally-block Manual Cleanup (Pre-Java 7 style)](#approach-5-finally-block-manual-cleanup-pre-java-7-style)
10. [Approach 6: SQLWarning Handling](#approach-6-sqlwarning-handling)
11. [Approach 7: Custom/Wrapped Exception Propagation](#approach-7-customwrapped-exception-propagation)
12. [Comparison of Approaches](#comparison-of-approaches)
13. [Exception Flow Diagram](#exception-flow-diagram)
14. [Common SQLException Subclasses](#common-sqlexception-subclasses)
15. [Common Exceptions & Causes](#common-exceptions--causes)
16. [Important Notes](#important-notes)

---

## Overview

JDBC exception handling differs from standard Java exception handling because almost **every JDBC operation** (connecting, executing, fetching, closing) can throw a **checked** `SQLException`. JDBC also supports **chained exceptions** (multiple causes linked together) and **non-fatal warnings** (`SQLWarning`) — both of which standard try-catch alone does not address well.

This document focuses on the **different structural approaches** to handle these situations, not on one single full program.

---

## Key Characteristics

| Feature | Detail |
|---|---|
| Base exception | `java.sql.SQLException` (checked) |
| Parent class | `java.lang.Exception` |
| Chaining support | Yes — via `getNextException()` (SQL-specific chain) and `getCause()` (standard Java chain) |
| Resource safety | `Connection`, `Statement`, `ResultSet` all implement `AutoCloseable` |
| Non-fatal alerts | `SQLWarning` — does NOT stop execution, must be checked explicitly |
| Vendor info | Carries `SQLState` (ANSI/ODBC code) and vendor-specific `errorCode` |
| Subclasses (JDBC 4+) | More specific exception types added for fine-grained catching |

---

## SQLException Class Hierarchy

```
java.lang.Throwable
        │
        ▼
java.lang.Exception
        │
        ▼
java.sql.SQLException  (checked, implements Iterable<Throwable> since JDBC 4.0)
        │
        ├── java.sql.SQLNonTransientException
        │       ├── SQLDataException
        │       ├── SQLIntegrityConstraintViolationException
        │       ├── SQLInvalidAuthorizationSpecException
        │       ├── SQLSyntaxErrorException
        │       └── SQLFeatureNotSupportedException
        │
        ├── java.sql.SQLTransientException
        │       ├── SQLTimeoutException
        │       ├── SQLTransactionRollbackException
        │       └── SQLTransientConnectionException
        │
        ├── java.sql.SQLRecoverableException
        │
        └── java.sql.BatchUpdateException   (thrown during executeBatch())

java.sql.SQLWarning  (extends SQLException, but NON-FATAL — chained on Connection/Statement/ResultSet)
```

---

## Important Fields & Methods of SQLException

| Member | Description |
|---|---|
| `getMessage()` | Human-readable description of the error |
| `getSQLState()` | 5-character ANSI/ODBC standard error code (e.g., `"23505"` for unique violation) |
| `getErrorCode()` | Vendor/database-specific numeric error code |
| `getNextException()` | Returns the next chained `SQLException` (JDBC-specific chain, separate from `getCause()`) |
| `setNextException(SQLException ex)` | Manually link another exception into the chain |
| `getCause()` | Standard Java exception chaining (underlying root cause, e.g., `IOException`) |
| `iterator()` | Iterates through the chained exceptions (JDBC 4.0+, enables for-each) |

> **Note:** `getNextException()` (SQL-specific siblings) and `getCause()` (Java-standard cause chain) are **two separate chains** that can both exist simultaneously.

---

## Approach 1: Single try-catch with SQLException

**When to use:** Simple applications where all SQL errors are treated uniformly.

```java
try {
    // open connection, execute query
} catch (SQLException e) {
    // single handler — log message, SQLState, error code
    logger.error("SQLState: " + e.getSQLState() +
                 ", ErrorCode: " + e.getErrorCode() +
                 ", Message: " + e.getMessage());
}
```

**Trade-off:** Easy to write, but loses granularity — can't react differently to "duplicate key" vs "connection lost."

---

## Approach 2: Chained Exception Traversal (getNextException)

**When to use:** Batch operations or multi-statement transactions where **multiple SQLExceptions** can occur and get linked together by the driver.

```java
try {
    // ... JDBC operations that may chain multiple SQLExceptions
} catch (SQLException e) {
    // walk the SQL-specific chain manually
    while (e != null) {
        // process e.getMessage(), e.getSQLState(), e.getErrorCode()
        e = e.getNextException();
    }
}
```

**Alternative (JDBC 4.0+, since SQLException implements Iterable):**
```java
catch (SQLException e) {
    for (Throwable t : e) {
        // iterates the chain automatically
    }
}
```

**Trade-off:** Necessary for completeness — a single `catch` block alone only sees the **first** exception in the chain; subsequent linked errors are silently lost without traversal.

---

## Approach 3: Multi-catch by Specific SQLException Subclass

**When to use:** Different recovery logic needed per error category (e.g., retry on transient errors, fail fast on syntax errors).

```java
try {
    // JDBC operation
} catch (SQLIntegrityConstraintViolationException e) {
    // duplicate key / FK violation — handle as business rule conflict
} catch (SQLSyntaxErrorException e) {
    // bad SQL — programmer error, fail immediately, do not retry
} catch (SQLTransientConnectionException e) {
    // network blip — safe to retry with backoff
} catch (SQLTimeoutException e) {
    // query took too long — maybe retry with shorter query
} catch (SQLException e) {
    // fallback for anything not specifically handled above
}
```

**Trade-off:** More verbose, but enables **error-specific recovery strategy** instead of one-size-fits-all logging. Order matters — catch specific subclasses before the general `SQLException`.

---

## Approach 4: try-with-resources (Auto-Closeable)

**When to use:** Default/recommended approach for all modern JDBC code (Java 7+) — guarantees resources close even on exception, without manual `finally`.

```java
// Connection, Statement, PreparedStatement, ResultSet all implement AutoCloseable
try (Connection con = DriverManager.getConnection(url, user, pass);
     PreparedStatement ps = con.prepareStatement(sql);
     ResultSet rs = ps.executeQuery()) {

    // use rs ...

} catch (SQLException e) {
    // resources are already closed automatically before this block runs
    // even if an exception occurred mid-way
}
```

**Trade-off:** Eliminates resource-leak bugs entirely. Resources close in **reverse order of declaration**. If both the try-block and the auto-close throw, the close-exception is **suppressed** and attached via `getSuppressed()` on the original exception.

---

## Approach 5: finally-block Manual Cleanup (Pre-Java 7 style)

**When to use:** Legacy codebases, or environments restricted to older Java versions.

```java
Connection con = null;
Statement st = null;
ResultSet rs = null;
try {
    con = DriverManager.getConnection(url, user, pass);
    st = con.createStatement();
    rs = st.executeQuery(sql);
    // process rs
} catch (SQLException e) {
    // handle error
} finally {
    // must close in reverse order, each wrapped in its own try-catch
    // because close() itself can throw SQLException
    try { if (rs != null) rs.close(); } catch (SQLException e) { /* log */ }
    try { if (st != null) st.close(); } catch (SQLException e) { /* log */ }
    try { if (con != null) con.close(); } catch (SQLException e) { /* log */ }
}
```

**Trade-off:** Verbose and error-prone (forgetting a null-check or nested try is a common bug source). Superseded by Approach 4 in all modern code — included here only for legacy maintenance awareness.

---

## Approach 6: SQLWarning Handling

**When to use:** Situations needing visibility into **non-fatal** issues (e.g., data truncation) that do NOT throw and do NOT stop execution.

```java
// Warnings are NOT thrown — they must be explicitly retrieved
SQLWarning warning = con.getWarnings();  // or st.getWarnings(), rs.getWarnings()
while (warning != null) {
    // log warning.getMessage(), warning.getSQLState()
    warning = warning.getNextWarning();  // separate chain from SQLException
}
con.clearWarnings(); // optional: reset for next reuse of same Connection/Statement
```

**Trade-off:** Easy to forget entirely since no exception is thrown — must be **proactively polled** after operations; otherwise silent data issues (e.g., truncated VARCHAR) go unnoticed.

---

## Approach 7: Custom/Wrapped Exception Propagation

**When to use:** Layered application architecture (DAO → Service → Controller) where SQL-specific details should not leak into upper layers.

```java
// Custom unchecked exception in the application's own exception hierarchy
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, SQLException cause) {
        super(message, cause); // preserves original SQLException via getCause()
    }
}

// In DAO layer:
try {
    // JDBC operation
} catch (SQLException e) {
    // wrap and rethrow as unchecked — caller doesn't need to know it's JDBC
    throw new DataAccessException("Failed to fetch user record", e);
}
```

**Trade-off:** Decouples upper layers from `java.sql.*`, but requires discipline to **always preserve `cause`** so root SQL error isn't lost during debugging.

---

## Comparison of Approaches

| Approach | Granularity | Resource Safety | Use Case |
|---|---|---|---|
| 1. Single catch | Low | Manual | Quick scripts, prototypes |
| 2. Chain traversal | Medium | Manual | Batch/multi-error scenarios |
| 3. Multi-catch by subclass | High | Manual | Error-specific recovery logic |
| 4. try-with-resources | Medium–High | **Automatic** | **Default for all modern JDBC code** |
| 5. finally cleanup | Medium | Manual, verbose | Legacy (pre-Java 7) only |
| 6. SQLWarning polling | N/A (non-fatal) | N/A | Data-integrity audits |
| 7. Wrapped/custom exception | High (architectural) | Depends on inner approach | Layered enterprise applications |

---

## Exception Flow Diagram

```
                JDBC Operation Invoked
                        │
                        ▼
          ┌─────────────────────────────┐
          │ Does operation fail?        │
          └─────────────────────────────┘
              │                  │
             No                 Yes
              │                  ▼
              │      ┌───────────────────────────┐
              │      │  SQLException thrown      │
              │      └───────────────────────────┘
              │                  │
              │                  ▼
              │      ┌───────────────────────────┐
              │      │ getNextException() chain? │
              │      └───────────────────────────┘
              │             │           │
              │            Yes          No
              │             ▼            │
              │   ┌─────────────────┐    │
              │   │ Traverse chain  │    │
              │   │ (Approach 2)    │    │
              │   └─────────────────┘    │
              │             │            │
              │             ▼            ▼
              │      ┌──────────────────────────┐
              │      │ Catch by subclass type   │
              │      │ (Approach 3) OR generic  │
              │      │ SQLException (Approach 1)│
              │      └──────────────────────────┘
              │                  │
              │                  ▼
              │      ┌───────────────────────────┐
              │      │ Wrap as custom exception? │
              │      │ (Approach 7, optional)    │
              │      └───────────────────────────┘
              ▼                  ▼
   ┌─────────────────────────────────────┐
   │  finally / try-with-resources:      │
   │  close ResultSet → Statement →      │
   │  Connection (Approach 4 or 5)       │
   └─────────────────────────────────────┘
                        │
                        ▼
          ┌───────────────────────────────┐
          │ Check SQLWarning separately   │
          │ (Approach 6 — non-fatal path) │
          └───────────────────────────────┘
```

---

## Common SQLException Subclasses

| Subclass | Typical Trigger |
|---|---|
| `SQLIntegrityConstraintViolationException` | Primary key / unique / foreign key violation |
| `SQLSyntaxErrorException` | Malformed SQL query |
| `SQLDataException` | Invalid data type, out-of-range value |
| `SQLTimeoutException` | Query/connection exceeded timeout threshold |
| `SQLTransientConnectionException` | Temporary connection failure (network blip) |
| `SQLNonTransientConnectionException` | Persistent connection failure (won't succeed on retry) |
| `SQLInvalidAuthorizationSpecException` | Wrong username/password |
| `BatchUpdateException` | One or more statements failed during `executeBatch()` |

---

## Common Exceptions & Causes

| Exception | Cause | Typical Fix |
|---|---|---|
| `SQLException: No suitable driver found` | JDBC driver not registered/loaded | Add driver JAR to classpath; check `Class.forName()` or driver auto-registration |
| `SQLException: Connection refused` | DB server down or wrong host/port | Verify DB server is running; check URL |
| `SQLIntegrityConstraintViolationException` | Duplicate primary key insert | Check existing rows before insert, or handle via `ON CONFLICT` |
| `SQLSyntaxErrorException` | Typo in SQL string | Validate SQL; use `PreparedStatement` instead of string concatenation |
| `BatchUpdateException` | One row in batch failed | Use `getUpdateCounts()` to find which statement failed |
| `SQLTimeoutException` | Long-running query exceeded `setQueryTimeout()` | Optimize query, add index, or increase timeout |
| `IllegalStateException` after `close()` | Using a `Connection`/`ResultSet` after it was closed | Recheck object lifecycle and scope |

---

## Important Notes

1. **Always catch specific subclasses before the generic `SQLException`** — Java requires more specific catch blocks to appear first, otherwise it's a compile error (unreachable catch).

2. **`getNextException()` ≠ `getCause()`** — these are two independent chains. `getNextException()` is JDBC's own SQL-error chain (set by the driver); `getCause()` is the standard Java throwable chain (e.g., wraps an `IOException` from the network layer). Always check both when debugging.

3. **`SQLWarning` is silent by design** — it does not interrupt control flow. If data-integrity correctness matters (e.g., string truncation), explicitly poll `getWarnings()` after every relevant operation; it is never delivered via `catch`.

4. **try-with-resources is the modern default** — prefer Approach 4 unless constrained to Java 6 or earlier. It also correctly handles **suppressed exceptions** (e.g., if `close()` itself throws while another exception is already in flight), which the manual `finally` approach typically discards or overwrites.

5. **`BatchUpdateException.getUpdateCounts()`** — when using `executeBatch()`, this returns an `int[]` indicating how many rows succeeded per statement before the failure; a value of `Statement.EXECUTE_FAILED` (`-3`) marks the specific failed statement.

6. **Wrap JDBC exceptions at the DAO boundary** — in layered architectures (DAO/Service/Controller), translate `SQLException` into an application-specific unchecked exception (Approach 7) so service/controller layers don't depend on `java.sql.*` and so checked-exception propagation doesn't pollute every method signature up the call stack.

7. **`getSQLState()` is portable; `getErrorCode()` is not** — `SQLState` follows the ANSI/ODBC standard (mostly consistent across databases), while `errorCode` is vendor-specific (e.g., MySQL vs Oracle have different numeric codes for the same logical error). Prefer `SQLState` when writing database-agnostic error handling.

8. **Don't swallow exceptions silently** — an empty `catch (SQLException e) {}` block is a common anti-pattern that hides connection/data errors; always log at minimum, even in the simplest single-catch approach.