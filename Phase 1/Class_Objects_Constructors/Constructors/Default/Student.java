public class Student {
    String name,id,gender,department;
    int roll_no;
    Student(){
        System.out.println("This is a default constructor which is got automatically called.");
        id="Unknown";
        roll_no=0;
        name="Unknown";
        gender="Unknown";
        department="Unknown";
    }
    void details(){
        System.out.printf("Student Id: %s\nRoll no.: %d\nName: %s\nGender: %s\nDepartment: %s",id,roll_no,name,gender,department);
    }

    public static void main(String[] args){
        Student obj=new Student();
        obj.details();
    }
    
}
