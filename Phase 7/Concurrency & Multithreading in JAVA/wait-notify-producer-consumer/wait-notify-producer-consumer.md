# Chapter 3: `wait()` / `notify()` and Producer-Consumer

## 1. The problem `synchronized` alone cannot solve

`synchronized` prevents two threads from touching shared state simultaneously, but it says nothing about a thread needing to **pause until some condition becomes true** — for example, a consumer that should sleep when a buffer is empty, or a producer that should sleep when a buffer is full.

A naive approach is to busy-loop:
```java
while (buffer.isEmpty()) {
    // spin, checking repeatedly
}
```
This "works" but wastes CPU constantly re-checking a condition that isn't ready — this is called **busy-waiting**, and it's considered bad practice. Java provides a proper mechanism to let a thread genuinely sleep — consuming no CPU — until another thread specifically signals that something may have changed.

## 2. `wait()`, `notify()`, `notifyAll()` — the mechanism

These three methods exist on every Java object (inherited from `Object` itself, not from `Thread`), and they only work from **inside a `synchronized` block**, always relative to the lock currently held.

```java
synchronized (lock) {
    while (conditionNotMet) {
        lock.wait();     // releases the lock, sleeps, waits to be notified
    }
    // proceed once condition is met
}
```
```java
synchronized (lock) {
    // change some shared state
    lock.notify();       // wakes ONE arbitrary waiting thread
    // or:
    lock.notifyAll();    // wakes ALL waiting threads
}
```

**Core mechanics, each with a specific reason behind it:**

1. **`wait()` releases the lock while the thread sleeps.** This is essential — if it didn't, no other thread could ever acquire the lock to change the condition and call `notify()`, and the sleeping thread would wait forever with no possible way to be woken. This is the key difference from simply calling `Thread.sleep()` inside a synchronized block, which holds the lock the entire time and can deadlock everything else.

2. **When woken, the thread must re-acquire the lock before continuing** — it does not simply resume freely; it competes for the lock like any other thread trying to enter that synchronized section.

3. **Always check the condition with `while`, never `if`.** Java's specification permits **spurious wakeups** — a thread waking from `wait()` occasionally without any actual `notify()` call having happened. If the check used `if`, a spurious wakeup would let the thread proceed even though the real condition still isn't true. `while` re-checks the condition immediately after waking and goes back to `wait()` if it's still false, closing this gap entirely.

4. **`notify()` wakes exactly one waiting thread, chosen arbitrarily. `notifyAll()` wakes every waiting thread**, and they then re-compete for the lock one at a time, each re-checking its own `while` condition. `notify()` can be risky whenever more than one *kind* of thread might be waiting on the same lock for different reasons — it might wake the "wrong" thread (one whose condition still isn't satisfied), leaving a thread that actually could have proceeded still asleep. **Default to `notifyAll()` unless you have specifically reasoned through why `notify()` is safe** for the exact situation (all waiting threads interchangeable, only one condition ever being waited on).

## 3. Producer-Consumer — the canonical use case

A bounded buffer needs two complementary waiting conditions:
- A producer must wait when the buffer is **full**.
- A consumer must wait when the buffer is **empty**.

```java
public synchronized void produce(int value) throws InterruptedException {
    while (bufferIsFull()) {
        wait();
    }
    // add value
    notifyAll();   // wake up any consumer waiting for something to consume
}

public synchronized int consume() throws InterruptedException {
    while (bufferIsEmpty()) {
        wait();
    }
    // remove and return a value
    notifyAll();   // wake up any producer waiting for space
}
```

A subtle bug worth calling out explicitly, since it's easy to get backwards: **the condition being signaled after an action is the opposite thread's condition.** After a producer adds an item, it's consumers that need waking (the buffer is now less empty). After a consumer removes an item, it's producers that need waking (there's now more space). Mixing this up — signaling the same condition you just waited on, instead of the other one — silently breaks the whole system: nobody who actually needs waking ever gets woken, and the program hangs.

## 4. A closely related bug: mirroring collection state in a separate counter

An easy mistake is tracking "how much room is left" or "how many items exist" using a hand-maintained counter variable, separate from the actual backing collection:
```java
private int space = 7;
// increment/decrement `space` manually in multiple places
```
If any code path forgets to update this counter (e.g., a consumer that removes an item but never increments `space` back), the counter drifts out of sync with the real state of the collection, and the wait/notify logic starts making decisions based on stale information. **The safer approach is to check the actual collection directly** (`buffer.size() == capacity`, `buffer.isEmpty()`) rather than maintaining a parallel variable that has to be kept perfectly in sync by hand.

## 5. Non-determinism strikes again — even for "correct" logic

Correct wait/notify logic can still *appear* broken if you never observe the blocking behavior it's designed to handle. If a producer and a consumer happen to run at similar average speeds, the buffer may never actually fill up or empty out during a given run — so `wait()` never triggers, even though the code is completely correct. This is the same non-deterministic scheduling theme from earlier: **the logic is not lacking; the demonstration needs enough of a timing gap (different sleep durations, smaller buffer capacity) to reliably expose the behavior you're trying to see.**

## 6. Scaling to multiple producers and multiple consumers

Nothing about the `produce()`/`consume()` methods needs to change structurally when there are many threads of each kind — the same locking and while-loop logic already generalizes correctly, because each call is written per-item, not per-thread. What *does* start to matter more is the `notify()` vs `notifyAll()` choice: with multiple consumers potentially waiting simultaneously, `notifyAll()` ensures all of them get a chance to recheck, rather than risking waking a thread that (in more complex systems, with multiple distinct wait-reasons on one lock) might not actually be able to proceed yet.

When scaling up producer/consumer *counts* in code (not just per-item logic), the same coordination discipline from earlier chapters applies: total items produced across all producer threads must equal total items expected to be consumed across all consumer threads, or some consumer will wait forever for an item that will never arrive — the same class of bug as any other thread-count/total mismatch.

## 7. Reference implementations from this chapter

- **Basic Producer-Consumer with a bounded `LinkedList` buffer** — single producer, single consumer; established the base pattern.
- **Parking Garage** — multiple arrival threads and multiple departure threads sharing a capacity-limited resource, using `notifyAll()` in both directions.
- **Print Queue** — multiple submitter threads and multiple printer threads, the clearest demonstration of why `notifyAll()` matters once several threads of the same kind can be waiting simultaneously; also where the "mirrored counter drifts out of sync" bug was caught and fixed by checking `jobs.size()` directly instead.

## 8. What comes next

`wait()`/`notify()` is tied to the intrinsic (`synchronized`) lock, which only gives you *one* wait-condition per object unless you carefully reason about mixed wake-ups. The next chapter introduces `ReentrantLock`, which offers more explicit control (try-without-blocking, interruptible waiting) and `Condition` objects, which let a single lock support *multiple independent* wait-conditions — directly solving the "wrong thread woken" risk in a more structured way. It also introduces atomic types, for the narrower case of simple counters that don't need a full lock at all.