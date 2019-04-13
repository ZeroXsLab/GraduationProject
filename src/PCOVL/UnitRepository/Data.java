/*
 *
 * Data.java
 * GraduationProject
 *
 * Created by X on 2019/4/13
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

public class Data {

    // whether the unit has got and process the inData, otherwise others should wait before write it.
    private int beenRead = -1;
    // the content 
    int content = 0;

    public void setBeenRead(int beenRead) {
        this.beenRead = beenRead;
    }

    public synchronized void write(String name){
        String desc = DataUtil.getDesc(Integer.toHexString(this.hashCode()));
        if (desc == null) {
            desc = "NULL~" + Integer.toHexString(this.hashCode());
        }
        if (this.beenRead == 0) {
            System.out.println(name + "\twait to write..." + desc );
            try {
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        this.beenRead = 0;
        System.out.println(name + "\tWrite at " + desc + ":\t" + content);
        notify();
    }

    public synchronized void read(String name){
        String desc = DataUtil.getDesc(Integer.toHexString(this.hashCode()));
        if (desc != null) {
            desc = desc.substring(desc.length() - 4);
        } else {
            desc = "NULL~" + Integer.toHexString(this.hashCode());
        }
        if (this.beenRead == 1 || this.beenRead == -1) {
            System.out.println(name + "\twait to read..." + desc );
            try{
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.beenRead = 1;
        System.out.println(name + "\tRead at " + desc + ":\t" + content);
        notify();
    }
}
