package com.tutorial2.service;

import com.tutorial2.entities.Product;
import com.tutorial2.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ProductService {
    private SessionFactory sessionFactory= HibernateUtil.getSessionFactory();

    public void saveProduct(Product product){
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=session.beginTransaction();
            try{
                session.persist(product);
                transaction.commit();
            }catch (Exception e){
                transaction.rollback();
                throw e;
            }
        }
    }

//    public Student getById(long id){
//        try(Session session=sessionFactory.openSession()){
//            try{
//
//            }
//        }
//    }
}
