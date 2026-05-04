# 🔨 Constructors in Java


---

## 📌 What is a Constructor?

A **constructor** is a special method that is **automatically called when an object is created**. It is used to **initialize the object's fields** with default or user-supplied values.

```
┌──────────────────────────────────────────────────────┐
│                  Object Creation Flow                │
│                                                      │
│   Student s1 = new Student("Aasii", 21);            │
│                     │                                │
│                     ▼                                │
│          ┌─────────────────────┐                     │
│          │    Constructor      │                     │
│          │  Student(name, age) │ ← called instantly  │
│          └──────────┬──────────┘                     │
│                     │                                │
│                     ▼                                │
│          ┌─────────────────────┐                     │
│          │   Object in Heap    │                     │
│          │  name = "Aasii"     │                     │
│          │  age  = 21          │                     │
│          └─────────────────────┘                     │
└──────────────────────────────────────────────────────┘
```

> **Fig. 1 — Constructor Call Flow on Object Creation**

---

## 📐 Rules for Constructors in Java

| Rule | Detail |
|------|--------|
| Same name as class | Must match the class name exactly |
| No return type | Not even `void` |
| Called automatically | Invoked when `new` is used |
| Can be overloaded | Multiple constructors with different parameters |
| Cannot be `static` | Belongs to the object, not the class |
| Cannot be `final` or `abstract` | These keywords are not allowed |

> 🆚 **C++ vs Java** — Same basic rules apply in both. However, in C++ a constructor can have a **member initializer list** (`: field(value)`). Java does **not** support initializer lists — all initialization is done inside the constructor body.

---

## 🧩 Types of Constructors

```
┌────────────────────────────────────────────────────────┐
│                  Types of Constructors                 │
│                                                        │
│   ┌──────────────────┐     ┌────────────────────────┐  │
│   │  Default         │     │  Parameterized         │  │
│   │  Constructor     │     │  Constructor           │  │
│   │  (no args)       │     │  (with args)           │  │
│   └──────────────────┘     └────────────────────────┘  │
│                                                        │
│             ┌──────────────────────┐                   │
│             │   Copy Constructor   │                   │
│             │  (copies an object)  │                   │
│             └──────────────────────┘                   │
└────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Types of Constructors**

---

### 1. 🔹 Default Constructor

A constructor with **no parameters**. If you don't define any constructor, the Java compiler **automatically provides** one.

```java
class Student {
    String name;
    int age;

    // Default Constructor
    Student() {
        name = "Unknown";
        age  = 0;
    }
}

// Usage
Student s1 = new Student();   // calls default constructor
```

> 🆚 **C++ vs Java:**
> - In **C++**, the compiler also provides a default constructor if none is defined.
> - In **Java**, the compiler-provided default constructor **always calls `super()`** (the parent class constructor) as its first statement automatically.
> - If you define **any** constructor in Java (or C++), the compiler stops providing the default one.

---

### 2. 🔹 Parameterized Constructor

A constructor that **accepts arguments** to initialize fields with custom values.

```java
class Student {
    String name;
    int age;

    // Parameterized Constructor
    Student(String name, int age) {
        this.name = name;
        this.age  = age;
    }
}

// Usage
Student s1 = new Student("Aasii", 21);
Student s2 = new Student("Ram",   19);
```

```
┌──────────────────────────────────────────────────────┐
│             Parameterized Constructor                │
│                                                      │
│   new Student("Aasii", 21)                           │
│          │                                           │
│          ▼                                           │
│   Student(String name, int age)                      │
│        this.name = "Aasii"  ──► object.name          │
│        this.age  =  21      ──► object.age            │
└──────────────────────────────────────────────────────┘
```

> **Fig. 3 — Parameterized Constructor Flow**

---

### 3. 🔹 Copy Constructor

Creates a **new object as a copy** of an existing object.

> 🆚 **C++ vs Java — Important Difference!**
> - **C++** has a **built-in copy constructor** provided by the compiler automatically.
> - **Java** does **NOT** have a built-in copy constructor. You must **define it manually** by accepting an object of the same class as a parameter.

```java
class Student {
    String name;
    int age;

    // Parameterized Constructor
    Student(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    // Copy Constructor (manually defined in Java)
    Student(Student s) {
        this.name = s.name;
        this.age  = s.age;
    }
}

// Usage
Student s1 = new Student("Aasii", 21);
Student s2 = new Student(s1);   // s2 is a copy of s1
```

```
┌──────────────────────────────────────────────────────┐
│                 Copy Constructor                     │
│                                                      │
│   s1 ──► [ name="Aasii", age=21 ]  (original)       │
│                    │                                 │
│                    │  new Student(s1)                │
│                    ▼                                 │
│   s2 ──► [ name="Aasii", age=21 ]  (independent     │
│                                      copy)           │
└──────────────────────────────────────────────────────┘
```

> **Fig. 4 — Copy Constructor Creates Independent Copy**

---

## 🔁 Constructor Overloading

Java supports **multiple constructors** in the same class with different parameter lists — this is called **constructor overloading**.

```java
class Box {
    double length, width, height;

    Box() {                                   // default
        length = width = height = 1.0;
    }

    Box(double side) {                        // cube
        length = width = height = side;
    }

    Box(double l, double w, double h) {       // full
        length = l; width = w; height = h;
    }
}

// Usage
Box b1 = new Box();           // 1×1×1
Box b2 = new Box(5.0);        // 5×5×5
Box b3 = new Box(2, 3, 4);    // 2×3×4
```

```
┌───────────────────────────────────────────────────────┐
│              Constructor Overloading                  │
│                                                       │
│   new Box()           ──►  Box()                      │
│   new Box(5.0)        ──►  Box(double side)           │
│   new Box(2, 3, 4)    ──►  Box(double l, w, h)        │
│                                                       │
│   JVM picks the correct one based on arguments        │
└───────────────────────────────────────────────────────┘
```

> **Fig. 5 — Constructor Overloading Resolution**

---

## 🔗 `this()` — Constructor Chaining

One constructor can **call another constructor** in the same class using `this()`. Must be the **first statement**.

```java
class Student {
    String name;
    int age;
    String college;

    Student(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    Student(String name, int age, String college) {
        this(name, age);            // calls above constructor
        this.college = college;
    }
}
```

```
┌──────────────────────────────────────────────────────┐
│              Constructor Chaining with this()        │
│                                                      │
│   new Student("Aasii", 21, "PU")                    │
│          │                                           │
│          ▼                                           │
│   Student(name, age, college)                        │
│          │  this(name, age)                          │
│          ▼                                           │
│   Student(name, age)  ← runs first                  │
│          │                                           │
│          ▼                                           │
│   back to original → sets college                   │
└──────────────────────────────────────────────────────┘
```

> **Fig. 6 — Constructor Chaining Flow**

> 🆚 **C++ vs Java** — C++ (from C++11) supports **delegating constructors** with a similar syntax (`: ClassName(args)`). Java uses `this()` — both must appear as the **first statement/call**.

---

## 🔼 `super()` — Calling Parent Constructor

In a subclass, `super()` calls the **parent class constructor**. Must also be the **first statement**.

```java
class Animal {
    String type;
    Animal(String type) {
        this.type = type;
    }
}

class Dog extends Animal {
    String name;
    Dog(String name) {
        super("Mammal");     // calls Animal(String type)
        this.name = name;
    }
}
```

```
┌──────────────────────────────────────────────────────┐
│                super() Call Chain                    │
│                                                      │
│   new Dog("Bruno")                                   │
│          │                                           │
│          ▼                                           │
│   Dog(String name)                                   │
│          │  super("Mammal")                          │
│          ▼                                           │
│   Animal(String type)  ← runs first                 │
│     type = "Mammal"                                  │
│          │                                           │
│          ▼  back to Dog                              │
│     name = "Bruno"                                   │
└──────────────────────────────────────────────────────┘
```

> **Fig. 7 — `super()` Constructor Chaining**

> 🆚 **C++ vs Java** — In C++, parent constructor is called via **initializer list** (`: ParentClass(args)`). In Java, you explicitly write `super(args)` as the first line inside the child constructor.

---

## ⚡ Private Constructor

A constructor declared `private` — **prevents object creation from outside** the class. Used in:
- **Singleton** pattern
- **Utility classes** (all static methods, no objects needed)

```java
class MathUtils {
    private MathUtils() {}   // no instantiation allowed

    static int square(int n) { return n * n; }
}

// Usage
int result = MathUtils.square(5);   // ✅
MathUtils m = new MathUtils();       // ❌ compile error
```

---

## 📋 Key Differences — C++ vs Java (Constructors)

| Feature | C++ | Java |
|---------|-----|------|
| Default constructor | Auto-provided | Auto-provided (calls `super()`) |
| Copy constructor | Auto-provided | ❌ Must define manually |
| Initializer list | ✅ (`: field(val)`) | ❌ Not supported |
| Destructor | ✅ (`~ClassName()`) | ❌ No destructor (GC handles it) |
| `this()` chaining | Delegating constructor (C++11) | `this()` — first statement |
| Parent constructor | `: ParentClass(args)` | `super(args)` — first statement |
| `private` constructor | ✅ Allowed | ✅ Allowed |
| `virtual` constructor | ❌ Not allowed | ❌ Not allowed |

---

## 💡 Summary

| Constructor Type | When to Use |
|-----------------|-------------|
| **Default** | When no initial values are needed or for setting defaults |
| **Parameterized** | When you want to set custom values at creation time |
| **Copy** | When you need an independent duplicate of an object |
| **Overloaded** | When multiple ways to create an object are needed |
| **`this()` chaining** | To reuse constructor logic within the same class |
| **`super()`** | To initialize the parent class portion of the object |
| **Private** | To restrict object creation (Singleton, utility classes) |

---
