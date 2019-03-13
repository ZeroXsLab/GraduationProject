/*
 *
 * Keyboard.java
 * GraduationProject
 *
 * Created by X on 2019/3/13
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest.ReflectTest.Bean;

public class Keyboard implements USB {
    @Override
    public void connection() {
        System.out.println("Keyboard is running...");
    }

    @Override
    public void close() {
        System.out.println("Keyboard connection lost!");
    }
}
