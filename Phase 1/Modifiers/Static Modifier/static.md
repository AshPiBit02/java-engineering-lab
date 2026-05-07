# ⚡ Static Modifier in Java


---

## 📌 What is `static`?

The `static` keyword means a member **belongs to the class itself** rather than to any specific object. It is **shared across all instances**.

> 💡 You can access a `static` member **without creating an object**.

---

## 🏷️ Where Can `static` Be Applied?

| Target | Description |
|--------|-------------|
| `static` field | Shared variable across all objects |
| `static` method | Method callable without an object |
| `static` block | Runs once when the class is loaded |
| `static` nested class | A nested class that doesn't need outer class instance |

---

## 🔹 1. Static Field (Class Variable)

A single copy shared by **all objects** of the class.

```java
class Student {
    String name;          // instance variable — unique per object
    static int count = 0; // static variable  — shared by all

    Student(String name) {
        this.name = name;
        count++;          // increments shared counter
    }
}

Student s1 = new Student("Aasii");
Student s2 = new Student("Ram");
System.out.println(Student.count);  // 2
```

```
   s1 → [ name = "Aasii" ]  ─┐
                               ├──► count = 2  (shared)
   s2 → [ name = "Ram"   ]  ─┘
```

---

## 🔹 2. Static Method

Can be called **directly on the class** without creating an object.

```java
class MathUtils {
    static int square(int n) {
        return n * n;
    }
}

// No object needed
System.out.println(MathUtils.square(5));  // 25
```

### ⚠️ Restrictions inside a static method

| Allowed | Not Allowed |
|---------|-------------|
| Call other `static` methods | Call instance (non-static) methods directly |
| Access `static` fields | Access instance fields directly |
| Use local variables | Use `this` or `super` |

---

## 🔹 3. Static Block

Runs **once automatically** when the class is first loaded into memory — before any object is created. Used for static initialization.

```java
class Config {
    static String appName;

    static {
        appName = "MyApp";   // runs once on class load
        System.out.println("Static block executed");
    }
}
```

### Execution Order

```
Class loaded → static block runs → object created → constructor runs
```

---

## 🔹 4. Static Nested Class

A nested class marked `static` — does **not** need an instance of the outer class to be created.

```java
class Outer {
    static class Inner {
        void show() { System.out.println("Static nested class"); }
    }
}

// No Outer object needed
Outer.Inner obj = new Outer.Inner();
obj.show();
```

> 🆚 Unlike a regular inner class, a static nested class **cannot** access instance members of the outer class.

---

## 🔄 Static vs Instance — Quick Comparison

| Feature | `static` | Instance |
|---------|----------|----------|
| Belongs to | Class | Object |
| Memory | Allocated once | Per object |
| Access | `ClassName.member` | `objectName.member` |
| `this` keyword | ❌ Not available | ✅ Available |
| Called without object | ✅ Yes | ❌ No |
| Shared across objects | ✅ Yes | ❌ No |

---

## 🆚 C++ vs Java

| Feature | C++ | Java |
|---------|-----|------|
| `static` field | ✅ Same concept | ✅ Same concept |
| `static` method | ✅ Same concept | ✅ Same concept |
| `static` block | ❌ Not available | ✅ Available |
| Access via class name | ✅ | ✅ |
| `static` local variable | ✅ (persists between calls) | ❌ Not supported |

> 🆚 **Key Difference** — C++ supports `static` **local variables** inside functions (value persists between calls). Java does **not** have this feature.

---

## 💡 Summary

```
static  →  belongs to CLASS, not object
         →  shared across all instances
         →  accessible without creating an object
```

| Member | Use When |
|--------|---------|
| `static` field | Shared data (counters, constants) |
| `static` method | Utility/helper methods (e.g. `Math.sqrt()`) |
| `static` block | One-time class initialization |
| `static` nested class | Logically grouped class, no outer instance needed |

---
