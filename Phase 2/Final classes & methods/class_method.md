---

# Final Classes & Methods in Java

---

## Table of Contents

1. [Recap: The `final` Keyword](#1-recap-the-final-keyword)
2. [Final Methods](#2-final-methods)
3. [Final Classes](#3-final-classes)
4. [Why Immutability Matters — Design Intent](#4-why-immutability-matters--design-intent)
5. [Final vs. Abstract: A Direct Conflict](#5-final-vs-abstract-a-direct-conflict)
6. [Interaction with Inheritance & Interfaces](#6-interaction-with-inheritance--interfaces)
7. [Interaction with Static Methods (Method Hiding)](#7-interaction-with-static-methods-method-hiding)
8. [Real-World Use Cases](#8-real-world-use-cases)
9. [C++ vs Java Comparison](#9-c-vs-java-comparison)
10. [Common Mistakes & Pitfalls](#10-common-mistakes--pitfalls)
11. [Summary Table](#11-summary-table)

---

## 1. Recap: The `final` Keyword

The `final` keyword in Java is an **immutability/restriction signal**. Depending on where it is applied:

| Context          | Effect                                                             |
|------------------|--------------------------------------------------------------------|
| `final` variable | Value cannot be reassigned after initialization                    |
| `final` method   | Method cannot be **overridden** in any subclass                    |
| `final` class    | Class cannot be **subclassed** (extended) at all                   |

This file focuses entirely on **`final` methods** and **`final` classes** — two of the most architecturally significant uses of the keyword.

---

## 2. Final Methods

### 2.1 What It Means

A `final` method is a method whose **implementation is locked** — no subclass can provide its own version of it via `@Override`. The method can still be **called**, **inherited**, and **used**, but it cannot be **redefined**.

```
Fig. 1 — Final Method: Inheritance without Override
┌─────────────────────────────────┐
│           class Animal          │
│  ┌───────────────────────────┐  │
│  │  + breathe()  [FINAL]     │  │
│  │  + speak()                │  │
│  └───────────────────────────┘  │
└────────────────┬────────────────┘
                 │ extends
                 ▼
┌─────────────────────────────────┐
│            class Dog            │
│  ┌───────────────────────────┐  │
│  │  ✔ breathe() [inherited,  │  │
│  │    cannot override]       │  │
│  │  ✔ speak()   [overridden] │  │
│  └───────────────────────────┘  │
└─────────────────────────────────┘
```

### 2.2 Syntax

```java
class Animal {

    // Final method — locked implementation
    public final void breathe() {
        System.out.println("Inhale O2, exhale CO2.");
    }

    // Normal method — open for override
    public void speak() {
        System.out.println("...");
    }
}

class Dog extends Animal {

    // ✔ Allowed: overriding a non-final method
    @Override
    public void speak() {
        System.out.println("Woof!");
    }

    // ✗ COMPILE ERROR: cannot override final method
    // @Override
    // public void breathe() { ... }
}
```

> **Compiler error if you try to override:**
> `error: breathe() in Dog cannot override breathe() in Animal; overridden method is final`

### 2.3 Final Methods and Access Modifiers

A `final` method can have **any access modifier**. The two concepts are orthogonal:

```java
class BankAccount {

    // Anyone can call it, but no one can override it
    public final double calculateInterest(double principal, double rate) {
        return principal * rate / 100;
    }

    // Only subclasses can call it, but no one can override it
    protected final void auditLog(String action) {
        System.out.println("[AUDIT] " + action + " at " + System.currentTimeMillis());
    }

    // Only within class, and cannot be overridden
    private final void encryptPin(int pin) {
        // encryption logic
    }
}
```

> **Note:** `private` methods are implicitly final-like because they cannot be inherited at all — but declaring them `final` explicitly is allowed (though redundant).

### 2.4 Final Methods and `private`

A subtlety worth understanding:

```java
class Parent {
    private void secret() {           // NOT inherited
        System.out.println("Parent secret");
    }
}

class Child extends Parent {
    // This is NOT overriding — it's a brand-new method
    private void secret() {
        System.out.println("Child secret");
    }
}
```

- `private` methods are **not visible** to subclasses.
- A subclass defining a method with the same name is **not overriding** — it's creating a new, unrelated method.
- Therefore, `private final` is **redundant** (`private` already prevents override).

### 2.5 Final Methods Prevent "Fragile Base Class" Problem

This is the primary architectural reason for `final` methods:

```
Fig. 2 — Fragile Base Class Problem
Without final:
┌────────────────────────────────────────┐
│  BaseClass.doWork() calls validate()   │
│  Subclass overrides validate()         │
│  → Subclass breaks BaseClass invariant │
└────────────────────────────────────────┘

With final:
┌────────────────────────────────────────┐
│  BaseClass.doWork() calls validate()   │
│  validate() is final                   │
│  → Invariant always preserved          │
└────────────────────────────────────────┘
```

```java
class Template {

    // Template Method pattern — calls final helpers
    public final void process() {
        validate();    // Must not be changed by subclasses
        execute();     // Can be customized
        cleanup();     // Must not be changed by subclasses
    }

    private final void validate() {
        System.out.println("Validating inputs...");
    }

    protected void execute() {
        System.out.println("Default execution");
    }

    private final void cleanup() {
        System.out.println("Releasing resources...");
    }
}

class CustomProcess extends Template {
    @Override
    protected void execute() {
        System.out.println("Custom execution logic");
    }
    // Cannot touch validate() or cleanup() — invariants preserved
}
```

This is the **Template Method Design Pattern** — a major real-world use of `final` methods.

---

## 3. Final Classes

### 3.1 What It Means

A `final` class **cannot be extended** at all. No other class can use it as a superclass. This is a stronger guarantee than a `final` method — instead of locking one method, you lock the entire class hierarchy.

```
Fig. 3 — Final Class: No Inheritance Allowed
┌───────────────────────────────────┐
│      final class ImmutablePoint   │
│   - x: double                     │
│   - y: double                     │
│   + getX(): double                │
│   + getY(): double                │
└───────────────────────────────────┘
          │
          │  extends  ← ✗ COMPILE ERROR
          ▼
┌───────────────────────────────────┐
│       class Point3D               │   ← NOT ALLOWED
└───────────────────────────────────┘
```

### 3.2 Syntax

```java
public final class ImmutablePoint {
    private final double x;
    private final double y;

    public ImmutablePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

// ✗ COMPILE ERROR: cannot inherit from final ImmutablePoint
// class Point3D extends ImmutablePoint { ... }
```

> **Compiler error:**
> `error: cannot inherit from final ImmutablePoint`

### 3.3 The Canonical Example: `java.lang.String`

`String` is the most famous final class in all of Java:

```java
// java.lang.String source (simplified):
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    // ...
}
```

**Why is `String` final?**

```
Fig. 4 — Why String Must Be Final (Security Model)
┌────────────────────────────────────────────────────────┐
│  If String were NOT final:                             │
│                                                        │
│  class EvilString extends String {                     │
│      @Override                                         │
│      public boolean equals(Object o) {                 │
│          return true;  // always equal!                │
│      }                                                 │
│  }                                                     │
│                                                        │
│  Password check: password.equals(userInput)            │
│  → Could be bypassed if password is an EvilString      │
│                                                        │
│  ClassLoader trusts String class names                 │
│  → Malicious subclass could hijack class loading       │
└────────────────────────────────────────────────────────┘
```

Making `String` final guarantees:
- Its behavior is predictable everywhere in the JVM
- Security-sensitive APIs that rely on `String` cannot be subverted
- The JVM can optimize `String` aggressively (interning, compile-time constants)

### 3.4 Other Well-Known Final Classes in the JDK

| Class                     | Package            | Reason for Being Final                       |
|---------------------------|--------------------|----------------------------------------------|
| `String`                  | `java.lang`        | Security, predictability, JVM optimization   |
| `Integer`                 | `java.lang`        | Immutable value type; caching (-128 to 127)  |
| `Long`, `Double`, etc.    | `java.lang`        | Same as Integer                              |
| `Math`                    | `java.lang`        | Utility class; no state, no reason to extend |
| `LocalDate`               | `java.time`        | Immutable date; value semantics              |
| `LocalDateTime`           | `java.time`        | Same as LocalDate                            |
| `UUID`                    | `java.util`        | Value type; immutable identifier             |
| `Optional<T>`             | `java.util`        | Sealed value container                       |

```java
// You can USE Integer, but not extend it:
Integer a = 42;
Integer b = Integer.valueOf("100");
System.out.println(Integer.MAX_VALUE); // 2147483647

// ✗ Not allowed:
// class BiggerInteger extends Integer { ... }
```

---

## 4. Why Immutability Matters — Design Intent

### 4.1 The Immutable Object Pattern

`final` classes are the backbone of the **Immutable Object Pattern**. A truly immutable object satisfies:

```
Fig. 5 — Immutable Object Checklist
┌─────────────────────────────────────────────┐
│  ✔  Class declared final (no subclassing)   │
│  ✔  All fields private                      │
│  ✔  All fields final                        │
│  ✔  No setters                              │
│  ✔  Deep copies in constructor if needed    │
│  ✔  Deep copies in getters if needed        │
└─────────────────────────────────────────────┘
```

```java
public final class Money {
    private final long amount;      // in paisa (smallest unit)
    private final String currency;

    public Money(long amount, String currency) {
        if (amount < 0) throw new IllegalArgumentException("Negative money");
        this.amount = amount;
        this.currency = currency;
    }

    public long getAmount()     { return amount; }
    public String getCurrency() { return currency; }

    // Returns NEW object — does not mutate this
    public Money add(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch");
        return new Money(this.amount + other.amount, this.currency);
    }

    @Override
    public String toString() {
        return currency + " " + (amount / 100.0);
    }
}
```

### 4.2 Thread Safety Through Immutability

Immutable objects are **inherently thread-safe** — no synchronization needed:

```java
// Money is final and immutable
// Multiple threads can read the same Money object without locks
Money price = new Money(5000, "NPR");  // NPR 50.00

// Each thread gets its own result object; no race conditions
Thread t1 = new Thread(() -> System.out.println(price.add(new Money(1000, "NPR"))));
Thread t2 = new Thread(() -> System.out.println(price.add(new Money(2000, "NPR"))));

t1.start(); t2.start();
// Safe — price never changes
```

---

## 5. Final vs. Abstract: A Direct Conflict

`abstract` and `final` are **mutually exclusive** on a class:

| Modifier     | Meaning                          |
|--------------|----------------------------------|
| `abstract`   | *Must* be subclassed             |
| `final`      | *Cannot* be subclassed           |

```java
// ✗ COMPILE ERROR: illegal combination of modifiers: abstract and final
abstract final class Contradiction { }
```

Similarly, `abstract` and `final` are mutually exclusive on a **method**:

```java
abstract class Shape {
    // ✗ COMPILE ERROR: illegal combination: abstract and final
    // abstract final double area();

    // ✔ These are fine independently:
    abstract double area();            // must be overridden
    final void describe() {            // cannot be overridden
        System.out.println("I am a shape with area: " + area());
    }
}
```

> Notice: `describe()` is `final` but calls `area()` which is `abstract`. This is the **Template Method** pattern again — the algorithm skeleton is locked, the step is delegated.

```
Fig. 6 — Abstract + Final in the Same Class (Template Method Pattern)
┌──────────────────────────────────────────┐
│         abstract class Shape             │
│                                          │
│  final describe()  ← LOCKED              │
│       │ calls                            │
│       ▼                                  │
│  abstract area()   ← MUST BE OVERRIDDEN  │
└──────────────────────────────────────────┘
         │ extends
         ▼
┌──────────────────────────────────────────┐
│         class Circle extends Shape       │
│                                          │
│  ✔ area() — implemented here            │
│  ✗ describe() — cannot be overridden    │
└──────────────────────────────────────────┘
```

---

## 6. Interaction with Inheritance & Interfaces

> *(Cross-reference: **Inheritance** and **Interfaces** notes for full context.)*

### 6.1 Final Class Can Still Implement Interfaces

A `final` class can **implement interfaces** — that is not inheritance of implementation, it's a contract:

```java
interface Printable {
    void print();
}

interface Serializable {
    byte[] serialize();
}

// Final class, but implements interfaces freely
public final class Report implements Printable, Serializable {

    private final String content;

    public Report(String content) {
        this.content = content;
    }

    @Override
    public void print() {
        System.out.println(content);
    }

    @Override
    public byte[] serialize() {
        return content.getBytes();
    }
}
```

```
Fig. 7 — Final Class and Interfaces
      «interface»              «interface»
       Printable               Serializable
           │                       │
           └───────────┬───────────┘
                       │ implements
              ┌────────▼────────┐
              │  final Report   │
              │  (no subclass)  │
              └─────────────────┘
```

### 6.2 Inheriting Final Methods Through a Chain

`final` methods propagate through inheritance — once declared final, they are locked for all descendants:

```java
class A {
    public final void method() {
        System.out.println("A.method");
    }
}

class B extends A {
    // Cannot override method() — it's final in A
    // But can add new methods:
    public void newMethod() {
        System.out.println("B.newMethod");
    }
}

class C extends B {
    // Still cannot override A.method() — final is permanent
}
```

### 6.3 Default Methods in Interfaces and Final

Interface `default` methods can be overridden in implementing classes — **unless** that class is `final`, in which case the default method is simply used as-is (it cannot be overridden later since the class is final):

```java
interface Greetable {
    default void greet() {
        System.out.println("Hello!");
    }
}

// Final class uses the default, cannot be overridden further
public final class Robot implements Greetable {
    // greet() is inherited from interface — no override possible by subclasses
    // (there are no subclasses anyway)
}
```

---

## 7. Interaction with Static Methods (Method Hiding)

> This is a subtle but important distinction — `final` applies to **overriding**, not **hiding**.

In Java:
- **Instance methods** → can be overridden (unless `final`)
- **Static methods** → cannot be overridden; they are **hidden** if redeclared in a subclass

```java
class Parent {
    public final void instanceMethod() {        // Cannot be overridden
        System.out.println("Parent instance");
    }

    public static void staticMethod() {         // Cannot be "overridden" — hidden instead
        System.out.println("Parent static");
    }

    public final static void finalStaticMethod() {  // Redundant but valid
        System.out.println("Parent final static");
    }
}

class Child extends Parent {
    // ✗ Cannot override instanceMethod() — it's final

    // ✔ This HIDES Parent.staticMethod() (not overriding)
    public static void staticMethod() {
        System.out.println("Child static");
    }

    // ✗ Cannot hide finalStaticMethod() — it's final
    // public static void finalStaticMethod() { }
}

public class Test {
    public static void main(String[] args) {
        Parent obj = new Child();

        // Dynamic dispatch — calls Child's override (if it existed)
        obj.instanceMethod(); // → "Parent instance" (final, no override)

        // Static: resolved at compile time by reference type
        obj.staticMethod();   // → "Parent static" (not polymorphic!)

        Child c = new Child();
        c.staticMethod();     // → "Child static" (calls Child's hidden version)
    }
}
```

```
Fig. 8 — Override vs. Hide
┌──────────────────────────────────────────────────────────┐
│                     Method Dispatch                      │
│                                                          │
│  Instance method (non-final):                            │
│    Resolved at RUNTIME based on actual object type       │
│    → Polymorphism                                        │
│                                                          │
│  Instance method (final):                                │
│    Resolved at RUNTIME, but only one version exists      │
│    → No polymorphism possible (locked)                   │
│                                                          │
│  Static method:                                          │
│    Resolved at COMPILE TIME based on reference type      │
│    → No polymorphism (hiding, not overriding)            │
└──────────────────────────────────────────────────────────┘
```

---

## 8. Real-World Use Cases

### 8.1 Security-Critical Classes

```java
// Simulating a secure token — should not be subclassable
public final class AuthToken {
    private final String value;
    private final long expiryEpoch;

    public AuthToken(String value, long ttlMillis) {
        this.value = value;
        this.expiryEpoch = System.currentTimeMillis() + ttlMillis;
    }

    public boolean isValid() {
        return System.currentTimeMillis() < expiryEpoch;
    }

    public String getValue() {
        return isValid() ? value : null;
    }
}
```

### 8.2 Value Objects / Domain Primitives

```java
public final class EmailAddress {
    private final String address;

    public EmailAddress(String address) {
        if (!address.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$"))
            throw new IllegalArgumentException("Invalid email: " + address);
        this.address = address.toLowerCase();
    }

    public String getAddress() { return address; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmailAddress)) return false;
        return address.equals(((EmailAddress) o).address);
    }

    @Override
    public int hashCode() { return address.hashCode(); }

    @Override
    public String toString() { return address; }
}
```

### 8.3 Strategy Pattern with Final Core Logic

```java
abstract class PaymentProcessor {

    // Core flow is locked — cannot be changed
    public final void processPayment(double amount) {
        if (!validateAmount(amount)) {
            throw new IllegalArgumentException("Invalid amount: " + amount);
        }
        charge(amount);
        sendReceipt(amount);
    }

    // Specific steps are overridable
    protected abstract boolean validateAmount(double amount);
    protected abstract void charge(double amount);

    // Receipt format is fixed
    private final void sendReceipt(double amount) {
        System.out.printf("Receipt: Charged NPR %.2f%n", amount);
    }
}

class EsewaProcessor extends PaymentProcessor {
    @Override
    protected boolean validateAmount(double amount) {
        return amount > 0 && amount <= 100000;  // eSewa limit
    }

    @Override
    protected void charge(double amount) {
        System.out.println("Charging via eSewa: " + amount);
    }
}

class KhaltiProcessor extends PaymentProcessor {
    @Override
    protected boolean validateAmount(double amount) {
        return amount > 0 && amount <= 200000;  // Khalti limit
    }

    @Override
    protected void charge(double amount) {
        System.out.println("Charging via Khalti: " + amount);
    }
}
```

### 8.4 Utility / Helper Classes

```java
// No state, no reason to instantiate, no reason to extend
public final class MathUtils {
    private MathUtils() {}  // Prevent instantiation

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0) return false;
        return true;
    }
}
```

---

## 9. C++ vs Java Comparison

| Feature                             | C++                                               | Java                                              |
|-------------------------------------|---------------------------------------------------|---------------------------------------------------|
| Prevent method override             | `final` keyword (C++11): `void foo() final;`      | `final` keyword: `public final void foo()`        |
| Prevent class inheritance           | `final` keyword (C++11): `class Foo final {}`     | `final` keyword: `public final class Foo {}`      |
| Default for methods                 | Non-virtual by default (cannot be overridden unless `virtual`) | All methods are virtual by default (can be overridden) |
| Making a method non-overridable     | Simply don't declare it `virtual` (default)       | Must explicitly use `final`                       |
| `const` vs `final`                  | `const` on member functions prevents mutation of `this` | No equivalent; Java uses `final` fields instead  |
| Immutable class idiom               | `const` member variables + deleted copy assignment | `final` class + `final` private fields + no setters |
| Abstract + final conflict           | Same conflict exists in C++11                     | Compile error for `abstract final`                |
| `String` equivalence                | `std::string` is NOT final; can be subclassed     | `java.lang.String` IS final                       |
| Static method behavior              | No `virtual` + `static` combination; similar hiding | Static methods hide, not override                 |
| Override keyword                    | `override` is optional (hint to compiler)         | `@Override` is optional but strongly recommended |

### Key Insight for C++ Programmers:

In C++, methods are **non-virtual by default** — you must opt IN to polymorphism with `virtual`. This means most C++ methods are already "final-like" unless you deliberately make them virtual.

In Java, all instance methods are **virtual by default** — you must opt OUT of polymorphism with `final`. This is a fundamental philosophical difference:

```
Fig. 9 — C++ vs Java Default Method Behavior
┌─────────────────────────────────────────────────────────┐
│                         C++                             │
│  method → non-virtual (non-overridable) by default      │
│  virtual method → overridable                           │
│  virtual method + final → locked again (C++11)          │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│                        Java                             │
│  method → virtual (overridable) by default              │
│  final method → locked (non-overridable)                │
└─────────────────────────────────────────────────────────┘
```

---

## 10. Common Mistakes & Pitfalls

### Mistake 1: Trying to Override a Final Method

```java
class Base {
    public final void display() { System.out.println("Base"); }
}

class Derived extends Derived {
    @Override
    public void display() { ... }  // ✗ Compile error
}
```
**Fix:** Remove `final` from `Base.display()` if overriding is intended by design.

---

### Mistake 2: Confusing `private` with `final`

```java
class A {
    private void helper() { System.out.println("A"); }
}

class B extends A {
    private void helper() { System.out.println("B"); }  // New method, NOT override
}

A obj = new B();
// obj.helper(); ← Won't compile — helper() is private to A
```
**Insight:** `private` methods are invisible to subclasses. The subclass's `helper()` is a completely separate method.

---

### Mistake 3: Thinking Final Classes Can't Have Inheritance From Above

```java
// Legal: final class inheriting from a non-final class
public final class MyArrayList extends java.util.ArrayList<String> {
    // ArrayList is NOT final, so we CAN extend it
    // But no one can extend MyArrayList further
}
```

---

### Mistake 4: Forgetting Deep Immutability

```java
public final class BadImmutable {
    private final List<String> items;

    public BadImmutable(List<String> items) {
        this.items = items;  // ✗ Stores reference — caller can mutate!
    }

    public List<String> getItems() {
        return items;  // ✗ Returns mutable reference!
    }
}

// Fix — defensive copies:
public final class GoodImmutable {
    private final List<String> items;

    public GoodImmutable(List<String> items) {
        this.items = List.copyOf(items);         // ✔ Defensive copy in
    }

    public List<String> getItems() {
        return Collections.unmodifiableList(items); // ✔ Safe view out
    }
}
```

> *(Connects to your **Java Collections** notes — `List.copyOf()` returns an unmodifiable list.)*

---

### Mistake 5: Making Everything Final Prematurely

`final` is a **design commitment**. Once a class is published as `final` in a library, removing `final` later is a backward-compatible change — but if users have already been forced to use composition instead of inheritance because of it, the design choices are locked in. Use `final` **intentionally**, not by default.

---

## 11. Summary Table

| Concept                          | Key Rule                                                               | Example in JDK              |
|----------------------------------|------------------------------------------------------------------------|-----------------------------|
| `final` method                   | Cannot be overridden by any subclass                                   | `Thread.start()`            |
| `final` class                    | Cannot be extended (subclassed) at all                                 | `String`, `Integer`         |
| `final` + `abstract` method      | **Illegal** — mutually exclusive                                       | Compile error               |
| `final` + `abstract` class       | **Illegal** — mutually exclusive                                       | Compile error               |
| `final` class + implements       | Legal — can implement interfaces, just not be extended                 | `String implements CharSequence` |
| `private` vs `final` method      | `private` → not inherited; `final` → inherited but not overridable     | Both restrict override      |
| `static` + `final`               | Legal, redundant: static methods already can't be overridden (only hidden) | `Math.PI` (field), utility methods |
| `final` class for immutability   | Combine with `final` fields + no setters + defensive copies            | `LocalDate`, `Money`        |
| Template Method Pattern          | `final` method calls `abstract` methods — locks skeleton, opens steps  | `HttpServlet.service()`     |
| C++ analogy for `final` class    | C++11 `class Foo final {}`                                             | —                           |
| C++ analogy for `final` method   | Non-virtual method (default in C++) or `virtual foo() final` (C++11)  | —                           |
| Thread safety                    | Immutable (`final` class + `final` fields) objects are inherently safe | `String`, `Integer`         |

---

> **Connections to previous topics:**
> - **Final Modifier** notes → `final` variables and blank finals
> - **Inheritance** notes → how `final` restricts the inheritance hierarchy
> - **OOP Principles** notes → abstract classes and the abstract/final conflict
> - **Interfaces** notes → final classes still implement interfaces freely
> - **Static Modifier** notes → static method hiding vs. instance method overriding
> - **Access Modifiers** notes → `private` and its relationship with overriding
> - **Java Collections** notes → `List.copyOf()` for defensive immutability

---
