# Criteria API

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Why the Criteria API Came to Life](#2-why-the-criteria-api-came-to-life)
- [3. What Problem It Solves](#3-what-problem-it-solves)
- [4. Key Characteristics](#4-key-characteristics)
- [5. Core Components](#5-core-components)
- [6. Essential Methods Reference](#6-essential-methods-reference)
- [7. Working Sequence — How a Criteria Query Executes](#7-working-sequence--how-a-criteria-query-executes)
- [8. Code Example — Building Queries Step by Step](#8-code-example--building-queries-step-by-step)
- [9. Real-World Usage](#9-real-world-usage)
- [10. Named Query](#10-named-query)
- [11. Criteria API vs Named Query vs HQL/JPQL vs Native SQL](#11-criteria-api-vs-named-query-vs-hqljpql-vs-native-sql)
- [12. Common Exceptions](#12-common-exceptions)
- [13. Important Notes](#13-important-notes)
- [14. Summary](#14-summary)

---

## 1. Introduction

The **Criteria API** is a **type-safe, programmatic way to build database queries** in Java, without writing SQL or HQL/JPQL as raw strings. Instead of constructing a query as text, you build it as a tree of Java objects — `CriteriaBuilder`, `CriteriaQuery`, `Root`, `Predicate` — which the JPA provider (Hibernate) translates into SQL at execution time.

```
Fig 1.1 — Criteria API in the Persistence Stack
┌─────────────────────────┐
│      Application Code   │
├─────────────────────────┤
│      Criteria API       │  builds queries as Java objects
├─────────────────────────┤
│   JPA / Hibernate       │  translates to SQL
├─────────────────────────┤
│         Database        │
└─────────────────────────┘
```

---

## 2. Why the Criteria API Came to Life

```
Fig 2.1 — Problems With String-Based Queries (HQL/JPQL)
┌───────────────────────────────────────────────────────────────┐
│ 1. No compile-time checking                                   │
│    "FROM Studnt WHERE dept = :dept" — typo only discovered    │
│    at runtime, not compile time                               │
├───────────────────────────────────────────────────────────────┤
│ 2. Hard to build dynamic queries                              │
│    Conditionally adding WHERE clauses meant ugly string       │
│    concatenation based on if/else logic                       │
├───────────────────────────────────────────────────────────────┤
│ 3. No IDE support                                             │
│    String-based queries get no autocomplete, no refactor      │
│    support, no "find usages" for entity field names           │
├───────────────────────────────────────────────────────────────┤
│ 4. Refactoring risk                                           │
│    Renaming an entity field doesn't update the query string — │
│    breaks silently at runtime                                 │
└───────────────────────────────────────────────────────────────┘
```

The Criteria API was introduced (JPA 2.0) specifically to let developers build queries using **Java code and type-safe field references** instead of fragile strings — catching mistakes at compile time and making dynamic, conditional query building straightforward.

```
Fig 2.2 — Before vs After
BEFORE (HQL string)                       AFTER (Criteria API)
────────────────────                      ─────────────────────
"FROM Student s WHERE s.dept = :d"  ──►   cb.equal(root.get("dept"), dept)
Typos caught only at runtime         ──►   Caught at compile time (with metamodel)
Manual string building for            ──►   Predicates combined programmatically
  conditional filters
```

---

## 3. What Problem It Solves

| Problem (String-Based Queries) | Criteria API Solution |
|----------------------------------|---------------------------|
| No compile-time safety | Field access via `Root`/`Path` objects, validated by the JPA metamodel |
| Difficult dynamic query construction | `Predicate` objects can be conditionally added to a `List<Predicate>` |
| Poor refactoring support | Field renames can be tracked through generated metamodel classes |
| SQL injection risk from manual concatenation | Parameters are bound programmatically, not concatenated as strings |

---

## 4. Key Characteristics

| Characteristic | Description |
|-----------------|-------------|
| Type-safe | Queries built with Java objects/methods, not raw strings |
| Dynamic query building | Easy to add/remove conditions based on runtime logic |
| Metamodel support | Optional generated `_Student` metamodel classes give full compile-time field checking |
| Provider-independent | Part of the JPA specification — works with Hibernate, EclipseLink, etc. |
| Verbose but explicit | More code than HQL for simple queries, but much safer for complex/dynamic ones |

---

## 5. Core Components

| Component | Role |
|------------|------|
| `CriteriaBuilder` | Factory for creating `CriteriaQuery` objects, predicates, and expressions |
| `CriteriaQuery<T>` | Represents the top-level query being built, with a defined result type `T` |
| `Root<T>` | Represents the "FROM" entity — the main table being queried |
| `Predicate` | Represents a condition (equivalent to a WHERE clause condition) |
| `Path<T>` | Represents a reference to an entity's field/attribute |
| `Order` | Represents ORDER BY direction (ascending/descending) |
| `Join<X,Y>` | Represents a join between two entities (like an SQL JOIN) |
| `Expression<T>` | General-purpose representation of a computed or referenced value |

```
Fig 5.1 — Component Relationships
CriteriaBuilder ──creates──► CriteriaQuery<T>
                                   │
                                   │ defines FROM via
                                   ▼
                                 Root<T> ──provides fields via──► Path<T>
                                   │
                        used to build
                                   ▼
                              Predicate (WHERE conditions)
                                   │
                       combined and attached to
                                   ▼
                         CriteriaQuery.where(...)
```

---

## 6. Essential Methods Reference

| Method | Belongs To | Purpose |
|--------|-------------|----------|
| `getCriteriaBuilder()` | `EntityManager` | Obtains the `CriteriaBuilder` instance |
| `createQuery(Class<T>)` | `CriteriaBuilder` | Creates a new `CriteriaQuery` with result type `T` |
| `from(Class<T>)` | `CriteriaQuery` | Defines the root entity (FROM clause) |
| `get(String field)` | `Root`/`Path` | References a specific entity field |
| `equal(x, y)` | `CriteriaBuilder` | Builds an equality predicate (`=`) |
| `notEqual(x, y)` | `CriteriaBuilder` | Builds an inequality predicate (`!=`) |
| `greaterThan(x, y)` / `lessThan(x, y)` | `CriteriaBuilder` | Builds comparison predicates |
| `like(x, pattern)` | `CriteriaBuilder` | Builds a `LIKE` predicate for pattern matching |
| `and(p1, p2, ...)` / `or(p1, p2, ...)` | `CriteriaBuilder` | Combines multiple predicates |
| `where(Predicate...)` | `CriteriaQuery` | Attaches conditions to the query |
| `orderBy(Order...)` | `CriteriaQuery` | Sets result ordering |
| `asc(x)` / `desc(x)` | `CriteriaBuilder` | Builds ascending/descending `Order` objects |
| `join(String field)` | `Root` | Joins a related entity for multi-table queries |
| `createQuery(criteriaQuery)` | `EntityManager` | Compiles the criteria query into an executable `TypedQuery` |
| `getResultList()` | `TypedQuery` | Executes the query and returns results as a list |

---

## 7. Working Sequence — How a Criteria Query Executes

```
Fig 7.1 — Criteria Query Build & Execution Sequence

Application          CriteriaBuilder        CriteriaQuery       Database
     │                       │                     │                 │
     │──getCriteriaBuilder()►│                     │                 │
     │──createQuery(Student) ─────────────────────►│                 │
     │──query.from(Student)───────────────────────►│  defines FROM   │
     │──cb.equal(root.get("dept"), "CS")───────────│  builds WHERE   │
     │──query.where(predicate)────────────────────►│                 │
     │──entityManager.createQuery(query) ───────────────────────────►│
     │                      │                      │──translate to──►│
     │                      │                      │   SQL and run   │
     │◄─────────────────────List<Student>────────────────────────────│
```

---

## 8. Code Example — Building Queries Step by Step

```java
// ---------- Setup ----------
CriteriaBuilder cb = entityManager.getCriteriaBuilder();       // factory for query parts
CriteriaQuery<Student> query = cb.createQuery(Student.class);  // result type: Student
Root<Student> root = query.from(Student.class);                // FROM student

// ---------- Simple equality condition ----------
Predicate deptPredicate = cb.equal(root.get("dept"), "CS");    // dept = 'CS'
query.select(root).where(deptPredicate);

TypedQuery<Student> tq = entityManager.createQuery(query);
List<Student> students = tq.getResultList();                    // executes and maps results


// ---------- Dynamic query building (the real strength of Criteria API) ----------
List<Predicate> predicates = new ArrayList<>();

if (deptFilter != null) {
    predicates.add(cb.equal(root.get("dept"), deptFilter));     // only added if provided
}
if (minSemester != null) {
    predicates.add(cb.greaterThanOrEqualTo(root.get("semester"), minSemester));
}

query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));  // combine all filters
List<Student> filtered = entityManager.createQuery(query).getResultList();


// ---------- Ordering results ----------
query.orderBy(cb.asc(root.get("name")));   // ORDER BY name ASC


// ---------- Join example ----------
Root<Student> studentRoot = query.from(Student.class);
Join<Student, Course> courseJoin = studentRoot.join("courses");  // INNER JOIN courses
query.select(studentRoot).where(cb.equal(courseJoin.get("title"), "Java"));
```

---

## 9. Real-World Usage

```
Fig 9.1 — Where Criteria API Is Actually Used
┌────────────────────────────────────────────────────────────────┐
│ Admin dashboards / search filters                              │
│   → user picks optional filters (dept, semester, name) —       │
│     Criteria API builds the WHERE clause dynamically only      │
│     for filters the user actually selected                     │
├────────────────────────────────────────────────────────────────┤
│ Reporting systems                                              │
│   → complex, conditional queries built based on report         │
│     parameters chosen at runtime                               │
├────────────────────────────────────────────────────────────────┤
│ Multi-tenant applications                                      │
│   → dynamically adding a tenant_id predicate to every query    │
│     without duplicating query strings per tenant               │
├────────────────────────────────────────────────────────────────┤
│ Generic repository/DAO layers                                  │
│   → building reusable, type-safe query-building utility methods│
│     shared across many entity types                            │
└────────────────────────────────────────────────────────────────┘
```

**Typical real-world scenario:** A search API endpoint (`/students/search?dept=CS&minSemester=3`) where any combination of query parameters may or may not be present — Criteria API lets you build the query by conditionally adding predicates, something HQL/JPQL string concatenation handles far more awkwardly.

---

## 10. Named Query

**Definition:** A **Named Query** is a **predefined, static query** (HQL/JPQL or native SQL) given a name and declared once — either via annotation (`@NamedQuery`) on the entity class or in XML mapping — then referenced by name anywhere in the application instead of rewriting the query string each time.

```java
@Entity
@NamedQuery(
    name = "Student.findByDept",                          // reusable identifier
    query = "SELECT s FROM Student s WHERE s.dept = :dept" // JPQL, parsed and validated at startup
)
public class Student {
    // fields, getters, setters
}

// Usage anywhere in the app:
TypedQuery<Student> query = entityManager.createNamedQuery("Student.findByDept", Student.class);
query.setParameter("dept", "CS");
List<Student> results = query.getResultList();
```

**Why it exists / problem it solves:**
- Centralizes query strings on the entity itself instead of scattering the same JPQL string across multiple DAO/service classes.
- Validated and **parsed once at application startup** (not on every call), catching syntax errors early rather than at first execution.
- Named Queries are cached by the persistence provider, giving a minor performance edge over ad-hoc queries built repeatedly.

**Use case:** Fixed, frequently reused queries (e.g., "find active users", "find orders by status") that don't need runtime-conditional logic — the opposite use case from Criteria API's dynamic filtering.

---

## 11. Criteria API vs Named Query vs HQL/JPQL vs Native SQL

| Aspect | Criteria API | Named Query | HQL/JPQL (inline) | Native SQL |
|--------|----------------|--------------|----------------------|--------------|
| Type safety | Yes (especially with metamodel) | No — plain string, but validated at startup | No — plain strings | No — plain strings |
| Dynamic query building | Excellent — predicates added programmatically | Not suited — static by design | Awkward — manual string concatenation | Awkward — manual string concatenation |
| Validated | Compile time (with metamodel) | Application startup | Runtime | Runtime |
| Readability for simple queries | More verbose | Concise, defined once on entity | Concise and readable | Concise, but database-specific |
| Database portability | Fully portable (JPA spec) | Fully portable (JPA spec) | Fully portable (JPA spec) | Tied to a specific SQL dialect |
| Best suited for | Complex, conditional, or dynamically-built queries | Fixed, frequently reused static queries | Simple one-off static queries | Highly optimized or database-specific queries |

---

## 12. Common Exceptions

| Exception | Cause |
|-----------|-------|
| `IllegalArgumentException` | Referencing a field name that doesn't exist on the entity via `root.get("field")` |
| `PersistenceException` | General wrapper for errors during query compilation or execution |
| `IllegalStateException` | Reusing a `CriteriaQuery`/`Root` incorrectly across unrelated queries |
| `QuerySyntaxException` (provider-specific) | Rare with Criteria API, but can surface from malformed joins or subqueries |

---

## 13. Important Notes

- The Criteria API is **verbose by design** — it trades brevity for compile-time safety and dynamic flexibility; use HQL/JPQL for simple, static queries and Criteria API when queries are complex or conditional.
- The **JPA Metamodel** (`Student_` generated classes, via annotation processing) gives full compile-time field-name checking — without it, `root.get("dept")` is still just a string internally.
- Predicates are just objects — they can be built conditionally, stored in a list, and combined with `cb.and()`/`cb.or()` at the end, which is the core reason Criteria API excels at dynamic filters.
- Criteria API is part of the **JPA specification**, so the same code works whether the underlying provider is Hibernate or EclipseLink.

---

## 14. Summary

```
Fig 13.1 — Criteria API Recap
┌─────────────────────────────────────────────────────────────┐
│  CRITERIA API                                               │
│                                                             │
│  Problem: String-based HQL/JPQL queries are fragile,        │
│           hard to build dynamically, and unsafe at compile  │
│           time                                              │
│                                                             │
│  Solution: Build queries as Java objects — CriteriaBuilder, │
│            CriteriaQuery, Root, Predicate                   │
│                                                             │
│  Result: Type-safe, dynamically composable, provider-       │
│          independent queries                                │
└─────────────────────────────────────────────────────────────┘
```

| Concept | Key Takeaway |
|---------|---------------|
| Purpose | Build queries programmatically instead of as raw strings |
| Core components | `CriteriaBuilder`, `CriteriaQuery`, `Root`, `Predicate` |
| Biggest strength | Dynamic, conditional query building without string concatenation |
| Biggest trade-off | More verbose than HQL/JPQL for simple, static queries |
| Best use case | Search/filter APIs, reporting systems, generic DAO layers |
| Named Query | Static, predefined query declared once via `@NamedQuery`, validated at startup, referenced by name — best for fixed, frequently reused queries |