# 📋 List in Java

> *Part of Java Collections Framework — `java.util`*

---

## 📌 What is a List?

A **List** is an **ordered collection** that allows **duplicate elements**. Elements are accessed by their **index** (0-based).

| Property | Value |
|----------|-------|
| Ordered | ✅ Maintains insertion order |
| Duplicates | ✅ Allowed |
| Null elements | ✅ Allowed |
| Index-based access | ✅ Yes |

---

## 🗂️ List Implementations

| Class | Backed By | Thread-Safe | Best For |
|-------|-----------|-------------|---------|
| `ArrayList` | Dynamic array | ❌ | Random access, frequent reads |
| `LinkedList` | Doubly linked list | ❌ | Frequent insert/delete |
| `Vector` | Dynamic array | ✅ | Legacy thread-safe list |
| `Stack` | Extends Vector | ✅ | LIFO operations |

```
ArrayList   →  [ 0 ][ 1 ][ 2 ][ 3 ]   (array — fast index access)
LinkedList  →  [ A ] ↔ [ B ] ↔ [ C ]  (nodes — fast insert/delete)
```

---

## ⚙️ Common List Methods

| Method | Description | Example |
|--------|-------------|---------|
| `add(e)` | Appends element at end | `list.add("Java")` |
| `add(i, e)` | Inserts at index `i` | `list.add(1, "Go")` |
| `get(i)` | Returns element at index `i` | `list.get(0)` |
| `set(i, e)` | Replaces element at index `i` | `list.set(0, "Python")` |
| `remove(i)` | Removes element at index `i` | `list.remove(1)` |
| `remove(obj)` | Removes first occurrence of object | `list.remove("Java")` |
| `size()` | Returns number of elements | `list.size()` |
| `contains(e)` | Checks if element exists | `list.contains("Java")` |
| `indexOf(e)` | Returns first index of element | `list.indexOf("Java")` |
| `lastIndexOf(e)` | Returns last index of element | `list.lastIndexOf("Java")` |
| `isEmpty()` | Checks if list is empty | `list.isEmpty()` |
| `clear()` | Removes all elements | `list.clear()` |
| `subList(from, to)` | Returns a portion of list | `list.subList(1, 3)` |
| `sort(comparator)` | Sorts the list | `list.sort(null)` |
| `toArray()` | Converts list to array | `list.toArray()` |
| `addAll(coll)` | Adds all elements from collection | `list.addAll(other)` |

---

## 🔹 ArrayList

```java
import java.util.ArrayList;

List<String> list = new ArrayList<>();

list.add("Java");
list.add("Python");
list.add("Java");        // duplicate ✅
list.add(1, "Go");       // insert at index 1

System.out.println(list);          // [Java, Go, Python, Java]
System.out.println(list.get(0));   // Java
System.out.println(list.size());   // 4

list.remove("Java");               // removes first "Java"
list.set(0, "C++");                // replace at index 0

Collections.sort(list);            // sort alphabetically
```

> 💡 `ArrayList` doubles its capacity when full — **amortized O(1)** for add.

---

## 🔹 LinkedList

```java
import java.util.LinkedList;

LinkedList<String> list = new LinkedList<>();

list.add("A");
list.addFirst("Z");      // insert at beginning
list.addLast("M");       // insert at end

System.out.println(list.getFirst());   // Z
System.out.println(list.getLast());    // M

list.removeFirst();
list.removeLast();
```

> 💡 `LinkedList` also implements `Queue` and `Deque` — so it can be used as a queue or stack too.

---

## 🔹 Stack

```java
import java.util.Stack;

Stack<Integer> stack = new Stack<>();

stack.push(10);
stack.push(20);
stack.push(30);

System.out.println(stack.peek());   // 30 (top, no remove)
System.out.println(stack.pop());    // 30 (removes top)
System.out.println(stack.isEmpty()); // false
```

> 💡 Prefer `ArrayDeque` over `Stack` in modern Java — faster and not legacy.

---

## ⚡ ArrayList vs LinkedList

| Operation | ArrayList | LinkedList |
|-----------|-----------|------------|
| `get(i)` | O(1) ✅ fast | O(n) ❌ slow |
| `add(e)` at end | O(1) amortized | O(1) |
| `add(i, e)` middle | O(n) ❌ slow | O(1) ✅ fast |
| `remove(i)` middle | O(n) ❌ slow | O(1) ✅ fast |
| Memory | Less (array) | More (node pointers) |

---

## 💡 When to Use What

| Use Case | Best Choice |
|----------|------------|
| Frequent reads / random access | `ArrayList` |
| Frequent insert / delete in middle | `LinkedList` |
| Need queue + list combined | `LinkedList` |
| LIFO stack operations | `ArrayDeque` |
| Thread-safe list (legacy) | `Vector` |

---

*`List` — Ordered · Duplicates Allowed · Index-Based*