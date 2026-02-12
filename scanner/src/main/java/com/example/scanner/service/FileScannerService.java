package com.example.scanner.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

@Service

public class FileScannerService {

    // -------- REGEX PATTERNS --------

    private final Pattern emailPattern =
            Pattern.compile("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");

    private final Pattern creditCardPattern =
            Pattern.compile("\\b\\d{16}\\b");

    private final Pattern scriptPattern =
            Pattern.compile("(?i)<script.*?>");

    private final Pattern sqlPattern =
            Pattern.compile("(?i)drop\\s+table");


    // -------- SCAN DIRECTORY --------

    public List<String> scanDirectory(String folderPath) {

        List<String> report = new ArrayList<>();

        try {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toFile().isHidden())
                    .filter(this::isTextFile)
                    .filter(this::isSmallEnough)
                    .forEach(path -> scanFile(path, report));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return report;
    }


    // -------- SCAN SINGLE FILE --------

    private void scanFile(Path path, List<String> report) {

        try {
            List<String> lines = Files.readAllLines(path);
            int lineNumber = 0;

            for (String line : lines) {
                lineNumber++;

                checkPattern(line, emailPattern, "Email", "Low", path, lineNumber, report);
                checkPattern(line, creditCardPattern, "Credit Card", "High", path, lineNumber, report);
                checkPattern(line, scriptPattern, "XSS Script", "Medium", path, lineNumber, report);
                checkPattern(line, sqlPattern, "SQL Injection", "High", path, lineNumber, report);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // -------- PATTERN CHECK --------

    private void checkPattern(String line,
                              Pattern pattern,
                              String type,
                              String risk,
                              Path path,
                              int lineNumber,
                              List<String> report) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {

            String entry = "File: " + path.toString()
                    + " | Pattern: " + type
                    + " | Risk: " + risk
                    + " | Line: " + lineNumber;

            report.add(entry);
        }
    }


    // -------- FILE TYPE FILTER --------

    private boolean isTextFile(Path path) {

        String name = path.toString().toLowerCase();

        return name.endsWith(".txt")
                || name.endsWith(".java")
                || name.endsWith(".html")
                || name.endsWith(".js")
                || name.endsWith(".css");
    }


    // -------- FILE SIZE LIMIT (5MB) --------

    private boolean isSmallEnough(Path path) {

        try {
            return Files.size(path) < 5 * 1024 * 1024;
        } catch (IOException e) {
            return false;
        }
    }


    // -------- DELETE FILE --------

    public boolean deleteFile(String filePath) {

        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            return false;
        }
    }


    // -------- EXPORT LOG --------

    public String exportLog(List<String> report, String outputPath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            writer.write("Security Scan Report");
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();

            for (String entry : report) {
                writer.write(entry);
                writer.newLine();
            }

            writer.write("----------------------");
            writer.newLine();
            writer.write("Total Issues Found: " + report.size());

            return "Log exported successfully.";

        } catch (IOException e) {
            return "Error exporting log.";
        }
    }
}
