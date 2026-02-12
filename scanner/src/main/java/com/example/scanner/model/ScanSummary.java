package com.example.scanner.model;

import java.util.List;

public class ScanSummary {

    private int totalFilesScanned;
    private int totalIssuesFound;
    private int highRiskCount;
    private int mediumRiskCount;
    private int lowRiskCount;
    private long scanTimeMs;
    private List<String> report;

    public ScanSummary(int totalFilesScanned,
                       int totalIssuesFound,
                       int highRiskCount,
                       int mediumRiskCount,
                       int lowRiskCount,
                       long scanTimeMs,
                       List<String> report) {

        this.totalFilesScanned = totalFilesScanned;
        this.totalIssuesFound = totalIssuesFound;
        this.highRiskCount = highRiskCount;
        this.mediumRiskCount = mediumRiskCount;
        this.lowRiskCount = lowRiskCount;
        this.scanTimeMs = scanTimeMs;
        this.report = report;
    }

    public int getTotalFilesScanned() { return totalFilesScanned; }
    public int getTotalIssuesFound() { return totalIssuesFound; }
    public int getHighRiskCount() { return highRiskCount; }
    public int getMediumRiskCount() { return mediumRiskCount; }
    public int getLowRiskCount() { return lowRiskCount; }
    public long getScanTimeMs() { return scanTimeMs; }
    public List<String> getReport() { return report; }
}
