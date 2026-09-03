package com.pietro.codesentinel.specification;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.model.LogType;
import jakarta.persistence.criteria.ParameterExpression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

    public static Specification<LogEntry> dateAfter(LocalDate date){
        return (root, query, criteriaBuilder) -> {
            if(date == null) return null;
            OffsetDateTime begin = date.atStartOfDay().atOffset(ZoneOffset.of("-03:00"));
            return criteriaBuilder.greaterThanOrEqualTo(root.get("logDate"), begin);
        };
    }

    public static Specification<LogEntry> dateBefore(LocalDate date){
        return (root, query, criteriaBuilder) -> {
            if(date == null) return null;
            OffsetDateTime end = date.atTime(23, 59, 59).atOffset(ZoneOffset.of("-03:00"));
            return criteriaBuilder.lessThanOrEqualTo(root.get("logDate"), end);
        };
    }
}
