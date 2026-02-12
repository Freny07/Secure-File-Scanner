package com.example.scanner.controller;

import com.example.scanner.service.FileScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scanner")
public class ScannerController {

    @Autowired
    private FileScannerService scannerService;

    // 1️⃣ Scan a folder
    @GetMapping("/scan")
    public List<String> scan(@RequestParam String path) {
        return scannerService.scanDirectory(path);
    }

    // 2️⃣ Delete a file
    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam String path) {
        boolean deleted = scannerService.deleteFile(path);
        return deleted
                ? "File deleted: " + path
                : "File not found or cannot delete: " + path;
    }

    // 3️⃣ Export log
    @PostMapping("/export")
    public String exportLog(@RequestParam String folder,
                            @RequestParam String exportPath) {

        List<String> infected = scannerService.scanDirectory(folder);
        return scannerService.exportLog(infected, exportPath);
    }
}
