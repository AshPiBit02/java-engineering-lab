# Creational Design Patterns

Creational patterns deal with **object creation mechanisms**, trying to create objects in a manner suitable to the situation. They abstract the instantiation process, making a system independent of how its objects are created, composed, and represented.


---

## Index

1. [Singleton](#1-singleton)
2. [Factory Method](#2-factory-method)
3. [Abstract Factory](#3-abstract-factory)
4. [Builder](#4-builder)
5. [Prototype](#5-prototype)
6. [Quick Comparison Table](#quick-comparison-table)

---

## 1. Singleton

### Definition
Ensures a class has only one instance and provides a global point of access to it.

### Purpose
Control access to a shared resource where multiple instances would cause inconsistency, wasted resources, or conflicting state.

### Use Case
- Configuration manager, application settings.
- Logging service.
- Database connection pool / thread pool.

### Structure
`Client → Singleton.getInstance() → single shared instance`

### Snippet (Java — thread-safe lazy init)
```java
class Singleton {
    private static volatile Singleton instance;
    private Singleton() {} // private constructor blocks external instantiation

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) instance = new Singleton();
            }
        }
        return instance;
    }
}
```

### Key Notes
- Private constructor + static access method are mandatory.
- `volatile` + double-checked locking avoids race conditions in multithreading.
- Enum-based Singleton (`enum Singleton { INSTANCE; }`) is the simplest thread-safe form in Java, immune to reflection/serialization attacks.
- Considered an anti-pattern if overused — introduces global state, hides dependencies, complicates unit testing.

---

## 2. Factory Method

### Definition
Defines an interface for creating an object, but lets subclasses decide which class to instantiate.

### Purpose
Delegate instantiation logic to subclasses so the base class doesn't need to know concrete types in advance — decouples client code from concrete classes.

### Use Case
- A `Logistics` class where `RoadLogistics` creates `Truck`, `SeaLogistics` creates `Ship`.
- UI frameworks where a base `Dialog` lets platform-specific subclasses create the right `Button` type.

### Structure
`Creator (abstract, has factoryMethod()) ← ConcreteCreatorA/B` produce `Product (interface) ← ConcreteProductA/B`

### Snippet (Java)
```java
interface Notification { void notifyUser(); }

class SMSNotification implements Notification {
    public void notifyUser() { System.out.println("Sending SMS"); }
}
class EmailNotification implements Notification {
    public void notifyUser() { System.out.println("Sending Email"); }
}

abstract class NotificationCreator {
    abstract Notification createNotification(); // factory method

    void send() { // uses the product without knowing its concrete type
        Notification n = createNotification();
        n.notifyUser();
    }
}
class SMSCreator extends NotificationCreator {
    Notification createNotification() { return new SMSNotification(); }
}
```

### Key Notes
- Follows Open/Closed Principle — new product types added via new subclasses, no existing code changed.
- Different from Simple Factory (not a GoF pattern): Factory Method uses inheritance/polymorphism, Simple Factory is just a static helper with a switch/if-else.

---

## 3. Abstract Factory

### Definition
Provides an interface for creating **families of related or dependent objects** without specifying their concrete classes.

### Purpose
Ensure that products created together are compatible (e.g., all UI elements match one theme), and let entire product families be swapped by changing a single factory.

### Use Case
- Cross-platform UI kit: `WindowsFactory` produces `WindowsButton` + `WindowsCheckbox`; `MacFactory` produces `MacButton` + `MacCheckbox`.
- Database driver families (different DB → different `Connection`, `Command`, `Adapter` implementations).

### Structure
`AbstractFactory (interface) ← ConcreteFactory1/2`, each producing a matching set of `AbstractProductA` / `AbstractProductB` implementations.

### Snippet (Java)
```java
interface Button { void render(); }
interface Checkbox { void render(); }

class WindowsButton implements Button { public void render() { System.out.println("Windows Button"); } }
class WindowsCheckbox implements Checkbox { public void render() { System.out.println("Windows Checkbox"); } }

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}
class WindowsFactory implements GUIFactory {
    public Button createButton() { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}
```

### Key Notes
- Factory Method creates **one** product; Abstract Factory creates a **family** of related products (often implemented using multiple Factory Methods internally).
- Adding a new product family = new concrete factory (easy). Adding a new product type to existing families = must change the `GUIFactory` interface and all its implementations (harder).

---

## 4. Builder

### Definition
Separates the construction of a complex object from its representation, so the same construction process can create different representations. Builds an object step-by-step.

### Purpose
Avoid a constructor with too many parameters ("telescoping constructor" problem) and allow optional parameters/step-by-step construction with clear, readable code.

### Use Case
- Building a `House` with optional garage, garden, pool, etc.
- Constructing complex `HttpRequest` objects (headers, body, params, method — many optional fields).
- SQL query builders.

### Structure
`Director (optional, orchestrates steps) → Builder (interface) ← ConcreteBuilder → builds Product`

### Snippet (Java — fluent builder, common in practice)
```java
class Pizza {
    private final String size;
    private final boolean cheese;
    private final boolean pepperoni;

    private Pizza(Builder b) {
        this.size = b.size; this.cheese = b.cheese; this.pepperoni = b.pepperoni;
    }

    static class Builder {
        private String size;
        private boolean cheese, pepperoni;

        Builder(String size) { this.size = size; } // required field
        Builder addCheese() { this.cheese = true; return this; }
        Builder addPepperoni() { this.pepperoni = true; return this; }
        Pizza build() { return new Pizza(this); }
    }
}
// Usage: Pizza p = new Pizza.Builder("Large").addCheese().addPepperoni().build();
```

### Key Notes
- The fluent/chained style (`return this`) is a common real-world variant, though GoF's original design uses a separate `Director` class to define build order/recipes.
- Product's constructor is often `private`, forcing use of the Builder.
- Different from Abstract Factory: Builder constructs a complex object step-by-step over several calls; Abstract Factory returns the product immediately, fully built, in one call.

---

## 5. Prototype

### Definition
Specifies the kind of objects to create using a prototypical instance, and creates new objects by **copying/cloning** this prototype rather than instantiating from scratch.

### Purpose
Avoid the cost of creating an object from scratch when creation is expensive (heavy initialization, DB/network calls) — clone an existing, fully-configured instance instead.

### Use Case
- Cloning a pre-configured game character/enemy template for spawning many similar instances.
- Copying a complex object graph (e.g., a document with nested formatting) instead of rebuilding it.

### Structure
`Prototype (interface with clone()) ← ConcretePrototypeA/B` — client asks an existing object to clone itself.

### Snippet (Java)
```java
abstract class Shape implements Cloneable {
    String color;
    abstract void draw();

    public Shape clone() {
        try { return (Shape) super.clone(); } // shallow copy
        catch (CloneNotSupportedException e) { throw new RuntimeException(e); }
    }
}
class Circle extends Shape {
    void draw() { System.out.println("Drawing " + color + " circle"); }
}
// Usage: Circle original = new Circle(); original.color = "red";
//        Circle copy = (Circle) original.clone();
```

### Key Notes
- Watch for **shallow vs deep copy**: `super.clone()` copies primitive/reference fields as-is; mutable nested objects (e.g., a `List` field) will be *shared*, not duplicated, unless you manually deep-copy them.
- Useful when subclassing to create objects is more complex/costly than copying an existing instance.

---

## Quick Comparison Table

| Pattern           | Intent (one line)                                          | Creation Style                     |
|--------------------|--------------------------------------------------------------|--------------------------------------|
| Singleton          | Ensure only one instance exists                              | Controlled single instance          |
| Factory Method     | Let subclasses decide which class to instantiate             | One product, via inheritance         |
| Abstract Factory   | Create families of related objects                            | Multiple related products            |
| Builder            | Construct complex objects step-by-step                        | Step-by-step, same process → variants |
| Prototype          | Create new objects by cloning an existing instance             | Copy/clone                           |

### Commonly Confused Pairs
- **Factory Method vs Abstract Factory** → Factory Method = one product via subclassing; Abstract Factory = a family of related products via composition (often built from several Factory Methods).
- **Builder vs Abstract Factory** → Builder focuses on constructing one complex object incrementally (order matters); Abstract Factory returns fully-built related objects immediately (order doesn't matter).
- **Prototype vs Factory Method** → Factory Method creates a new instance via `new` inside subclasses; Prototype creates a new instance by **cloning** an existing object (no subclassing needed).

---

## Suggested Implementation Order (for practice)
1. Singleton — simplest, single class
2. Factory Method — introduces polymorphic creation
3. Builder — practice fluent/step-by-step construction
4. Prototype — practice shallow vs deep clone
5. Abstract Factory — most complex, combines multiple factory methods into families