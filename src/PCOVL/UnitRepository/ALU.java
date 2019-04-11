/*
 *
 * ALU.java
 * GraduationProject
 *
 * Created by X on 2019/4/11
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.GlobalVariable;

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
            inToRun = new int[2];
            inToRun[0] = 2;
            inToRun[1] = 1;
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
                // ADD
                setOutContent(inputOne + inputTwo);
                break;
            case 1:
                // SUB
                setOutContent(inputOne - inputTwo);
                break;
            case 2:
                // INC
                setOutContent(inputOne + 1);
                break;
            case 3:
                // =Y
                setOutContent(inputTwo);
                break;
        }
        if (GlobalVariable.programCounter.inputEnable) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + GlobalVariable.programCounter.in[0].content);
            GlobalVariable.programCounter.setOutContent(GlobalVariable.programCounter.in[0].content);
            GlobalVariable.programCounter.setLabel();
        }
    }
}