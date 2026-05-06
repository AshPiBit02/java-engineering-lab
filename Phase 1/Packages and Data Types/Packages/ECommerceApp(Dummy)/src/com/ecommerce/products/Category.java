package com.ecommerce.products;

public class Category {
    private int id;
    private String title;

    public Category(int id,String title){
        this.id=id;
        this.title=title;
    }
    
    @Override
    public String toString(){
        return "Product Title: "+title+"(ID: "+id+")";
    }

    
}
