# 🔗 Inheritance in Java


---

## 📌 What is Inheritance?

**Inheritance** is a mechanism where a **child class (subclass)** acquires the **properties and behaviors** of a **parent class (superclass)**. It promotes **code reusability** and establishes an **"IS-A" relationship** between classes.

> A `Dog` IS-A `Animal` → Dog can inherit from Animal.

```
┌──────────────────────────────────────────────────────────────┐
│                   Inheritance Concept                        │
│                                                              │
│         ┌─────────────────────────┐                          │
│         │      Parent Class       │                          │
│         │      (Superclass)       │                          │
│         │                         │                          │
│         │  fields + methods       │                          │
│         └────────────┬────────────┘                          │
│                      │                                       │
│                   extends                                    │
│                      │                                       │
│         ┌────────────▼────────────┐                          │
│         │      Child Class        │                          │
│         │      (Subclass)         │                          │
│         │                         │                          │
│         │  inherits parent's      │                          │
│         │  fields + methods       │                          │
│         │  + its own new ones     │                          │
│         └─────────────────────────┘                          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Parent → Child Inheritance**

---

## 🔑 `extends` Keyword

In Java, inheritance is achieved using the `extends` keyword.

```java
class Parent {
    // fields and methods
}

class Child extends Parent {
    // inherits Parent + adds its own
}
```

> 🆚 **C++ vs Java** — C++ uses `:` for inheritance (`class Dog : public Animal`). Java uses the `extends` keyword — more readable and explicit.

---

## 🧱 Superclass & Subclass

| Term | Also Called | Description |
|------|-------------|-------------|
| **Superclass** | Parent class, Base class | The class being inherited from |
| **Subclass** | Child class, Derived class | The class that inherits |

```java
class Animal {                    // superclass
    String name;
    int age;

    void eat() {
        System.out.println(name + " is eating.");
    }

    void sleep() {
        System.out.println(name + " is sleeping.");
    }
}

class Dog extends Animal {        // subclass
    String breed;

    void bark() {
        System.out.println(name + " is barking!");  // name inherited ✅
    }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│                 Superclass vs Subclass                       │
│                                                              │
│   Animal (superclass)         Dog (subclass)                 │
│   ───────────────────         ─────────────────────          │
│   name   ─────────────────►  name   (inherited)             │
│   age    ─────────────────►  age    (inherited)             │
│   eat()  ─────────────────►  eat()  (inherited)             │
│   sleep()─────────────────►  sleep()(inherited)             │
│                              breed  (own field)              │
│                              bark() (own method)             │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — What is Inherited and What is New**

---

## 👪 Member Access in Inheritance

Not all members of the parent class are accessible in the child class — it depends on **access modifiers**.

```
┌──────────────────────────────────────────────────────────────┐
│           Member Access in Inheritance                       │
│                                                              │
│   Parent Member    Accessible in Child?                      │
│   ─────────────────────────────────────                      │
│   public           ✅  Yes — always                          │
│   protected        ✅  Yes — even across packages            │
│   default          ⚠️  Only if same package                  │
│   private          ❌  Never — completely hidden             │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Access Modifier Behavior in Inheritance**

```java
class Animal {
    public    String name  = "Animal";   // ✅ accessible in child
    protected int    age   = 5;          // ✅ accessible in child
              String type  = "Mammal";   // ⚠️ only if same package
    private   int    id    = 101;        // ❌ not accessible in child
}

class Dog extends Animal {
    void show() {
        System.out.println(name);    // ✅
        System.out.println(age);     // ✅
        System.out.println(type);    // ⚠️ only same package
        System.out.println(id);      // ❌ compile error
    }
}
```

> 💡 Use `protected` for members you want **visible to subclasses but hidden from the outside world**.

---

## 🔼 The `super` Keyword

`super` refers to the **immediate parent class**. It is used to:

| Use | Description |
|-----|-------------|
| `super.field` | Access parent's field (when child has same name) |
| `super.method()` | Call parent's method |
| `super()` | Call parent's constructor |

```java
class Animal {
    String name = "Animal";

    Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor called");
    }

    void display() {
        System.out.println("I am: " + name);
    }
}

class Dog extends Animal {
    String name = "Dog";     // same field name as parent

    Dog(String name) {
        super(name);         // calls Animal(String name) ✅ must be first line
        System.out.println("Dog constructor called");
    }

    void show() {
        System.out.println(this.name);   // Dog's name
        System.out.println(super.name);  // Animal's name
        super.display();                 // Animal's display()
    }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│                    super Keyword Uses                        │
│                                                              │
│   super()           →  call parent constructor               │
│   super.field       →  access parent's field                 │
│   super.method()    →  call parent's method                  │
│                                                              │
│   ⚠️ super() must always be the FIRST statement             │
│      in the child constructor                                │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Uses of `super` Keyword**

> 🆚 **C++ vs Java** — C++ calls parent constructor via **initializer list** (`: Animal(name)`). Java uses `super(args)` explicitly as the **first line** of the child constructor.

---

## 🔄 Constructor Chaining in Inheritance

When a child object is created, the **parent constructor always runs first** — either explicitly via `super()` or implicitly by the compiler.

```java
class A {
    A() { System.out.println("A constructor"); }
}

class B extends A {
    B() {
        // super() called implicitly if not written
        System.out.println("B constructor");
    }
}

class C extends B {
    C() {
        // super() called implicitly
        System.out.println("C constructor");
    }
}

new C();
// Output:
// A constructor
// B constructor
// C constructor
```

```
┌──────────────────────────────────────────────────────────────┐
│             Constructor Chaining Flow                        │
│                                                              │
│   new C()                                                    │
│      │                                                       │
│      ▼   C() calls super() → B()                            │
│      ▼   B() calls super() → A()                            │
│      ▼   A() runs first    → prints "A constructor"         │
│      ▼   B() runs next     → prints "B constructor"         │
│      ▼   C() runs last     → prints "C constructor"         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Constructor Chaining: Parent Always Runs First**

---

## 📦 Types of Inheritance — Quick Overview

> *Covered in full detail in `2.3 — Types of Inheritance`*

```
┌──────────────────────────────────────────────────────────────┐
│               Types of Inheritance (brief)                   │
│                                                              │
│   Single       A → B                                         │
│   Multilevel   A → B → C                                     │
│   Hierarchical A → B, A → C                                  │
│   Multiple     A + B → C  (❌ not via classes in Java)       │
│   Hybrid       combination  (❌ not via classes in Java)     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Types of Inheritance at a Glance**

> 💡 Java does **not** support multiple inheritance through classes — only through **interfaces** — to avoid the **Diamond Problem**. Full details in `2.3`.

---

## 🆚 C++ vs Java — Inheritance

| Feature | C++ | Java |
|---------|-----|------|
| Keyword | `: public/private/protected` | `extends` |
| Multiple inheritance | ✅ Via classes | ❌ Via interfaces only |
| Default access in child | Depends on specifier | Follows access modifiers |
| `super` equivalent | `ParentClass::method()` | `super.method()` |
| Parent constructor call | Initializer list | `super()` — first line |
| All classes inherit from | Nothing (by default) | `Object` class |

> 🆚 **Key Difference** — In Java, **every class implicitly extends `Object`** — the root of all Java classes. C++ has no such universal base class.

---

## 💡 Summary

| Concept | Key Point |
|---------|-----------|
| **Inheritance** | Child acquires parent's fields and methods |
| **`extends`** | Keyword used to inherit in Java |
| **Superclass** | Parent — the class being inherited from |
| **Subclass** | Child — the class that inherits |
| **`super`** | Refers to parent class — fields, methods, constructor |
| **Member access** | `public`, `protected` inherited; `private` never |
| **Constructor order** | Parent constructor always runs before child |
| **`Object` class** | Root of all Java classes — every class inherits it |