# 🧱 Classes & Objects in Java


---

## 📌 What is a Class?

A **class** is a **blueprint or template** from which objects are created. It defines the **properties (fields)** and **behaviors (methods)** that the objects of that class will have.

> Think of a class as a **cookie cutter** — the cutter is the class, the cookies are the objects.

```
┌──────────────────────────────────────────┐
│              CLASS (Blueprint)           │
│                                          │
│   Fields (attributes / properties)      │
│   ├── String name                        │
│   ├── int age                            │
│   └── double salary                     │
│                                          │
│   Methods (behaviors)                   │
│   ├── void display()                    │
│   └── double getSalary()                │
└──────────────────────────────────────────┘
         │  instantiate
         ▼
┌──────────────────────────────────────────┐
│           OBJECT (Instance)              │
│   name   = "Aasii"                       │
│   age    = 21                            │
│   salary = 50000.0                       │
└──────────────────────────────────────────┘
```

> **Fig. 1 — Class as Blueprint, Object as Instance**

---

## 🔷 Defining a Class in Java

```java
class ClassName {
    // fields (properties)
    dataType fieldName;

    // methods (behaviors)
    returnType methodName() {
        // body
    }
}
```

### Example

```java
class Student {
    String name;      // field
    int rollNo;       // field

    void display() {  // method
        System.out.println(name + " - " + rollNo);
    }
}
```

> 🆚 **C++ vs Java** — In C++, you use `;` after the closing `}` of a class definition. In Java, **no semicolon** is needed after `}`.

---

## 🔶 What is an Object?

An **object** is a **real-world instance** of a class. It has:

| Property | Description |
|----------|-------------|
| **State** | Data stored in fields (e.g., name, age) |
| **Behavior** | Actions via methods (e.g., display(), run()) |
| **Identity** | Unique memory address that distinguishes it |

---

## 🏗️ Creating an Object

```java
ClassName objectName = new ClassName();
```

```java
Student s1 = new Student();   // object creation
s1.name   = "Aasii";          // accessing field
s1.rollNo = 101;
s1.display();                 // calling method
```

```
┌─────────────────────────────────────────────────────────┐
│                   Memory Layout                         │
│                                                         │
│   Stack                      Heap                       │
│   ┌──────────┐               ┌─────────────────────┐    │
│   │  s1  ───┼──────────────►│  Student Object     │    │
│   └──────────┘               │  name   = "Aasii"  │    │
│                              │  rollNo = 101       │    │
│                              └─────────────────────┘    │
└─────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Stack & Heap Memory on Object Creation**

> 🆚 **C++ vs Java** — In C++, objects can be created on the **stack** (`Student s1;`) or heap (`new Student()`). In Java, objects are **always created on the heap** using `new`. The reference variable lives on the stack.

---

## 🧩 Components of a Class

```
┌───────────────────────────────────────────┐
│                  CLASS                    │
│                                           │
│  ┌─────────────┐   ┌───────────────────┐  │
│  │   Fields    │   │    Methods        │  │
│  │ (Variables) │   │  (Functions)      │  │
│  └─────────────┘   └───────────────────┘  │
│  ┌─────────────┐   ┌───────────────────┐  │
│  │Constructors │   │   Nested Classes  │  │
│  └─────────────┘   └───────────────────┘  │
│  ┌─────────────┐   ┌───────────────────┐  │
│  │   Blocks    │   │   Access Modifiers│  │
│  │(static/init)│   │ pub/pri/pro/def   │  │
│  └─────────────┘   └───────────────────┘  │
└───────────────────────────────────────────┘
```

> **Fig. 3 — Components of a Java Class**

---

## 🔑 Access Modifiers

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|----------|:---------:|:------------:|:--------:|:----------:|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `default` *(no keyword)* | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

> 🆚 **C++ vs Java** — In C++, access modifiers are declared as **blocks** (`public:`, `private:`). In Java, **each member** has its own modifier written individually.

---

## 📦 Types of Classes in Java

### 1. 🔹 Concrete Class
A **normal class** with full implementation of all methods.

```java
class Car {
    void drive() { System.out.println("Driving..."); }
}
```

---

### 2. 🔹 Abstract Class
A class that **cannot be instantiated** — must be extended. Can have both abstract and concrete methods.

```java
abstract class Shape {
    abstract void draw();       // no body — must override
    void color() { System.out.println("Colored"); }
}
```

> 🆚 **C++ vs Java** — C++ uses **pure virtual functions** (`virtual void draw() = 0`). Java uses the `abstract` keyword — cleaner and more explicit.

---

### 3. 🔹 Final Class
A class that **cannot be subclassed / extended**.

```java
final class Constants {
    static final double PI = 3.14159;
}
```

> Example: `String` class in Java is `final`.

---

### 4. 🔹 Singleton Class
Only **one object** can be created from this class.

```java
class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }
}
```

---

### 5. 🔹 Inner / Nested Class
A class defined **inside another class**.

```java
class Outer {
    class Inner {
        void show() { System.out.println("Inner class"); }
    }
}
```

---

### 6. 🔹 Anonymous Class
A class with **no name**, defined and instantiated at the same time.

```java
Shape s = new Shape() {
    void draw() { System.out.println("Anonymous draw"); }
};
```

---

## 🏷️ Class Types Overview

```
┌──────────────────────────────────────────────────────┐
│                   Types of Classes                   │
│                                                      │
│   ┌────────────┐   ┌────────────┐  ┌─────────────┐  │
│   │  Concrete  │   │  Abstract  │  │    Final    │  │
│   │  (normal)  │   │(can't inst)│  │(can't extend│  │
│   └────────────┘   └────────────┘  └─────────────┘  │
│                                                      │
│   ┌────────────┐   ┌────────────┐  ┌─────────────┐  │
│   │ Singleton  │   │   Inner/   │  │  Anonymous  │  │
│   │(1 instance)│   │   Nested   │  │  (no name)  │  │
│   └────────────┘   └────────────┘  └─────────────┘  │
└──────────────────────────────────────────────────────┘
```

> **Fig. 4 — Types of Classes in Java**

---

## 🔄 `this` Keyword

`this` refers to the **current object** of the class.

```java
class Student {
    String name;
    Student(String name) {
        this.name = name;   // differentiates field from parameter
    }
}
```

> 🆚 **C++ vs Java** — Both use `this`, but in Java `this` is a **reference** (not a pointer). So you write `this.name`, not `this->name` like in C++.

---

## ⚡ Static Members

`static` members belong to the **class itself**, not to any object.

```java
class Counter {
    static int count = 0;   // shared across all objects

    Counter() { count++; }

    static void showCount() {
        System.out.println("Count: " + count);
    }
}
```

```
┌──────────────────────────────────────────────────────┐
│                   Static vs Instance                 │
│                                                      │
│   Static Field      → shared by ALL objects          │
│   Instance Field    → unique to EACH object          │
│                                                      │
│   obj1.name = "A"   obj2.name = "B"   (different)   │
│   Counter.count = 3  ←  shared by obj1 & obj2       │
└──────────────────────────────────────────────────────┘
```

> **Fig. 5 — Static vs Instance Members**

---

## 🗑️ Garbage Collection

In Java, you do **not** manually destroy objects. The **Garbage Collector (GC)** automatically reclaims memory of objects no longer referenced.

```java
Student s1 = new Student();
s1 = null;   // object is now eligible for GC
```

> 🆚 **C++ vs Java** — C++ requires **manual memory management** using `delete`. Java handles this automatically — no `delete`, no destructors needed (though `finalize()` existed, it's deprecated in Java 9+).

---

## 📋 Key Differences — C++ vs Java (Classes & Objects)

| Feature | C++ | Java |
|---------|-----|------|
| Semicolon after class `}` | ✅ Required | ❌ Not needed |
| Object on stack | ✅ Allowed | ❌ Always on heap |
| `this` | Pointer (`this->`) | Reference (`this.`) |
| Memory management | Manual (`delete`) | Automatic (GC) |
| Multiple inheritance | ✅ Supported | ❌ Via interfaces only |
| Access modifier syntax | Block-style | Per-member |
| Destructor | ✅ (`~ClassName()`) | ❌ No destructor (use GC) |
| `struct` vs `class` | Different defaults | No `struct` in Java |

---

## 💡 Summary

| Concept | Key Point |
|---------|-----------|
| **Class** | Blueprint / template for objects |
| **Object** | Instance of a class — lives on heap |
| **Fields** | Store state of object |
| **Methods** | Define behavior of object |
| **`new`** | Keyword to create objects in Java |
| **`this`** | Refers to current object (reference, not pointer) |
| **`static`** | Belongs to class, shared by all objects |
| **GC** | Java handles memory automatically |

---
