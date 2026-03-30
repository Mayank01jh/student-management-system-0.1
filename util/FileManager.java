package util;

import model.Student;
import module.GradeBook;
import module.StudentRegistry;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_PATH = "students.csv";
    private static final String HEADER    = "rollNo,name,gpa,course";

    // ── Save all students to CSV ──────────────────────────
    public static void save(StudentRegistry registry) {
        List<Student> all = registry.getAllStudents();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write(HEADER);
            bw.newLine();

            for (Student s : all) {
                String line = String.join(",",
                        String.valueOf(s.getRollNo()),
                        escapeCsv(s.getName()),
                        String.valueOf(s.getGpa()),
                        escapeCsv(s.getCourse())
                );
                bw.write(line);
                bw.newLine();
            }

            System.out.println("✓ Saved " + all.size() + " student(s) to " + FILE_PATH);

        } catch (IOException e) {
            System.out.println("✗ Failed to save: " + e.getMessage());
        }
    }

    // ── Load students from CSV ────────────────────────────
    public static void load(StudentRegistry registry, GradeBook gradeBook) {
        Path path = Paths.get(FILE_PATH);

        if (!Files.exists(path)) {
            System.out.println("⚠ No save file found (" + FILE_PATH + "). Starting fresh.");
            return;
        }

        int loaded = 0, skipped = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = parseCsvLine(line);
                if (parts.length < 4) { skipped++; continue; }

                try {
                    int    rollNo = Integer.parseInt(parts[0].trim());
                    String name   = parts[1].trim();
                    double gpa    = Double.parseDouble(parts[2].trim());
                    String course = parts[3].trim();

                    registry.addStudent(rollNo, name, gpa, course);
                    Student s = registry.findStudent(rollNo);
                    if (s != null) gradeBook.register(s);
                    loaded++;

                } catch (NumberFormatException e) {
                    skipped++;
                }
            }

            System.out.println("✓ Loaded " + loaded + " student(s) from " + FILE_PATH
                    + (skipped > 0 ? " (" + skipped + " skipped)" : ""));

        } catch (IOException e) {
            System.out.println("✗ Failed to load: " + e.getMessage());
        }
    }

    // ── CSV helpers ───────────────────────────────────────

    private static String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString());
        return fields.toArray(new String[0]);
    }
}
