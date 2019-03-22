/*
 *
 * HomePage.java
 * GraduationProject
 *
 * Created by X on 2019/3/23
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;
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
        this.setVisible(true);
    }

    void initSubView(){
        unitPanel = new JPanel();
        unitPanel.setName("UnitPanel");
        unitPanel.setSize(unitWidth + 20, this.getHeight());
        unitPanel.setLayout(null);

        workingPanel = new JPanel();
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
        BaseUnitUI unit1 = new BaseUnitUI("First", 1, 1);
        unit1.setLocation(10,10);
        unitPanel.add(unit1);
        BaseUnitUI unit2 = new BaseUnitUI("Second", 2, 2);
        unit2.setLocation(10, 20 + unitHeight);
        unitPanel.add(unit2);
    }
}
