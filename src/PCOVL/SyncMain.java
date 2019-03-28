/*
 *
 * SyncMain.java
 * GraduationProject
 *
 * Created by X on 2019/3/28
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

import PCOVL.UI.GlobalVariable;
import PCOVL.UI.HomePage;

import java.util.ArrayList;

public class SyncMain {

//    static ArrayList<SuperUnit> unitArray = new ArrayList<>();
    static ArrayList<Thread> threads = new ArrayList<>();
//    static MUX2 first = new MUX2(1);

    public static void main(String[] args) throws Exception {

////        linkOrder[0].add(first);
//        unitArray.add(first);
//        // Do it when you drag a unit out
//        MUX2 third = new MUX2(3);
//        unitArray.add(third);
//        Switch second = new Switch(2);
//        unitArray.add(second);
//        // Do it when you link a unit with another
//        linkUnit(first, second);
//        linkUnit(second, third);
//        linkUnit(third,first);
//        // Execute a instruction
//        executeInstruction();   // First(1)->Third(3)->Second(2)
//        unitArray.remove(second);
//        unitArray.add(1,second);
//        second.inputEnable = false;
//        executeInstruction();   // First(1)->Second(2)->Third(3)

//        Switch one = new Switch(1);
//        Switch two = new Switch(2);
//        MUX2 three = new MUX2(3);
//        unitArray.add(one);
//        unitArray.add(two);
//        unitArray.add(three);
//        one.init();
//        two.init();
//        linkUnit(one,0,three);
////        linkUnit(one,1,two);
//        linkUnit(two,1,three);
//        executeInstruction();
//        executeInstruction();


        // The UI
        new HomePage();

    }

    public static void linkUnit(SuperUnit in, int index, SuperUnit out){
        in.setOut(out.getInAt(index));
    }



}
/*TODO LinkMultiTree Create
    let the SuperUnit support mutil in out(func linkUnit need to update, so we can link with specific in and out
*/