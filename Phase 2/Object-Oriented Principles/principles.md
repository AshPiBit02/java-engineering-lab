# 🧬 Object-Oriented Principles in Java


---

## 📌 What is OOP?

**Object-Oriented Programming (OOP)** is a programming paradigm that organizes software around **objects** rather than functions and logic. Java is built from the ground up as an object-oriented language.

> Everything in Java (except primitives) is an **object**.

```
┌──────────────────────────────────────────────────────────────┐
│                Four Pillars of OOP                           │
│                                                              │
│   ┌─────────────────┐       ┌─────────────────┐              │
│   │  Encapsulation  │       │   Abstraction   │              │
│   │  (data hiding)  │       │(hide complexity)│              │
│   └─────────────────┘       └─────────────────┘              │
│   ┌─────────────────┐       ┌─────────────────┐              │
│   │   Inheritance   │       │  Polymorphism   │              │
│   │ (reuse & extend)│       │  (many forms)   │              │
│   └─────────────────┘       └─────────────────┘              │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Four Pillars of OOP**

---

## 🆚 OOP vs Procedural (C vs Java)

| Feature | Procedural (C) | OOP (Java) |
|---------|---------------|------------|
| Focus | Functions / procedures | Objects |
| Data | Global / passed around | Encapsulated in objects |
| Reusability | Function reuse | Class + Inheritance |
| Real-world modeling | ❌ Difficult | ✅ Natural |
| Security | ❌ Data exposed | ✅ Data hidden via access modifiers |

---

## 1. 🧱 Class & Object *(covered in depth — quick recap)*

A **class** is the blueprint. An **object** is the real instance created from it.

```java
class Student {               // class — blueprint
    String name;
    int age;
    void display() {
        System.out.println(name + " | " + age);
    }
}

Student s = new Student();    // object — instance on heap
s.name = "Aasii";
s.age  = 21;
s.display();                  // Aasii | 21
```

> 📄 *Covered in full detail in `java-classes-objects.md`*

---

## 2. 🔒 Encapsulation

**Wrapping data (fields) and methods together** inside a class and **restricting direct access** to the data using access modifiers.

> The outside world interacts only through **getters and setters** — not the raw field.

```java
class BankAccount {
    private double balance;          // hidden from outside

    public double getBalance() {     // controlled read
        return balance;
    }

    public void deposit(double amt) { // controlled write
        if (amt > 0) balance += amt;
    }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│                    Encapsulation                             │
│                                                              │
│   Outside World                   Class                      │
│                                                              │
│   can NOT access ──► ❌  private double balance             │
│                                                              │
│   can access     ──► ✅  getBalance()  (public method)      │
│                  ──► ✅  deposit()     (public method)      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Encapsulation: Data Hidden, Methods Exposed**

**Benefits:**
- Protects internal state from unintended modification
- Easier to maintain and change implementation without breaking outside code
- Enforces **data validation** (e.g. no negative deposit)

---

## 3. 🎭 Abstraction

**Hiding the internal implementation** and showing only what is necessary to the user.

> You use `System.out.println()` without knowing how it works internally — that's abstraction.

```java
abstract class Shape {
    abstract double area();          // what — no implementation

    void display() {                 // how — shown to user
        System.out.println("Area: " + area());
    }
}

class Circle extends Shape {
    double r;
    Circle(double r) { this.r = r; }

    double area() { return Math.PI * r * r; }  // implementation hidden inside
}
```

```
┌──────────────────────────────────────────────────────────────┐
│                      Abstraction                             │
│                                                              │
│   User sees:           area()  →  "just call it"             │
│                                                              │
│   Hidden inside:       Math.PI * r * r   (complexity hidden) │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Abstraction: What vs How**

| Encapsulation | Abstraction |
|---------------|-------------|
| Hides **data** | Hides **implementation** |
| Uses `private` + getters/setters | Uses `abstract` class / interface |
| Protects state | Reduces complexity |

---

## 4. 🔗 Inheritance *(brief — covered in detail at 2.2 & 2.3)*

**Inheritance** allows one class (child/subclass) to **acquire the properties and behaviors** of another class (parent/superclass). Promotes **code reusability**.

```java
class Animal {              // parent class
    String type = "Animal";
    void eat() { System.out.println("Eating..."); }
}

class Dog extends Animal {  // child class — inherits Animal
    void bark() { System.out.println("Barking..."); }
}

Dog d = new Dog();
d.eat();    // ✅ inherited from Animal
d.bark();   // ✅ own method
```

```
┌──────────────────────────────────────────────────────────────┐
│                      Inheritance                             │
│                                                              │
│         ┌─────────────────────────┐                          │
│         │       Animal            │  ← parent (superclass)   │
│         │   type, eat()           │                          │
│         └────────────┬────────────┘                          │
│                      │  extends                              │
│         ┌────────────▼────────────┐                          │
│         │         Dog             │  ← child (subclass)      │
│         │  inherits type, eat()   │                          │
│         │  + own: bark()          │                          │
│         └─────────────────────────┘                          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Inheritance: Child extends Parent**

> 📄 *Types of inheritance, `extends`, `super` keyword covered in detail at `2.2`, `2.3`, `2.4`*

---

## 5. 🎨 Polymorphism

**Polymorphism** means **"many forms"** — the same method name behaves differently based on the object or arguments.

Two types in Java:

```
┌──────────────────────────────────────────────────────────────┐
│                     Polymorphism                             │
│                                                              │
│   ┌─────────────────────────┐  ┌──────────────────────────┐  │
│   │  Compile-time           │  │  Runtime                 │  │
│   │  (Method Overloading)   │  │  (Method Overriding)     │  │
│   │                         │  │                          │  │
│   │  Same name,             │  │  Child redefines         │  │
│   │  different parameters   │  │  parent's method         │  │
│   │                         │  │                          │  │
│   │  Resolved at compile    │  │  Resolved at runtime     │  │
│   └─────────────────────────┘  └──────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Two Types of Polymorphism**

### Overloading (Compile-time)

```java
class Calc {
    int add(int a, int b)            { return a + b; }
    double add(double a, double b)   { return a + b; }
    int add(int a, int b, int c)     { return a + b + c; }
}
```

### Overriding (Runtime)

```java
class Animal {
    void sound() { System.out.println("Generic sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Woof!"); }   // overrides parent
}

Animal a = new Dog();    // parent reference, child object
a.sound();               // Woof!  ← decided at runtime
```

> 📄 *Covered in full detail in `2.5 — Overriding / Overloading`*

---

## 🔁 How the Four Pillars Connect

```
┌──────────────────────────────────────────────────────────────┐
│                  OOP Pillars in Action                       │
│                                                              │
│   Class / Object      → foundation of everything             │
│         │                                                    │
│   Encapsulation       → protects data inside the object      │
│         │                                                    │
│   Abstraction         → exposes only what user needs         │
│         │                                                    │
│   Inheritance         → child reuses parent's code           │
│         │                                                    │
│   Polymorphism        → same interface, different behavior   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — How OOP Pillars Build on Each Other**

---

## 💡 Summary

| Pillar | Core Idea | Java Mechanism |
|--------|-----------|---------------|
| **Class & Object** | Blueprint and its instance | `class`, `new` |
| **Encapsulation** | Hide data, expose behavior | `private` + getters/setters |
| **Abstraction** | Hide complexity, show interface | `abstract`, `interface` |
| **Inheritance** | Reuse and extend parent code | `extends` |
| **Polymorphism** | Same name, different behavior | Overloading + Overriding |

> 🆚 **C++ vs Java** — All four OOP pillars exist in both. Key differences: Java has **no multiple class inheritance** (only via interfaces), Java has **no destructor** (GC handles it), and Java enforces OOP more strictly — you can't write code outside a class.

---
