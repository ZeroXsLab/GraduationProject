/*
 *
 * UnitPanelDelegate.java
 * GraduationProject
 *
 * Created by X on 2019/4/4
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
        Component newCom = EventUtil.copyUnitFrom(e.getComponent(), true);
        newCom.setLocation(point);
        GlobalVariable.unitPanel.add(newCom);
        GlobalVariable.unitPanel.updateUI();
        GlobalVariable.draggingUnit = (BaseUnitUI) newCom;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Check: if NOT in workPanel, remove it.
        Point abPoint = EventUtil.getAbsolutePointBy(e);
        Point pointToWorking = EventUtil.transformToRelative(abPoint, GlobalVariable.workPanel);
        if (!GlobalVariable.workPanel.contains(pointToWorking)){
            // the mouse release at out of workPanel, clear what we create.
            GlobalVariable.unitPanel.remove(e.getComponent());
            GlobalVariable.draggingUnit = null;
            GlobalVariable.unitPanel.updateUI();
            // if the mouse enter the workPanel before it release, clear the newUnitForWork
            if (GlobalVariable.newUnitForWork != null) {
                GlobalVariable.workPanel.remove(GlobalVariable.newUnitForWork);
                GlobalVariable.workPanel.updateUI();
            }
        } else {
            // it release at the workPanel, add workPanelDelegate.
            WorkingPanelDelegate workingPanelDelegate = new WorkingPanelDelegate();
            GlobalVariable.newUnitForWork.addMouseListener(workingPanelDelegate);
            GlobalVariable.newUnitForWork.addMouseMotionListener(workingPanelDelegate);
            // release in the workPanel, create Logi Class.
            EventUtil.initLogi(GlobalVariable.newUnitForWork);
        }
        // it's done, clear the record.
        GlobalVariable.newUnitForWork = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Relocate the components(both in unitPanel and workPanel) around
        Component component = e.getComponent();
        Point newLoca = EventUtil.getAbsolutePointBy(e);
        component.setLocation(newLoca);
        if (GlobalVariable.newUnitForWork != null) {
            GlobalVariable.newUnitForWork.setLocation(EventUtil.transformToRelative(newLoca, GlobalVariable.workPanel));
        }
    }

}