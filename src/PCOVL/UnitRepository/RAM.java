/*
 *
 * RAM.java
 * GraduationProject
 *
 * Created by X on 2019/5/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.GlobalVariable;

import java.awt.*;

// when process data, we can ignore outData(In 0) status for some situation
public class RAM extends SuperUnit {
    private int data2Write;
    private int address;
    // the right actionLabel is the outData.

    // the memory data
    private int[] memory;
    // whether is to write
    private boolean isWrite = false;
    private int dataIndex = 0;

    public RAM(BaseUnitUI unitUI) {
        super(2, unitUI);
    }

    @Override
    public void run() {
        if (isControllerInChanrge()) {
            dataIndex = 12;
            isWrite = Controller.signal[8] == 0;    // This is OK when it can not be read and write in one instruction.
        } else {
            // In User Control State
            isWrite = !isTheControlInNotLink();     // when the Control In is Linked, it is to write.
        }
        // override the run() to make it can work without the data2Write ready.
        if (!isWrite){
            // when this instruction is going to Read data instead of Write data
            inToRun = new int[1];
            // Only read the In[1]
            inToRun[0] = 1;
            // is going to Read data, need set out.
            needSetOut = true;
        } else {
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
        String binaryString = DataUtil.getBinaryString(address,16);
        address = DataUtil.getInteger(binaryString.substring(4),2);
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
            String string = "Data "; // set string = "<html><body>"+strMsg1+"<br>"+strMsg2+"<body></html>" to break the line.
            for (int iMem = dataIndex; iMem < memory.length; iMem ++) {
                string += memory[iMem] + ":";
            }
            string = string.substring(0, string.length() - 1);
            unitUI.setText(string);
        } else {
            // Read data, show the data we read.
            super.setLabel();
        }
    }

    public void setMemory(int[] memory) {
        this.memory = memory;
    }
}
