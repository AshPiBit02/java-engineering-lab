package com.tutorial;

import org.hibernate.*;
import com.tutorial.entities.Student;
import com.tutorial.util.HibernateUtil;

public class StudentService {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction beginTransaction = session.beginTransaction();
            session.persist(student);
            beginTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Student getById(long studentId) {
        try (Session session = sessionFactory.openSession()) {
            Student student = session.find(Student.class, studentId);
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student updateStudent(long studentId, Student student) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Student oldStudent = session.find(Student.class, studentId);
            if (oldStudent != null) {
                oldStudent.setName(student.getName());
                oldStudent.setEmail(student.getEmail());
                oldStudent.setCollege(student.getCollege());
                oldStudent.setActive(true);
                oldStudent = session.merge(oldStudent);
            }
            transaction.commit();
            return oldStudent;
        }
    }

    public void deleteStudent(long studentId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = session.find(Student.class, studentId);
            if (student != null) {
                session.remove(student);
            }
            transaction.commit();
        }
    }
}
