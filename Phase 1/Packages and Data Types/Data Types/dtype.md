# 🔢 Data Types in Java


---

## 📌 Overview

A **data type** defines the **type of value** a variable can hold and the **operations** that can be performed on it.

```
┌─────────────────────────────────────────┐
│            Java Data Types              │
│                                         │
│        ┌──────────────────────┐         │
│        │    Primitive Types   │         │
│        │  (built-in, 8 types) │         │
│        └──────────────────────┘         │
│        ┌──────────────────────┐         │
│        │   Non-Primitive      │         │
│        │  (Reference Types)   │         │
│        └──────────────────────┘         │
└─────────────────────────────────────────┘
```

---

## 1. 🔹 Primitive Data Types

Java has **8 primitive types** — stored directly in **stack memory**.

| Type | Size | Default | Range | Example |
|------|------|---------|-------|---------|
| `byte` | 1 byte | 0 | -128 to 127 | `byte b = 100;` |
| `short` | 2 bytes | 0 | -32,768 to 32,767 | `short s = 500;` |
| `int` | 4 bytes | 0 | -2³¹ to 2³¹-1 | `int i = 1000;` |
| `long` | 8 bytes | 0L | -2⁶³ to 2⁶³-1 | `long l = 99L;` |
| `float` | 4 bytes | 0.0f | ~7 decimal digits | `float f = 3.14f;` |
| `double` | 8 bytes | 0.0d | ~15 decimal digits | `double d = 3.14;` |
| `char` | 2 bytes | `\u0000` | 0 to 65,535 (Unicode) | `char c = 'A';` |
| `boolean` | 1 bit | `false` | `true` / `false` | `boolean flag = true;` |

> 🆚 **C++ vs Java** — In C++, sizes of types like `int` and `long` are **platform-dependent**. In Java, sizes are **fixed regardless of platform** — guaranteeing portability.

> 💡 `char` in Java is **2 bytes** (Unicode/UTF-16) vs **1 byte** in C++ (ASCII).

---

### Integer Types — Size Hierarchy

```
  byte   <   short   <   int   <   long
  1 byte     2 bytes     4 bytes    8 bytes
  (small)                          (large)
```

### Floating Point Types

```
  float  (4 bytes, ~7 digits precision)   → use suffix f  e.g. 3.14f
  double (8 bytes, ~15 digits precision)  → default decimal type
```

> 💡 Always prefer `double` over `float` unless memory is a strict constraint.

---

## 2. 🔷 Non-Primitive (Reference) Types

Reference types store a **reference (address)** to the object in **heap memory** — not the value itself.

| Type | Description | Example |
|------|-------------|---------|
| `String` | Sequence of characters | `String name = "Aasii";` |
| `Array` | Fixed-size collection of same type | `int[] nums = {1, 2, 3};` |
| `Class` | User-defined blueprint | `Student s = new Student();` |
| `Interface` | Abstract type contract | `Runnable r = ...` |

```
┌────────────────────────────────────────────────────┐
│          Primitive vs Reference in Memory          │
│                                                    │
│   Stack                        Heap                │
│   ┌──────────────┐             ┌────────────────┐  │
│   │ int x = 10   │             │                │  │
│   │  x  →  10    │  (direct)   │                │  │
│   ├──────────────┤             │                │  │
│   │ String s     │             │  "Aasii"       │  │
│   │  s  →  ──────────────────► │  (object)      │  │
│   └──────────────┘             └────────────────┘  │
└────────────────────────────────────────────────────┘
```

> **Fig. 1 — Primitive (stack) vs Reference (heap) Memory**

---

## 🔄 Type Casting

### Widening (Implicit) — automatic, no data loss

```java
int i = 100;
long l = i;      // int → long  ✅ automatic
double d = i;    // int → double ✅ automatic
```

```
byte → short → int → long → float → double
                               (widening direction →)
```

### Narrowing (Explicit) — manual, may lose data

```java
double d = 9.99;
int i = (int) d;   // i = 9 — decimal part lost ⚠️
```

> 🆚 **C++ vs Java** — Both support implicit widening and explicit narrowing. Java is **stricter** — narrowing always requires an explicit cast; C++ sometimes allows implicit narrowing (which can silently lose data).

---

## 📦 Wrapper Classes

Every primitive type has a corresponding **Wrapper Class** — used when an object is needed (e.g., in collections).

| Primitive | Wrapper Class |
|-----------|--------------|
| `byte` | `Byte` |
| `short` | `Short` |
| `int` | `Integer` |
| `long` | `Long` |
| `float` | `Float` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

```java
int x = 10;
Integer obj = x;        // autoboxing   (primitive → object)
int y = obj;            // unboxing     (object → primitive)
```

> 💡 **Autoboxing / Unboxing** — Java automatically converts between primitives and wrapper classes since Java 5. C++ has no direct equivalent.

---

## 💡 Summary

| Category | Stored In | Examples |
|----------|-----------|---------|
| Primitive | Stack | `int`, `double`, `char`, `boolean` |
| Reference | Heap | `String`, arrays, objects |
| Wrapper | Heap | `Integer`, `Double`, `Character` |

> Use **primitives** for performance. Use **wrapper classes** when working with collections or generics.

---
