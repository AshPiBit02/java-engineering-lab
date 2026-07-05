package com.tutorial2.entities;

import com.tutorial2.entities.Category;
import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name="product_name",length = 150,nullable = false)
    private String name;

    @Column(name="price",nullable = false)
    private double price;

    @Column(name="quantity",nullable = false)
    private int quantity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id",nullable = false)
    private Category category;

    @Column(name="description")
    private String description;

    public long getProductId(){
        return productId;
    }
    public void setProductId(long productId){
        this.productId=productId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price=price;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }


    public Category getCategory(){
        return category;
    }
    public void setCategory(Category category){
        this.category=category;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
}
