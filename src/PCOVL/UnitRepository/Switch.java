/*
 *
 * Switch.java
 * GraduationProject
 *
 * Created by X on 2019/4/9
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

// Remember to call the readyForRead() to make the unit always readable after Creating and Running
public class Switch extends SuperUnit {
    private Boolean isOn = false;

    public Switch(BaseUnitUI unitUI) {
        super(0, unitUI);
        this.bits = 1;
        this.readyForRead();    // Always readable.
        this.switchIt();
    }

    @Override
    public void run() {
        super.run();
        this.readyForRead();    // Always readable.
    }

    // Just put the onState to output
    @Override
    public void processData() {
        out.content = isOn ? 1 : 0;
    }

    // when you click it, change the onState.
    public void switchIt(){
        isOn = !isOn;
        unitUI.setText(isOn ? "1" : "0");
    }
}
