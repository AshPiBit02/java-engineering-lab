# 🔀 Conditional Statements in Java

---

## 📌 What are Conditional Statements?

Conditional statements allow a program to **make decisions** and execute different blocks of code based on whether a condition evaluates to `true` or `false`. They form the backbone of **control flow** in any Java program.

```
┌──────────────────────────────────────────────────────────┐
│              Types of Conditional Statements             │
│                                                          │
│         ┌────────────────────────────────────┐           │
│         │        Conditional Statements      │           │
│         └──────────────────┬─────────────────┘           │
│                            │                             │
│        ┌───────────────────┼───────────────────┐         │
│        │                   │                   │         │
│  ┌─────▼──────┐    ┌───────▼──────┐   ┌───────▼──────┐   │
│  │  if / if-  │    │ switch-case  │   │   Ternary    │   │
│  │  else /    │    │              │   │   ( ? : )    │   │
│  │  else-if   │    └──────────────┘   └──────────────┘   │
│  └────────────┘                                          │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Types of Conditional Statements in Java**

---

## 1. 🔹 `if` Statement

The simplest conditional — executes a block **only if** the condition is `true`. If false, the block is entirely skipped.

### Syntax

```java
if (condition) {
    // executes only when condition is true
}
```

### Example

```java
int age = 20;

if (age >= 18) {
    System.out.println("You are eligible to vote.");
}
```

```
┌──────────────────────────────────────────────────────────┐
│                     if Statement Flow                    │
│                                                          │
│                   ┌─────────────┐                        │
│                   │  condition  │                        │
│                   └──────┬──────┘                        │
│                          │                               │
│               ┌──────────┴──────────┐                    │
│             true                  false                  │
│               │                     │                    │
│        ┌──────▼──────┐              │                    │
│        │ execute     │              │                    │
│        │   block     │              │                    │
│        └──────┬──────┘              │                    │
│               └──────────┬──────────┘                    │
│                          ▼                               │
│                    rest of program                       │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 2 — `if` Statement Flow**

---

## 2. 🔹 `if-else` Statement

Provides **two paths** — one when the condition is `true`, another when `false`. Exactly one block always executes.

### Syntax

```java
if (condition) {
    // true block
} else {
    // false block
}
```

### Example

```java
int marks = 40;

if (marks >= 45) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
// Output: Fail
```

```
┌──────────────────────────────────────────────────────────┐
│                   if-else Statement Flow                 │
│                                                          │
│                   ┌─────────────┐                        │
│                   │  condition  │                        │
│                   └──────┬──────┘                        │
│                          │                               │
│               ┌──────────┴──────────┐                    │
│             true                  false                  │
│               │                     │                    │
│        ┌──────▼──────┐       ┌──────▼──────┐             │
│        │  if block   │       │ else block  │             │
│        └──────┬──────┘       └──────┬──────┘             │
│               └──────────┬──────────┘                    │
│                          ▼                               │
│                    rest of program                       │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 3 — `if-else` Statement Flow**

---

## 3. 🔹 `else-if` Ladder

Used when there are **more than two possible outcomes**. Conditions are checked **top to bottom** — as soon as one is `true`, its block executes and the rest are skipped.

### Syntax

```java
if (condition1) {
    // block 1
} else if (condition2) {
    // block 2
} else if (condition3) {
    // block 3
} else {
    // default block (none of above matched)
}
```

### Example

```java
int marks = 75;

if (marks >= 90) {
    System.out.println("Grade: A+");
} else if (marks >= 80) {
    System.out.println("Grade: A");
} else if (marks >= 70) {
    System.out.println("Grade: B");
} else if (marks >= 60) {
    System.out.println("Grade: C");
} else {
    System.out.println("Grade: Fail");
}
// Output: Grade: B
```

```
┌──────────────────────────────────────────────────────────┐
│                   else-if Ladder Flow                    │
│                                                          │
│   ┌─────────────┐                                        │
│   │ condition 1 │── true ──► execute block 1 ──► STOP    │
│   └──────┬──────┘                                        │
│        false                                             │
│   ┌─────────────┐                                        │
│   │ condition 2 │── true ──► execute block 2 ──► STOP    │
│   └──────┬──────┘                                        │
│        false                                             │
│   ┌─────────────┐                                        │
│   │ condition 3 │── true ──► execute block 3 ──► STOP    │
│   └──────┬──────┘                                        │
│        false                                             │
│   ┌─────────────┐                                        │
│   │    else     │──────────► execute default  ──► STOP   │
│   └─────────────┘                                        │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 4 — `else-if` Ladder Flow**

---

## 4. 🔹 Nested `if`

An `if` (or `if-else`) placed **inside another `if`** block. Used when a second condition only needs to be checked after the first one passes.

### Example

```java
int age = 20;
boolean hasID = true;

if (age >= 18) {
    if (hasID == true) {
        System.out.println("Entry allowed.");
    } else {
        System.out.println("No ID — entry denied.");
    }
} else {
    System.out.println("Underage — entry denied.");
}
```

```
┌──────────────────────────────────────────────────────────┐
│                  Nested if Flow                          │
│                                                          │
│            ┌──────────────────┐                          │
│            │  age >= 18 ?     │                          │
│            └────────┬─────────┘                          │
│                     │                                    │
│          true ◄─────┴─────► false                        │
│            │                   │                         │
│     ┌──────▼──────────┐    ┌───▼──────────────┐          │
│     │  hasID == true? │    │ "Underage"       │          │
│     └──────┬──────────┘    └──────────────────┘          │
│            │                                             │
│    true ◄──┴──► false                                    │
│      │             │                                     │
│ "Entry         "No ID"                                   │
│  allowed"                                                │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Nested `if` Flow**

> ⚠️ Avoid deeply nested `if` blocks — hard to read and maintain. Prefer `else-if` ladder or early `return` when possible.

---

## 5. 🔹 `switch-case` Statement

A cleaner alternative to long `else-if` chains when matching a variable against **multiple fixed values**.

### Syntax

```java
switch (expression) {
    case value1:
        // block 1
        break;
    case value2:
        // block 2
        break;
    default:
        // runs if no case matches
}
```

### Example

```java
int day = 3;

switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    default:
        System.out.println("Invalid day");
}
// Output: Wednesday
```

```
┌──────────────────────────────────────────────────────────┐
│                   switch-case Flow                       │
│                                                          │
│              ┌─────────────────┐                         │
│              │   expression    │                         │
│              └────────┬────────┘                         │
│                       │                                  │
│     ┌─────────────────┼──────────────────┐               │
│     │                 │                  │               │
│  ┌──▼────┐         ┌──▼────┐         ┌───▼─────┐         │
│  │case 1 │         │case 2 │  . . .  │ default │         │
│  └──┬────┘         └──┬────┘         └───┬─────┘         │
│     │                 │                  │               │
│  execute           execute            execute            │
│     │                 │                  │               │
│   break             break               end              │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 6 — `switch-case` Flow**

---

### ⚠️ Fall-through Behavior

If `break` is **omitted**, execution **falls through** into the next case automatically:

```java
int x = 1;
switch (x) {
    case 1: System.out.println("One");    // no break!
    case 2: System.out.println("Two");    // also runs
    case 3: System.out.println("Three");  // also runs
        break;
}
// Output: One  Two  Three
```

> 🆚 **C++ vs Java** — Fall-through behavior is **identical** in both. Always add `break` unless fall-through is intentional.

---

### Supported Types in `switch`

```
┌──────────────────────────────────────────────────────────┐
│              switch Supported Types                      │
│                                                          │
│   All Java versions  →  byte, short, int, char           │
│   Java 5+            →  enum                             │
│   Java 7+            →  String  ← key difference vs C++  │
│   Java 14+           →  Pattern matching (preview)       │
└──────────────────────────────────────────────────────────┘
```

> 🆚 **C++ vs Java** — C++ `switch` does **not** support `String`. Java 7+ added `String` in switch — a notable difference.

---

## 6. 🔹 Ternary Operator `? :`

A **compact one-line** shorthand for a simple `if-else` assignment.

### Syntax

```java
variable = (condition) ? valueIfTrue : valueIfFalse;
```

### Example

```java
int age = 20;
String status = (age >= 18) ? "Adult" : "Minor";
System.out.println(status);   // Adult
```

```
┌──────────────────────────────────────────────────────────┐
│                   Ternary Operator Flow                  │
│                                                          │
│       (condition) ? valueIfTrue : valueIfFalse           │
│             │              │              │              │
│        evaluate       assign this    assign this         │
│        condition       if true        if false           │
│                                                          │
│   (age >= 18)  ?  "Adult"  :  "Minor"                    │
│       true    →   "Adult"  ← assigned                    │
│       false   →   "Minor"  ← assigned                    │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 7 — Ternary Operator Flow**

> 💡 Use ternary for **simple value assignments** only. Avoid nesting ternary operators — it reduces readability significantly.

> 🆚 **C++ vs Java** — Identical syntax and behavior in both.

---

## 🆚 `if-else` vs `switch` — When to Use?

```
┌──────────────────────────────────────────────────────────┐
│               if-else   vs   switch                      │
│                                                          │
│   if-else                    switch                      │
│   ────────────────           ──────────────────          │
│   Range-based checks         Exact value matching        │
│   (x > 10, x < 50)           (x == 1, x == 2)            │
│                                                          │
│   Boolean expressions        Fixed discrete values       │
│   Complex conditions         enum / String matching      │
│                                                          │
│   Two outcomes only          Many fixed cases            │
│   (use ternary instead)      (cleaner than else-if)      │
└──────────────────────────────────────────────────────────┘
```

| Scenario | Best Choice |
|----------|-------------|
| Range check (`marks >= 90`) | `if-else` |
| Exact value match (day, month) | `switch` |
| Only two outcomes | `if-else` or ternary `? :` |
| Many fixed values | `switch` |
| `String` or `enum` matching | `switch` (Java 7+) |
| Complex boolean logic | `if-else` |

---

## 📋 Key Differences — C++ vs Java

| Feature | C++ | Java |
|---------|-----|------|
| `switch` with `String` | ❌ Not supported | ✅ Java 7+ |
| `switch` with `enum` | ✅ | ✅ Java 5+ |
| Fall-through in `switch` | ✅ Same behavior | ✅ Same behavior |
| Ternary `? :` | ✅ Identical | ✅ Identical |
| Condition must be `boolean` | ❌ (any non-zero = true) | ✅ Strictly `boolean` |

> 🆚 **Important** — In C++, any **non-zero value** is treated as `true` in a condition. In Java, the condition **must be strictly `boolean`** — `if (1)` causes a compile error in Java.

---

## 💡 Summary

| Statement | Purpose |
|-----------|---------|
| `if` | Execute block only when condition is true |
| `if-else` | Choose between two paths |
| `else-if` ladder | Choose among multiple conditions in order |
| Nested `if` | Condition within a condition |
| `switch-case` | Match a value against multiple fixed cases |
| Ternary `? :` | Inline single-line conditional value assignment |

---
