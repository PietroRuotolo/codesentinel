import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    private final static List<LogEntry> logEntryList = new ArrayList<>();

    public static void main(String[] args){
        try(BufferedReader br = new BufferedReader(new FileReader("./logs.txt"))) {
            filterByError(br);
        }catch (IOException e){
            System.out.println("Ocorreu um erro: " + e.getMessage());
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

        printLog(errorList, LogTypes.ERRO);
        printLogInfo(errorList.size(), logEntryList.size(), badFormatCount);
    }

    public static void printLog(List<LogEntry> logEntryList, LogTypes header){

        System.out.println("-".repeat(10));
        System.out.println("LISTA DE LOGS");
        System.out.println("TIPO: " + "[" + header + "]");
        System.out.println("-".repeat(10));
        logEntryList.forEach(System.out::println);
    }

    public static void saveLog(LogEntry entry){
        logEntryList.add(entry);
    }

    public static void printLogInfo(long errorCounter, int lineCounter, int badFormatCount){
        System.out.printf("Foram indentificados um total de %d erros em %d linhas.%nUm total de %d linhas não foram possíveis de serem lidas.%n",
                errorCounter, lineCounter, badFormatCount);
    }
}

