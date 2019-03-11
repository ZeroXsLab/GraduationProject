package SyncTest;

public class SuperBtn extends Thread{
    private Data in = new Data();
    private Data out = new Data();
    private int ID;

    public void init() {
        this.in.setBeenRead(false);
    }

    public SuperBtn(int ID) {
        this.ID = ID;
    }

    public Data getIn() {
        return in;
    }

    public void setOut(Data out) {
        this.out = out;
    }

    public void finish() {
        in.setBeenRead(true);
        out.setBeenRead(true);
    }

    @Override
    public void run() {
        super.run();
        in.read(this.getClass().getName() + " " + ID);
        out.write(this.getClass().getName() + " " + ID);
    }
}
