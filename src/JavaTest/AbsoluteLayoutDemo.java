/*
 *
 * AbsoluteLayoutDemo.java
 * GraduationProject
 *
 * Created by X on 2019/4/4
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
    private JPanel unitPanel;
    private JPanel workingPanel;//创建面板
    private JSplitPane contentPanel;
    public AbsoluteLayoutDemo()
    {
        this.setTitle("布局Demo");//设置标题名字
        this.setName("JFrame Demo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认退出
        this.setBounds(100, 100, 750, 500);//设置窗体的大小
        this.unitPanel =new JPanel();//初始化面板
        this.unitPanel.setSize(this.getWidth() / 5, this.getHeight());
        this.unitPanel.setName("ContentPane");
        this.unitPanel.setLayout(null);//设置布局NULL
        UnitButton unitButton1 = new UnitButton("The First",2,3);
        this.unitPanel.add(unitButton1);
        UnitButton uBtn2 = new UnitButton("The Second",1,1);
        uBtn2.setLocation(40,70);
        this.unitPanel.add(uBtn2,0);
//        this.add(this.unitPanel);
        this.workingPanel = new JPanel();
        this.workingPanel.setName("Working Panel");
        this.workingPanel.setSize(this.getWidth() - this.unitPanel.getWidth(), this.getHeight());
        this.workingPanel.setLayout(null);
//        this.workPanel.setBackground(Color.CYAN);
        this.workingPanel.addMouseListener(new MouseDelegate());
        GlobalVariable.workingPanel = this.workingPanel;
        Point start = new Point(10,50);
        Point end = new Point(50,10);
        linkLine line = new linkLine(start, end);
        this.workingPanel.add(line);
        line.setBounds(10,10,100,40);
//        this.add(this.workPanel);
        this.contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,unitPanel,workingPanel);
        this.contentPanel.setSize(this.getSize());
        this.contentPanel.setDividerLocation(this.unitPanel.getWidth());
        this.add(contentPanel);
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
//    public JLabel lastOutLabel;

    public UnitButton(String unitText, int input, int output) {
        MouseDelegate mouseDelegate = new MouseDelegate();
        MouseMotionDelegate mouseMotionDelegate = new MouseMotionDelegate();
        this.setLayout(null);
        this.setBounds(10,10,panelWidth,panelHeight);
        this.setName("Panel");
        this.in = new JLabel[input];
        for (int i = 0; i < input; i ++) {
            in[i] = new JLabel();
            in[i].setName("In " + i);
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
        this.label.setName(unitText);
        this.label.setHorizontalAlignment(SwingConstants.CENTER);
        this.label.setBounds(0,buttonHeight/2,panelWidth,panelHeight - buttonHeight);
        this.label.setBackground(Color.LIGHT_GRAY);
        this.label.setOpaque(true);
        this.add(this.label);
        this.setBackground(Color.darkGray);
        this.addMouseMotionListener(mouseMotionDelegate);
        this.addMouseListener(mouseDelegate);
        this.setName(unitText + " Container");
    }
}

class MouseMotionDelegate implements MouseMotionListener {
    UnitButton uBtn;
    int count = 0;

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GlobalVariable.dragState) {
            case init:
                Component eventComponent = ((UnitButton)e.getSource()).getParent().getComponentAt(Until.getAbsolutePointBy(e));
                Component subComponent = eventComponent.getComponentAt(Until.transformToRelative(Until.getAbsolutePointBy(e),eventComponent));
                System.out.println("Mouse Dragged at " + e.getX() + "," + e.getY() + "Component is " + eventComponent.getName() + "," + subComponent.getName());
                if (eventComponent instanceof UnitButton ){
                    if (!(Until.isPointInComponents(e,"In")) && !(Until.isPointInComponents(e,"Out"))){
                        // You are attempt to relocate the Unit.
                        Point newLoca = Until.getAbsolutePointBy(e);
                        ((Component)e.getSource()).setLocation(newLoca);
                        GlobalVariable.dragState = GlobalVariable.DragState.forRelocate;
                    } else {
                        // you drag a In or Out, so it is to Link Unit
                        GlobalVariable.dragState = GlobalVariable.DragState.forLink;
                    }
                }
                break;
            case forLink:
                break;
            case forRelocate:
                // if this event occur during dragging label around
                Point newLoca = Until.getAbsolutePointBy(e);
                ((Component)e.getSource()).setLocation(newLoca);
                break;
        }

//        Point newLoca = Until.getAbsolutePointBy(e);
//        count++;
//        if (uBtn == null && count == 50){
//            // new a one
//            System.out.println("We are add a label");
//            uBtn = new UnitButton("The Second",3,3);
//            uBtn.setLocation(newLoca);
//            Container su = ((UnitButton)e.getSource()).getParent();
//            su.add(uBtn);
        // Revalidate the panel which you add to show the newest component.
//            su.revalidate();
//        } else {
//            ((Component)e.getSource()).setLocation(newLoca);
//        }

    }
}

class MouseDelegate implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        UnitButton ubtnD = new UnitButton("a new one",2,2);
        ubtnD.setLocation(Until.getAbsolutePointBy(e));
        GlobalVariable.workingPanel.add(ubtnD,0);
        GlobalVariable.workingPanel.revalidate();
        GlobalVariable.workingPanel.updateUI();
        System.out.println("Mouse Click");
    }

    @Override
    public void mousePressed(MouseEvent e) {

        System.out.print("Mouse Pressed at " + e.getX() + "," + e.getY());
        Until.printLabel(e);
        if (((UnitButton)e.getSource()).getComponentAt(e.getPoint()) instanceof JLabel){
            if (Until.isPointInComponents(e,"Out")){
                GlobalVariable.lastPress = ((JLabel) ((UnitButton)e.getSource()).getComponentAt(e.getPoint()));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        GlobalVariable.dragState = GlobalVariable.DragState.init;
        System.out.print("Mouse Released at " + e.getX() + "," + e.getY());
        Until.printLabel(e);
        Component eventComponent = ((UnitButton)e.getSource()).getParent().getComponentAt(Until.getAbsolutePointBy(e));
        Component subComponent = eventComponent.getComponentAt(Until.transformToRelative(Until.getAbsolutePointBy(e),eventComponent));
        if (subComponent instanceof JLabel){
            if (Until.isPointInComponents(e,"In")){
                JLabel lastPress = GlobalVariable.lastPress;
                if (lastPress != null) {
                    subComponent.setBackground(lastPress.getBackground());
                }
            } else {
                GlobalVariable.lastPress = null;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Enter:" + e.getX() + "," + e.getY() + ":" + ((Component)e.getSource()).getName());
    }

    @Override
    public void mouseExited(MouseEvent e) {}

}

class Until {
    public static Point getAbsolutePointBy(MouseEvent event){
        Point point = new Point();
        point.x = ((Component)event.getSource()).getX() + event.getX();
        point.y = ((Component)event.getSource()).getY() + event.getY();
        return point;
    }

    public static Point transformToRelative(Point point, Component in){
        point.x -= in.getX();
        point.y -= in.getY();
        return point;
    }

    public static boolean isPointInComponents(MouseEvent e, String comName){
        Component eventComponent = ((UnitButton)e.getSource()).getParent().getComponentAt(Until.getAbsolutePointBy(e));
        Component subComponent = eventComponent.getComponentAt(Until.transformToRelative(Until.getAbsolutePointBy(e),eventComponent));
        if (subComponent instanceof JLabel){
            String name = subComponent.getName();
            if (name != null && name.contains(comName)) {
                return true;
            }
        }
        return false;
    }

    public static void printLabel(MouseEvent e){
        Component eventComponent = ((UnitButton)e.getSource()).getParent().getComponentAt(Until.getAbsolutePointBy(e));
        Component subComponent = eventComponent.getComponentAt(Until.transformToRelative(Until.getAbsolutePointBy(e),eventComponent));
        if (subComponent instanceof JLabel){
            System.out.println("\t" + subComponent.getName());
        } else {
            System.out.println();
        }
    }
}

class GlobalVariable {
    enum DragState {
        init, forLink, forRelocate, forCreate
    }
    static DragState dragState = DragState.init;
    public static JLabel lastPress;
    public static JPanel workingPanel;
}

class linkLine extends JLabel {

    public linkLine(Point start, Point end) {
        super();
        this.setText("Wakaka");
        Dimension size = getPreferredSize();
        size.height = (int) (start.getY() - end.getY());
        size.width = (int) (start.getX() - end.getX());
        setPreferredSize(size);
        this.setOpaque(true);
        this.setBackground(Color.CYAN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(getWidth(),0,0,getHeight());
        g.setColor(Color.RED);

    }
}
