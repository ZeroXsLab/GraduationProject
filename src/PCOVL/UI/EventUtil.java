/*
 *
 * EventUtil.java
 * GraduationProject
 *
 * Created by X on 2019/4/12
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
        System.out.println("......................Process a instruction");
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < GlobalVariable.unitToRun.size(); i ++){
            threads.add(new Thread(GlobalVariable.unitToRun.get(i)));
            threads.get(threads.size() - 1).start();
        }
        if (Controller.signal[0] == 0) {
            // In Fetch State, begin from PC
            System.out.println("Fetching Instruction............");
            if (GlobalVariable.programCounter != null) {
                threads.add(0,new Thread(GlobalVariable.programCounter));
                threads.get(0).start();
                GlobalVariable.programCounter.readyForRead();
            }
        } else {
            // In Execute State, begin from IR
            System.out.println("Executing Instruction.............");
            for (int i = 0; i < GlobalVariable.unitToRun.size() ; i++) {
                if (GlobalVariable.unitToRun.get(i).unitUI.getName().contains("IR")) {
                    GlobalVariable.unitToRun.get(i).readyForRead();
                    break;
                }
            }
        }
        try {
            threads.get(0).join();
        }catch (Exception ex){
            System.out.println("Exception occur at joining");
            ex.printStackTrace();
        }
        // Reset the status of the last unit if you know which is. if not, reset all
        for (int i = 0; i < GlobalVariable.unitToRun.size(); i ++) {
            (GlobalVariable.unitToRun.get(i)).finish();
        }
        // Change Controller Signal after all unit run.
        if (GlobalVariable.controller != null) {
            if (Controller.signal[0] == 0) {
                GlobalVariable.controller.generateSignal();
            } else {
                Controller.signal = Controller.signalTable[0];
            }
        }
        System.out.println("......................Finish a instruction");
    }
}
