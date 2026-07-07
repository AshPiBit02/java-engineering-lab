package com.tutorial3.service;

import com.tutorial3.entities.Department;
import com.tutorial3.entities.Employee;

public class Usage {
    static Service serv=new Service();

    static public void addDepartment(String name,String location){
        Department dept=new Department();
        dept.setName(name);
        dept.setLocation(location);
        serv.saveDepartment(dept);
    }

    static public void addEmployee(String name,String email,double salary,String address,String contact,String job_title,long departmentId){
        Department dept=serv.getDepartmentById(departmentId);
        Employee emp=new Employee();
        emp.setName(name);
        emp.setEmail(email);
        emp.setSalary(salary);
        emp.setAddress(address);
        emp.setContact(contact);
        emp.setJobTitle(job_title);
        emp.setDepartment(dept);
        serv.saveEmployee(emp);
    }
    static public void empWithSalaryGt(double salary){
        serv.fetchAllEmployeeSalary(salary);
    }
}
