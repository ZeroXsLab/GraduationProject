/*
 *
 * WorkingPanelDelegate.java
 * GraduationProject
 *
 * Created by X on 2019/4/2
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
                if (componentOfMouse instanceof BaseUnitUI) {
                    if (!(EventUtil.isPointInComponents(e, "In")) && !(EventUtil.isPointInComponents(e, "Out"))) {
                        // You are attempt to relocate the Unit
                        e.getComponent().setLocation(abPoint);
                        GlobalVariable.dragState = GlobalVariable.DragState.forRelocate;
                    } else {
                        // You drag a In or Out, so it is to Link Unit
                        GlobalVariable.dragState = GlobalVariable.DragState.forLink;
                        Line line = GlobalVariable.lastLine;
                        line.updateEndPoint(abPoint);
                        GlobalVariable.workingPanel.updateUI();
                    }
                }
                break;
            case forRelocate:
                // if it is called during dragging label around
                Point newLoc = EventUtil.getAbsolutePointBy(e);
                e.getComponent().setLocation(newLoc);
                int unitIndex = GlobalVariable.componentArray.indexOf(e.getComponent());
                SuperUnit unit = GlobalVariable.unitArray.get(unitIndex);
                // update the line linked with the unit you want to relocate.
                if (unit.getOutLine() != null) {
                    Point origin = unit.unitUI.getComponent(unit.getInLines().length).getLocation();
                    origin = EventUtil.transformToSuperLoca(origin, unit.unitUI);
                    origin.x += GlobalVariable.actionWidth / 2;
                    unit.getOutLine().updateStartPoint(origin);
                }
                for (int iIn = 0; iIn < unit.getInLines().length; iIn ++) {
                    if (unit.getInLines()[iIn] != null) {
                        Point origin = unit.unitUI.getComponent(iIn).getLocation();
                        origin = EventUtil.transformToSuperLoca(origin, unit.unitUI);
                        origin.x += GlobalVariable.actionWidth / 2;
                        unit.getInLines()[iIn].updateEndPoint(origin);
                    }
                }
                break;
            case forLink:
                Point endPoint = EventUtil.getAbsolutePointBy(e);
                Line line = GlobalVariable.lastLine;
                line.updateEndPoint(endPoint);
                GlobalVariable.workingPanel.updateUI();
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (EventUtil.isPointInComponents(e, "Out")) {
            // Record the OutLabel you click.
            GlobalVariable.lastOutLabel = (JLabel) e.getComponent().getComponentAt(e.getPoint());
            Point labelOrigin = GlobalVariable.lastOutLabel.getLocation();
            labelOrigin = EventUtil.transformToSuperLoca(labelOrigin, GlobalVariable.lastOutLabel.getParent());
            labelOrigin.x += GlobalVariable.actionWidth / 2;
            labelOrigin.y += GlobalVariable.actionHeight / 4;
            Line line = new Line(labelOrigin,labelOrigin);
            GlobalVariable.workingPanel.add(line);
            GlobalVariable.workingPanel.updateUI();
            GlobalVariable.lastLine = line;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point abPoint = EventUtil.getAbsolutePointBy(e);
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
                // Save the line in the Unit
                below.setOutLine(GlobalVariable.lastLine);
                above.setInLines(GlobalVariable.lastLine,index);
                // Refine the line location
                Point origin = above.unitUI.getComponent(index).getLocation();
                origin = EventUtil.transformToSuperLoca(origin, above.unitUI);
                origin.x += GlobalVariable.actionWidth / 2;
                above.getInLines()[index].updateEndPoint(origin);
            }
        } else if (GlobalVariable.lastLine != null && GlobalVariable.dragState == GlobalVariable.DragState.forLink) {
            Line line = GlobalVariable.lastLine;
            GlobalVariable.workingPanel.remove(line);
            GlobalVariable.workingPanel.updateUI();
            GlobalVariable.lastLine = null;
        }
        // Clear the Out Record.
        GlobalVariable.lastOutLabel = null;
        GlobalVariable.dragState = GlobalVariable.DragState.init;
    }

}