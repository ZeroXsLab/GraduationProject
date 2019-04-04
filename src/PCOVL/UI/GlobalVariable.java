/*
 *
 * GlobalVariable.java
 * GraduationProject
 *
 * Created by X on 2019/4/4
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

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
    public static Line lastLine;
    enum DragState {
        init, forLink, forRelocate
    }
    static DragState dragState = DragState.init;

    // Constant.
    public static int unitWidth = 100;
    public static int unitHeight = 50;
    public static int actionWidth = 10;
    public static int actionHeight = 10;
    public static Color[] actionColor = {Color.CYAN,Color.green,Color.red, Color.blue, Color.DARK_GRAY, Color.MAGENTA};
    public static Color labelColor = Color.lightGray;

}
