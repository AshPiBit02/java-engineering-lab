public static void DataFetcherParallel() {
        System.out.println("Parallel Fetching");
        System.out.println("-".repeat(50));
        Random rand = new Random();

        Callable<String> userTask = () -> {
            Thread.sleep(rand.nextInt(501) + 200);
            return "User data fetched!";
        };

        Callable<String> orderTask = () -> {
            Thread.sleep(rand.nextInt(501) + 200);
            return "Order data fetched!";
        };

        Callable<String> inventoryTask = () -> {
            Thread.sleep(rand.nextInt(501) + 200);
            return "Invenotry data fetched!";
        };

        ExecutorService executor = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();

        Future<String> userFuture = executor.submit(userTask);
        Future<String> orderFuture = executor.submit(orderTask);
        Future<String> inventoryFuture = executor.submit(inventoryTask);

        String userResult = userFuture.get();
        String orderResult = orderFuture.get();
        String inventoryResult = inventoryFuture.get();

        long elapsed = System.currentTimeMillis() - start;

        System.out.println(userResult);
        System.out.println(orderResult);
        System.out.println(inventoryResult);

        System.out.println("All services responded. Rendering final page.");
        System.out.println("Total time taken: " + elapsed + "ms");

        executor.shutdown();
        boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Executor terminated cleanly: " + terminated);
    }

    public static void DataFetcherSequential() {
        System.out.println("Sequential Fetching");
        System.out.println("-".repeat(50));
        Random rand = new Random();

        long start = System.currentTimeMillis();

        Thread.sleep(rand.nextInt(501) + 200);
        String userResult = "User data fetched!";

        Thread.sleep(rand.nextInt(501) + 200);
        String orderResult = "Order data fetched!";
        Thread.sleep(rand.nextInt(501) + 200);
        String inventoryResult = "Inventory data fetched!";

        long elapsed = System.currentTimeMillis() - start;

        System.out.print(userResult);
        System.out.print(orderResult);
        System.out.print(inventoryResult);

        System.out.println("All serives responded. Rendering final page.");
        System.out.println("Total time taken(sequential): " + elapsed + "ms");
    }