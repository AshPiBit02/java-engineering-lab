# 7.5 Concurrency and Multithreading in Java

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Why Concurrency Matters](#2-why-concurrency-matters)
- [3. Key Characteristics](#3-key-characteristics)
- [4. Thread Lifecycle](#4-thread-lifecycle)
- [5. Creating Threads — Two Approaches](#5-creating-threads--two-approaches)
- [6. Thread Safety and Synchronization](#6-thread-safety-and-synchronization)
- [7. Working Sequence — Race Condition vs Synchronized Access](#7-working-sequence--race-condition-vs-synchronized-access)
- [8. Code Example — Thread, Synchronization, and Executor Service](#8-code-example--thread-synchronization-and-executor-service)
- [9. Executor Framework](#9-executor-framework)
- [10. Common Concurrency Issues](#10-common-concurrency-issues)
- [11. Common Exceptions](#11-common-exceptions)
- [12. Important Notes](#12-important-notes)
- [13. Summary](#13-summary)

---

## 1. Introduction

**Concurrency** is the ability of a program to execute multiple tasks seemingly at the same time, while **multithreading** is Java's specific mechanism for achieving this by running multiple **threads** within a single process. This is the same mechanism the Servlet container (Phase 6) relies on to handle many client requests simultaneously.

```
Fig 1.1 — Single-Threaded vs Multithreaded Execution
Single-threaded:
Task A ────────────► Task B ────────────► Task C
(sequential, one at a time)

Multithreaded:
Task A ─────────────►
Task B ─────────────►   (running concurrently on separate threads)
Task C ─────────────►
```

---

## 2. Why Concurrency Matters

```
Fig 2.1 — Where Concurrency Shows Up
┌───────────────────────────────────────────────────────────────┐
│ Web servers/Servlet containers                                │
│   → each incoming request handled on its own thread           │
├───────────────────────────────────────────────────────────────┤
│ Background/batch processing                                   │
│   → long-running tasks run without blocking the main flow     │
├───────────────────────────────────────────────────────────────┤
│ GUI applications                                              │
│   → keeping the UI responsive while work happens in background│
├───────────────────────────────────────────────────────────────┤
│ Parallel computation                                          │
│   → splitting large workloads across CPU cores for speed      │
└───────────────────────────────────────────────────────────────┘
```

---

## 3. Key Characteristics

| Characteristic | Description |
|-----------------|-------------|
| Process vs Thread | A process has its own memory space; threads within a process share memory |
| Lightweight | Threads are cheaper to create/switch between than processes |
| Shared state risk | Threads sharing memory can corrupt data if not synchronized properly |
| Preemptive scheduling | The JVM/OS decides thread execution order — not fully predictable |
| Daemon vs User threads | Daemon threads don't prevent JVM shutdown; user threads do |

---

## 4. Thread Lifecycle

```
Fig 4.1 — Thread Lifecycle States

     NEW
      │  start()
      ▼
   RUNNABLE ◄──────────────┐
      │  scheduler picks it│ notify()/notifyAll()
      ▼                    │  or lock acquired
   RUNNING                 │
      │  wait()/sleep()/   │
      │  blocked on lock   │
      ▼                    │
  WAITING/BLOCKED ─────────┘
      │
      │  run() completes
      ▼
  TERMINATED
```

| State | Description |
|-------|--------------|
| NEW | Thread object created but `start()` not yet called |
| RUNNABLE | Eligible to run; waiting for CPU time from the scheduler |
| RUNNING | Actively executing |
| BLOCKED/WAITING | Waiting for a lock, or waiting due to `wait()`/`join()`/`sleep()` |
| TERMINATED | `run()` method has completed |

---

## 5. Creating Threads — Two Approaches

| Approach | Description |
|----------|--------------|
| Extending `Thread` | Subclass `Thread`, override `run()` — simple but prevents extending another class |
| Implementing `Runnable` | Implement `run()` in a separate class, pass to a `Thread` — preferred, allows extending other classes |

```
Fig 5.1 — Runnable vs Thread
class MyTask implements Runnable {     class MyThread extends Thread {
    public void run() { ... }              public void run() { ... }
}                                       }
new Thread(new MyTask()).start();      new MyThread().start();
        (preferred — decouples task            (less flexible — uses
         logic from thread mechanics)            up single inheritance)
```

---

## 6. Thread Safety and Synchronization

| Tool | Purpose |
|------|----------|
| `synchronized` keyword | Locks a method/block so only one thread can execute it at a time |
| `volatile` keyword | Ensures visibility of a variable's latest value across threads |
| `Lock` / `ReentrantLock` | More flexible, explicit locking (from `java.util.concurrent.locks`) |
| `Atomic` classes (`AtomicInteger`, etc.) | Lock-free, thread-safe operations on single variables |
| `ConcurrentHashMap` | Thread-safe map implementation optimized for concurrent access |

---

## 7. Working Sequence — Race Condition vs Synchronized Access

```
Fig 7.1 — Race Condition (Unsafe)

Thread A                Thread B                 balance
   │                        │                       100
   │──read balance (100)───►│                       100
   │                        │──read balance (100)──►100
   │──add 50, write 150────►│                       150
   │                        │──add 30, write 130────│130  ← Thread A's update LOST!

Fig 7.2 — Synchronized Access (Safe)

Thread A                 Thread B                 balance
   │──acquire lock──────────│                        100
   │──read, add 50,         │  (Thread B blocked,    150
   │   write 150            │   waiting for lock)
   │──release lock─────────►│
   │                        │──acquire lock───────────│
   │                        │──read, add 30,          │180
   │                        │   write 180             │
   │                        │──release lock──────────►│
```

---

## 8. Code Example — Thread, Synchronization, and Executor Service

```java
// ---------- Creating a thread via Runnable (preferred) ----------
class PrintTask implements Runnable {
    public void run() {
        System.out.println("Running on: " + Thread.currentThread().getName());
    }
}

Thread t = new Thread(new PrintTask());
t.start();   // starts a new thread; NEVER call run() directly (that would run on the current thread)


// ---------- Synchronized method to prevent race conditions ----------
class BankAccount {
    private int balance = 100;

    public synchronized void deposit(int amount) {  // only one thread at a time
        balance += amount;                            // safe read-modify-write
    }

    public synchronized int getBalance() {
        return balance;
    }
}


// ---------- ExecutorService — managed thread pool (preferred over raw threads) ----------
ExecutorService executor = Executors.newFixedThreadPool(4);  // pool of 4 reusable threads

for (int i = 0; i < 10; i++) {
    final int taskId = i;
    executor.submit(() -> {                 // submit 10 tasks to the pool
        System.out.println("Task " + taskId + " on " + Thread.currentThread().getName());
    });
}

executor.shutdown();   // stop accepting new tasks, finish queued ones
```

---

## 9. Executor Framework

```
Fig 9.1 — Why ExecutorService over Raw Threads
┌───────────────────────────────────────────────────────────────┐
│ Raw Thread per task                                           │
│   → unbounded thread creation, expensive, hard to manage      │
├───────────────────────────────────────────────────────────────┤
│ ExecutorService (thread pool)                                 │
│   → reuses a fixed set of threads, queues excess tasks,       │
│     provides lifecycle management (shutdown, awaitTermination)│
└───────────────────────────────────────────────────────────────┘
```

| Executor Type | Use Case |
|----------------|-----------|
| `newFixedThreadPool(n)` | Fixed number of reusable threads — predictable load |
| `newCachedThreadPool()` | Grows/shrinks dynamically — bursty, short-lived tasks |
| `newSingleThreadExecutor()` | Sequential task execution on one dedicated thread |
| `newScheduledThreadPool(n)` | Tasks run after a delay or periodically |

---

## 10. Common Concurrency Issues

| Issue | Description |
|-------|--------------|
| Race condition | Multiple threads access/modify shared data without synchronization, causing inconsistent results |
| Deadlock | Two or more threads wait on each other's locks indefinitely |
| Livelock | Threads keep responding to each other without making progress |
| Starvation | A thread is perpetually denied access to a resource it needs |
| Visibility problem | A thread doesn't see the latest value written by another thread (solved by `volatile`/synchronization) |

---

## 11. Common Exceptions

| Exception | Cause |
|-----------|-------|
| `InterruptedException` | A thread was interrupted while waiting/sleeping/blocked |
| `IllegalMonitorStateException` | Calling `wait()`/`notify()` outside a synchronized block |
| `ConcurrentModificationException` | Modifying a non-thread-safe collection while iterating over it |
| `RejectedExecutionException` | Task submitted to an `ExecutorService` that has already shut down |

---

## 12. Important Notes

- Always prefer `Runnable`/`Callable` + `ExecutorService` over manually managing raw `Thread` objects in real applications.
- `synchronized` provides safety but can hurt performance if overused — lock only the minimal critical section.
- Deadlocks typically happen when multiple threads acquire the same locks in a **different order** — always acquire locks in a consistent order.
- Java's `java.util.concurrent` package (introduced to solve exactly these problems) provides high-level, tested concurrency utilities — prefer them over hand-rolled synchronization where possible.

---

## 13. Summary

```
Fig 13.1 — Concurrency Recap
┌──────────────────────────────────────────────────────────┐
│  CONCURRENCY & MULTITHREADING                            │
│                                                          │
│  Threads share memory within a process → risk of race    │
│  conditions if not synchronized                          │
│                                                          │
│  Tools: synchronized, volatile, Locks, Atomic classes,   │
│         ExecutorService                                  │
│                                                          │
│  Goal: Correctness (no race conditions/deadlocks) +      │
│        Performance (efficient use of CPU cores)          │
└──────────────────────────────────────────────────────────┘
```

| Concept | Key Takeaway |
|---------|---------------|
| Thread lifecycle | NEW → RUNNABLE → RUNNING → WAITING/BLOCKED → TERMINATED |
| Runnable vs Thread | Prefer `Runnable` — decouples task logic from thread mechanics |
| Synchronization | Protects shared mutable state from race conditions |
| ExecutorService | Preferred way to manage threads via reusable pools |
| Common pitfalls | Race conditions, deadlocks, and visibility issues are the main risks |