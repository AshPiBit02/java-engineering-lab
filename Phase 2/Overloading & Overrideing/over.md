---

# Overloading & Overriding in Java

---

## Table of Contents

1. [The Core Distinction](#1-the-core-distinction)
2. [Method Overloading](#2-method-overloading)
3. [Method Overriding](#3-method-overriding)
4. [Overriding Rules in Detail](#4-overriding-rules-in-detail)
5. [Compile-Time vs Runtime Binding](#5-compile-time-vs-runtime-binding)
6. [Covariant Return Types](#6-covariant-return-types)
7. [Overloading vs Overriding — Head to Head](#7-overloading-vs-overriding--head-to-head)
8. [Common Mistakes & Pitfalls](#8-common-mistakes--pitfalls)
9. [Summary Table](#9-summary-table)

---

## 1. The Core Distinction

Both overloading and overriding let you use the **same method name** for different behavior — but they operate at completely different levels:

```
Fig. 1 — Overloading vs Overriding: Where They Live
┌────────────────────────────────────────────────────┐
│                   OVERLOADING                      │
│         Same class, same name, different           │
│         parameter list — resolved at COMPILE TIME  │
│                                                    │
│   void print(int x)                                │
│   void print(String s)      ← same class           │
│   void print(int x, int y)                         │
└────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────┐
│                   OVERRIDING                       │
│         Parent + Child, same signature —           │
│         resolved at RUNTIME via polymorphism       │
│                                                    │
│   class Animal { void speak() { ... } }            │
│   class Dog extends Animal {                       │
│       @Override void speak() { ... }  ← subclass  │
│   }                                                │
└────────────────────────────────────────────────────┘
```

---

## 2. Method Overloading

### 2.1 What It Is

Overloading means defining **multiple methods with the same name** in the same class, each with a **different parameter list**. The compiler picks the right one based on the arguments you pass.

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    String add(String a, String b) {
        return a + b;
    }
}
```

```java
Calculator c = new Calculator();
c.add(2, 3);          // → int version
c.add(2.0, 3.5);      // → double version
c.add(1, 2, 3);       // → three-arg version
c.add("Hello", "!");  // → String version
```

### 2.2 What Makes a Valid Overload

The parameter list must differ in at least one of:

| Difference        | Valid Overload? | Example                               |
|-------------------|-----------------|---------------------------------------|
| Number of params  | ✔ Yes           | `f(int)` vs `f(int, int)`             |
| Type of params    | ✔ Yes           | `f(int)` vs `f(double)`               |
| Order of params   | ✔ Yes           | `f(int, String)` vs `f(String, int)`  |
| Return type only  | ✗ No            | `int f(int)` vs `double f(int)`       |
| Access modifier only | ✗ No        | `public f(int)` vs `private f(int)`   |
| Exception only    | ✗ No            | Same signature, different throws      |

> **Return type alone does not differentiate overloads.** The compiler resolves overloads before it knows what you'll do with the return value.

```java
class Bad {
    int getValue()    { return 1; }
    // ✗ Compile error — same erasure as above
    double getValue() { return 1.0; }
}
```

### 2.3 Type Promotion in Overloading

When no exact match exists, Java **automatically promotes** the argument to the next wider type:

```
Fig. 2 — Java's Automatic Type Promotion Chain
byte → short → int → long → float → double
                 ↑
              char ┘
```

```java
class Demo {
    void show(long x)   { System.out.println("long: " + x); }
    void show(double x) { System.out.println("double: " + x); }
}

Demo d = new Demo();
d.show(10);     // int → promoted to long  → "long: 10"
d.show(10.5f);  // float → promoted to double → "double: 10.5"
```

If you pass an `int` and there's no `int` version, Java picks `long`. If there's no `long`, it tries `float`, then `double`. This happens silently — worth knowing to avoid surprising behavior.

### 2.4 Overloading with `null`

```java
class Printer {
    void print(String s)  { System.out.println("String"); }
    void print(Object o)  { System.out.println("Object"); }
}

new Printer().print(null);  // → "String" (most specific type wins)
```

When `null` is passed, Java picks the **most specific** (most derived) type that matches. `String` is more specific than `Object`, so `String` version is called.

```java
class Ambiguous {
    void test(String s)  { }
    void test(Integer i) { }
}
new Ambiguous().test(null);  // ✗ Compile error — ambiguous, neither is more specific
```

### 2.5 Overloading Constructors

Constructors follow the exact same overloading rules:

```java
class Person {
    String name;
    int age;

    Person() {
        this("Unknown", 0);   // chains to Person(String, int)
    }

    Person(String name) {
        this(name, 0);         // chains to Person(String, int)
    }

    Person(String name, int age) {
        this.name = name;
        this.age  = age;
    }
}
```

---

## 3. Method Overriding

### 3.1 What It Is

Overriding means a subclass provides its **own implementation** of a method that already exists in the parent class, with the **exact same signature**.

```java
class Animal {
    void speak() {
        System.out.println("Some generic sound");
    }
}

class Dog extends Animal {
    @Override
    void speak() {
        System.out.println("Woof!");
    }
}

class Cat extends Animal {
    @Override
    void speak() {
        System.out.println("Meow!");
    }
}
```

```
Fig. 3 — Overriding: One Name, Many Behaviors
              ┌─────────────────────┐
              │       Animal        │
              │   + speak()         │
              └──────────┬──────────┘
              ┌──────────┴──────────┐
              ▼                     ▼
   ┌──────────────────┐  ┌──────────────────┐
   │      Dog         │  │      Cat         │
   │  + speak()       │  │  + speak()       │
   │  "Woof!"         │  │  "Meow!"         │
   └──────────────────┘  └──────────────────┘

Animal a1 = new Dog();
Animal a2 = new Cat();
a1.speak(); → "Woof!"    ← runtime decides
a2.speak(); → "Meow!"    ← runtime decides
```

### 3.2 The `@Override` Annotation

`@Override` is optional but **strongly recommended**:

```java
class Animal {
    void spek() { }    // typo in method name
}

class Dog extends Animal {
    @Override
    void spek() { }    // ✔ catches nothing — typo matches typo

    @Override
    void speak() { }   // ✗ Compile error: speak() doesn't exist in Animal
}
```

Without `@Override`, a typo silently creates a **new method** instead of overriding — a hard-to-find bug. With it, the compiler catches mismatches immediately.

---

## 4. Overriding Rules in Detail

```
Fig. 4 — Overriding Rules at a Glance
┌──────────────────────────────────────────────────────────┐
│  Must match:  method name + parameter list (signature)   │
│  Must match:  return type (or covariant — see §6)        │
│                                                          │
│  Access:      can WIDEN, cannot NARROW                   │
│               protected → public ✔                       │
│               public → protected ✗                       │
│                                                          │
│  Exceptions:  can throw fewer/narrower checked exceptions│
│               cannot throw NEW/BROADER checked exceptions│
│                                                          │
│  Cannot override:  final methods                        │
│  Cannot override:  static methods (that's hiding)        │
│  Cannot override:  private methods (not visible)         │
└──────────────────────────────────────────────────────────┘
```

### 4.1 Access Modifier — Can Only Widen

```java
class Parent {
    protected void display() { }
}

class Child extends Parent {
    @Override
    public void display() { }    // ✔ protected → public (widened)
}

class BadChild extends Parent {
    @Override
    void display() { }           // ✗ protected → package-private (narrowed)
}
```

> The rule exists because of polymorphism: if you have a `Parent` reference pointing to a `Child` object, calling `display()` must always be accessible at least as much as the parent promised.

### 4.2 Exception Rule

```java
class Parent {
    void readFile() throws IOException { }
}

class Child extends Parent {
    @Override
    void readFile() throws FileNotFoundException { }  // ✔ narrower (subclass of IOException)

    // @Override
    // void readFile() throws Exception { }           // ✗ broader than IOException

    // @Override
    // void readFile() { }                            // ✔ throwing nothing is fine
}
```

Unchecked exceptions (`RuntimeException` and its subclasses) have no restriction — you can throw any unchecked exception in an override.

### 4.3 What Cannot Be Overridden

```java
class Base {
    final void locked()     { }   // ✗ final — cannot override
    private void hidden()   { }   // ✗ private — not visible, creates new method
    static void classLevel(){ }   // ✗ static — hiding, not overriding
}
```

---

## 5. Compile-Time vs Runtime Binding

This is the most important conceptual difference between overloading and overriding.

```
Fig. 5 — Binding: When the Decision Is Made
┌──────────────────────────────────────────────┐
│           OVERLOADING (Static Binding)       │
│                                              │
│  Compiler looks at:                          │
│    - reference type                          │
│    - number and types of arguments           │
│  Decision made at: COMPILE TIME              │
│  Also called: early binding                  │
└──────────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│           OVERRIDING (Dynamic Binding)       │
│                                              │
│  JVM looks at:                               │
│    - actual object type at runtime           │
│  Decision made at: RUNTIME                   │
│  Also called: late binding / dynamic dispatch│
└──────────────────────────────────────────────┘
```

### 5.1 Seeing the Difference

```java
class Animal {
    void speak()         { System.out.println("Animal speaks"); }
    void speak(String s) { System.out.println("Animal: " + s); }  // overload
}

class Dog extends Animal {
    @Override
    void speak() { System.out.println("Dog barks"); }  // override
}

Animal a = new Dog();

a.speak();          // → "Dog barks"       (RUNTIME — actual type is Dog)
a.speak("hello");   // → "Animal: hello"   (COMPILE TIME — Dog has no speak(String))
```

- `speak()` — the JVM checks: actual object is `Dog` → calls `Dog.speak()` ✓
- `speak("hello")` — the compiler checks: `Animal` reference has `speak(String)` → picks that version. `Dog` doesn't override it, so `Animal`'s runs.

### 5.2 Static Methods Are Not Overridden — They Are Hidden

```java
class Parent {
    static void staticMethod() { System.out.println("Parent static"); }
    void instanceMethod()      { System.out.println("Parent instance"); }
}

class Child extends Parent {
    static void staticMethod() { System.out.println("Child static"); }  // HIDING
    @Override
    void instanceMethod()      { System.out.println("Child instance"); } // OVERRIDING
}

Parent ref = new Child();
ref.staticMethod();   // → "Parent static"   (compile-time, reference type wins)
ref.instanceMethod(); // → "Child instance"  (runtime, actual type wins)
```

Static methods belong to the **class**, not the object. Polymorphism doesn't apply to them.

---

## 6. Covariant Return Types

Java allows an overriding method to return a **subtype** of the parent's return type. This is called a **covariant return type**.

```java
class Animal {
    Animal create() {
        return new Animal();
    }
}

class Dog extends Animal {
    @Override
    Dog create() {          // ✔ Dog is a subtype of Animal — covariant
        return new Dog();
    }
}
```

```
Fig. 6 — Covariant Return Type
  Parent method return type:  Animal
  Child method return type:   Dog      (Dog IS-A Animal → valid)
  
  Animal ◄──── Dog   (Dog is more specific)
  
  ✔ Allowed: narrowing the return type in override
  ✗ Not allowed: widening (Dog → Object would break callers expecting Animal)
```

This is particularly useful in the **Builder** and **Factory Method** patterns:

```java
class Builder {
    Builder setName(String name) { /* ... */ return this; }
}

class AdvancedBuilder extends Builder {
    @Override
    AdvancedBuilder setName(String name) {   // covariant — returns own type
        super.setName(name);
        return this;
    }

    AdvancedBuilder setLevel(int level) { /* ... */ return this; }
}

// Now method chaining works without casting:
new AdvancedBuilder()
    .setName("X")     // returns AdvancedBuilder (not just Builder)
    .setLevel(5);     // ✔ works directly
```

> **C++ note:** C++ also supports covariant return types for virtual functions — same concept, same name.

---

## 7. Overloading vs Overriding — Head to Head

| Aspect                    | Overloading                              | Overriding                                      |
|---------------------------|------------------------------------------|-------------------------------------------------|
| Where it happens          | Same class                               | Parent class + subclass                         |
| Method signature          | Must differ (param types/count/order)    | Must be identical                               |
| Return type               | Can differ freely                        | Must be same or covariant (subtype)             |
| Access modifier           | Can differ freely                        | Can only widen (not narrow)                     |
| Binding time              | Compile time (static)                    | Runtime (dynamic)                               |
| Polymorphism              | Not involved                             | Core mechanism of runtime polymorphism          |
| `@Override` annotation    | N/A                                      | Optional but recommended                        |
| `static` methods          | Can be overloaded                        | Cannot be overridden (only hidden)              |
| `final` methods           | Can be overloaded                        | Cannot be overridden                            |
| `private` methods         | Can be overloaded within same class      | Cannot be overridden (not inherited)            |
| Inheritance required      | No                                       | Yes                                             |
| Also known as             | Static / compile-time polymorphism       | Dynamic / runtime polymorphism                  |

---

## 8. Common Mistakes & Pitfalls

### Mistake 1: Thinking Return Type Alone Creates an Overload

```java
class X {
    int process()    { return 0; }
    double process() { return 0.0; }  // ✗ Compile error — not a valid overload
}
```

---

### Mistake 2: Accidentally Creating a New Method Instead of Overriding

```java
class Animal {
    void speak() { System.out.println("Animal"); }
}

class Dog extends Animal {
    void Speak() { System.out.println("Dog"); }  // ✗ capital S — new method, not override!
}

Animal a = new Dog();
a.speak();  // → "Animal"  — Dog's method never called
```

**Fix:** Always use `@Override`. The compiler would have caught `Speak()` immediately.

---

### Mistake 3: Overriding with a Narrower Access Modifier

```java
class Parent {
    public void show() { }
}

class Child extends Parent {
    @Override
    protected void show() { }  // ✗ Compile error — narrowing access
}
```

---

### Mistake 4: Confusing Overloading with Overriding When Types Promote

```java
class Printer {
    void print(int x)    { System.out.println("int: " + x); }
    void print(double x) { System.out.println("double: " + x); }
}

Printer p = new Printer();
p.print(5);    // → "int: 5"
p.print(5L);   // → "double: 5.0"  (long → promoted to double, no long version)
```

Unexpected promotion can make you think the wrong method is being called. The fix is to add the version you need or cast explicitly: `p.print((int) 5L)`.

---

### Mistake 5: Expecting Polymorphism from Overloaded Methods

```java
class Base {
    void handle(Base b)    { System.out.println("Base handles Base"); }
    void handle(Derived d) { System.out.println("Base handles Derived"); }
}

class Derived extends Base { }

Base obj = new Derived();
Base arg = new Derived();   // reference type is Base

obj.handle(arg);  // → "Base handles Base"
                  // ← overload resolved at compile time by arg's reference type (Base)
                  //   NOT the actual runtime type (Derived)
```

Overloading is always resolved by the **compile-time type of the argument**, never the runtime type. This surprises many developers who expect polymorphic dispatch here.

---

## 9. Summary Table

| Concept                        | Key Rule                                                                       |
|--------------------------------|--------------------------------------------------------------------------------|
| Overloading                    | Same name, different parameter list, same class, compile-time resolution       |
| Overriding                     | Same signature, subclass, runtime resolution via dynamic dispatch              |
| Valid overload differentiator  | Param count, param types, param order — not return type or modifiers alone     |
| Type promotion in overloading  | `byte→short→int→long→float→double`; most specific match wins                   |
| `null` in overloading          | Most specific type wins; ambiguous if two types are equally specific           |
| `@Override`                    | Optional annotation; catches signature mismatches at compile time              |
| Access rule in overriding      | Can widen (protected→public), cannot narrow (public→protected)                 |
| Exception rule in overriding   | Can throw fewer/narrower checked exceptions; unchecked have no restriction     |
| Covariant return type          | Override may return a subtype of the parent method's return type               |
| Static method + override       | Not overriding — it's method hiding; resolved by reference type at compile time|
| `final` method + override      | Illegal — final locks the implementation                                       |
| Static binding                 | Overloading — decided by compiler using reference types and argument types     |
| Dynamic binding                | Overriding — decided by JVM at runtime using actual object type                |

---
