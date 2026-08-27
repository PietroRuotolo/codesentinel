package com.pietro.codesentinel.repository;

import com.pietro.codesentinel.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

    boolean existsByLogDateAndMessage(OffsetDateTime logDate, String message);
}
