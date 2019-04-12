/*
 *
 * PC.java
 * GraduationProject
 *
 * Created by X on 2019/4/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class PC extends SuperUnit {

    private int pointer = 0;

    public PC(BaseUnitUI unitUI) {
        super(1, unitUI);
    }

    @Override
    public void run() {
        if (isControllerInChanrge()) {
            // In Controller State, set enable by the control signal
            inputEnable = (Controller.signal[2] != 0);
            // FIXME how to define the inToRun and isJMP.
        } else {
            // In User Control State, always be enable.
            if (isTheControlInNotLink()) {
                // the control IN is Linked, read the in Data.
                inputEnable = false;
            } else {
                // the control IN is NOT Linked, we don't need the InData(as a PC can work without In, just to increase.)
                inputEnable = true;
            }
        }
        super.run();
    }

    @Override
    public void processData() {
        if (inputEnable) {
            pointer = in[0].content;
        }
        setOutContent(pointer);
    }
}
