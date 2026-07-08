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
        int prepTime = rand.nextInt(800) + 500;
        try {
            prepareDish(dishName, prepTime);
            System.out.println(chefName + ": " + dishName + " prepared!");
        } catch (InterruptedException e) {
            orderCancel = true;
            System.out.println("Head Chef: " + dishName + " Canceled!!!");
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
    private static int maxTime = 800;

    public static void main(String[] args) throws InterruptedException {
        Dish d1 = new Dish("Chef-1", "Briyani");
        Dish d2 = new Dish("Chef-2", "Sushi");
        Dish d3 = new Dish("Chef-3", "Ramen");
        Thread chef1 = new Thread(d1);
        Thread chef2 = new Thread(d2);
        Thread chef3 = new Thread(d3);

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

        if (d1.orderCancel() || d2.orderCancel() || d3.orderCancel()) {
            System.out.println("Customer frustrated!!");
        } else {
            System.out.println("Customer enjoying dishes");
        }

    }

}
