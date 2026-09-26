import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class CanteenBillingCounter {
    private static final Map<String, Function<Scanner, Bill>> BILL_TYPES = Map.of(
            "STUDENT", input -> new StudentBill(input.nextBigDecimal()),
            "STAFF", input -> new StaffBill(input.nextBigDecimal()),
            "GUEST", input -> new GuestBill(input.nextBigDecimal())
    );

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int count = input.nextInt();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < count; i++) {
            Bill bill = BILL_TYPES.get(input.next()).apply(input);
            BigDecimal amount = bill.finalAmount();
            System.out.printf(Locale.US, "%s: %.2f%n", bill.customerType(), amount);
            total = total.add(amount);
        }

        System.out.printf(Locale.US, "Total: %.2f%n", total);
    }

    private abstract static class Bill {
        private final BigDecimal amount;

        private Bill(BigDecimal amount) {
            this.amount = amount;
        }

        protected BigDecimal amount() {
            return amount;
        }

        abstract String customerType();

        abstract BigDecimal finalAmount();

        protected BigDecimal rounded(BigDecimal value) {
            return value.setScale(2, RoundingMode.HALF_UP);
        }
    }

    private static class StudentBill extends Bill {
        private StudentBill(BigDecimal amount) {
            super(amount);
        }

        @Override
        String customerType() {
            return "STUDENT";
        }

        @Override
        BigDecimal finalAmount() {
            return rounded(amount().multiply(new BigDecimal("0.90")));
        }
    }

    private static class StaffBill extends Bill {
        private StaffBill(BigDecimal amount) {
            super(amount);
        }

        @Override
        String customerType() {
            return "STAFF";
        }

        @Override
        BigDecimal finalAmount() {
            return rounded(amount().multiply(new BigDecimal("0.95")));
        }
    }

    private static class GuestBill extends Bill {
        private GuestBill(BigDecimal amount) {
            super(amount);
        }

        @Override
        String customerType() {
            return "GUEST";
        }

        @Override
        BigDecimal finalAmount() {
            return rounded(amount().add(BigDecimal.TEN));
        }
    }
}
