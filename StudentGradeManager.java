import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class StudentGradeManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // --- Initial Setup ---
        System.out.println("Each subject has a maximum of 100 marks.");
        System.out.print("Enter the Number of Subjects: ");
        int numSubjects = sc.nextInt();
        sc.nextLine(); // Consume the newline

        String[] subjectNames = new String[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter the Name of Subject " + (i + 1) + ": ");
            subjectNames[i] = sc.nextLine();
        }

        System.out.print("\nEnter the Number of Students: ");
        int numStudents = sc.nextInt();
        sc.nextLine(); // Consume the leftover newline character

        // --- Data Storage Arrays ---
        String[] studentNames = new String[numStudents];
        double[] studentAverages = new double[numStudents];
        int[] studentTotals = new int[numStudents];
        String[] studentGrades = new String[numStudents];
        int[][] marks = new int[numStudents][numSubjects];

        // --- Data Input and Calculation ---
        for (int i = 0; i < numStudents; i++) {
            System.out.print("\nEnter the Name of Student " + (i + 1) + ": ");
            studentNames[i] = sc.nextLine();

            int sum = 0;
            for (int j = 0; j < numSubjects; j++) {
                System.out.print("Enter marks for " + subjectNames[j] + " for " + studentNames[i] + ": ");
                marks[i][j] = sc.nextInt();
                sum += marks[i][j];
            }
            sc.nextLine(); // Consume the newline

            studentTotals[i] = sum;
            studentAverages[i] = (double) sum / numSubjects;
            studentGrades[i] = calculateGrade(studentAverages[i]);
        }

        // --- Build the Report String ---
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("--- Grade Summary Report ---\n");
        for (int i = 0; i < numStudents; i++) {
            reportBuilder.append("\nStudent " + (i + 1) + " is: " + studentNames[i] + "\n");
            reportBuilder.append("Total Marks: " + studentTotals[i] + "\n");
            reportBuilder.append(String.format("Average Marks: %.2f\n", studentAverages[i]));
            reportBuilder.append("Grade: " + studentGrades[i] + "\n");
        }

        // --- Subject-wise Analysis ---
        reportBuilder.append("\n--- Subject-wise Analysis ---\n");
        for (int j = 0; j < numSubjects; j++) {
            int highestMark = -1, lowestMark = 101;
            String topStudent = "", bottomStudent = "";

            for (int i = 0; i < numStudents; i++) {
                if (marks[i][j] > highestMark) {
                    highestMark = marks[i][j];
                    topStudent = studentNames[i];
                }
                if (marks[i][j] < lowestMark) {
                    lowestMark = marks[i][j];
                    bottomStudent = studentNames[i];
                }
            }
            reportBuilder.append("\nSubject: " + subjectNames[j] + "\n");
            reportBuilder.append("Highest Marks: " + highestMark + " by " + topStudent + "\n");
            reportBuilder.append("Lowest Marks: " + lowestMark + " by " + bottomStudent + "\n");
        }
        reportBuilder.append("----------------------------\n");

        // Print the full report to the console
        System.out.println("\n" + reportBuilder.toString());

        // --- Save the Report to a Hardcoded File Path ---
        String filePath = "F:\\Code Alpha\\Task-1\\output.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(reportBuilder.toString());
            System.out.println("Report successfully saved to " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }

        sc.close();
    }

    static String calculateGrade(double average) {
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        return "F";
    }
}
