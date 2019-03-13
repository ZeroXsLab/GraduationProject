/*
 *
 * LinkListDemo.java
 * GraduationProject
 *
 * Created by X on 2019/3/13
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import java.util.ArrayList;
import java.util.LinkedList;

public class LinkListDemo {
    public static void main(String[] args) {
        testArrayList();
    }

    public static void testArrayList() {
        ArrayList <String> arrayList1 = new ArrayList<>();
        String a = "a";
        String b = "b";
        String c = "c";
        arrayList1.add(a);
        arrayList1.add(b);
        for (String temp: arrayList1){
            System.out.println(temp);
        }
        arrayList1.add(arrayList1.indexOf(a),c);
        for (String temp: arrayList1){
            System.out.println(temp);
        }
    }
}
