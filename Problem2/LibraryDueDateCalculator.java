import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryDueDateCalculator {
    private static final LocalDate CURRENT_DATE = LocalDate.of(2023, 10, 26);
    private static final Pattern TOKEN = Pattern.compile("\"([^\"]*)\"|(\\S+)");

    interface BorrowableItem {
        String title();
        LocalDate dueDate();
    }

    abstract static class LibraryItem implements BorrowableItem {
        private final String title;

        LibraryItem(String title) {
            this.title = title;
        }

        abstract int borrowingDays();

        public final String title() {
            return title;
        }

        public final LocalDate dueDate() {
            return CURRENT_DATE.plusDays(borrowingDays());
        }
    }

    static class Book extends LibraryItem {
        Book(String title) { super(title); }
        int borrowingDays() { return 14; }
    }

    static class DVD extends LibraryItem {
        DVD(String title) { super(title); }
        int borrowingDays() { return 7; }
    }

    static class Magazine extends LibraryItem {
        Magazine(String title) { super(title); }
        int borrowingDays() { return 3; }
    }

    private static List<String> tokens(String line) {
        List<String> result = new ArrayList<>();
        Matcher matcher = TOKEN.matcher(line);
        while (matcher.find()) {
            result.add(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(input.readLine().trim());
        Map<String, Function<String, BorrowableItem>> itemTypes = new HashMap<>();
        itemTypes.put("BOOK", Book::new);
        itemTypes.put("DVD", DVD::new);
        itemTypes.put("MAGAZINE", Magazine::new);

        for (int i = 0; i < count; i++) {
            List<String> fields = tokens(input.readLine());
            String type = fields.get(0).toUpperCase(Locale.ROOT);
            String title = String.join(" ", fields.subList(1, fields.size()));
            BorrowableItem item = itemTypes.get(type).apply(title);
            System.out.println(item.title() + ": " + item.dueDate());
        }
    }
}
