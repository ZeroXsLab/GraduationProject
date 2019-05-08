/*
 *
 * HomePage.java
 * GraduationProject
 *
 * Created by X on 2019/5/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import PCOVL.UnitRepository.RAM;
import PCOVL.UnitRepository.SuperUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static PCOVL.UI.GlobalVariable.*;

public class HomePage extends JFrame {
    private JSplitPane contentPanel;
    private boolean memoryReady = false;

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
        runBtn = btn;
        btn.setBounds(workPanel.getWidth() - 80, 10,60,20);
        workPanel.add(btn);
        btn.addActionListener((ActionEvent e) ->
                {
                    if (unitArray.size() == 1 && GlobalVariable.unitArray.contains(GlobalVariable.RAM)){
                        // user do nothing before click, add machine.
                        initMachine();
                    }
                    if (memoryReady || !GlobalVariable.unitArray.contains(GlobalVariable.RAM)) {
                        EventUtil.executeInstruction();
                    } else {
                        memoryReady = EventUtil.initMemory();
                    }
                }
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
        // {String UnitName,
        // int inputNumber,
        // int outputNumber,
        // boolean isUpsideDown,
        // boolean isTheFirstInControl}
        String[][] unitString = new String[][]{
                {"Switch", "0", "1","false","false"},
                {"MUX2", "3", "1","false","true"},
                {"MUX2r", "3", "1","true","true"},
                {"PC","1","1","false","false"},
                {"Acc", "1","1","false","false"},
                {"IR", "1", "1", "true","false"},
                {"ALU","3","1","false","true"},
                {"Ctrl","1","0","false","true"}};
        BaseUnitUI unit;
        for (int iStr = 0; iStr < unitString.length; iStr ++) {
            String[] str = unitString[iStr];
            unit = new BaseUnitUI(str[0],Integer.parseInt(str[1]),
                    Integer.parseInt(str[2]),
                    Boolean.parseBoolean(str[3]),
                    Boolean.parseBoolean(str[4]));
            unit.setName(str[0]);
            unit.setLocation(10, 10 * (iStr + 1) + iStr * unitHeight);
            unitPanel.add(unit);
        }

        // One RAM only
        BaseUnitUI ramUI = getRAMUI();
        workPanel.add(ramUI);
    }

    BaseUnitUI getRAMUI() {
        BaseUnitUI unitUI = new BaseUnitUI("RAM", 3,0,false, false);
        unitUI = new BaseUnitUI(unitUI, false);
        unitUI.getComponent(2).setName("Out 0");
        unitUI.getComponent(2).setBackground(outColor[0]);
        WorkingPanelDelegate workingPanelDelegate = new WorkingPanelDelegate();
        unitUI.addMouseListener(workingPanelDelegate);
        unitUI.addMouseMotionListener(workingPanelDelegate);
        RAM ram = new RAM(unitUI);
        GlobalVariable.unitArray.add(ram);
        unitToRun.add(ram);
        GlobalVariable.componentArray.add(unitUI);
        RAM = ram;
        unitUI.setName("RAM");
        unitUI.setLocation(500,100);
        return unitUI;
    }

    void initMachine() {
        WorkingPanelDelegate workingPanelDelegate = new WorkingPanelDelegate();
        // Index, X, Y
        int[][] unitArray = new int[][]{
                {1,300,200},    // DataOutMUX2
                {1,500,200},    // AddressMUX2
                {3,350,280},    // PC
                {6,250,400},    // ALU
                {4,150,300},    // Acc
                {5,600,300},    // IR
                {7,800,300},    // Ctrl
                {2,700,400}     // DataInMUX2
        };
        for (int arrI = 0; arrI < unitArray.length; arrI++) {
            int[] tempArr = unitArray[arrI];
            // Copy UI
            Component component = EventUtil.copyUnitFrom(unitPanel.getComponent(tempArr[0]), false);
            component.setLocation(tempArr[1],tempArr[2]);
            // Add delegate
            component.addMouseListener(workingPanelDelegate);
            component.addMouseMotionListener(workingPanelDelegate);
            // Init Logic
            EventUtil.initLogi((BaseUnitUI) component);
            // Add to panel
            workPanel.add(component);
        }
        // Line Initial
        // inUnitIndex, outUnitIndex, inIndex
        int[][] lineArray = new int[][]{
                {0, 1, 0},  // DataOut->RAM
                {0, 2, 1},  // Addr->RAM
                {1, 3, 2},  // PC->DataOut
                {2, 3, 1},  // PC->Addr
                {4, 1, 1},  // DataOut->ALU
                {3, 4, 0},  // ALU->PC
                {6, 0, 0},  // RAM->IR
                {7, 6, 0},  // IR->Ctrl
                {2, 6, 2},  // IR->Addr
                {8, 0, 1},  // RAM->DataIn
                {4, 8, 2},  // DataIn->ALU
                {5, 4, 0},  // ALU->Acc
                {1, 5, 1},  // Acc->DataOut
                {8, 6, 2}   // IR->DataIn
        };
        for (int arrI = 0; arrI < lineArray.length; arrI++) {
            int[] tempArr = lineArray[arrI];
            SuperUnit above = GlobalVariable.unitArray.get(tempArr[0]);
            SuperUnit below = GlobalVariable.unitArray.get(tempArr[1]);
            below.setOut(above.getInAt(tempArr[2]));
            // Line init
            Point bePoint = below.unitUI.getComponent(below.unitUI.getComponentCount() - 2).getLocation();
            bePoint = EventUtil.transformToSuperLoca(bePoint, below.unitUI);
            bePoint.x += GlobalVariable.actionWidth / 2;
            bePoint.y += GlobalVariable.actionHeight / 4;
            Point abPoint = above.unitUI.getComponent(tempArr[2]).getLocation();
            abPoint = EventUtil.transformToSuperLoca(abPoint, above.unitUI);
            abPoint.x += GlobalVariable.actionWidth / 2;
            Line line = new Line(bePoint, bePoint, below.unitUI.isUpsideDown());
            if (above.unitUI.getName().contains("RAM")) {
                line.setEndUpsideDown(false);
            } else {
                line.setEndUpsideDown(above.unitUI.isUpsideDown());
            }
            if (arrI == 4) {
                line.minHWid = 150;
            }
            if (arrI == 6) {
                line.ratio = 0.3;
            }
            if (arrI == 9) {
                line.ratio = 0.1;
            }
            // Save Line
            below.setOutLine(line, above.getInAt(tempArr[2]));
            above.setInLines(line, tempArr[2]);
            line.updateEndPoint(abPoint);
            line.destination = above;
            line.originUnit = below;
            line.destIndex = tempArr[2];
            GlobalVariable.workPanel.add(line);
            workPanel.updateUI();
        }
        workPanel.updateUI();
    }
}
