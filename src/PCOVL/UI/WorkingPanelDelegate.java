/*
 *
 * WorkingPanelDelegate.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import PCOVL.UnitRepository.SuperUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class WorkingPanelDelegate implements MouseListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            // Double Click, do remove action
            JPanel workPanel = GlobalVariable.workPanel;
            if (e.getComponent() instanceof BaseUnitUI) {
                // you double click at a BaseUnit
                BaseUnitUI baseUnitUI = (BaseUnitUI)e.getComponent();
                int index = GlobalVariable.componentArray.indexOf(baseUnitUI);
                SuperUnit unit = GlobalVariable.unitArray.get(index);
                // remove UI
                if (unit.getOutLine() != null) {
                    workPanel.remove(unit.getOutLine());
                }
                for (int iIn = 0; iIn < unit.getInLines().length; iIn ++) {
                    if (unit.getInLines()[iIn] != null) {
                        workPanel.remove(unit.getInLines()[iIn]);
                    }
                }
                workPanel.remove(baseUnitUI);
                workPanel.updateUI();
                // remove Logi
                GlobalVariable.unitArray.remove(index);
                GlobalVariable.componentArray.remove(index);
            }
        }
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
            // if you are dragging a unit into WorkPanel from the unitPanel
            Point point =  e.getPoint();
            Component newCom = EventUtil.copyUnitFrom(GlobalVariable.draggingUnit, false);
            newCom.setLocation(point);
            GlobalVariable.workPanel.add(newCom);
            GlobalVariable.workPanel.updateUI();
            // Store the unitOfWorkPanel for future handle(Add listener if release at workPanel, or remove if not)
            GlobalVariable.newUnitForWork= (BaseUnitUI) newCom;
            // Clear the draggingUnit.
            GlobalVariable.draggingUnit = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // e.getComponent is a BaseUnitUI.
        switch (GlobalVariable.dragState) {
            case init:
                Point abPoint = EventUtil.getAbsolutePointBy(e);
                // the component that the mouse at (as it's not e.getComponent if you move far away.
                Component componentOfMouse = e.getComponent().getParent().getComponentAt(abPoint);
                if (componentOfMouse instanceof BaseUnitUI) {
                    if (!(EventUtil.isPointInComponents(e, "In"))
                            && !(EventUtil.isPointInComponents(e, "Out"))) {
                        // You are attempt to relocate the Unit
                        e.getComponent().setLocation(abPoint);
                        GlobalVariable.dragState = GlobalVariable.DragState.forRelocate;
                    } else {
                        // You drag a In or Out, so it is to Link Unit
                        GlobalVariable.dragState = GlobalVariable.DragState.forLink;
                        // get the line you create during mousePress and update its endPoint
                        Line line = GlobalVariable.lastLine;
                        if (line != null) {
                            line.updateEndPoint(abPoint);
                            GlobalVariable.workPanel.updateUI();
                        }
                    }
                }
                break;
            case forRelocate:
                // if it is called during dragging label around
                Point newLoc = EventUtil.getAbsolutePointBy(e);
                e.getComponent().setLocation(newLoc);
                // get the unit which this UI belong to.
                int unitIndex = GlobalVariable.componentArray.indexOf(e.getComponent());
                SuperUnit unit = GlobalVariable.unitArray.get(unitIndex);
                // update the line linked with the unit you want to relocate.
                if (unit.getOutLine() != null) {
                    // get the label center location
                    Point origin = unit.unitUI.getComponent(unit.getInLines().length).getLocation();
                    origin = EventUtil.transformToSuperLoca(origin, unit.unitUI);
                    origin.x += GlobalVariable.actionWidth / 2;
                    unit.getOutLine().updateStartPoint(origin);
                }
                for (int iIn = 0; iIn < unit.getInLines().length; iIn ++) {
                    if (unit.getInLines()[iIn] != null) {
                        // get the label center location
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
                if (line != null) {
                    line.updateEndPoint(endPoint);
                    GlobalVariable.workPanel.updateUI();
                }
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (EventUtil.isPointInComponents(e, "Out")) {
            // Record the OutLabel you click.
            GlobalVariable.lastOutLabel = (JLabel) e.getComponent().getComponentAt(e.getPoint());
            // Create a new line for further use.
            Point labelOrigin = GlobalVariable.lastOutLabel.getLocation();
            labelOrigin = EventUtil.transformToSuperLoca(labelOrigin, GlobalVariable.lastOutLabel.getParent());
            labelOrigin.x += GlobalVariable.actionWidth / 2;
            labelOrigin.y += GlobalVariable.actionHeight / 4;
            Line line = new Line(labelOrigin,labelOrigin);
            GlobalVariable.workPanel.add(line);
            GlobalVariable.workPanel.updateUI();
            GlobalVariable.lastLine = line;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point abPoint = EventUtil.getAbsolutePointBy(e);
        // componentOfMouse is a BaseUnitUI which the mouse in(as it's not e.getComponent if you move far away.
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
                Line line = above.getInLines()[index];
                Point origin = above.unitUI.getComponent(index).getLocation();
                origin = EventUtil.transformToSuperLoca(origin, above.unitUI);
                origin.x += GlobalVariable.actionWidth / 2;
                line.updateEndPoint(origin);
                // save the destination of the line
                line.destination = above;
                line.originUnit = below;
                line.destIndex = index;
            }
        } else if (GlobalVariable.lastLine != null && GlobalVariable.dragState == GlobalVariable.DragState.forLink) {
            // you are going to cancel the link action, clear the line that just created
            Line line = GlobalVariable.lastLine;
            GlobalVariable.workPanel.remove(line);
            GlobalVariable.workPanel.updateUI();
            GlobalVariable.lastLine = null;
        }
        // Clear the Out Record.
        GlobalVariable.lastOutLabel = null;
        GlobalVariable.dragState = GlobalVariable.DragState.init;
    }

}
// FIXME MouseDrag: maybe we can make it not relocate the origin to the mouse but where we click