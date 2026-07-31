package com.SpringCoreConcepts.SprintCoreConcepts;

import javax.xml.crypto.Data;

public class PostgresDB implements DataBase {
    @Override
    public void save(String user) {
        System.out.println("Saving "+user+" to postgresDB");
    }
}
