/*
 *
 * Line.java
 * GraduationProject
 *
 * Created by X on 2019/4/4
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Line extends JButton {

    // Save the JButton shape for action of Click.(which will call contains()
    private Shape shape;
    private double lineWidth = 4.0;
    // Save the original Point for use of update one side.
    private Point startPoint, endPoint;
    // the origin point in the parent, for setLocation.
    Point origin;

    public Line(Point fromBelow, Point toUpper) {
        super();
        startPoint = fromBelow;
        endPoint = toUpper;
        Dimension size = format(fromBelow, toUpper);
        setPreferredSize(size);
        setSize(size);
        // Not going to draw square background, so it can look like other shape we want.
        setContentAreaFilled(false);
        // Not going to drawBorder, it's useless if it can not the same color to the background panel.
        setBorderPainted(false);
        setLocation(origin);
    }

    // create the shape we want.
    public Shape calcArea(){
        Rectangle2D horizontal = new Rectangle2D.Double(1,(getHeight() - lineWidth )/2,getWidth() - 2,lineWidth);
        Rectangle2D bottom;
        Rectangle2D top;
        if (origin.x < startPoint.x) {
            // topLeft, bottomRight     ↓→↓
            bottom = new Rectangle2D.Double(getWidth() - 1 - lineWidth,horizontal.getCenterY(),lineWidth,getHeight() - horizontal.getCenterY() - 2);
            top = new Rectangle2D.Double(horizontal.getX(),1,lineWidth,horizontal.getCenterY());
        } else {
            // topRight, bottomLeft     ↓←↓
            bottom = new Rectangle2D.Double(horizontal.getX(),horizontal.getCenterY(),lineWidth,getHeight() - horizontal.getCenterY() - 2);
            top = new Rectangle2D.Double(getWidth() - 1 - lineWidth,1,lineWidth,horizontal.getCenterY());
        }
        // combine the shape
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
            //if click at the button
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.GRAY);
        }
        super.paintComponent(g);
        // translate into Graphics2D and fill the area we want.
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.fill(calcArea());
    }

    // this func will be call to define whether mouse at the button, remember to override it after change the shape.
    @Override
    public boolean contains(int x, int y) {
        // if the button border or location change, create a new one.
        if ((shape == null) || (!shape.getBounds().equals(getBounds()))) {
            shape = calcArea();
        }
        return shape.contains(x, y);
    }

    public void updateEndPoint(Point end) {
        // update size and origin
        Dimension size = format(startPoint, end);
        this.setSize(size);
        setLocation(origin);
        // save the new endPoint.
        this.endPoint = end;
    }

    public void updateStartPoint(Point start) {
        // update size and origin
        Dimension size = format(start, endPoint);
        this.setSize(size);
        setLocation(origin);
        // save the new startPoint.
        this.startPoint = start;
    }

    // calculate the size base on the two point.
    public Dimension format(Point fromBelow, Point toUpper) {
        int width;
        if (fromBelow.x > toUpper.x) {
            // topLeft, bottomRight     ↑←↑
            width = fromBelow.x - toUpper.x;
            origin = toUpper;
        } else {
            // topRight, bottomLeft     ↑→↑
            width = toUpper.x - fromBelow.x;
            origin = new Point(fromBelow.x, toUpper.y);
        }
        // set the width higher than lineWidth.
        if (width < lineWidth) {
            width = (int) lineWidth;
        }
        int height = fromBelow.y - toUpper.y;
        // set the height higher than lineWidth.
        if (height < lineWidth) {
            height = (int)lineWidth;
        }
        Dimension size = new Dimension();
        size.width = width;
        size.height = height;
        return size;
    }
}
// FIXME make the line can link from top to low(should change the mouseDrag func in workPanel)