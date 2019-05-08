/*
 *
 * Controller.java
 * GraduationProject
 *
 * Created by X on 2019/5/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;
import PCOVL.UI.GlobalVariable;

public class Controller extends SuperUnit {
    private int instruction;
    private int address;

    String[] insInfo = {"LDA", "STA", "ADD", "SUB", "JMP", "JGE", "JNE", "STP"};

    public static final int[][] signalTable = new int[][]{
            //State-00,IREnable-01,PCEnable-02,AccEnable-03,M[1:0]-04,Xsel-05,Ysel-06,Asel-07,Ren-08,Wen-09
            {0,        1,          1,          0,           2,        1,      -1,     0,      1,     0},
            {1,        0,          0,          1,           0,       -1,       0,     1,      1,     0},    // LDA
            {1,        0,          0,          0,          -1,        0,      -1,     1,      0,     1},    // STA
            {1,        0,          0,          1,           1,        0,       0,     1,      1,     0},    // ADD
            {1,        0,          0,          1,           3,        0,       0,     1,      1,     0},    // SUB
            {1,        0,          1,          0,           0,       -1,       1,    -1,      0,     0},    // JMP
            {1,        0,          0,          0,           0,       -1,       1,    -1,      0,     0},    // JGE
            {1,        0,          0,          0,           0,       -1,       1,    -1,      0,     0},    // JNE
            {1,        0,          0,          0,          -1,       -1,      -1,    -1,      0,     0},    // STP
            {-1,       1,          1,          1,           1,        1,       1,     1,      1,     1}};
            // -1 -> Special Signal State for All Unit Enable.
    public static int[] signal = signalTable[9];    // default 0, set 9 to make all enable
    public static int usualOrJGEorJNE = 0;

    public Controller(BaseUnitUI unitUI) {
        super(1, unitUI);
        bits = 4;
    }

    @Override
    public void processData() {
        int data = in[0].content;
        String binaryString = DataUtil.getBinaryString(data,16);
        instruction = (DataUtil.getInteger(binaryString.substring(0,4),2) + 8) % 8;
        address = DataUtil.getInteger(binaryString.substring(4),2);
        System.out.println(instruction + ":" + address);
        setOutContent(instruction);
        if (signal[0] == 0) {
            addition = "Fetch Instruction";
        } else {
            addition = insInfo[instruction] + " [" + binaryString.substring(4) + "]";
        }
        if (instruction == 5) {
            usualOrJGEorJNE = 1;
        } else if (instruction == 6) {
            usualOrJGEorJNE = 2;
        } else {
            usualOrJGEorJNE = 0;
        }
    }

    public void generateSignal() {
        signal = new int[10];
        for (int iSig = 0; iSig < signalTable[0].length; iSig++) {
            signal[iSig] = signalTable[instruction + 1][iSig];
        }
        if (instruction + 1 == 3 || instruction + 1 == 4) {
            // Add Sub instruction, Acc should out put
            for (int i = 0; i < GlobalVariable.unitToRun.size() ; i++) {
                if (GlobalVariable.unitToRun.get(i) instanceof MUX2 && ((MUX2)GlobalVariable.unitToRun.get(i)).isDataOut) {
                    GlobalVariable.unitToRun.get(i).readyForRead();
                    break;
                }
            }
        }
        for (int i = 0; i < signal.length; i++) {
            System.out.print(signal[i] + "\t");
        }
        System.out.println();
        // On Debug, to stay in Fetching State.
//        if (signal[0] != 0) {
//            signal = signalTable[0];
//        }
//        System.out.println("After rejudge.");
//        for (int i = 0; i < signal.length; i++) {
//            System.out.print(signal[i] + "\t");
//        }
//        System.out.println();
    }
}
