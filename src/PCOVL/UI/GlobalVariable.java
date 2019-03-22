/*
 *
 * GlobalVariable.java
 * GraduationProject
 *
 * Created by X on 2019/3/23
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;
import java.awt.*;

public class GlobalVariable {
    // HomePage
    public static JPanel unitPanel,workingPanel;
    public static JLabel lastPress;
    public static int unitWidth = 100;
    public static int unitHeight = 50;
    public static int actionWidth = 10;
    public static int actionHeight = 10;
    public static Color[] actionColor = {Color.CYAN,Color.green,Color.red, Color.blue, Color.DARK_GRAY, Color.MAGENTA};
    public static Color labelColor = Color.lightGray;

    // UnitListenDelegate
    enum DragState {
        init, forLink, forRelocate, forCreate
    }
    static DragState dragState = DragState.init;
    public static Component draggingUnit, newUnitForWork;
}
