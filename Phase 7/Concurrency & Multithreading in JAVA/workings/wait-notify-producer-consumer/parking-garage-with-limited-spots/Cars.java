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

    public boolean isGarageEmpty() {
        return garage.isEmpty();
    }

    public int numUndepaturedCars() {
        return garage.size();
    }
}

public class Cars {
    public static void main(String[] args) throws InterruptedException {
        Garage gar = new Garage();

        int numArrivalThreads = 4;
        int numDepartureThreads = 3;
        int arrivalsPerThread = 15;
        int departuresPerThread = 20;

        Thread[] arrivalThreads = new Thread[numArrivalThreads];
        Thread[] departureThreads = new Thread[numDepartureThreads];

        for (int t = 0; t < numArrivalThreads; t++) {
            final int threadId = t;
            arrivalThreads[t] = new Thread(() -> {
                Random rand = new Random();
                for (int i = 1; i <= arrivalsPerThread; i++) {
                    try {
                        gar.arrive("Car-" + threadId + "-" + i);
                        Thread.sleep(rand.nextInt(151) + 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int t = 0; t < numDepartureThreads; t++) {
            departureThreads[t] = new Thread(() -> {
                Random rand = new Random();
                for (int i = 1; i <= departuresPerThread - 1; i++) {
                    try {
                        gar.leave();
                        Thread.sleep(rand.nextInt(401) + 200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
        }

        for (Thread t : arrivalThreads)
            t.start();
        for (Thread t : departureThreads)
            t.start();

        for (Thread t : arrivalThreads)
            t.join();
        for (Thread t : departureThreads)
            t.join();

        if (gar.isGarageEmpty()) {
            System.out.println("Garage Closed!");
        } else {
            System.out.println(gar.numUndepaturedCars() + " cars has been stolen! Call the cops");
        }
    }
}
