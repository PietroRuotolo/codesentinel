package com.pietro.codesentinel.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class LogEntry {

    @Id @GeneratedValue() @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LogType level;

    private String message;

    private OffsetDateTime logDate;

    public LogEntry() {}

    public LogEntry(LogType level, String message, OffsetDateTime logDate) {
        this.level = level;
        this.message = message;
        this.logDate = logDate;
    }

    public static Optional<LogEntry> from(String line){
        String regex =
                "^(?:\\[)?([^\\]]+)(?:\\])?\\s+" +
                "(?:\\[)?(INFO|DEBUG|ERROR|WARN)(?:\\])?\\s+" +
                "(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if(matcher.matches()){
            String timestamp = matcher.group(1);
            Optional<LogType> level = LogType.parse(matcher.group(2));
            String message = matcher.group(3);

            if(level.isPresent()){
                OffsetDateTime formattedDate;
                try{
                    formattedDate = OffsetDateTime.parse(timestamp);
                    return Optional.of(new LogEntry(level.get(), message, formattedDate));
                }catch (DateTimeParseException e){
                    System.err.println("Invalid date format: " + line);
                }
            }
        }
        System.err.println("Invalid log Format from: " + line);
        return Optional.empty();
    }

    public Optional<String> getExceptionType(){
        if(!isError()) return Optional.empty();

        if(message.contains(":")){
            String exceptionLine = message.substring(0, message.indexOf(":"));
            if(exceptionLine.contains(".")){
                return Optional.of(exceptionLine.substring(exceptionLine.lastIndexOf(".") + 1));
            }
        }
        System.err.println("Cannot catch exception from: " + message);
        return Optional.empty();
    }


    public boolean isError() {
        return LogType.ERROR.equals(level);
    }

    @Override
    public String toString() {
        return "[%s] %s %s".formatted(level, logDate ,message);
    }

    public Long getId() {
        return id;
    }

    public LogType getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getLogDate() {
        return logDate;
    }
}
