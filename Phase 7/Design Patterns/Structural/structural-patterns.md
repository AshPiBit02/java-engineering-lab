# Structural Design Patterns

Structural patterns deal with **how classes and objects are composed to form larger structures**, while keeping these structures flexible and efficient. They focus on simplifying relationships between entities — often by using composition/inheritance to combine interfaces or implementations.

> Companion to: `design_patterns_readme.md` (Creational Patterns)

---

## Index

1. [Adapter](#1-adapter)
2. [Bridge](#2-bridge)
3. [Composite](#3-composite)
4. [Decorator](#4-decorator)
5. [Facade](#5-facade)
6. [Flyweight](#6-flyweight)
7. [Proxy](#7-proxy)
8. [Quick Comparison Table](#quick-comparison-table)

---

## 1. Adapter

### Definition
Converts the interface of a class into another interface that clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

### Purpose
Bridge the gap between an existing (often legacy or third-party) class and the interface your code expects, **without modifying the existing class**.

### Use Case
- Integrating a third-party library whose API doesn't match your application's interface.
- Making legacy code compatible with new code.
- Example: Adapting a `MediaPlayer` interface to work with an old `LegacyAudioPlayer` (advanced/vlc format).

### Structure
`Client → Target (interface) ← Adapter → Adaptee (existing class)`

### Snippet (Java)
```java
// Existing incompatible class
class LegacyPrinter {
    void printOldFormat(String text) { System.out.println("[LEGACY] " + text); }
}

// Expected interface
interface ModernPrinter { void print(String text); }

// Adapter
class PrinterAdapter implements ModernPrinter {
    private final LegacyPrinter legacyPrinter;
    PrinterAdapter(LegacyPrinter legacyPrinter) { this.legacyPrinter = legacyPrinter; }
    public void print(String text) { legacyPrinter.printOldFormat(text); }
}
```

### Key Notes
- Two variants: **Object Adapter** (composition, shown above) and **Class Adapter** (inheritance — not possible in Java for multiple classes due to single inheritance).
- Doesn't change the Adaptee's code — respects Open/Closed Principle.

---

## 2. Bridge

### Definition
Decouples an abstraction from its implementation so the two can vary independently.

### Purpose
Avoid a permanent binding between abstraction and implementation; prevent a "class explosion" when you have multiple dimensions of variation (e.g., shape × color, device × remote).

### Use Case
- Cross-platform UI toolkits (abstraction = `Shape`, implementation = `DrawingAPI` for different OS renderers).
- Remote control (`Remote`) working across different devices (`TV`, `Radio`).

### Structure
`Abstraction --has-a--> Implementor (interface) ← ConcreteImplementorA/B`

### Snippet (Java)
```java
interface Device { void turnOn(); }
class TV implements Device { public void turnOn() { System.out.println("TV on"); } }
class Radio implements Device { public void turnOn() { System.out.println("Radio on"); } }

abstract class Remote {
    protected Device device;
    Remote(Device device) { this.device = device; }
    abstract void pressButton();
}
class BasicRemote extends Remote {
    BasicRemote(Device device) { super(device); }
    void pressButton() { device.turnOn(); }
}
```

### Key Notes
- Different from Adapter: Adapter makes unrelated things work together *after the fact*; Bridge is designed *upfront* to let abstraction/implementation evolve separately.
- Favors composition over inheritance.

---

## 3. Composite

### Definition
Composes objects into tree structures to represent part-whole hierarchies. Lets clients treat individual objects and compositions of objects uniformly.

### Purpose
Work with tree-like structures (files/folders, UI components, org charts) where a single leaf node and a group of nodes should be treated the same way through one interface.

### Use Case
- File system: `File` and `Folder` both implement `FileSystemItem`.
- UI: a `Panel` containing `Button`s and other `Panel`s.

### Structure
`Component (interface) ← Leaf` and `Component ← Composite (holds list of Component)`

### Snippet (Java)
```java
interface FileSystemItem { void showDetails(); }

class File implements FileSystemItem {
    private String name;
    File(String name) { this.name = name; }
    public void showDetails() { System.out.println("File: " + name); }
}

class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> items = new ArrayList<>();
    Folder(String name) { this.name = name; }
    void add(FileSystemItem item) { items.add(item); }
    public void showDetails() {
        System.out.println("Folder: " + name);
        for (FileSystemItem item : items) item.showDetails();
    }
}
```

### Key Notes
- Essential rule: Leaf and Composite must share the **same interface**.
- Recursive by nature — client code doesn't need to check `instanceof`.

---

## 4. Decorator

### Definition
Attaches additional responsibilities to an object dynamically, without altering its structure. Provides a flexible alternative to subclassing for extending functionality.

### Purpose
Add behavior/state to individual objects at runtime, keeping features composable rather than hardcoded via inheritance (avoids subclass explosion, e.g. `CoffeeWithMilkAndSugar`).

### Use Case
- Java I/O streams (`BufferedReader(new FileReader(...))`) — classic real example.
- Adding toppings to a coffee/pizza order; adding UI borders/scrollbars.

### Structure
`Component (interface) ← ConcreteComponent` and `Component ← Decorator (wraps a Component) ← ConcreteDecoratorA/B`

### Snippet (Java)
```java
interface Coffee { double cost(); String description(); }

class SimpleCoffee implements Coffee {
    public double cost() { return 2.0; }
    public String description() { return "Coffee"; }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
}

class MilkDecorator extends CoffeeDecorator {
    MilkDecorator(Coffee coffee) { super(coffee); }
    public double cost() { return coffee.cost() + 0.5; }
    public String description() { return coffee.description() + " + Milk"; }
}
// Usage: Coffee order = new MilkDecorator(new SimpleCoffee());
```

### Key Notes
- Decorator and wrapped object implement the **same interface**, so decorators can be stacked.
- Different from Adapter: Decorator adds behavior with same interface; Adapter changes interface.

---

## 5. Facade

### Definition
Provides a unified, simplified interface to a set of interfaces in a subsystem, making the subsystem easier to use.

### Purpose
Hide complexity of interacting subsystems behind one simple entry point; reduce coupling between client code and subsystem internals.

### Use Case
- A `HomeTheaterFacade.watchMovie()` that internally coordinates `Projector`, `SoundSystem`, `DVDPlayer`.
- Simplifying a complex library (e.g., ordering pipeline: `Inventory`, `Payment`, `Shipping` behind `OrderFacade`).

### Structure
`Client → Facade → Subsystem classes (A, B, C, ...)`

### Snippet (Java)
```java
class Projector { void on() { System.out.println("Projector ON"); } }
class SoundSystem { void on() { System.out.println("Sound ON"); } }

class HomeTheaterFacade {
    private Projector projector = new Projector();
    private SoundSystem sound = new SoundSystem();
    void watchMovie() {
        projector.on();
        sound.on();
        System.out.println("Enjoy the movie!");
    }
}
```

### Key Notes
- Facade doesn't add new functionality — only simplifies access.
- Subsystem classes remain usable directly for clients who need finer control (Facade doesn't hide them, just wraps).

---

## 6. Flyweight

### Definition
Uses sharing to support large numbers of fine-grained objects efficiently, by separating **intrinsic** (shared, immutable) state from **extrinsic** (context-specific, passed-in) state.

### Purpose
Reduce memory footprint when creating a huge number of similar objects.

### Use Case
- Text editors: character glyph objects shared across a document (font/style = intrinsic, position = extrinsic).
- Game development: rendering thousands of trees/particles sharing the same texture/model.

### Structure
`FlyweightFactory (caches/returns Flyweights) → Flyweight (intrinsic state) ; Client supplies extrinsic state per call`

### Snippet (Java)
```java
class TreeType { // intrinsic state — shared
    String name, color, texture;
    TreeType(String name, String color, String texture) {
        this.name = name; this.color = color; this.texture = texture;
    }
    void draw(int x, int y) { // extrinsic state passed in
        System.out.println("Drawing " + name + " at (" + x + "," + y + ")");
    }
}

class TreeFactory {
    private static Map<String, TreeType> cache = new HashMap<>();
    static TreeType getTreeType(String name, String color, String texture) {
        String key = name + color + texture;
        return cache.computeIfAbsent(key, k -> new TreeType(name, color, texture));
    }
}
```

### Key Notes
- Trade-off: saves memory at the cost of extra complexity (managing extrinsic state externally).
- Factory ensures objects are shared/reused, not duplicated.

---

## 7. Proxy

### Definition
Provides a surrogate or placeholder object that controls access to another object.

### Purpose
Add a control layer (access control, lazy loading, logging, caching) in front of the real object, without the client knowing the difference.

### Use Case
- **Virtual Proxy**: lazy-load a heavy image until it's actually needed.
- **Protection Proxy**: restrict access based on permissions.
- **Remote Proxy**: represent an object in a different address space (e.g., RPC stubs).

### Structure
`Client → Subject (interface) ← RealSubject` and `Subject ← Proxy (holds reference to RealSubject)`

### Snippet (Java)
```java
interface Image { void display(); }

class RealImage implements Image {
    private String filename;
    RealImage(String filename) { this.filename = filename; loadFromDisk(); }
    private void loadFromDisk() { System.out.println("Loading " + filename); }
    public void display() { System.out.println("Displaying " + filename); }
}

class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;
    ProxyImage(String filename) { this.filename = filename; }
    public void display() {
        if (realImage == null) realImage = new RealImage(filename); // lazy load
        realImage.display();
    }
}
```

### Key Notes
- Different from Decorator: Proxy controls **access** (may deny/delay it); Decorator adds **behavior**, always delegates.
- Different from Facade: Proxy has the *same* interface as the real object; Facade defines a *new*, simpler interface.

---

## Quick Comparison Table

| Pattern    | Intent (one line)                                  | Relationship Type        |
|------------|-----------------------------------------------------|---------------------------|
| Adapter    | Make incompatible interfaces work together          | Wraps one object          |
| Bridge     | Decouple abstraction from implementation             | Composition, two hierarchies |
| Composite  | Treat individual & grouped objects uniformly         | Tree structure             |
| Decorator  | Add responsibilities dynamically                     | Wraps, same interface      |
| Facade     | Simplify access to a complex subsystem                | Wraps many objects         |
| Flyweight  | Share state to save memory                            | Shared instances via factory |
| Proxy      | Control access to an object                           | Wraps, same interface      |

### Commonly Confused Pairs
- **Adapter vs Facade** → Adapter changes an interface to match an expected one; Facade simplifies (doesn't necessarily change) a complex interface.
- **Decorator vs Proxy** → Both wrap an object with the same interface; Decorator *adds* behavior, Proxy *controls* access.
- **Composite vs Decorator** → Both use recursive composition, but Composite is about part-whole hierarchies (many children), Decorator is about layering behavior (single wrapped object).

---

## Suggested Implementation Order (for practice)
1. Adapter — simplest to grasp
2. Decorator — builds on interface + composition idea
3. Facade — straightforward, low complexity
4. Composite — introduces recursion/tree handling
5. Proxy — similar shape to Decorator, different intent
6. Bridge — two-hierarchy thinking
7. Flyweight — most nuanced (needs a working factory/cache)