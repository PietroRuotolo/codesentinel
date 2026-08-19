package com.pietro.codesentinel.controller;

import com.pietro.codesentinel.service.LogAnalyzer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LogController {
    private final LogAnalyzer logAnalyzer;

    public LogController(LogAnalyzer logAnalyzer){
        this.logAnalyzer = logAnalyzer;
    }

    @GetMapping("/analyze")
    public Map<String, Long> getAnalysis(){
        return logAnalyzer.analyzeFile("logs.txt");
    }
}
