import java.util.LinkedList;
import java.util.Random;

class Garage {
    private int spotsAvailable = 15;
    private LinkedList<String> garage = new LinkedList<>();

    public synchronized void arrive(String car) throws InterruptedException {
        while (spotsAvailable == 0) {
            System.out.println(car + " waiting for parking space.");
            wait();
        }
        garage.add(car);
        spotsAvailable--;
        System.out.println(car + " parked!");
        notifyAll();
    }

    public synchronized void leave() throws InterruptedException {
        while (garage.isEmpty()) {
            System.out.println("No car in parking!");
            wait();
        }
        String leavingCar = garage.getFirst();
        garage.removeFirst();
        spotsAvailable++;
        System.out.println(leavingCar + " left the parking!");
        notifyAll();
    }
}

public class Cars {
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        Garage gar = new Garage();

        Thread carsArrival = new Thread(() -> {
            for (int i = 1; i <= 53; i++) {
                try {
                    gar.arrive("Car-" + i);
                    Thread.sleep(rand.nextInt(151) + 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread carsLeave = new Thread(() -> {
            for (int i = 1; i <= 53; i++) {
                try {
                    gar.leave();
                    Thread.sleep(rand.nextInt(401) + 200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        carsArrival.start();
        carsLeave.start();

        carsArrival.join();
        carsLeave.join();

        System.out.println("Garage Closed!");

    }
}
