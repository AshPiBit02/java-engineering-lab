class Outer{
    void outerMethod(){
        //Local Inner class defined inside a outerMethod
        class LocalInner{
            void print(){
                System.out.println("About:\nDefined inside a method or block.\nScope is limited to that method/block.\nUsed when you need a helper class that is only relevant inside a single method.");
            }
        }
        LocalInner obj=new LocalInner();
        obj.print();
    }
}

public class Main {
    public static void main(String[] args){
        Outer obj=new Outer();
        obj.outerMethod();
    }
    
}
