package com.pietro.codesentinel.specification;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.model.LogType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LogEntrySpecification{

    public static Specification<LogEntry> hasLevel(LogType level){
        return (root, query, criteriaBuilder) -> {
            if(level == null) return null;
            return criteriaBuilder.equal(root.get("level"), level);
        };
    }

    public static Specification<LogEntry> containsMessage(String message){
        return (root, query, criteriaBuilder) -> {
            if(message == null) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("message")),
                    "%" + message.toLowerCase() + "%"
            );
        };
    }

    public static Specification<LogEntry> hasDate(OffsetDateTime logDate){
        return (root, query, criteriaBuilder) -> {
            if(logDate == null) return null;
            return criteriaBuilder.equal(root.get("logDate"), logDate);
        };
    }
}
