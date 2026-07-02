package com.tutorial;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tutorial.entities.Student;
import com.tutorial.util.HibernateUtil;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Student student = new Student();
        student.setName("Juj Khan");
        student.setCollege("PEC");
        student.setContact("98XXXXXXXX");
        student.setEmail("jujkhan@123");
        student.setAbout("His name is mr.Khan from khansar");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // session.persist(student);
            transaction.commit();

            System.out.println("Student saved successfully");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }
}
