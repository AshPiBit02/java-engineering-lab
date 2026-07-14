# Chapter 1: Thread Fundamentals

## 1. Why threads exist at all

A normal Java program runs on a single thread — the **main thread** — executing one instruction after another. Threads let a program do multiple things in overlapping time: while one part of the program waits (for a sleep, for I/O, for a slow calculation), another part can keep making progress. This is the entire motivation for everything in this chapter — without a reason to run things concurrently, none of these tools matter.

Two ways this shows up in practice:
- **Responsiveness** — a program shouldn't freeze entirely just because one task is slow.
- **Throughput** — independent pieces of work (e.g., three unrelated calculations) can finish sooner in parallel than one after another.

## 2. `Thread` vs `Runnable` — two ways to describe "what to run"

Java ships a `Thread` class representing an actual unit of execution. To give a thread something to do, you have two options:

- **Extend `Thread`** and override `run()`.
- **Implement `Runnable`** and pass an instance into a `Thread`'s constructor.

`Runnable` is generally preferred. The reasoning: Java classes can only extend one parent class. If your task class already needs to extend something else, extending `Thread` blocks that off. `Runnable` describes *the job*, and a `Thread` is *the worker executing the job* — separating "what to do" from "how it's run" is a cleaner design, and it's also what lets the same `Runnable` be reused across thread pools later.

**Essential distinction:** a `Runnable` on its own does nothing concurrently. It's just an object with a `run()` method. Only wrapping it in a `Thread` (or, later, submitting it to an executor) actually gives it its own line of execution.

## 3. `start()` vs `run()` — the most common beginner mistake

Calling `run()` directly executes that code **on the current thread**, like any ordinary method call — no new thread is created. Calling `start()` is what actually asks the JVM/OS to create a new thread and have it execute `run()` independently.

This matters because the mistake compiles and often even seems to work in trivial cases, which is exactly why it survives as a bug — nothing crashes, concurrency just silently isn't happening.

## 4. Thread lifecycle

A thread moves through states:

```
NEW → RUNNABLE → (scheduled to actually run) → TERMINATED
              ↕
     BLOCKED / WAITING / TIMED_WAITING
```

The important nuance: **RUNNABLE does not mean "currently executing."** It means "eligible to be given CPU time." The OS scheduler decides, moment to moment, which RUNNABLE thread actually gets the processor. This is the root cause of everything that feels unpredictable about concurrent programs — you don't control scheduling, so you can never assume a specific interleaving order between threads unless you explicitly enforce one (which is what the rest of this roadmap is about).

## 5. Core methods and what problem each one solves

| Method | Problem it solves |
|---|---|
| `start()` | Actually create and launch a new thread |
| `join()` | "Wait here until that other thread finishes" — needed whenever a later step depends on earlier threads being done |
| `sleep(ms)` | Pause the *current* thread without consuming CPU the whole time |
| `interrupt()` | Politely ask a thread to stop, without force-killing it |
| `isAlive()` | Check if a thread has finished, without blocking like `join()` does |
| `setDaemon(true)` | Mark a thread as background-only, so it doesn't keep the program alive by itself |

### `join()` in depth
Without `join()`, the main thread (or any thread) has no way to know when another thread's work is done — it would just barrel ahead. `join()` blocks the *calling* thread until the target thread terminates. This was the mechanism behind almost every "wait for these workers to finish before printing a final result" pattern in the implementations so far.

### `sleep()` in depth
`Thread.sleep(ms)` is a **static** method — it always pauses whichever thread called it, regardless of which `Thread` object you technically call it "on" syntactically (a common point of confusion). It throws a checked `InterruptedException`, because a sleeping thread can be woken early by an interrupt.

### `interrupt()` in depth — cooperative, not forceful
Java deliberately does not provide a safe way to force-kill a thread. Instead, `interrupt()` sets an internal flag and, if the thread is currently blocked in `sleep()`, `wait()`, or `join()`, that blocking call throws `InterruptedException` immediately. If the thread isn't blocked, nothing happens automatically — the thread must periodically check `isInterrupted()` itself.

**The correct pattern**, and a subtle mistake worth calling out: catching `InterruptedException` and only printing/logging it (without acting on it) means the interrupt was "heard" but ignored — the loop keeps going. Properly handling an interrupt means using the catch block to actually stop the loop (e.g., `break`).

### Daemon threads
Every thread is normal ("non-daemon") by default — the JVM will not exit as long as any non-daemon thread is alive. A **daemon thread** is explicitly marked as not important enough to keep the program running for; once all non-daemon threads finish, the JVM exits immediately, even if daemon threads are mid-execution. Real-world use: background logging, periodic housekeeping — work that should simply stop existing when the "real" program is done, with no need for a clean shutdown.

**Rule:** `setDaemon(true)` must be called *before* `start()`. After starting, it throws `IllegalStateException`.

## 6. Non-deterministic scheduling — the recurring theme

The single most important mental model from this chapter: **you cannot predict the exact order in which independent threads interleave their output.** Two runs of the same program, with the same code, can produce different orderings. This isn't a bug — it's the OS scheduler making different choices moment to moment. Every later chapter (race conditions, locks, wait/notify) exists specifically to let you impose *some* order or safety on top of this fundamental unpredictability, where the program's correctness genuinely requires it.

## 7. Uncaught exceptions and thread isolation

A subtlety that surprises people coming from single-threaded code: if a thread throws an exception it never catches, **that thread dies alone.** It prints a stack trace, but `main()` and every other thread keep running completely unaffected. This is different from single-threaded programs, where an uncaught exception typically brings the whole program down. It's a useful safety property (one bad task doesn't take down everything) but also a trap — a silently-dying thread might mean lost work with no obvious crash to alert you.

## 8. `throws InterruptedException` vs. try/catch — a rule about method signatures

Any method that calls `Thread.sleep()` (or `wait()`, or `join()`) either has to catch `InterruptedException` itself, or declare `throws InterruptedException` so the *caller* is responsible for handling it.

One firm rule worth remembering: **`Runnable.run()` cannot declare `throws InterruptedException`.** Since `run()` overrides an interface method with no `throws` clause, Java doesn't allow you to widen the exception contract. This means any sleeping/waiting inside `run()` itself must be handled with an actual try/catch — you cannot punt the exception upward the way you can from an ordinary helper method.

## 9. Random timing as a simulation tool

Real-world concurrent tasks rarely take identical, fixed amounts of time. Using `Random.nextInt(bound)` to vary `sleep()` durations is a deliberate technique for making toy examples behave more like real systems — services with variable latency, workers with unpredictable task sizes — and for exposing timing-dependent bugs that a fixed, uniform delay might never reveal.

## 10. Reference implementations from this chapter

The following were built to exercise these ideas directly:
- **Multi-thread number printer** — first demonstration of `start()`/`join()` and interleaved, non-deterministic output.
- **Traffic Light System** — a cyclic state loop combined with `interrupt()`-driven shutdown.
- **Restaurant Kitchen** — cancellable tasks, and the importance of keeping direct references to worker objects (not just their `Thread` wrappers) to inspect state afterward.
- **Background Logger + Faulty Sensors** — daemon threads, uncaught-exception isolation, and `isAlive()` polling, all in one system.

These are worth re-reading with this chapter's reasoning in mind, rather than treating them as separate exercises — each one is a direct demonstration of one or more of the concepts above.

## 11. What comes next

Everything in this chapter carefully avoided one thing: **multiple threads touching the same shared, mutable data.** Every task either worked with local state, or only printed without modifying anything shared. The next chapter removes that restriction — and shows exactly what breaks when it's removed, and why.