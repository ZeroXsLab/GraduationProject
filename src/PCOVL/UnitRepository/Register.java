/*
 *
 * Register.java
 * GraduationProject
 *
 * Created by X on 2019/4/11
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class Register extends SuperUnit {

    private int currentData;

    public Register(BaseUnitUI unitUI) {
        super(1, unitUI);
    }

    @Override
    public void run() {
        if (isControllerInChanrge()) {
            // In Controller State.
            if (this.unitUI.getName().contains("IR")) {
                inputEnable = (Controller.signal[1] != 0);
            } else {
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
        setOutContent(currentData);
    }
}
