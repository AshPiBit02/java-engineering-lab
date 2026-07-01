# Hibernate

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Key Characteristics](#2-key-characteristics)
- [3. Hibernate Architecture](#3-hibernate-architecture)
- [4. Core Interfaces and Classes](#4-core-interfaces-and-classes)
- [5. Working Sequence — Hibernate Save/Fetch Flow](#5-working-sequence--hibernate-savefetch-flow)
- [6. Entity Mapping — Annotations](#6-entity-mapping--annotations)
- [7. Code Example — Full Hibernate Workflow](#7-code-example--full-hibernate-workflow)
- [8. HQL vs SQL](#8-hql-vs-sql)
- [9. Caching in Hibernate](#9-caching-in-hibernate)
- [10. C++ vs Java — Persistence Frameworks](#10-c-vs-java--persistence-frameworks)
- [11. Common Exceptions](#11-common-exceptions)
- [12. Important Notes](#12-important-notes)
- [13. Summary](#13-summary)

---

## 1. Introduction

**Hibernate** is the most widely used **ORM framework** in Java and the most common implementation of the **JPA (Java Persistence API)** specification introduced in 7.1. It handles the translation between Java objects and relational database rows, generating SQL automatically based on annotated entity classes.

```
Fig 1.1 — Hibernate's Place in the Stack
┌──────────────────────────┐
│      Application Code    │
├──────────────────────────┤
│   JPA (specification)    │  defines the contract
├──────────────────────────┤
│Hibernate (implementation)│  does the actual work
├──────────────────────────┤
│        JDBC Driver       │  talks to the database
├──────────────────────────┤
│         Database         │
└──────────────────────────┘
```

---

## 2. Key Characteristics

| Characteristic | Description |
|-----------------|-------------|
| JPA implementation | Implements the JPA specification, but also offers Hibernate-native APIs |
| HQL support | Query using Hibernate Query Language — object-oriented, not table-oriented |
| Automatic SQL generation | Generates database-specific SQL via configurable "dialects" |
| Caching | First-level (session) cache always on; second-level (SessionFactory) cache optional |
| Lazy/eager loading | Fine-grained control over when related entities are fetched |
| Transaction integration | Works with JDBC transactions or JTA in enterprise environments |
| Dirty checking | Automatically detects changes to managed entities and updates the DB on flush |

---

## 3. Hibernate Architecture

```
Fig 3.1 — Hibernate Architecture Diagram

┌────────────────────────────────────────────────────────┐
│                     Application Layer                  │
└───────────────────────────┬────────────────────────────┘
                             │
                             ▼
┌────────────────────────────────────────────────────────┐
│               Configuration (hibernate.cfg.xml /       │
│                application.properties)                 │
│   → DB connection info, dialect, mapped entity classes │
└───────────────────────────┬────────────────────────────┘
                             │ builds
                             ▼
┌────────────────────────────────────────────────────────┐
│                    SessionFactory                      │
│         (heavyweight, created ONCE per application —   │
│          classic Singleton pattern, see 7.6)           │
└───────────────────────────┬────────────────────────────┘
                             │ creates (lightweight, per request/unit of work)
                             ▼
┌────────────────────────────────────────────────────────┐
│                       Session                          │
│   → first-level cache, tracks entity state, issues SQL │
└───────────────────────────┬────────────────────────────┘
                             │ wraps operations in
                             ▼
┌────────────────────────────────────────────────────────┐
│                     Transaction                        │
│         → commit() / rollback() boundary               │
└───────────────────────────┬────────────────────────────┘
                             │
                             ▼
                          Database
```

---

## 4. Core Interfaces and Classes

| Interface/Class | Role |
|-------------------|------|
| `Configuration` | Reads settings (DB URL, dialect, mappings) and builds a `SessionFactory` |
| `SessionFactory` | Thread-safe, immutable, expensive to create — one per application |
| `Session` | Not thread-safe — one per unit of work (e.g., one per request); represents a connection + first-level cache |
| `Transaction` | Manages atomic commit/rollback boundaries |
| `Query` / `TypedQuery` | Represents an HQL or criteria query |
| `Criteria` (legacy) / `CriteriaBuilder` (JPA) | Programmatic, type-safe query construction |

---

## 5. Working Sequence — Hibernate Save/Fetch Flow

```
Fig 5.1 — Save Operation Sequence

Application     SessionFactory     Session      Transaction     Database
     │                 │               │              │              │
     │──build once────►│               │              │              │
     │                 │──openSession─►│              │              │
     │──beginTransaction()───────────────────────────►│              │
     │──session.save(obj)─────────────►│              │              │
     │                 │               │──queue INSERT│              │
     │──transaction.commit()─────────────────────────►│──flush SQL──►│
     │                 │               │              │◄───success───│
     │──session.close()───────────────►│              │              │

Fig 5.2 — Fetch Operation Sequence

Application     Session          Database
     │              │                 │
     │──session.get(Student.class,id)►│
     │              │──check 1st-level│
     │              │   cache first   │
     │              │──(miss) SELECT─►│
     │              │◄───ResultSet────│
     │◄──Student object────│          │
```

---

## 6. Entity Mapping — Annotations

| Annotation | Purpose |
|------------|---------|
| `@Entity` | Marks a class as a mapped Hibernate/JPA entity |
| `@Table(name="...")` | Specifies the target table name |
| `@Id` | Marks the primary key field |
| `@GeneratedValue` | Specifies primary key generation strategy (IDENTITY, SEQUENCE, AUTO) |
| `@Column(name="...")` | Maps a field to a specific column name |
| `@OneToMany` / `@ManyToOne` | Declares one-to-many / many-to-one relationships |
| `@ManyToMany` | Declares many-to-many relationships via a join table |
| `@JoinColumn` | Specifies the foreign key column for a relationship |

```
Fig 6.1 — Relationship Mapping Example
class Student {                    class Course {
    @OneToMany                          @ManyToOne
    List<Course> courses;               Student student;
}                                   }

STUDENT table          COURSE table
+----+-------+         +----+-------+------------+
| id | name  |         | id | title | student_id | ◄── foreign key
+----+-------+         +----+-------+------------+
```

---

## 7. Code Example — Full Hibernate Workflow

```java
// ---------- Entity class ----------
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment PK
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dept")
    private String dept;

    // constructors, getters, setters omitted for brevity
}

// ---------- Building the SessionFactory (once, at startup) ----------
Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
SessionFactory sessionFactory = cfg.buildSessionFactory();  // expensive, do once

// ---------- Saving an entity ----------
try (Session session = sessionFactory.openSession()) {
    Transaction tx = session.beginTransaction();

    Student s = new Student();
    s.setName("Aasciii");
    s.setDept("Computer Engineering");

    session.save(s);        // Hibernate generates the INSERT SQL
    tx.commit();             // flush changes to the database
} catch (Exception e) {
    // rollback on failure in real code
}

// ---------- Fetching an entity ----------
try (Session session = sessionFactory.openSession()) {
    Student s = session.get(Student.class, 1);   // SELECT by primary key
    System.out.println(s.getName());
}

// ---------- HQL query example ----------
try (Session session = sessionFactory.openSession()) {
    // HQL queries the ENTITY and its FIELDS, not the table/columns
    Query<Student> query = session.createQuery(
        "FROM Student WHERE dept = :deptName", Student.class);
    query.setParameter("deptName", "Computer Engineering");
    List<Student> results = query.list();
}
```

---

## 8. HQL vs SQL

| Aspect | HQL (Hibernate Query Language) | SQL |
|--------|----------------------------------|-----|
| Operates on | Entity classes and their fields | Tables and columns |
| Case sensitivity | Entity/field names are case-sensitive | Table/column names typically case-insensitive |
| Portability | Database-independent — Hibernate translates to the right dialect | Tied to a specific database's SQL dialect |
| Joins | Expressed via object relationships (`student.courses`) | Expressed via explicit `JOIN ... ON` clauses |
| Example | `FROM Student WHERE dept = :dept` | `SELECT * FROM student WHERE dept = ?` |

---

## 9. Caching in Hibernate

```
Fig 9.1 — Two-Level Cache Architecture
┌─────────────────────────────────────────────────┐
│  First-Level Cache (Session scope)              │
│  → Always enabled, cannot be disabled           │
│  → Cleared when the Session closes              │
├─────────────────────────────────────────────────┤
│  Second-Level Cache (SessionFactory scope)      │
│  → Optional, shared across all Sessions         │
│  → Requires a provider (e.g., Ehcache)          │
└─────────────────────────────────────────────────┘
```

---

## 10. C++ vs Java — Persistence Frameworks

| Aspect | C++ | Java (Hibernate) |
|--------|-----|--------------------|
| Reflection-based mapping | Not natively supported | Built on Java reflection + annotations |
| Standardized specification | No equivalent standard | JPA provides a vendor-neutral contract |
| Object caching | Manual, application-specific | Built-in first/second-level caching |
| Query abstraction | Raw SQL or ORM libraries like ODB | HQL/JPQL object-oriented queries |

---

## 11. Common Exceptions

| Exception | Cause |
|-----------|-------|
| `LazyInitializationException` | Accessing a lazy-loaded association after the `Session` is closed |
| `NonUniqueObjectException` | Two different objects with the same identifier attached to one Session |
| `StaleObjectStateException` | Optimistic locking conflict — entity was modified by another transaction |
| `HibernateException` | General wrapper for Hibernate-specific runtime errors |
| `ConstraintViolationException` | Database-level constraint violated (unique/foreign key) during flush |

---

## 12. Important Notes

- `SessionFactory` is expensive to build — create it **once** (Singleton pattern, formalized in 7.6) and reuse it across the application.
- `Session` is **not thread-safe** — never share one `Session` across multiple threads/requests.
- Always close a `Session` (or use try-with-resources) to release the first-level cache and database resources.
- Prefer HQL/Criteria API over native SQL when possible, to keep queries database-independent.
- Hibernate performs **dirty checking** automatically — modifying a managed entity's fields is enough to trigger an UPDATE on commit, without calling `save()` again.

---

## 13. Summary

```
Fig 13.1 — Hibernate Recap
┌─────────────────────────────────────────────────────────────┐
│  HIBERNATE                                                  │
│                                                             │
│  Configuration → SessionFactory (once) → Session (per unit  │
│  of work) → Transaction → Database                          │
│                                                             │
│  Query via HQL (object-oriented) instead of raw SQL         │
│  Automatic dirty checking, caching, and dialect translation │
└─────────────────────────────────────────────────────────────┘
```

| Concept | Key Takeaway |
|---------|---------------|
| SessionFactory | Heavyweight, thread-safe, build once |
| Session | Lightweight, not thread-safe, one per request/unit of work |
| HQL | Queries entities/fields, not tables/columns — database-independent |
| Dirty checking | Changes to managed entities auto-sync on transaction commit |
| Caching | First-level always on (per Session); second-level optional (shared) |