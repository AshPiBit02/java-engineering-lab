class Outer{
    private String message="Hello from Outer class.";

    class Inner{
        void description(){
            System.out.println("About:\nAssociated with an instance of the outer class.\nCan access all members(even private) of the outer class.\nUses when the nested class needs access to instance members of the outer class.");
            System.out.print(message);
        }
    }
}

public class Main {
    public static void main(String[] args){
        Outer outer=new Outer();
        Outer.Inner inner=outer.new Inner();
        inner.description();
    }
    
}
