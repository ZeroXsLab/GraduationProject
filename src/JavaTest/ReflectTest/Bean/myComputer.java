/*
 *
 * myComputer.java
 * GraduationProject
 *
 * Created by X on 2019/3/13
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest.ReflectTest.Bean;

public class myComputer {
    public void run() {
        System.out.println("Running...");
    }
    public void close(){
        System.out.println("Closed!");
    }
    public void useUSB(USB usb){
        if(usb != null) {
            usb.connection();
            usb.close();
        }
    }
}
