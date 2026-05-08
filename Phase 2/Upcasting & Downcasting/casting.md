# 🔃 Upcasting & Downcasting in Java


---

## 📌 What is Type Casting in OOP?

In Java, **type casting** in the context of inheritance means treating an object of one class as an object of **another class in the same hierarchy**. There are two directions:

```
┌──────────────────────────────────────────────────────────────┐
│               Casting in Inheritance Hierarchy               │
│                                                              │
│         ┌──────────────────┐                                 │
│         │   Parent Class   │  ← superclass                   │
│         └────────┬─────────┘                                 │
│                  │                                           │
│         ┌────────▼─────────┐                                 │
│         │   Child Class    │  ← subclass                     │
│         └──────────────────┘                                 │
│                                                              │
│   Upcasting   →  Child to Parent  (going UP)   ✅ safe      │
│   Downcasting →  Parent to Child  (going DOWN) ⚠️ careful   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Direction of Upcasting and Downcasting**

---

## 1. ⬆️ Upcasting

**Upcasting** is casting a **child class object** to a **parent class reference**.

> Child IS-A Parent → safe to treat child as parent.

### Syntax

```java
Parent ref = new Child();           // implicit — no cast operator needed
Parent ref = (Parent) new Child();  // explicit — also valid but unnecessary
```

### Example

```java
class Animal {
    void eat() { System.out.println("Animal eating..."); }
}

class Dog extends Animal {
    void bark() { System.out.println("Dog barking..."); }
}

// Upcasting — Dog object stored in Animal reference
Animal a = new Dog();     // ✅ implicit, automatic

a.eat();    // ✅ works — eat() is in Animal
a.bark();   // ❌ compile error — bark() not visible via Animal ref
```

```
┌──────────────────────────────────────────────────────────────┐
│                     Upcasting in Memory                      │
│                                                              │
│   Animal a = new Dog();                                      │
│                                                              │
│   Stack                         Heap                         │
│   ┌──────────────┐              ┌──────────────────────┐     │
│   │ a  (Animal)  │─────────────►│   Dog Object         │     │
│   │ reference    │              │   ┌──────────────┐   │     │
│   └──────────────┘              │   │ Animal part  │   │     │
│                                 │   │  eat()       │   │     │
│                                 │   ├──────────────┤   │     │
│                                 │   │  Dog part    │   │     │
│                                 │   │  bark()      │   │     │
│                                 │   └──────────────┘   │     │
│                                 └──────────────────────┘     │
│                                                              │
│   Via 'a' → only Animal part visible                         │
│   Dog part exists in heap but NOT accessible via 'a'         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Upcasting: Only Parent Part Accessible via Reference**

### Key Points

- Done **implicitly** — no explicit cast required
- **Compile-time** type is `Animal` — only `Animal` methods accessible
- **Runtime** type is still `Dog` — object remains a Dog in memory
- Access to child-specific methods is **lost** via parent reference
- **Never fails** — always safe

---

## 2. ⬇️ Downcasting

**Downcasting** is casting a **parent class reference** (that actually holds a child object) back to a **child class reference**.

> Recovers access to the child-specific methods lost during upcasting.

### Syntax

```java
Child ref = (Child) parentRef;    // explicit cast operator required
```

### Example

```java
class Animal {
    void eat() { System.out.println("Animal eating..."); }
}

class Dog extends Animal {
    void bark() { System.out.println("Dog barking..."); }
}

Animal a = new Dog();     // upcasting first

// Downcasting — recover Dog reference
Dog d = (Dog) a;          // ✅ explicit cast required

d.eat();    // ✅ inherited
d.bark();   // ✅ now accessible again after downcast
```

```
┌──────────────────────────────────────────────────────────────┐
│                    Downcasting in Memory                     │
│                                                              │
│   Animal a = new Dog();    // upcast                         │
│   Dog d = (Dog) a;         // downcast                       │
│                                                              │
│   Stack                         Heap                         │
│   ┌──────────────┐              ┌──────────────────────┐     │
│   │ a  (Animal)  │─────────────►│   Dog Object         │     │
│   ├──────────────┤              │   ┌──────────────┐   │     │
│   │ d  (Dog)     │─────────────►│   │ Animal part  │   │     │
│   └──────────────┘              │   │  eat()       │   │     │
│                                 │   ├──────────────┤   │     │
│                                 │   │  Dog part    │   │     │
│                                 │   │  bark() ✅  │   │     │
│                                 │   └──────────────┘   │     │
│                                 └──────────────────────┘     │
│                                                              │
│   Via 'd' → full Dog object accessible (both parts)          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Downcasting: Full Child Access Restored**

### ⚠️ ClassCastException — Invalid Downcast

Downcasting fails at **runtime** if the object is not actually an instance of the target class.

```java
Animal a = new Animal();   // NOT a Dog — just an Animal

Dog d = (Dog) a;           // ❌ ClassCastException at runtime!
```

```
┌──────────────────────────────────────────────────────────────┐
│               Valid vs Invalid Downcast                      │
│                                                              │
│   Animal a = new Dog();                                      │
│   Dog d = (Dog) a;       ✅ valid — actual object IS a Dog  │
│                                                              │
│   Animal a = new Animal();                                   │
│   Dog d = (Dog) a;       ❌ ClassCastException              │
│                              actual object is NOT a Dog      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Valid vs Invalid Downcast**

---

## 🛡️ `instanceof` — Safe Downcasting

Always check with `instanceof` before downcasting to avoid `ClassCastException`.

```java
Animal a = new Dog();

if (a instanceof Dog) {        // check before cast
    Dog d = (Dog) a;           // ✅ safe
    d.bark();
}
```

### Pattern Matching `instanceof` (Java 16+)

```java
// Old way
if (a instanceof Dog) {
    Dog d = (Dog) a;
    d.bark();
}

// New way — Java 16+ (cleaner)
if (a instanceof Dog d) {      // cast + assign in one line
    d.bark();
}
```

---

## 🎭 Runtime Polymorphism with Upcasting

The most **powerful use** of upcasting — calling overridden methods on parent references resolves to the **actual object's method at runtime**.

```java
class Animal {
    void sound() { System.out.println("Generic sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Woof!"); }
}

class Cat extends Animal {
    @Override
    void sound() { System.out.println("Meow!"); }
}

Animal a1 = new Dog();
Animal a2 = new Cat();

a1.sound();   // Woof!  ← Dog's method, resolved at runtime
a2.sound();   // Meow!  ← Cat's method, resolved at runtime
```

```
┌──────────────────────────────────────────────────────────────┐
│              Runtime Polymorphism via Upcasting              │
│                                                              │
│   Animal ref  →  Dog object  →  a.sound() = "Woof!"          │
│   Animal ref  →  Cat object  →  a.sound() = "Meow!"          │
│   Animal ref  →  Bird object →  a.sound() = "Tweet!"         │
│                                                              │
│   Same reference type (Animal)                               │
│   Different actual objects                                   │
│   Different method behavior at runtime  ← polymorphism       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Runtime Polymorphism via Upcasting**

---

## 🔄 Full Upcasting → Downcasting Flow

```
┌──────────────────────────────────────────────────────────────┐
│                    Complete Cast Flow                        │
│                                                              │
│   Dog d1 = new Dog();      // original Dog object            │
│         │                                                    │
│         │  upcast (implicit)                                 │
│         ▼                                                    │
│   Animal a = d1;           // only Animal methods visible    │
│         │                                                    │
│         │  downcast (explicit)                               │
│         ▼                                                    │
│   Dog d2 = (Dog) a;        // Dog methods restored ✅       │
│                                                              │
│   d1 and d2 point to the same object in heap                 │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Full Up → Down Cast Flow**

---

## ❓ Why Use Upcasting?

| Reason | Explanation |
|--------|-------------|
| **Runtime Polymorphism** | Call overridden methods on parent reference — JVM decides which at runtime |
| **Generalization** | Write methods that accept `Animal` and work for `Dog`, `Cat`, `Bird` etc. |
| **Collections** | Store different subclass objects in one `List<Animal>` |
| **Loose coupling** | Code depends on parent type, not specific child — easier to extend |

```java
// Accept any Animal subclass — no need to write one for each
void makeSound(Animal a) {
    a.sound();    // works for Dog, Cat, Bird — any subclass
}

makeSound(new Dog());    // Woof!
makeSound(new Cat());    // Meow!
```

---

## ❓ Why Use Downcasting?

| Reason | Explanation |
|--------|-------------|
| **Access child methods** | After upcasting, child-specific methods are hidden — downcast to get them back |
| **Specific behavior** | When you need a method that only exists in the child class |
| **After `instanceof` check** | Safely retrieve the specific type from a general reference |

```java
List<Animal> animals = new ArrayList<>();
animals.add(new Dog());
animals.add(new Cat());

for (Animal a : animals) {
    if (a instanceof Dog d) {
        d.bark();     // Dog-specific behavior
    } else if (a instanceof Cat c) {
        c.meow();     // Cat-specific behavior
    }
}
```

---

## 🆚 Upcasting vs Downcasting

| Feature | Upcasting | Downcasting |
|---------|-----------|-------------|
| Direction | Child → Parent | Parent → Child |
| Cast operator | ❌ Not required (implicit) | ✅ Required (explicit) |
| Safety | ✅ Always safe | ⚠️ Can fail at runtime |
| Exception risk | ❌ None | ✅ `ClassCastException` possible |
| Methods accessible | Parent methods only | Both parent + child methods |
| When resolved | Compile-time (type) | Runtime (actual object) |
| Check recommended | ❌ Not needed | ✅ Use `instanceof` |

---

## 🌍 Real-World Applications

### 1. Processing a Mixed Collection

```java
List<Shape> shapes = new ArrayList<>();
shapes.add(new Circle(5));
shapes.add(new Rectangle(3, 4));

for (Shape s : shapes) {
    System.out.println(s.area());   // upcasting — polymorphism
}
```

### 2. GUI Frameworks (AWT / Swing)

```java
// All UI components stored as Component references
Component btn = new Button("Click");   // upcasting
Component lbl = new Label("Hello");

// Downcast when specific behavior needed
if (btn instanceof Button b) {
    b.setLabel("Submit");   // Button-specific method
}
```

### 3. Factory Pattern

```java
Animal getAnimal(String type) {
    if (type.equals("dog")) return new Dog();   // upcast automatically
    if (type.equals("cat")) return new Cat();
    return new Animal();
}

Animal a = getAnimal("dog");   // caller gets Animal ref
a.sound();                     // Woof! — runtime polymorphism
```

### 4. Dependency Injection / Loose Coupling

```java
// Program to parent type — not specific child
void processPayment(Payment p) {   // works for CreditCard, PayPal, UPI
    p.pay();
}

processPayment(new CreditCard());
processPayment(new PayPal());
```

---

## 🆚 C++ vs Java

| Feature | C++ | Java |
|---------|-----|------|
| Upcasting | Implicit | Implicit |
| Downcasting | `static_cast<>` / `dynamic_cast<>` | `(ChildType)` explicit cast |
| Safe downcast check | `dynamic_cast<>` returns `nullptr` on fail | `instanceof` before cast |
| Runtime error on bad cast | Undefined behavior (`static_cast`) | `ClassCastException` |
| Pattern matching cast | ❌ | ✅ Java 16+ `instanceof Type var` |

> 🆚 **Key Difference** — C++ has two cast operators: `static_cast` (no runtime check, faster) and `dynamic_cast` (runtime check, safer). Java always does a **runtime check** on explicit downcasts and throws `ClassCastException` if invalid — safer by default.

---

## 💡 Summary

| Concept | Key Point |
|---------|-----------|
| **Upcasting** | Child → Parent, implicit, always safe, loses child methods |
| **Downcasting** | Parent → Child, explicit, can fail, restores child methods |
| **`instanceof`** | Always check before downcast to avoid `ClassCastException` |
| **Runtime Polymorphism** | Biggest benefit of upcasting — overridden methods resolve at runtime |
| **`ClassCastException`** | Thrown when downcasting to wrong type at runtime |
| **Java 16+** | Pattern matching `instanceof` — cast + check in one line |

```
Upcast   →  generalize  →  polymorphism  →  always safe
Downcast →  specialize  →  child access  →  check instanceof first
```