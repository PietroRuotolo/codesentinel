import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try(BufferedReader br = new BufferedReader(new FileReader("./logs.txt"))){
            String linha;
            while ((linha = br.readLine()) != null){
                System.out.println(linha);
            }
        }catch (IOException e){
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }
}