package com.pietro.codesentinel.controller;

import com.pietro.codesentinel.service.LogAnalyzer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LogController {
    private final LogAnalyzer logAnalyzer;

    public LogController(LogAnalyzer logAnalyzer){
        this.logAnalyzer = logAnalyzer;
    }

    @GetMapping("/analyzes")
    public Map<String, Long> getLogs(){
        return logAnalyzer.analyzeErrors("logs.txt");
    }

    @PostMapping("/logs")
    public Map<String, Long> postLogs(@RequestBody String logData){
        return logAnalyzer.analyzeErrors(logData);
    }
}
