package com.tutorial3.entities;

import jakarta.persistence.*;

@Entity
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="department_id")
    private long departmentId;

    @Column(name="department_name",length = 50,nullable = false,unique = true)
    private String name;

    @Column(name="location",length = 100,nullable = false)
    private String location;

    public long getDepartmentId(){
        return departmentId;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public String getLocation(){
        return location;
    }
}
