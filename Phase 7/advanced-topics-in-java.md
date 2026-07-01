# Advanced Topics in Java (Unit 7)

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Why These Topics? — From Servlets to Modern Java](#2-why-these-topics--from-servlets-to-modern-java)
- [3. Phase 7 Architecture Map](#3-phase-7-architecture-map)
- [4. Topic Breakdown](#4-topic-breakdown)
- [5. How the Topics Connect in a Real Application](#5-how-the-topics-connect-in-a-real-application)
- [6. ORM/Spring Stack vs Raw Servlet+JDBC (Phase 6)](#6-ormspring-stack-vs-raw-servletjdbc-phase-6)
- [7. Common Pitfalls Across the Phase](#7-common-pitfalls-across-the-phase)
- [8. Summary](#8-summary)

---

## 1. Introduction

Phase 7 moves beyond the raw Servlet/JDBC model of Phase 6 into the tools and patterns that power **modern, production-grade Java applications**: object-relational mapping, the Spring ecosystem, concurrent programming, and classic design patterns used throughout enterprise Java codebases.

```
Fig 1.1 — Phase 7 Scope
┌─────────────────────────────────────────────────────────┐
│              PHASE 7: ADVANCED JAVA TOPICS              │
│                                                         │
│  Persistence Layer   Framework Layer    Runtime & Design│
│   (7.1, 7.2)            (7.3, 7.4)        (7.5, 7.6)    │
└─────────────────────────────────────────────────────────┘
```

---

## 2. Why These Topics? — From Servlets to Modern Java

```
Fig 2.1 — Progression from Phase 6 to Phase 7
Raw JDBC (Phase 6, 6.7)
   │  manual SQL, manual ResultSet mapping
   ▼
ORM / Hibernate (7.1, 7.2)
   │  objects mapped directly to tables, no manual SQL
   ▼
Web Framework / Spring Boot (7.3, 7.4)
   │  auto-configuration, dependency injection, replaces
   │  raw Servlets + web.xml boilerplate
   ▼
Concurrency (7.5)
   │  handling multiple simultaneous requests safely
   ▼
Design Patterns (7.6)
   used throughout ORM, Spring, and concurrent code itself
```

Where Phase 6 taught the *mechanics* of a Java web request, Phase 7 teaches the *abstractions* that remove that boilerplate in real-world development.

---

## 3. Phase 7 Architecture Map

```
Fig 3.1 — How a Modern Spring/Hibernate App Is Layered

   Client
     │  HTTP Request
     ▼
┌───────────────────────────┐
│   Spring Boot / Web       │  auto-configured server,
│   Framework Layer         │  routing via annotations   [7.3, 7.4]
└─────────────┬─────────────┘
              │ delegates to
              ▼
┌────────────────────────────┐
│   Service Layer            │  business logic, may spawn
│   (uses Concurrency)       │  threads/tasks for parallel work [7.5]
└─────────────┬──────────────┘
              │ calls
              ▼
┌────────────────────────────┐
│   ORM / Hibernate Layer    │  entities mapped to tables,
│   (7.1, 7.2)               │  no manual SQL
└─────────────┬──────────────┘
              │
              ▼
          Database

   (Design Patterns [7.6] — Singleton, Factory, Abstract
    Factory — appear across every layer above: Hibernate's
    SessionFactory, Spring's Bean container, and thread-safe
    singletons in concurrent code.)
```

---

## 4. Topic Breakdown

| # | Topic | Core Focus | Depends On |
|---|-------|-----------|------------|
| 7.1 | Overview of ORM | Object-relational mapping concept, entity-table mapping | Phase 6 (JDBC) |
| 7.2 | Hibernate | Hibernate architecture, SessionFactory, HQL, annotations | 7.1 |
| 7.3 | Web Framework Introduction | Role of frameworks, MVC pattern, why frameworks replace raw Servlets | Phase 6 (Servlets) |
| 7.4 | Basics of Spring Boot | Auto-configuration, dependency injection, REST controllers, starters | 7.3 |
| 7.5 | Concurrency and Multithreading in Java | Thread lifecycle, synchronization, executor services, thread safety | Core Java |
| 7.6 | Design Patterns — Singleton, Factory, Abstract Factory | Creational design patterns, use in frameworks | Core Java |

---

## 5. How the Topics Connect in a Real Application

```
Fig 5.1 — End-to-End Sequence (Spring Boot + Hibernate Example)

Client        Spring Boot         Service Layer      Hibernate        Database
  │                │                    │                │               │
  │──GET /users───►│  [7.3, 7.4]        │                │               │
  │                │──route to bean────►│                │               │
  │                │                    │──submit task──►│  [7.5]        │
  │                │                    │   to executor  │  (thread pool)│
  │                │                    │                │──query via───►│
  │                │                    │                │  Session      │
  │                │                    │                │  (SessionFactory
  │                │                    │                │   = Singleton)│ [7.6]
  │                │                    │                │◄──entities────│
  │◄───JSON Response────────────────────│◄───────────────│               │
```

---

## 6. ORM/Spring Stack vs Raw Servlet+JDBC (Phase 6)

| Aspect | Phase 6 (Servlet + JDBC) | Phase 7 (Hibernate + Spring Boot) |
|--------|---------------------------|-------------------------------------|
| Data access | Manual SQL via `PreparedStatement` | Entities auto-mapped by Hibernate (ORM) |
| Boilerplate | High — manual `ResultSet` mapping, connection handling | Low — annotations and auto-configuration |
| Routing | `web.xml` / `@WebServlet` per class | Centralized via Spring MVC controllers |
| Object creation | Manual `new` calls | Dependency Injection (Spring container) |
| Concurrency handling | Left entirely to the developer | Framework-managed thread pools, but still needs 7.5 concepts |
| Extensibility | Tightly coupled, harder to swap components | Loosely coupled via interfaces and patterns (7.6) |

---

## 7. Common Pitfalls Across the Phase

```
Fig 7.1 — Pitfall Map
┌───────────────────────────────────────────────┐
│ 7.1  Treating ORM as "no SQL knowledge        │
│      needed" → inefficient queries (N+1)      │
├───────────────────────────────────────────────┤
│ 7.2  Not closing/managing Hibernate           │
│      Sessions → memory leaks                  │
├───────────────────────────────────────────────┤
│ 7.3  Confusing "framework" with "library"     │
│      → misunderstanding inversion of control  │
├───────────────────────────────────────────────┤
│ 7.4  Overusing auto-configuration without     │
│      understanding what it wires together     │
├───────────────────────────────────────────────┤
│ 7.5  Shared mutable state across threads      │
│      without synchronization → race conditions│
├───────────────────────────────────────────────┤
│ 7.6  Overusing Singleton where a normal       │
│      object would do → hidden global state    │
└───────────────────────────────────────────────┘
```

---

## 8. Summary

```
Fig 8.1 — Phase 7 Recap
┌────────────────────────────────────────────────────────────┐
│  PHASE 7: ADVANCED TOPICS IN JAVA                          │
│                                                            │
│  ORM Concepts  →  Hibernate  →  Web Frameworks             │
│         →  Spring Boot  →  Concurrency  →  Design Patterns │
│                                                            │
│  Outcome: Understanding of the abstractions and patterns   │
│  that power modern, framework-driven Java applications,    │
│  building directly on the raw Servlet/JDBC foundation      │
│  from Phase 6.                                          v  │
└────────────────────────────────────────────────────────────┘
```

| Concept | Key Takeaway |
|---------|---------------|
| ORM | Maps Java objects to database tables, removing manual SQL/ResultSet code |
| Hibernate | Concrete ORM implementation; manages sessions, caching, and HQL queries |
| Web Frameworks | Provide routing, MVC structure, and remove Servlet boilerplate |
| Spring Boot | Auto-configuration + dependency injection on top of a web framework |
| Concurrency | Multiple threads must coordinate safely around shared resources |
| Design Patterns | Singleton, Factory, and Abstract Factory recur throughout ORM and Spring internals |
