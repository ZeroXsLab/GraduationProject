/*
 *
 * myMouseDelegate.java
 * GraduationProject
 *
 * Created by X on 2019/4/1
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class myMouseDelegate implements MouseMotionListener, MouseListener {
    Frame frame = new Frame("关于鼠标的多重监听器");
    TextField tField = new TextField(30);

    public myMouseDelegate() {
        Label label = new Label("请按下鼠标左键并拖动");
        frame.add(label, "North");
        frame.add(tField, "South");
        frame.setBackground(new Color(180, 255, 255));
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });
        frame.setSize(500, 500);
        frame.setLocation(400, 250);

        Point start = new Point(10,50);
        Point end = new Point(50,10);
        linkLine line = new linkLine(start, end);
        frame.add(line);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new myMouseDelegate();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.print("鼠标点击----" + "\t");
        if (e.getClickCount() == 1) {
            System.out.println("单击！");
            e.getComponent().getGraphics().drawLine(0,0,e.getX(),e.getY());
        } else if (e.getClickCount() == 2) {
            System.out.println("双击！");
            e.getComponent().repaint(); // this will clear all the line as it's not belong to the component.
        } else if (e.getClickCount() == 3) {
            System.out.println("三连击！！");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("鼠标按下");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("鼠标松开");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        tField.setText("鼠标已经进入窗体");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        tField.setText("鼠标已经移出窗体");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        String string = "鼠标拖动到位置：（" + e.getX() + "，" + e.getY() + "）";
        tField.setText(string);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}

//class linkLine extends JLabel {
//
//    public linkLine(Point start, Point end) {
//        super();
//        this.setText("Wakaka");
//        Dimension size = getPreferredSize();
//        size.height = (int) (start.getY() - end.getY());
//        size.width = (int) (start.getX() - end.getX());
//        setPreferredSize(size);
//        this.setOpaque(true);
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        g.drawLine(getX(),0,0,getY());
//        g.setColor(Color.RED);
//        super.paintComponent(g);
//    }
//}
