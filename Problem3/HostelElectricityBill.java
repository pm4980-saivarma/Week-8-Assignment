import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class HostelElectricityBill {
    private static final Map<String, Function<Scanner, Room>> ROOM_TYPES = Map.of(
            "SINGLE", input -> new SingleRoom(input.nextInt()),
            "SHARED", input -> new SharedRoom(input.nextInt(), input.nextInt()),
            "AC", input -> new AcRoom(input.nextInt())
    );

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int count = input.nextInt();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < count; i++) {
            Room room = ROOM_TYPES.get(input.next()).apply(input);
            BigDecimal bill = room.bill();
            System.out.printf(Locale.US, "%s: %.2f%n", room.roomType(), bill);
            total = total.add(bill);
        }

        System.out.printf(Locale.US, "Total: %.2f%n", total);
    }

    private abstract static class Room {
        private final int units;

        private Room(int units) {
            this.units = units;
        }

        protected int units() {
            return units;
        }

        abstract String roomType();

        abstract BigDecimal bill();
    }

    private static class SingleRoom extends Room {
        private SingleRoom(int units) {
            super(units);
        }

        @Override
        String roomType() {
            return "SINGLE";
        }

        @Override
        BigDecimal bill() {
            return BigDecimal.valueOf(units() * 8L);
        }
    }

    private static class SharedRoom extends Room {
        private final int occupants;

        private SharedRoom(int units, int occupants) {
            super(units);
            this.occupants = occupants;
        }

        @Override
        String roomType() {
            return "SHARED";
        }

        @Override
        BigDecimal bill() {
            return BigDecimal.valueOf(units() * 6L)
                    .divide(BigDecimal.valueOf(occupants), 2, RoundingMode.HALF_UP);
        }
    }

    private static class AcRoom extends Room {
        private AcRoom(int units) {
            super(units);
        }

        @Override
        String roomType() {
            return "AC";
        }

        @Override
        BigDecimal bill() {
            return BigDecimal.valueOf(units() * 10L + 200L);
        }
    }
}
