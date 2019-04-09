/*
 *
 * LineDelegate.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class LineDelegate implements MouseListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Line line = (Line) e.getComponent();
            // Disconnect the unit
            line.originUnit.clearOut(line.destination.getInAt(line.destIndex));
            line.destination.disconnectInAt(line.destIndex);
            // remove the line from the workPanel
            GlobalVariable.workPanel.remove(line);
            GlobalVariable.workPanel.updateUI();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double newY = e.getY();
        int height = e.getComponent().getHeight();
        Line line = (Line) e.getComponent();
        line.ratio = newY / height;
        line.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
