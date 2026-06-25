package com.init;

import javafx.beans.property.*;

public class Student {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty course;
    private StringProperty faculty;
    private StringProperty level;

    public Student(int id, String name, String course, String faculty, String level) {
        this.id = id;
        this.name = nmae;
        this.course = course;
        this.faculty = faculty;
        this.level = level;
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getCourse() {
        return course.get();
    }

    public String getFaculty() {
        return faculty.get();
    }

    public String getLevel() {
        return level.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty courseProperty() {
        return course;
    }

    public StringProperty facultyProperty() {
        return faculty;
    }

    public StringProperty levelProperty() {
        return level;
    }

}
