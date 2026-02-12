package com.example.scanner.service;

import com.example.scanner.model.ScanResult;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class FileScannerService {

    private final Pattern emailPattern =
            Pattern.compile("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");

    private final Pattern creditCardPattern =
            Pattern.compile("\\b\\d{16}\\b");

    private final Pattern sqlPattern =
            Pattern.compile("(?i)drop\\s+table");

    private final Pattern scriptPattern =
            Pattern.compile("(?i)<script.*?>");

    public List<ScanResult> scanDirectory(String folderPath, String sortOrder) {

        List<ScanResult> results = new ArrayList<>();

        try {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> !p.toFile().isHidden())
                    .filter(this::isTextFile)
                    .forEach(path -> scanFile(path, results));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if ("asc".equalsIgnoreCase(sortOrder)) {
            results.sort(Comparator.comparingLong(ScanResult::getFileSize));
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            results.sort((a, b) -> Long.compare(b.getFileSize(), a.getFileSize()));
        }

        return results;
    }

    private void scanFile(Path path, List<ScanResult> results) {

        try {
            List<String> lines = Files.readAllLines(path);
            long size = Files.size(path);

            int lineNumber = 0;
            for (String line : lines) {
                lineNumber++;

                if (emailPattern.matcher(line).find()) {
                    results.add(new ScanResult(
                            path.toString(),
                            "Email (PII)",
                            "Low",
                            lineNumber,
                            size
                    ));
                }

                if (creditCardPattern.matcher(line).find()) {
                    results.add(new ScanResult(
                            path.toString(),
                            "Credit Card (PII)",
                            "High",
                            lineNumber,
                            size
                    ));
                }

                if (sqlPattern.matcher(line).find()) {
                    results.add(new ScanResult(
                            path.toString(),
                            "SQL Injection",
                            "High",
                            lineNumber,
                            size
                    ));
                }

                if (scriptPattern.matcher(line).find()) {
                    results.add(new ScanResult(
                            path.toString(),
                            "XSS Script",
                            "Medium",
                            lineNumber,
                            size
                    ));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isTextFile(Path path) {
        String name = path.toString().toLowerCase();
        return name.endsWith(".txt") ||
                name.endsWith(".java") ||
                name.endsWith(".html") ||
                name.endsWith(".js") ||
                name.endsWith(".css");
    }

    public boolean deleteFile(String path) {
        try {
            return Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            return false;
        }
    }

    public String exportLog(List<ScanResult> results, String exportPath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportPath))) {

            writer.write("Secure File Scanner Report\n");
            writer.write("Generated at: " + new Date() + "\n\n");

            for (ScanResult r : results) {
                writer.write(
                        r.getFilePath() + " | " +
                                r.getPatternType() + " | " +
                                r.getRiskLevel() + " | Line " +
                                r.getLineNumber() + " | Size " +
                                r.getFileSize() + " bytes\n"
                );
            }

            return "Report exported successfully";

        } catch (IOException e) {
            return "Error exporting report";
        }
    }
}
