/*
 *
 * stringFormat.java
 * GraduationProject
 *
 * Created by X on 2019/5/3
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class stringFormat {
    public static void main(String[] args){
        String url = "lda 000, add 223, sta 354646";
        String regex = "([a-z|A-Z]{3}\\s\\d*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        int i = 1;
        while (matcher.find()) {
            System.out.println(matcher.group(0) + "\t" + i);
            i++;
        }
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
