package com.SpringCoreConcepts.SprintCoreConcepts;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name="db.type",havingValue = "mysql")
public class MySQLDB implements DataBase {
    @Override
    public void save(String user) {
        System.out.println("Saving "+user+" to mySQLDB");
    }
}
