class Singleton{
    //private static instance
    private static Singleton instance;

    //private constructor
    private Singleton()
    {
        System.out.println("Singleton instance created.");
    }
    // public static method to get instance
    public static Singleton getInstance(){
        if(instance==null){
            instance = new Singleton();//create only once
        }
        return instance;
    }

    public void description(){
        System.out.println("Description:\nPrevents direct instantiation with new.\n Holds the single object.\nProvides global access to that single instance.\nEnsures only one instance is created even in multithreaded environment.");

    }
}
public class Main {
    public static void main(String[] args){
        Singleton s1=Singleton.getInstance();
        Singleton s2=Singleton.getInstance();
        System.out.println(s1 == s2); // True means no matter how many times you try to create objects. Only one object will be created.
        s1.description();
        // s2.description(); //Same as s1
    }

    
}
