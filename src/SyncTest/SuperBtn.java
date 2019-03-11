package SyncTest;

public class SuperBtn extends Thread{
    private Data in, out;
    private int ID;

    public SuperBtn(Data in, Data out, int ID) {
        this.in = in;
        this.out = out;
        this.ID = ID;
    }

    @Override
    public void run() {
        super.run();
        in.read(this.getClass().getName() + " " + ID);
        out.write(this.getClass().getName() + " " + ID);
    }
}
