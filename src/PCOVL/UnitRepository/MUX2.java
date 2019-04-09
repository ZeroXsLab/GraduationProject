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
        if (shouldGetSpecificIn()) {
            isTheControlInLink = false;
            inToRun = new int[2];
            inToRun[0] = 1;
            inToRun[1] = 2;
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
}
