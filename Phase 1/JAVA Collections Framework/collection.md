# 🗃️ Java Collections Framework


---

## 📌 What is the Collections Framework?

The **Java Collections Framework (JCF)** is a unified architecture for storing, retrieving, and manipulating **groups of objects**. It provides ready-to-use **data structures and algorithms** so you don't have to build them from scratch.

> Think of it as Java's **built-in toolkit for managing groups of data** — lists, sets, maps, queues, and more — all under one consistent framework.

---

## ❓ Why Collections over Arrays?

| Feature | Array | Collection |
|---------|-------|------------|
| Size | Fixed at creation | Dynamic (grows/shrinks) |
| Type safety | Primitives + objects | Objects only (use wrappers) |
| Built-in algorithms | ❌ None | ✅ sort, search, shuffle |
| Null elements | ✅ | Depends on implementation |
| Data structures | Only linear | List, Set, Queue, Map, Stack… |
| Ease of use | Manual management | Rich API built-in |

---

## 🏗️ Architecture Overview

The entire framework is built on a set of **interfaces, abstract classes, and concrete implementations**.

```
┌──────────────────────────────────────────────────────────────┐
│               Java Collections Framework                     │
│                                                              │
│                    ┌────────────────┐                        │
│                    │   Iterable<E>  │  (java.lang)           │
│                    └───────┬────────┘                        │
│                            │                                 │
│                    ┌───────▼────────┐                        │
│                    │ Collection<E>  │  (root interface)      │
│                    └───────┬────────┘                        │
│                            │                                 │
│          ┌─────────────────┼──────────────────┐              │
│          │                 │                  │              │
│   ┌──────▼──────┐  ┌───────▼──────┐  ┌───────▼──────┐        │
│   │   List<E>   │  │   Set<E>     │  │   Queue<E>   │        │
│   │  (ordered,  │  │ (unique,     │  │  (FIFO       │        │
│   │  duplicates)│  │  no dups)    │  │   order)     │        │
│   └──────┬──────┘  └───────┬──────┘  └───────┬──────┘        │
│          │                 │                  │              │
│  ArrayList      HashSet,SortedSet      PriorityQueue         │
│  LinkedList     LinkedHashSet          LinkedList            │
│  Vector         TreeSet                Deque                 │
│  Stack                                                       │
│                                                              │
│                    ┌───────────────┐                         │
│                    │    Map<K,V>   │  (separate hierarchy)   │
│                    └───────┬───────┘                         │
│                            │                                 │
│              HashMap, LinkedHashMap, TreeMap                 │
│              Hashtable, SortedMap                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Java Collections Framework Architecture**

> 💡 `Map` is **not** a subtype of `Collection` — it has its own separate hierarchy but is still part of the JCF.

---

## 🧩 Core Interfaces

```
┌──────────────────────────────────────────────────────────────┐
│                   Core JCF Interfaces                        │
│                                                              │
│  ┌─────────────┬────────────────────────────────────────┐    │
│  │  Interface  │  Description                           │    │
│  ├─────────────┼────────────────────────────────────────┤    │
│  │ Collection  │  Root — basic group of elements        │    │
│  │ List        │  Ordered, allows duplicates            │    │
│  │ Set         │  No duplicates, may be unordered       │    │
│  │ SortedSet   │  Set maintained in sorted order        │    │
│  │ Queue       │  FIFO — elements inserted/removed      │    │
│  │ Deque       │  Double-ended queue (both ends)        │    │
│  │ Map         │  Key-value pairs, unique keys          │    │
│  │ SortedMap   │  Map with keys in sorted order         │    │
│  └─────────────┴────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Core JCF Interfaces**

---

## ⚙️ Key Concepts

---

### 🔹 Generics in Collections

Collections use **generics** (`<E>`) to enforce **type safety** at compile time — no accidental mixing of types.

```java
// Without generics (old way) — unsafe
List list = new ArrayList();
list.add("Hello");
list.add(123);           // no error — mixing types!
String s = (String) list.get(1);  // ❌ ClassCastException at runtime

// With generics (correct way)
List<String> list = new ArrayList<>();
list.add("Hello");
list.add(123);           // ❌ compile error — caught early ✅
```

> 🆚 **C++ vs Java** — Java generics are similar to **C++ templates** but are implemented via **type erasure** — generic type info is removed at runtime. C++ templates generate actual separate code per type at compile time.

---

### 🔹 Autoboxing with Collections

Collections can only store **objects**, not primitives. Java **autoboxing** handles the conversion automatically.

```java
List<Integer> nums = new ArrayList<>();
nums.add(10);       // int → Integer  (autoboxing, automatic)
int x = nums.get(0); // Integer → int  (unboxing, automatic)
```

| Primitive | Wrapper (used in collections) |
|-----------|-------------------------------|
| `int` | `Integer` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

---

### 🔹 `Iterable` and `Iterator`

Every collection implements `Iterable`, allowing it to be traversed using a **for-each loop** or an **Iterator**.

```java
List<String> names = new ArrayList<>(List.of("Aasii", "Ram", "Sita"));

// For-each (uses Iterable internally)
for (String name : names) {
    System.out.println(name);
}

// Iterator (explicit)
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}
```

---

### 🔹 `Collections` Utility Class

`java.util.Collections` is a helper class with **static utility methods** for collections:

| Method | Description |
|--------|-------------|
| `Collections.sort(list)` | Sorts a list |
| `Collections.reverse(list)` | Reverses a list |
| `Collections.shuffle(list)` | Randomly shuffles |
| `Collections.min(coll)` | Finds minimum element |
| `Collections.max(coll)` | Finds maximum element |
| `Collections.frequency(coll, obj)` | Count occurrences |
| `Collections.unmodifiableList(list)` | Returns read-only view |
| `Collections.synchronizedList(list)` | Returns thread-safe view |

> 💡 Don't confuse `Collection` (interface) with `Collections` (utility class).

---

### 🔹 Comparable vs Comparator

Used to define **ordering/sorting** of objects in collections.

```
┌──────────────────────────────────────────────────────────────┐
│              Comparable  vs  Comparator                      │
│                                                              │
│   Comparable                  Comparator                     │
│   ─────────────────           ─────────────────────          │
│   java.lang package           java.util package              │
│   Implemented by the class    External/separate class        │
│   compareTo(Object o)         compare(Object o1, Object o2)  │
│   Natural ordering            Custom ordering                │
│   Modifies original class     Does not modify class          │
│                                                              │
│   e.g. String, Integer        e.g. sort by name, then age    │
│   already implement it        when you need custom sort      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Comparable vs Comparator**

---

### 🔹 Fail-Fast vs Fail-Safe Iterators

| Type | Behavior | Examples |
|------|----------|---------|
| **Fail-Fast** | Throws `ConcurrentModificationException` if collection is modified during iteration | `ArrayList`, `HashMap` |
| **Fail-Safe** | Works on a copy — no exception on modification | `CopyOnWriteArrayList`, `ConcurrentHashMap` |

---

### 🔹 Thread Safety in Collections

By default, most collections are **not thread-safe**. Java provides alternatives:

```
┌──────────────────────────────────────────────────────────────┐
│                Thread Safety Options                         │
│                                                              │
│   Not thread-safe (default)                                  │
│   ArrayList, HashMap, HashSet, LinkedList                    │
│                                                              │
│   Thread-safe (legacy)                                       │
│   Vector, Hashtable, Stack                                   │
│                                                              │
│   Thread-safe (modern — java.util.concurrent)                │
│   ConcurrentHashMap, CopyOnWriteArrayList                    │
│   ConcurrentLinkedQueue, BlockingQueue                       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Thread Safety in Collections**

---

## 📦 Implementations — Quick Intro

> *Each of these will be covered in detail in their own separate file.*

---

### 📋 List — Ordered, Allows Duplicates

Maintains **insertion order**. Elements accessed by **index**.

| Class | Backed By | Best For |
|-------|-----------|---------|
| `ArrayList` | Dynamic array | Fast random access |
| `LinkedList` | Doubly linked list | Fast insert/delete |
| `Vector` | Dynamic array (synchronized) | Thread-safe (legacy) |
| `Stack` | Extends Vector | LIFO operations |

```java
List<String> list = new ArrayList<>();
list.add("Java");
list.add("Java");   // duplicates allowed ✅
```

---

### 🔵 Set — Unique Elements, No Duplicates

Automatically **rejects duplicate** elements.

| Class | Order | Best For |
|-------|-------|---------|
| `HashSet` | No order | Fast lookup |
| `LinkedHashSet` | Insertion order | Ordered unique elements |
| `TreeSet` | Sorted (natural/custom) | Sorted unique elements |

```java
Set<String> set = new HashSet<>();
set.add("Java");
set.add("Java");   // duplicate silently ignored ✅
```

---

### 📬 Queue — FIFO Order

Elements inserted at the **tail**, removed from the **head**.

| Class | Type | Best For |
|-------|------|---------|
| `PriorityQueue` | Priority-based | Process highest priority first |
| `LinkedList` | FIFO | General queue operations |
| `ArrayDeque` | Double-ended | Stack + Queue both |

```java
Queue<String> queue = new LinkedList<>();
queue.offer("First");
queue.offer("Second");
queue.poll();   // removes "First" (FIFO)
```

---

### 🗺️ Map — Key-Value Pairs

Stores data as **key → value** pairs. Keys must be **unique**.

| Class | Order | Best For |
|-------|-------|---------|
| `HashMap` | No order | Fast key-based lookup |
| `LinkedHashMap` | Insertion order | Ordered key-value pairs |
| `TreeMap` | Sorted by key | Sorted key-value pairs |
| `Hashtable` | No order | Thread-safe (legacy) |

```java
Map<String, Integer> map = new HashMap<>();
map.put("Aasii", 21);
map.get("Aasii");   // returns 21
```

---

## 🗺️ Choosing the Right Collection

```
┌──────────────────────────────────────────────────────────────┐
│              Which Collection to Use?                        │
│                                                              │
│   Need key-value pairs?                                      │
│       Yes → Map (HashMap / TreeMap / LinkedHashMap)          │
│       No  ↓                                                  │
│                                                              │
│   Need unique elements only?                                 │
│       Yes → Set (HashSet / TreeSet / LinkedHashSet)          │
│       No  ↓                                                  │
│                                                              │
│   Need FIFO / priority ordering?                             │
│       Yes → Queue (LinkedList / PriorityQueue / ArrayDeque)  │
│       No  ↓                                                  │
│                                                              │
│   Need index-based access + duplicates?                      │
│       Yes → List (ArrayList / LinkedList)                    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Choosing the Right Collection**

---

## 🆚 C++ vs Java — Collections

| Feature | C++ (STL) | Java (JCF) |
|---------|-----------|------------|
| Dynamic array | `vector` | `ArrayList` |
| Linked list | `list` | `LinkedList` |
| Unique set | `set` | `HashSet` / `TreeSet` |
| Key-value | `map` / `unordered_map` | `TreeMap` / `HashMap` |
| Queue | `queue` | `LinkedList` / `PriorityQueue` |
| Stack | `stack` | `Stack` / `ArrayDeque` |
| Algorithms | `<algorithm>` header | `Collections` utility class |
| Type safety | Templates | Generics (type erasure) |
| Primitives | ✅ Directly | ❌ Wrapper classes needed |

---

## 💡 Summary

| Interface | Characteristic | Common Implementations |
|-----------|---------------|------------------------|
| `List` | Ordered, duplicates allowed | `ArrayList`, `LinkedList` |
| `Set` | Unique elements | `HashSet`, `TreeSet` |
| `Queue` | FIFO processing | `LinkedList`, `PriorityQueue` |
| `Deque` | Double-ended queue | `ArrayDeque` |
| `Map` | Key-value pairs, unique keys | `HashMap`, `TreeMap` |

> The Collections Framework is one of Java's most powerful features — understanding the **right collection for the right problem** is a core Java skill.

---
