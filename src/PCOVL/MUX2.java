/*
 *
 * MUX2.java
 * GraduationProject
 *
 * Created by X on 2019/3/28
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

import PCOVL.UI.BaseUnitUI;

public class MUX2 extends SuperUnit {
    private int S0;
    private int inputOne;
    private int inputTwo;

    public MUX2(int ID, BaseUnitUI unitUI) {
        super(ID,3, unitUI);
    }

    @Override
    public void processData() {
//        out.content = in[0].content + in[1].content;
        S0 = in[0].content;
        inputOne = in[1].content;
        inputTwo = in[2].content;
        if (S0 > 0) {
            // Select A
            out.content = inputOne;
        } else {
            out.content = inputTwo;
        }
        setLabel();
    }
}
