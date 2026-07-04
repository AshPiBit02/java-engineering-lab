package com.tutorial2.service;

import com.tutorial2.entities.Product;
import com.tutorial2.util.HibernateUtil;
import org.hibernate.FetchNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.NotFound;
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

    public void updateProductField(long id,String fieldName,Object value) {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                try {
                    Product product = session.find(Product.class, id);
                    if (product == null) {
                        throw new IllegalArgumentException("Product not found with id: " + id);
                    } else {
                        switch (fieldName) {
                            case "name" -> product.setName((String) value);
                            case "price" -> product.setPrice((double) value);
                            case "quantity" -> product.setQuantity((Integer) value);
                            case "description" -> product.setDescription((String) value);
                            case "category" -> product.setCategory((String) value);
                            default -> throw new IllegalArgumentException("Unknown Field: "+fieldName);
                        }
                    }
                    tx.commit();
                    System.out.println("Updated successfully");
                }catch (IllegalArgumentException e){
                    System.out.println("Invalid Field! "+e.getMessage());
                }
                catch (Exception e) {
                    tx.rollback();
                    System.out.println("Product Not Found!");
                    throw e;
                }
            }
    }

    public void deleteProductById(long id){
        try(Session session=sessionFactory.openSession()){
            Transaction tx=session.beginTransaction();
            try{
                Product product=session.find(Product.class,id);
                if(product==null){
                    throw new IllegalArgumentException("Product not found with id: "+id);
                }else{
                    session.remove(product);
                }
                tx.commit();
                System.out.println("Product removed from record with ID: "+id);
            }catch (IllegalArgumentException e){
                tx.rollback();
                System.out.println("Delete failed: "+e.getMessage());
            }catch(Exception e){
                tx.rollback();
                System.out.println("Error:  "+e.getMessage());
            }
        }
    }

    public List<Product> findByCategory(String category){
        try(Session session=sessionFactory.openSession()){
            String hql="FROM Product WHERE lOWER(category) =LOWER(:cat) ";
            Query<Product>query=session.createQuery(hql,Product.class).setParameter("cat",category);
            return query.list();
        }
    }

    public List<Product> findByPriceRange(double min,double max){
        try(Session session=sessionFactory.openSession()){
            String hql="FROM Product WHERE price BETWEEN :min AND :max";
            Query<Product>query= session.createQuery(hql,Product.class).setParameter("min",min).setParameter("max",max);
            return query.list();
        }
    }

    public List<Product> findLowStock(int threshold){
        try(Session session=sessionFactory.openSession()){
            String hql="FROM Product WHERE quantity<:threshold";
            Query<Product>query= session.createQuery(hql,Product.class).setParameter("threshold",threshold);
            return query.list();
        }
    }

    public List<Product> searchByName(String keyword){
        try(Session session=sessionFactory.openSession()){
            String hql="FROM Product WHERE LOWER(name) LIKE LOWER(:keyword)";
            Query<Product>query=session.createQuery(hql,Product.class).setParameter("keyword","%"+keyword+"%");
            return query.list();
        }
    }
}
