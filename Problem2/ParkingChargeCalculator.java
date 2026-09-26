import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class ParkingChargeCalculator {
    private static final Map<String, Function<Scanner, Vehicle>> VEHICLE_TYPES = Map.of(
            "BIKE", input -> new Bike(input.nextInt()),
            "CAR", input -> new Car(input.nextInt()),
            "TRUCK", input -> new Truck(input.nextInt())
    );

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int count = input.nextInt();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < count; i++) {
            Vehicle vehicle = VEHICLE_TYPES.get(input.next()).apply(input);
            BigDecimal charge = vehicle.charge();
            System.out.printf(Locale.US, "%s: %.2f%n", vehicle.vehicleType(), charge);
            total = total.add(charge);
        }

        System.out.printf(Locale.US, "Total: %.2f%n", total);
    }

    private abstract static class Vehicle {
        private final int hours;

        private Vehicle(int hours) {
            this.hours = hours;
        }

        protected int hours() {
            return hours;
        }

        abstract String vehicleType();

        abstract BigDecimal charge();
    }

    private static class Bike extends Vehicle {
        private Bike(int hours) {
            super(hours);
        }

        @Override
        String vehicleType() {
            return "BIKE";
        }

        @Override
        BigDecimal charge() {
            return BigDecimal.valueOf(hours() * 10L);
        }
    }

    private static class Car extends Vehicle {
        private Car(int hours) {
            super(hours);
        }

        @Override
        String vehicleType() {
            return "CAR";
        }

        @Override
        BigDecimal charge() {
            return BigDecimal.valueOf(30L + (hours() - 1L) * 20L);
        }
    }

    private static class Truck extends Vehicle {
        private Truck(int hours) {
            super(hours);
        }

        @Override
        String vehicleType() {
            return "TRUCK";
        }

        @Override
        BigDecimal charge() {
            return BigDecimal.valueOf(Math.max(100L, hours() * 50L));
        }
    }
}
