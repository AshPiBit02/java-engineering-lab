package com.ecommerceApp.order;
import com.ecommerceApp.product.Product;
import com.ecommerceApp.customer.Customer;
public class Order {
    private int orderId;
    private Product product;
    private Customer customer;

    public Order(int orderId,Product product,Customer customer){
        this.orderId=orderId;
        this.product=product;
        this.customer=customer;
    }

    public int getOrderId(){
        return orderId;
    }
    public Customer getCustomer(){
        return customer;
    }
    public Product getProduct(){
        return product;
    }
    public void showOrder(){
        System.out.println("Order ID: "+orderId);
        System.out.println("Customer: "+customer.getName());
        System.out.println("Product: "+product.getName());
    }


}
