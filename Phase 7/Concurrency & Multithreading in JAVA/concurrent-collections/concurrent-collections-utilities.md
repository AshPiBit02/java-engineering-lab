# Chapter 7: Concurrent Collections, Concurrency Utilities & Final Project

## 1. Why this chapter exists

Every shared collection used throughout this roadmap — a bounded buffer, a shared counter, a results map — was protected by hand: `synchronized`, `ReentrantLock`, `wait()`/`notify()`, or `Condition`, all written explicitly. Java's `java.util.concurrent` package provides **pre-built thread-safe collections and coordination tools** that give the same guarantees internally, without writing that locking code yourself.

**This chapter is best read after, not instead of, the earlier ones.** Having built a buffer and a shared map by hand makes it possible to actually understand what a `BlockingQueue` or `ConcurrentHashMap` is doing for you underneath — otherwise it looks like unexplained magic.

---

## 2. `ConcurrentHashMap<K, V>`

Package: `java.util.concurrent.ConcurrentHashMap`

A thread-safe alternative to `HashMap`. The key architectural difference from wrapping a plain `HashMap` in one big `synchronized` block: a `ConcurrentHashMap` internally locks only **small segments** of the map, so multiple threads can read and write **different keys** at the same time without blocking each other. A fully-synchronized `HashMap` wrapper, by contrast, serializes *every* operation behind one lock regardless of which keys are involved — far worse for concurrent throughput.

**Core methods:**

| Method | Description |
|---|---|
| `V put(K key, V value)` | Inserts or replaces a value for a key; thread-safe. |
| `V get(Object key)` | Retrieves the value for a key; thread-safe, and does not block writers to other keys. |
| `V remove(Object key)` | Removes a key's mapping. |
| `V putIfAbsent(K key, V value)` | Inserts only if the key is not already present — atomically, avoiding a manual "check then put" race. |
| `V computeIfAbsent(K key, Function<K,V> mappingFunction)` | Atomically computes and inserts a value only if the key is absent — useful for lazy initialization of per-key data without a separate check. |
| `V compute(K key, BiFunction<K,V,V> remappingFunction)` | Atomically computes a new value based on the key and its current value (or `null` if absent). |
| `V merge(K key, V value, BiFunction<V,V,V> remappingFunction)` | Atomically combines an existing value with a new one (e.g., `map.merge("key", 1, Integer::sum)` to increment a per-key counter safely) — this is the atomic version of the classic "read, modify, write" pattern that caused the very first race condition in this roadmap, but now built-in and lock-free from the caller's point of view. |
| `int size()` | Approximate count of entries (may be slightly stale under heavy concurrent modification, by design — an exact snapshot would require locking the whole map). |
| `boolean containsKey(Object key)` | Checks key presence. |

**Essential note:** iterating a `ConcurrentHashMap` (e.g., with a for-each loop) is safe and will not throw `ConcurrentModificationException` even if the map is being modified by other threads during iteration — the iterator reflects the state of the map at some point during the traversal, rather than requiring the map to be frozen.

---

## 3. `BlockingQueue<E>` (interface) and `LinkedBlockingQueue<E>` (implementation)

Package: `java.util.concurrent.BlockingQueue`, `java.util.concurrent.LinkedBlockingQueue`

This interface directly replaces everything built by hand in earlier chapters for a bounded producer-consumer buffer — the capacity check, the blocking, and the wake-up logic are all handled internally.

```java
BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10); // capacity 10
```

**Core methods:**

| Method | Description |
|---|---|
| `void put(E element)` | Inserts an element, **blocking automatically** if the queue is currently full — no manual `wait()`/`await()` needed. |
| `E take()` | Removes and returns the head element, **blocking automatically** if the queue is currently empty. |
| `boolean offer(E element)` | Attempts to insert without blocking; returns `false` immediately if full instead of waiting — the queue equivalent of `tryLock()`. |
| `boolean offer(E element, long timeout, TimeUnit unit)` | Attempts to insert, waiting up to the given timeout before giving up. |
| `E poll()` | Attempts to remove without blocking; returns `null` immediately if empty. |
| `E poll(long timeout, TimeUnit unit)` | Attempts to remove, waiting up to the given timeout before giving up and returning `null`. This was used directly in the final project so that worker threads could avoid blocking forever on `take()` once producers were finished and the queue might be legitimately, permanently empty. |
| `int size()` | Current number of elements (approximate under concurrent modification, same caveat as `ConcurrentHashMap.size()`). |
| `int remainingCapacity()` | How much more room is left before the queue is full. |

**Why `poll(timeout, unit)` matters for coordination:** a worker looping with `while (someSharedCondition) { queue.take(); ... }` can get permanently stuck if `take()` is called right as the queue becomes empty for the last time — nothing further will ever be added, so `take()` would block forever, even though the *loop's* exit condition might otherwise have caught this on the very next check. Using `poll()` with a bounded wait lets the thread periodically wake up, re-check its actual stopping condition, and exit cleanly instead of hanging.

Other common implementations of the `BlockingQueue` interface worth knowing exist (not covered in depth here, since `LinkedBlockingQueue` demonstrates the core ideas): `ArrayBlockingQueue` (fixed-capacity, backed by an array), `PriorityBlockingQueue` (elements ordered by priority rather than insertion order), `SynchronousQueue` (zero capacity — every `put()` must be matched immediately by a waiting `take()`, useful for direct hand-offs between exactly one producer and one consumer at a time).

---

## 4. Other concurrency utilities worth knowing

These were not built into a project directly in this roadmap, but are common enough in real code to know at a conceptual level.

### `CountDownLatch`
Package: `java.util.concurrent.CountDownLatch`

A one-time-use gate: one or more threads wait for a fixed number of "events" to occur before proceeding.
```java
CountDownLatch latch = new CountDownLatch(3);
// worker threads, each calling:
latch.countDown();     // decrement the count by 1
// a waiting thread:
latch.await();          // blocks until count reaches 0
```
| Method | Description |
|---|---|
| `void countDown()` | Decrements the count by one; once it reaches zero, all waiting threads are released. |
| `void await()` | Blocks the calling thread until the count reaches zero. |
| `boolean await(long timeout, TimeUnit unit)` | Same, but bounded by a timeout; returns whether the count reached zero in time. |
| `long getCount()` | Current count, without blocking. |

**Key difference from `join()`:** `join()` waits for a specific `Thread` object to finish; `CountDownLatch` waits for a specific *number of events*, which could come from any threads, and does not require holding references to particular `Thread` objects at all. It also **cannot be reset** — once it reaches zero, it stays there permanently (contrast with `CyclicBarrier` below).

### `CyclicBarrier`
Package: `java.util.concurrent.CyclicBarrier`

A reusable synchronization point where a fixed number of threads must all arrive before any of them can proceed — useful for phased computations where every thread must finish "phase 1" before any of them starts "phase 2."
```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All arrived, proceeding"));
// each participating thread calls:
barrier.await();   // blocks until all 3 threads have called await()
```
| Method | Description |
|---|---|
| `int await()` | Blocks until all parties have called `await()`; returns the arrival index. |
| `int await(long timeout, TimeUnit unit)` | Same, bounded by a timeout. |
| `int getParties()` | The number of threads required to trip the barrier. |
| `void reset()` | Resets the barrier to its initial state, allowing it to be reused. |

**Key difference from `CountDownLatch`:** a `CyclicBarrier` is reusable across multiple "rounds," and it waits for threads to arrive at each other (mutually), rather than one set of threads waiting for a separate set of events to be counted down.

### `Semaphore`
Package: `java.util.concurrent.Semaphore`

Generalizes the "limited resource pool" idea (like the checkout-counter or parking-garage tasks built earlier) into a ready-made primitive: a semaphore maintains a set number of "permits," and threads acquire/release them.
```java
Semaphore semaphore = new Semaphore(3); // 3 permits available
semaphore.acquire();   // blocks if no permits are available
try {
    // use the limited resource
} finally {
    semaphore.release();
}
```
| Method | Description |
|---|---|
| `void acquire()` | Blocks until a permit is available, then takes one. |
| `boolean tryAcquire()` | Attempts to take a permit without blocking; returns `false` immediately if none available — the semaphore equivalent of `tryLock()`. |
| `boolean tryAcquire(long timeout, TimeUnit unit)` | Attempts to take a permit, waiting up to the given timeout. |
| `void release()` | Returns a permit to the pool, potentially unblocking a waiting thread. |
| `int availablePermits()` | Current number of permits available, without blocking. |

**Essential note:** the hand-built "3 checkout counters" system from Chapter 4 is conceptually exactly what a `Semaphore(3)` is designed for — having built it manually with a `ReentrantLock` + `Condition` first makes it clear what a `Semaphore` is actually doing internally when used as a shortcut later.

### `CopyOnWriteArrayList<E>`
Package: `java.util.concurrent.CopyOnWriteArrayList`

A thread-safe `List` optimized for situations with **many reads and rare writes**. Every write (add/remove) creates an entirely new internal copy of the underlying array; reads/iteration never need any locking at all, since they always operate on a stable, unchanging snapshot. This trade-off makes it a poor choice for write-heavy workloads (each write is relatively expensive), but excellent for something like a list of event listeners that's iterated constantly but rarely modified.

### `ConcurrentLinkedQueue<E>`
Package: `java.util.concurrent.ConcurrentLinkedQueue`

A thread-safe, **non-blocking** queue — unlike `BlockingQueue` implementations, it has no capacity limit and no `put()`/`take()` that wait; `offer()`/`poll()` always return immediately (`poll()` returns `null` on an empty queue rather than blocking). Useful when you want thread-safe FIFO ordering but don't need or want producer/consumer threads to ever block on capacity.

### `ThreadLocalRandom`
Package: `java.util.concurrent.ThreadLocalRandom`

A version of `Random` optimized for concurrent use: `java.util.Random` is thread-safe but becomes a contention point when many threads use the *same* instance heavily (internally, it must coordinate updates to its shared seed). `ThreadLocalRandom.current()` gives each thread its own independent generator, avoiding that contention entirely.
```java
int value = ThreadLocalRandom.current().nextInt(100);
```

### `ExecutorService` variants worth knowing exist
`Executors.newWorkStealingPool()` creates a pool backed by a `ForkJoinPool`, where idle threads can "steal" queued sub-tasks from busier threads — well suited to recursively-splittable workloads. `ForkJoinPool` itself (with `ForkJoinTask`, `RecursiveTask`, `RecursiveAction`) is the engine behind Java's parallel streams and divide-and-conquer style parallelism — a deeper topic beyond the scope of this roadmap, but worth recognizing by name.

---

## 5. Final Project: Order Processing System — how it ties everything together

**Structure:**
- A shared `BlockingQueue<String>` for incoming orders — replacing what would otherwise be a hand-built, lock-protected buffer.
- A shared `ConcurrentHashMap<String, String>` recording each order's status — replacing what would otherwise be a hand-synchronized map.
- Multiple plain `Thread`s acting as producers, each submitting a fixed number of orders via `queue.put(...)`.
- An `ExecutorService` (fixed thread pool) acting as consumers/processors, each pulling with `queue.poll(timeout, unit)` and recording completion in the map.
- A `ScheduledExecutorService` printing a periodic progress report, entirely independent of the producers/processors.

**The coordination challenge this project centers on:** processor threads loop "forever" pulling from the queue, so something must tell them when to actually stop. The solution demonstrated: track a known total order count in advance, have each processor loop `while (orderStatus.size() < totalOrders)`, and use `poll(timeout, unit)` instead of `take()` so a processor never blocks indefinitely once the supply of new orders has genuinely run out.

**Shutdown sequence used, and why each step is in that order:**
1. Start and `join()` all producer threads — this only waits for *production* to finish, not processing.
2. Loop-check `orderStatus.size()` against the known total on the main thread — since an `ExecutorService`'s pooled threads cannot be `join()`ed directly the way raw `Thread` objects can, this substitutes for that wait.
3. `shutdown()` both the worker pool and the scheduler, then `awaitTermination(...)` on each, to allow a clean, bounded exit rather than leaving anything running indefinitely.

This project intentionally requires nearly everything covered across the roadmap to reason about correctly: thread creation and joining (Chapter 1), the need for genuinely thread-safe shared state (Chapters 2 and 4), producer/consumer coordination (Chapter 3), pooled task execution and scheduled reporting (Chapter 5), and — the actual point of this chapter — recognizing that `BlockingQueue`/`ConcurrentHashMap` already provide everything that would otherwise have to be built by hand.

---

## 6. Where this roadmap ends

This covers the essential, load-bearing concepts of Java concurrency: threads and their lifecycle, race conditions and mutual exclusion, condition-based waiting, explicit locks and lock-free atomics, thread pools and scheduling, asynchronous result composition, and ready-made concurrent collections and coordination primitives. Deeper topics exist beyond this point — the Java Memory Model in full formal detail, `ForkJoinPool`/parallel streams, virtual threads (Project Loom, Java 21+), and non-blocking algorithm design — but everything above is the foundation those topics build on, and it transfers conceptually to concurrency in any other language, including Python.