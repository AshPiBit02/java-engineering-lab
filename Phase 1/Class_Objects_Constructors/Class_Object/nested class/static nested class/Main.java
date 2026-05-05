class Outer{
    static class Inner{
        void description(){
            System.out.println("About:\nDeclared with the 'static' keyword.\nCan access only the outer class's static members.\nUsed when the nested class doesn't need access to instance members of the outer class.");
        }
    }
}

public class Main {
    public static void main(String[] args){
        Outer.Inner obj=new Outer.Inner();
        obj.description();
    }
    
}
