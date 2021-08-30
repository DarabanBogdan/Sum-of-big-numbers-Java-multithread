import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {

    public static Pair<List<Integer>,List<Integer>> ReadFromFile(String nume){
        List<Integer> first=new ArrayList<>();
        List<Integer> second=new ArrayList<>();

        boolean firstNumber=true;
        File file = new File(nume);

        try {
            FileInputStream fis = new FileInputStream(file);
            int i = fis.read();
            while (i != -1) {
                if (i == '\n') {
                    firstNumber = false;
                }
                else if(firstNumber)
                    first.add(Character.getNumericValue(i));
                else
                    second.add(Character.getNumericValue(i));

                i = fis.read();

            }
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.reverse(first);
        Collections.reverse(second);
        return new Pair<List<Integer>,List<Integer>>(first, second);
    };

    public static void WriteInFile(String nume,List<Integer> suma) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(nume, "UTF-8");
        boolean first=true;
        for(int i=0;i<suma.size();i++){
            if(suma.get(i)!=0)
                first=false;
            if(!first)
                writer.print(suma.get(i));}
        writer.close();
    }

    public static void createFileRandomNumbers(String nume, int size, int min, int max) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(nume, "UTF-8");
        Random rand = new Random();
        for(int i=0;i<size;i++){
            int numar=rand.nextInt((max - min) + 1) + min-1;
            writer.print(rand.nextInt(9)+1);
            for(int j=0;j<numar-1;j++)
                writer.print(rand.nextInt(10));
            writer.print("\n");
        }
        writer.close();


    }

    public static boolean checkTwoFiles(String fille1, String fille2){
        boolean areFilesIdentical = true;
        File file1 = new File(fille1);
        File file2 = new File(fille2);
        if (!file1.exists() || !file2.exists()) {

            return false;
        }
        try {
            FileInputStream fis1 = new FileInputStream(file1);
            FileInputStream fis2 = new FileInputStream(file2);
            int i1 = fis1.read();
            int i2 = fis2.read();
            while (i1 != -1) {
                if (i1 != i2) {
                    System.out.print(i1+" "+i2);
                    areFilesIdentical = false;
                    break;
                }
                i1 = fis1.read();
                i2 = fis2.read();
            }
            fis1.close();
            fis2.close();
        } catch (IOException e) {
            System.out.println("IO exception");
            areFilesIdentical = false;
        }
        return areFilesIdentical;
    }

    public static void writeFileExcel(long numar,String thred) throws IOException {
        FileWriter writer = new FileWriter("time.csv",true);
        writer.write(numar+"ms,"+ thred);
        writer.write('\n');
        writer.close();


    }

}
