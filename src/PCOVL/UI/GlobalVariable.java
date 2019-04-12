/*
 *
 * GlobalVariable.java
 * GraduationProject
 *
 * Created by X on 2019/4/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import PCOVL.UnitRepository.Controller;
import PCOVL.UnitRepository.SuperUnit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GlobalVariable {
    // HomePage
    public static JPanel unitPanel, workPanel;

    // UnitPanel
    public static BaseUnitUI newUnitForWork;

    // workPanel
    public static JLabel lastOutLabel;
    public static BaseUnitUI draggingUnit;
    public static ArrayList<SuperUnit> unitArray = new ArrayList<>();
    public static ArrayList<BaseUnitUI> componentArray = new ArrayList<>();
    public static ArrayList<SuperUnit> unitToRun = new ArrayList<>();
    public static Line lastLine;
    enum DragState {
        init, forLink, forRelocate
    }
    static DragState dragState = DragState.init;

    // Constant.
    public static int unitWidth = 160;
    public static int unitHeight = 50;
    public static int actionWidth = 20;
    public static int actionHeight = 10;
    public static double lineWidth = 7.0;
    public static Color[] inColor = {new Color(239,154,154), new Color(229,115,115), new Color(239,83,80), new Color(244,67,54), new Color(229,57,53)};
    public static Color[] outColor = {new Color(84,226,229),new Color(0,212,218),new Color(0,201,210), new Color(0,191,205), new Color(0,175,187)};
    public static Color labelColor = Color.lightGray;

    public static SuperUnit programCounter;
    public static SuperUnit RAM;
    public static Controller controller;

    public static int[] Flag = new int[2];  // NegativeFlag, ZeroFlag
}
