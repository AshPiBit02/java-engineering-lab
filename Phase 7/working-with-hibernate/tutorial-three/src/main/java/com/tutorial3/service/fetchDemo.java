package com.tutorial3.service;
import com.tutorial3.entities.Employee;

import java.util.List;
public class fetchDemo {
    static Service serv=new Service();

    public static void singleFetchDemo(){
        System.out.println("-".repeat(10)+"SINGLE EMPLOYEE FETCH"+"-".repeat(10));
        Employee emp=serv.getEmployeeById(1);
        System.out.println("Employee fetched. Now accessing department...");
        System.out.println("Department: "+emp.getDepartment().getName());
    }

    public static void listFetchDemo(){
        System.out.println("-".repeat(10)+"All Employees Fetch(N+1 Check)"+"-".repeat(10));
        List<Employee> employees=serv.getAllEmployees();
        System.out.println("Employees list fetched. Now looping and accessing department for each....");
        for(Employee emp: employees){
            System.out.println(emp.getName()+" -> "+ emp.getDepartment().getName());
        }
    }
    public static void main(String[] args){
        singleFetchDemo();
        System.out.println();
        listFetchDemo();
    }
}
