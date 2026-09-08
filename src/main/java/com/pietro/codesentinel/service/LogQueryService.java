package com.pietro.codesentinel.service;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.model.LogType;
import com.pietro.codesentinel.repository.LogEntryRepository;
import com.pietro.codesentinel.specification.LogEntrySpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LogQueryService {

    private final LogEntryRepository logEntryRepository;

    public LogQueryService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    public List<LogEntry> getLogs(LogType level, String message, OffsetDateTime logDate, OffsetDateTime dateAfter, OffsetDateTime dateBefore){

        Specification<LogEntry> spec = Specification.allOf(
                LogEntrySpecification.hasLevel(level),
                LogEntrySpecification.containsMessage(message),
                LogEntrySpecification.dateAfter(dateAfter),
                LogEntrySpecification.dateBefore(dateBefore));

        return logEntryRepository.findAll(spec, Sort.by("logDate").descending());
    }
}
