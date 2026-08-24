class A {
    protected void showMessage() {
        System.out.println("This message is from class A");
    }

    protected void showMessage(String msg) {
        System.out.println("This message is from class A. Actual Message: " + msg);
    }
}

class B extends A {
    @Override
    protected void showMessage() {
        System.out.println("This message is from class B");
    }
}

public class Polymorphism {
    public static void main(String[] args) {
        B a = new B();
        a.showMessage("Tomorrow is my exam");
    }
}
