package com.university.teacher;

public class Subject {
    private String title;
    private int credits;

    public Subject(String title,int credits){
        this.title=title;
        this.credits=credits;
    }

    @Override
    public String toString(){
        return title+ " (" + credits + " credits)";
    }
}
