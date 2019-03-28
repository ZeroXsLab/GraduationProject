/*
 *
 * HomePage.java
 * GraduationProject
 *
 * Created by X on 2019/3/28
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static PCOVL.UI.GlobalVariable.*;

public class HomePage extends JFrame {
    private JSplitPane contentPanel;

    public HomePage() {
        this.setTitle("Principles of Computer Organization Virtual Lab");
        this.setName("HomePageJFrame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,1200,800);
        initSubView();
        initUnitInUnitPanel();
        JButton btn = new JButton("Run");
        btn.setBounds(workingPanel.getWidth() - 80, 10,60,20);
        workingPanel.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventUtil.executeInstruction();
            }
        });
        this.setVisible(true);
    }

    void initSubView(){
        unitPanel = new JPanel();
        unitPanel.setName("UnitPanel");
        unitPanel.setSize(unitWidth + 20, this.getHeight());
        unitPanel.setLayout(null);

        workingPanel = new GraphicJPanel();
        workingPanel.setName("WorkingPanel");
        workingPanel.setSize(this.getWidth() - unitPanel.getWidth(), this.getHeight());
        workingPanel.setLayout(null);
        workingPanel.addMouseListener(new WorkingPanelDelegate());

        this.contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,unitPanel,workingPanel);
        this.contentPanel.setName("ContentPanel");
        this.setSize(this.getSize());
        this.contentPanel.setDividerLocation(unitPanel.getWidth());
        this.contentPanel.setEnabled(false);
        this.setContentPane(contentPanel);
    }

    void initUnitInUnitPanel() {
        BaseUnitUI unit1 = new BaseUnitUI("Switch", 0, 1);
        unit1.setName("Switch");
        unit1.setLocation(10,10);
        unitPanel.add(unit1);
        BaseUnitUI unit2 = new BaseUnitUI("MUX2", 3, 1);
        unit2.setName("MUX2");
        unit2.setLocation(10, 20 + unitHeight);
        unitPanel.add(unit2);
    }
}
