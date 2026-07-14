import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class IntegerFetcher {
    public static void main(String[] args) throws Exception {
        Random rand = new Random();
        List<Integer> integers = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            int num = rand.nextInt(101);
            if ((num % 2 == 0) || (num % 3 == 0) || (num % 5 == 0)) {
                integers.add(num);
            }
        }

        Callable<List<Integer>> multipleOfTwo = () -> {
            List<Integer> multiple2 = new ArrayList<>();
            Thread.sleep(rand.nextInt(101) + 50);
            for (Integer i : integers) {
                if (i % 2 == 0) {
                    multiple2.add(i);
                }
            }
            return multiple2;
        };
        Callable<List<Integer>> multipleOfThree = () -> {
            List<Integer> multiple3 = new ArrayList<>();
            Thread.sleep(rand.nextInt(101) + 75);
            for (Integer i : integers) {
                if (i % 3 == 0) {
                    multiple3.add(i);
                }
            }
            return multiple3;
        };
        Callable<List<Integer>> multipleOfFive = () -> {
            List<Integer> multiple5 = new ArrayList<>();
            Thread.sleep(rand.nextInt(101) + 90);
            for (Integer i : integers) {
                if (i % 5 == 0) {
                    multiple5.add(i);
                }
            }
            return multiple5;
        };

        ExecutorService executor = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();

        Future<List<Integer>> twoMultipleFuture = executor.submit(multipleOfTwo);
        Future<List<Integer>> threeMultipleFuture = executor.submit(multipleOfThree);
        Future<List<Integer>> fiveMultipleFuture = executor.submit(multipleOfFive);

        List<Integer> result2 = twoMultipleFuture.get();
        List<Integer> result3 = threeMultipleFuture.get();
        List<Integer> result5 = fiveMultipleFuture.get();

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Multiples of 2: ");
        for (Integer i : result2) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Multiples of 3: ");
        for (Integer i : result3) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Multiples of 5: ");
        for (Integer i : result5) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Total time taken: " + elapsed + "ms");

        executor.shutdown();
        boolean terminated = executor.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Executor terminated cleanly: " + terminated);

    }

}
