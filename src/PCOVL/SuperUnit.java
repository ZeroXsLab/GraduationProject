/*
 *
 * SuperUnit.java
 * GraduationProject
 *
 * Created by X on 2019/3/23
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

public class SuperUnit implements Runnable{
    protected Data in = new Data();
    protected Data out = new Data();
    protected Boolean inputEnable = true;
    private int ID;

    public void init() {
        this.in.setBeenRead(false);
    }

    public SuperUnit(int ID) {
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
        if (inputEnable) {
            in.read(this.getClass().getName() + " " + ID);
            processData();
            out.write(this.getClass().getName() + " " + ID);
        } else {
            // Do nothing
        }
    }

    // When a Unit extends, it should override this method to custom the function
    public void processData() {
        out.content = in.content + 1;
    }
}

// TODO: it contain a UI variable, so we can call function to link it with the exist UI.
