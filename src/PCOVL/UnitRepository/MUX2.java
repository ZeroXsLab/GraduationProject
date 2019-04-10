/*
 *
 * MUX2.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.GlobalVariable;

public class MUX2 extends SuperUnit {
    // store which input the MUX2 select, 0 For inputOne, 1 For inputTwo
    private int S0 = 0;
    private int inputOne;
    private int inputTwo;

    private boolean isTheControlInLink = false;

    public MUX2(BaseUnitUI unitUI) {
        super(3, unitUI);
    }

    @Override
    public void run() {
        int selOrder;
        if (checkOut(GlobalVariable.RAM.in[0])) {
            // Xsel
            selOrder = 5;
        } else if (checkOut(GlobalVariable.RAM.in[1])) {
            // Asel
            selOrder = 7;
        } else {
            // Ysel
            selOrder = 6;
        }
        if (shouldGetSpecificIn()) {
            isTheControlInLink = false;
            inToRun = new int[1];
            inToRun[0] = Controller.signal[selOrder] + 1;
            S0 = Controller.signal[selOrder];
            System.out.println(this.getClass().getName() + "\t@" + Integer.toHexString(this.hashCode()) + "\tGoing to get in at" + inToRun[0]);
        } else {
            isTheControlInLink = true;
            inToRun = null;
        }
        super.run();
    }

    @Override
    public void processData() {
        // Store the data from the In
        if (isTheControlInLink) {
            S0 = in[0].content;
        }
        inputOne = in[1].content;
        inputTwo = in[2].content;
        // Set Out
        if (S0 < 1) {
            // it's 0, Select A(InputOne)
            setOutContent(inputOne);
        } else {
            setOutContent(inputTwo);
        }
    }

    boolean checkOut(Data compareTo) {
        for (int iOut = 0; iOut < this.out.length; iOut ++) {
            if (compareTo == this.out[iOut]) {
                return true;
            }
        }
        return false;
    }
}
