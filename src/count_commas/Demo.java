package count_commas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Demo {

    public static void main(String[] args) {

        File file = new File("voina_mir.txt");
        long start = System.currentTimeMillis();
        int commas = countCommas(file, 5);
        long end = System.currentTimeMillis();
        System.out.println(commas);
        System.out.println("It took " + (end - start) + " milliseconds");
    }

    public static int countCommas(File file, int countThreads){
        //read the file in a string
        String text = null;
        StringBuilder sb = new StringBuilder();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                sb.append(sc.nextLine() + System.lineSeparator());
            }
            text = sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("oops");
        }
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
        //create Threads
        Set<CommaCounter> threads = new HashSet<>();
        for (int i = 0; i < countThreads; i++) {
            CommaCounter t = new CommaCounter(parts[i]);
            threads.add(t);
            t.start();
        }
        for(CommaCounter t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("oops");
            }
        }
        int commas = 0;
        for(CommaCounter t : threads){
            commas += t.getTotal();
        }
        return commas;
    }
}
