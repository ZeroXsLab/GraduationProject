/*
 *
 * ALU.java
 * GraduationProject
 *
 * Created by X on 2019/5/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.GlobalVariable;

import java.awt.*;

public class ALU extends SuperUnit {

    private int control = 0;
    private int inputOne;
    private int inputTwo;

    public ALU(BaseUnitUI unitUI) {
        super(3, unitUI);
    }

    @Override
    public void run() {
        if (isControllerInChanrge()) {
            // In Controller State, we don't need to read the controlIn
            if (Controller.signal[4] == -1) {
                // The unit is Disable.
                return;
            } else if (Controller.signal[4] == 1 || Controller.signal[4] == 3) {
                inToRun = new int[2];
                inToRun[0] = 2;
                inToRun[1] = 1;
            } else{
                inToRun = new int[1];
                inToRun[0] = Controller.signal[4] == 2 ? 1 : 2;
            }
        } else {
            // In User Control State we should read all the IN data.
            inToRun = null;
        }
        super.run();
    }

    @Override
    public void processData() {
        if (!isControllerInChanrge()) {
            // In User Control State
            control = in[0].content;
        }else {
            // In Controller State
            control = Controller.signal[4];
        }
        inputOne = in[1].content;
        inputTwo = in[2].content;
        switch (control) {
            case 0:
                // =Y
                setOutContent(inputTwo);
                addition = "=Y: " + inputOne + " and " + inputTwo;
                if (Controller.usualOrJGEorJNE == 1 && GlobalVariable.Flag[0] == 0) {
                    Controller.signal[2] = 1;
                } else if (Controller.usualOrJGEorJNE == 2 && GlobalVariable.Flag[1] == 0) {
                    Controller.signal[2] = 1;
                }
                break;
            case 1:
                // ADD
                setOutContent(inputOne + inputTwo);
                addition = "ADD: " + inputOne + " and " + inputTwo;
                break;
            case 2:
                // INC
                setOutContent(inputOne + 1);
                addition = "INC X: " + inputOne + " and " + inputTwo;
                break;
            case 3:
                // SUB
                setOutContent(inputOne - inputTwo);
                addition = "SUB: " + inputOne + " and " + inputTwo;
                break;
        }
        if (GlobalVariable.programCounter != null && (Controller.signal[2] != 0)) {
            // Update the PC
            boolean enableStatus = GlobalVariable.programCounter.inputEnable;
            GlobalVariable.programCounter.inputEnable = true;
            GlobalVariable.programCounter.processData();
            GlobalVariable.programCounter.inLines[0].setBackground(Color.RED);
            GlobalVariable.programCounter.inputEnable = enableStatus;
        }
    }
}
