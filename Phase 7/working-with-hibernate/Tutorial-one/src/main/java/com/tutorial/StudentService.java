package com.tutorial;

import java.util.List;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.tutorial.entities.Student;
import com.tutorial.util.HibernateUtil;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

    // Get all student using hql
    public List<Student> getAllStudentsHQL() {
        try (Session session = sessionFactory.openSession()) {
            String getHQL = "FROM Student";
            Query<Student> query = session.createQuery(getHQL, Student.class);
            return query.list();
        }
    }

    // Get student by name
    public Student gerStudentByNameHQL(String name) {
        try (Session session = sessionFactory.openSession()) {
            String getByNameHql = "FROM Student WHERE name = :studentName";
            Query<Student> query = session.createQuery(getByNameHql, Student.class);
            query.setParameter("studentName", name);
            return query.uniqueResult();

        }
    }

    // Criteria API
    public List<Student> getStudentsByCollegeCriteria(String college) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root).where(criteriaBuilder.equal(root.get("college"), college));
            Query<Student> query2 = session.createQuery(query);
            return query2.getResultList();
        }
    }

}
