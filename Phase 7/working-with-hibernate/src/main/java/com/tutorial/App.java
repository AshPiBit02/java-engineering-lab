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
        student.setName("Jon Snow");
        student.setCollege("TUE");
        student.setContact("98X245XXX");
        student.setEmail("jonsnownorth@gmail.com");
        student.setAbout("King in the north");

        StudentService stuService = new StudentService();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            stuService.saveStudent(student);
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
