abstract class College{
    //abstract method
    public abstract void location();
    //Concrete method
    public void description(){
        System.out.println("Descriptions:\nContains both abstract and concrete methods.\nAllows sharing common logic while enforcing certain methods.");

    }

}
class Location extends College{

    //abstract method override
    public void location(){
        System.out.println("Pokhara Engineering College is location at Pokhara, Nepal.");
    }
}

public class mixed{
    public static void main(String[] args){
        Location l=new Location();
        l.location();
        l.description();

    }
    
}
