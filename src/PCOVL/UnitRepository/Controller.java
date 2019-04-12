/*
 *
 * Controller.java
 * GraduationProject
 *
 * Created by X on 2019/4/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.BaseUnitUI;

public class Controller extends SuperUnit {
    private int instruction;
    private int address;

    //State-00,IREnable-01,PCEnable-02,AccEnable-03,M[1:0]-04,Xsel-05,Ysel-06,Asel-07,Ren-08,Wen-09
    public static final int[][] signalTable = new int[][]{
            {0,1,1,0,2,1,0,0,1,0},
            {1,0,0,1,0,0,0,1,1,0},
            {1,0,0,0,0,0,0,1,0,1},
            {1,0,0,1,1,0,0,1,1,0},
            {1,0,0,1,3,0,0,1,1,0},
            {1,0,1,0,0,0,1,0,0,0},
            {1,0,0,0,0,0,1,0,0,0},
            {1,0,0,0,0,0,1,0,0,0},
            {1,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,1,1,1}};
    public static int[] signal = signalTable[9];    // default 0, set 9 to make all enable

    public Controller(BaseUnitUI unitUI) {
        super(1, unitUI);
    }

    @Override
    public void processData() {
        int data = in[0].content;
        String binaryString = DataUtil.getBinaryString(data,16);
        instruction = DataUtil.getInteger(binaryString.substring(0,4),2);
        address = DataUtil.getInteger(binaryString.substring(4),2);
        System.out.println(instruction + ":" + address);
    }

    public void generateSignal() {
        signal = signalTable[instruction + 1];
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
