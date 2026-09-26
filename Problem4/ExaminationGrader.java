import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExaminationGrader {
    private static final Pattern TOKEN = Pattern.compile("\"([^\"]*)\"|(\\S+)");

    interface Question {
        double score();
    }

    static class ObjectiveQuestion implements Question {
        private final String correctAnswer;
        private final String studentAnswer;
        private final double points;

        ObjectiveQuestion(String correctAnswer, String studentAnswer, double points) {
            this.correctAnswer = correctAnswer;
            this.studentAnswer = studentAnswer;
            this.points = points;
        }

        public double score() {
            return correctAnswer.equalsIgnoreCase(studentAnswer) ? points : 0;
        }
    }

    static class EssayQuestion implements Question {
        private final String correctAnswer;
        private final String studentAnswer;
        private final double points;

        EssayQuestion(String correctAnswer, String studentAnswer, double points) {
            this.correctAnswer = correctAnswer;
            this.studentAnswer = studentAnswer;
            this.points = points;
        }

        public double score() {
            String answer = studentAnswer.toLowerCase(Locale.ROOT);
            int matches = 0;
            for (String keyword : correctAnswer.split(",")) {
                if (answer.contains(keyword.trim().toLowerCase(Locale.ROOT))) {
                    matches++;
                }
            }
            if (matches >= 2) {
                return points * 0.75;
            }
            return matches == 1 ? points * 0.50 : 0;
        }
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
        Map<String, QuestionFactory> questionTypes = new HashMap<>();
        questionTypes.put("MCQ", (fields) -> new ObjectiveQuestion(
                fields.get(2), fields.get(3), Double.parseDouble(fields.get(4))));
        questionTypes.put("TF", (fields) -> new ObjectiveQuestion(
                fields.get(2), fields.get(3), Double.parseDouble(fields.get(4))));
        questionTypes.put("ESSAY", (fields) -> new EssayQuestion(
                fields.get(2), fields.get(3), Double.parseDouble(fields.get(4))));

        double total = 0;
        for (int i = 0; i < count; i++) {
            List<String> fields = tokens(input.readLine());
            String type = fields.get(0).toUpperCase(Locale.ROOT);
            Question question = questionTypes.get(type).create(fields);
            double score = question.score();
            total += score;
            System.out.printf(Locale.US, "%s: %.2f%n", type, score);
        }
        System.out.printf(Locale.US, "Total Score: %.2f%n", total);
    }

    interface QuestionFactory {
        Question create(List<String> fields);
    }
}
