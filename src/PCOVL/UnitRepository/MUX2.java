/*
 *
 * MUX2.java
 * GraduationProject
 *
 * Created by X on 2019/5/5
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

    private int selIndex = 0;   // 5 For Xsel(Data Out); 6 For Ysel(Data In); 7 For Asel(Address)
    public boolean isDataOut = false;
    private String type = "";

    public MUX2(BaseUnitUI unitUI) {
        super(3, unitUI);
    }

    @Override
    public void run() {
        if (isControllerInChanrge()) {
            // In Controller State, only read the specific one In we need
            if (isOutsContain(GlobalVariable.RAM.in[0])) {
                // Xsel
                selIndex = 5;
                isDataOut = true;
                type = "DataOut ";
            } else if (isOutsContain(GlobalVariable.RAM.in[1])) {
                // Asel
                selIndex = 7;
                isDataOut = false;
                this.bits = 12;     //It is the address MUX2, only get and show the last 12 bits
                type = "Address ";
            } else {
                // Ysel
                selIndex = 6;
                isDataOut = false;
                type = "DataIn ";
            }
            if (Controller.signal[selIndex] == -1) {
                // Disable
                return;
            } else {
                inToRun = new int[1];
                inToRun[0] = Controller.signal[selIndex] + 1;   // As In0 is the controlIn, so it has to plus one.
            }
        } else {
            // In User Control State, we should read all the In data.
            inToRun = null;
        }
        super.run();
    }

    @Override
    public void processData() {
        // Store the data from the In
        if (isControllerInChanrge()){
            // In Controller State, set S0 by the Control Signal
            S0 = Controller.signal[selIndex];
        } else {
            // In User Control State, set S0 by the control IN
            S0 = in[0].content;
        }
        inputOne = in[1].content;
        inputTwo = in[2].content;
        // Set Out
        if (S0 < 1) {
            // it's 0, Select A(InputOne)
            setOutContent(inputOne);
            addition = type + "Select Zero";
        } else {
            if (type.contains("In")) {
                inputTwo %= 4096;
            }
            setOutContent(inputTwo);
            addition = type + "Select One";
        }
    }

    // the check whether the Data has been link to this out.
    boolean isOutsContain(Data compareTo) {
        for (int iOut = 0; iOut < this.out.length; iOut ++) {
            if (compareTo == this.out[iOut]) {
                return true;
            }
        }
        return false;
    }
}
