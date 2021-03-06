/*
 *
 * CustomButton.java
 * GraduationProject
 *
 * Created by X on 2019/4/17
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CustomButton extends JButton {

    private Shape shape; //用于保存按钮的形状，有助于侦听单击按钮事件
    private Double lineWidth = 10.0;
    double ratio = 0.3;
    boolean isStartUpsideDown = true;
    boolean isEndUpsideDown = true;
    Point startPoint = new Point();
    Point endPoint = new Point();
    double startLength = 3*lineWidth;
    double endLength = 3*lineWidth;

    public CustomButton(int width, int height) {
        super();
        Dimension size = getPreferredSize();//获取按钮的最佳大小
        size.width = width;
        size.height = height;
        setPreferredSize(size);
        //使JButton不画背景，即不显示方形背景
        setContentAreaFilled(false);
    }

    //画按钮的背景和标签
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            //getModel方法返回鼠标的模型ButtonModel
            //如果鼠标按下按钮，则ButtonModel的Armed属性为真
            g.setColor(Color.RED);
        }else {
            g.setColor(getBackground());
        }
        //fileOval方法画一个矩形的内切椭圆，并且填充这个椭圆
        //当矩形为正方形时，画出的椭圆便是圆
//        g.fillOval(0,0,getSize().width - 1, getSize().height - 1);

        //调用父类的paintComponent画按钮的标签和焦点所在的小矩形
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;


        shape = getShape();

        graphics2D.fill(shape);
//        Stroke stroke = new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
//        graphics2D.setStroke(stroke);
//        Line2D line2D = new Line2D.Double(10,10,getWidth(),getHeight());
//        graphics2D.draw(line2D);
    }

    //用简单的弧充当按钮的边界
    @Override
    protected void paintBorder(Graphics g) {
        //drawOval方法画矩形的内切椭圆，但不填充，只画出一个边界
//        g.drawOval(0,0,getSize().width - 1, getSize().height - 1);
    }

    //判断鼠标是否点在按钮上

    @Override
    public boolean contains(int x,int y){
        //如果按钮边框,位置发生改变,则产生一个新的形状对象
        if((shape==null)||(!shape.getBounds().equals(getBounds()))){
            //构造椭圆型对象
            shape = getShape();
        }
        //判断鼠标的x,y坐标是否落在按钮形状内
        return shape.contains(x,y);
    }

    public static void main(String[] args) {
        JButton button = new CustomButton(100,100);
        button.setBackground(Color.lightGray);//设置背景色为绿色
//        button.addActionListener( (ActionEvent e) ->
//                {
//                    System.out.println("ActionEvent Detected");
//                    button.setSize(300,300); }
//        );
        LineDelegate lineDelegate = new LineDelegate();
        button.addMouseListener(lineDelegate);
        button.addMouseMotionListener(lineDelegate);
        //产生一个框架显示这个按钮
        JFrame frame=new JFrame("图形按钮");
//        frame.getContentPane().setBackground(Color.blue);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(button);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Shape getShape() {
        Rectangle2D startVertical,endVertical;
        Area area = new Area();
//        if (isStartUpsideDown) {
//            // the From Unit is UpsideDown
//            if (startPoint.x > endPoint.x) {
//                if (startPoint.y > endPoint.y && isEndUpsideDown) {
//                    // ↓←↑←↓
//                    startVertical = new Rectangle2D.Double(
//                            getWidth() - lineWidth - 1,
//                            getHeight() - startLength - 1,
//                            lineWidth,
//                            startLength);
//                    Rectangle2D middleVertical = new Rectangle2D.Double(
//                            (getWidth() - lineWidth) / 2,
//                            1,
//                            lineWidth,
//                            getHeight() - 2
//                    );
//                    Rectangle2D startHorizontal = new Rectangle2D.Double(
//                            middleVertical.getCenterX(),
//                            getHeight() - lineWidth - 1,
//                            (getWidth() - lineWidth) / 2,
//                            lineWidth);
//                    Rectangle2D endHorizontal = new Rectangle2D.Double(
//                            1,
//                            1,
//                            (getWidth() - lineWidth) / 2,
//                            lineWidth
//                    );
//                    endVertical = new Rectangle2D.Double(
//                            1,
//                            1,
//                            lineWidth,
//                            endLength
//                    );
//                    Area startV = new Area(startVertical);
//                    Area middleV = new Area(middleVertical);
//                    Area startH = new Area(startHorizontal);
//                    Area endH = new Area(endHorizontal);
//                    Area endV = new Area(endVertical);
//                    area.add(startV);
//                    area.add(middleV);
//                    area.add(startH);
//                    area.add(endH);
//                    area.add(endV);
//                } else {
//                    Rectangle2D horizontal;
//                    if (startPoint.y > endPoint.y && !isEndUpsideDown) {
//                        // ↓↓←↑
//                        startVertical = new Rectangle2D.Double(
//                                getWidth() - lineWidth - 1,
//                                1,
//                                lineWidth,
//                                getHeight() - 2
//                        );
//                        horizontal = new Rectangle2D.Double(
//                                1,
//                                getHeight() - lineWidth - 1,
//                                getWidth() - 2,
//                                lineWidth
//                        );
//                        endVertical = new Rectangle2D.Double(
//                                1,
//                                getHeight() - endLength - 1,
//                                lineWidth,
//                                endLength
//                        );
//                    } else {
//                        // startPoint.x > endPoint.x && startPoint.y < endPoint.y
//                        if (isEndUpsideDown) {
//                            // ↓←↓
//                            horizontal = new Rectangle2D.Double(
//                                    1,
//                                    (getHeight() - lineWidth ) * ratio,
//                                    getWidth() - 2,
//                                    lineWidth);
//                            startVertical = new Rectangle2D.Double(
//                                    getWidth() - 1 - lineWidth,
//                                    1,
//                                    lineWidth,
//                                    horizontal.getCenterY());
//                            endVertical = new Rectangle2D.Double(
//                                    horizontal.getX(),
//                                    horizontal.getCenterY(),
//                                    lineWidth,
//                                    getHeight() - horizontal.getCenterY() - 2);
//                        } else {
//                            // ↓←↑↑
//                            startVertical = new Rectangle2D.Double(
//                                    getWidth() - lineWidth - 1,
//                                    getHeight() - startLength - 1,
//                                    lineWidth,
//                                    startLength
//                            );
//                            horizontal = new Rectangle2D.Double(
//                                    1,
//                                    getHeight() - lineWidth - 1,
//                                    getWidth() - 2,
//                                    lineWidth
//                            );
//                            endVertical = new Rectangle2D.Double(
//                                    1,
//                                    1,
//                                    lineWidth,
//                                    getHeight() - 2
//                            );
//
//                        }
//                    }
//                    Area startV = new Area(startVertical);
//                    Area horizon = new Area(horizontal);
//                    Area endV = new Area(endVertical);
//                    area.add(startV);
//                    area.add(horizon);
//                    area.add(endV);
//                }
//            }
//
//        } else {
//            // the From Unit is NOT UpsideDown
//
//        }
        return area;
    }
}

class LineDelegate implements MouseListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        e.getComponent().setSize(
                (e.getComponent().getWidth() == 100 && e.getComponent().getHeight() == 100)
                        ? new Dimension(200,200)
                        : new Dimension(100,100)
        );
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

    @Override
    public void mouseDragged(MouseEvent e) {
        Point newPoint = e.getPoint();
        double newDiv = newPoint.getY() / e.getComponent().getHeight();
        CustomButton button = (CustomButton) e.getComponent();
        button.ratio = newDiv;
        button.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
