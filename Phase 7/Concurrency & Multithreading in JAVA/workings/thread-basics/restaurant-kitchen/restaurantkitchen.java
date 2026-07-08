import java.util.Random;

class Dish implements Runnable {
    private String dishName;
    private String chefName;
    private Random rand = new Random();
    private boolean orderCancel = false;

    Dish(String chefName, String dishName) {
        this.chefName = chefName;
        this.dishName = dishName;
    }

    @Override
    public void run() {
        int prepTime = rand.nextInt(1501) + 500;
        try {
            prepareDish(dishName, prepTime);
        } catch (InterruptedException e) {
            orderCancel = true;
            System.out.println("Head Chef: Order Canceled!!!");
        }
    }

    private void prepareDish(String dish, int prepTime) throws InterruptedException {
        System.out.println(chefName + ": Preparing " + dish + "......");
        Thread.sleep(prepTime);
    }

    public boolean orderCancel() {
        return orderCancel;
    }
}

public class restaurantkitchen {
    private static int maxTime = 5000;

    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new Thread(new Dish("Chef-1", "Briyani"));
        Thread chef2 = new Thread(new Dish("Chef-2", "Sushi"));
        Thread chef3 = new Thread(new Dish("Chef-3", "Ramen"));

        chef1.start();
        chef2.start();
        chef3.start();

        Thread.sleep(maxTime);

        chef1.interrupt();
        chef2.interrupt();
        chef3.interrupt();

        chef1.join();
        chef2.join();
        chef3.join();

        Dish d = new Dish(null, null);
        if (d.orderCancel()) {
            System.out.println("Customer frustrated!!");
        } else {
            System.out.println("Customer enjoying dishes");
        }

    }

}
