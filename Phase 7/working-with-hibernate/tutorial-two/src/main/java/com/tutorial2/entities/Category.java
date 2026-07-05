package com.tutorial2.entities;

import jakarta.persistence.*;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private long categoryId;

    @Column(name="category_name",length = 100,nullable = false,unique = true)
    private String name;

    public long getCategoryId(){
        return categoryId;
    }
    public void setCategoryId(long id){
        categoryId=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

}
