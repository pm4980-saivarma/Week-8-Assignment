import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DeliveryFeeCalculator {
    interface Delivery {
        BigDecimal fee(BigDecimal weight, BigDecimal distance, BigDecimal customsFee);
    }

    static class StandardDelivery implements Delivery {
        public BigDecimal fee(BigDecimal weight, BigDecimal distance, BigDecimal customsFee) {
            return new BigDecimal("5")
                    .add(weight.multiply(new BigDecimal("0.50")))
                    .add(distance.multiply(new BigDecimal("0.10")));
        }
    }

    static class ExpressDelivery implements Delivery {
        public BigDecimal fee(BigDecimal weight, BigDecimal distance, BigDecimal customsFee) {
            return new BigDecimal("15")
                    .add(weight)
                    .add(distance.multiply(new BigDecimal("0.20")));
        }
    }

    static class InternationalDelivery implements Delivery {
        public BigDecimal fee(BigDecimal weight, BigDecimal distance, BigDecimal customsFee) {
            return new BigDecimal("25")
                    .add(weight.multiply(new BigDecimal("2")))
                    .add(distance.multiply(new BigDecimal("0.50")))
                    .add(customsFee);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(input.readLine().trim());
        Map<String, Delivery> deliveryTypes = new HashMap<>();
        deliveryTypes.put("STANDARD", new StandardDelivery());
        deliveryTypes.put("EXPRESS", new ExpressDelivery());
        deliveryTypes.put("INTERNATIONAL", new InternationalDelivery());

        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < count; i++) {
            String[] fields = input.readLine().trim().split("\\s+");
            String type = fields[0].toUpperCase(Locale.ROOT);
            BigDecimal weight = new BigDecimal(fields[1]);
            BigDecimal distance = new BigDecimal(fields[2]);
            BigDecimal customsFee = fields.length > 3 ? new BigDecimal(fields[3]) : BigDecimal.ZERO;
            BigDecimal fee = deliveryTypes.get(type).fee(weight, distance, customsFee);
            total = total.add(fee);
            System.out.printf(Locale.US, "%s: %.2f%n", type, fee);
        }
        System.out.printf(Locale.US, "Total: %.2f%n", total);
    }
}
