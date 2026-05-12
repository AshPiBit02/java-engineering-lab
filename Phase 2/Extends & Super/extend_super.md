---

# `extends` & `super` in Java

---

## Table of Contents

1. [Quick Orientation](#1-quick-orientation)
2. [The `extends` Keyword](#2-the-extends-keyword)
3. [What Gets Inherited?](#3-what-gets-inherited)
4. [The `super` Keyword](#4-the-super-keyword)
5. [Constructor Chaining with `super()`](#5-constructor-chaining-with-super)
6. [Method Overriding & `super.method()`](#6-method-overriding--supermethod)
7. [`super` in Multilevel Inheritance](#7-super-in-multilevel-inheritance)
8. [`extends` with Interfaces](#8-extends-with-interfaces)
9. [C++ vs Java Comparison](#9-c-vs-java-comparison)
10. [Common Mistakes & Pitfalls](#10-common-mistakes--pitfalls)
11. [Summary Table](#11-summary-table)

---

## 1. Quick Orientation

| Keyword   | Role                                                              |
|-----------|-------------------------------------------------------------------|
| `extends`  | Declares that a class (or interface) inherits from another       |
| `super`    | Refers to the **immediate parent** — its constructor or members  |

Together, they define *how* a subclass connects to and communicates with its parent.

---

## 2. The `extends` Keyword

### 2.1 Basic Syntax

```java
class Animal {
    String name;
    void eat() { System.out.println(name + " is eating."); }
}

class Dog extends Animal {       // Dog IS-A Animal
    void bark() { System.out.println(name + " barks!"); }
}
```

```
Fig. 1 — extends: IS-A Relationship
┌─────────────────┐
│     Animal      │
│  - name         │
│  + eat()        │
└────────┬────────┘
         │ extends
         ▼
┌─────────────────┐
│      Dog        │
│  (inherits name │
│   and eat())    │
│  + bark()       │
└─────────────────┘
```

### 2.2 Single Inheritance Only

Java allows a class to extend **exactly one** class. This is a deliberate design choice to avoid the **Diamond Problem**.

```java
class A {}
class B {}

// ✗ Compile error — Java does not allow multiple class inheritance
// class C extends A, B {}

// ✔ Only one superclass allowed
class C extends A {}
```

> **C++ note:** C++ supports multiple inheritance of classes directly. Java replaces this with **interface implementation** (covered in your Interfaces notes).

### 2.3 All Classes Extend `Object` Implicitly

If you don't write `extends`, Java silently inserts `extends Object`:

```java
class Animal { }
// is exactly the same as:
class Animal extends Object { }
```

```
Fig. 2 — The Root of All Java Classes
             ┌──────────────┐
             │    Object    │  ← java.lang.Object
             │  toString()  │
             │  equals()    │
             │  hashCode()  │
             │  getClass()  │
             └──────┬───────┘
                    │ (implicit extends)
             ┌──────▼───────┐
             │    Animal    │
             └──────┬───────┘
                    │ extends
             ┌──────▼───────┐
             │     Dog      │
             └──────────────┘
```

Every class you write already inherits `toString()`, `equals()`, `hashCode()`, and `getClass()` from `Object` — you can override them freely.

---

## 3. What Gets Inherited?

Not everything from the parent class travels to the subclass:

```
Fig. 3 — Inheritance: What Passes, What Doesn't
┌──────────────────────────────────────────────────┐
│                  Parent Class                    │
│                                                  │
│  ✔ public fields & methods       → inherited     │
│  ✔ protected fields & methods    → inherited     │
│  ✔ package-private (default)     → inherited *   │
│  ✗ private fields & methods      → NOT inherited │
│  ✗ constructors                  → NOT inherited │
│  ✗ static members                → NOT inherited │
│    (accessible, but not "owned") │               │
└──────────────────────────────────────────────────┘
  * only if subclass is in the same package
```

```java
class Vehicle {
    public String brand = "Generic";       // ✔ inherited
    protected int year;                    // ✔ inherited
    private String vin;                    // ✗ not accessible directly

    public void start()   { System.out.println("Starting..."); }
    protected void refuel() { System.out.println("Refuelling..."); }
    private void internalCheck() {}        // ✗ not accessible
}

class Car extends Vehicle {
    void demo() {
        System.out.println(brand);    // ✔ public field
        year = 2024;                  // ✔ protected field
        // vin = "...";              // ✗ compile error
        start();                     // ✔ public method
        refuel();                    // ✔ protected method
        // internalCheck();          // ✗ compile error
    }
}
```


---

## 4. The `super` Keyword

`super` is a **reference to the immediate parent class**. It has two distinct uses:

| Use                  | Syntax                          | Purpose                                        |
|----------------------|---------------------------------|------------------------------------------------|
| Call parent constructor | `super(args)`               | Must be first statement in subclass constructor |
| Access parent member | `super.fieldOrMethod()`        | Disambiguate when subclass has same name        |

### 4.1 Accessing a Parent Field with `super`

```java
class Animal {
    String type = "Animal";
}

class Dog extends Animal {
    String type = "Dog";   // shadows the parent field

    void printTypes() {
        System.out.println(type);        // → "Dog"  (own field)
        System.out.println(super.type);  // → "Animal" (parent field)
    }
}
```

> Field shadowing is generally discouraged — it reduces clarity. Prefer unique names.

---

## 5. Constructor Chaining with `super()`

### 5.1 The Rule

When a subclass object is created, the **parent constructor must run first**. Java enforces this:

- If you write `super(...)` explicitly → it must be the **very first line** of the subclass constructor.
- If you don't write it → Java **automatically inserts** `super()` (no-arg) as the first line.

```
Fig. 4 — Constructor Execution Order
  new Dog("Rex") called
        │
        ▼
  Dog(String name) runs
        │ first line: super(name)  ← or implicit super()
        ▼
  Animal(String name) runs
        │ first line: super()  ← Object's constructor
        ▼
  Object() runs
        │
        ▼
  Object() completes
  Animal() completes
  Dog() completes
  Object fully initialized ✔
```

### 5.2 Explicit `super()` Call

```java
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor: " + name);
    }
}

class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        super(name);              // ← MUST be first; calls Animal(String)
        this.breed = breed;
        System.out.println("Dog constructor: " + breed);
    }
}

// Output when: new Dog("Rex", "Labrador")
// Animal constructor: Rex
// Dog constructor: Labrador
```

### 5.3 Implicit `super()` — When It Can Fail

If you don't write `super(...)`, Java inserts `super()`. This **fails at compile time** if the parent has no no-arg constructor:

```java
class Animal {
    Animal(String name) { this.name = name; }  // Only parameterized constructor
    String name;
}

class Cat extends Animal {
    Cat() {
        // Java inserts super() here automatically
        // ✗ Compile error: no suitable constructor found in Animal
    }
}

// Fix: explicitly call the right parent constructor
class Cat extends Animal {
    Cat(String name) {
        super(name);   // ✔ explicit call to Animal(String)
    }
}
```

> **C++ note:** C++ (before C++11) required explicit base constructor calls in the member initializer list: `Cat(string name) : Animal(name) {}`. The concept is the same, but Java uses `super()` inside the body (as the first statement).

### 5.4 `super()` vs `this()` — Cannot Use Both First

```java
class Dog extends Animal {
    Dog() {
        this("Unknown", "Mixed");  // calls Dog(String, String) — first line
        // super() is called by Dog(String, String) — chained
    }

    Dog(String name, String breed) {
        super(name);              // calls Animal(String) — first line
        this.breed = breed;
    }

    String breed;
}
```

> `super()` and `this()` can both be constructor-chaining tools — but only **one** can be the first statement, and you cannot use both at the top level of the same constructor.

---

## 6. Method Overriding & `super.method()`

### 6.1 Calling the Parent's Version

When you override a method, `super.methodName()` lets you call the parent's original implementation:

```java
class Animal {
    void describe() {
        System.out.println("I am an animal.");
    }
}

class Dog extends Animal {
    @Override
    void describe() {
        super.describe();                         // ← parent's version first
        System.out.println("Specifically, I am a dog.");
    }
}

// new Dog().describe() outputs:
// I am an animal.
// Specifically, I am a dog.
```

```
Fig. 5 — super.method() in Overriding
  Dog.describe() called
        │
        ├─→ super.describe()  →  Animal.describe() runs
        │                             prints "I am an animal."
        │   ←──────────────────────────────────────────
        │
        └─→ prints "Specifically, I am a dog."
```

### 6.2 When Is `super.method()` Useful?

The typical pattern is **extend, don't replace**:

```java
class Logger {
    void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

class TimestampLogger extends Logger {
    @Override
    void log(String message) {
        super.log(message);   // reuse parent logic
        System.out.println("    at: " + System.currentTimeMillis());
    }
}
```

This avoids duplicating the parent's logic while adding new behavior — a clean, composable pattern.

### 6.3 `super` is One Level Only

`super` always refers to the **immediate** parent — you cannot skip levels:

```java
class A { void hello() { System.out.println("A"); } }
class B extends A { void hello() { System.out.println("B"); } }
class C extends B {
    void hello() {
        super.hello();        // → calls B.hello() only
        // super.super.hello() ← ✗ NOT valid in Java
    }
}
```

> **C++ note:** C++ has the same restriction — `A::hello()` would be the explicit workaround in C++, but Java has no equivalent syntax for skipping a level.

---

## 7. `super` in Multilevel Inheritance

```
Fig. 6 — Multilevel super Chain
┌─────────────────────┐
│       Object        │
└──────────┬──────────┘
           │ extends (implicit)
┌──────────▼──────────┐
│       Shape         │  ← Shape() called 3rd (by Object above)
│  Shape(String color)│
└──────────┬──────────┘
           │ extends
┌──────────▼──────────┐
│       Polygon       │  ← Polygon(sides, color)
│  super(color)       │    calls Shape(color) 2nd
└──────────┬──────────┘
           │ extends
┌──────────▼──────────┐
│       Rectangle     │  ← new Rectangle() called 1st
│  super(4, color)    │    calls Polygon(4, color)
└─────────────────────┘
```

```java
class Shape {
    String color;
    Shape(String color) {
        this.color = color;
        System.out.println("Shape: " + color);
    }
}

class Polygon extends Shape {
    int sides;
    Polygon(int sides, String color) {
        super(color);         // → Shape(color)
        this.sides = sides;
        System.out.println("Polygon: " + sides + " sides");
    }
}

class Rectangle extends Polygon {
    Rectangle(String color) {
        super(4, color);      // → Polygon(4, color) → Shape(color)
        System.out.println("Rectangle created");
    }
}

// new Rectangle("red") outputs:
// Shape: red
// Polygon: 4 sides
// Rectangle created
```

Every level in the chain must eventually reach `Object()` — Java guarantees this via the enforced first-statement rule.

---

## 8. `extends` with Interfaces

`extends` is not exclusive to classes. **Interfaces can extend other interfaces** — and unlike classes, they can extend **multiple** interfaces:

```java
interface Drawable {
    void draw();
}

interface Resizable {
    void resize(double factor);
}

// Interface extending multiple interfaces — allowed!
interface Shape extends Drawable, Resizable {
    double area();
}

class Circle implements Shape {
    double radius;
    Circle(double r) { this.radius = r; }

    @Override public void draw()              { System.out.println("Drawing circle"); }
    @Override public void resize(double f)    { radius *= f; }
    @Override public double area()            { return Math.PI * radius * radius; }
}
```

```
Fig. 7 — Interface extends Interface
┌─────────────┐    ┌─────────────┐
│  Drawable   │    │  Resizable  │
│  + draw()   │    │  + resize() │
└──────┬──────┘    └──────┬──────┘
       │  extends         │ extends
       └────────┬─────────┘
          ┌─────▼──────┐
          │   Shape    │
          │  + area()  │
          └─────┬──────┘
                │ implements
          ┌─────▼──────┐
          │   Circle   │
          └────────────┘
```

| Scenario                              | Keyword used  | Multiple allowed? |
|---------------------------------------|---------------|-------------------|
| Class inheriting a class              | `extends`     | No (one only)     |
| Class implementing interfaces         | `implements`  | Yes               |
| Interface inheriting interface(s)     | `extends`     | Yes               |


---

## 9. C++ vs Java Comparison

| Feature                          | C++                                              | Java                                              |
|----------------------------------|--------------------------------------------------|---------------------------------------------------|
| Inheritance keyword              | `: public BaseClass`                             | `extends BaseClass`                               |
| Multiple class inheritance       | Supported                                        | Not supported (use interfaces)                    |
| Call parent constructor          | `: BaseClass(args)` in initializer list          | `super(args)` as first statement in constructor   |
| Implicit parent constructor call | Not automatic — must be explicit                 | Auto-inserts `super()` if not written             |
| Access parent method             | `BaseClass::method()`                            | `super.method()`                                  |
| Skip multiple levels with super  | `GrandParent::method()` — possible               | Not possible — `super` is always one level        |
| Default base class               | None (no universal root)                         | `java.lang.Object` — always                       |
| Interface inheritance            | No direct equivalent (`virtual` classes used)    | Interface `extends` interface(s)                  |
| `virtual` keyword                | Required for polymorphism                        | Not needed — all instance methods are virtual     |

---

## 10. Common Mistakes & Pitfalls

### Mistake 1: `super()` Not First Statement

```java
class Dog extends Animal {
    Dog(String name) {
        System.out.println("Preparing...");  // ✗ Compile error
        super(name);
    }
}
// Fix: move super(name) to be the very first line
```

---

### Mistake 2: Forgetting Parent Has No Default Constructor

```java
class Animal {
    Animal(String name) { ... }   // Only parameterized — no Animal()
}

class Fish extends Animal {
    Fish() { }  // ✗ Java inserts super() — but Animal() doesn't exist!
}
// Fix: Fish() { super("Unknown"); }
```

---

### Mistake 3: Using `super` Outside a Subclass

```java
class Standalone {
    void method() {
        super.toString();  // ✗ Compile error — no parent context
    }
}
// super is only valid inside a class that extends something other than Object
// (and even then, super.toString() on Object works but is unusual)
```

---

### Mistake 4: Confusing `super.field` Access with Constructors

```java
class A { int x = 10; }
class B extends A {
    int x = 20;
    void show() {
        System.out.println(x);        // 20 — B's field
        System.out.println(super.x);  // 10 — A's field
    }
}
```
This is legal, but **field shadowing is a design smell** — use distinct names unless there's a strong reason.

---

### Mistake 5: Trying to `super` into a `final` class

Final classes cannot be extended, so `super` into them is never possible (the class itself can still call `super` to `Object` internally).

---

## 11. Summary Table

| Concept                            | Key Rule                                                                        |
|------------------------------------|---------------------------------------------------------------------------------|
| `extends` (class)                  | Single inheritance only; subclass IS-A superclass                               |
| `extends` (interface)              | Interface can extend multiple interfaces                                        |
| Implicit superclass                | Every class implicitly extends `Object` if no `extends` written                 |
| What is inherited                  | `public` and `protected` members; not `private`, not constructors               |
| `super(args)`                      | Calls parent constructor; must be the **first line** of subclass constructor    |
| Implicit `super()`                 | Auto-inserted by Java if you omit it; fails if parent has no no-arg constructor |
| `super.method()`                   | Calls parent's version of an overridden method                                  |
| `super.field`                      | Accesses parent's field when shadowed by subclass field                         |
| `super` reach                      | One level only — immediate parent; cannot skip levels                           |
| `super()` vs `this()`              | Both must be first-line constructor calls; cannot use both in one constructor   |
| C++ equivalent of `super(args)`    | `: BaseClass(args)` in member initializer list                                  |
| C++ equivalent of `super.method()` | `BaseClass::method()`                                                           |

---

> **Connections to previous topics:**
> - **Inheritance (Basics + Types)** → IS-A relationships that `extends` formalizes
> - **OOP Principles** → polymorphism enabled by `extends` + overriding
> - **Upcasting & Downcasting** → `extends` defines the type hierarchy that makes casting possible
> - **Access Modifiers** → determines which parent members a subclass can actually see
> - **Constructors** → constructor chaining via `super()` ties directly into your constructor notes
> - **Interfaces** → `extends` on interfaces for multi-interface hierarchies
> - **Final Classes & Methods** → `final` is what *stops* `extends` from working

---
