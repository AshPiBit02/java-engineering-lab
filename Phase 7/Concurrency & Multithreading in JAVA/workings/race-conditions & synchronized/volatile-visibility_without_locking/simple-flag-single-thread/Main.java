class Counter implements Runnable{
    private int counter=1;
    private volatile boolean running=true;
    
    public void stop(){
        running=false;
    }
    
    @Override
    public void run(){
        while(running){
            System.out.println(counter);
            counter++;
            try{
                Thread.sleep(200);
            }catch(InterruptedException e) {
                System.out.println("Paused "+ counter);
                break;
            }
        }
    }
}
public class Main{
    public static void main(String[] args) throws InterruptedException{
        Counter c=new Counter();
        Thread t = new Thread(c);

        t.start();

        Thread.sleep(5369);

        // t.interrupt();
        c.stop();
        t.join(); 
          
        System.out.println("Stoppped!");
    }

}