/*
 *
 * pauseDemo.java
 * GraduationProject
 *
 * Created by X on 2019/5/18
 * Copyright (c) 2019 X. All right reserved.
 *
 */

package JavaTest;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class pauseDemo extends JFrame{
    private static final long serialVersionUID = 1L;
    private JButton startBtn = null;
    private JButton stopBtn = null;
    private JPanel mainPane = null;
//    private ThreadTest thread = null;
    public pauseDemo(){
        initGui();
        addListener();
    }
    private void initGui() {
        startBtn = new JButton("开始");
        stopBtn = new JButton("结束");
        Dimension dm = new Dimension(71, 21);
        startBtn.setPreferredSize(dm);
        stopBtn.setPreferredSize(dm);
        mainPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 50));
        mainPane.add(startBtn);
        mainPane.add(stopBtn);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setContentPane(mainPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void addListener(){
        startBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
//                thread = new ThreadTest();
//                thread.start();
                ThreadTest one = new ThreadTest(0);
                ThreadTest two = new ThreadTest(100);
                one.start();
                two.start();
            }
        });
        stopBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                ThreadTest.currentThread().stop();
            }
        });
    }
    public static void main(String[] args) {
        new pauseDemo();
    }
    class ThreadTest extends Thread{
        public int count = 0;
        public boolean runFlag = true;
        public ThreadTest(int count) {
            this.count = count;
        }
        public void run(){
            runFlag = true;
            while(runFlag){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                if(runFlag){
//                    // TODO 这里加需要线程执行的事件
                    count ++;
                    System.out.println("线程正在运行!!!!!!!!\t" + count);
//                }
            }
        }
        public void stopThread(){
            runFlag = false;
        }
    }
}
