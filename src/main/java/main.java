import javafx.util.Pair;


import java.io.*;
import java.util.*;
import java.util.List;

import static java.lang.Integer.min;

public class main {




    private static Pair<List<Integer>,String> SumaSecvential(Pair<List<Integer>, List<Integer>> pair){
        long startTime = System.nanoTime();
        List<Integer> suma=new ArrayList<>();
        List<Integer> first=pair.getKey();
        List<Integer> second=pair.getValue();
        int carry=0,sumaTemp=0,i,j=-1;
        for(i=0;i<first.size() && i<second.size();i++)
        {
            sumaTemp=first.get(i)+second.get(i);
            suma.add((sumaTemp+carry)%10);
            carry=(sumaTemp+carry)/10;
        }
        if(carry!=0)
            j=i;
        while(i<first.size()){

            suma.add(first.get(i));
            i++;
        }
        while(i<second.size()){

            suma.add(second.get(i));
            i++;
        }

        if(j!=-1){
            if(suma.size()==j)
                suma.add(0);
            suma.set(j,suma.get(j)+1);}
        long finishTime = System.nanoTime();
        return new Pair<List<Integer>,String>(suma,String.valueOf((finishTime-startTime)/1000000));
    }


    private static Pair<List<Integer>,String> SumaParalelOptimizat(Pair<List<Integer>, List<Integer>> pair, int numberThreads){
         long startTime = System.nanoTime();


            int start = 0, finish, lenght;
            Thread th[] = new Thread[numberThreads];
            lenght = min(pair.getKey().size(), pair.getValue().size()) / numberThreads + 1;

            if (pair.getKey().size() < pair.getValue().size()) {
                for (int i = 0; i < numberThreads; i++) {
                    finish = start + lenght;
                    th[i] = new ThreadClass(pair.getKey(), pair.getValue(), pair.getValue(), start, finish);
                    start = finish;
                    th[i].start();


                }
                for(int i=0;i<numberThreads;i++) {
                    try {
                        th[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                long finishTime = System.nanoTime();

                return new Pair<List<Integer>, String>(pair.getValue(), String.valueOf((finishTime - startTime) / 1000000));
            } else {
                for (int i = 0; i < numberThreads; i++) {
                    finish = start + lenght;
                    th[i] = new ThreadClass(pair.getKey(), pair.getValue(), pair.getKey(), start, finish);
                    start = finish;
                    th[i].start();
                }
                for(int i=0;i<numberThreads;i++) {
                    try {
                        th[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


                long finishTime = System.nanoTime();

                return new Pair<List<Integer>, String>(pair.getKey(), String.valueOf((finishTime - startTime) / 1000000));

            }
        }

    private static Pair<List<Integer>,String> SumaParalelSimplificat(Pair<List<Integer>, List<Integer>> pair, int numberThreads) {
        long startTime = System.nanoTime();
        int start = 0, finish, lenght;
        Thread th[] = new Thread[numberThreads];
        lenght = min(pair.getKey().size(), pair.getValue().size()) / numberThreads + 1;

        if (pair.getKey().size() < pair.getValue().size()) {
            for (int i = 0; i < numberThreads; i++) {
                finish = start + lenght;
                th[i] = new ThreadClass(pair.getKey(), pair.getValue(), pair.getValue(), start, finish);
                start = finish;
                th[i].start();
                try {
                    th[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            long finishTime = System.nanoTime();

            return new Pair<List<Integer>, String>(pair.getValue(), String.valueOf((finishTime - startTime) / 1000000));
        } else {
            for (int i = 0; i < numberThreads; i++) {
                finish = start + lenght;
                th[i] = new ThreadClass(pair.getKey(), pair.getValue(), pair.getKey(), start, finish);
                start = finish;
                th[i].start();
                try {
                    th[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            long finishTime = System.nanoTime();

            return new Pair<List<Integer>, String>(pair.getKey(), String.valueOf((finishTime - startTime) / 1000000));

        }
    }



    public static void main(String[] args) throws IOException {


        String output="suma";
        String input="numere.txt";
        //Utils.createFileRandomNumbers(input,2,1000000,1100000);

        Pair<List<Integer>,List<Integer>> numbers=Utils.ReadFromFile(input);
        Pair<List<Integer>,String> secvential=SumaSecvential(numbers);


        Utils.WriteInFile(output+"0.txt",secvential.getKey());
        Utils.writeFileExcel(Long.valueOf(secvential.getValue()),"0");



        numbers=Utils.ReadFromFile(input);
        Pair<List<Integer>,String> par1=SumaParalelOptimizat(numbers,1);
        numbers=Utils.ReadFromFile(input);
        Pair<List<Integer>,String> par2=SumaParalelOptimizat(numbers,2);
        numbers=Utils.ReadFromFile(input);
        Pair<List<Integer>,String> par4=SumaParalelOptimizat(numbers,4);
        numbers=Utils.ReadFromFile(input);
        Pair<List<Integer>,String> par6=SumaParalelOptimizat(numbers,6);
        numbers=Utils.ReadFromFile(input);
        Pair<List<Integer>,String> par8=SumaParalelOptimizat(numbers,8);
        Utils.WriteInFile(output+"O1.txt",par1.getKey());
        Utils.WriteInFile(output+"O2.txt",par2.getKey());
        Utils.WriteInFile(output+"O4.txt",par4.getKey());
        Utils.WriteInFile(output+"O6.txt",par6.getKey());
        Utils.WriteInFile(output+"O8.txt",par8.getKey());
        if( Utils.checkTwoFiles("suma0.txt","sumaO1.txt"))
            Utils.writeFileExcel(Long.valueOf(par1.getValue()),"1O");
        if( Utils.checkTwoFiles("suma0.txt","sumaO2.txt"))
            Utils.writeFileExcel(Long.valueOf(par2.getValue()),"2O");
        if(Utils.checkTwoFiles("suma0.txt","sumaO4.txt"))
            Utils.writeFileExcel(Long.valueOf(par4.getValue()),"4O");
        if(Utils.checkTwoFiles("suma0.txt","sumaO6.txt"))
            Utils.writeFileExcel(Long.valueOf(par6.getValue()),"6O");
        if(Utils.checkTwoFiles("suma0.txt","sumaO8.txt"))
            Utils.writeFileExcel(Long.valueOf(par8.getValue()), "8O");




        numbers=Utils.ReadFromFile(input);
        par1=SumaParalelSimplificat(numbers,1);
        numbers=Utils.ReadFromFile(input);
        par2=SumaParalelSimplificat(numbers,2);
        numbers=Utils.ReadFromFile(input);
        par4=SumaParalelSimplificat(numbers,4);
        numbers=Utils.ReadFromFile(input);
        par6=SumaParalelSimplificat(numbers,6);
        numbers=Utils.ReadFromFile(input);
        par8=SumaParalelSimplificat(numbers,8);
        Utils.WriteInFile(output+"S1.txt",par1.getKey());
        Utils.WriteInFile(output+"S2.txt",par2.getKey());
        Utils.WriteInFile(output+"S4.txt",par4.getKey());
        Utils.WriteInFile(output+"S6.txt",par6.getKey());
        Utils.WriteInFile(output+"S8.txt",par8.getKey());
        if( Utils.checkTwoFiles("suma0.txt","sumaS1.txt"))
            Utils.writeFileExcel(Long.valueOf(par2.getValue()),"1S");
        if( Utils.checkTwoFiles("suma0.txt","sumaS2.txt"))
            Utils.writeFileExcel(Long.valueOf(par2.getValue()),"2S");
        if(Utils.checkTwoFiles("suma0.txt","sumaS4.txt"))
            Utils.writeFileExcel(Long.valueOf(par4.getValue()),"4S");
        if(Utils.checkTwoFiles("suma0.txt","sumaS6.txt"))
            Utils.writeFileExcel(Long.valueOf(par6.getValue()),"6S");
        if(Utils.checkTwoFiles("suma0.txt","sumaS8.txt"))
            Utils.writeFileExcel(Long.valueOf(par8.getValue()), "8S");

    }

}