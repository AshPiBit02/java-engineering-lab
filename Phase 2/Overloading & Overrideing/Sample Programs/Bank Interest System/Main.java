class Bank {
    double interestRate() {
        return 5.0;
    }
}

class NBL extends Bank {
    @Override
    double interestRate() {
        return 6.5;
    }

    double interestRate(double principle, double year) {
        double rate = interestRate();
        return (principle * rate * year) / 100;
    }
}

public class Main {
    public static void main(String[] args) {
        Bank b = new Bank();
        NBL nbl = new NBL();

        // overriding in action
        System.out.println("Bank base rate:" + b.interestRate());
        System.out.println("NBL base rate:" + nbl.interestRate());

        // Overloading in action
        double totalInterest = nbl.interestRate(10000, 2);
        System.out.println("NBL Interest for 2 years on $10,000: $" + totalInterest);

    }

}
