/*
 *
 * stringFormat.java
 * GraduationProject
 *
 * Created by X on 2019/4/5
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

public class stringFormat {
    public static void main(String[] args){
//        int panelWidth = 300;
//        int output = 200;
//        int buttonWidth = 30;
//        int i = 1;
//        System.out.println("@(i + 1) * (@panelWidth - @output*@buttonWidth) / @(output + 1) + @i*@buttonWidth");
//        easyOutput("@(i + 1) * (@panelWidth - @output*@buttonWidth) / @(output + 1) + @i*@buttonWidth");
//        System.out.println((i + 1) + " * ("+ panelWidth +" - "+ output +"*"+buttonWidth+") / "+(output + 1) +" + "+ i+"*"+buttonWidth);
//        String a = null;
//        if (a.contains("a")) {
//            System.out.println("AAA");
//        } else {
//            System.out.println("BBB");
//        }
        String origin = "PCOVL.UnitRepository.Switch 1";
        String[] s = origin.split(" ");
        System.out.println();
    }

    public static void easyOutput(String string){
        String regex = "(@[a-zA-Z_$][a-zA-Z0-9_$]*)|(@\\([^)]*\\))";
        String format = string.replaceAll(regex,"%d");
        System.out.println(format);
        String[] arg = format.split("%d");
        for (String s:arg
             ) {
            System.out.print(string.indexOf(s) + "\t");
        }
    }
}
