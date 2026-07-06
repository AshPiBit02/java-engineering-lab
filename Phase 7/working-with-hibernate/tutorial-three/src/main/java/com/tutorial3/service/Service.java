package com.tutorial3.service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.tutorial3.entities.*;
import com.tutorial3.util.HibernateUtil;
public class Service {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();



}
