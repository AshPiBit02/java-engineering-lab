
public class product {
    //Attributes
    int product_id;
    String Product_name;
    int quantity;
    float rate;

    // Parameterized Constructor
    product(int product_id,String Product_name,int quantity,float rate){
        this.product_id=product_id;
        this.Product_name=Product_name;
        this.quantity=quantity;
        this.rate=rate;
    }

    // Methods
    public void cost(String product){
        float total_cost=(quantity*rate);
        System.out.println("Total cost for " + product +" : $"+total_cost);
    }
    public void details(){
        System.out.println("Product id: "+ product_id +" | " + "Product Name: " + Product_name + " | " + "Quantity: " + " | " + " Rate: " + rate  );
    }

    //Objects
    public static void main(String[] args){
        product p1=new product(101,"RAM",12,4851.542f);
        product p2=new product(103,"SSD",5,5682.12f);

        p1.details();
        p1.cost(p1.Product_name);

        p2.details();
        p2.cost(p2.Product_name);
        

    }


    
}
