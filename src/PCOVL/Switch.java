/*
 *
 * Switch.java
 * GraduationProject
 *
 * Created by X on 2019/3/28
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

import PCOVL.UI.BaseUnitUI;

public class Switch extends SuperUnit {
    private Boolean isOn = false;

    public Switch(int ID, BaseUnitUI unitUI) {
        super(ID,0, unitUI);
        this.init();    // Always readable.
        this.switchIt();
    }

    @Override
    public void run() {
        super.run();
        this.init();    // Always readable.
    }

    @Override
    public void processData() {
        out.content = isOn ? 1 : 0;
    }

    public void switchIt(){
        isOn = !isOn;
        unitUI.setText(isOn ? "1" : "0");
    }
}
