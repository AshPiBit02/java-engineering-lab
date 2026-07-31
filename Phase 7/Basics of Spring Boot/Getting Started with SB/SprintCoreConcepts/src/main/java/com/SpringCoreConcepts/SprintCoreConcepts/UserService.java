package com.SpringCoreConcepts.SprintCoreConcepts;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    DataBase dataBase;
    public UserService(DataBase dataBase)
    {
        this.dataBase=dataBase;
    }

    public void saveUser(String user){
        System.out.println("UserService is saving user: "+user);
        dataBase.save(user);
    }
}
