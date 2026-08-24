class KiddoException extends Exception {
    public KiddoException(String message) {
        super(message);
    }
}

public class VotingDemo {
    static void checkEligibility(int age) throws KiddoException {
        if (age < 18) {
            throw new KiddoException("Go Home kiddo and 18 then come!");
        }
        System.out.println("You can vote!");
    }

    public static void main(String[] args) {
        try {
            VotingDemo.checkEligibility(23);
            VotingDemo.checkEligibility(k);
            VotingDemo.checkEligibility(16);
        } catch (KiddoException e) {
            System.out.println("Kiddo Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
