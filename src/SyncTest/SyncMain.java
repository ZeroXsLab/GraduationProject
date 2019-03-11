package SyncTest;

import java.util.ArrayList;

public class SyncMain {

    static ArrayList<Object> unitArray = new ArrayList<>();
    static ArrayList<Thread> threads = new ArrayList<>();
    static btnB first = new btnB(1);

    public static void main(String args[]) throws Exception {

//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.next();
//        while (!s.equalsIgnoreCase("exit")){
//            Class c = Class.forName("SyncTest." + s);
//            Object o = c.getConstructor(new Class[]{Data.class}).newInstance(data);
//            unitArray.add(o);
//            s = scanner.next();
//        }
//        for (Object o : unitArray){
//            runThread t = ((runThread)o);
//            threads.add(new Thread(t));
//        }

        unitArray.add(first);
        // Do it when you drag a unit out
        btnB third = new btnB(2);
        unitArray.add(third);
        btnA second = new btnA(12);
        unitArray.add(second);
        // Do it when you link a unit with another
        linkBtn(first, second);
        linkBtn(second, third);
        // Execute a instruction
        executeInstruction();
        executeInstruction();
    }

    public static void linkBtn(SuperBtn in, SuperBtn out){
        in.setOut(out.getIn());
    }

    private static void executeInstruction() throws Exception {
        System.out.println("......................Process a instruction");
        threads = new ArrayList<>();
        for (int i = 0; i < unitArray.size(); i ++){
            threads.add(new Thread( (SuperBtn) unitArray.get(i) ));
            threads.get(i).start();
        }
        first.init();
        for (int i = 0; i < threads.size(); i ++) {
            threads.get(i).join();
        }
        // Reset the status of the last unit if you know which is. if not, reset all
        for (int i = 0; i < unitArray.size(); i ++) {
            ((SuperBtn) unitArray.get(i)).finish();
        }
        System.out.println("......................Finish a instruction");
    }

}
