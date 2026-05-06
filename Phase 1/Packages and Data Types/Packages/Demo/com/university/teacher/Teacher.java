package com.university.teacher;

public class Teacher {
    private String name;
    private String department;

    public Teacher(String name,String department){
        this.name=name;
        this.department=department;
    }

    public void teach(String subject){
        System.out.println(name + " is teaching " + subject + " in " + department+".");
    }
    
}
