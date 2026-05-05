public class Product {
    String productId,productName,Category;
    int quantitiy;
    float rate;

    //parameterized constructor
    Product(String productId,String productName, String Category,int quantitiy,float rate){ // In Java, we cannot assign default values to constructor parameters like C++
        this.productId=productId;
        this.productName=productName;
        this.Category=Category;
        this.quantitiy=quantitiy;
        this.rate=rate;
    }

    void purchaseDetails(){
        float total_cost=quantitiy*rate;
        System.out.printf("Purchase Details:\nProduct Id: %s\nProduct Name: %s\nCategory: %s\nQuantity: %d\nRate: $%.2f/item\nTotal Cost: $%.2f",productId,productName,Category,quantitiy,rate,total_cost);
    }

    public static void main(String[] args){
        Product prod1=new Product("RF12J4","Electric Stove","Kitchen",7,42.68f);
        prod1.purchaseDetails();
    }
    
}
