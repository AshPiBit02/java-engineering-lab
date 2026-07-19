# Behavioral Design Patterns

Behavioral patterns focus on **communication and responsibility between objects** — how objects interact, distribute responsibility, and encapsulate behavior/algorithms that can vary independently.

---

## Index

1. [Strategy](#1-strategy)
2. [Observer](#2-observer)
3. [Command](#3-command)
4. [State](#4-state)
5. [Template Method](#5-template-method)
6. [Iterator](#6-iterator)
7. [Chain of Responsibility](#7-chain-of-responsibility)
8. [Mediator](#8-mediator)
9. [Memento](#9-memento)
10. [Visitor](#10-visitor)
11. [Interpreter](#11-interpreter)
12. [Quick Comparison Table](#quick-comparison-table)

---

## 1. Strategy

### Definition
Defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime.

### Purpose
Let an algorithm vary independently from the clients that use it — avoid conditional (`if/else`, `switch`) blocks for selecting behavior.

### Use Case
- Payment methods: `CreditCardStrategy`, `PayPalStrategy`, `CryptoStrategy` swapped at checkout.
- Sorting: different comparison strategies passed into a sort routine.

### Snippet (Java)
```java
interface PaymentStrategy { void pay(double amount); }

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) { System.out.println("Paid " + amount + " via Credit Card"); }
}
class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) { System.out.println("Paid " + amount + " via PayPal"); }
}

class Checkout {
    private PaymentStrategy strategy;
    Checkout(PaymentStrategy strategy) { this.strategy = strategy; }
    void process(double amount) { strategy.pay(amount); }
}
```

### Key Notes
- Context holds a reference to a Strategy interface, not a concrete implementation — swap via constructor/setter.
- Very similar in structure to **State** (see below) — the difference is in *intent*, not code shape.

---

## 2. Observer

### Definition
Defines a one-to-many dependency between objects so that when one object (Subject) changes state, all its dependents (Observers) are notified automatically.

### Purpose
Enable event-driven communication without tightly coupling the source of change to the code that reacts to it.

### Use Case
- UI event listeners (button click notifies all registered handlers).
- Pub/sub systems, stock price tickers notifying multiple displays.

### Snippet (Java)
```java
interface Observer { void update(String data); }

class Subject {
    private List<Observer> observers = new ArrayList<>();
    void subscribe(Observer o) { observers.add(o); }
    void notifyAll(String data) { for (Observer o : observers) o.update(data); }
}
class EmailSubscriber implements Observer {
    public void update(String data) { System.out.println("Email alert: " + data); }
}
```

### Key Notes
- Java's built-in `java.util.Observer`/`Observable` are deprecated since Java 9 — implement manually or use `PropertyChangeListener` / reactive libraries in real projects.
- Watch for memory leaks: observers must be explicitly unsubscribed, or the subject holds references forever.

---

## 3. Command

### Definition
Encapsulates a request (action + receiver + parameters) as an object, allowing you to parameterize clients with queues, requests, and operations, and to support undo/redo.

### Purpose
Decouple the object that invokes an operation from the one that knows how to perform it; enable queuing, logging, and undoable operations.

### Use Case
- GUI buttons/menu items bound to `Command` objects (`CopyCommand`, `PasteCommand`).
- Undo/redo stacks in editors.
- Task queues / job scheduling.

### Snippet (Java)
```java
interface Command { void execute(); }

class Light {
    void on() { System.out.println("Light ON"); }
}
class LightOnCommand implements Command {
    private Light light;
    LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
}
class RemoteControl {
    private Command command;
    void setCommand(Command command) { this.command = command; }
    void pressButton() { command.execute(); }
}
```

### Key Notes
- The **Receiver** (`Light`) contains the actual logic; the **Command** just binds a receiver + action together.
- Add an `undo()` method to the interface if undo/redo support is needed — often paired with **Memento** for state restoration.

---

## 4. State

### Definition
Allows an object to alter its behavior when its internal state changes — the object appears to change its class.

### Purpose
Replace large conditional blocks that check state (`if (state == X)`) with polymorphic state objects, each encapsulating behavior for that state.

### Use Case
- Traffic light (`RedState`, `GreenState`, `YellowState`).
- Media player (`PlayingState`, `PausedState`, `StoppedState`) where the same button behaves differently per state.
- Order lifecycle (`Pending`, `Shipped`, `Delivered`, `Cancelled`).

### Snippet (Java)
```java
interface State { void handle(Context context); }

class PendingState implements State {
    public void handle(Context context) {
        System.out.println("Order Pending → shipping now");
        context.setState(new ShippedState());
    }
}
class ShippedState implements State {
    public void handle(Context context) { System.out.println("Order already Shipped"); }
}
class Context {
    private State state = new PendingState();
    void setState(State state) { this.state = state; }
    void request() { state.handle(this); }
}
```

### Key Notes
- Structurally almost identical to Strategy; **intent differs**: State objects know about and trigger transitions to other states; Strategy objects are independent and chosen externally by the client.
- Each state class typically knows which state(s) can follow it.

---

## 5. Template Method

### Definition
Defines the skeleton of an algorithm in a base class method, deferring some steps to subclasses without changing the algorithm's overall structure.

### Purpose
Reuse common algorithm structure while letting subclasses customize specific steps — enforces "don't call us, we'll call you" (Hollywood Principle).

### Use Case
- Data processing pipelines: `readData()` → `process()` → `saveResult()`, where `process()` varies by subclass (CSV vs JSON parser).
- Framework lifecycle methods (e.g., test framework's `setUp() → runTest() → tearDown()`).

### Snippet (Java)
```java
abstract class DataProcessor {
    // template method — final so subclasses can't change the sequence
    final void process() {
        readData();
        transformData();
        saveData();
    }
    abstract void readData();
    abstract void transformData();
    void saveData() { System.out.println("Saving to DB (default)"); } // optional hook
}
class CSVProcessor extends DataProcessor {
    void readData() { System.out.println("Reading CSV"); }
    void transformData() { System.out.println("Transforming CSV rows"); }
}
```

### Key Notes
- The template method itself is usually marked `final` to prevent overriding the overall algorithm structure.
- Subclasses override only the abstract "steps," not the orchestration — differs from Strategy, which swaps the *entire* algorithm via composition rather than inheritance.

---

## 6. Iterator

### Definition
Provides a way to access elements of a collection sequentially without exposing its underlying representation.

### Purpose
Decouple traversal logic from the collection itself, and support multiple simultaneous traversals with different cursors.

### Use Case
- Custom collection classes needing `for-each` support.
- Traversing a tree/graph in different orders (in-order, pre-order) without exposing internal node structure.

### Snippet (Java)
```java
class NameCollection implements Iterable<String> {
    private String[] names;
    NameCollection(String[] names) { this.names = names; }

    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;
            public boolean hasNext() { return index < names.length; }
            public String next() { return names[index++]; }
        };
    }
}
// Usage: for (String name : new NameCollection(new String[]{"A","B"})) { ... }
```

### Key Notes
- Java already has this baked in via `Iterable`/`Iterator` interfaces — implementing them lets your custom class work with `for-each` loops and Streams.
- Keeps single-responsibility: collection manages storage, iterator manages traversal state.

---

## 7. Chain of Responsibility

### Definition
Passes a request along a chain of handlers; each handler decides either to process the request or pass it to the next handler in the chain.

### Purpose
Decouple sender from receiver, allowing multiple objects a chance to handle a request without the sender knowing which one will.

### Use Case
- Middleware pipelines (auth check → validation → logging → controller).
- Support ticket escalation (Level1 → Level2 → Manager).
- Java Servlet Filters.

### Snippet (Java)
```java
abstract class Handler {
    protected Handler next;
    Handler setNext(Handler next) { this.next = next; return next; }
    abstract void handle(int request);
}
class LowLevelHandler extends Handler {
    void handle(int request) {
        if (request < 10) System.out.println("Handled by LowLevel");
        else if (next != null) next.handle(request);
    }
}
```

### Key Notes
- Each handler must have a reference to the next one (or `null` to terminate the chain).
- If no handler processes the request, ensure a default/fallback behavior exists — otherwise requests can silently fall through.

---

## 8. Mediator

### Definition
Defines an object that encapsulates how a set of objects interact, promoting loose coupling by preventing objects from referring to each other directly.

### Purpose
Reduce chaotic many-to-many dependencies between components by centralizing communication through one mediator object.

### Use Case
- Chat room: users send messages via a `ChatMediator` rather than directly to each other.
- UI dialogs where multiple widgets (checkbox, textbox, button) need to react to each other's changes.
- Air traffic control coordinating planes.

### Snippet (Java)
```java
interface ChatMediator { void sendMessage(String msg, User sender); }

class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();
    void addUser(User u) { users.add(u); }
    public void sendMessage(String msg, User sender) {
        for (User u : users) if (u != sender) u.receive(msg);
    }
}
class User {
    String name; ChatMediator mediator;
    User(String name, ChatMediator mediator) { this.name = name; this.mediator = mediator; }
    void send(String msg) { mediator.sendMessage(msg, this); }
    void receive(String msg) { System.out.println(name + " received: " + msg); }
}
```

### Key Notes
- Components (`User`) only know the Mediator, not each other — reduces coupling but can turn the Mediator into a "god object" if it grows unchecked.
- Similar in spirit to Facade, but Mediator centralizes **peer-to-peer communication**, while Facade simplifies **client-to-subsystem** access.

---

## 9. Memento

### Definition
Captures and externalizes an object's internal state without violating encapsulation, so the object can be restored to this state later.

### Purpose
Support undo/rollback functionality without exposing the internal structure of the object being saved.

### Use Case
- Undo feature in text editors.
- Game save/checkpoint systems.
- Often paired with **Command** to implement undoable operations.

### Snippet (Java)
```java
class EditorMemento {
    private final String content;
    EditorMemento(String content) { this.content = content; }
    String getContent() { return content; }
}
class Editor {
    private String content = "";
    void type(String words) { content += words; }
    EditorMemento save() { return new EditorMemento(content); }
    void restore(EditorMemento m) { content = m.getContent(); }
}
```

### Key Notes
- Three roles: **Originator** (`Editor`, creates/restores mementos), **Memento** (stores snapshot), **Caretaker** (holds a history stack of mementos, never inspects their contents).
- Memento's fields should stay `private`/opaque to everything except the Originator — otherwise encapsulation is broken.

---

## 10. Visitor

### Definition
Represents an operation to be performed on elements of an object structure, letting you define a new operation without changing the classes of the elements it operates on.

### Purpose
Add new operations to a class hierarchy without modifying the hierarchy itself — useful when the object structure is stable but operations on it change frequently.

### Use Case
- Compilers/ASTs: a `Visitor` performs type-checking, code generation, or pretty-printing over stable node classes (`NumberNode`, `AddNode`).
- Exporting a document's elements (`Paragraph`, `Image`, `Table`) to multiple formats (PDF, HTML) without changing the element classes.

### Snippet (Java)
```java
interface Visitor { void visit(Circle c); void visit(Square s); }
interface Shape { void accept(Visitor v); }

class Circle implements Shape {
    public void accept(Visitor v) { v.visit(this); } // double dispatch
}
class Square implements Shape {
    public void accept(Visitor v) { v.visit(this); }
}
class AreaVisitor implements Visitor {
    public void visit(Circle c) { System.out.println("Computing circle area"); }
    public void visit(Square s) { System.out.println("Computing square area"); }
}
```

### Key Notes
- Relies on **double dispatch**: `accept()` calls back into the correct overloaded `visit()` based on the element's real type.
- Trade-off: easy to add new operations (new Visitor), but hard to add new element types (must update every existing Visitor).

---

## 11. Interpreter

### Definition
Given a language, defines a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.

### Purpose
Evaluate sentences/expressions in a simple custom language or grammar by modeling each grammar rule as a class.

### Use Case
- Simple expression evaluators (arithmetic, boolean logic).
- Rule engines, SQL-like query parsers, regex-lite engines.

### Snippet (Java)
```java
interface Expression { int interpret(); }

class Number implements Expression {
    private int value;
    Number(int value) { this.value = value; }
    public int interpret() { return value; }
}
class Add implements Expression {
    private Expression left, right;
    Add(Expression left, Expression right) { this.left = left; this.right = right; }
    public int interpret() { return left.interpret() + right.interpret(); }
}
// Usage: new Add(new Number(5), new Number(3)).interpret(); // => 8
```

### Key Notes
- Best suited to small, simple grammars — for anything non-trivial, a real parser/parser-generator (ANTLR, etc.) is more practical than hand-rolled Interpreter classes.
- Each grammar rule (terminal/non-terminal) becomes its own class — can get unwieldy for complex languages.

---

## Quick Comparison Table

| Pattern                 | Intent (one line)                                              | Key Mechanism                  |
|--------------------------|--------------------------------------------------------------------|-----------------------------------|
| Strategy                 | Swap interchangeable algorithms at runtime                         | Composition, external choice      |
| Observer                  | Notify dependents automatically on state change                    | Subscribe/notify                  |
| Command                  | Encapsulate a request as an object                                  | Wraps receiver + action            |
| State                    | Change behavior when internal state changes                        | Composition, internal transitions  |
| Template Method          | Fix algorithm skeleton, let subclasses fill in steps                | Inheritance, overridden steps      |
| Iterator                 | Traverse a collection without exposing internals                    | Cursor object                      |
| Chain of Responsibility  | Pass request along a chain until handled                            | Linked handlers                    |
| Mediator                 | Centralize communication between objects                            | Central hub object                 |
| Memento                  | Capture/restore state without breaking encapsulation                | Snapshot object                    |
| Visitor                  | Add new operations without changing element classes                 | Double dispatch                    |
| Interpreter              | Evaluate sentences in a custom grammar                               | Recursive class-per-rule           |

### Commonly Confused Pairs
- **Strategy vs State** → Same structure; Strategy = client picks algorithm externally, unaware of others; State = object transitions itself between states internally.
- **Command vs Strategy** → Command wraps a request (receiver + action + params) often supporting undo/queuing; Strategy wraps only an interchangeable algorithm, no undo concept.
- **Mediator vs Observer** → Mediator centralizes many-to-many communication through one hub; Observer is one-to-many notification from a single subject.
- **Visitor vs Iterator** → Iterator focuses on traversal only; Visitor focuses on performing an operation on each element once reached (often used together).

---

## Suggested Implementation Order (for practice)
1. Strategy — simplest, foundational composition idea
2. Observer — very common, event-driven thinking
3. Template Method — inheritance-based, quick to grasp
4. Iterator — mostly built into Java, good to implement manually once
5. State — builds directly on Strategy's shape
6. Command — introduces undo/queuing concepts
7. Chain of Responsibility — linked-handler thinking
8. Mediator — centralizing communication
9. Memento — pair with Command for undo practice
10. Visitor — double dispatch, more advanced
11. Interpreter — most niche, do last