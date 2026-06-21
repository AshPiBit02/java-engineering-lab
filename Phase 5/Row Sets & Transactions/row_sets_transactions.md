# JDBC RowSet & Transactions

---

## Table of Contents
1. [Part A: RowSet](#part-a-rowset)
   - [Overview](#overview)
   - [Key Characteristics](#key-characteristics)
   - [Class Hierarchy](#class-hierarchy)
   - [RowSet Types](#rowset-types)
   - [Important Methods](#important-methods)
   - [Approach 1: JdbcRowSet (Connected)](#approach-1-jdbcrowset-connected)
   - [Approach 2: CachedRowSet (Disconnected)](#approach-2-cachedrowset-disconnected)
   - [Real-Life Use Cases](#real-life-use-cases)
2. [Part B: Transactions](#part-b-transactions)
   - [Overview](#overview-1)
   - [Key Characteristics](#key-characteristics-1)
   - [ACID Properties](#acid-properties)
   - [Important Methods](#important-methods-1)
   - [Transaction Isolation Levels](#transaction-isolation-levels)
   - [Approach 1: Basic Commit/Rollback](#approach-1-basic-commitrollback)
   - [Approach 2: Savepoints (Partial Rollback)](#approach-2-savepoints-partial-rollback)
   - [Real-Life Use Cases](#real-life-use-cases-1)
3. [Combined Flow Diagram](#combined-flow-diagram)
4. [Common Exceptions](#common-exceptions)
5. [Important Notes](#important-notes)

---

# Part A: RowSet

## Overview

`RowSet` is a JavaBean-style wrapper around `ResultSet` — it can be used like a normal `ResultSet` but additionally supports being **disconnected** from the database, **serialized**, and passed around (e.g., to a UI layer) without holding a live connection open.

## Key Characteristics

| Feature | Detail |
|---|---|
| Package | `javax.sql.RowSet` (extends `java.sql.ResultSet`) |
| Connection model | Connected (`JdbcRowSet`) or Disconnected (`CachedRowSet`, etc.) |
| Serializable | Disconnected RowSets implement `Serializable` |
| JavaBean | Has properties (e.g., `setUrl`, `setCommand`) settable without a `Connection` object upfront |
| Event support | Supports listeners (`RowSetListener`) for row-changed/inserted/deleted events |

## Class Hierarchy

```
java.sql.ResultSet
        │
        ▼
javax.sql.RowSet  (interface)
        │
        ├── JdbcRowSet            ← connected, thin wrapper over ResultSet
        ├── CachedRowSet          ← disconnected, in-memory, most commonly used
        │       ├── WebRowSet         ← CachedRowSet + can read/write as XML
        │       ├── FilteredRowSet    ← CachedRowSet + filter criteria
        │       └── JoinRowSet        ← combines multiple RowSets (in-memory join)
```

## RowSet Types

| Type | Connected? | Typical Use |
|---|---|---|
| `JdbcRowSet` | Yes (holds live `Connection`) | Lightweight scrollable/updatable wrapper |
| `CachedRowSet` | No (fetches data, then disconnects) | Pass data to UI/another layer without holding DB connection |
| `WebRowSet` | No | Export/import result data as XML |
| `FilteredRowSet` | No | Apply in-memory filter predicate without re-querying DB |
| `JoinRowSet` | No | Join multiple disconnected RowSets in memory |

## Important Methods

| Method | Description |
|---|---|
| `setUrl(String)` / `setUsername(...)` / `setPassword(...)` | Configure connection info as bean properties |
| `setCommand(String sql)` | Set the query to execute |
| `execute()` | Run the configured query, populate the RowSet |
| `acceptChanges()` | (CachedRowSet) Push in-memory changes back to the database |
| `acceptChanges(Connection con)` | Same, using an explicit connection |
| `addRowSetListener(RowSetListener)` | Register a listener for row change events |

## Approach 1: JdbcRowSet (Connected)

```java
JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet();
jrs.setUrl("jdbc:mysql://localhost/db");
jrs.setUsername("user");
jrs.setPassword("pass");
jrs.setCommand("SELECT * FROM employee");
jrs.execute();   // runs query, stays connected like a normal scrollable ResultSet
```

## Approach 2: CachedRowSet (Disconnected)

```java
CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
crs.setCommand("SELECT * FROM employee");
crs.setUrl("jdbc:mysql://localhost/db");
crs.execute();      // fetches data, then DISCONNECTS from DB

// ... pass crs to another layer/thread, no open connection consumed ...

crs.absolute(2);            // scrollable like ResultSet, works while disconnected
crs.updateString("name", "Updated");
crs.updateRow();
crs.acceptChanges();        // reconnects briefly to push changes back to DB
```

## Real-Life Use Cases

- **Disconnected data for UI binding** — fetch once, hand off to a Swing/web layer without keeping a DB connection open per user session.
- **Connection pooling efficiency** — `CachedRowSet` releases the connection immediately after fetch, freeing pool slots for other requests.
- **Caching/offline editing** — edit rows in memory, then sync back with `acceptChanges()` only when ready.
- **XML data interchange** — `WebRowSet` can write query results to XML for transport between systems.

---

# Part B: Transactions

## Overview

A **transaction** groups multiple SQL operations into a single atomic unit — either all succeed (`commit`) or all are undone (`rollback`). JDBC manages this through the `Connection` object.

## Key Characteristics

| Feature | Detail |
|---|---|
| Default mode | Auto-commit **ON** — every statement commits immediately |
| Manual control | `con.setAutoCommit(false)` to start a manual transaction |
| Scope | Spans multiple statements on the same `Connection` |
| Isolation | Controlled via `setTransactionIsolation(int level)` |
| Partial rollback | Supported via `Savepoint` |

## ACID Properties

| Property | Meaning |
|---|---|
| **A**tomicity | All statements in the transaction succeed, or none do |
| **C**onsistency | DB moves from one valid state to another valid state |
| **I**solation | Concurrent transactions don't interfere (controlled by isolation level) |
| **D**urability | Once committed, changes survive crashes/restarts |

## Important Methods

| Method | Description |
|---|---|
| `setAutoCommit(boolean)` | Enable/disable auto-commit mode |
| `commit()` | Permanently saves all changes since last commit/rollback |
| `rollback()` | Undoes all changes since last commit |
| `rollback(Savepoint sp)` | Undoes changes only back to a specific savepoint |
| `setSavepoint()` / `setSavepoint(String name)` | Marks a point to roll back to later |
| `releaseSavepoint(Savepoint sp)` | Removes a savepoint (no longer needed) |
| `setTransactionIsolation(int level)` | Sets concurrency isolation level |
| `getTransactionIsolation()` | Reads current isolation level |

## Transaction Isolation Levels

| Constant | Dirty Read | Non-Repeatable Read | Phantom Read |
|---|---|---|---|
| `TRANSACTION_READ_UNCOMMITTED` | Possible | Possible | Possible |
| `TRANSACTION_READ_COMMITTED` | Prevented | Possible | Possible |
| `TRANSACTION_REPEATABLE_READ` | Prevented | Prevented | Possible |
| `TRANSACTION_SERIALIZABLE` | Prevented | Prevented | Prevented |

## Approach 1: Basic Commit/Rollback

```java
con.setAutoCommit(false);            // start manual transaction
try {
    ps1.executeUpdate();             // e.g., debit account
    ps2.executeUpdate();             // e.g., credit account
    con.commit();                    // both succeed together
} catch (SQLException e) {
    con.rollback();                  // undo both on any failure
} finally {
    con.setAutoCommit(true);         // restore default behavior
}
```

## Approach 2: Savepoints (Partial Rollback)

```java
con.setAutoCommit(false);
ps1.executeUpdate();                          // statement 1
Savepoint sp1 = con.setSavepoint("afterStep1"); // mark point
ps2.executeUpdate();                          // statement 2 — risky step

try {
    ps3.executeUpdate();                      // statement 3 — may fail
    con.commit();
} catch (SQLException e) {
    con.rollback(sp1);   // undo only statement 2/3, KEEP statement 1
    con.commit();        // commit the partial, still-valid state
}
```

## Real-Life Use Cases

- **Bank transfers** — debit + credit must both succeed or neither happens (classic atomicity example).
- **Order processing** — insert order, deduct inventory, create invoice — all-or-nothing.
- **Batch imports with partial recovery** — use savepoints so a single bad record doesn't roll back an entire large import.
- **Booking systems** — reserve seat + process payment together; rollback releases the seat if payment fails.

---

## Combined Flow Diagram

```
                JDBC Transaction + RowSet Interaction
   ┌─────────────────────────────────────────────────────────┐
   │ con.setAutoCommit(false)                                │
   └─────────────────────────────────────────────────────────┘
                            │
                            ▼
   ┌─────────────────────────────────────────────────────────┐
   │ Execute DML via PreparedStatement / RowSet.execute()    │
   └─────────────────────────────────────────────────────────┘
                            │
                            ▼
   ┌─────────────────────────────────────────────────────────┐
   │  CachedRowSet: disconnects after fetch                  │
   │  (edits happen in memory, no live transaction held)     │
   └─────────────────────────────────────────────────────────┘
                            │
                            ▼
              ┌─────────────────────────┐
              │ acceptChanges() called? │
              └─────────────────────────┘
                  │                │
                 Yes               No
                  ▼                ▼
   ┌──────────────────────┐   ┌──────────────────────┐
   │ Reconnects briefly,  │   │ Changes remain only  │
   │ pushes changes as DML│   │ in memory (discarded)│
   └──────────────────────┘   └──────────────────────┘
                  │
                  ▼
   ┌─────────────────────────────────────────────────────────┐
   │ All statements succeeded? → commit()                    │
   │ Any failure?              → rollback() [or rollback(sp)]│
   └─────────────────────────────────────────────────────────┘
```

---

## Common Exceptions

| Exception | Cause | Fix |
|---|---|---|
| `SQLException: Connection has already been closed` | Using a `CachedRowSet` reference after underlying ops finished incorrectly | Ensure `execute()`/`acceptChanges()` calls happen on a valid connection config |
| `SQLException: Cannot commit when autoCommit is true` | Calling `commit()` without disabling auto-commit first | Call `setAutoCommit(false)` before manual `commit()`/`rollback()` |
| `SQLException: Savepoint not found` | Using a `Savepoint` after it was released or from a different transaction | Recreate savepoint within the active transaction scope |
| `SyncProviderException` | Conflict during `acceptChanges()` (row changed in DB since fetch) | Implement conflict resolution or re-fetch before retrying |
| `SQLFeatureNotSupportedException` | Isolation level not supported by the DB/driver | Check driver docs; use a supported level |

---

## Important Notes

1. **`CachedRowSet` trades freshness for efficiency** — since it disconnects after fetching, it does not see DB changes made by others until re-executed; ideal when slight staleness is acceptable in exchange for not holding a connection.

2. **`acceptChanges()` can conflict** — if the underlying row changed in the database between fetch and sync-back, a `SyncProviderException` occurs; real-world systems often need a conflict-resolution strategy (e.g., last-write-wins, or manual merge).

3. **Auto-commit must be explicitly disabled for multi-statement atomicity** — by default every statement commits on its own; forgetting `setAutoCommit(false)` means `rollback()` has nothing meaningful to undo.

4. **Savepoints enable partial rollback within one transaction** — useful for long batch operations where one failed step shouldn't discard all prior valid work.

5. **Higher isolation levels reduce concurrency anomalies but cost performance** — `SERIALIZABLE` is safest but slowest (most locking); `READ_COMMITTED` is the common practical default in most production systems.

6. **Always restore `setAutoCommit(true)`** after a manual transaction block (or properly manage connection-pool reset) to avoid leaving a pooled connection in an unexpected transactional state for the next user.