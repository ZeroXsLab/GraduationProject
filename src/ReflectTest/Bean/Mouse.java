package ReflectTest.Bean;

public class Mouse implements USB {

    @Override
    public void connection() {
        System.out.println("Mouse is running...");
    }

    @Override
    public void close() {
        System.out.println("Mouse Connection Lost!");
    }
}
