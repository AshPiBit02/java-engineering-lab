package com.tutorial2;

import com.tutorial2.entities.Product;
import com.tutorial2.service.ProductService;
public class App 
{
    public static void main( String[] args )
    {
        ProductService productService=new ProductService();
        Product product1=new Product();
        product1.setName("Laptop");
        product1.setPrice(269999);
        product1.setQuantity(1);
        product1.setDescription("14-inch mini led, 16GB unified RAM,512GB SSD,M4(10-CPU Core,10-GUP Core,16-NPU Core");
        product1.setCategory("Electronics");

        productService.saveProduct(product1);
    }
}
