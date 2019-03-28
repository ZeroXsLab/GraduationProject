/*
 *
 * SuperUnit.java
 * GraduationProject
 *
 * Created by X on 2019/3/28
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

import PCOVL.UI.BaseUnitUI;

import java.awt.*;

public class SuperUnit implements Runnable{
    protected Data[] in;
    protected Data out = new Data();
    public Boolean inputEnable = true;
    protected BaseUnitUI unitUI;
    private int ID;

    public void init() {
//        this.in.setBeenRead(false);
        for (int iIn = 0; iIn < in.length; iIn ++) {
            this.in[iIn].setBeenRead(false);
        }
    }

    public SuperUnit(int ID, int inCount, BaseUnitUI unitUI) {
        this.ID = ID;
        this.in = new Data[inCount];
        this.unitUI = unitUI;
        for (int iIn = 0; iIn < in.length; iIn ++) {
            this.in[iIn] = new Data();
        }
    }

    public Data getInAt(int index) {
        return in[index];
    }

    public void setOut(Data out) {
        this.out = out;
    }

    public void finish() {
//        in.setBeenRead(true);
        for (int iIn = 0; iIn < in.length; iIn ++) {
            this.in[iIn].setBeenRead(true);
        }
        out.setBeenRead(true);
    }

    @Override
    public void run() {
        if (inputEnable) {
//            in.read(this.getClass().getName() + " " + ID);
            for (int iIn = 0; iIn < in.length; iIn ++) {
                in[iIn].read(this.getClass().getName() + " " + ID);
            }
            processData();
            out.write(this.getClass().getName() + " " + ID);
        } else {
            // Do nothing
        }
    }

    // When a Unit extends, it should override this method to custom the function
    public void processData() {
        out.content = in[0].content + 1;
    }

    public void setLabel() {
        unitUI.setText("" + out.content);
    }
}

// TODO: it contain a UI variable, so we can call function to link it with the exist UI.
