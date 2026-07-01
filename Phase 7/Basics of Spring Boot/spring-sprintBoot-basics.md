# Basics of Spring Boot

## Table of Contents
- [1. Introduction — Spring Framework](#1-introduction--spring-framework)
- [2. Spring Framework — Key Features](#2-spring-framework--key-features)
- [3. Spring Framework — Pros](#3-spring-framework--pros)
- [4. Spring Framework — Cons](#4-spring-framework--cons)
- [5. Introduction — Spring Boot](#5-introduction--spring-boot)
- [6. Spring Boot — Key Features](#6-spring-boot--key-features)
- [7. Spring Boot — How It Eliminates Spring's Cons](#7-spring-boot--how-it-eliminates-springs-cons)
- [8. Spring Boot — Pros](#8-spring-boot--pros)
- [9. Working Sequence — Spring Boot Application Startup](#9-working-sequence--spring-boot-application-startup)
- [10. Code Example — Spring (Manual Config) vs Spring Boot](#10-code-example--spring-manual-config-vs-spring-boot)
- [11. Common Exceptions / Issues](#11-common-exceptions--issues)
- [12. Important Notes](#12-important-notes)
- [13. Summary](#13-summary)

---

## 1. Introduction — Spring Framework

**Spring** is a comprehensive Java application framework built around **Dependency Injection (DI)** and **Inversion of Control (IoC)**. It provides the underlying container that manages objects ("beans"), their lifecycles, and their dependencies, and it forms the foundation for Spring MVC (introduced conceptually in 7.3).

```
Fig 1.1 — Spring Core Concept
┌────────────────────────────────────────────┐
│              Spring IoC Container          │
│                                            │
│   Creates, configures, and wires objects   │
│   ("beans") instead of the developer doing │
│   it manually with `new`                   │
└────────────────────────────────────────────┘
```

---

## 2. Spring Framework — Key Features

| Feature | Description |
|---------|--------------|
| Dependency Injection | Objects receive their dependencies from the container rather than creating them |
| Inversion of Control | The framework controls object creation/flow, not the application code |
| Modular architecture | Separate modules for MVC, Data Access, Security, AOP, etc. — use only what's needed |
| Aspect-Oriented Programming (AOP) | Cross-cutting concerns (logging, transactions) handled declaratively |
| Integration support | Works with JDBC, Hibernate/JPA, JMS, and many other technologies |

---

## 3. Spring Framework — Pros

- Highly modular — pick only the modules a project needs.
- Loose coupling via DI makes code easier to test (mock dependencies easily).
- Mature, battle-tested, huge community and ecosystem.
- Flexible — supports both XML and annotation-based configuration.

---

## 4. Spring Framework — Cons

```
Fig 4.1 — Pain Points of Plain Spring
┌────────────────────────────────────────────────────────────┐
│ 1. Heavy configuration overhead                            │
│    Requires extensive XML or Java config to wire beans,    │
│    data sources, view resolvers, etc.                      │
├────────────────────────────────────────────────────────────┤
│ 2. Manual dependency/version management                    │
│    Developer must pick compatible versions of Spring       │
│    modules and third-party libraries by hand               │
├────────────────────────────────────────────────────────────┤
│ 3. No embedded server                                      │
│    A Spring web app must be manually packaged as a WAR and │
│    deployed to an external servlet container (e.g., Tomcat)│
├────────────────────────────────────────────────────────────┤
│ 4. Steep learning curve for setup                          │
│    New projects require significant boilerplate before any │
│    business logic can even run                             │
└────────────────────────────────────────────────────────────┘
```

---

## 5. Introduction — Spring Boot

**Spring Boot** is built on top of the Spring Framework and is designed specifically to **eliminate the configuration burden** described above. It provides **auto-configuration**, **starter dependencies**, and an **embedded server**, letting a developer go from zero to a running application in minutes.

```
Fig 5.1 — Spring Boot's Place in the Stack
┌─────────────────────────┐
│    Spring Boot          │  auto-config, embedded server, starters
├─────────────────────────┤
│Spring MVC / Spring Data │  web + persistence modules
├─────────────────────────┤
│  Spring Core (DI/IoC)   │  the underlying container
└─────────────────────────┘
```

---

## 6. Spring Boot — Key Features

| Feature | Description |
|---------|--------------|
| Auto-Configuration | Automatically configures beans based on what's on the classpath (e.g., detects a DB driver and configures a `DataSource`) |
| Starter Dependencies | Pre-bundled dependency sets (e.g., `spring-boot-starter-web`) that pull in everything needed for a use case |
| Embedded Server | Ships with an embedded Tomcat/Jetty — run as a plain `.jar`, no external server needed |
| Spring Boot CLI / Initializr | Quickly scaffold new projects with selected starters |
| Actuator | Built-in production-ready endpoints for health checks, metrics, monitoring |
| Externalized Configuration | Simple `application.properties`/`application.yml` replaces most XML config |

---

## 7. Spring Boot — How It Eliminates Spring's Cons

| Spring's Con | How Spring Boot Solves It |
|----------------|------------------------------|
| Heavy configuration overhead | Auto-Configuration inspects the classpath and wires sensible defaults automatically |
| Manual dependency/version management | Starter dependencies bundle compatible, tested versions together |
| No embedded server | Embedded Tomcat/Jetty ships inside the app — runnable as a single `.jar` |
| Steep setup learning curve | Spring Initializr generates a working project skeleton in seconds |

```
Fig 7.1 — Before vs After Spring Boot
BEFORE (Plain Spring)                  AFTER (Spring Boot)
──────────────────────                 ─────────────────────
Write XML/Java config for       ──►    Auto-configured based on
every bean manually                     classpath dependencies
Manage compatible library       ──►    Starter POMs bundle tested
versions by hand                        version sets
Package as WAR, deploy to       ──►    Run as a self-contained
external Tomcat                          jar with embedded server
Configure logging, health       ──►    Actuator provides these
checks manually                         out of the box
```

---

## 8. Spring Boot — Pros

- Drastically faster project setup — "convention over configuration."
- Self-contained, runnable JARs simplify deployment (especially with containers/Docker).
- Built-in monitoring and health-check support via Actuator.
- Still 100% built on Spring — no new concepts to learn, just less boilerplate.
- Large ecosystem of starters covering web, data, security, messaging, and more.

---

## 9. Working Sequence — Spring Boot Application Startup

```
Fig 9.1 — Startup Sequence

  main() method
       │
       ▼
SpringApplication.run(App.class, args)
       │
       ▼
┌─────────────────────────┐
│  Classpath scan         │  detects starters/dependencies
└─────────────┬───────────┘
              ▼
┌─────────────────────────┐
│  Auto-Configuration     │  configures beans automatically
│  (e.g., DataSource,     │  based on what's found
│   DispatcherServlet)    │
└─────────────┬───────────┘
              ▼
┌─────────────────────────┐
│  Embedded server starts │  Tomcat/Jetty boots inside the JVM
└─────────────┬───────────┘
              ▼
       Application ready
       to accept requests
```

---

## 10. Code Example — Spring (Manual Config) vs Spring Boot

```java
// ---------- BEFORE: Plain Spring MVC — manual configuration ----------
@Configuration
@EnableWebMvc
@ComponentScan("com.example.app")
public class WebConfig implements WebMvcConfigurer {
    // manually configure view resolvers, data sources, etc.
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/school");
        ds.setUsername("root");
        ds.setPassword("password");
        return ds;
    }
}
// Requires packaging as a WAR and deploying to an external Tomcat server.


// ---------- AFTER: Spring Boot — auto-configured ----------
@SpringBootApplication            // combines @Configuration, @EnableAutoConfiguration,
public class SchoolApp {          // and @ComponentScan into one annotation
    public static void main(String[] args) {
        SpringApplication.run(SchoolApp.class, args);   // starts embedded server
    }
}

// application.properties — replaces the manual DataSource bean above
// spring.datasource.url=jdbc:mysql://localhost:3306/school
// spring.datasource.username=root
// spring.datasource.password=password
// Spring Boot auto-configures the DataSource bean from these properties.

@RestController
public class StudentController {
    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.findAll();   // ready to run immediately, no WAR/Tomcat setup
    }
}
```

---

## 11. Common Exceptions / Issues

| Exception / Issue | Cause |
|---------------------|-------|
| `NoSuchBeanDefinitionException` | Requested bean was never created/registered in the IoC container |
| `BeanCreationException` | A bean's dependencies could not be satisfied during startup |
| Port already in use | Embedded server can't bind because another process is using the configured port |
| Auto-configuration not applying | Missing starter dependency, or a conflicting manual `@Configuration` overriding defaults |

---

## 12. Important Notes

- Spring Boot **does not replace** Spring — it's Spring with sensible defaults and less setup; all core Spring concepts (DI, IoC, beans) still apply.
- `@SpringBootApplication` is a convenience annotation bundling three others: `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`.
- Auto-configuration can be overridden at any time by defining your own bean — Spring Boot backs off when it detects a manual configuration.
- Spring Initializr (start.spring.io) is the standard way to scaffold a new Spring Boot project with the required starters pre-selected.

---

## 13. Summary

```
Fig 13.1 — Spring vs Spring Boot Recap
┌───────────────────────────────────────────────────────────┐
│  SPRING FRAMEWORK           →      SPRING BOOT            │
│                                                           │
│  Manual XML/Java config     →      Auto-configuration     │
│  Manual dependency mgmt      →      Starter dependencies  │
│  External server (WAR)       →      Embedded server (JAR) │
│  Steep initial setup          →      Spring Initializr,   │
│                                       ready in seconds    │
└───────────────────────────────────────────────────────────┘
```

| Concept | Key Takeaway |
|---------|---------------|
| Spring Framework | DI/IoC container; powerful but configuration-heavy |
| Spring Boot | Spring + auto-configuration + starters + embedded server |
| Relationship | Spring Boot is built on Spring, not a replacement for it |
| Core benefit | Convention over configuration — faster setup, less boilerplate |