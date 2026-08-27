package com.pietro.codesentinel.service;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.repository.LogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogAnalyzer {

    private final LogEntryRepository logEntryRepository;

    public LogAnalyzer(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    private List<LogEntry> processLogs(String text){
        List<LogEntry> formatedLogs = text.lines()
                .map(LogEntry::from)
                .flatMap(Optional::stream)
                .toList();
        List<LogEntry> newLogs = formatedLogs.stream()
                        .filter(l -> !logEntryRepository.existsByLogDateAndMessage(l.getLogDate(), l.getMessage()))
                        .toList();
        logEntryRepository.saveAll(newLogs);
        return formatedLogs;
    }

    public Map<String, Long> analyzeErrors(String text){
        List<LogEntry> errorList = processLogs(text).stream()
                .filter(LogEntry::isError)
                .toList();
        return groupExceptions(errorList);
    }

    private Map<String, Long> groupExceptions(List<LogEntry> errorList){
         return errorList.stream()
                 .map(LogEntry::getExceptionType)
                 .flatMap(Optional::stream)
                 .collect(Collectors.groupingBy(type -> type, Collectors.counting()));
    }
}
