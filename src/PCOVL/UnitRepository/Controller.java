/*
 *
 * Controller.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class Controller extends SuperUnit {
    private int instruction;
    private int address;

    //State-00,IREnable-01,PCEnable-02,AccEnable-03,M[1:0]-04,Xsel-05,Ysel-06,Asel-07,Ren-08,Wen-09
    private static final int[][] signalTable = new int[][]{
            {0,1,1,0,2,1,0,0,1,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0}};
    public static int[] signal = signalTable[0];

    public Controller(BaseUnitUI unitUI) {
        super(1, unitUI);
    }

    @Override
    public void processData() {
        int data = in[0].content;
        String binaryString = DataUtil.getBinaryString(data,16);
        instruction = DataUtil.getInteger(binaryString.substring(0,4),2);
        address = DataUtil.getInteger(binaryString.substring(4),2);
    }

    public void generateSignal() {
        switch (instruction) {
            case 0:
                // LDA S    Acc := [S]
                signal = signalTable[1];
                break;
                default:
                    break;
        }
    }
}
