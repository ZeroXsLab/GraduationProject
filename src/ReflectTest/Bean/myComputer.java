package ReflectTest.Bean;

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
