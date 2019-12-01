package example1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FileDemo {

    public static void main(String[] args) {

        File dir = new File("C:/Users/user/Desktop/javaTest/iotest");
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File("C:/Users/user/Desktop/javaTest/iotest/test.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            }
            catch(IOException e){
                System.out.println("oops");
            }
        }
        for(File f : dir.listFiles()){
            System.out.println(f.getName());
        }
        File[] list = dir.listFiles();
        for (int i = 0; i < list.length; i++) {
            if(list[i].getName().charAt(0) == 't'){
                list[i].delete();
            }
        }
    }
}
