/*
 *
 * unitB.java
 * GraduationProject
 *
 * Created by X on 2019/3/13
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL;

public class unitB extends SuperUnit {

    public unitB(int ID) {
        super(ID);
    }

    @Override
    public void processData() {
        out.content = in.content + 2;
    }
}
