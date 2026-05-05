abstract class Person{
    abstract void Speak();
}

public class Main {
    public static void main(String[] args){
        Person male=new Person(){
            void Speak(){
                System.out.println("Greetings....");
            }

        };
        male.Speak();
    }
}
