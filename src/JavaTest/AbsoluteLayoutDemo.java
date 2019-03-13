/*
 *
 * AbsoluteLayoutDemo.java
 * GraduationProject
 *
 * Created by X on 2019/3/13
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AbsoluteLayoutDemo extends JFrame {
    private JPanel contentPane;//创建面板
    private JButton button1;//创建按钮1
    private JButton button2;//创建按钮2
    public AbsoluteLayoutDemo()
    {
        this.setTitle("绝对布局");//设置标题名字
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认退出
        this.setBounds(100, 100, 500, 500);//设置窗体的大小
        this.contentPane=new JPanel();//初始化面板
        this.contentPane.setLayout(null);//设置布局NULL
        this.button1=new JButton("按钮1");//给按钮名字
        this.button1.setBounds(6,20,90,30);//设置按钮名字
        this.contentPane.add(button1);//加入面板中
        this.button2=new JButton("按钮2");
        this.contentPane.add(button2);
        this.button2.setBounds(138, 26, 90, 30);
        this.add(this.contentPane);
        this.setVisible(true);

    }
    public static void main(String[]args)
    {
        AbsoluteLayoutDemo example=new AbsoluteLayoutDemo();
    }
}
