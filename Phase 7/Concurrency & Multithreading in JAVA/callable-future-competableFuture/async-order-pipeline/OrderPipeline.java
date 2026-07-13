import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class OrderPipeline {
    public static void main(String[] args) {
        Random rand = new Random();

        CompletableFuture<Integer> priceFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int price = rand.nextInt(401) + 100;
            System.out.println("Price fetched: $" + price);
            return price;
        });

        CompletableFuture<Integer> discountedPriceFuture = priceFuture.thenApply(price -> {
            int discounted = (int) (price * 0.9);
            System.out.println("Discounted applied. New price: $" + discounted);
            return discounted;
        });

        CompletableFuture<Integer> shippingFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int shipping = rand.nextInt(41) + 10;
            System.out.println("Shipping cost calculated: $" + shipping);
            return shipping;
        });

        CompletableFuture<Integer> totalFuture = discountedPriceFuture.thenCombine(shippingFuture,
                (discountedPrice, shippingCost) -> discountedPrice + shippingCost);

        CompletableFuture<Void> finalStep = totalFuture
                .thenAccept(total -> System.out.println("Final order total: $" + total));

        long start = System.currentTimeMillis();

        finalStep.join();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Total pipeline time: " + elapsed + "ms");
    }
}