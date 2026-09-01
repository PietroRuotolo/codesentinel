package com.pietro.codesentinel.controller;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.model.LogType;
import com.pietro.codesentinel.service.LogAnalyzer;
import com.pietro.codesentinel.service.LogQueryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
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
    public ResponseEntity<List<LogEntry>> getLogs(
            @RequestParam(required = false) LogType level,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime logDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime after,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime before){

        return ResponseEntity.ok(logQueryService.getLogs(level, message, logDate, after, before));

    }

    @PostMapping("/logs")
    public Map<String, Long> analyzeLogs(@RequestBody String logData){return logAnalyzer.analyzeErrors(logData);}
}
