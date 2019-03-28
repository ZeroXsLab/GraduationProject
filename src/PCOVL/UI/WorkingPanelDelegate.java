/*
 *
 * WorkingPanelDelegate.java
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class WorkingPanelDelegate implements MouseListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Detect mouse to create Unit when you drag it from UnitPanel
        if (GlobalVariable.draggingUnit != null) {
            Point point =  e.getPoint();
            Component newCom = EventUtil.copyUnitByEvent(GlobalVariable.draggingUnit, false);
            newCom.setLocation(point);
            GlobalVariable.workingPanel.add(newCom);
            GlobalVariable.workingPanel.updateUI();
            GlobalVariable.newUnitForWork= (BaseUnitUI) newCom;
            GlobalVariable.draggingUnit = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // e.getComponent is a BaseUnit.
        switch (GlobalVariable.dragState) {
            case init:
                Point abPoint = EventUtil.getAbsolutePointBy(e);
                Component componentOfMouse = e.getComponent().getParent().getComponentAt(abPoint);
//                Component eventComponent = e.getComponent();
//                Component subComponent = componentOfMouse.getComponentAt(EventUtil.transformToRelative(abPoint,componentOfMouse));
                if (componentOfMouse instanceof BaseUnitUI) {
                    if (!(EventUtil.isPointInComponents(e, "In")) && !(EventUtil.isPointInComponents(e, "Out"))) {
                        // You are attempt to relocate the Unit
                        Point newLoc = abPoint;
                        e.getComponent().setLocation(newLoc);
                        GlobalVariable.dragState = GlobalVariable.DragState.forRelocate;
                    } else {
                        // You drag a In or Out, so it is to Link Unit
                        GlobalVariable.dragState = GlobalVariable.DragState.forLink;
                        Point startPoint = GlobalVariable.lastOutLabel.getLocation();
                        Point endPoint = abPoint;
                    }
                }
                break;
            case forRelocate:
                // if it is called during dragging label around
                Point newLoc = EventUtil.getAbsolutePointBy(e);
                e.getComponent().setLocation(newLoc);
                break;
            case forLink:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (EventUtil.isPointInComponents(e, "Out")) {
            // Record the OutLabel you click.
            GlobalVariable.lastOutLabel = (JLabel) e.getComponent().getComponentAt(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point abPoint = EventUtil.getAbsolutePointBy(e);
//        Component eventComponent = e.getComponent();
        Component componentOfMouse = e.getComponent().getParent().getComponentAt(abPoint);
        Component subComponent = componentOfMouse.getComponentAt(EventUtil.transformToRelative(abPoint, componentOfMouse));
        if (EventUtil.isPointInComponents(e, "In")) {
            JLabel lastOutLabel = GlobalVariable.lastOutLabel;
            if (lastOutLabel != null) {
                // You press at a Out before you release.
                subComponent.setBackground(lastOutLabel.getBackground());
                // Link the unit
                int aboveIndex = GlobalVariable.componentArray.indexOf(componentOfMouse);
                SuperUnit above = GlobalVariable.unitArray.get(aboveIndex);
                int belowIndex = GlobalVariable.componentArray.indexOf(lastOutLabel.getParent());
                SuperUnit below = GlobalVariable.unitArray.get(belowIndex);
                String indexString = subComponent.getName().substring(3);
                int index = Integer.parseInt(indexString);
                below.setOut(above.getInAt(index));
            }
        }
        // Clear the Out Record.
        GlobalVariable.lastOutLabel = null;
        GlobalVariable.dragState = GlobalVariable.DragState.init;
    }

}
// TODO Unit Linking(Mouse Drag and Release)