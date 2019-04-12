/*
 *
 * DataUtil.java
 * GraduationProject
 *
 * Created by X on 2019/4/12
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package PCOVL.UnitRepository;

import PCOVL.UI.GlobalVariable;

public class DataUtil {

    static String getBinaryString(int number, int bits) {
        String string = Integer.toBinaryString(number);
        // Format the string into the same bits.
        if (string.length() > bits) {
            string = string.substring(string.length() - bits);
        } else {
            String zero = "0000000000000000";
            string = zero.substring(0, bits - string.length()) + string;
        }
        return string;
    }

    static int getInteger(String string, int radix) {
        int bits = string.length();
        // the max value that this bits can present in Two's Complement
        int maxValue = 1 << (bits - 1) - 1;
        int complementValue = maxValue << 1;
        int number = Integer.parseUnsignedInt(string, radix);
        if (number > maxValue) {
            // it is a negative number, so we should subtract it with complementValue
            number -= complementValue;
        }
        return number;
    }

    static String getComplement(String string) {
        int number = getInteger(string, 2);
        number = 0 - number;
        String str = getBinaryString(number, string.length());
        return str;
    }

    static String formatString(String name) {
        String tail = "----------------";
        int length = name.length();
        String formatted = name + tail.substring(length) + "@";
        return formatted;
    }

    public static void showLinkRelationship() {
        for (SuperUnit unit: GlobalVariable.unitToRun) {
            System.out.println(unit.unitUI.getName() + ":");
            for (int i = 0; i < unit.in.length; i++) {
                if (unit.in[i] != null) {
                    System.out.print("In" + i + " :" + Integer.toHexString(unit.in[i].hashCode()) + "\t");
                }
            }
            System.out.println();
            for (int i = 0; i < unit.out.length; i++) {
                if (unit.out[i] != null) {
                    System.out.print("Out" + i + " :" + Integer.toHexString(unit.out[i].hashCode()) + "\t");
                }
            }
            System.out.println();
        }
    }
}
