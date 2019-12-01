import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {

    public static void main(String[] args) {

        File file = new File("voina_mir.txt");
        long start = System.currentTimeMillis();
        try{
            int commas = countCommas(file, 2);
            System.out.println(commas + " commas");
        }
        catch (FileNotFoundException e){
            System.out.println("No such file");
        }
        catch(IOException e){
            System.out.println("Something went wrong");
        }
        long end = System.currentTimeMillis();
        System.out.println("It took " + (end - start)  + " milliseconds");
    }

    public static int countCommas(File file, int countThreads) throws IOException {
        //read the file in a string
        String text = Files.readString(file.toPath());
        //split that string in equal parts
        int partLength = text.length()/countThreads;
        String[] parts = new String[countThreads];
        int endIndex = partLength - 1;
        int startIndex = 0;
        for (int i = 0; i < countThreads; i++) {
            if(i == countThreads - 1){
                parts[i] = text.substring(startIndex, text.length() - 1);
                break;
            }
            parts[i] = text.substring(startIndex, endIndex);
            startIndex = endIndex + 1;
            endIndex = endIndex + partLength;
        }
        //create countThreads threads
        int[] counts = new int[countThreads];
        for (int i = 0; i < countThreads; i++) {
            AtomicInteger counter = new AtomicInteger();
            int finalI = i;
            Thread t = new Thread(()->{
                for (int j = 0; j < parts[finalI].length(); j++) {
                    if(parts[finalI].charAt(j) == ','){
                        counter.getAndIncrement();
                    }
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("oops");
            }
            counts[finalI] = counter.intValue();
        }
        //sum all the counters in one and return it
        int commas = 0;
        for (int count : counts) {
            commas += count;
        }
        return commas;
    }
}
