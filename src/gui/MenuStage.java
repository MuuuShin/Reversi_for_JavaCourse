package gui;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuStage extends JPanel{
    JButton jButton1=new JButton("新的一局");
    JButton jButton2=new JButton("继续对局");
    JButton jButton3=new JButton("用户信息");
    Font f2 = new Font("微软雅黑", Font.BOLD,25);
    public MenuStage() throws IOException {
        jButton1.setFont(f2);
        jButton2.setFont(f2);
        jButton3.setFont(f2);
        JLabel jLabel=new JLabel("");
        setLayout(null);
        jButton1.setBounds(500,80,200,70);
        jButton2.setBounds(500,170,200,70);
        jButton3.setBounds(500,260,200,70);
        add(jButton1);
        add(jButton2);
        add(jButton3);
    }
}
