/*
 *
 * EventUtil.java
 * GraduationProject
 *
 * Created by X on 2019/3/23
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;

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

    public static Component copyUnitByEvent(Component component, Boolean withDelegate){
        Class unitClass = component.getClass();
        try {
            Constructor unitConstructor = unitClass.getDeclaredConstructor(unitClass, Boolean.class);
            component = (Component) unitConstructor.newInstance(component, withDelegate);
        }catch (Exception ex) {
            System.out.println("Exception occur when copying a unit by mouse event");
            ex.printStackTrace();
        }
        return component;
    }
}
