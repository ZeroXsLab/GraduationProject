/*
 *
 * RAM.java
 * GraduationProject
 *
 * Created by X on 2019/4/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

// when process data, we can ignore outData(In 0) status for some situation
public class RAM extends SuperUnit {
    private int data2Write;
    private int address;
    // the right actionLabel is the outData.

    // the memory data
    private int[] memory = {20,40,60,80};
    // whether is to write
    private boolean isWrite = false;

    public RAM(BaseUnitUI unitUI) {
        super(2, unitUI);
    }

    @Override
    public void run() {
        // override the run() to make it can work without the data2Write ready.
        if (inLines[0] == null){// Not this equal, fix later
            // when this instruction is going to Read data instead of Write data
            isWrite = false;
            if (inputEnable) {
                in[1].read(this.getClass().getName() + "\t@" + Integer.toHexString(this.hashCode()));
                processData();
                setLabel();
            }
        } else {
            isWrite = true;
            super.run();
        }
    }

    @Override
    public void processData() {
        data2Write = in[0].content;
        address = in[1].content;
//        int index;
        if (isWrite) {
//            index = data2Write + address;
            memory[address] = data2Write;
            out.content = memory[address];
        } else {
//            index = 3;
            out.content = memory[address];
        }
//        out.content = memory[index];
    }

    @Override
    public void setLabel() {
        if (isWrite) {
            // Write data, show all the data of memory
            String string = ""; // set string = "<html><body>"+strMsg1+"<br>"+strMsg2+"<body></html>" to break the line.
            for (int iMem = 0; iMem < memory.length; iMem ++) {
                string += memory[iMem] + ":";
            }
            unitUI.setText(string);
        } else {
            // Read data, show the data we read.
            super.setLabel();
        }
    }
}
