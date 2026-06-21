# JDBC ResultSet

---

## Table of Contents
1. [Overview](#overview)
2. [Key Characteristics](#key-characteristics)
3. [Class & Interface Hierarchy](#class--interface-hierarchy)
4. [ResultSet Types (Scrollability)](#resultset-types-scrollability)
5. [ResultSet Concurrency Modes](#resultset-concurrency-modes)
6. [ResultSet Holdability](#resultset-holdability)
7. [Creating a Configured ResultSet](#creating-a-configured-resultset)
8. [Cursor Navigation Methods](#cursor-navigation-methods)
9. [Data Retrieval (getXxx) Methods](#data-retrieval-getxxx-methods)
10. [Data Modification (updateXxx) Methods — Updatable ResultSet](#data-modification-updatexxx-methods--updatable-resultset)
11. [ResultSetMetaData](#resultsetmetadata)
12. [Working Sequence](#working-sequence)
13. [Cursor Flow Diagram](#cursor-flow-diagram)
14. [Approach 1: Forward-Only Read (Default)](#approach-1-forward-only-read-default)
15. [Approach 2: Scrollable Navigation](#approach-2-scrollable-navigation)
16. [Approach 3: Updatable ResultSet (Live Update)](#approach-3-updatable-resultset-live-update)
17. [Approach 4: Reading via ResultSetMetaData (Dynamic Columns)](#approach-4-reading-via-resultsetmetadata-dynamic-columns)
18. [Common Exceptions](#common-exceptions)
19. [Important Notes](#important-notes)

---

## Overview

A `ResultSet` represents the **tabular result of a SQL query** — a cursor positioned over a set of rows fetched from the database. It is not a static in-memory table by default; it is typically a **live cursor** over the underlying data, whose behavior (scrolling, updating, visibility of changes) is controlled by configuration flags set **before** the query executes.

---

## Key Characteristics

| Feature | Detail |
|---|---|
| Package | `java.sql.ResultSet` |
| Initial cursor position | Before the first row (`beforeFirst`) |
| Default type | `TYPE_FORWARD_ONLY` (cursor moves forward only) |
| Default concurrency | `CONCUR_READ_ONLY` |
| Column indexing | **1-based**, not 0-based |
| Column access | By index (`int`) or by label/name (`String`) |
| Auto-closed | When parent `Statement` is closed or re-executed |
| Null detection | `wasNull()` — checked **after** a `getXxx()` call |

---

## Class & Interface Hierarchy

```
java.sql.Wrapper
        │
        ▼
java.sql.ResultSet  (interface)
        │
        ├── implemented by JDBC driver-specific classes
        │       (e.g., com.mysql.cj.jdbc.result.ResultSetImpl)
        │
        └── extended by:
                java.sql.RowSet  (disconnected, JavaBean-based variant)
                        ├── JdbcRowSet
                        ├── CachedRowSet
                        ├── WebRowSet
                        └── FilteredRowSet / JoinRowSet

Related:
java.sql.ResultSetMetaData  (describes columns of a ResultSet — separate interface)
```

---

## ResultSet Types (Scrollability)

| Constant | Cursor Movement | Sees Live DB Changes? |
|---|---|---|
| `TYPE_FORWARD_ONLY` | Forward only (`next()` only) | No (default, fastest) |
| `TYPE_SCROLL_INSENSITIVE` | Forward + backward + absolute/relative jump | No — snapshot at query time |
| `TYPE_SCROLL_SENSITIVE` | Forward + backward + absolute/relative jump | Yes — reflects underlying DB changes |

---

## ResultSet Concurrency Modes

| Constant | Meaning |
|---|---|
| `CONCUR_READ_ONLY` | Cursor cannot be used to update rows (default) |
| `CONCUR_UPDATABLE` | Cursor supports `updateXxx()` + `updateRow()` / `insertRow()` / `deleteRow()` |

---

## ResultSet Holdability

| Constant | Behavior on `commit()` |
|---|---|
| `HOLD_CURSORS_OVER_COMMIT` | ResultSet stays open after transaction commit |
| `CLOSE_CURSORS_AT_COMMIT` | ResultSet closes automatically at commit (default for most drivers) |

---

## Creating a Configured ResultSet

```java
// Type and concurrency are set when creating the Statement, NOT the ResultSet itself
Statement st = con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,   // scrollability
        ResultSet.CONCUR_UPDATABLE           // concurrency
);
ResultSet rs = st.executeQuery("SELECT * FROM employee");
// rs now inherits the scrollable + updatable behavior from the Statement
```

> Same pattern applies to `PreparedStatement`:
> `con.prepareStatement(sql, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE)`

---

## Cursor Navigation Methods

| Method | Description | Requires |
|---|---|---|
| `next()` | Moves cursor forward one row; `false` if no more rows | Any type |
| `previous()` | Moves cursor backward one row | Scrollable only |
| `first()` | Moves to the first row | Scrollable only |
| `last()` | Moves to the last row | Scrollable only |
| `beforeFirst()` | Moves before the first row (initial position) | Scrollable only |
| `afterLast()` | Moves after the last row | Scrollable only |
| `absolute(int row)` | Moves to a specific row number (negative = from end) | Scrollable only |
| `relative(int rows)` | Moves cursor by a relative offset (+/-) | Scrollable only |
| `getRow()` | Returns current row number (0 if not on a row) | Any type |
| `isFirst()` / `isLast()` | Checks if cursor is at first/last row | Scrollable only |
| `isBeforeFirst()` / `isAfterLast()` | Checks boundary position | Scrollable only |

---

## Data Retrieval (getXxx) Methods

| Method | Returns |
|---|---|
| `getInt(int/String)` | `int` |
| `getString(int/String)` | `String` |
| `getDouble(int/String)` | `double` |
| `getBoolean(int/String)` | `boolean` |
| `getDate(int/String)` | `java.sql.Date` |
| `getTimestamp(int/String)` | `java.sql.Timestamp` |
| `getObject(int/String)` | `Object` (generic, type inferred by driver) |
| `getBlob(int/String)` | `Blob` (binary large object) |
| `getClob(int/String)` | `Clob` (character large object) |
| `wasNull()` | `boolean` — `true` if the last `getXxx()` read returned SQL `NULL` |

> Column access can be by **1-based index** (`getString(1)`) or **column label** (`getString("name")`). Label lookup is more readable but marginally slower (extra lookup by driver).

---

## Data Modification (updateXxx) Methods — Updatable ResultSet

| Method | Description |
|---|---|
| `updateInt(int/String, int)` | Stages an update to a column in the **current row** (in memory) |
| `updateString(int/String, String)` | Same, for `String` |
| `updateRow()` | Commits staged column updates to the database for current row |
| `cancelRowUpdates()` | Discards staged updates before `updateRow()` is called |
| `insertRow()` | Inserts a new row (after positioning cursor via `moveToInsertRow()`) |
| `moveToInsertRow()` | Moves cursor to a special insert row buffer |
| `moveToCurrentRow()` | Returns cursor from insert-row buffer back to last known row |
| `deleteRow()` | Deletes the current row from both ResultSet and database |
| `refreshRow()` | Re-fetches current row's data from database (for `TYPE_SCROLL_SENSITIVE`) |

---

## ResultSetMetaData

Obtained via `rs.getMetaData()` — describes the **shape** of the result (column count, names, types) without knowing the query in advance.

| Method | Description |
|---|---|
| `getColumnCount()` | Number of columns in the ResultSet |
| `getColumnName(int)` | Underlying DB column name |
| `getColumnLabel(int)` | Column alias used in SQL (`AS`) — falls back to name if no alias |
| `getColumnType(int)` | SQL type code (`java.sql.Types.*`) |
| `getColumnTypeName(int)` | Database-specific type name (e.g., `"VARCHAR"`) |
| `getColumnDisplaySize(int)` | Max display width of the column |
| `isNullable(int)` | Whether column can hold SQL `NULL` |
| `getTableName(int)` | Source table name for the column |

---

## Working Sequence

```
1. Statement/PreparedStatement created with desired TYPE + CONCURRENCY
2. executeQuery() runs → returns ResultSet
3. Cursor starts BEFORE the first row
4. next() called → cursor moves to row 1 → returns true
5. getXxx() calls extract column values from current row
6. (Optional) updateXxx() + updateRow() to modify current row
7. Loop: next() repeatedly until it returns false (no more rows)
8. ResultSet closed (explicitly or via Statement/Connection close)
```

---

## Cursor Flow Diagram

```
                    ResultSet Cursor Lifecycle
   ┌───────────────────────────────────────────────────────────┐
   │                                                           │
   │   [beforeFirst]                                           │
   │        │                                                  │
   │        │ next()                                           │
   │        ▼                                                  │
   │   ┌─────────┐  next() ┌─────────┐  next() ┌─────────┐     │
   │   │  Row 1  │────────►│  Row 2  │────────►│  Row N  │     │
   │   └─────────┘         └─────────┘         └─────────┘     │
   │        ▲   │previous()     ▲   │previous()      │         │
   │        └───┘               └───┘                │ next()  │
   │     (scrollable only)  (scrollable only)         ▼        │
   │                                              [afterLast]  │
   │                                            next() returns │
   │                                                 false     │
   └───────────────────────────────────────────────────────────┘

   absolute(n) / relative(n) / first() / last()  → direct jumps
   (only valid when TYPE_SCROLL_INSENSITIVE or TYPE_SCROLL_SENSITIVE)
```

---

## Approach 1: Forward-Only Read (Default)

**When to use:** Simple sequential read of query results — most common case.

```java
Statement st = con.createStatement();              // default: FORWARD_ONLY, READ_ONLY
ResultSet rs = st.executeQuery("SELECT id, name FROM employee");

while (rs.next()) {            // moves cursor forward; loop ends when no more rows
    int id = rs.getInt("id");        // access by column label
    String name = rs.getString("name");
    // process row
}
```

**Trade-off:** Fastest and least memory-intensive (driver may stream rows), but cursor **cannot move backward** — must re-execute the query to read again.

---

## Approach 2: Scrollable Navigation

**When to use:** UI pagination, jumping to specific rows, displaying "Page N of M," or re-reading earlier rows without re-querying.

```java
Statement st = con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,   // snapshot, scrollable both ways
        ResultSet.CONCUR_READ_ONLY);
ResultSet rs = st.executeQuery("SELECT * FROM employee");

rs.last();                     // jump to last row
int totalRows = rs.getRow();   // get total row count via cursor position
rs.absolute(5);                // jump directly to row 5
rs.previous();                 // step back to row 4
rs.beforeFirst();              // reset cursor to start for a fresh forward scan
```

**Trade-off:** Enables random access, but `TYPE_SCROLL_INSENSITIVE` takes a **snapshot** — later changes made by other transactions in the DB are not reflected unless `refreshRow()` is called (and even then, only with `TYPE_SCROLL_SENSITIVE`).

---

## Approach 3: Updatable ResultSet (Live Update)

**When to use:** Editing rows directly through the cursor instead of writing separate `UPDATE`/`INSERT`/`DELETE` SQL statements.

```java
Statement st = con.createStatement(
        ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE);
ResultSet rs = st.executeQuery("SELECT id, salary FROM employee");

while (rs.next()) {
    if (rs.getInt("id") == 101) {
        rs.updateDouble("salary", 55000.0);  // stage change in memory
        rs.updateRow();                      // commit change to DB for this row
        break;
    }
}

// Inserting a new row:
rs.moveToInsertRow();              // switch to special insert buffer
rs.updateInt("id", 200);
rs.updateString("name", "New Hire");
rs.insertRow();                    // commit new row to DB
rs.moveToCurrentRow();             // return cursor to normal position
```

**Trade-off:** Convenient for row-level edits during iteration, but requires driver support for `CONCUR_UPDATABLE` (not all queries are updatable — e.g., joins, aggregates, queries without a primary key are typically rejected).

---

## Approach 4: Reading via ResultSetMetaData (Dynamic Columns)

**When to use:** Generic/reusable code that must process query results **without knowing column names or count in advance** (e.g., a generic table-printer utility, ORMs, admin tools).

```java
ResultSet rs = st.executeQuery(query);          // query unknown ahead of time
ResultSetMetaData meta = rs.getMetaData();      // describe the result shape
int colCount = meta.getColumnCount();

while (rs.next()) {
    for (int i = 1; i <= colCount; i++) {       // metadata columns are 1-indexed too
        String colName = meta.getColumnLabel(i);
        Object value = rs.getObject(i);          // generic retrieval, type-agnostic
        // print or process colName + value
    }
}
```

**Trade-off:** Fully dynamic and reusable across any query, but loses compile-time type safety — every value comes back as `Object` and must be cast/checked at runtime.

---

## Common Exceptions

| Exception | Cause | Fix |
|---|---|---|
| `SQLException: ResultSet is closed` | Accessing `rs` after `Statement`/`Connection` closed, or after re-executing the statement | Don't reuse `rs` past its parent's lifecycle; re-query if needed |
| `SQLException: Invalid column index/name` | Wrong index (not 1-based) or misspelled column label | Verify column count via metadata; check SQL alias spelling |
| `SQLException: Operation not allowed for forward-only resultset` | Calling `previous()`/`absolute()` on a `TYPE_FORWARD_ONLY` result | Recreate Statement with `TYPE_SCROLL_INSENSITIVE`/`SENSITIVE` |
| `SQLException: ResultSet not updatable` | Calling `updateRow()`/`insertRow()` on `CONCUR_READ_ONLY`, or on a non-updatable query (e.g., joins without keys) | Set `CONCUR_UPDATABLE`; ensure query is simple single-table with primary key |
| `SQLFeatureNotSupportedException` | Driver doesn't support requested type/concurrency combo | Check driver docs; fall back to `TYPE_FORWARD_ONLY` |
| `NullPointerException` after `getXxx()` on primitive | Column was SQL `NULL`, retrieved with primitive getter (e.g., `getInt`) returning `0` silently, then misused | Always check `wasNull()` after primitive getters when NULL is possible |

---

## Important Notes

1. **Column indices are 1-based, not 0-based** — `rs.getString(1)` retrieves the *first* column. Using index `0` throws `SQLException: Invalid column index`.

2. **Type and concurrency are set on the `Statement`, not the `ResultSet`** — there is no `ResultSet` constructor; its behavior is entirely inherited from how the parent `Statement`/`PreparedStatement` was created.

3. **`wasNull()` must be called immediately after a `getXxx()`** — it reflects only the *last* retrieval. This matters most for primitive getters (`getInt`, `getDouble`) which silently return `0`/`false` for SQL `NULL` instead of throwing.

4. **`TYPE_SCROLL_INSENSITIVE` is a snapshot, not live** — despite being scrollable, it does not reflect concurrent updates made by other transactions after the query executed. Only `TYPE_SCROLL_SENSITIVE` attempts to reflect live changes (driver support varies).

5. **Updatable ResultSets have restrictions** — typically require: a single table (no joins), the primary key column included in the `SELECT`, and driver-level support. Complex queries usually fall back to `CONCUR_READ_ONLY` even if requested as updatable.

6. **`getRow()` returns `0`** when the cursor is not positioned on a valid row (i.e., before first or after last) — useful as a guard check before accessing data.

7. **Default ResultSet is forward-only and read-only** — if no type/concurrency is specified during `createStatement()`/`prepareStatement()`, JDBC silently uses `TYPE_FORWARD_ONLY` + `CONCUR_READ_ONLY`. Any scrollable/updatable need must be explicitly requested upfront.

8. **`ResultSet` closes automatically** when its parent `Statement` is closed, re-executed, or the `Connection` is closed — holding a reference to a stale `ResultSet` afterward and calling methods on it throws `SQLException`.

9. **Label vs index access trade-off** — `getString("name")` is more readable and resilient to column-order changes in the query, but involves a name-to-index lookup internally; `getString(1)` is marginally faster for performance-critical loops over large result sets.