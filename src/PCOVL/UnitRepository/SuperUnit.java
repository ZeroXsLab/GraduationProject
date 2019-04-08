/*
 *
 * SuperUnit.java
 * GraduationProject
 *
 * Created by X on 2019/4/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.Line;

public class SuperUnit implements Runnable{
    // Store the data
    protected Data[] in;
    protected Data out = new Data();
    // the UI of unit, contains the inLabel, outLabel, mainLabel
    public BaseUnitUI unitUI;
    // Store the line linked with self.
    protected Line[] inLines;
    protected Line outLine;
    // Is the unit enable(can work, can be write)
    public Boolean inputEnable = true;

    // make the unit ready, so it can be read by others
    public void readyForRead() {
        for (int iIn = 0; iIn < in.length; iIn ++) {
            this.in[iIn].setBeenRead(false);
        }
    }

    // it's fine if just initial the inThings.
    public SuperUnit(int inCount, BaseUnitUI unitUI) {
        this.in = new Data[inCount];
        for (int iIn = 0; iIn < in.length; iIn ++) {
            this.in[iIn] = new Data();
        }
        this.unitUI = unitUI;
        this.inLines = new Line[inCount];
    }

    public Data getInAt(int index) {
        return in[index];
    }

    public void disconnectInAt(int index) {
        in[index] = new Data();
        inLines[index] = null;
    }

    public void setOut(Data out) {
        this.out = out;
    }

    public Line[] getInLines() {
        return inLines;
    }

    public void setInLines(Line line, int index) {
        this.inLines[index] = line;
    }

    public Line getOutLine() {
        return outLine;
    }

    public void setOutLine(Line line) {
        this.outLine = line;
    }

    public void finish() {
        for (int iIn = 0; iIn < in.length; iIn ++) {
            this.in[iIn].setBeenRead(true);
        }
        out.setBeenRead(true);
    }

    @Override
    public void run() {
        if (inputEnable) {
            for (int iIn = 0; iIn < in.length; iIn ++) {
                in[iIn].read(this.getClass().getName() + "\t@" + Integer.toHexString(this.hashCode()));
            }
            processData();
            // show the result.
            setLabel();
            out.write(this.getClass().getName() + "\t@" + Integer.toHexString(this.hashCode()));
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
