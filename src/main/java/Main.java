import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Participant> parList=new ArrayList<>();
        try(Scanner scanner=new Scanner(new FileInputStream("./src/main/java/5TR.csv"), StandardCharsets.UTF_16)){
            System.out.println(scanner.hasNext());
            boolean flag=false;
            String tempLine;
            while(scanner.hasNextLine()){
                tempLine= scanner.nextLine();
                if(tempLine.equals("3. Działania podczas spotkania")){
                    flag=false;
                }
                if(flag && !tempLine.isBlank()) parList.add(getValues(tempLine));

                if(tempLine.equals("2. Uczestnicy")){
                    flag=true;
                    scanner.nextLine();
                }

            }
        }
        catch (FileNotFoundException exception){
            System.out.println("File not found");
        }
        saveToJson(parList);
    }
    private static Participant getValues(String line){
        Participant participant=new Participant();
        try (Scanner scanner=new Scanner(line)){
            scanner.useDelimiter("\\t");
            participant=new Participant(
                    scanner.next(),
                    scanner.next(),
                    scanner.next(),
                    scanner.next(),
                    scanner.next(),
                    scanner.next(),
                    scanner.next()
            );
        }
        participant.showParticipant();
        return participant;
    }
    private static void saveToJson(List<Participant> list){
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("target/5tr.json"), list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}