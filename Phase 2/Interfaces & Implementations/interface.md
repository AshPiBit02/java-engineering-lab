# 🔌 Interfaces in Java


---

## 📌 What is an Interface?

An **interface** is a **fully abstract contract** that defines **what a class must do** — without specifying **how** it does it. It is a collection of abstract method declarations (and constants) that a class agrees to implement.

> An interface is like a **job description** — it says what skills are required, not how you do the work.

```
┌──────────────────────────────────────────────────────────────┐
│                   What is an Interface?                      │
│                                                              │
│   Interface (contract)           Class (implementation)      │
│   ──────────────────────         ──────────────────────────  │
│   Defines WHAT to do             Defines HOW to do it        │
│                                                              │
│   interface Flyable {            class Bird implements       │
│       void fly();   ────────────►    Flyable {               │
│   }                                  void fly() {            │
│                                          // actual logic     │
│                                      }                       │
│                                  }                           │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Interface as a Contract**

---

## 🔑 Declaring and Implementing an Interface

### Declaring

```java
interface InterfaceName {
    // constants (implicitly public static final)
    int MAX = 100;

    // abstract methods (implicitly public abstract)
    void method1();
    int method2(String s);
}
```

### Implementing

```java
class MyClass implements InterfaceName {

    @Override
    public void method1() {
        System.out.println("method1 implemented");
    }

    @Override
    public int method2(String s) {
        return s.length();
    }
}
```

> 💡 A class **must implement ALL methods** of the interface — otherwise it must be declared `abstract`.

---

## 🧬 Interface Features & Characteristics

```
┌──────────────────────────────────────────────────────────────┐
│               Interface Features at a Glance                 │
│                                                              │
│   ✅ All methods are public abstract by default              │
│   ✅ All fields are public static final by default           │
│   ✅ Cannot be instantiated directly                         │
│   ✅ A class can implement multiple interfaces               │
│   ✅ An interface can extend multiple interfaces             │
│   ✅ Can have default methods  (Java 8+)                     │
│   ✅ Can have static methods   (Java 8+)                     │
│   ✅ Can have private methods  (Java 9+)                     │
│   ❌ Cannot have instance fields                             │
│   ❌ Cannot have constructors                                │
│   ❌ Cannot have instance initializer blocks                 │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Interface Features and Restrictions**

---

## 📦 What Can an Interface Contain?

| Member | Allowed? | Implicit Modifier | Since |
|--------|----------|-------------------|-------|
| Abstract method | ✅ | `public abstract` | Java 1 |
| Constant field | ✅ | `public static final` | Java 1 |
| Default method | ✅ | `public` | Java 8 |
| Static method | ✅ | `public` | Java 8 |
| Private method | ✅ | `private` | Java 9 |
| Constructor | ❌ | — | Never |
| Instance field | ❌ | — | Never |
| `static` block | ❌ | — | Never |

---

## ⚙️ Types of Methods in an Interface

### 🔹 Abstract Method *(default before Java 8)*

No body — **must be overridden** by implementing class.

```java
interface Shape {
    double area();          // abstract — no body
    double perimeter();     // abstract — no body
}
```

---

### 🔹 Default Method *(Java 8+)*

Has a body — provides a **default implementation**. Implementing class can override it or use as-is.

```java
interface Greeting {
    void greet(String name);

    default void greetAll() {               // default method
        System.out.println("Hello, everyone!");
    }
}

class EnglishGreeting implements Greeting {
    public void greet(String name) {
        System.out.println("Hello, " + name);
    }
    // greetAll() is inherited as-is — no need to override
}
```

> 💡 Default methods were added to allow **adding new methods to interfaces** without breaking existing implementing classes.

---

### 🔹 Static Method *(Java 8+)*

Belongs to the **interface itself** — called via interface name, not through implementing class.

```java
interface MathOps {
    static int square(int n) { return n * n; }
}

// Call via interface name — not via implementing class
int result = MathOps.square(5);   // ✅
```

---

### 🔹 Private Method *(Java 9+)*

Used internally within the interface to **avoid code duplication** between default methods. Cannot be accessed outside the interface.

```java
interface Logger {
    default void logInfo(String msg)  { log("INFO", msg); }
    default void logError(String msg) { log("ERROR", msg); }

    private void log(String level, String msg) {    // shared helper
        System.out.println("[" + level + "] " + msg);
    }
}
```

---

## 🔗 Implementing Multiple Interfaces

A class can implement **multiple interfaces** — this is Java's solution to multiple inheritance.

```java
interface Flyable  { void fly(); }
interface Swimmable { void swim(); }
interface Runnable  { void run(); }

class Duck implements Flyable, Swimmable, Runnable {
    public void fly()  { System.out.println("Duck flying"); }
    public void swim() { System.out.println("Duck swimming"); }
    public void run()  { System.out.println("Duck running"); }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│            Multiple Interface Implementation                 │
│                                                              │
│   ┌──────────┐   ┌───────────┐   ┌──────────┐                │
│   │ Flyable  │   │ Swimmable │   │ Runnable │                │
│   │ fly()    │   │ swim()    │   │ run()    │                │
│   └────┬─────┘   └─────┬─────┘   └────┬─────┘                │
│        │               │              │                      │
│        └───────────────┼──────────────┘                      │
│                        │  implements                         │
│                 ┌──────▼──────┐                              │
│                 │    Duck     │                              │
│                 │  fly()  ✅  │                             │
│                 │  swim() ✅  │                             │
│                 │  run()  ✅  │                             │
│                 └─────────────┘                              │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — One Class Implementing Multiple Interfaces**

---

## 🔗 Interface Extending Multiple Interfaces

An interface can **extend multiple interfaces** — unlike classes.

```java
interface A { void methodA(); }
interface B { void methodB(); }

interface C extends A, B {     // interface extends multiple interfaces ✅
    void methodC();
}

class MyClass implements C {
    public void methodA() { }
    public void methodB() { }
    public void methodC() { }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│          Interface Extending Multiple Interfaces             │
│                                                              │
│   ┌──────────┐          ┌──────────┐                         │
│   │Interface A│          │Interface B│                       │
│   │ methodA() │          │ methodB() │                       │
│   └─────┬─────┘          └─────┬─────┘                       │
│         │                      │                             │
│         └──────────┬───────────┘                             │
│                    │  extends                                │
│           ┌────────▼────────┐                                │
│           │  Interface C    │  ← inherits A + B + own        │
│           │  methodA()      │                                │
│           │  methodB()      │                                │
│           │  methodC()      │                                │
│           └────────┬────────┘                                │
│                    │  implements                             │
│           ┌────────▼────────┐                                │
│           │    MyClass      │                                │
│           └─────────────────┘                                │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Interface Extending Multiple Interfaces**

---

## 🆚 Interface vs Abstract Class

```
┌──────────────────────────────────────────────────────────────┐
│              Interface  vs  Abstract Class                   │
│                                                              │
│   Interface                    Abstract Class                │
│   ─────────────────────        ───────────────────────       │
│   Pure contract                Partial implementation        │
│   No constructors              Has constructors              │
│   No instance fields           Has instance fields           │
│   Multiple "inheritance"       Single inheritance only       │
│   All methods public           Any access modifier           │
│   implements keyword           extends keyword               │
│   IS-A-CAPABLE-OF              IS-A relationship             │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Interface vs Abstract Class**

| Feature | Interface | Abstract Class |
|---------|-----------|---------------|
| Keyword | `interface` / `implements` | `abstract class` / `extends` |
| Instantiation | ❌ Cannot | ❌ Cannot |
| Constructor | ❌ No | ✅ Yes |
| Instance fields | ❌ No | ✅ Yes |
| Method body | ✅ Only `default`/`static`/`private` | ✅ Any method |
| Access modifiers on methods | `public` only | Any |
| Multiple inheritance | ✅ Class implements many | ❌ Class extends one only |
| `extends` / `implements` | Interface `extends` interface | Class `extends` abstract class |
| State (data) | ❌ No instance state | ✅ Can hold state |
| Relationship | CAN-DO / CAPABLE-OF | IS-A |

---

## ❓ Why Use Interface Over Abstract Class?

```
┌──────────────────────────────────────────────────────────────┐
│          When to Prefer Interface over Abstract Class        │
│                                                              │
│   1. Multiple "inheritance" needed                           │
│      A class can implement many interfaces                   │
│      but can only extend one abstract class                  │
│                                                              │
│   2. Unrelated classes need same behavior                    │
│      Bird and Airplane both Flyable — not same IS-A tree     │
│                                                              │
│   3. Defining a pure contract / API                          │
│      You want to define WHAT, not HOW                        │
│                                                              │
│   4. Loose coupling                                          │
│      Code to interface, not to implementation                │
│                                                              │
│   5. Full abstraction required                               │
│      No shared state or partial implementation needed        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — When to Prefer Interface**

---

## 🕐 When to Use — Quick Reference

| Situation | Use Interface | Use Abstract Class |
|-----------|:------------:|:-----------------:|
| Multiple inheritance needed | ✅ | ❌ |
| Unrelated classes share behavior | ✅ | ❌ |
| Defining a pure API contract | ✅ | ❌ |
| Shared code / partial implementation | ❌ | ✅ |
| Need constructors | ❌ | ✅ |
| Need instance variables (state) | ❌ | ✅ |
| IS-A relationship (strong) | ❌ | ✅ |
| CAN-DO / CAPABLE-OF relationship | ✅ | ❌ |
| Adding behavior to unrelated classes | ✅ | ❌ |
| Template method pattern | ❌ | ✅ |

---

## 🌍 Interface as a Type (Polymorphism)

An interface can be used as a **reference type** — enabling polymorphism just like a parent class.

```java
interface Drawable {
    void draw();
}

class Circle implements Drawable {
    public void draw() { System.out.println("Drawing Circle"); }
}

class Square implements Drawable {
    public void draw() { System.out.println("Drawing Square"); }
}

// Interface used as type
Drawable d1 = new Circle();   // ✅
Drawable d2 = new Square();   // ✅

d1.draw();   // Drawing Circle
d2.draw();   // Drawing Square

// Store in a list of interface type
List<Drawable> shapes = new ArrayList<>();
shapes.add(new Circle());
shapes.add(new Square());

for (Drawable d : shapes) {
    d.draw();    // polymorphism via interface
}
```

---

## 🔄 Interface vs Class vs Abstract Class — Full Picture

```
┌──────────────────────────────────────────────────────────────┐
│         Interface vs Abstract Class vs Concrete Class        │
│                                                              │
│   Concrete Class                                             │
│   ─────────────────────────────────────────────────────      │
│   Full implementation, can be instantiated                   │
│   extends one class, implements many interfaces              │
│                                                              │
│   Abstract Class                                             │
│   ─────────────────────────────────────────────────────      │
│   Partial implementation, cannot be instantiated             │
│   Can have constructors, fields, any method type             │
│   Extended by one class at a time                            │
│                                                              │
│   Interface                                                  │
│   ─────────────────────────────────────────────────────      │
│   No implementation (except default/static/private)          │
│   Cannot be instantiated, no constructors, no fields         │
│   Implemented by many classes, extended by many interfaces   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — Full Comparison: Concrete Class vs Abstract Class vs Interface**

---

## 🔁 Marker Interfaces

A **marker interface** has **no methods** — it simply marks a class to indicate something to the JVM or framework.

```java
interface Serializable { }   // no methods — just a marker
interface Cloneable    { }   // no methods — just a marker
```

```java
class Student implements Serializable {   // marks Student as serializable
    String name;
    int age;
}
```

> 💡 The JVM and Java APIs check `instanceof SerializableInterface` to decide behavior — e.g. `ObjectOutputStream` only serializes `Serializable` objects.

---

## 🔁 Functional Interface *(Java 8+)*

An interface with **exactly one abstract method** — used with **lambda expressions**.

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);   // only one abstract method
}

// Using lambda expression
Calculator add = (a, b) -> a + b;
Calculator mul = (a, b) -> a * b;

System.out.println(add.calculate(3, 5));   // 8
System.out.println(mul.calculate(3, 5));   // 15
```

> 💡 Common built-in functional interfaces: `Runnable`, `Comparator`, `Callable`, `Predicate`, `Function`, `Consumer`, `Supplier`.

---

## 🆚 C++ vs Java — Interfaces

| Feature | C++ | Java |
|---------|-----|------|
| Interface concept | Pure abstract class (all `= 0`) | `interface` keyword |
| Multiple inheritance | ✅ Via classes (diamond issue) | ✅ Via interfaces (safe) |
| Default methods | ❌ | ✅ Java 8+ |
| Static methods in interface | ❌ | ✅ Java 8+ |
| Functional interface / lambda | ✅ (function pointers / `std::function`) | ✅ `@FunctionalInterface` |
| Marker interface | ❌ (use traits/tags) | ✅ |
| `implements` keyword | ❌ (just `:`) | ✅ explicit `implements` |

> 🆚 **Key Difference** — C++ achieves interface-like behavior using **pure abstract classes** (`virtual void method() = 0`). Java has a dedicated `interface` keyword with additional features like `default`, `static`, and `private` methods that C++ pure abstract classes don't have.

---

## 💡 Summary

| Concept | Key Point |
|---------|-----------|
| **Interface** | Contract — defines WHAT, not HOW |
| **`implements`** | Keyword used by class to fulfill interface |
| **All methods** | `public abstract` by default |
| **All fields** | `public static final` by default |
| **Default method** | Has body — Java 8+, optional to override |
| **Static method** | Belongs to interface — called via interface name |
| **Private method** | Internal helper — Java 9+ |
| **Multiple impl.** | A class can implement many interfaces |
| **Marker interface** | No methods — just tags a class |
| **Functional interface** | One abstract method — used with lambdas |
| **vs Abstract class** | Interface = CAN-DO, Abstract = IS-A |

```
Interface  →  contract  →  WHAT to do  →  implements  →  multiple allowed
Abstract   →  partial   →  HOW partially  →  extends  →  one only
```