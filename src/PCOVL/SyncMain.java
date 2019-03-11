/*
 *
 * SyncMain.java
 * GraduationProject
 *
 * Created by X on 2019/3/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

import java.util.ArrayList;

public class SyncMain {

    static ArrayList<Object> unitArray = new ArrayList<>();
    static ArrayList<Thread> threads = new ArrayList<>();
    static unitB first = new unitB(1);

    public static void main(String args[]) throws Exception {

        unitArray.add(first);
        // Do it when you drag a unit out
        unitB third = new unitB(2);
        unitArray.add(third);
        unitA second = new unitA(12);
        unitArray.add(second);
        // Do it when you link a unit with another
        linkUnit(first, second);
        linkUnit(second, third);
        // Execute a instruction
        executeInstruction();
        executeInstruction();
    }

    public static void linkUnit(SuperUnit in, SuperUnit out){
        in.setOut(out.getIn());
    }

    private static void executeInstruction() throws Exception {
        System.out.println("......................Process a instruction");
        threads = new ArrayList<>();
        for (int i = 0; i < unitArray.size(); i ++){
            threads.add(new Thread( (SuperUnit) unitArray.get(i) ));
            threads.get(i).start();
        }
        first.init();
        for (int i = 0; i < threads.size(); i ++) {
            threads.get(i).join();
        }
        // Reset the status of the last unit if you know which is. if not, reset all
        for (int i = 0; i < unitArray.size(); i ++) {
            ((SuperUnit) unitArray.get(i)).finish();
        }
        System.out.println("......................Finish a instruction");
    }

}
