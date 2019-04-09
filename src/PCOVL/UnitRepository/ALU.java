/*
 *
 * ALU.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class ALU extends SuperUnit {

    private int control = 0;
    private int inputOne;
    private int inputTwo;

    private boolean isTheControlInLink = false;

    public ALU(BaseUnitUI unitUI) {
        super(3, unitUI);
    }

    @Override
    public void run() {
        if (shouldGetSpecificIn()) {
            isTheControlInLink = false;
            inToRun = new int[2];
            inToRun[0] = 2;
            inToRun[1] = 1;
        } else {
            isTheControlInLink = true;
            inToRun = null;
        }
        super.run();
    }

    @Override
    public void processData() {
        control = in[0].content;
        inputOne = in[1].content;
        inputTwo = in[2].content;
        switch (control) {
            case 0:
                setOutContent(inputOne + inputTwo);
                break;
            case 1:
                setOutContent(inputOne - inputTwo);
                break;
            case 2:
                setOutContent(inputOne);
                break;
            case 3:
                setOutContent(inputTwo);
                break;
        }
    }
}
