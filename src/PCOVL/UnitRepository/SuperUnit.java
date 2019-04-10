/*
 *
 * SuperUnit.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.Line;

public class SuperUnit implements Runnable{
    private int outDataCount = 3;
    // Store the data
    protected Data[] in;
    protected Data[] out = new Data[outDataCount];
    // the UI of unit, contains the inLabel, outLabel, mainLabel
    public BaseUnitUI unitUI;
    // Store the line linked with self.
    protected Line[] inLines;
    protected Line[] outLine = new Line[outDataCount];
    // Is the unit enable(can work, can be write)
    public Boolean inputEnable = true;
    protected int bits = 16;

    int[] inToRun = null;
    boolean needSetOut = true;

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
        // make that always at least one out available
        this.out[this.out.length - 1] = new Data();
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

    public void clearOut(Data out) {
        // when disconnect, always set to null begin from the first one
        for (int iOut = 0; iOut < this.out.length - 1; iOut ++) {
            if (this.out[iOut] == out) {
                this.out[iOut] = this.out[0];
                this.out[0] = null;
            }
        }
    }

    public void setOut(Data out) {
        for (int iOut = 0; iOut < this.out.length; iOut ++) {
            if ((this.out[iOut] == null) || (iOut == this.out.length - 1)) {
                // search for the first null Out, if all exist, replace the last Out.
                this.out[iOut] = out;
                break;
            }
        }
    }

    public Line[] getInLines() {
        return inLines;
    }

    public void setInLines(Line line, int index) {
        this.inLines[index] = line;
    }

    public Line[] getOutLine() {
        return outLine;
    }

    public void setOutLine(Line line, Data belongTo) {
        int index = 0;
        for (; index < this.out.length - 1; index ++) {
            if (belongTo == this.out[index]) {
                // search the index this line belong to
                break;
            }
        }
        this.outLine[index] = line;
    }

    public void finish() {
        for (int iIn = 0; iIn < in.length; iIn ++) {
            this.in[iIn].setBeenRead(true);
        }
        for (int iOut = 0; iOut < this.out.length; iOut ++) {
            if (this.out[iOut] != null) {
                this.out[iOut].setBeenRead(true);
            }
        }
    }

    @Override
    public void run() {
        if (inputEnable) {
            if (inToRun == null) {
                for (int iIn = 0; iIn < in.length; iIn ++) {
                    in[iIn].read(this.getClass().getName() + "\t@" + Integer.toHexString(this.hashCode()));
                }
            } else {
                // only read specific In
                int index;
                for (int iInTo = 0; iInTo < inToRun.length; iInTo ++) {
                    index = inToRun[iInTo];
                    in[index].read(this.getClass().getName() + "\t@" + Integer.toHexString(this.hashCode()));
                }
            }
            processData();
            // show the result.
            setLabel();
            if (needSetOut) {
                for (int iOut = 0; iOut < this.out.length; iOut ++) {
                    if (this.out[iOut] != null) {
                        this.out[iOut].write(this.getClass().getName() + "\t@" + Integer.toHexString(this.hashCode()));
                    }
                }
            }
        } else {
            // Do nothing
        }
    }

    // When a Unit extends, it should override this method to custom the function
    public void processData() {
        // Abstract Unit do nothing to the data.
        ;
    }

    public void setLabel() {
        int content = 0;
        for (int iOut = 0; iOut < this.out.length; iOut ++) {
            if (this.out[iOut] != null) {
                // get the first notNULL content, always get one as the last one has been init.
                content = this.out[iOut].content;
                break;
            }
        }
        unitUI.setText(unitUI.getName() + ":" + DataUtil.getBinaryString(content, bits));
    }

    public boolean shouldGetSpecificIn() {
        boolean forInLine0 = inLines[0] == null;
        return forInLine0;
    }

    // make the out always be the same content.
    public void setOutContent(int content) {
        for (int iOut = 0; iOut < this.out.length; iOut ++) {
            if (this.out[iOut] != null) {
                this.out[iOut].content = content;
            }
        }
    }
}
