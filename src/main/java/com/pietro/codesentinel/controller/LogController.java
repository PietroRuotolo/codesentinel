package com.pietro.codesentinel.controller;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.repository.LogEntryRepository;
import com.pietro.codesentinel.service.LogAnalyzer;
import com.pietro.codesentinel.service.LogQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LogController {

    private final LogAnalyzer logAnalyzer;
    private final LogQueryService logQueryService;

    public LogController(LogAnalyzer logAnalyzer, LogQueryService logQueryService)
    {
        this.logAnalyzer = logAnalyzer;
        this.logQueryService = logQueryService;

    }

    @GetMapping("/logs")
    public List<LogEntry> getLogs(){return logQueryService.getLogs();}

    @PostMapping("/logs")
    public Map<String, Long> analyzeLogs(@RequestBody String logData){return logAnalyzer.analyzeErrors(logData);}
}
