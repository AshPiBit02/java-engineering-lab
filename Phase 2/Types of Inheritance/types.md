# 🌳 Types of Inheritance in Java


---

## 📌 What is Inheritance? *(quick recap)*

Inheritance allows a child class to acquire the properties and behaviors of a parent class using the `extends` keyword. Java supports **5 types of inheritance** — but not all are supported directly through classes.

```
┌──────────────────────────────────────────────────────────────┐
│                 Types of Inheritance in Java                 │
│                                                              │
│   ┌─────────────┐  ┌─────────────┐  ┌──────────────────┐    │
│   │   Single    │  │ Multilevel  │  │  Hierarchical    │    │
│   │  (via class)│  │ (via class) │  │   (via class)    │    │
│   └─────────────┘  └─────────────┘  └──────────────────┘    │
│                                                              │
│   ┌─────────────┐  ┌─────────────┐                          │
│   │  Multiple   │  │   Hybrid    │                          │
│   │ ❌ classes  │  │ ❌ classes  │                          │
│   │ ✅ interface│  │ ✅ interface│                          │
│   └─────────────┘  └─────────────┘                          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Types of Inheritance and Their Support in Java**

---

## 1. 🔹 Single Inheritance

A class inherits from **exactly one parent class**.

```
      A
      │
      B
```

```java
class Animal {
    void eat() { System.out.println("Eating..."); }
}

class Dog extends Animal {        // Dog inherits Animal only
    void bark() { System.out.println("Barking..."); }
}

Dog d = new Dog();
d.eat();    // ✅ inherited
d.bark();   // ✅ own method
```

```
┌──────────────────────────────────────────────────────────────┐
│                    Single Inheritance                        │
│                                                              │
│              ┌──────────────────┐                            │
│              │     Animal       │  ← parent                 │
│              │  eat()           │                            │
│              └────────┬─────────┘                            │
│                       │  extends                            │
│              ┌────────▼─────────┐                            │
│              │       Dog        │  ← child                  │
│              │  eat() inherited │                            │
│              │  bark() own      │                            │
│              └──────────────────┘                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Single Inheritance**

---

## 2. 🔹 Multilevel Inheritance

A class inherits from a parent, which itself inherits from another parent — forming a **chain**.

```
      A
      │
      B
      │
      C
```

```java
class Animal {
    void eat() { System.out.println("Eating..."); }
}

class Dog extends Animal {
    void bark() { System.out.println("Barking..."); }
}

class Puppy extends Dog {        // Puppy → Dog → Animal
    void weep() { System.out.println("Weeping..."); }
}

Puppy p = new Puppy();
p.eat();    // ✅ inherited from Animal (via Dog)
p.bark();   // ✅ inherited from Dog
p.weep();   // ✅ own method
```

```
┌──────────────────────────────────────────────────────────────┐
│                  Multilevel Inheritance                      │
│                                                              │
│              ┌──────────────────┐                            │
│              │     Animal       │  ← grandparent            │
│              │  eat()           │                            │
│              └────────┬─────────┘                            │
│                       │  extends                            │
│              ┌────────▼─────────┐                            │
│              │       Dog        │  ← parent                 │
│              │  eat() inherited │                            │
│              │  bark() own      │                            │
│              └────────┬─────────┘                            │
│                       │  extends                            │
│              ┌────────▼─────────┐                            │
│              │      Puppy       │  ← child                  │
│              │  eat()  inherited│                            │
│              │  bark() inherited│                            │
│              │  weep() own      │                            │
│              └──────────────────┘                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Multilevel Inheritance Chain**

---

## 3. 🔹 Hierarchical Inheritance

**Multiple child classes** inherit from a **single parent class**.

```
         A
        / \
       B   C
```

```java
class Animal {
    void eat() { System.out.println("Eating..."); }
}

class Dog extends Animal {
    void bark() { System.out.println("Barking..."); }
}

class Cat extends Animal {        // Cat also inherits Animal
    void meow() { System.out.println("Meowing..."); }
}

Dog d = new Dog();
d.eat();    // ✅ inherited
d.bark();   // ✅ own

Cat c = new Cat();
c.eat();    // ✅ inherited
c.meow();   // ✅ own
```

```
┌──────────────────────────────────────────────────────────────┐
│                Hierarchical Inheritance                      │
│                                                              │
│                ┌──────────────────┐                          │
│                │      Animal      │  ← parent               │
│                │  eat()           │                          │
│                └────────┬─────────┘                          │
│                         │                                   │
│            ┌────────────┴────────────┐                       │
│            │                         │                      │
│   ┌────────▼─────────┐   ┌───────────▼──────┐               │
│   │       Dog        │   │       Cat        │               │
│   │  eat() inherited │   │  eat() inherited │               │
│   │  bark() own      │   │  meow() own      │               │
│   └──────────────────┘   └──────────────────┘               │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Hierarchical Inheritance**

---

## 4. 🔹 Multiple Inheritance

A class inherits from **more than one parent class**.

```
     A     B
      \   /
        C
```

> ❌ **Java does NOT support multiple inheritance through classes.**

### Why? — The Diamond Problem

```
┌──────────────────────────────────────────────────────────────┐
│                  The Diamond Problem                         │
│                                                              │
│         ┌──────────────────┐                                 │
│         │        A         │   void show() { "A" }          │
│         └────────┬─────────┘                                 │
│                  │                                           │
│       ┌──────────┴──────────┐                                │
│       │                     │                               │
│  ┌────▼─────┐          ┌────▼─────┐                          │
│  │    B     │          │    C     │                          │
│  │ show()   │          │ show()   │   both override show()   │
│  └────┬─────┘          └────┬─────┘                          │
│       │                     │                               │
│       └──────────┬──────────┘                                │
│                  │                                           │
│             ┌────▼─────┐                                     │
│             │    D     │   which show() to use? A, B or C?  │
│             │  ❓❓❓  │   → AMBIGUITY = Diamond Problem     │
│             └──────────┘                                     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — The Diamond Problem — Why Multiple Class Inheritance is Forbidden**

### ✅ Solution — Multiple Inheritance via Interfaces

Java allows a class to **implement multiple interfaces** — achieving multiple inheritance safely.

```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {   // multiple interfaces ✅
    public void fly()  { System.out.println("Duck flying..."); }
    public void swim() { System.out.println("Duck swimming..."); }
}

Duck d = new Duck();
d.fly();    // ✅
d.swim();   // ✅
```

> 🆚 **C++ vs Java** — C++ supports multiple class inheritance directly but suffers from the diamond problem. Java **eliminates** it by restricting multiple inheritance to interfaces only.

---

## 5. 🔹 Hybrid Inheritance

A **combination** of two or more types of inheritance.

```
         A
        / \
       B   C
        \ /
         D
```

> ❌ **Not supported in Java through classes** — leads to the diamond problem.
> ✅ **Achievable through interfaces** in Java.

```java
interface A { void methodA(); }
interface B extends A { void methodB(); }
interface C extends A { void methodC(); }

class D implements B, C {         // hybrid via interfaces ✅
    public void methodA() { System.out.println("A"); }
    public void methodB() { System.out.println("B"); }
    public void methodC() { System.out.println("C"); }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│                   Hybrid Inheritance                         │
│                                                              │
│          ┌──────────────────┐                                │
│          │   Interface A    │                                │
│          └────────┬─────────┘                                │
│                   │                                          │
│        ┌──────────┴──────────┐                               │
│        │                     │                              │
│  ┌─────▼──────┐        ┌─────▼──────┐                        │
│  │Interface B │        │Interface C │                        │
│  └─────┬──────┘        └─────┬──────┘                        │
│        │                     │                              │
│        └──────────┬──────────┘                               │
│                   │  implements                             │
│            ┌──────▼──────┐                                   │
│            │   Class D   │  ← implements B and C ✅          │
│            └─────────────┘                                   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Hybrid Inheritance via Interfaces**

---

## 📊 Inheritance Types — At a Glance

| Type | Structure | Supported via Classes | Supported via Interface |
|------|-----------|:---------------------:|:-----------------------:|
| Single | A → B | ✅ | ✅ |
| Multilevel | A → B → C | ✅ | ✅ |
| Hierarchical | A → B, A → C | ✅ | ✅ |
| Multiple | A + B → C | ❌ Diamond Problem | ✅ |
| Hybrid | combination | ❌ | ✅ |

---

## 🆚 C++ vs Java — Inheritance Types

| Feature | C++ | Java |
|---------|-----|------|
| Single | ✅ | ✅ |
| Multilevel | ✅ | ✅ |
| Hierarchical | ✅ | ✅ |
| Multiple (classes) | ✅ (diamond problem possible) | ❌ |
| Multiple (interfaces) | ✅ (virtual inheritance) | ✅ |
| Hybrid | ✅ (with virtual) | ✅ (via interfaces) |

> 🆚 **Key Difference** — C++ tries to solve the diamond problem using **virtual inheritance** (`virtual` keyword). Java avoids it entirely by **disallowing multiple class inheritance** and allowing it only through interfaces.

---

## 💡 Summary

| Type | Key Point |
|------|-----------|
| **Single** | One parent, one child — simplest form |
| **Multilevel** | Chain — A → B → C, each level extends previous |
| **Hierarchical** | One parent, many children |
| **Multiple** | Many parents → one child — ❌ classes, ✅ interfaces |
| **Hybrid** | Mix of types — ❌ classes, ✅ interfaces |
| **Diamond Problem** | Ambiguity when two parents have same method — reason Java restricts multiple class inheritance |