/*
 *
 * Controller.java
 * GraduationProject
 *
 * Created by X on 2019/4/21
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
            {1,        0,          0,          1,           0,       -1,       0,     1,      1,     0},
            {1,        0,          0,          0,          -1,        0,      -1,     1,      0,     1},
            {1,        0,          0,          1,           1,        0,       0,     1,      1,     0},
            {1,        0,          0,          1,           3,        0,       0,     1,      1,     0},
            {1,        0,          1,          0,           0,       -1,       1,    -1,      0,     0},
            {1,        0,          0,          0,           0,       -1,       1,    -1,      0,     0},
            {1,        0,          0,          0,           0,       -1,       1,    -1,      0,     0},
            {1,        0,          0,          0,          -1,       -1,      -1,    -1,      0,     0},
            {-1,       1,          1,          1,           1,        1,       1,     1,      1,     1}};
            // -1 -> Special Signal State for All Unit Enable.
    public static int[] signal = signalTable[9];    // default 0, set 9 to make all enable

    public Controller(BaseUnitUI unitUI) {
        super(1, unitUI);
        bits = 4;
    }

    @Override
    public void processData() {
        int data = in[0].content;
        String binaryString = DataUtil.getBinaryString(data,16);
        instruction = DataUtil.getInteger(binaryString.substring(0,4),2);
        address = DataUtil.getInteger(binaryString.substring(4),2);
        System.out.println(instruction + ":" + address);
        setOutContent(instruction);
        if (signal[0] == 0) {
            addition = "Fetch Instruction";
        } else {
            addition = insInfo[instruction] + " [" + binaryString.substring(4) + "]";
        }
    }

    public void generateSignal() {
        signal = signalTable[instruction + 1];
        if (instruction + 1 == 3) {
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
