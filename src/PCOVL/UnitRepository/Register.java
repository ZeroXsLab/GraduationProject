/*
 *
 * Register.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class Register extends SuperUnit {

    private int currentData;
    private boolean shouldUpdate = false;

    public Register(BaseUnitUI unitUI) {
        super(1, unitUI);
    }

    @Override
    public void run() {
        if (this.unitUI.getName().contains("IR")) {
            inputEnable = (Controller.signal[1] != 0);
        } else {
            inputEnable = (Controller.signal[3] != 0);
        }
        if (shouldGetSpecificIn()) {
            inToRun = new int[0];
            shouldUpdate = false;
        } else {
            inToRun = null;
            shouldUpdate = true;
        }
        super.run();
    }

    @Override
    public void processData() {
        if (shouldUpdate) {
            currentData = in[0].content;
        }
        setOutContent(currentData);
    }
}
