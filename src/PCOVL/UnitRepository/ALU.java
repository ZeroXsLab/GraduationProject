/*
 *
 * ALU.java
 * GraduationProject
 *
 * Created by X on 2019/4/9
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class ALU extends SuperUnit {

    private int control;
    private int inputOne;
    private int inputTwo;

    public ALU(BaseUnitUI unitUI) {
        super(3, unitUI);
    }

    @Override
    public void processData() {
        control = in[0].content;
        inputOne = in[1].content;
        inputTwo = in[2].content;
        switch (control) {
            case 0:
                out.content = inputOne + inputTwo;
                break;
            case 1:
                out.content = inputOne - inputTwo;
                break;
            case 2:
                out.content = inputOne;
                break;
            case 3:
                out.content = inputTwo;
                break;
        }
    }
}
