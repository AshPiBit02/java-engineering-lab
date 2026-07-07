package com.tutorial3.service;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.tutorial3.entities.*;
import com.tutorial3.util.HibernateUtil;
public class Service {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveDepartment(Department department){
        try(Session session=sessionFactory.openSession()){
            Transaction tx=session.beginTransaction();
            try{
                session.persist(department);
                tx.commit();
                System.out.println("New Department added successfully.");
            }catch (Exception e){
                tx.rollback();
                System.out.println("Error adding new Department!");
                throw e;
            }
        }
    }

    public void saveEmployee(Employee employee){
        try(Session session=sessionFactory.openSession()){
            Transaction tx=session.beginTransaction();
            try{
                session.persist(employee);
                tx.commit();
                System.out.println("New Employee added successfully.");
            }catch (Exception e){
                tx.rollback();
                System.out.println("Error adding new Employee!");
                throw e;
            }
        }
    }

    public Employee getEmployeeById(long id){
        try(Session session=sessionFactory.openSession()){
            return session.find(Employee.class,id);
        }
    }


}
