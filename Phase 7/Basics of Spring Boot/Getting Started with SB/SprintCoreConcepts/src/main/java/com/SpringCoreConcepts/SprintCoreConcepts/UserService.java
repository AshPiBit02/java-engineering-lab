package com.SpringCoreConcepts.SprintCoreConcepts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    DataBase dataBase;
    Configurations configurations;
    public UserService(DataBase dataBase, Configurations configurations)
    {
        this.dataBase=dataBase;
        this.configurations = configurations;
        this.configurations=configurations;
    }

    public void saveUser(String user){
        System.out.println("UserService is saving user: "+user+ " with property: "+configurations);
        dataBase.save(user);
    }
}