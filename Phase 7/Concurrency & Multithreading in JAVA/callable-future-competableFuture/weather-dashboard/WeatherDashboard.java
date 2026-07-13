import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class WeatherDashboard {
    public static void main(String[] args) {
        Random rand = new Random();

        CompletableFuture<Integer> tempFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int temp = rand.nextInt(41) + 60;
            System.out.println("Temperature: " + temp + "C");
            return temp;
        });

        CompletableFuture<Integer> humidityFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int humidity = rand.nextInt(61) + 30;
            System.out.println("Humidity: " + humidity + "%");
            return humidity;
        });

        CompletableFuture<Integer> comfortIndexFuture = tempFuture.thenCombine(humidityFuture,
                (temp, humidity) -> (int) (temp - (humidity / 10)));

        CompletableFuture<Integer> airQualityFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int aqi = rand.nextInt(201);
            System.out.println("AQI: " + aqi);
            return aqi;
        });

        CompletableFuture<String> summaryFuture = comfortIndexFuture.thenCombine(airQualityFuture,
                (comfortIndex, aqi) -> "Comfort: " + comfortIndex + ", Air Quality: " + aqi);

        CompletableFuture<Void> finalStep = summaryFuture
                .thenAccept(summary -> System.out.println("Final Dashboard -> " + summary));

        long start = System.currentTimeMillis();
        finalStep.join();
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Total time: " + elapsed + "ms");
    }
}
