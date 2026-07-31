package com.SpringCoreConcepts.SprintCoreConcepts;

public class UserService {
    PostgresDB postgresDB=new PostgresDB();
    public void saveUser(String user){
        System.out.println("UserService is saving user: "+user);
        postgresDB.saveToDB(user);
    }
}
