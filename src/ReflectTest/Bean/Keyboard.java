package ReflectTest.Bean;

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
