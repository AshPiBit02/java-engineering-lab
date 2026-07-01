package com.tutorial;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

import com.tutorial.util.HibernateUtil;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println(sessionFactory);
    }
}
