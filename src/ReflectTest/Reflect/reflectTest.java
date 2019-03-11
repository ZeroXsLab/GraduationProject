package ReflectTest.Reflect;

import ReflectTest.Bean.USB;
import ReflectTest.Bean.myComputer;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class reflectTest {
    public static void main(String[] args) throws Exception{
        myComputer mc = new myComputer();
        //MARK ActionEvent.getSource().getClass().getName()
        //By Code
//        String className = "ReflectTest.Bean.Mouse";

        //By Config File
//        File config = new File(reflectTest.class.getResource("").toURI().getPath() + "tempFile\\usb.config");
//        System.out.println(config.getAbsolutePath());
//        FileInputStream fis = new FileInputStream(config);
//        Properties prop = new Properties();
//        prop.load(fis);
//        String className = null;
//        className = prop.getProperty("usb");

        //By Function
        String className = LoadFile();

        Class<?> c = Class.forName(className);
        Object obj = c.newInstance();

        USB usb = (USB)obj;
        mc.useUSB(usb);
    }

    public static String LoadFile() throws Exception{
        File config = new File(reflectTest.class.getResource("").toURI().getPath() + "tempFile\\usb.config");
        System.out.println(config.getAbsolutePath());
        FileInputStream fis = new FileInputStream(config);
        Properties prop = new Properties();
        prop.load(fis);
        String className = null;
        className = prop.getProperty("usb");
        return className;
    }
}
