/*
 *
 * HomePage.java
 * GraduationProject
 *
 * Created by X on 2019/4/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import PCOVL.UnitRepository.RAM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static PCOVL.UI.GlobalVariable.*;

public class HomePage extends JFrame {
    private JSplitPane contentPanel;

    public HomePage() {
        // JFrame initial.
        this.setTitle("Principles of Computer Organization Virtual Lab");
        this.setName("HomePageJFrame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200,800);
        this.setLocationRelativeTo(null);
        // init the content panel ( unitPanel, workPanel)
        initSubView();
        initUnitInUnitPanel();
        // Button to execute the instruction.
        JButton btn = new JButton("Run");
        btn.setBounds(workPanel.getWidth() - 80, 10,60,20);
        workPanel.add(btn);
        btn.addActionListener((ActionEvent e) ->
                { EventUtil.executeInstruction(); }
            );
        this.setVisible(true);
    }

    void initSubView(){
        unitPanel = new JPanel();
        unitPanel.setName("UnitPanel");
        unitPanel.setSize(unitWidth + 20, this.getHeight());
        unitPanel.setLayout(null);

        workPanel = new JPanel();
        workPanel.setName("WorkingPanel");
        workPanel.setSize(this.getWidth() - unitPanel.getWidth(), this.getHeight());
        workPanel.setLayout(null);
        workPanel.addMouseListener(new WorkingPanelDelegate());

        this.contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,unitPanel, workPanel);
        this.contentPanel.setName("ContentPanel");
        this.setSize(this.getSize());
        this.contentPanel.setDividerLocation(unitPanel.getWidth());
        // disable the action to change the divider location
        this.contentPanel.setEnabled(false);
        this.setContentPane(contentPanel);
    }

    void initUnitInUnitPanel() {
        String[][] unitString = new String[][]{
                {"Switch", "0", "1","false"},
                {"MUX2", "3", "1","false"},
                {"MUX2", "3", "1","true"},
                {"PC","1","1","false"},
                {"Acc", "1","1","false"},
                {"IR", "1", "1", "true"}};
        BaseUnitUI unit;
        for (int iStr = 0; iStr < unitString.length; iStr ++) {
            String[] str = unitString[iStr];
            unit = new BaseUnitUI(str[0],Integer.parseInt(str[1]), Integer.parseInt(str[2]), Boolean.parseBoolean(str[3]));
            unit.setName(str[0]);
            unit.setLocation(10, 10 * (iStr + 1) + iStr * unitHeight);
            unitPanel.add(unit);
        }

        // One RAM only
        BaseUnitUI ramUI = getRAMUI();
        workPanel.add(ramUI);
    }

    BaseUnitUI getRAMUI() {
        BaseUnitUI unitUI = new BaseUnitUI("RAM", 3,0,false);
        unitUI = new BaseUnitUI(unitUI, false);
        unitUI.getComponent(2).setName("Out 0");
        unitUI.getComponent(2).setBackground(outColor[0]);
        WorkingPanelDelegate workingPanelDelegate = new WorkingPanelDelegate();
        unitUI.addMouseListener(workingPanelDelegate);
        unitUI.addMouseMotionListener(workingPanelDelegate);
        RAM ram = new RAM(unitUI);
        GlobalVariable.unitArray.add(ram);
        GlobalVariable.componentArray.add(unitUI);
        unitUI.setName("RAM");
        unitUI.setLocation(500,100);
        return unitUI;
    }
}
