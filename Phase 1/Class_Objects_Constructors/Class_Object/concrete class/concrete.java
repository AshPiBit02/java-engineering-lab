public class concrete {
    //Attributes
    int classId;
    String className;
    String classType;


    //Conctructor
    concrete(int classId,String className,String classType){
        this.classId=classId;
        this.className=className;
        this.classType=classType;
    }

    //Methods
    public void classInfo(){
        System.out.println("Class Id: "+classId +" | Class Name: "+ className + " | classType: " + classType);
    }
    public void classDesc(){
        System.out.println("It is a type of class that provides complete implementations for all its methods,\nmeaning it has no abstract or unimplemented methods.");
    }
    public static void main(String[] args){
        concrete c1=new concrete(235,"Dummy","Concrete");


        //Usage
        c1.classInfo();
        c1.classDesc();

    } 
    
    
}
