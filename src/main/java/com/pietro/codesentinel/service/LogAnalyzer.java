package com.pietro.codesentinel.service;

import com.pietro.codesentinel.model.LogEntry;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogAnalyzer {

    public Map<String, Long> analyzeErrors(String text){
        List<LogEntry> errorList = text.lines()
                .map(LogEntry::from).flatMap(Optional::stream)
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
