# Web Framework Introduction

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Why Web Frameworks? — From Raw Servlets to Frameworks](#2-why-web-frameworks--from-raw-servlets-to-frameworks)
- [3. Key Characteristics](#3-key-characteristics)
- [4. The MVC Pattern](#4-the-mvc-pattern)
- [5. Working Sequence — Request Flow Through a Framework](#5-working-sequence--request-flow-through-a-framework)
- [6. Code Example — Raw Servlet vs Framework Style](#6-code-example--raw-servlet-vs-framework-style)
- [7. Common Java Web Frameworks](#7-common-java-web-frameworks)
- [8. Common Exceptions / Issues](#8-common-exceptions--issues)
- [9. Important Notes](#9-important-notes)
- [10. Summary](#10-summary)

---

## 1. Introduction

A **web framework** is a software layer built on top of the raw Servlet API that provides ready-made structure for routing, request handling, view rendering, and application configuration — removing the repetitive boilerplate seen throughout Phase 6.

```
Fig 1.1 — Framework's Place in the Stack
┌─────────────────────────┐
│      Application Code   │
├─────────────────────────┤
│   Web Framework (Spring,│  routing, DI, MVC structure
│   Struts, JSF, etc.)    │
├─────────────────────────┤
│      Servlet API        │  raw request/response handling
├─────────────────────────┤
│   Servlet Container     │  (Tomcat, Jetty)
└─────────────────────────┘
```

---

## 2. Why Web Frameworks? — From Raw Servlets to Frameworks

```
Fig 2.1 — Problems Frameworks Solve
┌───────────────────────────────────────────────────────────┐
│ 1. URL mapping boilerplate                                │
│   Every servlet needed manual @WebServlet or web.xml entry│
├───────────────────────────────────────────────────────────┤
│ 2. No built-in structure                                  │
│    Business logic, view rendering, and request handling   │
│    often ended up mixed in the same servlet class         │
├───────────────────────────────────────────────────────────┤
│ 3. Manual object wiring                                   │
│    Every dependency (DB connection, service class) had to │
│    be constructed manually with `new`                     │
├───────────────────────────────────────────────────────────┤
│ 4. Repetitive cross-cutting logic                         │
│    Logging, validation, security checks duplicated across │
│    many servlets                                          │
└───────────────────────────────────────────────────────────┘
```

Frameworks emerged to enforce a consistent **architecture** (typically MVC), automate wiring of components, and let developers focus on business logic instead of plumbing.

---

## 3. Key Characteristics

| Characteristic | Description |
|-----------------|-------------|
| Routing | Maps URLs to handler methods declaratively (annotations or config), not manual servlet mapping |
| MVC structure | Enforces separation of Model, View, and Controller responsibilities |
| Dependency Injection | Framework constructs and wires objects automatically |
| Convention over configuration | Sensible defaults reduce the amount of explicit setup needed |
| Cross-cutting concerns | Centralized handling of logging, security, validation via interceptors/filters |
| Templating/view support | Built-in support for view technologies (JSP, Thymeleaf, etc.) |

---

## 4. The MVC Pattern

```
Fig 4.1 — MVC Flow
┌───────────┐      updates       ┌───────────┐
│   Model   │◄────────────────── │ Controller│
│ (data +   │                    │ (handles  │
│  business │──────notifies────► │  request, │
│  logic)   │                    │  updates  │
└───────────┘                    │  model)   │
      │                          └─────┬─────┘
      │ renders data into              │ selects
      ▼                                ▼
┌───────────────────────────────────────────┐
│                    View                   │
│         (HTML/JSP shown to the user)      │
└───────────────────────────────────────────┘
```

| Component | Responsibility |
|-----------|------------------|
| Model | Represents data and business logic (often backed by ORM entities from 7.1/7.2) |
| View | Renders the response (HTML, JSON) shown to the client |
| Controller | Receives the request, invokes business logic, chooses the view |

---

## 5. Working Sequence — Request Flow Through a Framework

```
Fig 5.1 — Framework Request Sequence

Client        Front Controller       Controller       Model/Service
  │                  │                    │                  │
  │──GET /students──►│  single entry      │                  │
  │                  │  point for ALL     │                  │
  │                  │  requests          │                  │
  │                  │──route by URL─────►│                  │
  │                  │                    │──fetch data─────►│
  │                  │                    │◄──data───────────│
  │                  │◄──select view──────│                  │
  │◄────rendered HTML/JSON────────────────│                  │
```

The **Front Controller pattern** is central to nearly every web framework: instead of many independent servlets, a single dispatcher receives every request and routes it internally.

---

## 6. Code Example — Raw Servlet vs Framework Style

```java
// ---------- BEFORE: Raw Servlet (Phase 6 style) ----------
@WebServlet("/students")                          // manual URL mapping
public class StudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // manually fetch data, manually build response
        List<Student> students = studentDao.findAll();
        req.setAttribute("students", students);
        req.getRequestDispatcher("/students.jsp").forward(req, resp);
    }
}

// ---------- AFTER: Framework style (conceptual, Spring MVC-like) ----------
@Controller                          // marks this class as a request handler
@RequestMapping("/students")         // base URL mapping, no web.xml needed
public class StudentController {

    @Autowired                        // dependency injected automatically
    private StudentService studentService;

    @GetMapping                       // maps GET /students
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students";            // logical view name, framework resolves it
    }
}
```

---

## 7. Common Java Web Frameworks

| Framework | Notes |
|------------|-------|
| Spring MVC | Most widely used; annotation-driven, full MVC support; foundation for Spring Boot (7.4) |
| Struts | Older, XML-config-heavy MVC framework, largely legacy today |
| JSF (JavaServer Faces) | Component-based UI framework, tightly integrated with Java EE |
| Play Framework | Reactive, lightweight, convention-over-configuration |
| Micronaut / Quarkus | Modern, cloud-native, fast-startup frameworks for microservices |

---

## 8. Common Exceptions / Issues

| Exception / Issue | Cause |
|---------------------|-------|
| `NoHandlerFoundException` | No controller method matches the requested URL |
| `HttpRequestMethodNotSupportedException` | URL matched, but not for the HTTP method used (e.g., POST on a GET-only route) |
| Circular dependency errors | Two or more beans depend on each other during dependency injection |
| `404`/`500` from misconfigured routing | Missing or conflicting URL mappings across controllers |

---

## 9. Important Notes

- Frameworks sit **on top of** the Servlet API — they don't replace it; Spring MVC's `DispatcherServlet`, for example, is itself an `HttpServlet`.
- Learning raw Servlets first (Phase 6) makes framework "magic" much easier to understand, since frameworks automate exactly those steps.
- Dependency Injection is a core concept enabling frameworks to wire controllers, services, and repositories without manual `new` calls.
- Most modern Java web development uses **Spring Boot** (7.4) rather than configuring Spring MVC manually — but Spring Boot is built directly on these same MVC concepts.

---

## 10. Summary

```
Fig 10.1 — Web Framework Recap
┌──────────────────────────────────────────────────────────┐
│  WEB FRAMEWORK                                           │
│                                                          │
│  Problem: Manual servlet mapping, mixed logic, no DI,    │
│           repeated cross-cutting code                    │
│                                                          │
│  Solution: Front Controller + MVC structure + Dependency │
│            Injection + declarative routing               │
│                                                          │
│  Result: Clear separation of concerns, less boilerplate, │
│          consistent architecture across the app          │
└──────────────────────────────────────────────────────────┘
```

| Concept | Key Takeaway |
|---------|---------------|
| Front Controller | Single entry point routes all requests internally |
| MVC | Separates data (Model), display (View), and request handling (Controller) |
| Dependency Injection | Framework wires objects automatically instead of manual `new` |
| Built on Servlets | Frameworks are a layer over the Servlet API, not a replacement for it |
| Next step | Spring Boot (7.4) automates framework setup even further |