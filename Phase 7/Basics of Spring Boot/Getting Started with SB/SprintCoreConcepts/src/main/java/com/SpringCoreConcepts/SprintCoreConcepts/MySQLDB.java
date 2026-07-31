package com.SpringCoreConcepts.SprintCoreConcepts;

import org.springframework.stereotype.Component;

@Component
public class MySQLDB implements DataBase {
    @Override
    public void save(String user) {
        System.out.println("Saving "+user+" to mySQLDB");
    }
}
