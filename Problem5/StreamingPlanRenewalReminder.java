import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class StreamingPlanRenewalReminder {
    private static final Map<String, Function<Scanner, Subscriber>> PLAN_TYPES = Map.of(
            "BASIC", input -> new BasicSubscriber(input.next(), LocalDate.parse(input.next())),
            "STANDARD", input -> new StandardSubscriber(input.next(), LocalDate.parse(input.next())),
            "PREMIUM", input -> new PremiumSubscriber(input.next(), LocalDate.parse(input.next()))
    );

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int count = input.nextInt();

        for (int i = 0; i < count; i++) {
            Subscriber subscriber = PLAN_TYPES.get(input.next()).apply(input);
            System.out.printf(Locale.US, "%s: %s%n", subscriber.name(), subscriber.renewalDate());
        }
    }

    private abstract static class Subscriber {
        private final String name;
        private final LocalDate startDate;

        private Subscriber(String name, LocalDate startDate) {
            this.name = name;
            this.startDate = startDate;
        }

        String name() {
            return name;
        }

        protected LocalDate startDate() {
            return startDate;
        }

        abstract LocalDate renewalDate();
    }

    private static class BasicSubscriber extends Subscriber {
        private BasicSubscriber(String name, LocalDate startDate) {
            super(name, startDate);
        }

        @Override
        LocalDate renewalDate() {
            return startDate().plusDays(30);
        }
    }

    private static class StandardSubscriber extends Subscriber {
        private StandardSubscriber(String name, LocalDate startDate) {
            super(name, startDate);
        }

        @Override
        LocalDate renewalDate() {
            return startDate().plusDays(90);
        }
    }

    private static class PremiumSubscriber extends Subscriber {
        private PremiumSubscriber(String name, LocalDate startDate) {
            super(name, startDate);
        }

        @Override
        LocalDate renewalDate() {
            return startDate().plusDays(365);
        }
    }
}
