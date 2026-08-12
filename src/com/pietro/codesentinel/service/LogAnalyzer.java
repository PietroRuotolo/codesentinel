package com.pietro.codesentinel.service;

import com.pietro.codesentinel.model.LogEntry;
import com.pietro.codesentinel.model.LogTypes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LogAnalyzer {

    private final List<LogEntry> logEntryList = new ArrayList<>();

    public void saveLog(LogEntry entry){
        logEntryList.add(entry);
    }

    private Map<String, Long> groupExceptions(List<LogEntry> errorList){
         return errorList.stream()
                 .map(LogEntry::getExceptionType)
                 .flatMap(Optional::stream)
                 .collect(Collectors.groupingBy(type -> type, Collectors.counting()));
    }

    public void filterByError(BufferedReader br) throws IOException {
        String line;
        LogEntry entry;
        int badFormatCount = 0;
        while ((line = br.readLine()) != null){
            Optional<LogEntry> optionalEntry = LogEntry.from(line);
            if(optionalEntry.isPresent()){
                entry = optionalEntry.get();
                saveLog(entry);
            }else{
                System.err.println("Bad log format");
                badFormatCount++;
            }
        }

        List<LogEntry> errorList = logEntryList.stream().filter(LogEntry::isError)
                .toList();
        Map<String, Long> exceptions = groupExceptions(errorList);
        printLog(errorList, exceptions ,LogTypes.ERRO);
        printLogInfo(errorList.size(), logEntryList.size(), badFormatCount);
        exportToCsv(exceptions);
    }

    public void printLog(List<LogEntry> logEntryList, Map<String, Long> logMap ,LogTypes header){

        System.out.println("-".repeat(30));
        System.out.println("LOG LIST");
        System.out.println("TYPE: " + "[" + header + "]");
        System.out.println("-".repeat(30));
        logEntryList.forEach(System.out::println);
        System.out.println("-".repeat(30));

        System.out.println("More Details: ");
        System.out.println("-".repeat(30));
        logMap.forEach((k,v) -> System.out.println(k + " : " + v));
        System.out.println("-".repeat(30));
    }

    public void printLogInfo(long errorCounter, int lineCounter, int badFormatCount){
        System.out.printf("It was found a total of %d errors in %d lines.%nA total of %d lines could not been read.%n",
                errorCounter, lineCounter, badFormatCount);
    }

    public void exportToCsv(Map<String, Long> logMap){
        try(FileWriter fw = new FileWriter("logdata.csv");
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
