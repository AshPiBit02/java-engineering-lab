abstract class Anonymous{
    abstract void description();
}

public class Main {
    public static void main(String[] args){
        Anonymous obj=new Anonymous(){
            void description(){
                System.out.println("About:\nA Class without a name, declared and instantiated at the same time.\nCommonly used for overriding methods quickly.\nUsed when you need a one-off implementation, especially for interfaces or abstract classes.");
            }

        };
        obj.description();
    }
}
