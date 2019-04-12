/*
 *
 * Data.java
 * GraduationProject
 *
 * Created by X on 2019/4/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

public class Data {

    // whether the unit has got and process the inData, otherwise others should wait before write it.
    private boolean beenRead = true;
    // the content 
    int content = 0;

    public void setBeenRead(boolean beenRead) {
        this.beenRead = beenRead;
    }

    public synchronized void write(String name){
        if (!this.beenRead) {
            System.out.println(name + "\twait to write..." + Integer.toHexString(this.hashCode()));
            try {
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        this.beenRead = false;
        System.out.println(name + "\tWrite at " + Integer.toHexString(this.hashCode()) + ":\t" + content);
        notify();
    }

    public synchronized void read(String name){
        if (this.beenRead) {
            System.out.println(name + "\twait to read..." + Integer.toHexString(this.hashCode()));
            try{
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.beenRead = true;
        System.out.println(name + "\tRead at " + Integer.toHexString(this.hashCode()) + ":\t" + content);
        notify();
    }
}
