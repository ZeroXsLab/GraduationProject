/*
 *
 * BaseUnitUI.java
 * GraduationProject
 *
 * Created by X on 2019/4/10
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import javax.swing.*;

import java.awt.*;

import static PCOVL.UI.GlobalVariable.*;

// this.getComponents return order: In->Out->TextLabel
public class BaseUnitUI extends JPanel{
    private JLabel textLabel;
    private JLabel[] in, out;
    private UnitPanelDelegate unitPanelDelegate = new UnitPanelDelegate();

    private boolean isUpsideDown = false;
    // whether we show the control label so we are able to link to it.
    private boolean isTheFirstInControl = false;

    public BaseUnitUI(String text, int inputNum, int outputNum, boolean isUpsideDown, boolean isTheFirstInControl) {
        this.setName("BaseUnitUI JPanel");
        this.setLayout(null);
        this.setSize(unitWidth, unitHeight);
        this.isUpsideDown = isUpsideDown;
        this.isTheFirstInControl = isTheFirstInControl;
        initSubView(text, inputNum, outputNum);
        // Add listener
        this.addMouseListener(unitPanelDelegate);
        this.addMouseMotionListener(unitPanelDelegate);
    }

    // Clone Function
    public BaseUnitUI(BaseUnitUI original, Boolean withDelegate) {
        this.setName(original.getName());
        this.setLayout(original.getLayout());
        this.setSize(original.getWidth(), original.getHeight());
        this.isUpsideDown = original.isUpsideDown;
        this.isTheFirstInControl = original.isTheFirstInControl;
        initSubView(original.textLabel.getText(), original.in.length, original.out.length);
        if (withDelegate){
            this.addMouseListener(unitPanelDelegate);
            this.addMouseMotionListener(unitPanelDelegate);
        }
    }

    void initSubView(String text, int inputNum, int outputNum) {
        int actionY;

        this.in = new JLabel[inputNum];
        actionY = isUpsideDown ? 0 : unitHeight - actionHeight;
        setActionLabels(this.in, "In", inputNum, inColor, actionY);

        this.out = new JLabel[outputNum];
        actionY = isUpsideDown ? unitHeight - actionHeight : 0;
        setActionLabels(this.out, "Out", outputNum, outColor, actionY);

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
    void setActionLabels(JLabel[] actionLabels, String withText, int totalCount, Color[] colors, int actionY) {
//        actionLabels = new JLabel[totalCount];
        // the real indexInUnitUI of the label as it may be upside down
        int indexInUnitUI;
        for (int iAc = 0; iAc < totalCount; iAc ++) {
            indexInUnitUI = isUpsideDown ? totalCount - iAc - 1 : iAc;
            actionLabels[iAc] = new JLabel();
            actionLabels[iAc].setName(withText + " " + iAc);
            actionLabels[iAc].setText("" + iAc);
            actionLabels[iAc].setHorizontalAlignment(SwingConstants.CENTER);
//            actionLabels[iAc].setToolTipText(withText + " " + iAc);   //when we add this, the whole label unable to get the mouseEvent. Fix later.
            actionLabels[iAc].setBackground(colors[iAc]);
            if (this.isTheFirstInControl && iAc == 0 && withText.equals("In")) {
                // we are initial the control In, set it to different color.
                actionLabels[iAc].setBackground(Color.green);
            }
            actionLabels[iAc].setOpaque(true);        // Otherwise we can't see the background color
            actionLabels[iAc].setBounds((indexInUnitUI + 1) * (unitWidth - totalCount*actionWidth) / (totalCount + 1) + indexInUnitUI*actionWidth,
                    actionY,
                    actionWidth,
                    actionHeight);
            this.add(actionLabels[iAc]);
        }

    }

    public void setText(String text) {
        this.textLabel.setText(text);
    }
}
