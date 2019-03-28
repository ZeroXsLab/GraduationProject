/*
 *
 * GlobalVariable.java
 * GraduationProject
 *
 * Created by X on 2019/3/28
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import PCOVL.SuperUnit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GlobalVariable {
    // HomePage
    public static JPanel unitPanel,workingPanel;
    public static JLabel lastOutLabel;
    public static int unitWidth = 100;
    public static int unitHeight = 50;
    public static int actionWidth = 10;
    public static int actionHeight = 10;
    public static Color[] actionColor = {Color.CYAN,Color.green,Color.red, Color.blue, Color.DARK_GRAY, Color.MAGENTA};
    public static Color labelColor = Color.lightGray;

    // UnitListenDelegate
    enum DragState {
        init, forLink, forRelocate
    }
    static DragState dragState = DragState.init;
    public static BaseUnitUI draggingUnit, newUnitForWork;

    public static Graphics2D linkLine;

    public static ArrayList<SuperUnit> unitArray = new ArrayList<>();
    public static ArrayList<BaseUnitUI> componentArray = new ArrayList<>();
}
