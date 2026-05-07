# 📬 Queue in Java

> *Part of Java Collections Framework — `java.util`*

---

## 📌 What is a Queue?

A **Queue** is a collection that follows the **FIFO (First In, First Out)** principle — elements are inserted at the **tail** and removed from the **head**.

| Property | Value |
|----------|-------|
| Order | FIFO (First In, First Out) |
| Duplicates | ✅ Allowed |
| Null elements | Depends on implementation |
| Index-based access | ❌ No |

```
Enqueue (add) ──►  [ C ][ B ][ A ]  ──► Dequeue (remove)
               tail                  head
```

---

## 🗂️ Queue Implementations

| Class | Type | Null | Best For |
|-------|------|------|---------|
| `LinkedList` | FIFO Queue | ✅ | General-purpose queue |
| `PriorityQueue` | Priority-based | ❌ | Process highest/lowest priority first |
| `ArrayDeque` | Double-ended (Deque) | ❌ | Stack + Queue — faster than LinkedList |
| `LinkedBlockingQueue` | Thread-safe FIFO | ❌ | Producer-consumer pattern |

---

## ⚙️ Common Queue Methods

Java Queue provides **two sets of methods** — one throws exceptions, the other returns special values:

| Operation | Throws Exception | Returns Special Value |
|-----------|------------------|-----------------------|
| Insert | `add(e)` | `offer(e)` → `false` if full |
| Remove | `remove()` | `poll()` → `null` if empty |
| Peek (head) | `element()` | `peek()` → `null` if empty |

> 💡 Prefer `offer()`, `poll()`, and `peek()` — they are **safer** (no exceptions on empty queue).

---

## 🔹 LinkedList as Queue

```java
import java.util.LinkedList;
import java.util.Queue;

Queue<String> queue = new LinkedList<>();

queue.offer("First");
queue.offer("Second");
queue.offer("Third");

System.out.println(queue.peek());    // First  (head, no remove)
System.out.println(queue.poll());    // First  (removes head)
System.out.println(queue.poll());    // Second
System.out.println(queue.size());    // 1
```

---

## 🔹 PriorityQueue

Elements are **ordered by priority** (natural order or custom `Comparator`) — smallest element has highest priority by default.

```java
import java.util.PriorityQueue;

PriorityQueue<Integer> pq = new PriorityQueue<>();  // min-heap by default

pq.offer(30);
pq.offer(10);
pq.offer(20);

System.out.println(pq.peek());    // 10  (smallest = highest priority)
System.out.println(pq.poll());    // 10
System.out.println(pq.poll());    // 20
System.out.println(pq.poll());    // 30
```

### Max-Heap (reverse order)

```java
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
maxPQ.offer(10);
maxPQ.offer(30);
maxPQ.offer(20);
System.out.println(maxPQ.poll());   // 30  (largest first)
```

> 💡 `PriorityQueue` uses a **binary heap** internally — O(log n) for insert and remove.

---

## 🔹 Deque (Double-Ended Queue)

A **Deque** (pronounced "deck") allows insertion and removal from **both ends** — can be used as both a **Queue** and a **Stack**.

```
addFirst() ──► [ D ][ C ][ B ][ A ] ◄── addLast()
removeFist() ◄─                    ─► removeLast()
```

```java
import java.util.ArrayDeque;
import java.util.Deque;

Deque<String> deque = new ArrayDeque<>();

deque.addFirst("B");
deque.addFirst("A");    // [A, B]
deque.addLast("C");     // [A, B, C]

System.out.println(deque.peekFirst());   // A
System.out.println(deque.peekLast());    // C

deque.removeFirst();    // removes A
deque.removeLast();     // removes C
```

### Deque Methods

| Operation | Front | Rear |
|-----------|-------|------|
| Insert | `addFirst(e)` / `offerFirst(e)` | `addLast(e)` / `offerLast(e)` |
| Remove | `removeFirst()` / `pollFirst()` | `removeLast()` / `pollLast()` |
| Peek | `peekFirst()` / `getFirst()` | `peekLast()` / `getLast()` |

### ArrayDeque as Stack (LIFO)

```java
Deque<String> stack = new ArrayDeque<>();

stack.push("A");    // same as addFirst()
stack.push("B");
stack.push("C");

System.out.println(stack.pop());    // C  (LIFO)
System.out.println(stack.peek());   // B
```

> 💡 `ArrayDeque` is preferred over `Stack` and `LinkedList` for both stack and queue — **faster, no legacy baggage**.

---

## ⚡ Queue Implementations Comparison

| Feature | LinkedList | PriorityQueue | ArrayDeque |
|---------|------------|---------------|------------|
| Order | FIFO | Priority-based | FIFO or LIFO |
| Null allowed | ✅ | ❌ | ❌ |
| Performance | O(1) enqueue/dequeue | O(log n) | O(1) amortized |
| Use as Stack | ✅ | ❌ | ✅ (preferred) |
| Use as Queue | ✅ | ✅ | ✅ |
| Thread-safe | ❌ | ❌ | ❌ |

---

## 💡 When to Use What

| Use Case | Best Choice |
|----------|------------|
| Simple FIFO queue | `LinkedList` or `ArrayDeque` |
| Process by priority | `PriorityQueue` |
| Stack (LIFO) | `ArrayDeque` |
| Both ends insertion/removal | `ArrayDeque` (Deque) |
| Thread-safe queue | `LinkedBlockingQueue` |

---

*`Queue` — FIFO · Tail Insert · Head Remove · No Index Access*