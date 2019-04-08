/*
 *
 * PC.java
 * GraduationProject
 *
 * Created by X on 2019/4/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class PC extends SuperUnit {

    private int pointer = 0;
    private boolean isJMP = false;

    public PC(BaseUnitUI unitUI) {
        super(1, unitUI);
    }

    @Override
    public void run() {
        if (shouldGetSpecificIn()) {
            // normal state, we don't need the InData
            inToRun = new int[0];
            isJMP = false;
        } else {
            inToRun = null;
            isJMP = true;
        }
        super.run();
    }

    @Override
    public void processData() {
        if (isJMP) {
            pointer = in[0].content;
        }
        out.content = pointer;
        pointer ++;
    }
}
