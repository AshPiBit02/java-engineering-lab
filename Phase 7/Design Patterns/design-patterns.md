# 7.6 Design Patterns

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Why Design Patterns Matter](#2-why-design-patterns-matter)
- [3. Categories of Design Patterns](#3-categories-of-design-patterns)
- [4. Creational Patterns](#4-creational-patterns)
  - [4.1 Singleton](#41-singleton)
  - [4.2 Factory Method](#42-factory-method)
  - [4.3 Abstract Factory](#43-abstract-factory)
  - [4.4 Builder](#44-builder)
  - [4.5 Prototype](#45-prototype)
- [5. Structural Patterns](#5-structural-patterns)
  - [5.1 Adapter](#51-adapter)
  - [5.2 Decorator](#52-decorator)
  - [5.3 Proxy](#53-proxy)
  - [5.4 Facade](#54-facade)
- [6. Behavioral Patterns](#6-behavioral-patterns)
  - [6.1 Observer](#61-observer)
  - [6.2 Strategy](#62-strategy)
  - [6.3 Command](#63-command)
  - [6.4 Iterator](#64-iterator)
- [7. Pattern Comparison Table](#7-pattern-comparison-table)
- [8. Common Mistakes When Using Patterns](#8-common-mistakes-when-using-patterns)
- [9. Important Notes](#9-important-notes)
- [10. Summary](#10-summary)

---

## 1. Introduction

**Design patterns** are proven, reusable solutions to common software design problems. They aren't finished code — they're **templates** for how to structure classes and objects to solve a recurring problem cleanly. The patterns below come from the classic "Gang of Four" (GoF) catalog and appear throughout the Java ecosystem — including Hibernate and Spring, covered earlier in this phase.

```
Fig 1.1 — Design Pattern Categories
┌──────────────────────────────────────────────────────────────────┐
│  CREATIONAL   → how objects are created                          │
│  STRUCTURAL    → how objects/classes are composed                │
│  BEHAVIORAL    → how objects communicate and share responsibility│
└──────────────────────────────────────────────────────────────────┘
```

---

## 2. Why Design Patterns Matter

- Provide a **shared vocabulary** between developers ("just use a Factory here").
- Encapsulate **battle-tested solutions**, avoiding reinventing flawed designs.
- Directly explain the internals of tools already covered: Hibernate's `SessionFactory` (Singleton + Factory), Spring's Bean container (Singleton scope by default, Dependency Injection via Factory-like mechanisms), and JDBC drivers (Factory Method via `DriverManager`).

---

## 3. Categories of Design Patterns

| Category | Purpose | Patterns Covered |
|-----------|----------|-------------------|
| Creational | Control object creation | Singleton, Factory Method, Abstract Factory, Builder, Prototype |
| Structural | Compose classes/objects into larger structures | Adapter, Decorator, Proxy, Facade |
| Behavioral | Manage communication and responsibility between objects | Observer, Strategy, Command, Iterator |

---

## 4. Creational Patterns

### 4.1 Singleton

**Intro:** Ensures a class has only **one instance** across the entire application, with a global access point to it.

```
Fig 4.1.1 — Singleton Structure
┌─────────────────────┐
│     Singleton       │
│  - instance (static)│
│  - Singleton()      │◄── private constructor
│  + getInstance()    │──► always returns the SAME object
└─────────────────────┘
```

```java
public class ConfigManager {
    private static ConfigManager instance;   // single shared instance

    private ConfigManager() { }               // private — prevents external `new`

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();    // created only once
        }
        return instance;
    }
}
```

**Use case:** Hibernate's `SessionFactory`, logging managers, application-wide configuration objects.

---

### 4.2 Factory Method

**Intro:** Defines an interface for creating an object, but lets subclasses decide **which class to instantiate**.

```
Fig 4.2.1 — Factory Method Structure
┌───────────┐      ┌────────────┐
│  Creator  │─────►│   Product  │ (interface)
│ +factory()│      └──────┬─────┘
└───────────┘             │
                  ┌───────┴────────┐
             ┌──────────┐   ┌──────────┐
             │ ConcreteA│   │ ConcreteB│
             └──────────┘   └──────────┘
```

```java
interface Notification { void notifyUser(); }

class EmailNotification implements Notification {
    public void notifyUser() { System.out.println("Sending Email"); }
}
class SMSNotification implements Notification {
    public void notifyUser() { System.out.println("Sending SMS"); }
}

class NotificationFactory {
    public static Notification create(String type) {   // factory method decides the class
        if (type.equals("EMAIL")) return new EmailNotification();
        return new SMSNotification();
    }
}
```

**Use case:** JDBC's `DriverManager.getConnection()` — returns different driver implementations behind one interface.

---

### 4.3 Abstract Factory

**Intro:** Provides an interface for creating **families of related objects** without specifying their concrete classes.

```
Fig 4.3.1 — Abstract Factory Structure
┌───────────────────┐
│ AbstractFactory   │
│ +createButton()   │
│ +createCheckbox() │
└────────┬──────────┘
      ┌──┴─────────────┐
 ┌───────────┐    ┌──────────┐
 │ WinFactory│    │MacFactory│
 └───────────┘    └──────────┘
```

```java
interface Button { void render(); }
class WindowsButton implements Button { public void render() { System.out.println("Windows Button"); } }
class MacButton implements Button { public void render() { System.out.println("Mac Button"); } }

interface GUIFactory { Button createButton(); }
class WindowsFactory implements GUIFactory {
    public Button createButton() { return new WindowsButton(); }   // family of Windows widgets
}
class MacFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }        // family of Mac widgets
}
```

**Use case:** Cross-platform UI toolkits; database driver factories that produce matching families of connection/statement/result-set objects.

---

### 4.4 Builder

**Intro:** Separates the construction of a **complex object** from its representation, allowing step-by-step construction.

```
Fig 4.4.1 — Builder Structure
Director → uses → Builder → builds → Product (step by step)
```

```java
class Student {
    private String name, dept;
    private int semester;

    static class Builder {
        private Student s = new Student();
        Builder name(String n) { s.name = n; return this; }        // fluent chaining
        Builder dept(String d) { s.dept = d; return this; }
        Builder semester(int sem) { s.semester = sem; return this; }
        Student build() { return s; }
    }
}

// Usage:
Student s = new Student.Builder().name("Aasii").dept("CS").semester(3).build();
```

**Use case:** Constructing objects with many optional fields (avoids constructors with 8+ parameters); `StringBuilder` itself follows this pattern.

---

### 4.5 Prototype

**Intro:** Creates new objects by **copying (cloning)** an existing object instead of instantiating from scratch.

```
Fig 4.5.1 — Prototype Structure
existingObject ──clone()──► newObject (same state, independent copy)
```

```java
class Report implements Cloneable {
    String title;
    Report(String title) { this.title = title; }

    public Report clone() {
        return new Report(this.title);   // simplified deep copy
    }
}

Report base = new Report("Q1 Summary");
Report copy = base.clone();   // avoids re-running expensive construction logic
```

**Use case:** Cloning pre-configured objects (e.g., default document templates) instead of rebuilding them from raw data each time.

---

## 5. Structural Patterns

### 5.1 Adapter

**Intro:** Converts one interface into another that a client expects, allowing incompatible classes to work together.

```
Fig 5.1.1 — Adapter Structure
Client ──uses──► Target Interface ◄──implemented by── Adapter ──wraps──► Adaptee (incompatible class)
```

```java
interface MediaPlayer { void play(String fileName); }

class LegacyPlayer {                        // incompatible existing class
    void playOldFormat(String file) { System.out.println("Playing old format: " + file); }
}

class MediaAdapter implements MediaPlayer {   // adapts LegacyPlayer to MediaPlayer
    private LegacyPlayer legacy = new LegacyPlayer();
    public void play(String fileName) { legacy.playOldFormat(fileName); }
}
```

**Use case:** Wrapping a legacy API/library to match a modern interface expected by new code.

---

### 5.2 Decorator

**Intro:** Dynamically adds new behavior/responsibility to an object **without modifying its class**.

```
Fig 5.2.1 — Decorator Structure
Component ◄── ConcreteComponent
    ▲
    │ wraps
Decorator ◄── ConcreteDecoratorA, ConcreteDecoratorB (stackable)
```

```java
interface Coffee { double cost(); }

class SimpleCoffee implements Coffee {
    public double cost() { return 50; }
}

class MilkDecorator implements Coffee {       // wraps another Coffee, adds behavior
    private Coffee base;
    MilkDecorator(Coffee base) { this.base = base; }
    public double cost() { return base.cost() + 15; }   // extends without modifying SimpleCoffee
}

Coffee order = new MilkDecorator(new SimpleCoffee());  // cost() = 65
```

**Use case:** Java I/O streams (`BufferedReader` wrapping `FileReader`) are a textbook real-world example.

---

### 5.3 Proxy

**Intro:** Provides a **stand-in object** that controls access to another object (for lazy loading, access control, or logging).

```
Fig 5.3.1 — Proxy Structure
Client ──► Proxy ──controls access to──► RealSubject
```

```java
interface Image { void display(); }

class RealImage implements Image {
    private String file;
    RealImage(String file) { this.file = file; loadFromDisk(); }
    private void loadFromDisk() { System.out.println("Loading " + file); }
    public void display() { System.out.println("Displaying " + file); }
}

class ProxyImage implements Image {           // controls/delays creation of RealImage
    private RealImage real;
    private String file;
    ProxyImage(String file) { this.file = file; }
    public void display() {
        if (real == null) real = new RealImage(file);   // lazy loading
        real.display();
    }
}
```

**Use case:** Hibernate's lazy-loaded entity proxies (7.2) — the object isn't fetched until it's actually accessed.

---

### 5.4 Facade

**Intro:** Provides a **simplified, unified interface** to a complex subsystem of classes.

```
Fig 5.4.1 — Facade Structure
Client ──► Facade ──► Subsystem A, Subsystem B, Subsystem C (hidden complexity)
```

```java
class CPU { void start() { System.out.println("CPU started"); } }
class Memory { void load() { System.out.println("Memory loaded"); } }
class HardDrive { void read() { System.out.println("Disk read"); } }

class ComputerFacade {                        // hides subsystem complexity
    private CPU cpu = new CPU();
    private Memory memory = new Memory();
    private HardDrive hd = new HardDrive();

    void start() {                             // one simple method call
        cpu.start();
        memory.load();
        hd.read();
    }
}
```

**Use case:** Spring's `JdbcTemplate` — hides raw JDBC boilerplate (Connection, Statement, ResultSet) behind simple method calls.

---

## 6. Behavioral Patterns

### 6.1 Observer

**Intro:** Defines a **one-to-many dependency** so that when one object changes state, all its dependents are notified automatically.

```
Fig 6.1.1 — Observer Structure
Subject ──notifies──► Observer 1
        ──notifies──► Observer 2
        ──notifies──► Observer 3
```

```java
interface Observer { void update(String event); }

class Subject {
    private List<Observer> observers = new ArrayList<>();
    void subscribe(Observer o) { observers.add(o); }
    void notifyAll(String event) {
        for (Observer o : observers) o.update(event);   // notify every subscriber
    }
}
```

**Use case:** GUI event listeners, messaging systems, Spring's `ApplicationEvent`/`ApplicationListener`.

---

### 6.2 Strategy

**Intro:** Defines a family of interchangeable algorithms and lets the client choose one at **runtime**.

```
Fig 6.2.1 — Strategy Structure
Context ──uses──► Strategy (interface)
                       ▲
              ┌────────┴────────┐
        ConcreteStrategyA   ConcreteStrategyB
```

```java
interface SortStrategy { void sort(int[] data); }

class BubbleSort implements SortStrategy {
    public void sort(int[] data) { System.out.println("Bubble sorting"); }
}
class QuickSort implements SortStrategy {
    public void sort(int[] data) { System.out.println("Quick sorting"); }
}

class Sorter {
    private SortStrategy strategy;
    Sorter(SortStrategy s) { this.strategy = s; }      // strategy chosen at runtime
    void performSort(int[] data) { strategy.sort(data); }
}
```

**Use case:** Payment processing (choosing card/UPI/wallet at runtime), Spring Security's pluggable authentication strategies.

---

### 6.3 Command

**Intro:** Encapsulates a **request as an object**, allowing actions to be queued, logged, or undone.

```
Fig 6.3.1 — Command Structure
Invoker ──executes──► Command (interface) ──calls──► Receiver
```

```java
interface Command { void execute(); }

class Light {
    void turnOn() { System.out.println("Light ON"); }
}

class TurnOnCommand implements Command {      // wraps a request as an object
    private Light light;
    TurnOnCommand(Light light) { this.light = light; }
    public void execute() { light.turnOn(); }
}

class RemoteControl {
    void pressButton(Command cmd) { cmd.execute(); }   // invoker doesn't know the details
}
```

**Use case:** Undo/redo systems, task queues, GUI button actions.

---

### 6.4 Iterator

**Intro:** Provides a way to access elements of a collection **sequentially without exposing its underlying structure**.

```
Fig 6.4.1 — Iterator Structure
Client ──uses──► Iterator ──traverses──► Aggregate (collection)
```

```java
List<String> names = List.of("Aasii", "Ram", "Sita");
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    System.out.println(it.next());   // sequential access, no need to know List's internals
}
```

**Use case:** Java's own Collections Framework (`Iterator`, enhanced for-loop) is a direct, built-in implementation of this pattern.

---

## 7. Pattern Comparison Table

| Pattern | Category | One-Line Purpose |
|---------|-----------|--------------------|
| Singleton | Creational | One instance, globally accessible |
| Factory Method | Creational | Subclass decides which object to create |
| Abstract Factory | Creational | Creates families of related objects |
| Builder | Creational | Step-by-step construction of complex objects |
| Prototype | Creational | Create new objects by cloning existing ones |
| Adapter | Structural | Makes incompatible interfaces work together |
| Decorator | Structural | Adds behavior dynamically without modifying the class |
| Proxy | Structural | Controls/delays access to another object |
| Facade | Structural | Simplifies access to a complex subsystem |
| Observer | Behavioral | Notifies dependents automatically on state change |
| Strategy | Behavioral | Swaps algorithms at runtime |
| Command | Behavioral | Encapsulates a request as an object |
| Iterator | Behavioral | Sequential access without exposing internal structure |

---

## 8. Common Mistakes When Using Patterns

```
Fig 8.1 — Pitfall Map
┌──────────────────────────────────────────────┐
│ Overusing Singleton → hidden global state,   │
│ hard to test, hidden dependencies            │
├──────────────────────────────────────────────┤
│ Forcing a pattern where a simple object/     │
│ method would suffice → unnecessary complexity│
├──────────────────────────────────────────────┤
│ Confusing Factory Method with Abstract       │
│ Factory (single product vs product families) │
├──────────────────────────────────────────────┤
│ Using Decorator excessively → deeply nested, │
│ hard-to-debug wrapper chains                 │
└──────────────────────────────────────────────┘
```

---

## 9. Important Notes

- Design patterns are **not code to copy-paste blindly** — they're structural templates adapted to the specific problem.
- Many patterns already studied in this phase are built into frameworks: `SessionFactory` (Singleton), Hibernate lazy proxies (Proxy), `JdbcTemplate` (Facade), Spring's event system (Observer).
- Patterns are grouped by **intent** (creational/structural/behavioral) — always ask "what problem is this solving?" before picking one.
- Overengineering with patterns is a common mistake for developers early in their careers — use a pattern only when the problem it solves is actually present.

---

## 10. Summary

```
Fig 10.1 — Design Patterns Recap
┌─────────────────────────────────────────────────────────────┐
│  DESIGN PATTERNS                                            │
│                                                             │
│  Creational  → Singleton, Factory Method, Abstract Factory, │
│                Builder, Prototype                           │
│  Structural   → Adapter, Decorator, Proxy, Facade           │
│  Behavioral   → Observer, Strategy, Command, Iterator       │
│                                                             │
│  Result: Reusable, well-understood solutions to recurring   │
│  design problems — and the same vocabulary used inside      │
│  Hibernate, Spring, and the Java Collections Framework.     │
└─────────────────────────────────────────────────────────────┘
```

| Category | Patterns | Key Idea |
|----------|-----------|-----------|
| Creational | Singleton, Factory Method, Abstract Factory, Builder, Prototype | Control how objects are created |
| Structural | Adapter, Decorator, Proxy, Facade | Control how objects/classes are composed |
| Behavioral | Observer, Strategy, Command, Iterator | Control how objects communicate and share responsibility |
