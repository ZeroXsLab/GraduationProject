package SyncTest;

import java.util.ArrayList;
import java.util.Scanner;

public class SyncMain {
    public static void main(String args[]) throws Exception {
        Data data1 = new Data();
        Data data2 = new Data();
        Data data3 = new Data();
        Data data4 = new Data();
        ArrayList<Object> arr = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.next();
//        while (!s.equalsIgnoreCase("exit")){
//            Class c = Class.forName("SyncTest." + s);
//            Object o = c.getConstructor(new Class[]{Data.class}).newInstance(data);
//            arr.add(o);
//            s = scanner.next();
//        }
//        for (Object o : arr){
//            runThread t = ((runThread)o);
//            threads.add(new Thread(t));
//        }
        threads.add(new Thread(new btnB(data1, data2, 1)));
//        threads.add(new Thread(new btnA(data1, data2, 12)));
        threads.add(new Thread(new btnB(data3, data4, 2)));
        threads.add(new Thread(new btnA(data2, data3, 12)));

        for (int i = 0; i < threads.size(); i ++){
            threads.get(i).start();
        }
        System.out.println("......................all thread start");
        data1.setBeenRead(false);
        for (int i = 0; i < threads.size(); i ++){
            threads.get(i).join();
        }
        System.out.println("......................all thread join");
    }


}
