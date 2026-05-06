# 🔐 Final Modifier in Java


---

## 📌 What is `final`?

The `final` keyword means **"cannot be changed"**. It can be applied to variables, methods, and classes — each with a different effect.

| Applied To | Effect |
|------------|--------|
| `final` variable | Value cannot be reassigned (constant) |
| `final` method | Cannot be overridden in subclass |
| `final` class | Cannot be extended (subclassed) |

---

## 🔹 1. Final Variable (Constant)

Once assigned, the value **cannot be changed**.

```java
final int MAX = 100;
MAX = 200;   // ❌ compile error — cannot reassign
```

### Types of Final Variables

| Type | Description | Example |
|------|-------------|---------|
| **Final local** | Constant inside a method | `final int x = 10;` |
| **Final instance** | Must be initialized at declaration or in constructor | `final String name;` |
| **Final static** | Class-level constant, by convention ALL_CAPS | `static final double PI = 3.14;` |

```java
class Circle {
    static final double PI = 3.14159;   // constant — shared, unchangeable
    final int radius;                   // must be set in constructor

    Circle(int radius) {
        this.radius = radius;           // ✅ only assigned once
    }
}
```

> 💡 `static final` together = a **true constant** (like `Math.PI` in Java's standard library).

---

## 🔹 2. Final Method

A `final` method **cannot be overridden** by any subclass.

```java
class Vehicle {
    final void start() {
        System.out.println("Vehicle starting...");
    }
}

class Car extends Vehicle {
    void start() { }   // ❌ compile error — cannot override final method
}
```

> 💡 Use `final` methods when the behavior **must not be altered** by subclasses — e.g. security-critical logic.

---

## 🔹 3. Final Class

A `final` class **cannot be subclassed / extended** at all.

```java
final class Constants {
    static final double GRAVITY = 9.8;
}

class MyConstants extends Constants { }  // ❌ compile error
```

### Well-known `final` classes in Java

| Class | Package |
|-------|---------|
| `String` | `java.lang` |
| `Integer` | `java.lang` |
| `Math` | `java.lang` |
| `System` | `java.lang` |

> 💡 `String` is `final` to ensure **immutability and security** — no subclass can alter its behavior.

---

## ⚠️ `final` vs `finally` vs `finalize`

These three are **completely different** — a common source of confusion:

| Keyword | Type | Purpose |
|---------|------|---------|
| `final` | Modifier | Restricts change/override/inheritance |
| `finally` | Block | Always runs after `try-catch` (exception handling) |
| `finalize()` | Method | Called by GC before object is destroyed *(deprecated Java 9+)* |

---

## 🔄 What `final` Prevents

```
final variable  →  no reassignment
final method    →  no overriding
final class     →  no extending
```

---

## 🆚 C++ vs Java

| Feature | C++ | Java |
|---------|-----|------|
| Constant variable | `const` | `final` |
| Prevent overriding | `virtual f() final` (C++11) | `final` on method |
| Prevent inheritance | `class X final {}` (C++11) | `final` on class |
| Compile-time constant | `constexpr` | `static final` |

> 🆚 **Key Difference** — C++ uses `const` for variables and added `final` for classes/methods only in **C++11**. Java has used `final` for all three purposes since the beginning.

---

## 💡 Summary

| `final` on | Means |
|------------|-------|
| Variable | Constant — value fixed after assignment |
| Method | Locked behavior — subclass cannot override |
| Class | Closed for inheritance — no subclasses allowed |

> ✅ Use `final` to enforce **immutability**, **security**, and **design intent**.

---
