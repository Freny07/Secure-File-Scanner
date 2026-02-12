package com.example.scanner.model;

public class ScanResult {

    private String filePath;
    private String patternType;
    private String riskLevel;
    private int lineNumber;
    private long fileSize;   // NEW

    public ScanResult(String filePath,
                      String patternType,
                      String riskLevel,
                      int lineNumber,
                      long fileSize) {

        this.filePath = filePath;
        this.patternType = patternType;
        this.riskLevel = riskLevel;
        this.lineNumber = lineNumber;
        this.fileSize = fileSize;
    }

    public String getFilePath() { return filePath; }
    public String getPatternType() { return patternType; }
    public String getRiskLevel() { return riskLevel; }
    public int getLineNumber() { return lineNumber; }
    public long getFileSize() { return fileSize; }
}
