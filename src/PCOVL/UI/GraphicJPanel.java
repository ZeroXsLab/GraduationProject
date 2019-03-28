/*
 *
 * GraphicJPanel.java
 * GraduationProject
 *
 * Created by X on 2019/3/28
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;
import java.awt.*;

public class GraphicJPanel extends JPanel {

    Graphics2D graphics2D;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics2D = (Graphics2D) g.create();
    }

    public void drawLine(Point start, Point end) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.RED);
        graphics2D.drawLine(start.x, start.y, end.x, end.y);
        updateUI();
    }
}
