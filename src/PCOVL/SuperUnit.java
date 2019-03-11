/*
 *
 * SuperUnit.java
 * GraduationProject
 *
 * Created by X on 2019/3/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

public class SuperUnit implements Runnable{
    private Data in = new Data();
    private Data out = new Data();
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
        in.read(this.getClass().getName() + " " + ID);
        out.write(this.getClass().getName() + " " + ID);
    }
}
