import java.util.List;

public class ThreadClass extends Thread{
    private int start;
    private int finish;
    List<Integer> first,second,suma;


    public ThreadClass(List<Integer> first, List<Integer> second, List<Integer> suma, int start, int finish){
        this.finish=finish;
        this.start=start;
        this.first=first;
        this.second=second;
        this.suma=suma;
    }
    @Override
    public void run(){
        int carry=0,sumaTemp=0,i;
        for(i=start;i<finish && i<first.size() && i<second.size();i++)
        {
            sumaTemp=first.get(i)+second.get(i);
            suma.set(i,(sumaTemp+carry)%10);
            carry=(sumaTemp+carry)/10;

        }

        while(carry!=0 && i<suma.size()){

            sumaTemp=suma.get(i)+1;
            suma.set(i,sumaTemp%10);

            carry=sumaTemp/10;
            i++;

        }
        if(carry!=0){

            suma.add(1);
        }

    }

}
