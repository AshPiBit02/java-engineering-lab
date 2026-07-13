# Chapter 4: Locks (`ReentrantLock`) & Atomics

## 1. Why `ReentrantLock` when `synchronized` already works

`synchronized` is simple but rigid: you cannot check whether a lock is currently held, cannot attempt to acquire it without blocking, and cannot interrupt a thread that's stuck waiting for it. `java.util.concurrent.locks.ReentrantLock` exposes all of this explicitly, at the cost of managing the lock yourself instead of the JVM doing it automatically.

```java
import java.util.concurrent.locks.ReentrantLock;

private ReentrantLock lock = new ReentrantLock();

public void deposit(int amount) {
    lock.lock();
    try {
        balance += amount;
    } finally {
        lock.unlock();
    }
}
```

**The single most important rule: always call `unlock()` inside a `finally` block.** Unlike `synchronized`, which releases its lock automatically even if an exception is thrown inside it, `ReentrantLock` requires you to release it manually. If an exception skips your `unlock()` call, or you simply forget it, the lock stays held forever and every other thread waiting on it freezes permanently. This is the most common `ReentrantLock` bug, and `finally` is the only reliable defense against it.

**"Reentrant" meaning:** a thread that already holds the lock can acquire it again (e.g., by calling another locked method on itself) without deadlocking on its own lock — the same behavior `synchronized` already provides, just made explicit here. This was demonstrated directly: a locked method calling another locked getter on the same object did not hang, because the same thread reacquiring its own lock is always permitted.

## 2. Capabilities `synchronized` cannot offer

- **`tryLock()`** — attempts to acquire the lock **without blocking**, returning `true`/`false` immediately. This lets a thread do something else (retry later, give up, log a "system busy" message) instead of waiting indefinitely.
```java
if (lock.tryLock()) {
    try {
        // acquired — proceed
    } finally {
        lock.unlock();
    }
} else {
    // did not acquire — do something else
}
```
- **`tryLock(timeout, unit)`** — wait up to a bounded amount of time before giving up.
- **`lockInterruptibly()`** — allows a thread blocked waiting for the lock to be woken via `interrupt()`, which is impossible with `synchronized` (a thread blocked waiting to enter a synchronized section cannot be interrupted out of that wait).

### The real risk `tryLock()` introduces
A single, one-shot `tryLock()` attempt with no retry logic can simply fail and never try again — even if the resource becomes available almost immediately afterward. This was observed directly: with many competing threads and no retry loop, only whichever thread grabbed the lock first ever succeeded, because everyone else's single attempt happened to land during that first hold and gave up permanently. **The fix is a bounded (or unbounded) retry loop** with a short backoff between attempts, so a thread gets multiple chances to catch the lock during its lifetime rather than betting everything on one instant.

## 3. `Condition` — the `ReentrantLock` equivalent of `wait()`/`notify()`, but with multiple independent queues

```java
private ReentrantLock lock = new ReentrantLock();
private Condition notFull = lock.newCondition();
private Condition notEmpty = lock.newCondition();
```

Mapping directly onto what `synchronized`/`wait()`/`notify()` already do:

| `synchronized` version | `ReentrantLock` version |
|---|---|
| `synchronized(this) { ... }` | `lock.lock(); try { ... } finally { lock.unlock(); }` |
| `wait()` | `someCondition.await();` |
| `notify()` | `someCondition.signal();` |
| `notifyAll()` | `someCondition.signalAll();` |

**The genuine upgrade:** a single `ReentrantLock` can have *multiple* `Condition` objects, each with its own independent wait-queue. Calling `notFull.signalAll()` wakes **only** the threads waiting on `notFull` (e.g., producers waiting for space) — consumers waiting on a separate `notEmpty` condition are completely undisturbed. This directly and structurally solves the "wrong thread woken" ambiguity that plain `notifyAll()` can only partially address by waking everyone indiscriminately.

**A design decision this forces on you:** when a long-running action (like simulated processing time) needs to happen as part of an operation, it should generally **not** happen while the lock is held — only the short bookkeeping steps (checking/updating shared counters or collections) need the lock. Holding a lock across a slow operation effectively serializes everything, defeating the purpose of allowing multiple resources/threads to operate independently. The working pattern that emerged was: lock → quick bookkeeping (claim a resource) → unlock → slow work, unlocked → lock again → quick bookkeeping (release the resource, signal) → unlock.

## 4. Atomic classes — correctness without locking at all

`java.util.concurrent.atomic.AtomicInteger` (and its relatives `AtomicLong`, `AtomicBoolean`, `AtomicReference<T>`) solve a narrower problem than locks: making **single, simple operations** (increment, add, compare-and-set) safe across threads without ever making a thread wait.

```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();   // increments, returns the NEW value
counter.getAndIncrement();   // returns the OLD value, THEN increments
counter.get();                // reads current value, no change
```

**Why this is safe without `synchronized`/`ReentrantLock`:** internally, atomics use a CPU-level **Compare-And-Swap (CAS)** instruction. The sequence is: read the current value, compute the new value, then attempt to write it back *only if the value hasn't changed since the read*; if something else changed it in the meantime, the whole attempt **retries** rather than corrupting data. This entire read-check-write cycle is a single indivisible hardware operation — no other thread can interleave in the middle of it. This is called **lock-free** programming: instead of making competing threads wait (as locks do), it makes a losing attempt retry. For simple counters, this is typically both simpler and faster than acquiring a full lock.

**The naming pattern to remember:** "incrementAndGet" increments *first*, then returns; "getAndIncrement" returns the *current* value *first*, then increments. The same convention applies to `addAndGet`/`getAndAdd` and other atomic operations.

**The hard limitation:** atomics only guarantee atomicity for the single operation they directly expose. They cannot replace a lock for any multi-step piece of logic with real conditions in between — for example, "check if a resource is available, and *if so*, decrement it" is two logically connected steps, and no atomic method does exactly that compound operation. This is precisely why a `ReentrantLock` was still required for the actual "check seats and book" logic in the ticket-booking system, even though the simple success counter alongside it was correctly handled with just an `AtomicInteger`.

## 5. Reference implementations from this chapter

- **Bank Account rewritten with `ReentrantLock`** — a direct mechanical translation from `synchronized` to `lock()`/`try`/`finally{unlock()}`, confirming the same correctness guarantee under the new API.
- **Ticket Booking System** — `tryLock()` demonstrating both the "one-shot attempt starves almost everyone" failure mode and its fix via a retry loop with backoff; paired with an `AtomicInteger` for a simple, lock-free success counter.
- **Multi-Counter Checkout System** — the fullest combination: `ReentrantLock` + a single `Condition` for "counter available," plus two `AtomicInteger`s tracking totals, and the two-locked-section pattern (short lock → unlocked slow work → short lock again) to allow genuine parallelism among customers.
- **Multi-Producer/Multi-Consumer buffer with `Condition`** — the same producer-consumer shape from the previous chapter, rebuilt with two separate `Condition`s (`bufferFull`/`bufferEmpty`) instead of one shared `wait()`/`notifyAll()` pool — including a hang caused by signaling the wrong condition, and the fix (swap which condition each method signals).

## 6. What comes next

Every task so far manually created and managed `Thread` objects — even tests with over a hundred real OS threads. Each of those threads carries real memory and scheduling overhead, and manually orchestrating large numbers of them (arrays, start-loops, join-loops) does not scale well. The next chapter introduces **thread pools**: a fixed, reusable set of worker threads that tasks are submitted to, rather than each task getting its own dedicated, disposable thread.