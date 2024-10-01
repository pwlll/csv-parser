import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Participant> parList=new ArrayList<>();
        try(Scanner scanner=new Scanner(new FileInputStream("./src/main/java/5TR.csv"), StandardCharsets.UTF_16)){
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

        for(Participant par:parList){
            par.showParticipant();
        }
        sortList(parList);
        System.out.println("\n");
        for(Participant par:parList){
            par.showParticipant();
        }
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

    private static List<Participant> sortList(List<Participant> list){
        List<Integer> tempTime;
        Scanner scn;
        int totalTime;
        for(int i=0;i<list.size();i++){
            tempTime=new ArrayList<>();
            scn=new Scanner(list.get(i).parTime);
            scn.useDelimiter(" ");
            totalTime=0;
            while(scn.hasNext()){
                try{
                    tempTime.add(Integer.parseInt(scn.next()));
                }
                catch (NumberFormatException exception){}
            }
            switch(tempTime.size()){
                case 1:
                    totalTime+=tempTime.get(0);
                    break;
                case 2:
                    totalTime+=tempTime.get(1);
                    totalTime+=tempTime.get(0)*60;
                    break;
                case 3:
                    totalTime+=tempTime.get(2);
                    totalTime+=tempTime.get(1)*60;
                    totalTime+=tempTime.get(0)*3600;
                    break;
            }
            list.get(i).parTime=String.valueOf(totalTime);
        }
        Collections.sort(list,new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                Participant par1=(Participant) o1;
                Participant par2=(Participant) o2;
                return Integer.parseInt(par1.parTime)-Integer.parseInt(par2.parTime);
            }
        });
        int hours,minutes,seconds;
        String str;
        for(int i=0;i<list.size();i++){
            totalTime=Integer.parseInt(list.get(i).parTime);
            hours = totalTime/ 3600;
            minutes = (totalTime% 3600) / 60;
            seconds = totalTime% 60;
            str= String.format("%d godz. %d min %d s", hours, minutes, seconds);
            list.get(i).parTime=str;
        }
        return list;
    }
}