public class Main{
    public static void main(String[] args){
        AppLogger logger1=AppLogger.getInstance();
        AppLogger logger2 = AppLogger.getInstance();
        
        logger1.log("This message is from logger1, which is giving 4XX error!");
        logger2.log("This message is from logger2, which is giving 5XX error!");

        System.out.println("Logs count: "+logger1.getLogCount());
    }
}