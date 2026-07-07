package com.tutorial3.service;
//import org.hibernate.query.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.tutorial3.entities.*;
import com.tutorial3.util.HibernateUtil;

import java.util.List;

public class Service {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveDepartment(Department department) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(department);
                tx.commit();
                System.out.println("New Department added successfully.");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error adding new Department!");
                throw e;
            }
        }
    }

    public void saveEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(employee);
                tx.commit();
                System.out.println("New Employee added successfully.");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error adding new Employee!");
                throw e;
            }
        }
    }

    public Department getDepartmentById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Department.class, id);
        }
    }

    public Employee getEmployeeById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Employee.class, id);
        }
    }

    public List<Employee> getAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    public void fetchAllEmployeeSalary(double salary) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("-".repeat(10) + "(CRITERIA)Employee with salary more than " + salary + "-".repeat(10));
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            cq.select(root).where(cb.gt(root.get("salary"), salary));
            List<Employee> results = session.createQuery(cq).list();
            if (results.isEmpty()) {
                System.out.println("No Employee with salary over $" + salary + " found!");
            } else {
                for (Employee e : results) {
                    System.out.println(e.getName() + " -> $" + e.getSalary());
                }
            }
        }


    }
}