/*
 *
 * Line.java
 * GraduationProject
 *
 * Created by X on 2019/4/2
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Line extends JButton {

    private Shape shape; //用于保存按钮的形状，有助于侦听单击按钮事件
    private double lineWidth = 4.0;
    private Point startPoint, endPoint;
    Point origin;
//    Point refineOrigin = new Point();

    public Line(Point fromBelow, Point toUpper) {
        super();
        startPoint = fromBelow;
        endPoint = toUpper;
        Dimension size = format(fromBelow, toUpper);
        setPreferredSize(size);
        setSize(size);
        //使JButton不画背景，即不显示方形背景
        setContentAreaFilled(false);
        setBorderPainted(false);
//        setLocation(refineOrigin);
        setLocation(origin);
    }

    public Shape calcArea(){
        Rectangle2D horizontal = new Rectangle2D.Double(1,(getHeight() - lineWidth )/2,getWidth() - 2,lineWidth);
        Rectangle2D bottom;
        Rectangle2D top;
        if (origin.x < startPoint.x) {
            // ↓→↓
            bottom = new Rectangle2D.Double(getWidth() - 1 - lineWidth,horizontal.getCenterY(),lineWidth,getHeight() - horizontal.getCenterY() - 2);
            top = new Rectangle2D.Double(horizontal.getX(),1,lineWidth,horizontal.getCenterY());
        } else {
            bottom = new Rectangle2D.Double(horizontal.getX(),horizontal.getCenterY(),lineWidth,getHeight() - horizontal.getCenterY() - 2);
            top = new Rectangle2D.Double(getWidth() - 1 - lineWidth,1,lineWidth,horizontal.getCenterY());
        }
        Area hori = new Area(horizontal);
        Area lb = new Area(bottom);
        Area rt = new Area(top);
        hori.add(lb);
        hori.add(rt);
        return hori;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            //getModel方法返回鼠标的模型ButtonModel
            //如果鼠标按下按钮，则ButtonModel的Armed属性为真
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.GRAY);
        }
        //调用父类的paintComponent画按钮的标签和焦点所在的小矩形
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.fill(calcArea());
    }

    @Override
    public boolean contains(int x, int y) {
        if ((shape == null) || (!shape.getBounds().equals(getBounds()))) {
            shape = calcArea();
        }
        return shape.contains(x, y);
    }

    public void updateEndPoint(Point end) {
        // update size and origin
        Dimension size = format(startPoint, end);
        this.setSize(size);
//        this.setLocation(refineOrigin);
        setLocation(origin);
        this.endPoint = end;
    }

    public void updateStartPoint(Point start) {
        Dimension size = format(start, endPoint);
        this.setSize(size);
//        this.setLocation(refineOrigin);
        setLocation(origin);
        this.startPoint = start;
    }

    public Dimension format(Point fromBelow, Point toUpper) {
        int offset = (int)(lineWidth/2);
        int width;
        if (fromBelow.x > toUpper.x) {
            //End在Start左边
            width = fromBelow.x - toUpper.x;
            origin = toUpper;
//            refineOrigin.x = origin.x - (GlobalVariable.actionWidth - (int)lineWidth) / 2;
//            refineOrigin.y = origin.y + GlobalVariable.actionHeight / 2;
        } else {
            width = toUpper.x - fromBelow.x;
            origin = new Point(fromBelow.x, toUpper.y);
//            refineOrigin.x = origin.x + (GlobalVariable.actionWidth - (int)lineWidth) / 2;
//            refineOrigin.y = origin.y + GlobalVariable.actionHeight / 2;
        }
        if (width < lineWidth) {
            width = (int) lineWidth;
        }
        // make it can link from top to low
        int height = fromBelow.y - toUpper.y;
        if (height < lineWidth) {
            height = (int)lineWidth;
        }
        Dimension size = new Dimension();
        size.width = width;
        size.height = height;
        return size;
    }
}
