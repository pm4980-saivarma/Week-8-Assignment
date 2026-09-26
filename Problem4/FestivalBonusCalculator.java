import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class FestivalBonusCalculator {
    private static final Map<String, Function<Scanner, Employee>> EMPLOYEE_TYPES = Map.of(
            "FULLTIME", input -> new FullTimeEmployee(input.next(), input.nextBigDecimal()),
            "PARTTIME", input -> new PartTimeEmployee(input.next(), input.nextBigDecimal()),
            "INTERN", input -> new Intern(input.next(), input.nextBigDecimal())
    );

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int count = input.nextInt();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < count; i++) {
            Employee employee = EMPLOYEE_TYPES.get(input.next()).apply(input);
            BigDecimal bonus = employee.bonus();
            System.out.printf(Locale.US, "%s: %.2f%n", employee.name(), bonus);
            total = total.add(bonus);
        }

        System.out.printf(Locale.US, "Total Bonus: %.2f%n", total);
    }

    private abstract static class Employee {
        private final String name;
        private final BigDecimal salary;

        private Employee(String name, BigDecimal salary) {
            this.name = name;
            this.salary = salary;
        }

        String name() {
            return name;
        }

        protected BigDecimal salary() {
            return salary;
        }

        abstract BigDecimal bonus();

        protected BigDecimal rounded(BigDecimal value) {
            return value.setScale(2, RoundingMode.HALF_UP);
        }
    }

    private static class FullTimeEmployee extends Employee {
        private FullTimeEmployee(String name, BigDecimal salary) {
            super(name, salary);
        }

        @Override
        BigDecimal bonus() {
            return rounded(salary().multiply(new BigDecimal("0.10")));
        }
    }

    private static class PartTimeEmployee extends Employee {
        private PartTimeEmployee(String name, BigDecimal salary) {
            super(name, salary);
        }

        @Override
        BigDecimal bonus() {
            return rounded(salary().multiply(new BigDecimal("0.05")));
        }
    }

    private static class Intern extends Employee {
        private Intern(String name, BigDecimal salary) {
            super(name, salary);
        }

        @Override
        BigDecimal bonus() {
            return new BigDecimal("2000.00");
        }
    }
}
