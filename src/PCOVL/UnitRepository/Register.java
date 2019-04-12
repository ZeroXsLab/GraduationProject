/*
 *
 * Register.java
 * GraduationProject
 *
 * Created by X on 2019/4/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.GlobalVariable;

public class Register extends SuperUnit {

    private int currentData;
    private boolean isAcc = false;

    public Register(BaseUnitUI unitUI) {
        super(1, unitUI);
    }

    @Override
    public void run() {
        if (isControllerInChanrge()) {
            // In Controller State.
            if (this.unitUI.getName().contains("IR")) {
                isAcc = false;
                inputEnable = (Controller.signal[1] != 0);
            } else {
                isAcc = true;
                inputEnable = (Controller.signal[3] != 0);
            }
        } else {
            // In User Control State, when the Control In is Link, the input is enable.
            if (isTheControlInNotLink()) {
                inputEnable = false;
            } else {
                inputEnable = true;
            }
        }
        super.run();
    }

    @Override
    public void processData() {
        if (inputEnable) {
            currentData = in[0].content;
        }
        if (isAcc) {
            GlobalVariable.Flag[0] = currentData < 0 ? 1 : 0;
            GlobalVariable.Flag[1] = currentData == 0 ? 1 : 0;
        }
        setOutContent(currentData);
    }
}
