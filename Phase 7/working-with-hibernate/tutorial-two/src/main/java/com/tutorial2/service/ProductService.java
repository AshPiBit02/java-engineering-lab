package com.tutorial2.service;

import com.tutorial2.entities.Product;
import com.tutorial2.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.*;

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

    public Product getById(long productId){
        try(Session session=sessionFactory.openSession()){
            return session.find(Product.class,productId);
        }
    }

    public List<Product> getAllProducts(){
        try(Session session=sessionFactory.openSession()){
            String hql="FROM Product";
            Query<Product> query=session.createQuery(hql, Product.class);
            return query.list();
        }
    }
}
