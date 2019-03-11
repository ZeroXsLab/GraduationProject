package SyncTest;

public class Data {

    private String name;
    private boolean beenRead = true;

    public boolean isBeenRead() {
        return beenRead;
    }

    public void setBeenRead(boolean beenRead) {
        this.beenRead = beenRead;
    }

    public synchronized void write(String name){
        if (!this.beenRead) {
            System.out.println("Wait to write: " + name);
            try {
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        this.name = name;
        this.beenRead = false;
        System.out.println("Write successfully\t\t" + name);
        notify();
    }

    public synchronized void read(String name){
        if (this.beenRead) {
            System.out.println("wait to read: " + name);
            try{
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.name = name;
        this.beenRead = true;
        System.out.println("Read successfully\t\t" + name);
        notify();
    }
}
