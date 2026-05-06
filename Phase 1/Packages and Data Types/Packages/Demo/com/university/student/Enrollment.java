package com.university.student;

public class Enrollment {
    private Student student;
    private String course;
    private int year;

    public Enrollment(Student student,String course,int year){
        this.student=student;
        this.course=course;
        this.year=year;
    }

    public void showEnrollment(){
        System.out.println(student + " enrolled in " + course + " in year " + year);
    }
    
}
