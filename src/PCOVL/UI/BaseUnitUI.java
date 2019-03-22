/*
 *
 * BaseUnitUI.java
 * GraduationProject
 *
 * Created by X on 2019/3/23
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;
import java.awt.*;

import static PCOVL.UI.GlobalVariable.*;

public class BaseUnitUI extends JPanel{
    private JLabel textLabel;
    private JLabel[] in, out;
    private UnitPanelDelegate unitPanelDelegate = new UnitPanelDelegate();

    public BaseUnitUI(String text, int inputNum, int outputNum) {
        this.setName("BaseUnitUI JPanel");
        this.setLayout(null);
        this.setSize(unitWidth, unitHeight);
        initSubView(text, inputNum, outputNum);
        // Add listener
        this.addMouseListener(unitPanelDelegate);
        this.addMouseMotionListener(unitPanelDelegate);
    }

    // Clone Function
    public BaseUnitUI(BaseUnitUI original, Boolean withDelegate) {
        this.setName(original.getName() + "(Clone)");
        this.setLayout(original.getLayout());
        this.setSize(original.getWidth(), original.getHeight());
        initSubView(original.textLabel.getText(), original.in.length, original.out.length);
        if (withDelegate){
            this.addMouseListener(unitPanelDelegate);
            this.addMouseMotionListener(unitPanelDelegate);
        }
    }

    void initSubView(String text, int inputNum, int outputNum) {

        this.in = new JLabel[inputNum];
        setActionLabels(this.in, "In", inputNum, unitHeight - actionHeight);

        this.out = new JLabel[outputNum];
        setActionLabels(this.out, "Out", outputNum,0);

        textLabel = new JLabel(text);
        textLabel.setName(text);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setBounds(0, actionHeight / 2, unitWidth, unitHeight - actionHeight);
        textLabel.setBackground(labelColor);
        textLabel.setOpaque(true);
        this.add(textLabel);
    }

    // for actionY:
    // (unitHeight - actionHeight) For In
    // 0 For Out
    void setActionLabels(JLabel[] actionLabels, String withText, int totalCount, int actionY) {
//        actionLabels = new JLabel[totalCount];
        for (int iAc = 0; iAc < totalCount; iAc ++) {
            actionLabels[iAc] = new JLabel();
            actionLabels[iAc].setName(withText + " " + iAc);
            actionLabels[iAc].setToolTipText(withText + " " + iAc);
            actionLabels[iAc].setBackground(actionColor[iAc]);
            actionLabels[iAc].setOpaque(true);        // Otherwise we can't see the background color
            actionLabels[iAc].setBounds((iAc + 1) * (unitWidth - totalCount*actionWidth) / (totalCount + 1) + iAc*actionWidth,
                    actionY,
                    actionWidth,
                    actionHeight);
            this.add(actionLabels[iAc]);
        }

    }
}
