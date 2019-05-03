/*
 *
 * Line.java
 * GraduationProject
 *
 * Created by X on 2019/5/4
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import PCOVL.UnitRepository.SuperUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Line extends JButton {

    // Line delegate
    LineDelegate lineDelegate = new LineDelegate();
    // Save the JButton shape for action of Click.(which will call contains()
    private Shape shape;
    private double lineWidth = GlobalVariable.lineWidth;
    // Save the original Point for use of update one side.
    private Point startPoint, endPoint;
    private boolean isStartUpsideDown, isEndUpsideDown = false;
    // the origin point in the parent, for setLocation.
    Point origin = new Point();
    // the ratio of the upperLine to lowerLine
    Dimension size = new Dimension();
    double ratio = 0.5;
    double startLength = 5 * lineWidth;
    double endLength = 5 * lineWidth;
    // the destination that line data send to
    public SuperUnit destination;
    SuperUnit originUnit;
    int destIndex;

    public Line(Point start, Point end, boolean isStartUD) {
        super();
        startPoint = start;
        endPoint = end;
        isStartUpsideDown = isStartUD;
        calcArea();
        setPreferredSize(size);
        setSize(size);
        // Not going to draw square background, so it can look like other shape we want.
        setContentAreaFilled(false);
        // Not going to drawBorder, it's useless if it can not the same color to the background panel.
        setBorderPainted(false);
        setLocation(origin);
        System.out.println();
        addMouseListener(lineDelegate);
        addMouseMotionListener(lineDelegate);
    }

    public void setEndUpsideDown(boolean endUpsideDown) {
        isEndUpsideDown = endUpsideDown;
    }

    // create the shape we want.
    public Shape calcArea(){
        Rectangle2D startVertical = new Rectangle2D.Double();
        Rectangle2D middleVertical = new Rectangle2D.Double();  // if only three line, this be null
        Rectangle2D endVertical = new Rectangle2D.Double();
        Rectangle2D startHorizontal = new Rectangle2D.Double();
        Rectangle2D endHorizontal = new Rectangle2D.Double();   // if only three line, this be null
        Area area = new Area();
        int minHWid = GlobalVariable.unitWidth / 2 + 4 * (int) lineWidth;
        if (isStartUpsideDown) {
            if (startPoint.y > endPoint.y) {
                if (isEndUpsideDown) {
                    if (startPoint.x - endPoint.x > minHWid) {
                        // startPoint.x - endPoint.x > minimumWidth && startPoint.y > endPoint.y && isStartUpsideDown && isEndUpsideDown
                        // ↓←↑←↓
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                getHeight() - startLength,
                                lineWidth,
                                startLength);
                        middleVertical = new Rectangle2D.Double(
                                (getWidth() - lineWidth) / 2,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                middleVertical.getCenterX(),
                                getHeight() - lineWidth,
                                (getWidth() - lineWidth) / 2,
                                lineWidth);
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                (getWidth() - lineWidth) / 2,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = startPoint.x - endPoint.x;
                        size.height = startPoint.y - endPoint.y + (int)(startLength + endLength);
                    } else if (startPoint.x - endPoint.x < -minHWid) {
                        // startPoint.x - endPoint.x < -minimumWidth && startPoint.y > endPoint.y && isStartUpsideDown && isEndUpsideDown
                        // ↓→↑→↓
                        startVertical = new Rectangle2D.Double(
                                0,
                                getHeight() - startLength,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                (getWidth() - lineWidth) / 2,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                startVertical.getCenterX(),
                                getHeight() - lineWidth,
                                getWidth() / 2,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                middleVertical.getCenterX(),
                                0,
                                getWidth() / 2,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = endPoint.x - startPoint.x;
                        size.height = startPoint.y - endPoint.y + (int) (startLength + endLength);
                    } else if (startPoint.x - endPoint.x > 0){
                        // minimumWidth > startPoint.x - endPoint.x > 0 && startPoint.y > endPoint.y && isStartUpsideDown && isEndUpsideDown
                        // ↓←↑→↓
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                getHeight() - startLength,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                middleVertical.getCenterX(),
                                getHeight() - lineWidth,
                                getWidth() - lineWidth,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                minHWid,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                endHorizontal.getMaxX() - lineWidth,
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x - minHWid;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = startPoint.x - endPoint.x + minHWid;
                        size.height = startPoint.y - endPoint.y + (int) (startLength + endLength);
                    } else {
                        // 0 > startPoint.x - endPoint.x > -minimumWidth && startPoint.y > endPoint.y && isStartUpsideDown && isEndUpsideDown
                        // ↓→↑←↓
                        startVertical = new Rectangle2D.Double(
                                0,
                                getHeight() - startLength,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                startVertical.getCenterX(),
                                getHeight() - lineWidth,
                                getWidth() - lineWidth,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                0,
                                minHWid,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                endHorizontal.getMinX(),
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = endPoint.x - startPoint.x + minHWid;
                        size.height = startPoint.y - endPoint.y + (int) (startLength + endLength);
                    }
                } else {
                    if (startPoint.x -  endPoint.x > minHWid) {
                        // startPoint.x - endPoint.x > minimumWidth && startPoint.y > endPoint.y && isStartUpsideDown && notEndUpsideDown
                        // ↓←↑↑
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                getHeight() - startLength,
                                lineWidth,
                                startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                getHeight()
                        );

                        origin.x = endPoint.x;
                        origin.y = endPoint.y;
                        size.width = startPoint.x - endPoint.x;
                        size.height = startPoint.y - endPoint.y + (int) startLength;
                    } else if (startPoint.x -  endPoint.x < -minHWid) {
                        // startPoint.x - endPoint.x < -minimumWidth && startPoint.y > endPoint.y && isStartUpsideDown && notEndUpsideDown
                        // ↓→↑↑
                        startVertical = new Rectangle2D.Double(
                                0,
                                getHeight() - startLength,
                                lineWidth,
                                startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight()
                        );

                        origin.x = startPoint.x;
                        origin.y = endPoint.y;
                        size.width = endPoint.x - startPoint.x;
                        size.height = startPoint.y - endPoint.y + (int) startLength;
                    } else {
//                        minHWid = minHWid * 4 / 3;
                        if (startPoint.x -  endPoint.x > 0) {
                            // minimumWidth > startPoint.x - endPoint.x > 0 && startPoint.y > endPoint.y && isStartUpsideDown && notEndUpsideDown
                            // ↓←↑→↑
                            startVertical = new Rectangle2D.Double(
                                    getWidth() - lineWidth,
                                    getHeight() - startLength,
                                    lineWidth,
                                    startLength
                            );
                            middleVertical = new Rectangle2D.Double(
                                    0,
                                    endLength,
                                    lineWidth,
                                    getHeight() - endLength
                            );
                            startHorizontal = new Rectangle2D.Double(
                                    middleVertical.getCenterX(),
                                    getHeight() - lineWidth,
                                    getWidth() - lineWidth,
                                    lineWidth
                            );
                            endHorizontal = new Rectangle2D.Double(
                                    0,
                                    endLength,
                                    minHWid,
                                    lineWidth
                            );
                            endVertical = new Rectangle2D.Double(
                                    endHorizontal.getMaxX() - lineWidth,
                                    0,
                                    lineWidth,
                                    endLength
                            );

                            origin.x = endPoint.x - minHWid;
                            origin.y = endPoint.y;
                            size.width = startPoint.x - endPoint.x + minHWid;
                            size.height = startPoint.y - endPoint.y + (int) startLength;
                        } else {
                            // 0 > startPoint.x - endPoint.x > -minimumWidth && startPoint.y > endPoint.y && isStartUpsideDown && notEndUpsideDown
                            // ↓→↑←↑
                            startVertical = new Rectangle2D.Double(
                                    0,
                                    getHeight() - startLength,
                                    lineWidth,
                                    startLength
                            );
                            middleVertical = new Rectangle2D.Double(
                                    getWidth() - lineWidth,
                                    endLength,
                                    lineWidth,
                                    getHeight() - endLength
                            );
                            startHorizontal = new Rectangle2D.Double(
                                    startVertical.getCenterX(),
                                    getHeight() - lineWidth,
                                    getWidth() - lineWidth,
                                    lineWidth
                            );
                            endHorizontal = new Rectangle2D.Double(
                                    getWidth() - minHWid,
                                    endLength,
                                    minHWid,
                                    lineWidth
                            );
                            endVertical = new Rectangle2D.Double(
                                    endHorizontal.getMinX(),
                                    0,
                                    lineWidth,
                                    endLength
                            );

                            origin.x = startPoint.x;
                            origin.y = endPoint.y;
                            size.width = endPoint.x - startPoint.x + minHWid;
                            size.height = startPoint.y - endPoint.y + (int) startLength;
                        }
                    }
                }
            } else {
                if (isEndUpsideDown) {
                    if (startPoint.x - endPoint.x > 0) {
                        // startPoint.x - endPoint.x > 0 && startPoint.y < endPoint.y && isStartUpsideDown && isEndUpsideDown
                        // ↓←↓
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                (getHeight() - lineWidth ) * ratio,
                                getWidth(),
                                lineWidth
                        );
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                startHorizontal.getCenterY()
                        );
                        endVertical = new Rectangle2D.Double(
                                startHorizontal.getX(),
                                startHorizontal.getCenterY(),
                                lineWidth,
                                getHeight() - startHorizontal.getCenterY()
                        );

                        origin.x = endPoint.x;
                        origin.y = startPoint.y;
                        size.width = startPoint.x - endPoint.x;
                        size.height = endPoint.y - startPoint.y;
                    } else {
                        // startPoint.x - endPoint.x < 0 && startPoint.y < endPoint.y && isStartUpsideDown && isEndUpsideDown
                        // ↓→↓
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                (getHeight() - lineWidth ) * ratio,
                                getWidth(),
                                lineWidth
                        );
                        startVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                startHorizontal.getCenterY()
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                startHorizontal.getCenterY(),
                                lineWidth,
                                getHeight() - startHorizontal.getCenterY()
                        );

                        origin.x = startPoint.x;
                        origin.y = startPoint.y;
                        size.width = endPoint.x - startPoint.x;
                        size.height = endPoint.y - startPoint.y;
                    }
                } else {
                    if (startPoint.x - endPoint.x > minHWid) {
                        // startPoint.x - endPoint.x > minimumWidth && startPoint.y < endPoint.y && isStartUpsideDown && notEndUpsideDown
                        // ↓↓←↑
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x;
                        origin.y = startPoint.y;
                        size.width = startPoint.x - endPoint.x;
                        size.height = endPoint.y - startPoint.y + (int) endLength;
                    } else if (startPoint.x - endPoint.x < - minHWid) {
                        // startPoint.x - endPoint.x < -minimumWidth && startPoint.y < endPoint.y && isStartUpsideDown && notEndUpsideDown
                        // ↓↓→↑
                        startVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x;
                        origin.y = startPoint.y;
                        size.width = endPoint.x - startPoint.x;
                        size.height = endPoint.y - startPoint.y + (int) endLength;
                    } else if (startPoint.x - endPoint.x > 0) {
                        // minimumWidth > startPoint.x - endPoint.x > 0 && startPoint.y < endPoint.y && isStartUpsideDown && notEndUpsideDown
                        // ↓←←↓→↑
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                0,
                                startLength,
                                lineWidth,
                                getHeight() - startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                startLength - lineWidth,
                                getWidth(),
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                minHWid,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                minHWid,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x - minHWid;
                        origin.y = startPoint.y;
                        size.width = startPoint.x - endPoint.x + minHWid;
                        size.height = endPoint.y - startPoint.y + (int) endLength;
                    } else {
                        // 0 > startPoint.x - endPoint.x > -minimumWidth && startPoint.y < endPoint.y && isStartUpsideDown && notEndUpsideDown
                        // ↓←↓→→↑
                        startVertical = new Rectangle2D.Double(
                                minHWid,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                0,
                                startLength,
                                lineWidth,
                                getHeight() - startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                startLength - lineWidth,
                                minHWid,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x - minHWid;
                        origin.y = startPoint.y;
                        size.width = endPoint.x - startPoint.x + minHWid;
                        size.height = endPoint.y - startPoint.y + (int) endLength;
                    }
                }
            }
        } else {
            if (startPoint.y > endPoint.y) {
                if (isEndUpsideDown) {
                    if (startPoint.x - endPoint.x > minHWid) {
                        // startPoint.x - endPoint.x > minimumWidth && startPoint.y > endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑↑←↓
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = startPoint.x - endPoint.x;
                        size.height = startPoint.y - endPoint.y + (int) endLength;
                    } else if (startPoint.x - endPoint.x < -minHWid) {
                        // startPoint.x - endPoint.x < -minimumWidth && startPoint.y > endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑↑→↓
                        startVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = endPoint.x - startPoint.x;
                        size.height = startPoint.y - endPoint.y + (int) endLength;
                    } else if (startPoint.x - endPoint.x > 0) {
                        // minimumWidth > startPoint.x - endPoint.x > 0 && startPoint.y > endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑→↑←↓
                        startVertical = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                getHeight() - startLength,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight() - startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                getHeight() - startLength,
                                minHWid,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = startPoint.x - endPoint.x + minHWid;
                        size.height = startPoint.y - endPoint.y + (int) endLength;
                    } else {
                        // 0 > startPoint.x - endPoint.x > -minimumWidth && startPoint.y > endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑←↑→↓
                        startVertical = new Rectangle2D.Double(
                                minHWid - lineWidth,
                                getHeight() - startLength,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                getHeight() - startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - startLength,
                                minHWid,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x - minHWid;
                        origin.y = endPoint.y - (int) endLength;
                        size.width = endPoint.x - startPoint.x + minHWid;
                        size.height = startPoint.y - endPoint.y + (int) endLength;
                    }
                } else {
                    if (startPoint.x - endPoint.x > 0) {
                        // startPoint.x - endPoint.x > 0 && startPoint.y > endPoint.y && notStartUpsideDown && notEndUpsideDown
                        // ↑←↑
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                (getHeight() - lineWidth) / 2,
                                lineWidth,
                                (getHeight() - lineWidth) / 2
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                (getHeight() - lineWidth) / 2,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                (getHeight() - lineWidth) / 2
                        );

                        origin.x = endPoint.x;
                        origin.y = endPoint.y + 5;
                        size.width = startPoint.x - endPoint.x;
                        size.height = startPoint.y - endPoint.y;
                    } else {
                        // startPoint.x - endPoint.x < 0 && startPoint.y > endPoint.y && notStartUpsideDown && notEndUpsideDown
                        // ↑→↑
                        startVertical = new Rectangle2D.Double(
                                0,
                                (getHeight() - lineWidth) / 2,
                                lineWidth,
                                (getHeight() - lineWidth) / 2
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                (getHeight() - lineWidth) / 2,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                (getHeight() - lineWidth) / 2
                        );

                        origin.x = startPoint.x;
                        origin.y = endPoint.y + 5;
                        size.width = endPoint.x - startPoint.x;
                        size.height = startPoint.y - endPoint.y;
                    }
                }
            } else {
                if (isEndUpsideDown) {
                    if (startPoint.x - endPoint.x > minHWid) {
                        // startPoint.x - endPoint.x > minimumWidth && startPoint.y < endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑←↓↓
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                getHeight()
                        );

                        origin.x = endPoint.x;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = startPoint.x - endPoint.x;
                        size.height = endPoint.y - startPoint.y + (int) startLength;
                    } else if (startPoint.x - endPoint.x < -minHWid) {
                        // startPoint.x - endPoint.x < -minimumWidth && startPoint.y < endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑→↓↓
                        startVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                startLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight()
                        );

                        origin.x = startPoint.x;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = endPoint.x - startPoint.x;
                        size.height = endPoint.y - startPoint.y + (int) startLength;
                    } else if (startPoint.x - endPoint.x > 0) {
                        // minimumWidth > startPoint.x - endPoint.x > 0 && startPoint.y < endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑←↓→↓
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                getHeight() - endLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - endLength,
                                minHWid,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                minHWid - lineWidth,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x - minHWid;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = startPoint.x - endPoint.x + minHWid;
                        size.height = endPoint.y - startPoint.y + (int) startLength;
                    } else {
                        // 0 > startPoint.x - endPoint.x > -minimumWidth && startPoint.y < endPoint.y && notStartUpsideDown && isEndUpsideDown
                        // ↑→↓←↓
                        startVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight() - endLength
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                getWidth()- minHWid,
                                getHeight() - endLength,
                                minHWid,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = endPoint.x - startPoint.x + minHWid;
                        size.height = endPoint.y - startPoint.y + (int) startLength;
                    }
                } else {
                    minHWid = minHWid * 4 / 3;
                    if (startPoint.x - endPoint.x > minHWid) {
                        // startPoint.x - endPoint.x > minimumWidth && startPoint.y < endPoint.y && notStartUpsideDown && notEndUpsideDown
                        // ↑←↓←↑
                        startVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                (getWidth() - lineWidth) / 2,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                (getWidth() - lineWidth) / 2,
                                0,
                                (getWidth() - lineWidth) / 2,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                (getWidth() - lineWidth) / 2,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = startPoint.x - endPoint.x;
                        size.height = endPoint.y - startPoint.y + (int) (startLength + endLength);
                    } else if (startPoint.x - endPoint.x < - minHWid) {
                        // startPoint.x - endPoint.x < -minimumWidth && startPoint.y < endPoint.y && notStartUpsideDown && notEndUpsideDown
                        // ↑→↓→↑
                        startVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                (getWidth() - lineWidth) / 2,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                (getWidth() - lineWidth) / 2,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                (getWidth() - lineWidth) / 2,
                                getHeight() - lineWidth,
                                (getWidth() - lineWidth) / 2,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = endPoint.x - startPoint.x;
                        size.height = endPoint.y - startPoint.y + (int) (startLength + endLength);
                    } else if (startPoint.x - endPoint.x > 0) {
                        // minimumWidth > startPoint.x - endPoint.x > 0 && startPoint.y < endPoint.y && notStartUpsideDown && notEndUpsideDown
                        // ↑→↓←←↑
                        startVertical = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                0,
                                minHWid,
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                0,
                                getHeight() - lineWidth,
                                getWidth(),
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                0,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = endPoint.x;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = startPoint.x - endPoint.x + minHWid;
                        size.height = endPoint.y - startPoint.y + (int) (startLength + endLength);
                    } else {
                        // startPoint.x - endPoint.x > minimumWidth && startPoint.y < endPoint.y && notStartUpsideDown && notEndUpsideDown
                        // ↑→→↓←↑
                        startVertical = new Rectangle2D.Double(
                                0,
                                0,
                                lineWidth,
                                startLength
                        );
                        middleVertical = new Rectangle2D.Double(
                                getWidth() - lineWidth,
                                0,
                                lineWidth,
                                getHeight()
                        );
                        startHorizontal = new Rectangle2D.Double(
                                0,
                                0,
                                getWidth(),
                                lineWidth
                        );
                        endHorizontal = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                getHeight() - lineWidth,
                                minHWid,
                                lineWidth
                        );
                        endVertical = new Rectangle2D.Double(
                                getWidth() - minHWid,
                                getHeight() - endLength,
                                lineWidth,
                                endLength
                        );

                        origin.x = startPoint.x;
                        origin.y = startPoint.y - (int) startLength;
                        size.width = endPoint.x - startPoint.x + minHWid;
                        size.height = endPoint.y - startPoint.y + (int) (startLength + endLength);
                    }
                }
            }
        }
        Area startV = new Area(startVertical);
        Area middleV = new Area(middleVertical);
        Area startH = new Area(startHorizontal);
        Area endH = new Area(endHorizontal);
        Area endV = new Area(endVertical);
        area.add(startV);
        area.add(middleV);
        area.add(startH);
        area.add(endH);
        area.add(endV);
        return area;
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
        endPoint = end;
        calcArea();
        if (size.width < lineWidth) {
            size.width = (int) lineWidth;
        }
        this.setSize(size);
        setLocation(origin);
        // save the new endPoint.
        this.endPoint = end;
    }

    public void updateStartPoint(Point start) {
        // update size and origin
        startPoint = start;
        calcArea();
        if (size.width < lineWidth) {
            size.width = (int) lineWidth;
        }
        this.setSize(size);
        setLocation(origin);
        // save the new startPoint.
        this.startPoint = start;
    }

}