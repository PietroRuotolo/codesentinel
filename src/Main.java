import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class Main {
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
        int errorCounter = 0;
        int lineCounter = 0;
        while ((line = br.readLine()) != null){
            Optional<LogEntry> optionalEntry = LogEntry.from(line);
            if(optionalEntry.isPresent()){
                entry = optionalEntry.get();
                if(entry.isError()){
                    System.out.println(entry);
                    errorCounter++;
                }
            }else{
                System.err.println("* Bad Log Format *");
            }
            lineCounter++;
        }
        printErrorLog(errorCounter, lineCounter);
    }

    public static void printErrorLog(int errorCounter, int lineCounter){
        System.out.println("Foram indentificados um total de " + errorCounter + " erros em " + lineCounter + " linhas.");
    }
}

