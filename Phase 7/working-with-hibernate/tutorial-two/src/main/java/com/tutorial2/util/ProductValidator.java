package com.tutorial2.util;
import com.tutorial2.entities.Product;
public class ProductValidator {
    public static void validate(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty!");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative!");
        }
    }

    public static void validateFieldValue(String fieldName,Object value){
        switch (fieldName){
            case "name" -> {
                if(value==null || ((String)value).trim().isEmpty()){
                    throw  new IllegalArgumentException("Product name cannot be empty");
                }
            }
            case "price" -> {
                if((double)value <0){
                    throw new IllegalArgumentException("Price cannot  be negative");
                }
            }
            case "quantity" -> {
                if((Integer)value<0){
                    throw new IllegalArgumentException("Quantity cannot be negative");
                }
            }

        }
    }
}
