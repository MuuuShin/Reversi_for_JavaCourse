package gui;

import gui.view.GameFrame;
import logic.Game;
import logic.GameSystem;
import logic.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static logic.GameSystem.listIncompleteList;

public class MainFrame {

    public static int status=0;
    ToolStage toolStage =new ToolStage();
    JFrame jFrame = new JFrame("2021F CS102A Project Reversi");
    MenuStage menuStage = new MenuStage();
    InfoStage infoStage = new InfoStage();
    RoomStage roomStage= new RoomStage();
    public static int getStatus(){
        return status;
    }
    public static void setStatus(int statusadd){
        status=statusadd;
    }
    public MainFrame() throws IOException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        this.initializeMenuStage();
        this.initializeToolStage();
        this.displayFrame();
    }
    public void initializeToolStage() throws IOException {
        jFrame.add(toolStage);
        toolStage.setOpaque(false);
        toolStage.backBtn.addActionListener(
                e -> {
                    onClickBack();
                });
    }
    public void initializeMenuStage() throws IOException {
        JPanel panelTop;
        jFrame.add(menuStage);
        panelTop = (JPanel) jFrame.getContentPane();
        // paneL和lpaneLTop设置透明
        panelTop.setOpaque(false);
        menuStage.setSize(addBackground());

        menuStage.setOpaque(false);
        //panelTop，顶层容器
        jFrame.setSize(menuStage.getSize());
        menuStage.jButton1.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(menuStage, "请输入本局代号(不输入自动创建)", "新建", 1);
            if(name == null){
                return;
            }
            if(name.equals("")){
                name="game"+Game.getGameCnt();
            }
            System.out.println("add a new game");
            Player a, b;
            String namea,nameb;
            do {
                namea = JOptionPane.showInputDialog(menuStage, "输入A玩家名字(输\"AI\"即为AI)", "新建", 1);
            }while("".equals(namea));
            if(namea == null){
                return;
            }
            if (GameSystem.checkPlayer(namea)) {
                System.out.println(namea);
                a = GameSystem.findPlayer(namea);
            } else {
                a = new Player(namea);
                GameSystem.addPlayer(a);
            }
            do {
                nameb = JOptionPane.showInputDialog(menuStage, "输入B玩家名字(输\"AI\"即为AI)", "新建", 1);
                if(nameb.equals(namea)){
                    JOptionPane.showMessageDialog(menuStage, "已在黑方中被选中！", "警告", 2);
                }
            }while("".equals(nameb) || nameb.equals(namea));
            if(nameb == null){
                return;
            }
            if ((GameSystem.getPlayerList().size() != 0) && GameSystem.checkPlayer(nameb)) {
                b = GameSystem.findPlayer(nameb);
            } else {
                b = new Player(nameb);
                GameSystem.addPlayer(b);
            }
            Game g1=new Game(name, a, b);
            GameSystem.addGame(g1);

            initializeGameStage(g1,a,b);
        });
        menuStage.jButton2.addActionListener(e -> {
            initializeRoomStage();

        });
        menuStage.jButton3.addActionListener(e -> {
            initializeInfoStage();
        });
    }

    private void initializeInfoStage() {
        status=3;
        toolStage.backBtn.setText("返回");
        infoStage.initInfoStage();

        jFrame.add(infoStage);
        infoStage.setVisible(true);
        menuStage.setVisible(false);
    }

    private void initializeRoomStage() {
        listIncompleteList();
        status=2;
        toolStage.backBtn.setText("返回");
        roomStage.setOpaque(false);
        roomStage.initRoomStage(GameSystem.getNotOverList());

        jFrame.add(roomStage);
        menuStage.setVisible(false);
        roomStage.setVisible(true);
        int times=0;
        System.out.println(" init");
        for(JButton jBotton:roomStage.getButtonList()){
            int finalTimes = times;
            jBotton.addActionListener(e -> {
                jFrame.setVisible(false);
                System.out.println(" times"+ finalTimes);
                int width,height;
                width= jFrame.getWidth();
                height= jFrame.getHeight();
                Game game=GameSystem.findGame(GameSystem.getNotOverList().get(finalTimes));
                try {
                    new GameFrame(width,height,game,jFrame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                menuStage.setVisible(true);
                roomStage.setVisible(false);
                toolStage.backBtn.setText("退出");
            });
            times++;
        }

    }

    public void initializeGameStage(Game game, Player blackPlayer, Player whitePlayer){
        status=1;
        jFrame.setVisible(false);
        GameStage gameStage = new GameStage((int) jFrame.getWidth(), (int) jFrame.getHeight(),game,blackPlayer,whitePlayer,jFrame);
    }

    public void displayFrame() {
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Dimension addBackground() throws IOException {
        BufferedImage img;
        img = ImageIO.read(new File(".\\source\\background.jpg"));
        ImageIcon background = new ImageIcon(img);
        JLabel lbBg = new JLabel(background);
        lbBg.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        jFrame.getLayeredPane().add(lbBg, new Integer(Integer.MIN_VALUE));
        Dimension d = new Dimension(background.getIconWidth() - 400, background.getIconHeight());
        return d;
    }
    public void onClickBack() {
        if(status == 0){
            //退出
            System.exit(0);
        }else{
            if(status == 2){
                //roomStage
                status = 0;
                menuStage.setVisible(true);
                roomStage.setVisible(false);
                toolStage.backBtn.setText("退出");
            }else{
                if (status == 3) {
                    //infoStage
                    menuStage.setVisible(true);
                    infoStage.setVisible(false);
                    status = 0;
                    toolStage.backBtn.setText("退出");
                }
            }
        }
            /*if(status==0){
                //退出
                System.exit(0);
            }else {
                if (status == 1) {
                    menuStage.setVisible(true);
                    //删游戏界面
                }else{
                    if(status == 2){
                        //roomStage
                        menuStage.setVisible(true);
                        roomStage.setVisible(false);
                    }else{
                        //infoStage
                        menuStage.setVisible(true);
                        infoStage.setVisible(false);
                    }
                }
            }*/
    }

    public MenuStage getMenuStage() {
        return menuStage;
    }

    public static void main(String[] args) throws IOException {
        MainFrame mainFrame = new MainFrame();
        mainFrame.initializeMenuStage();
        mainFrame.displayFrame();
    }
}
