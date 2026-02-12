package com.example.scanner.controller;

import com.example.scanner.model.ScanResult;
import com.example.scanner.service.FileScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scanner")
public class ScannerController {

    @Autowired
    private FileScannerService scannerService;

    @GetMapping("/scan")
    public List<ScanResult> scan(
            @RequestParam String path,
            @RequestParam(defaultValue = "none") String sort) {

        return scannerService.scanDirectory(path, sort);
    }

    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam String path) {
        boolean deleted = scannerService.deleteFile(path);
        return deleted ? "File deleted" : "Unable to delete file";
    }

    @PostMapping("/export")
    public String export(
            @RequestParam String folder,
            @RequestParam String exportPath,
            @RequestParam(defaultValue = "none") String sort) {

        List<ScanResult> results =
                scannerService.scanDirectory(folder, sort);

        return scannerService.exportLog(results, exportPath);
    }
}
