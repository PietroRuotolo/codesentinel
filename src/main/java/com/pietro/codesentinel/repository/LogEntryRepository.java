package com.pietro.codesentinel.repository;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.model.LogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long>, JpaSpecificationExecutor<LogEntry> {

    boolean existsByLogDateAndMessage(OffsetDateTime logDate, String message);

    List<LogEntry> findByLevel(LogType level);

}
