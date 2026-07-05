package com.tutorial2;

import com.tutorial2.entities.Category;
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
//        addProduct("Scambler 400X",845990,3,"37.5Nm Torque,398.8cc Capacity, Scambler","Vehicle");
//        getProductById(3);
//        showProductData(productService.getAllProducts());
//        productService.updateProductField(2L,"caegoy","Junk Food");
//        productService.updateProductField(2L,"category","Fast Food");
//        productService.deleteProductById(6);
//        showProductData(productService.getAllProducts());
//        showProductByCategory(productService.findByCategory("Electronics"),"Electronics");
//        showProductByPriceRange(productService.findByPriceRange(30,30000000),30,30000000);
//        showLowStockProduct(productService.findLowStock(5));
//        SearchByName(productService.searchByName("c"));
//        System.out.println("Inventory total valuation: $"+productService.getTotalInventoryValue());
//        showCountByCategory(productService.countByCategory());
//        showMostExpensiveProduct(productService.getMostExpensiveProduct());
//        showProductsPaginated(productService.getProductPaginated(1,10));
        productService.bulkUpdatePriceByCategory("Electronics",10);
        productService.bulkUpdatePriceByCategory("Electronic",10);
    }


    private static void addProduct(String name, double price, int quantity, String description, Category category){
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
        System.out.printf("%-10s %-25s %-14s %-10s %-16s %-29s%n","ProductID","ProductName","Price","Quantity","Category","Description");
        System.out.println("-".repeat(115));
        for(Product p: products){
            String desc=p.getDescription();
            String shortdesc=desc.length()>30?desc.substring(0,27)+"...":desc;
            System.out.printf("%-8d | %-23s | $%-10.2f |  %-8d | %-13s |  %-30s%n",p.getProductId(),p.getName(),p.getPrice(),p.getQuantity(),p.getCategory().getName(),shortdesc);
        }
    }

    private static void showProductByCategory(List<Product> product,String cat){
        if(product.isEmpty()){
            System.out.println("No products found in this category!");
        }else{
            System.out.println("-".repeat(10)+"Products("+cat+")"+"-".repeat(10));
            product.forEach(p->System.out.printf("%-10s %s%n","",p.getName()));
        }
    }

    private static void showProductByPriceRange(List<Product> products,double min,double max){
        if(products.isEmpty()){
            System.out.println("No products found in that price("+min+","+max+") range!");
        }else{
            System.out.printf("%-2sProducts in Range(%.2f,%.2f)%-10s%n","",min,max,"");
            System.out.println("-".repeat(40));
            products.forEach(p->System.out.printf("%-7s %s%n","",p.getName()));
        }
    }

    private static void showLowStockProduct(List<Product> products){
        if(products.isEmpty()){
            System.out.println("No products found with low stock");
        }else{
            System.out.println("Products with low stock");
            System.out.println("-".repeat(20));
            products.forEach(p->System.out.printf("  %s(%d)%n",p.getName(),p.getQuantity()));
        }
    }

    private static void SearchByName(List<Product> products){
        if(products.isEmpty()){
            System.out.println("No product with such keyword found!");
        }else{
            System.out.println("    Products");
            System.out.println("-".repeat(18));
            products.forEach(p->System.out.printf("  %s%n",p.getName()));
        }
    }

    private static void showCountByCategory(List<Object[]> products){
        if(products.isEmpty()){
            System.out.println("Inventory is Empty!");
        }else{
            System.out.printf("%-5s %-20s| %-5s %s%n","","Category","","Count");
            System.out.println("-".repeat(45));
            products.forEach(p -> System.out.printf("%-5s %-20s| %-5s %d%n","",(String)p[0],"",(Long)p[1]));
        }
    }

    private static void showMostExpensiveProduct(Object[] obj){
        if(obj==null){
            System.out.println("Inventory is Empty!");
        }else{
            System.out.println("Most Expensive Product");
            System.out.println("-".repeat(40));
            System.out.printf(" product [%-15s] %n price: [$%.2f]",(String)obj[0],(Double)obj[1]);
        }
    }

    private static void showProductsPaginated(List<Product>products){
        if(products.isEmpty()){
            System.out.println("Inventory is Empty!");
        }else{
            System.out.printf("%s Paginated Products %s%n","".repeat(15),"".repeat(15));
            showProductData(products);
        }
    }
}
