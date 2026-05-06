
import com.ecommerceApp.customer.Customer;
import com.ecommerceApp.order.Order;
import com.ecommerceApp.product.Product;
public class Main {
    public static void main(String[] args){
        Customer cus1=new Customer(1362,"Heisenberg");
        Product prod1=new Product(2008,"Methamphetamine",70652.5f);
        Order ord1=new Order(1051, prod1, cus1);
        ord1.showOrder();
        System.out.println("Price: $"+prod1.getPrice());
    }
}
