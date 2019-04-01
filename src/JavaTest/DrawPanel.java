/*
 *
 * DrawPanel.java
 * GraduationProject
 *
 * Created by X on 2019/4/1
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class DrawPanel extends JPanel {


    public static void main(String[] args) {
        JFrame frame = new JFrame("画板");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        DrawPanel contentPanel = new DrawPanel();
        frame.setContentPane(contentPanel);
        frame.setVisible(true);
    }

    public DrawPanel() {
        this.setBackground(Color.CYAN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        graphics2D.setStroke(stroke);
        Line2D line2D = new Line2D.Double(30,50,100,20);
        graphics2D.draw(line2D);
//        g.setColor(Color.red);
//        g.fillOval(0,0,getSize().width - 1, getSize().height - 1);
    }
}
