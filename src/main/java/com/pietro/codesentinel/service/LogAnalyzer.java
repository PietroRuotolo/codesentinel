package com.pietro.codesentinel.service;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.model.LogTypes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogAnalyzer {

    private final List<LogEntry> logEntryList = new ArrayList<>();

    public Map<String, Long> analyzeFile(String path){
        Resource resource = new ClassPathResource(path);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while((line = br.readLine()) != null){
                Optional<LogEntry> optionalLogEntry = LogEntry.from(line);
                optionalLogEntry.ifPresent(this::saveLog);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<LogEntry> errorList = logEntryList.stream()
                .filter(LogEntry::isError)
                .toList();
        return groupExceptions(errorList);
    }

    public void saveLog(LogEntry entry){
        logEntryList.add(entry);
    }

    private Map<String, Long> groupExceptions(List<LogEntry> errorList){
         return errorList.stream()
                 .map(LogEntry::getExceptionType)
                 .flatMap(Optional::stream)
                 .collect(Collectors.groupingBy(type -> type, Collectors.counting()));
    }

    public void exportToCsv(Map<String, Long> logMap){
        try(FileWriter fw = new FileWriter("analise/logdata.csv");
            BufferedWriter bw = new BufferedWriter(fw)){
                bw.write("exception_type,count");
                bw.newLine();
                for(var entry : logMap.entrySet()){
                    bw.write(entry.getKey() + "," + entry.getValue());
                    bw.newLine();
                }
            System.out.println("Map saved with success!");
        }catch (IOException e){
            System.err.println("Geral error in the file: " + e.getMessage());
        }
    }
}
