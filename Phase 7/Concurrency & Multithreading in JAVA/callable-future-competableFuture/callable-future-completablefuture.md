# Chapter 6: `Callable`, `Future`, `CompletableFuture`

## 1. The gap `Runnable` leaves open

Every task submitted through an `ExecutorService` so far has been a `Runnable` — its `run()` method returns `void`. This is fine for pure side effects (printing, updating shared state), but often a background task needs to **compute something and hand the result back** — "fetch this data and give it to me once it's ready." `Runnable` cannot express this directly; working around it with shared mutable fields reintroduces exactly the kind of race conditions covered in earlier chapters.

This chapter covers the three tools built specifically to close this gap, in increasing order of capability: `Callable` (a task that returns a value), `Future` (a handle to that eventually-available value), and `CompletableFuture` (a way to chain dependent asynchronous work without blocking at every step).

## 2. `Callable<T>` — interface

Package: `java.util.concurrent.Callable<V>`

A functional interface with a single method:
```java
V call() throws Exception;
```

Compared to `Runnable.run()`:
- Returns a value of type `V` instead of `void`.
- Declares `throws Exception` — meaning a `Callable` can throw **any** checked exception, not just `InterruptedException`. This is a meaningful upgrade over `Runnable`, whose `run()` cannot declare any checked exception at all.

```java
Callable<Integer> task = () -> {
    Thread.sleep(500);
    return 42;
};
```

A `Callable` on its own does nothing — like a `Runnable`, it only describes work. It must be submitted to an executor (or otherwise invoked) to actually run.

## 3. `Future<T>` — interface

Package: `java.util.concurrent.Future<V>`

Returned by `ExecutorService.submit(Callable<T>)`, representing a result that may not exist yet.

```java
ExecutorService executor = Executors.newFixedThreadPool(2);
Future<Integer> future = executor.submit(task);
```

**Methods:**

| Method | Purpose |
|---|---|
| `V get()` | Blocks the calling thread until the task completes, then returns its result. Throws checked `InterruptedException` (if the waiting thread itself is interrupted) and `ExecutionException` (if the task's `call()` itself threw an exception — the original exception is wrapped inside, accessible via `getCause()`). |
| `V get(long timeout, TimeUnit unit)` | Same as above, but gives up and throws `TimeoutException` if the result isn't ready within the given time. |
| `boolean isDone()` | Non-blocking check for whether the task has finished (successfully, exceptionally, or via cancellation). |
| `boolean isCancelled()` | Whether the task was cancelled before completing normally. |
| `boolean cancel(boolean mayInterruptIfRunning)` | Attempts to cancel the task. If `mayInterruptIfRunning` is `true` and the task is already running, it will be interrupted (same cooperative-cancellation caveat as `Thread.interrupt()` — a task that never checks for interruption will not actually stop). |

**Essential note on `get()`:** it plays a conceptually similar role to `Thread.join()` — waiting for something to finish — but returns an actual **value** instead of just resuming execution. Also essential: if multiple independent `Future`s are all submitted *before* any of them are queried with `get()`, calling `get()` on each one afterward does not add up their individual durations. Since all tasks were already running concurrently in the background, the total wait time approaches whichever task takes the **longest**, not the sum of all of them. This was demonstrated directly by timing a 3-task parallel fetch (all `submit()`ted first, then all three `.get()`ed) against the same three operations run purely sequentially — the parallel version's total time tracked the slowest individual task, while the sequential version's total time tracked the sum of all three.

## 4. `CompletableFuture<T>` — class

Package: `java.util.concurrent.CompletableFuture<T>`

`Future.get()` forces the calling thread to block and wait for a result before doing anything further. `CompletableFuture` allows **chaining** what happens once a result becomes available, without the calling thread ever blocking mid-chain — genuinely asynchronous, callback-style composition. By default, its asynchronous methods run on a shared common thread pool (`ForkJoinPool.commonPool()`), whose threads are daemon threads — meaning a program can exit before an in-progress chain finishes if nothing explicitly waits for it.

### Starting an asynchronous computation

| Method | Purpose |
|---|---|
| `static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)` | Starts a computation that **returns a value**, asynchronously, immediately. |
| `static CompletableFuture<Void> runAsync(Runnable runnable)` | Starts a computation with **no return value**, asynchronously. |
| Both also have overloads accepting an explicit `Executor` (e.g., a specific `ExecutorService`) instead of using the default common pool. |

```java
CompletableFuture<Integer> priceFuture = CompletableFuture.supplyAsync(() -> {
    // runs on a background thread immediately; this line does not block the caller
    return 42;
});
```

### Transforming a result once it's ready (chaining, non-blocking)

| Method | Purpose |
|---|---|
| `thenApply(Function<T,R>)` | Transforms the result once ready, returns a new `CompletableFuture<R>` — chainable. |
| `thenApplyAsync(Function<T,R>)` | Same, but explicitly runs the transformation on a (possibly different) async thread rather than whichever thread happened to complete the prior stage. |
| `thenAccept(Consumer<T>)` | Consumes the final result (e.g., to print it) — returns `CompletableFuture<Void>`, since there's nothing further to pass along. |
| `thenRun(Runnable)` | Runs a follow-up action that needs no access to the previous result at all. |

```java
CompletableFuture<Integer> discountedPriceFuture = priceFuture.thenApply(price -> (int) (price * 0.9));
```

### Combining two independent asynchronous results

| Method | Purpose |
|---|---|
| `thenCombine(CompletableFuture<U> other, BiFunction<T,U,R> combiner)` | Waits for **both** this future and `other` to complete, then combines their results into a new value — without blocking either side individually. |
| `thenCompose(Function<T, CompletableFuture<U>>)` | Chains one async operation whose *own result* is itself another `CompletableFuture` (flattening nested futures) — useful when a step's next action needs to trigger another independent async call. |

```java
CompletableFuture<Integer> totalFuture = discountedPriceFuture.thenCombine(
    shippingFuture,
    (price, shipping) -> price + shipping
);
```

Multiple `thenCombine` calls can be chained in sequence to merge more than two independent sources — each call only needs to wait for its own two immediate inputs to be ready, regardless of how many stages preceded them.

### Blocking to retrieve a final value

| Method | Purpose |
|---|---|
| `get()` | Same as `Future.get()` — blocks, throws checked `InterruptedException`/`ExecutionException`. |
| `join()` | Equivalent to `get()`, but throws only **unchecked** exceptions (`CompletionException` wrapping the real cause) — commonly used at the very end of a chain, since it doesn't force a surrounding try/catch. |

**Why a blocking call is still needed at the end of an otherwise non-blocking chain:** since the common pool's threads are daemons, the program could exit before an in-progress async chain finishes printing or completing its side effects. Calling `.join()` (or `.get()`) once, at the very end, ensures the calling thread waits for the entire chain to actually finish before the program proceeds or exits.

## 5. Reasoning about total time in a chain

A chain built from independent starting points and later merged should take roughly as long as its **slowest independent branch**, not the sum of every stage's duration — the same principle as plain `Future`, just applied through chaining instead of manual `get()` calls on separate futures. This was verified directly: two independent `supplyAsync` calls (differing sleep durations) combined via `thenCombine` completed in a total time close to the *longer* of the two individual delays, not their sum — and this held even when extended to three independent sources merged through two sequential `thenCombine` calls.

## 6. Summary table of everything covered in this chapter

| Type | Kind | Core purpose |
|---|---|---|
| `Callable<V>` | Interface | A task that returns a value and may throw checked exceptions |
| `Future<V>` | Interface | A handle to a result that may not be ready yet; `get()` blocks until it is |
| `CompletableFuture<T>` | Class (implements `Future<T>`) | Chainable, non-blocking asynchronous computation and composition |

**Key `Future` methods:** `get()`, `get(timeout, unit)`, `isDone()`, `isCancelled()`, `cancel(boolean)`

**Key `CompletableFuture` methods:** `supplyAsync(...)`, `runAsync(...)`, `thenApply(...)`, `thenApplyAsync(...)`, `thenAccept(...)`, `thenRun(...)`, `thenCombine(...)`, `thenCompose(...)`, `get()`, `join()`

## 7. Reference implementations from this chapter

- **Parallel Data Fetcher** — three independent `Callable`s submitted to a fixed pool, retrieved via three `Future.get()` calls, with total elapsed time confirmed to track the slowest task rather than the sum — directly contrasted against a purely sequential version of the same three operations.
- **Async Order Pipeline** — a `CompletableFuture` chain combining a sequential dependency (`supplyAsync` → `thenApply` for a discount) with an independent parallel branch (a separate `supplyAsync` for shipping), merged via `thenCombine`, consumed via `thenAccept`, and finalized with `.join()`.
- **Weather Dashboard** — extended the same pattern to three independent sources merged through two sequential `thenCombine` calls, confirming the "slowest branch, not the sum" timing principle continues to hold with more than two merging inputs.

## 8. What comes next

Every shared collection used in earlier chapters (a buffer, a queue, a map of results) was hand-protected with `synchronized`, `ReentrantLock`, or `wait()`/`notify()`/`Condition` written manually. The final chapter introduces Java's **pre-built concurrent collections** — `ConcurrentHashMap` and `BlockingQueue` — which provide the same safety guarantees internally, without requiring any of that manual locking code to be written by hand, and closes with a single project combining nearly everything from the roadmap.