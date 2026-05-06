public class Student {
    int roll_no,id;
    String name,department;

    //parameterized constructor
    Student(int id,int roll_no,String name,String department){
        System.out.println("(Parameterized constructor called...)");
        this.id=id;
        this.roll_no=roll_no;
        this.name=name;
        this.department=department;
    }
    
    //Copy constructor
    Student(Student s){
        System.out.println("(Copy constructor called...)");
        this.id=s.id;
        this.roll_no=s.roll_no;
        this.name=s.name;
        this.department=s.department;
    }

    public void studentInfo(){
        System.out.printf("Student Info:\nID: %d\nRoll_no: %d\nName: %s\nDepartment: %s\n",id,roll_no,name,department);
    }
    public static void main(String[] args){
        Student s=new Student(20242,2,"Jon Snow","Science & Technology");
        s.studentInfo();

        Student copy=new Student(s);
        copy.studentInfo();
    
    }
}

