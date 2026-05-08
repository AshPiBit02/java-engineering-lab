interface Payment{
    void pay();
}

class  CreditCard implements Payment{
    @Override
    public void pay(){
        System.out.println("Paid with Credit Card.");
    }
    void rewardPoints(){
        System.out.println("Reward point: +1");
    }
}

public class ecommerce {
    public static void main(String[] args){
        Payment p=new CreditCard();
        p.pay();
        // credit.rewardPoints(); not allowed

        CreditCard c = (CreditCard) p; //Downcasting
        c.rewardPoints();
    }
    
}
