/*
 *
 * RAM.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
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
    private int[] memory = {0,10,20,30};
    // whether is to write
    private boolean isWrite = false;

    public RAM(BaseUnitUI unitUI) {
        super(2, unitUI);
    }

    @Override
    public void run() {
        // override the run() to make it can work without the data2Write ready.
        if (shouldGetSpecificIn()){// Not this equal, fix later
            // when this instruction is going to Read data instead of Write data
            isWrite = false;
            inToRun = new int[1];
            // Only read the In[1]
            inToRun[0] = 1;
            // is going to Read data, need set out.
            needSetOut = true;
        } else {
            isWrite = true;
            inToRun = null;
            // is going to Write data, do NOT set out.
            needSetOut = false;
        }
        super.run();
    }

    @Override
    public void processData() {
        data2Write = in[0].content;
        address = in[1].content;
        if (isWrite) {
            memory[address] = data2Write;
            setOutContent(memory[address]);
        } else {
            setOutContent(memory[address]);
        }
    }

    @Override
    public void setLabel() {
        if (isWrite) {
            // Write data, show all the data of memory
            String string = ""; // set string = "<html><body>"+strMsg1+"<br>"+strMsg2+"<body></html>" to break the line.
            for (int iMem = 0; iMem < memory.length; iMem ++) {
                string += memory[iMem] + "-";
            }
            string = string.substring(0, string.length() - 1);
            unitUI.setText(string);
        } else {
            // Read data, show the data we read.
            super.setLabel();
        }
    }
}
