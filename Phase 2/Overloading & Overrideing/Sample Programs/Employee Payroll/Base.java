class Employee {
    int empId;
    String empName;
    double workedhour;
    double rate; // per hour rate

    Employee(int empId, String empName, double workedhour, double rate) {
        this.empId = empId;
        this.empName = empName;
        this.workedhour = workedhour;
        this.rate = rate;
    }

    double calculateSalary() {
        return workedhour * rate;
    }
}

class Manager extends Employee {
    Manager(int empId, String empName, double workedhour, double rate) {
        super(empId, empName, workedhour, rate);
    }

    @Override
    double calculateSalary() {
        return (workedhour * rate) + (workedhour * rate * 0.10); // 10% bonus
    }

    double calculateSalary(double bonusRate) {
        return (workedhour * rate) + (workedhour * rate * (bonusRate / 100)); // 10% bonus
    }
}

public class Base {
    public static void main(String[] args) {
        Employee e1 = new Employee(121, "Tony Walk", 19.5, 12.63);
        Manager m1 = new Manager(103, "Steve Hank", 15.6, 16.2);

        // Overriding in action
        System.out.println(
                e1.empName + "(" + e1.empId + ") Salary: $" + Math.round(e1.calculateSalary() * 100.0) / 100.0);
        System.out.println(
                m1.empName + "(" + m1.empId + ") Salary: $" + Math.round(m1.calculateSalary() * 100.0) / 100.0);

        // Overriding in action
        System.out.println(
                m1.empName + "(" + m1.empId + ") Salary: $" + Math.round(m1.calculateSalary() * 100.0) / 100.0); // default
                                                                                                                 // 10%
                                                                                                                 // rate
        System.out.println(
                m1.empName + "(" + m1.empId + ") Salary: $" + Math.round(m1.calculateSalary(20.5) * 100.0) / 100.0); // default
                                                                                                                     // custom
                                                                                                                     // rate

    }
}
