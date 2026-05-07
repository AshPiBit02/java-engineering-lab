# 🔵 Set in Java

> *Part of Java Collections Framework — `java.util`*

---

## 📌 What is a Set?

A **Set** is a collection that **does not allow duplicate elements**. It models the mathematical set concept.

| Property | Value |
|----------|-------|
| Ordered | Depends on implementation |
| Duplicates | ❌ Not allowed — silently ignored |
| Null elements | Depends on implementation |
| Index-based access | ❌ No |

---

## 🗂️ Set Implementations

| Class | Order | Null | Thread-Safe | Best For |
|-------|-------|------|-------------|---------|
| `HashSet` | ❌ No order | ✅ One null | ❌ | Fast lookup, no order needed |
| `LinkedHashSet` | ✅ Insertion order | ✅ One null | ❌ | Ordered unique elements |
| `TreeSet` | ✅ Sorted (natural/custom) | ❌ No null | ❌ | Sorted unique elements |

```
HashSet       →  { C, A, B }          (no guaranteed order)
LinkedHashSet →  { A, B, C }          (insertion order maintained)
TreeSet       →  { A, B, C }          (always sorted)
```

---

## ⚙️ Common Set Methods

| Method | Description | Example |
|--------|-------------|---------|
| `add(e)` | Adds element — ignored if duplicate | `set.add("Java")` |
| `remove(e)` | Removes the element | `set.remove("Java")` |
| `contains(e)` | Checks if element exists | `set.contains("Java")` |
| `size()` | Returns number of elements | `set.size()` |
| `isEmpty()` | Checks if set is empty | `set.isEmpty()` |
| `clear()` | Removes all elements | `set.clear()` |
| `addAll(coll)` | Union — adds all from collection | `set.addAll(other)` |
| `retainAll(coll)` | Intersection — keeps only common | `set.retainAll(other)` |
| `removeAll(coll)` | Difference — removes all in coll | `set.removeAll(other)` |
| `toArray()` | Converts set to array | `set.toArray()` |
| `iterator()` | Returns iterator for traversal | `set.iterator()` |

> 💡 No `get(index)` method — Sets have no index. Traverse using **for-each** or **iterator**.

---

## 🔹 HashSet

```java
import java.util.HashSet;

Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");
set.add("Java");     // duplicate — silently ignored ✅

System.out.println(set);              // [Python, Java]  (order not guaranteed)
System.out.println(set.contains("Java")); // true
System.out.println(set.size());       // 2

set.remove("Python");
```

> 💡 `HashSet` uses a `HashMap` internally — O(1) for add, remove, contains.

---

## 🔹 LinkedHashSet

```java
import java.util.LinkedHashSet;

Set<String> set = new LinkedHashSet<>();

set.add("Banana");
set.add("Apple");
set.add("Mango");
set.add("Apple");    // duplicate — ignored

System.out.println(set);   // [Banana, Apple, Mango]  (insertion order ✅)
```

> 💡 Use `LinkedHashSet` when you need **uniqueness + insertion order**.

---

## 🔹 TreeSet

```java
import java.util.TreeSet;

TreeSet<Integer> set = new TreeSet<>();

set.add(30);
set.add(10);
set.add(20);
set.add(10);    // duplicate — ignored

System.out.println(set);          // [10, 20, 30]  (sorted ✅)
System.out.println(set.first());  // 10
System.out.println(set.last());   // 30
System.out.println(set.headSet(20)); // [10]   (elements < 20)
System.out.println(set.tailSet(20)); // [20, 30] (elements >= 20)
```

### Extra TreeSet Methods

| Method | Description |
|--------|-------------|
| `first()` | Returns smallest element |
| `last()` | Returns largest element |
| `headSet(e)` | Elements strictly less than `e` |
| `tailSet(e)` | Elements greater than or equal to `e` |
| `subSet(from, to)` | Elements from `from` (inclusive) to `to` (exclusive) |
| `floor(e)` | Greatest element ≤ `e` |
| `ceiling(e)` | Smallest element ≥ `e` |

> 💡 `TreeSet` uses a **Red-Black Tree** internally — O(log n) for add, remove, contains.

---

## 🔢 Set Operations (Mathematical)

```java
Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5, 6));

// Union:  a ∪ b
a.addAll(b);          // {1, 2, 3, 4, 5, 6}

// Intersection: a ∩ b
a.retainAll(b);       // {3, 4}

// Difference: a - b
a.removeAll(b);       // {1, 2}
```

---

## ⚡ HashSet vs LinkedHashSet vs TreeSet

| Feature | HashSet | LinkedHashSet | TreeSet |
|---------|---------|---------------|---------|
| Order | ❌ None | ✅ Insertion | ✅ Sorted |
| Null allowed | ✅ One | ✅ One | ❌ No |
| Performance | O(1) | O(1) | O(log n) |
| Internal structure | HashMap | LinkedHashMap | Red-Black Tree |

---

## 💡 When to Use What

| Use Case | Best Choice |
|----------|------------|
| Fast lookup, order doesn't matter | `HashSet` |
| Unique elements in insertion order | `LinkedHashSet` |
| Unique elements always sorted | `TreeSet` |
| Range queries (headSet, tailSet) | `TreeSet` |

---

*`Set` — No Duplicates · No Index Access · Model of Math Sets*