/*
 *
 * AbsoluteLayoutDemo.java
 * GraduationProject
 *
 * Created by X on 2019/3/19
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AbsoluteLayoutDemo extends JFrame {
    private JPanel contentPane;//创建面板
    public AbsoluteLayoutDemo()
    {
        this.setTitle("绝对布局");//设置标题名字
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认退出
        this.setBounds(100, 100, 500, 500);//设置窗体的大小
        this.contentPane=new JPanel();//初始化面板
        this.contentPane.setLayout(null);//设置布局NULL
        UnitButton unitButton = new UnitButton("Hi buddy!",2,3);
        this.contentPane.add(unitButton);
        this.add(this.contentPane);
        this.setVisible(true);
    }

    public static void main(String[]args)
    {
        new AbsoluteLayoutDemo();
    }
}

// the super unit
class UnitButton extends JPanel {
    private JLabel label;
    // the data exist
    private JLabel[] in;
    private JLabel[] out;
    private int panelWidth = 100;
    private int panelHeight = 50;
    private int buttonWidth = 10;
    private int buttonHeight = 10;
    private Color[] colors = {Color.CYAN,Color.green,Color.red};
    public JLabel lastPress;

    public UnitButton(String unitText, int input, int output) {
        MouseDelegate mouseDelegate = new MouseDelegate();
        MouseMotionDelegate mouseMotionDelegate = new MouseMotionDelegate();
        this.setLayout(null);
        this.setBounds(100,100,panelWidth,panelHeight);
        this.setName("Panel");
        this.in = new JLabel[input];
        for (int i = 0; i < input; i ++) {
            in[i] = new JLabel();
            in[i].setName("In" + i);
            in[i].setText("In " + i);
            in[i].setHorizontalAlignment(SwingConstants.CENTER);
            in[i].setBackground(colors[i]);
            in[i].setOpaque(true);
            in[i].setBounds((i + 1) * (panelWidth - input*buttonWidth) / (input + 1) + i*buttonWidth,
                    panelHeight - buttonHeight,
                    buttonWidth,
                    buttonHeight);
            this.add(in[i]);
        }
        this.out = new JLabel[output];
        for (int i = 0; i < output; i ++) {
            out[i] = new JLabel();
            out[i].setName("Out " + i);
            out[i].setText("Out " + i);
            out[i].setHorizontalAlignment(SwingConstants.CENTER);
            out[i].setBackground(colors[i]);
            out[i].setOpaque(true);
            out[i].setBounds((i + 1) * (panelWidth - output*buttonWidth) / (output + 1) + i*buttonWidth,
                    0,
                    buttonWidth,
                    buttonHeight);
            this.add(out[i]);
        }
        this.label = new JLabel(unitText);
        this.label.setHorizontalAlignment(SwingConstants.CENTER);
        this.label.setBounds(0,buttonHeight/2,panelWidth,panelHeight - buttonHeight);
        this.label.setBackground(Color.LIGHT_GRAY);
        this.label.setOpaque(true);
        this.add(this.label);
        this.setBackground(Color.darkGray);
        this.addMouseMotionListener(mouseMotionDelegate);
        this.addMouseListener(mouseDelegate);
    }
}

class MouseMotionDelegate implements MouseMotionListener {

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
//        System.out.println("Mouse Dragged:" + e.getX() + "," + e.getY());
    }
}

class MouseDelegate implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
//        if (((UnitButton)e.getSource()).getComponentAt(e.getPoint()) instanceof JLabel){
//            try{
//                ((UnitButton)e.getSource()).getClass().getDeclaredField("lastPress").set(e.getSource(),((UnitButton)e.getSource()).getComponentAt(e.getPoint()));
//            }catch (Exception error){
//                System.out.println("Error occurred when handle the MousePressed Function");
//                error.printStackTrace();
//            }
//        }
        System.out.print("Mouse Pressed at " + e.getX() + "," + e.getY());
        if (((UnitButton)e.getSource()).getComponentAt(e.getPoint()) instanceof JLabel){
            System.out.println("\t" + ((UnitButton)e.getSource()).getComponentAt(e.getPoint()).getName());
        } else {
            System.out.println();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        if (((UnitButton)e.getSource()).getComponentAt(e.getPoint()) instanceof JLabel){
//            try{
//                ((UnitButton)e.getSource()).getComponentAt(e.getPoint()).setBackground(
//                        ((JLabel)((UnitButton)e.getSource()).getClass().getDeclaredField("lastPress").get(e.getSource())).getBackground()
//                );
//            }catch (Exception error){
//                System.out.println("Error occurred when handle the MousePressed Function");
//                error.printStackTrace();
//            }
//        }
        System.out.print("Mouse Released at " + e.getX() + "," + e.getY());
        if (((UnitButton)e.getSource()).getComponentAt(e.getPoint()) instanceof JLabel){
            System.out.println("\t" + ((UnitButton)e.getSource()).getComponentAt(e.getPoint()).getName());
        } else {
            System.out.println();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("Mouse Enter:" + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {}
}
// TODO we can set multi In and Out
// TODO we can move it around
// TODO we can do the link action
