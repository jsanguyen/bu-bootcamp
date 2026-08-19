import java.io.*;
import java.util.ArrayList;

public class GradeAnalyzer {

    static int invalidLines = 0;   // counted in readScores, shown in the report

public static void main(String[] args) {
    // args.length is 0 when no filename was supplied
    if (args.length < 1) {
        System.out.println("Usage: java GradeAnalyzer <scores-file>");
        System.out.println("Example: java GradeAnalyzer my_scores.txt");
        return;
    }

    String inputFile = args[0];

    ArrayList<Integer> scores = readScores(inputFile);

    if (scores.isEmpty()) {
        System.out.println("No valid scores found in " + inputFile + ". Nothing to report.");
        return;
    }

    double avg = calculateAverage(scores);

    // Step 5: highest and lowest
    int high = Integer.MIN_VALUE;
    int low  = Integer.MAX_VALUE;
    for (int score : scores) {
        if (score > high) high = score;
        if (score < low)  low  = score;
    }

    writeReport(scores, avg, high, low, "report.txt");
}
    // Returns a list of valid scores read from the file
    public static ArrayList<Integer> readScores(String filename) {
        ArrayList<Integer> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    scores.add(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    System.out.println("Warning: skipping invalid score \"" + line + "\"");
                    invalidLines++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return scores;
    }

    // Returns the average of a list of scores, or 0.0 if the list is empty
    public static double calculateAverage(ArrayList<Integer> scores) {
        if (scores.isEmpty()) return 0.0;

        double total = 0.0;
        for (int score : scores) {
            total += score;
        }
        return total / scores.size();
    }

    // Writes and prints the report
    public static void writeReport(ArrayList<Integer> scores,
                                   double avg, int high, int low,
                                   String outputFile) {

        // Step 6: count the grade bands
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;
        for (int score : scores) {
            if      (score >= 90) countA++;
            else if (score >= 80) countB++;
            else if (score >= 70) countC++;
            else if (score >= 60) countD++;
            else                  countF++;
        }

        // Build the report once, then print it and save it
        String report = String.format(
                "=== Grade Analysis Report ===%n" +
                "Total scores processed: %3d%n" +
                "Invalid lines skipped:  %3d%n" +
                "%n" +
                "Average score: %6.2f%n" +
                "Highest score: %6d%n" +
                "Lowest score:  %6d%n" +
                "%n" +
                "Grade distribution:%n" +
                "  A (90-100):   %2d%n" +
                "  B (80-89):    %2d%n" +
                "  C (70-79):    %2d%n" +
                "  D (60-69):    %2d%n" +
                "  F (below 60): %2d%n",
                scores.size(), invalidLines,
                avg, high, low,
                countA, countB, countC, countD, countF);

        System.out.print(report);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(report);
            System.out.println("\nReport saved to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }
}