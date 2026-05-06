package com.ecommerceApp.customer;

public class Customer {
    private int id;
    private String name;

    public Customer(int id,String name){
        this.id=id;
        this.name=name;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return "Customer Details:\nID: "+id+"\nName: "+name+"\n";
    }
    
}
