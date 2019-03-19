/*
 *
 * SyncMain.java
 * GraduationProject
 *
 * Created by X on 2019/3/19
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

import java.util.ArrayList;

public class SyncMain {

    static ArrayList<SuperUnit> unitArray = new ArrayList<>();
    static ArrayList<Thread> threads = new ArrayList<>();
    static unitB first = new unitB(1);

    public static void main(String[] args) throws Exception {

//        linkOrder[0].add(first);
        unitArray.add(first);
        // Do it when you drag a unit out
        unitB third = new unitB(3);
        unitArray.add(third);
        unitA second = new unitA(2);
        unitArray.add(second);
        // Do it when you link a unit with another
        linkUnit(first, second);
        linkUnit(second, third);
        linkUnit(third,first);
        // Execute a instruction
        executeInstruction();   // First(1)->Third(3)->Second(2)
        unitArray.remove(second);
        unitArray.add(1,second);
        second.inputEnable = false;
        executeInstruction();   // First(1)->Second(2)->Third(3)
    }

    public static void linkUnit(SuperUnit in, SuperUnit out){
        in.setOut(out.getIn());
    }

    private static void executeInstruction() throws Exception {
        System.out.println("......................Process a instruction");
        threads = new ArrayList<>();
        for (int i = 0; i < unitArray.size(); i ++){
            if (unitArray.get(i).inputEnable){
                threads.add(new Thread(unitArray.get(i)));
                threads.get(threads.size() - 1).start();
            } else {
                break;
            }
        }
        first.init();
        for (int i = 0; i < threads.size(); i ++) {
            threads.get(i).join();
        }
        // Reset the status of the last unit if you know which is. if not, reset all
        for (int i = 0; i < unitArray.size(); i ++) {
            (unitArray.get(i)).finish();
        }
        System.out.println("......................Finish a instruction");
    }

}
/*  TODO examine whether can we get the object easier where the mouse click and release;
    TODO UI create
    TODO LinkMultiTree Create
*/