public class car {
    String make;
    String model;
    int year;
    public car(String make,String model,int year){
        this.make=make;
        this.model=model;
        this.year=year;
    }
     public void displayInfo(){
        System.out.println(year + " " + make + " " + model);
     }

     public static void main (String[] args){
        car car1 =new car("Toyota","Camry",1978);
        car car2 =new car("Honda","Civic",1993);

        car1.displayInfo();
        car2.displayInfo();
     }

    
}