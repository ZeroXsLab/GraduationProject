/*
 *
 * EventUtil.java
 * GraduationProject
 *
 * Created by X on 2019/5/8
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import PCOVL.UnitRepository.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventUtil {
    public static Point getAbsolutePointBy(MouseEvent event) {
        Point point = new Point();
        Component component = event.getComponent();
        point.x = component.getX() + event.getX();
        point.y = component.getY() + event.getY();
        return point;
    }

    public static Point transformToRelative(Point point, Component component) {
        point.x -= component.getX();
        point.y -= component.getY();
        return point;
    }

    public static Point transformToSuperLoca(Point point, Component component) {
        point.x += component.getX();
        point.y += component.getY();
        return point;
    }

    public static Component copyUnitFrom(Component component, Boolean withDelegate){
        Class unitClass = component.getClass();
        try {
            Constructor unitConstructor = unitClass.getDeclaredConstructor(unitClass, Boolean.class);
            component = (Component) unitConstructor.newInstance(component, withDelegate);
        }catch (Exception ex) {
            System.out.println("Exception occur when copying " + component.getName() + " by mouse event");
            ex.printStackTrace();
        }
        return component;
    }

    public static Boolean isPointInComponents(MouseEvent event, String comName) {
        Point abPoint = EventUtil.getAbsolutePointBy(event);
        /* When the mouse out of the original Component, what we want to know is
        the component which contain the mouse(Below variable)
        rather than the original component(e.getComponent).
          */
        Component componentOfMouse = event.getComponent().getParent().getComponentAt(abPoint);
        Component subComponent = componentOfMouse.getComponentAt(EventUtil.transformToRelative(abPoint, componentOfMouse));
        if (subComponent instanceof JLabel) {
            String name = subComponent.getName();
            if (name != null && name.contains(comName)) {
                return true;
            }
        }
        return false;
    }

    public static void initLogi(BaseUnitUI logicComponent) {
        switch (logicComponent.getName()) {
            case "Switch" :
                Switch aSwitch = new Switch(logicComponent);
                GlobalVariable.unitArray.add(aSwitch);
                GlobalVariable.unitToRun.add(aSwitch);
                GlobalVariable.componentArray.add(logicComponent);
                logicComponent.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            int index = GlobalVariable.componentArray.indexOf(e.getComponent());
                            Switch switchClick = (Switch) GlobalVariable.unitArray.get(index);
                            switchClick.switchIt();
                        }
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
                });
                break;
            case "MUX2" :
            case "MUX2r":
                MUX2 mux2 = new MUX2(logicComponent);
                GlobalVariable.unitArray.add(mux2);
                GlobalVariable.unitToRun.add(mux2);
                GlobalVariable.componentArray.add(logicComponent);
                break;
            case "PC" :
                PC pc = new PC(logicComponent);
                GlobalVariable.unitArray.add(pc);
                GlobalVariable.componentArray.add(logicComponent);
                GlobalVariable.programCounter = pc;
                break;
            case "Acc" :
            case "IR":
                Register register = new Register(logicComponent);
                GlobalVariable.unitArray.add(register);
                GlobalVariable.unitToRun.add(register);
                GlobalVariable.componentArray.add(logicComponent);
                break;
            case "ALU" :
                ALU alu = new ALU(logicComponent);
                GlobalVariable.unitArray.add(alu);
                GlobalVariable.unitToRun.add(alu);
                GlobalVariable.componentArray.add(logicComponent);
                break;
            case "Ctrl" :
                Controller controller = new Controller(logicComponent);
                GlobalVariable.unitArray.add(controller);
                GlobalVariable.unitToRun.add(controller);
                GlobalVariable.componentArray.add(logicComponent);
                GlobalVariable.controller = controller;
                break;
                default:
                    break;
        }
    }

    public static void executeInstruction(){
        DataUtil.storeRelationship();
        System.out.println("......................Process a instruction");
        // Reset the status of the last unit if you know which is. if not, reset all
        for (int i = 0; i < GlobalVariable.unitArray.size(); i ++) {
            (GlobalVariable.unitArray.get(i)).finish();
        }
        // Change Controller Signal after all unit run.
        if (GlobalVariable.controller != null) {
            if (Controller.signal[0] == 0) {
                GlobalVariable.controller.generateSignal();
            } else {
                Controller.signal = Controller.signalTable[0];
            }
        }
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < GlobalVariable.unitToRun.size(); i ++){
            threads.add(new Thread(GlobalVariable.unitToRun.get(i)));
            threads.get(threads.size() - 1).start();
        }
        int joinIndex = 0;
        if (Controller.signal[0] == 0 || Controller.signal[0] == -1) {  // when equal to -1, it's the original state, also support unit test.
            // In Fetch State, begin from PC
            System.out.println("Fetching Instruction............");
            if (GlobalVariable.programCounter != null) {
                threads.add(0,new Thread(GlobalVariable.programCounter));
                threads.get(0).start();
                GlobalVariable.programCounter.readyForRead();
                joinIndex = 0;
            }
        } else {
            // In Execute State, begin from IR
            System.out.println("Executing Instruction.............");
            if (Controller.signal == Controller.signalTable[8]) {
                // Going to Execute STP instruction
                JOptionPane.showMessageDialog(null,
                        "All Instruction Has been Executed.",
                        "Completed",
                        JOptionPane.INFORMATION_MESSAGE);
                GlobalVariable.runBtn.setEnabled(false);
            }
            for (int i = 0; i < GlobalVariable.unitToRun.size() ; i++) {
                if (GlobalVariable.unitToRun.get(i).unitUI.getName().contains("IR")) {
                    joinIndex = i;
                    break;
                }
            }
        }
        try {
            threads.get(joinIndex).join();
        }catch (Exception ex){
            System.out.println("Exception occur at joining");
            ex.printStackTrace();
        }
        System.out.println("......................Finish a instruction");
    }

    public static boolean initMemory() {
        RAM memory = (RAM) GlobalVariable.RAM;
        String inputString = (String) JOptionPane.showInputDialog(
                null,
                "Enter the instruction and memory content, separate by ';'",
                "Input Something",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "lda 12, jne 3, add 13, sub 13, sta 14, stp; -13, 19, 0");
        if (inputString == null) {
            // user cancel, init failed.
            return false;
        }
        String[] formatStr = inputString.split(";");
        if (formatStr.length != 2 || formatStr[0].length() < 1 || formatStr[1].length() < 1) {
            JOptionPane.showMessageDialog(null,
                    "Instruction and memory content only!\nPlease try again",
                    "Syntax Error",
                    JOptionPane.ERROR_MESSAGE);
            // syntax error, init failed.
            return false;
        }
        // init the content, 0-11 is instruction, 12-23 is content.
        int[] content = new int[24];
        String regex = "([a-z|A-Z]{3}\\s\\d*)|([a-z|A-Z]{3})";
        ArrayList<String> instruction = getMatches(formatStr[0], regex);
        for (int iIns = 0; iIns < instruction.size() && iIns < 12; iIns++) {
            String str = instruction.get(iIns);
            int addr;
            if (str.length() > 3) {
                addr = Integer.parseInt(getMatches(str, "(\\d+)").get(0));
            } else {
                addr = 0;
            }
            switch (str.substring(0,3).toUpperCase()){
                case "LDA" :
                    content[iIns] = addr;
                    break;
                case "STA" :
                    content[iIns] = addr + 4096;
                    break;
                case "ADD" :
                    content[iIns] = addr + 8192;
                    break;
                case "SUB" :
                    content[iIns] = addr + 12288;
                    break;
                case "JMP" :
                    content[iIns] = addr + 16384;
                    break;
                case "JGE" :
                    content[iIns] = addr + 20480;
                    break;
                case "JNE" :
                    content[iIns] = addr + 24576;
                    break;
                case "STP" :
                    content[iIns] = addr + 28672;
                    break;
            }
        }
        ArrayList<String> contents = getMatches(formatStr[1], "(-?\\d)+");
        for (int iCon = 0; iCon < contents.size() && iCon < 12; iCon++) {
            content[12 + iCon] = Integer.parseInt(contents.get(iCon));
        }
        memory.setMemory(content);
        return true;
    }

    static ArrayList<String> getMatches(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        ArrayList<String> strings = new ArrayList<>();
        while (matcher.find()) {
            strings.add(matcher.group(0));
        }
        return strings;
    }
}
