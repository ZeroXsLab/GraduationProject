/*
 *
 * WorkingPanelDelegate.java
 * GraduationProject
 *
 * Created by X on 2019/3/23
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WorkingPanelDelegate implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Enter" + e.getPoint());
        if (GlobalVariable.draggingUnit != null) {
            Point point =  e.getPoint();
            Component newCom = EventUtil.copyUnitByEvent(GlobalVariable.draggingUnit, false);
            newCom.setLocation(point);
            GlobalVariable.workingPanel.add(newCom);
            GlobalVariable.workingPanel.updateUI();
            GlobalVariable.newUnitForWork= newCom;
            GlobalVariable.draggingUnit = null;
        }
    }

}
// TODO Unit Linking