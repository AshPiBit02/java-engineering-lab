import com.university.student.Student;
import com.university.student.Enrollment;
import com.university.teacher.Teacher;
import com.university.teacher.Subject;

public class Main {
    public static void main(String[] args){
        Student s1=new Student("Aashish",102);
        Enrollment e1=new Enrollment(s1,"Science & Teachnology",2024);

        Teacher t1=new Teacher("Jon Snow","Data Science");
        Subject sub1=new Subject("Deep Learning",3);

        e1.showEnrollment();
        t1.teach(sub1.toString());
        t1.teach("Math");
    }
    
}
