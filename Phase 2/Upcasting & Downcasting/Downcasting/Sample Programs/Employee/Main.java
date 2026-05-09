class Employee {
    String name;
    double salary;

    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    void work() {
        System.out.println(name + " is working.");
    }

    double getSalary() {
        return salary;
    }
}

class Manager extends Employee {
    Manager(String name, double salary) {
        super(name, salary); // init Manager with employees data
    }

    double bonus() {
        return salary * 0.10; // manager gets specific bonus
    }
}

public class Main {
    public static void main(String[] args) {
        Employee[] staff = {
                new Employee("Alice Border", 1850),
                new Manager("Adobe Hitter", 1942),
                new Employee("Charlie Stark", 1961),
                new Manager("Diana Rider", 2015)
        };

        double totalSalary = 0;
        double totalBonus = 0;

        for (Employee e : staff) {
            e.work();
            totalSalary += e.getSalary();

            // Downcasting check
            if (e instanceof Manager) {
                Manager m = (Manager) e;
                double b = m.bonus();
                totalBonus += b;
                System.out.println(m.name + " gets bonus: $" + b);
            }
        }
        System.out.println("Total Salary: $" + Math.round(totalSalary * 100.0) / 100.0);
        System.out.println("Total Bonus: $" + Math.round(totalBonus * 100.0) / 100.0);
        System.out.println("Grand Total: $" + Math.round((totalBonus + totalSalary) * 100.0) / 100.0);

    }

}
