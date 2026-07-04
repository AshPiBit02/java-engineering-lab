package com.tutorial2;

import com.tutorial2.entities.Product;
import com.tutorial2.service.ProductService;
import java.util.List;
public class App 
{
    static ProductService productService;
    public static void main( String[] args )
    {
        productService=new ProductService();
//        addProduct("MacBook Pro M4",269990,1,"16GB Unified RAM,512GB SSD,Chip M4(10-CPU Core, 10-GPU Core,16-NPU Core)","Electronics");
//        addProduct("Chips",80,23,"Lays Family Pack","Fast Food");
//        addProduct("Galaxy S26 Ultra",204990,2,"12GB RAM, 512GB Storage, SAmoled display","Electronics");
        getProductById(3);
        showProductData(productService.getAllProducts());
    }

    private static void addProduct(String name,double price,int quantity,String description,String category){
        Product product=new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setCategory(category);
        productService.saveProduct(product);
    }

    private static void getProductById(long id){
        Product product= productService.getById(id);
        if(product!=null){
            System.out.printf("%-10s %-22s %-14s %-10s %-16s %-29s%n","ProductID","ProductName","Price","Quantity","Category","Description");
            System.out.println("-".repeat(115));
            String des=product.getDescription();
            String shordes=des.length()<30?des.substring(0,27)+"...":des;
            System.out.printf("%-8d | %-20s | $%-10.2f |  %-8d | %-13s |  %-30s%n",product.getProductId(),product.getName(),product.getPrice(),product.getQuantity(),product.getCategory(),shordes);
        }else{
            System.out.println("Product not found!");
        }
    }



    private static void showProductData(List<Product> products){
        System.out.printf("%-10s %-22s %-14s %-10s %-16s %-29s%n","ProductID","ProductName","Price","Quantity","Category","Description");
        System.out.println("-".repeat(115));
        for(Product p: products){
            String desc=p.getDescription();
            String shortdesc=desc.length()>30?desc.substring(0,27)+"...":desc;
            System.out.printf("%-8d | %-20s | $%-10.2f |  %-8d | %-13s |  %-30s%n",p.getProductId(),p.getName(),p.getPrice(),p.getQuantity(),p.getCategory(),shortdesc);
        }
    }
}
