package gui;

import gui.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.ToLongBiFunction;

public class ToolStage extends JPanel {
    JButton backBtn = new JButton("退出");
    Font f2 = new Font("微软雅黑", Font.BOLD, 25);

    public ToolStage() {
        backBtn.setFont(f2);
        backBtn.setForeground(Color.red);
        setLayout(null);
        backBtn.setBounds(500, 410, 200, 70);
        add(backBtn);
    }

    public static void main(String[] args) {
        JPanel jPanel = new JPanel();
        int a = 2;
        a += 15.6;
        System.out.println(a);
        a -= 1.9;
        System.out.println(jPanel.equals("1"));
        Scanner sc = new Scanner(System.in);
        float d = sc.nextFloat();
        float g = sc.nextFloat();
        float f = sc.nextFloat();
        float s = sc.nextFloat();
        System.out.println(d / g);
        System.out.println(f / s);
        System.out.println(d / g / (f / s));
    }
}

class SuperClass {
    public SuperClass() {
        System.out.println("FromSuperClass");
    }
}

class SubClass extends SuperClass {
    final String name;

    public SubClass() {
        this("default");
        System.out.println("FromNon-argsSubClass");
    }

    public SubClass(String name) {
        this.name = name;
        System.out.printf("SubClass:name=\"%s\"\n", name);
    }

    public static void main(String[] args) {
        SubClass a = new SubClass();
        ArrayList<String> ar=new ArrayList<>();
        ar.add("as");
        ar.add("ak");
        System.out.println(ar.indexOf("as"));
        ar.add(ar.indexOf("as"),"Go");
        System.out.println(ar);System.out.println(ar.indexOf("as"));
    }
}
