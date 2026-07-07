package com.tutorial3.entities;
import jakarta.persistence.*;

@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long employeeId;

    @Column(name="employee_name",length = 50,nullable = false)
    private String name;

    @Column(name = "email",length = 40,nullable = false,unique = true)
    private String email;

    @Column(name="salary",nullable = false)
    private double salary;

    @Column(name="address",length = 100,nullable = false)
    private String address;

    @Column(name="contact",length = 15,nullable = false)
    private String contact;

    @Column(name="job_title",length = 30,nullable = false)
    private String jobTitle;

    @Column(name="departemnt",length=50,nullable=false)
    private String department;

    public long getEmployeeId(){
        return employeeId;
    }

    public void setDepartment(String dept){
        this.department=department;
    }
    public String getDepartment(){
        return department;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return  email;
    }

    public void setSalary(double salary){
        this.salary=salary;
    }
    public double getSalary(){
        return salary;
    }

    public void setContact(String contact){
        this.contact=contact;
    }
    public String getContact(){
        return contact;
    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return address;
    }

    public void setJobTitle(String jobTitle){
        this.jobTitle=jobTitle;
    }
    public String getJobTitle(){
        return jobTitle;
    }

}
