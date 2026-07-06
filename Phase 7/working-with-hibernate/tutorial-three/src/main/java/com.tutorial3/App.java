package com.tutorial3;
import com.tutorial3.entities.Department;
import com.tutorial3.service.Usage;
public class App{
    public  static void main(String[] args){
        Department dept=Usage.addDepartment("Engineering","Building A");
        Usage.addEmployee("John Snow","aegonTargerian@gmail.com",988856,"Kil'M ST. 89","97XXXXXXXX","Module Tester",dept);




    }
}