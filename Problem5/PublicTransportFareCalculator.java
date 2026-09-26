import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PublicTransportFareCalculator {
    interface Transport {
        BigDecimal fare(BigDecimal distance, BigDecimal peakHourFactor);
    }

    static class Bus implements Transport {
        public BigDecimal fare(BigDecimal distance, BigDecimal peakHourFactor) {
            BigDecimal calculated = new BigDecimal("2")
                    .add(distance.multiply(new BigDecimal("0.10")));
            return calculated.min(new BigDecimal("10"));
        }
    }

    static class Train implements Transport {
        public BigDecimal fare(BigDecimal distance, BigDecimal peakHourFactor) {
            return new BigDecimal("3").add(distance.multiply(new BigDecimal("0.15")));
        }
    }

    static class Metro implements Transport {
        public BigDecimal fare(BigDecimal distance, BigDecimal peakHourFactor) {
            return new BigDecimal("1.50")
                    .add(distance.multiply(new BigDecimal("0.20")))
                    .multiply(peakHourFactor);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(input.readLine().trim());
        Map<String, Transport> transportTypes = new HashMap<>();
        transportTypes.put("BUS", new Bus());
        transportTypes.put("TRAIN", new Train());
        transportTypes.put("METRO", new Metro());

        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < count; i++) {
            String[] fields = input.readLine().trim().split("\\s+");
            String type = fields[0].toUpperCase(Locale.ROOT);
            BigDecimal distance = new BigDecimal(fields[1]);
            BigDecimal peakHourFactor = fields.length > 2
                    ? new BigDecimal(fields[2]) : BigDecimal.ONE;
            BigDecimal fare = transportTypes.get(type).fare(distance, peakHourFactor)
                    .setScale(2, RoundingMode.HALF_UP);
            total = total.add(fare);
            System.out.printf(Locale.US, "%s: %.2f%n", type, fare);
        }
        System.out.printf(Locale.US, "Total: %.2f%n", total);
    }
}
