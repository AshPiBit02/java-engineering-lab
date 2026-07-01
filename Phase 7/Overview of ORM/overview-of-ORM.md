#  Overview of ORM (Object-Relational Mapping)

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Why ORM Came to Life](#2-why-orm-came-to-life)
- [3. Key Characteristics](#3-key-characteristics)
- [4. The Object-Relational Impedance Mismatch](#4-the-object-relational-impedance-mismatch)
- [5. Core ORM Concepts and Mapping](#5-core-orm-concepts-and-mapping)
- [6. Working Sequence — How ORM Processes a Request](#6-working-sequence--how-orm-processes-a-request)
- [7. Code Example — Manual JDBC vs ORM Style](#7-code-example--manual-jdbc-vs-orm-style)
- [8. Common ORM Frameworks in Java](#8-common-orm-frameworks-in-java)
- [9. C++ vs Java — Persistence Approach](#9-c-vs-java--persistence-approach)
- [10. Common Exceptions](#10-common-exceptions)
- [11. Important Notes](#11-important-notes)
- [12. Summary](#12-summary)

---

## 1. Introduction

**ORM (Object-Relational Mapping)** is a programming technique that maps **Java objects directly to relational database tables**, letting developers work with objects instead of writing raw SQL. An ORM tool handles the translation between the object-oriented world (classes, objects, references) and the relational world (tables, rows, foreign keys) automatically.

```
Fig 1.1 — ORM in One Picture
┌─────────────────┐        ORM Layer        ┌─────────────────┐
│   Java Objects  │   ◄──────────────────►  │  Database Tables│
│  (Student obj)  │   maps fields to        │  (STUDENT table)│
│  id, name, dept │   columns automatically │  id, name, dept │
└─────────────────┘                         └─────────────────┘
```

---

## 2. Why ORM Came to Life

Before ORM existed, all persistence in Java was done through **raw JDBC**, as seen in Phase 6 (6.7 — Database Connectivity through Servlets). This approach worked, but caused real, recurring problems in large applications:

```
Fig 2.1 — Problems That Led to ORM
┌───────────────────────────────────────────────────────────┐
│ 1. Repetitive boilerplate                                 │
│    Every table needed near-identical Connection→Statement │
│    → ResultSet → manual field mapping code                │
├───────────────────────────────────────────────────────────┤
│ 2. Manual object mapping                                  │
│    Developers hand-wrote rs.getString("name") style code  │
│    for every single column, every single query            │
├───────────────────────────────────────────────────────────┤
│ 3. SQL scattered across the codebase                      │
│    Raw SQL strings embedded in Java logic → hard to       │
│    maintain, hard to refactor, easy to break              │
├───────────────────────────────────────────────────────────┤
│ 4. Database vendor lock-in                                │
│    SQL syntax differences (MySQL vs Oracle vs PostgreSQL) │
│    forced code changes when switching databases           │
├───────────────────────────────────────────────────────────┤
│ 5. No object-graph awareness                              │
│    JDBC has no concept of relationships (a Student "has"  │
│    Courses) — developers manually wired foreign keys      │
├───────────────────────────────────────────────────────────┤
│ 6. Difficult caching and performance tuning               │
│    Every optimization (caching, lazy loading) had to be   │
│    built manually, from scratch, per project              │
└───────────────────────────────────────────────────────────┘
```

ORM tools (like Hibernate) emerged to **automate this repetitive translation layer**, letting developers describe *what* data they want (as objects) rather than *how* to fetch it (as SQL) — while still allowing raw SQL/HQL when fine control is needed.

```
Fig 2.2 — Before vs After ORM
BEFORE (Raw JDBC)                      AFTER (ORM)
─────────────────                      ───────────
Write SQL manually            ──►      Annotate a class as an entity
Map ResultSet to object by hand ──►    Object returned automatically
Handle relationships manually  ──►     Relationships declared via annotations
Rewrite SQL per database        ──►    ORM generates dialect-specific SQL
```

---

## 3. Key Characteristics

| Characteristic | Description |
|-----------------|-------------|
| Automatic mapping | Class fields map to table columns without manual code |
| Database independence | Same Java code can target different databases (MySQL, PostgreSQL, Oracle) via configuration |
| Relationship management | One-to-one, one-to-many, many-to-many relationships expressed as object references |
| Lazy/eager loading | Related data can be fetched on-demand or immediately, configurable |
| Caching | First-level and second-level caches reduce redundant database hits |
| Transaction management | Integrates with transaction boundaries (commit/rollback) |
| Query abstraction | Object-oriented query languages (e.g., HQL, JPQL) instead of raw SQL |

---

## 4. The Object-Relational Impedance Mismatch

ORM exists specifically to solve this mismatch between how Java models data and how relational databases store it.

```
Fig 4.1 — Impedance Mismatch
┌─────────────────────────┐         ┌─────────────────────────┐
│      OBJECT MODEL       │         │     RELATIONAL MODEL    │
│                         │         │                         │
│  Inheritance            │  ◄─X─►  │  No native inheritance  │
│  Objects reference      │  ◄─X─►  │  Foreign keys only      │
│  each other directly    │         │                         │
│  Identity = object      │  ◄─X─►  │  Identity = primary key │
│  reference              │         │                         │
│  Encapsulation (private │  ◄─X─►  │  Flat rows and columns  │
│  fields, methods)       │         │                         │
└─────────────────────────┘         └─────────────────────────┘
                     ORM bridges this gap
```

---

## 5. Core ORM Concepts and Mapping

| ORM Concept | Relational Equivalent |
|--------------|------------------------|
| Class | Table |
| Object / Instance | Row |
| Field / Attribute | Column |
| Object reference | Foreign key |
| Object identity (primary key field) | Primary key column |
| Collection (List, Set) of objects | One-to-many relationship |

```
Fig 5.1 — Field-to-Column Mapping Example
class Student {                 Table: STUDENT
    int id;          ──────►    +----+-------+------+
    String name;      ──────►   | id | name  | dept |
    String dept;      ──────►   +----+-------+------+
}                               | 1  | Aasii | CS   |
                                +----+-------+------+
```

---

## 6. Working Sequence — How ORM Processes a Request

```
Fig 6.1 — ORM Save Operation Sequence

Application         ORM Engine            Database
     │                    │                    │
     │──save(studentObj)─►│                    │
     │                    │──generate INSERT──►│
     │                    │   SQL from mapping │
     │                    │◄────success────────│
     │◄──return saved obj─│                    │

Fig 6.2 — ORM Fetch Operation Sequence

Application            ORM Engine            Database
     │                     │                    │
     │──find(Student.class,│                    │
     │        id=1)───────►│                    │
     │                     │──generate SELECT──►│
     │                     │◄────ResultSet──────│
     │                     │──map row to object─│
     │◄──Student object────│                    │
```

---

## 7. Code Example — Manual JDBC vs ORM Style

```java
// ---------- BEFORE: Raw JDBC (Phase 6 style) ----------
String sql = "SELECT id, name, dept FROM student WHERE id = ?";
try (Connection conn = dataSource.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql)) {

    stmt.setInt(1, studentId);
    try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            Student s = new Student();
            s.setId(rs.getInt("id"));         // manual field mapping
            s.setName(rs.getString("name"));  // repeated for every column
            s.setDept(rs.getString("dept"));
        }
    }
}

// ---------- AFTER: ORM style (conceptual, framework-agnostic) ----------
@Entity                       // marks this class as a mapped entity
@Table(name = "student")      // maps to the "student" table
public class Student {

    @Id                        // marks primary key
    private int id;

    private String name;       // auto-mapped to "name" column
    private String dept;       // auto-mapped to "dept" column

    // getters and setters
}

// Fetching becomes a single line — no manual SQL or mapping
Student s = entityManager.find(Student.class, studentId);
```

---

## 8. Common ORM Frameworks in Java

| Framework | Notes |
|------------|-------|
| Hibernate | Most widely used Java ORM; covered in detail in 7.2 |
| JPA (Java Persistence API) | A specification (not implementation); Hibernate is a JPA provider |
| EclipseLink | Reference implementation of JPA |
| MyBatis | Semi-ORM — maps SQL results to objects but keeps SQL explicit |
| Spring Data JPA | Adds repository abstraction on top of JPA/Hibernate |

---

## 9. C++ vs Java — Persistence Approach

| Aspect | C++ | Java |
|--------|-----|------|
| Native ORM support | None in standard library; typically raw SQL or third-party libs (e.g., ODB) | Rich ORM ecosystem (Hibernate, JPA) |
| Reflection support | Limited/manual (no built-in reflection) | Full reflection API enables annotation-driven mapping |
| Typical approach | Manual struct-to-row mapping, hand-written SQL | Annotation-based entity mapping via ORM frameworks |
| Ecosystem maturity | Fragmented, fewer standardized tools | Mature, standardized via JPA specification |

---

## 10. Common Exceptions

| Exception | Cause |
|-----------|-------|
| `MappingException` | Entity class not properly annotated/mapped, or mapping file errors |
| `LazyInitializationException` | Accessing a lazily-loaded relationship after the session/context is closed |
| `NonUniqueResultException` | A query expected to return one result returned multiple |
| `ConstraintViolationException` | Database constraint (unique, not-null, foreign key) violated during save |
| `OptimisticLockException` | Concurrent update conflict detected via versioning |

---

## 11. Important Notes

- ORM does **not** eliminate the need to understand SQL — it abstracts routine cases, but complex queries and performance tuning still require SQL/database knowledge.
- The **N+1 query problem** (fetching a list, then issuing one extra query per item for related data) is one of the most common ORM performance pitfalls.
- ORM is a *layer*, not a replacement for the database — the relational database still enforces integrity, constraints, and transactions.
- JPA is a **specification**; Hibernate is the most common **implementation** of that specification.

---

## 12. Summary

```
Fig 12.1 — ORM Recap
┌───────────────────────────────────────────────────────────┐
│  ORM (OBJECT-RELATIONAL MAPPING)                          │
│                                                           │
│  Problem: JDBC boilerplate, manual mapping, SQL scattered │
│           throughout code, vendor lock-in                 │
│                                                           │
│  Solution: Map classes to tables, objects to rows,        │
│            fields to columns — automatically              │
│                                                           │
│  Result: Less boilerplate, database independence,         │
│          relationship-aware persistence                   │
└───────────────────────────────────────────────────────────┘
```

| Concept | Key Takeaway |
|---------|---------------|
| ORM's purpose | Bridge the object-relational impedance mismatch |
| Origin | Born from JDBC boilerplate, mapping pain, and vendor lock-in |
| Mapping | Class ↔ Table, Object ↔ Row, Field ↔ Column |
| Java ecosystem | JPA (spec) + Hibernate (implementation) is the dominant pairing |
| Trade-off | Less boilerplate, but requires care around lazy loading and query efficiency |