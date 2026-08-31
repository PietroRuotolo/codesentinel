package com.pietro.codesentinel.service;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.repository.LogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogQueryService {

    private final LogEntryRepository logEntryRepository;

    public LogQueryService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    public List<LogEntry> getLogs(){
        return logEntryRepository.findAll();
    }
}
