
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.*;


public class Main {

    public static Map<String, String> map = new LinkedHashMap<>();
    public static ArrayList<String> listForLog =new ArrayList<>();
    public static Map<String, Integer> mapAnswer = new HashMap<>();
    public static void addCard()
    {
        Scanner scanner = new Scanner(System.in);
        outputMsg("The card:");
        //  System.out.println("The card:");

        String term = scanner.nextLine();
        listForLog.add(term);
        if(!map.containsKey(term))
        {
            outputMsg("The definition of the card:");
            String definition = scanner.nextLine();

            if(!map.containsValue(definition))
            { listForLog.add(definition);
                map.put(term,definition);
                outputMsg("The pair ("+term+":"+definition+") has been added.");
            }
            else
            {
                outputMsg("The definition "+"\"" +  definition + "\"" +" already exists.");
            }
        }
        else
        {
            outputMsg("The card "+"\"" +  term + "\""+ " already exists.");
        }

    }
    public static void removeCard()
    {
        // Scanner scanner = new Scanner(System.in);
        outputMsg("The card:");
        String term = getUserInput();

        if(map.containsKey(term))
        {
            map.remove(term);
            mapAnswer.remove(term);
            outputMsg("The card has been removed.");

        }
        else
        {
            outputMsg("Can't remove "+"\"" +term + "\""+": there is no such card.");
        }
    }

    public static void importCard()
    {
        // Scanner scanner = new Scanner(System.in);
        outputMsg("File name:");
        String pathToFile=getUserInput();
        int count=0;
        int count1=0;
        File file=new File("./"+pathToFile);
        if (!file.isFile()) {
            outputMsg("File not found.");
        } else {
            try (Scanner scannerFromFile = new Scanner(file)) {
                while (scannerFromFile.hasNext()) {
                    count++;
                    String term = scannerFromFile.nextLine();
                    String definition = scannerFromFile.nextLine();
                    int countInFile=scannerFromFile.nextInt();
                    String a=scannerFromFile.nextLine();
                    if (map.containsKey(term)) {
                        map.remove(term);
                        if(mapAnswer.containsKey(term))
                        {
                            //   count1=mapAnswer.get(term);
                            mapAnswer.remove(term);
                        }
                    }
                    map.put(term, definition);
                    mapAnswer.put(term,countInFile);
                }
                outputMsg(count + " cards have been loaded.");
            } catch (FileNotFoundException e) {
                outputMsg("No file found: " + pathToFile);
            }

        }

    }
    public static void exportCard() throws IOException {

        //    Scanner scanner = new Scanner(System.in);
        outputMsg("File name:");
        String pathToFile=getUserInput();
        File file = new File(pathToFile);
        FileWriter writer = new FileWriter(file);

        for (Map.Entry<String, String> i:map.entrySet()
        ) {
            writer.write(i.getKey()+"\r\n");
            writer.write(i.getValue()+"\r\n");
            if(mapAnswer.containsKey(i.getKey()))
            {
                writer.write(Integer.toString(mapAnswer.get(i.getKey()))+"\r\n");
            }
            else{
                writer.write(Integer.toString(0)+"\r\n");
            }
        }
        writer.close();
        outputMsg(map.size()+" cards have been saved.");
    }
    public static void ask() {
        Scanner scanner = new Scanner(System.in);
        outputMsg("How many times to ask?");
        int count = scanner.nextInt();
        listForLog.add(Integer.toString(count));
        String answer = scanner.nextLine();
        Map.Entry<String, String>[] entries = map.entrySet().toArray(new Map.Entry[0]);
        Random rand = new Random();
        List<String> keys = new ArrayList<String>(map.keySet());
        while (count !=  0) {
            count--;
            String term = keys.get(rand.nextInt(keys.size()));
            outputMsg("Print the definition of " + "\"" + term + "\"");
            answer = scanner.nextLine();
            listForLog.add(answer);
            String definition = map.get(term);
            if (definition.toLowerCase().equals(answer.toLowerCase())) {
                outputMsg("Correct answer.");
            }  else if (map.containsValue(answer)) {
                for (Map.Entry<String, String> i:map.entrySet()
                ) {
                    if(i.getValue().equals(answer))
                    {
                        outputMsg("Wrong answer. The correct one is " + "\"" + definition + "\"" + ", you've just written the definition of " + "\"" + i.getKey() + "\"" + ".");
                        countError(term);
                        break;
                    }
                }
            } else if (!map.containsValue(answer)) {
                outputMsg("Wrong answer. The correct one is " + "\"" + map.get(term) + "\"" + ".");
                countError(term);
            }
        }
    }
    public static void outputMsg (String msg)
    {
        listForLog.add(msg);
        System.out.println(msg);
    }
    public static String getUserInput()
    {
        Scanner scanner = new Scanner(System.in);
        String msg=scanner.nextLine();
        listForLog.add(msg);
        return msg;
    }

    public static void  log() throws IOException {
        //   Scanner scanner = new Scanner(System.in);
        outputMsg("File name:");
        String pathToFile=getUserInput();
        File file = new File("./"+pathToFile);
        FileWriter writer = new FileWriter(file);

        for (String i: listForLog
        ) {
            writer.write(i+"\r\n");

        }

        outputMsg("The log has been saved.");
        writer.close();
    }

    public static void hardestCard()
    {
        if(mapAnswer.isEmpty())
        {
            outputMsg("There are no cards with errors.");
        }
        else
        {

            List<Integer> sortList = new ArrayList<>(mapAnswer.values());
            List<String> MaxMapError=new ArrayList<>();
            Collections.sort(sortList);
            //    String msg=new StringBuilder();
            //      StringBuilder msg=new StringBuilder();
            String e="";
            int count=0;
            for (Map.Entry<String, Integer> i:mapAnswer.entrySet()) {

                //  outputMsg(Integer.toString(i));
                if(i.getValue()==sortList.get(sortList.size()-1))
                {   count++;
                    MaxMapError.add(i.getKey());
                    String s="";//s.concat("\"");
                    //  String d=s.concat("\""+String.valueOf(i));
                    String d=s.concat("\""+i.getKey());
                    String c=d.concat("\"");
                    e=e.concat(c);
                    //      msg.append("\"").append(i.getKey()).append("\"").toString();
                    if (count>1)
                        e=e.concat(",");
                    //  msg.append(",");

                }
            }
            if(count==1)
            {
                outputMsg("The hardest card is "+e+"."+" You have "+sortList.get(sortList.size()-1)+" errors answering it.");
            }
            else{
                outputMsg("The hardest card is "+e+"."+" You have "+sortList.get(sortList.size()-1)+" errors answering them.");
            }
        }
    }
    public static void countError(String c)
    {int count=0;
        if(mapAnswer.containsKey(c))
        {
            count=mapAnswer.get(c);
            mapAnswer.remove(c,count);
            mapAnswer.put(c,(count++));
        }
        else
            count++;
        mapAnswer.put(c,(count++));
    }
    public static void  resetStats()
    {
        mapAnswer.clear();
        outputMsg("Card statistics has been reset.");
    }
    public static void main(String[] args) throws IOException {
        //  Scanner scanner = new Scanner(System.in);
        for(int i=0;i<args.length;i++) {
            if (args[i].equals("-import".toLowerCase())) {
                importCard(args[i+1]);
            }
        }

        String a=null;
        do {
            outputMsg("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            a = getUserInput();
            switch (a)
            {
                case "add": addCard();break;
                case "remove":removeCard() ;break;
                case "import":importCard();break;
                case "export":exportCard() ;break;
                case "ask": ask();break;
                case "log": log();break;
                case "hardest card": hardestCard();break;
                case "reset stats": resetStats();break;
                case "exit":{
                    outputMsg("Bye bye!");
                    for(int i=0;i<args.length;i++) {
                        if (args[i].equals("-export")) {
                            exportCard(args[i+1]);
                        }
                    }

                    a="0";
                };
            }
        }while (a!="0");
    }


    public static void importCard(String a)
    {

        int count=0;
        File file=new File(a);
        if (!file.isFile()) {
            outputMsg("File not found.");
        } else {
            try (Scanner scannerFromFile = new Scanner(file)) {
                while (scannerFromFile.hasNext()) {
                    count++;
                    String term = scannerFromFile.nextLine();
                    String definition = scannerFromFile.nextLine();
                    int countInFile=scannerFromFile.nextInt();
                    String str=scannerFromFile.nextLine();
                    if (map.containsKey(term)) {
                        map.remove(term);
                        if(mapAnswer.containsKey(term))
                        {
                            //   count1=mapAnswer.get(term);
                            mapAnswer.remove(term);
                        }
                    }
                    map.put(term, definition);
                    mapAnswer.put(term,countInFile);
                }
                outputMsg(count + " cards have been loaded.");
            } catch (FileNotFoundException e) {
                outputMsg("No file found: " + a);
            }

        }

    }
    private static void exportCard(String arg) throws IOException {
        File file = new File(arg);
        FileWriter writer = new FileWriter(file);

        for ( Map.Entry<String, String> i:map.entrySet()
        ) {
            writer.write(i.getKey()+"\r\n");
            writer.write(i.getValue()+"\r\n");
            if(mapAnswer.containsKey(i.getKey()))
            {
                writer.write(Integer.toString(mapAnswer.get(i.getKey()))+"\r\n");
            }
            else{
                writer.write(Integer.toString(0)+"\r\n");
            }
        }
        writer.close();
        outputMsg(map.size()+" cards have been saved.");
    }


}
