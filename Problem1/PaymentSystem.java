import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentSystem {
    interface PaymentMethod {
        BigDecimal adjustedAmount(BigDecimal amount);
    }

    static class CardPayment implements PaymentMethod {
        public BigDecimal adjustedAmount(BigDecimal amount) {
            return amount.multiply(new BigDecimal("1.02"));
        }
    }

    static class WalletPayment implements PaymentMethod {
        public BigDecimal adjustedAmount(BigDecimal amount) {
            return amount.multiply(new BigDecimal("1.01"));
        }
    }

    static class BankTransferPayment implements PaymentMethod {
        public BigDecimal adjustedAmount(BigDecimal amount) {
            return amount;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(input.readLine().trim());
        Map<String, PaymentMethod> methods = new HashMap<>();
        methods.put("CARD", new CardPayment());
        methods.put("WALLET", new WalletPayment());
        methods.put("BANKTRANSFER", new BankTransferPayment());

        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < count; i++) {
            String[] fields = input.readLine().trim().split("\\s+");
            String type = fields[0].toUpperCase(Locale.ROOT);
            BigDecimal amount = new BigDecimal(fields[1]);
            PaymentMethod method = methods.get(type);
            BigDecimal adjusted = method.adjustedAmount(amount).setScale(2, RoundingMode.HALF_UP);
            total = total.add(adjusted);
            System.out.printf(Locale.US, "%s: %.2f%n", type, adjusted);
        }
        System.out.printf(Locale.US, "Total: %.2f%n", total.setScale(2, RoundingMode.HALF_UP));
    }
}
