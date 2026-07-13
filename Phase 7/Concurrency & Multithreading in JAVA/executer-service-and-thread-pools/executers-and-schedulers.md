# Chapter 5: `ExecutorService` & Thread Pools

## 1. The problem with manually managed threads

Every prior chapter created raw `Thread` objects directly — `new Thread(...)`, stored in arrays, started and joined in loops. Each real OS thread costs actual memory (roughly a 1MB stack by default) and real scheduling overhead. Creating hundreds or thousands of them for short-lived tasks is wasteful, and manually tracking arrays of `Thread` objects becomes unwieldy as systems grow.

**The fix: thread pools.** Instead of "one dedicated thread per task," maintain a fixed, reusable set of worker threads, and **submit** tasks to be run on whichever thread is currently free. Extra tasks simply wait in a queue rather than forcing the creation of more threads.

## 2. `ExecutorService` — the manager for a pool

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

ExecutorService executor = Executors.newFixedThreadPool(4);
executor.submit(() -> {
    System.out.println("Task running on " + Thread.currentThread().getName());
});
```

With this model, `new Thread(...)` is no longer written directly for ordinary task work — tasks are handed to `submit()` as `Runnable`s (or, as the next chapter covers, `Callable`s), and the executor decides which pooled thread actually executes each one, reusing threads across many tasks rather than creating one per task.

### Pool types and when each fits

| Factory method | Behavior |
|---|---|
| `Executors.newFixedThreadPool(n)` | Exactly `n` reusable threads; excess submitted tasks queue and wait |
| `Executors.newCachedThreadPool()` | Grows threads as needed, reuses idle ones, no fixed upper limit — well suited to many short-lived bursts of work, but risky for long-running tasks since it can grow unboundedly under sustained load |
| `Executors.newSingleThreadExecutor()` | Exactly one thread — submitted tasks run strictly one at a time, in submission order |

This distinction was demonstrated directly by comparing pool types under the same workload: a cached pool tends to start nearly everything immediately (little to nothing sits queued), while a fixed pool with fewer threads than tasks leaves a large portion of the work genuinely queued and waiting.

## 3. Shutdown — a mandatory step, not an optional cleanup

Pool threads are **not daemon threads** by default. If an executor is never shut down, its worker threads keep running indefinitely, and the JVM will never exit — even after `main()` itself has finished executing. This is a very common mistake, since it's easy to forget precisely because nothing appears to be wrong until the program simply never terminates.

```java
executor.shutdown();
executor.awaitTermination(5, TimeUnit.SECONDS);
```

- **`shutdown()`** — graceful. Stop accepting new tasks, but let anything already running or queued finish normally.
- **`shutdownNow()`** — forceful. Attempts to stop everything immediately: it calls `interrupt()` on threads currently running tasks (the same cooperative-cancellation mechanism from earlier chapters — a task that never checks for interruption or never calls something interruptible simply keeps running regardless), and it discards any tasks that were still sitting in the queue, returning them as a `List<Runnable>` so you know exactly what was abandoned.

```java
List<Runnable> neverStarted = executor.shutdownNow();
```

**Pool size directly determines what `shutdownNow()` can actually discard.** This was demonstrated concretely: with a `newCachedThreadPool()`, nearly every submitted task had already started running by the time `shutdownNow()` was called, so the "never started" list came back essentially empty. Switching to a `newFixedThreadPool()` with far fewer threads than tasks left a large number of tasks genuinely still queued, and `shutdownNow()` correctly reported a large "never started" count.

**`awaitTermination(timeout, unit)`** blocks the calling thread until either the pool has fully terminated, or the given timeout elapses — whichever comes first — and returns a boolean indicating which happened. This is a meaningfully different tool from `Thread.join()`: `join()` waits indefinitely for one specific thread; `awaitTermination()` waits (with a bound) for an entire pool's tasks to wind down, and lets the calling code distinguish "actually finished" from "gave up waiting."

## 4. `ScheduledExecutorService` — running tasks on a timer

Every executor covered so far runs a submitted task exactly once, as soon as a thread is free. `ScheduledExecutorService` adds the ability to run something after a delay, or repeatedly at a fixed interval.

```java
import java.util.concurrent.ScheduledExecutorService;

ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// run once, after a delay:
scheduler.schedule(() -> System.out.println("ran once"), 3, TimeUnit.SECONDS);

// run repeatedly:
scheduler.scheduleAtFixedRate(() -> System.out.println("tick"), 0, 2, TimeUnit.SECONDS);
```

**`scheduleAtFixedRate(task, initialDelay, period, unit)`** attempts to start the task every `period`, but does not allow overlapping executions — if a single run takes longer than `period`, the next run begins immediately after the slow one finishes, rather than "catching up" by running twice back to back.

**`scheduleWithFixedDelay(...)`** is a close relative worth knowing exists: instead of timing the next run from when the previous one *started*, it waits `period` after the previous run *finishes*. The distinction matters when task duration is variable or unpredictable.

The same shutdown discipline applies here as with any other executor — a scheduler left running with `scheduleAtFixedRate` will simply keep firing forever unless explicitly shut down.

## 5. Reference implementations from this chapter

- **Print Queue rewritten with `ExecutorService`** — the exact producer-consumer logic from Chapter 3, restructured so submitters and printers are pooled tasks (`submit()`) rather than individually managed `Thread` objects, demonstrated with pool sizes deliberately smaller than the number of logical roles to observe thread reuse.
- **Log Processing System** — direct side-by-side comparison of `newCachedThreadPool()` versus `newFixedThreadPool()` using `shutdownNow()`, specifically to observe how pool size changes how many tasks are caught mid-run (interruptible) versus discarded outright from the queue (never started).
- **Server Health Monitor** — a `ScheduledExecutorService` running repeating, independent health checks for multiple "servers" via `scheduleAtFixedRate`, running alongside a separate `ExecutorService` handling one-off, randomly-timed "incident" tasks — demonstrating that two independent executor-managed systems can run concurrently and be shut down together cleanly.

## 6. What comes next

Every task submitted so far has been a `Runnable` — its `run()` method returns nothing. Often, a background task needs to actually **compute and return a result** rather than only performing a side effect. The next chapter introduces `Callable<T>` (a `Runnable` that returns a value and may throw checked exceptions), `Future<T>` (a handle to a result that isn't ready yet), and `CompletableFuture` (a way to chain dependent asynchronous steps without ever blocking to wait for an intermediate result).