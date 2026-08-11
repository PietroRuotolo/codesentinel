import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final static List<LogEntry> logEntryList = new ArrayList<>();
    private static Map<String, Long> exceptionMap;

    public static void main(String[] args){
        try(BufferedReader br = new BufferedReader(new FileReader("./logs.txt"))) {
            filterByError(br);
        }catch (IOException e){
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void filterByError(BufferedReader br) throws IOException {
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

        groupException();
        printLog(errorList, LogTypes.ERRO);
        printLogInfo(errorList.size(), logEntryList.size(), badFormatCount);
    }

    private static void groupException(){
        exceptionMap = logEntryList.stream()
                .collect(Collectors.groupingBy(entry -> entry.getExceptionType().orElse("Not a valid exception"), Collectors.counting()));
    }

    public static void printLog(List<LogEntry> logEntryList, LogTypes header){

        System.out.println("-".repeat(30));
        System.out.println("LOG LIST");
        System.out.println("TYPE: " + "[" + header + "]");
        System.out.println("-".repeat(30));
        logEntryList.forEach(System.out::println);
        System.out.println("-".repeat(30));

        System.out.println("More Details: ");
        System.out.println("-".repeat(30));
        exceptionMap.forEach((k,v) -> System.out.println(k + " : " + v));
        System.out.println("-".repeat(30));

    }

    public static void saveLog(LogEntry entry){
        logEntryList.add(entry);
    }

    public static void printLogInfo(long errorCounter, int lineCounter, int badFormatCount){
        System.out.printf("It was found a total of %d errors in %d lines.%nA total of %d lines could not been read.%n",
                errorCounter, lineCounter, badFormatCount);
    }
}

