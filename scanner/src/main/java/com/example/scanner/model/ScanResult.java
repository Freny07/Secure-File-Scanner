package com.example.scanner.model;

public class ScanResult {

    private String filePath;
    private String patternType;
    private String riskLevel;
    private int lineNumber;

    public ScanResult(String filePath, String patternType,
                      String riskLevel, int lineNumber) {
        this.filePath = filePath;
        this.patternType = patternType;
        this.riskLevel = riskLevel;
        this.lineNumber = lineNumber;
    }

    public String getFilePath() { return filePath; }
    public String getPatternType() { return patternType; }
    public String getRiskLevel() { return riskLevel; }
    public int getLineNumber() { return lineNumber; }
}
