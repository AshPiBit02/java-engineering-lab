# Chapter 2: Race Conditions & `synchronized`

## 1. The problem this chapter exists to solve

Chapter 1 deliberately avoided one thing: multiple threads reading and writing the *same* mutable data. This chapter removes that restriction and shows exactly what goes wrong.

Consider an operation that looks like a single step:
```java
balance = balance + amount;
```
At the CPU level this is actually three separate steps: **read** `balance`, **compute** the new value, **write** it back. If two threads execute this "simultaneously," their steps can interleave:

```
Thread A reads balance = 500
Thread B reads balance = 500        (before A has written anything back)
Thread A computes 600, writes balance = 600
Thread B computes 600, writes balance = 600
```

Two deposits of 100 happened, but the balance only increased by 100 — one update was silently lost. This is a **race condition**: the final result depends on the unpredictable timing/interleaving of threads, rather than being determined purely by the logic of the program.

**Essential note on scale:** race conditions are not rare edge cases — they are common, but they need enough concurrent activity (many threads, many iterations) to reliably surface. A test with only a handful of threads can pass every time and still hide a fundamentally broken program. This was demonstrated directly: 100 threads depositing produced a correct balance every run, while 10,000+ threads reliably exposed lost updates. **Passing a small test is not proof of thread safety.**

## 2. `synchronized` — enforcing mutual exclusion

```java
public synchronized void deposit(int amount) {
    balance = balance + amount;
}
```

Every Java object carries an internal lock, sometimes called a **monitor** or **intrinsic lock**. Marking a method `synchronized` means: only one thread may be executing *any* synchronized method on that same object at a time. Any other thread attempting to enter a synchronized method/block on that object must wait until the current holder finishes.

You can also synchronize just part of a method, locking on a specific object:
```java
public void deposit(int amount) {
    synchronized (this) {
        balance = balance + amount;
    }
}
```
This is useful when only part of a method touches shared state, and you don't want to hold the lock for the rest of it (e.g., don't lock during a slow, unrelated computation).

**Two guarantees `synchronized` provides, not just one:**
1. **Mutual exclusion** — one thread at a time.
2. **Visibility** — changes made by one thread while holding the lock are guaranteed to be visible to the next thread that acquires the same lock. Without this, one thread might not even *see* another thread's update promptly, due to CPU-level caching — a separate and subtler issue from simple interleaving.

## 3. The lock only matters if everyone locks on the *same* object

A common, easy-to-miss mistake:
```java
synchronized (new Object()) {
    balance += amount;
}
```
Every call creates a **new**, distinct lock object. No two threads are ever competing for the same lock, so this provides **zero protection**, despite `synchronized` being present in the code and looking safe. The rule: the lock is only meaningful if all threads that need to be mutually exclusive synchronize on the *same* object — typically `this`, or a shared static lock for class-level state.

## 4. Every code path touching shared state must be protected — not just the writes

If some methods that touch shared state are synchronized and others aren't, the protection is incomplete:
```java
public synchronized void deposit(int amount) { balance += amount; }
public int getBalance() { return balance; }   // NOT synchronized
```
Even a plain read can observe a stale value without synchronization, due to the same visibility issue mentioned above. **Rule of thumb:** if any thread writes to shared state under a lock, every thread reading that state should go through the same lock too — reads are not automatically safe just because they don't modify anything.

## 5. Static synchronized methods lock the *class*, not the instance

```java
public static synchronized void log(String msg) { ... }
```
An instance-level `synchronized` method locks on `this` — one lock per object instance. A **static** synchronized method locks on the `Class` object itself (e.g., `BankAccount.class`) — a single lock shared across *every* instance of that class. This matters when the protected state is itself `static` (shared across all instances). A common mistake is assuming an instance lock and a static lock protect each other — they are two entirely separate locks and provide no mutual protection.

## 6. Deadlock — the cost of using more than one lock

A deadlock requires at least two locks acquired in inconsistent order by different threads:

```java
// Thread A: synchronized(lockX) { synchronized(lockY) { ... } }
// Thread B: synchronized(lockY) { synchronized(lockX) { ... } }
```
If Thread A holds `lockX` and waits for `lockY`, while Thread B holds `lockY` and waits for `lockX`, both threads wait forever — a circular wait with no possible resolution. This is not a rare or exotic problem: the classic real-world trigger is transferring value between two accounts, where a naive `transfer(from, to)` locks "from" then "to," and a transfer running in the reverse direction locks them in the opposite order.

**The fix: consistent lock ordering.** If every thread in the program always acquires multiple locks in the same global order (e.g., always lock the account with the lower ID first, regardless of which account is logically "from" or "to"), a circular wait becomes mathematically impossible — you cannot form a cycle if every participant moves through locks in one consistent direction, much like a one-way street prevents two cars from ever facing off against each other.

```java
Account first  = (from.id < to.id) ? from : to;
Account second = (from.id < to.id) ? to : from;
synchronized (first) {
    synchronized (second) {
        from.withdraw(amount);
        to.deposit(amount);
    }
}
```

**Important distinction to hold onto:** changing which lock is acquired *first* does **not** change the order of the actual business operations (`withdraw` then `deposit` still always happens in that order, top to bottom, in normal single-threaded program order within one call). Lock ordering only changes which lock two competing threads fight over first — it has nothing to do with reordering the statements inside the locked section. Additionally, because both locks are held for the *entire* transfer, no other thread can ever observe a half-completed transfer (money withdrawn from one account but not yet deposited in the other) — the transfer is atomic from every other thread's point of view, even though it's technically two separate statements.

## 7. Why a shared counter (used for bookkeeping) needs the same protection as the "real" data

Any variable multiple threads increment — even something as innocuous as "how many operations succeeded" — has the exact same read-modify-write race as the original balance example. A counter tracked purely for observation/testing purposes is not exempt just because it isn't the "main" data; it needs `synchronized` (or, as later chapters show, an atomic type) just as much as `balance` does.

## 8. Reference implementations from this chapter

- **Bank Account race demonstration** — proved the bug exists at scale (10,000+ threads), then fixed it with a single `synchronized` keyword, confirming the fix gives an exact guarantee rather than merely reducing the odds of failure.
- **Library Book Reservation** — `synchronized` guarding a counted resource (limited copies), plus a safely-tracked success/failure count using the same synchronized-counter principle.
- **Account Transfer (deadlock demo)** — built and observed an actual hang from inconsistent lock ordering, then fixed it with the lower-ID-first convention.

## 9. What comes next

`synchronized` solves *mutual exclusion* — never letting two threads touch shared state at once. It does not solve a different, related problem: what does a thread do when it needs to **wait for some condition to become true** before proceeding (e.g., "wait until there's room in this buffer")? Busy-looping to check a condition wastes CPU. The next chapter introduces the proper mechanism for a thread to sleep until it's specifically told the condition might now be true.