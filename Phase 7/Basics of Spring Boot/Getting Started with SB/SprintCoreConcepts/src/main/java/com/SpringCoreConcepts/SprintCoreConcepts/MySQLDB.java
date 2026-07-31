package com.SpringCoreConcepts.SprintCoreConcepts;


public class MySQLDB implements DataBase {
    @Override
    public void save(String user) {
        System.out.println("Saving "+user+" to mySQLDB");
    }
}
