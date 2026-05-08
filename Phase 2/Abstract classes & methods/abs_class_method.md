---

# Abstract Classes & Methods in Java

---

## Table of Contents

1. [What is an Abstract Class?](#1-what-is-an-abstract-class)
2. [Abstract Methods](#2-abstract-methods)
3. [Rules & Constraints](#3-rules--constraints)
4. [Concrete vs Abstract — The Spectrum](#4-concrete-vs-abstract--the-spectrum)
5. [Constructors in Abstract Classes](#5-constructors-in-abstract-classes)
6. [Abstract Classes vs Interfaces](#6-abstract-classes-vs-interfaces)
7. [The Template Method Pattern](#7-the-template-method-pattern)
8. [Real-World Use Cases](#8-real-world-use-cases)
9. [Common Mistakes & Pitfalls](#9-common-mistakes--pitfalls)
10. [Summary Table](#10-summary-table)

---

## 1. What is an Abstract Class?

An **abstract class** is a class that is **intentionally incomplete** — it defines a partial blueprint that subclasses are expected to complete. You cannot instantiate an abstract class directly; it exists solely to be extended.

```java
abstract class Shape {
    // ...
}

Shape s = new Shape();   // ✗ Compile error: Shape is abstract; cannot be instantiated
```

```
Fig. 1 — Abstract Class as Incomplete Blueprint
┌──────────────────────────────────────────┐
│          abstract class Shape            │
│                                          │
│  + color: String          ← concrete     │
│  + getColor(): String     ← concrete     │
│  + area(): double         ← abstract ??? │
│  + perimeter(): double    ← abstract ??? │
│                                          │
│  "I know every shape HAS an area,        │
│   but I don't know HOW to compute it"    │
└──────────────┬───────────────────────────┘
               │
       ┌───────┴────────┐
       ▼                ▼
┌────────────┐    ┌──────────────┐
│   Circle   │    │  Rectangle   │
│  area()✔   |    │ area()✔     │
│perimeter()✔│    │perimeter()✔ │
└────────────┘    └──────────────┘
  (complete)        (complete)
```

The abstract class enforces a **contract**: every concrete subclass *must* provide the missing pieces.

---

## 2. Abstract Methods

An **abstract method** is a method with **no body** — just a signature and a semicolon. It declares *what* must be done, leaving *how* entirely to the subclass.

```java
abstract class Shape {
    String color;

    // Abstract — no body, must be overridden
    abstract double area();
    abstract double perimeter();

    // Concrete — has a body, inherited as-is
    String getColor() {
        return color;
    }
}
```

### 2.1 Implementing Abstract Methods

A subclass that extends an abstract class **must implement all abstract methods**, or it must itself be declared `abstract`:

```java
class Circle extends Shape {
    double radius;

    Circle(double radius, String color) {
        this.radius = radius;
        this.color  = color;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * radius;
    }
}
```

```java
// If you only implement SOME abstract methods:
abstract class Polygon extends Shape {
    int sides;

    @Override
    double perimeter() {              // implements one
        return sides * sideLength();
    }

    abstract double sideLength();     // adds a NEW abstract method
    // area() still not implemented → class must be abstract
}
```

```
Fig. 2 — Partial Implementation Chain
abstract Shape          (area, perimeter — both abstract)
      │
abstract Polygon        (perimeter — implemented; area, sideLength — abstract)
      │
class Square            (area, sideLength — implemented; all satisfied ✔)
```

---

## 3. Rules & Constraints

```
Fig. 3 — Abstract Class Rules at a Glance
┌─────────────────────────────────────────────────────────┐
│  abstract class                                         │
│                                                         │
│  ✔ Can have abstract methods                            │
│  ✔ Can have concrete (non-abstract) methods             │
│  ✔ Can have instance fields                             │
│  ✔ Can have static fields and methods                   │
│  ✔ Can have constructors (not called directly)          │
│  ✔ Can have access modifiers (public, protected, etc.)  │
│  ✔ Can implement interfaces (partially or fully)        │
│  ✗ Cannot be instantiated with new                      │
│  ✗ Cannot be final (final = no subclass; contradicts)   │
│  ✗ Abstract method cannot be private                    │
│  ✗ Abstract method cannot be static                     │
│  ✗ Abstract method cannot be final                      │
└─────────────────────────────────────────────────────────┘
```

### 3.1 Why Abstract Method Cannot Be `private`

```java
abstract class Base {
    // ✗ Compile error — private abstract makes no sense:
    // private methods are invisible to subclasses,
    // so no subclass could ever implement it.
    private abstract void secret();
}
```

### 3.2 Why Abstract Method Cannot Be `static`

```java
abstract class Base {
    // ✗ Compile error
    static abstract void classLevel();
}
```

Static methods belong to the class itself — they are resolved at compile time. Abstract methods require runtime polymorphism (dynamic dispatch). These two concepts are incompatible.

### 3.3 A Class With Zero Abstract Methods Can Still Be Abstract

```java
// Perfectly valid — no abstract methods, but still abstract
abstract class DatabaseConnection {
    String url;
    int port;

    void connect()    { System.out.println("Connecting to " + url); }
    void disconnect() { System.out.println("Disconnecting"); }
}
```

This is used when you want to **prevent direct instantiation** of a base class even if all methods are implemented — forcing users to always work with a specific subclass.

---

## 4. Concrete vs Abstract — The Spectrum

A class hierarchy can mix abstract and concrete classes across multiple levels:

```
Fig. 4 — The Abstract-to-Concrete Spectrum
                   ┌──────────────────────┐
                   │   abstract Animal    │  ← cannot instantiate
                   │   + breathe()  [C]   │
                   │   + speak()    [A]   │
                   └──────────┬───────────┘
              ┌───────────────┼────────────────┐
              ▼               ▼                ▼
   ┌───────────────┐  ┌──────────────┐  ┌──────────────┐
   │abstract Mammal│  │ class Bird   │  │ class Fish   │
   │ + speak() [A] │  │ + speak() ✔  │  │ + speak() ✔ │
   │ + walk()  [A] │  │ (concrete)   │  │ (concrete)   │
   └───────┬───────┘  └──────────────┘  └──────────────┘
       ┌───┴──────────┐
       ▼              ▼
  ┌───────────┐  ┌───────────┐
  │  Dog      │  │  Cat      │
  │speak() ✔ │  │speak() ✔  │
  │walk()  ✔ │  │walk()  ✔  │
  │(concrete) │  │(concrete) │
  └───────────┘  └───────────┘

[A] = abstract method    [C] = concrete method
```

---

## 5. Constructors in Abstract Classes

Abstract classes **can and should** have constructors, even though you can never call `new AbstractClass()` directly. These constructors are invoked via `super()` from concrete subclasses.

```java
abstract class Vehicle {
    String brand;
    int year;

    // Constructor in abstract class
    Vehicle(String brand, int year) {
        this.brand = brand;
        this.year  = year;
        System.out.println("Vehicle initialized: " + brand);
    }

    abstract double fuelEfficiency();  // km per litre
}

class Car extends Vehicle {
    double engineCC;

    Car(String brand, int year, double engineCC) {
        super(brand, year);            // calls abstract class constructor
        this.engineCC = engineCC;
    }

    @Override
    double fuelEfficiency() {
        return 15.0 - (engineCC / 1000);   // rough formula
    }
}
```

```
Fig. 5 — Constructor Flow with Abstract Class
  new Car("Toyota", 2024, 1500.0)
           │
           ▼
   Car(brand, year, cc)
           │ super(brand, year)
           ▼
   Vehicle(brand, year)    ← abstract class constructor runs
           │ super()        (implicit)
           ▼
   Object()
```

**Why constructors in abstract classes?**
- Initialize **shared fields** that all subclasses will have
- Run **common validation** logic before the subclass adds its own state
- Enforce **invariants** across all concrete subclasses

```java
abstract class PositiveNumber {
    final double value;

    PositiveNumber(double value) {
        if (value <= 0)
            throw new IllegalArgumentException("Must be positive: " + value);
        this.value = value;   // validation guaranteed for ALL subclasses
    }

    abstract String unit();
}

class Kilogram extends PositiveNumber {
    Kilogram(double value) { super(value); }

    @Override
    String unit() { return "kg"; }
}
```

---

## 6. Abstract Classes vs Interfaces

This is one of the most common design decisions in Java. Both define contracts — but they serve different purposes.

```
Fig. 6 — Abstract Class vs Interface: Mental Model
┌────────────────────────────────────────────────────────┐
│              Abstract Class                            │
│                                                        │
│  "I am a partial implementation of a THING"            │
│   → shares state (fields)                              │
│   → shares behavior (concrete methods)                 │
│   → enforces a common constructor chain                │
│   → IS-A relationship                                  │
└────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────┐
│                   Interface                            │
│                                                        │
│  "I am a CAPABILITY or ROLE that something can play"   │
│   → no state (no instance fields)                      │
│   → defines a contract (what, not how)                 │
│   → CAN-DO relationship                                │
└────────────────────────────────────────────────────────┘
```

| Feature                          | Abstract Class              | Interface                          |
|----------------------------------|-----------------------------|------------------------------------|
| Instantiation                    | ✗ No                        | ✗ No                              |
| Instance fields                  | ✔ Yes                       | ✗ No (only `public static final`) |
| Constructors                     | ✔ Yes                       | ✗ No                              |
| Concrete methods                 | ✔ Yes                       | ✔ Yes (via `default`, Java 8+)    |
| Abstract methods                 | ✔ Yes                       | ✔ Yes (implicitly abstract)       |
| `static` methods                 | ✔ Yes                       | ✔ Yes (Java 8+)                   |
| Multiple inheritance             | ✗ One only (`extends`)      | ✔ Many (`implements A, B, C`)     |
| Access modifiers on methods      | Any                         | `public` only (implicitly)         |
| `final` methods                  | ✔ Yes                       | ✗ No                              |
| Constructor chaining (`super()`) | ✔ Yes                       | ✗ N/A                             |

### 6.1 When to Choose Which

```
Fig. 7 — Decision Flowchart: Abstract Class or Interface?
                        Start
                          │
         Do you need to share STATE (fields)?
                  │              │
                 YES             NO
                  │              │
     Use Abstract Class    Do you need CONSTRUCTORS
                           or initialization logic?
                                 │           │
                                YES          NO
                                 │           │
                      Use Abstract Class   Do multiple unrelated
                                           classes need this?
                                               │         │
                                              YES        NO
                                               │         │
                                          Interface  Either works;
                                                     prefer Interface
                                                     for flexibility
```

### 6.2 Using Both Together

The most powerful designs often use an **interface for the contract** and an **abstract class for partial implementation**:

```java
interface Drawable {
    void draw();
    void resize(double factor);
}

// Abstract class partially implements the interface
abstract class AbstractShape implements Drawable {
    String color;
    double x, y;

    AbstractShape(String color, double x, double y) {
        this.color = color;
        this.x = x; this.y = y;
    }

    @Override
    public void resize(double factor) {         // concrete — same for all shapes
        x *= factor;
        y *= factor;
    }

    // draw() still abstract — each shape draws differently
}

class Triangle extends AbstractShape {
    Triangle(String color, double x, double y) {
        super(color, x, y);
    }

    @Override
    public void draw() {
        System.out.println("Drawing triangle at (" + x + "," + y + ")");
    }
}
```

---

## 7. The Template Method Pattern

The **Template Method Pattern** is the canonical design pattern built on abstract classes. It uses a `final` concrete method to define an algorithm's **skeleton**, and abstract methods to define the **steps** that subclasses fill in.

```
Fig. 8 — Template Method Pattern Structure
┌─────────────────────────────────────────────┐
│           abstract class DataProcessor      │
│                                             │
│  + process() [final]  ← skeleton LOCKED     │
│      │                                      │
│      ├── readData()   [abstract]  ← step 1  │
│      ├── processData()[abstract]  ← step 2  │
│      └── writeResult()[concrete] ← step 3   │
└────────────────────┬────────────────────────┘
          ┌──────────┴──────────┐
          ▼                     ▼
┌──────────────────┐  ┌──────────────────────┐
│  CSVProcessor    │  │   JSONProcessor      │
│ readData() ✔     │  │  readData() ✔       │
│ processData() ✔  │  │  processData() ✔    │
└──────────────────┘  └──────────────────────┘
```

```java
abstract class DataProcessor {

    // Template method — skeleton is final, cannot be changed
    public final void process() {
        readData();
        processData();
        writeResult();
    }

    protected abstract void readData();
    protected abstract void processData();

    // Concrete step — same for all processors
    protected void writeResult() {
        System.out.println("Writing results to output...");
    }
}

class CSVProcessor extends DataProcessor {
    @Override
    protected void readData()    { System.out.println("Reading CSV file..."); }
    @Override
    protected void processData() { System.out.println("Parsing CSV rows..."); }
}

class JSONProcessor extends DataProcessor {
    @Override
    protected void readData()    { System.out.println("Reading JSON file..."); }
    @Override
    protected void processData() { System.out.println("Parsing JSON nodes..."); }
}

// Usage
DataProcessor p = new CSVProcessor();
p.process();
// Reading CSV file...
// Parsing CSV rows...
// Writing results to output...
```

The caller only ever interacts with `process()` — the concrete steps are encapsulated and interchangeable.

---

## 8. Real-World Use Cases

### 8.1 JDK — `java.io.InputStream`

```java
// Simplified from java.io.InputStream
public abstract class InputStream {

    // Abstract — every stream reads bytes differently
    public abstract int read() throws IOException;

    // Concrete — implemented once using read()
    public int read(byte[] b, int off, int len) throws IOException {
        // default implementation that calls read() in a loop
    }

    public void close() throws IOException { }
}

// FileInputStream, ByteArrayInputStream, etc. each extend this
// and only need to implement read() — everything else is inherited
```

### 8.2 JDK — `java.util.AbstractList`

```java
// AbstractList provides most of List's behavior
// You only need to implement get() and size()
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    public abstract E get(int index);
    public abstract int size();
    // add(), remove(), indexOf(), etc. all implemented in terms of get() and size()
}

class FixedList<E> extends AbstractList<E> {
    private final Object[] data;

    FixedList(Object[] data) { this.data = data; }

    @Override public E get(int i)  { return (E) data[i]; }
    @Override public int size()    { return data.length; }
}
```

### 8.3 Payment Gateway (Domain Example)

```java
abstract class PaymentGateway {
    private final String merchantId;

    PaymentGateway(String merchantId) {
        this.merchantId = merchantId;
    }

    // Template method
    public final boolean charge(double amount, String account) {
        if (!validateAmount(amount)) return false;
        boolean result = executeCharge(amount, account, merchantId);
        logTransaction(amount, account, result);
        return result;
    }

    protected abstract boolean validateAmount(double amount);
    protected abstract boolean executeCharge(double amount, String account, String mid);

    private void logTransaction(double amount, String account, boolean success) {
        System.out.printf("[%s] %s: %.2f for %s%n",
            merchantId, success ? "CHARGED" : "FAILED", amount, account);
    }
}

class EsewaGateway extends PaymentGateway {
    EsewaGateway() { super("ESEWA_MID_001"); }

    @Override
    protected boolean validateAmount(double a) { return a > 0 && a <= 100000; }

    @Override
    protected boolean executeCharge(double a, String acc, String mid) {
        System.out.println("Calling eSewa API...");
        return true;
    }
}
```

---

## 9. Common Mistakes & Pitfalls

### Mistake 1: Trying to Instantiate an Abstract Class

```java
abstract class Animal { }

Animal a = new Animal();   // ✗ Compile error
// Fix: instantiate a concrete subclass
Animal a = new Dog();      // ✔
```

---

### Mistake 2: Forgetting to Implement All Abstract Methods

```java
abstract class Shape {
    abstract double area();
    abstract double perimeter();
}

class Square extends Shape {
    double side;
    Square(double s) { this.side = s; }

    @Override
    double area() { return side * side; }

    // ✗ Compile error: perimeter() not implemented
    // Fix: implement perimeter(), or declare Square as abstract
}
```

---

### Mistake 3: Declaring Abstract Method `private`

```java
abstract class Base {
    private abstract void init();  // ✗ Compile error
    // private methods cannot be overridden — contradiction
}
```

---

### Mistake 4: Confusing "No Abstract Methods" with "Concrete Class"

```java
// This is valid — zero abstract methods, still abstract
abstract class Config {
    String host = "localhost";
    int port = 8080;
    void show() { System.out.println(host + ":" + port); }
}

Config c = new Config();  // ✗ Still fails — it's abstract
```

The `abstract` keyword on the class is what prevents instantiation — not the presence of abstract methods.

---

### Mistake 5: Using an Interface When You Need Shared State

```java
// ✗ Wrong tool — interfaces can't hold instance state
interface Animal {
    String name = "Unknown";  // implicitly public static final — shared, not per-object
    void speak();
}

// ✔ Right tool — abstract class holds per-object state
abstract class Animal {
    String name;              // each object has its own name
    Animal(String name) { this.name = name; }
    abstract void speak();
}
```

---

## 10. Summary Table

| Concept                          | Key Rule                                                                                  |
|----------------------------------|-------------------------------------------------------------------------------------------|
| Abstract class                   | Cannot be instantiated; must be extended to use                                           |
| Abstract method                  | No body; declared with `abstract`; must be overridden in first concrete subclass          |
| Class with abstract method       | Must itself be declared `abstract`                                                        |
| Abstract class without abstract methods | Valid; prevents instantiation without forcing any override                          |
| `abstract` + `final`             | Illegal — mutually exclusive on both class and method                                     |
| `abstract` + `private`           | Illegal on method — subclass can't see it to override                                     |
| `abstract` + `static`            | Illegal on method — static is compile-time; abstract needs runtime dispatch               |
| Constructors in abstract class   | Allowed and useful; called via `super()` from subclass constructors                       |
| Partial implementation           | Abstract subclass can implement some methods and add new abstract ones                    |
| Abstract class vs Interface      | Use abstract class for shared state/IS-A; use interface for capability/CAN-DO             |
| Template Method Pattern          | `final` method in abstract class defines skeleton; abstract methods define customizable steps |
| JDK examples                     | `InputStream`, `AbstractList`, `HttpServlet`, `AbstractMap`                               |

---
