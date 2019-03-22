/*
 *
 * UnitPanelDelegate.java
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
import java.awt.event.MouseMotionListener;

public class UnitPanelDelegate implements MouseListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // if press at a unit, create a new one to show when you drag the previous one around.
        Point point = e.getComponent().getLocation();
        Component newCom = EventUtil.copyUnitByEvent(e.getComponent(), true);
        newCom.setLocation(point);
        GlobalVariable.unitPanel.add(newCom);
        GlobalVariable.unitPanel.updateUI();
        GlobalVariable.draggingUnit = newCom;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Check: if NOT in workingPanel, remove it.
        Point abPoint = EventUtil.getAbsolutePointBy(e);
        Point pointToWorking = EventUtil.transformToRelative(abPoint, GlobalVariable.workingPanel);
        if (!GlobalVariable.workingPanel.contains(pointToWorking)){
            GlobalVariable.unitPanel.remove(e.getComponent());
            GlobalVariable.draggingUnit = null;
            GlobalVariable.unitPanel.updateUI();
            if (GlobalVariable.newUnitForWork != null) {
                GlobalVariable.workingPanel.remove(GlobalVariable.newUnitForWork);
                GlobalVariable.workingPanel.updateUI();
            }
        } else {
            // release in the workingPanel, create Logi Class.
        }
        GlobalVariable.newUnitForWork = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Relocate it around
        Component component = e.getComponent();
        Point newLoca = EventUtil.getAbsolutePointBy(e);
        component.setLocation(newLoca);
        if (GlobalVariable.newUnitForWork != null) {
            GlobalVariable.newUnitForWork.setLocation(EventUtil.transformToRelative(newLoca, GlobalVariable.workingPanel));
        }
    }

}
/* TODO:
    SHIT it call MouseEnter when move out from a unit of the Container!!
 */