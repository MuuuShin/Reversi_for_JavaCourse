package gui;

import logic.Game;
import logic.GameSystem;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class InfoStage extends JPanel {
    Font f1 = new Font("微软雅黑", Font.BOLD,16);
    ArrayList<JPanel> playerInfo = new ArrayList<JPanel>();//每个玩家一个JPanel 所有的JPanel存到一个LIst里
    JComboBox cmbPlayerList;
    JComboBox cmbListPlayerGame;
    JLabel label1 = new JLabel("Name: ");
    JLabel label2 = new JLabel("Pid: ");
    JLabel label3 = new JLabel("TotalGameNumber: ");
    JLabel label4 = new JLabel("WinGameNumber: ");
    JLabel label5 = new JLabel("LoseGameNumber: ");
    JLabel label6 = new JLabel("WinRate: ");
    JLabel label7 = new JLabel("LoseRate: ");

    public void initInfoStage(){
        label1.setText("Name: ");
        label2.setText("Pid: ");
        label3.setText("TotalGameNumber: ");
        label4 .setText("WinGameNumber: ");
        label5.setText("LoseGameNumber: ");
        label6.setText("WinRate: ");
        label7.setText("LoseRate: ");
        cmbListPlayerGame.setVisible(false);
        cmbPlayerList.setSelectedIndex(0);
    }



    public InfoStage(){
        label1.setFont(f1);
        label2.setFont(f1);
        label3.setFont(f1);
        label4.setFont(f1);
        label5.setFont(f1);
        label6.setFont(f1);
        label7.setFont(f1);
        ArrayList<Player> PlayerList=GameSystem.getPlayerList();
        String[] playerList=new String[PlayerList.size()+1];
        playerList[0]="--请选择玩家--";
        for(int i=1;i<PlayerList.size();i++){
            playerList[i]="Pid: "+PlayerList.get(i).getPid()+" Name: "+PlayerList.get(i).getName();
        }
        cmbPlayerList = new JComboBox(playerList);
        cmbPlayerList.setFont(f1);
        setLayout(new GridLayout(8,3,5,5));
        cmbListPlayerGame = new JComboBox();
        cmbListPlayerGame.setFont(f1);

        cmbListPlayerGame.setVisible(false);
        add(cmbPlayerList);
        add(new JLabel(""));
        add(cmbListPlayerGame);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(label1);
        add(label2);
        add(label3);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(label4);
        add(label5);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(label6);
        add(label7);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));

//每个玩家一个JPanel
        /*for(int i = 0; i< GameSystem.getPlayerList().size(); i++){
            JButton btn1 = new JButton(GameSystem.getPlayerList().get(i).getName());
            cmbPlayerList.addItem(btn1);
            ArrayList<Game> playerGame = GameSystem.listPlayerGame(GameSystem.getPlayerList().get(i).getPid());

            JPanel currentJP = new JPanel();
            currentJP.setLayout(new GridLayout(4,2,5,5));

            JLabel label1 = new JLabel("Name: "+GameSystem.getPlayerList().get(i).getName());
            JLabel label2 = new JLabel("Pid: "+GameSystem.getPlayerList().get(i).getPid());
            JLabel label3 = new JLabel("TotalGameNumber: "+GameSystem.calculatePlayerTotalGameNum(GameSystem.getPlayerList().get(i).getPid()));
            JLabel label4 = new JLabel("WinRate: "+GameSystem.calculatePlayerWinRate(GameSystem.getPlayerList().get(i).getPid()));
            JLabel label5 = new JLabel("WinGameNumber: "+GameSystem.getPlayerList().get(i).getWinGameNum());
            JLabel label6 = new JLabel("LoseGameNumber: "+GameSystem.getPlayerList().get(i).calculateLoseGameNum());
            currentJP.add(label1);
            currentJP.add(label2);
            currentJP.add(label3);
            currentJP.add(label4);
            currentJP.add(label5);
            currentJP.add(label6);

            JComboBox cmbListPlayerGame = new JComboBox();
            currentJP.add(cmbListPlayerGame);
            cmbListPlayerGame.addItem("--请选择棋局--");
            for(int t=0; t<playerGame.size(); t++){
                JButton btn2 = new JButton(String.valueOf(playerGame.get(t).getGid()));
                cmbListPlayerGame.addItem(btn2);
            }
            playerInfo.add(currentJP);
            add(currentJP);
            currentJP.setVisible(false);
        }

        /*(JButton)cmbPlayerList.getSelectedItem().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for(int k=0; k<
            }
        });*/

        /*cmbPlayerList.getSelectedItem().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                label.setText("按钮被单击了 "+(clicks++)+" 次");
            }
        });*/


         /* button1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                label.setText("按钮被单击了 "+(clicks++)+" 次");
            }
        });*/

/*menuStage.jButton3.addActionListener(e -> {
            initializeScoreStage();
        });*/

        cmbPlayerList.addItemListener(
                e -> {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        onChangeCombo1();
                    }
                }
        );
    }
    private void onChangeCombo1(){
        //System.out.println(cmbPlayerList.getSelectedIndex());
        // label2.setText("调用setText()方法");
        if(cmbPlayerList.getSelectedItem().equals("--请选择玩家--")){
            label1.setText("Name: ");
            label2.setText("Pid: ");
            label3.setText("TotalGameNumber: ");
            label4.setText("WinGameNumber: ");
            label5.setText("LoseGameNumber: ");
            label6.setText("WinRate: ");
            label7.setText("LoseRate: ");
            cmbListPlayerGame.removeAllItems();
            cmbListPlayerGame.addItem("--已玩棋局--");
        }else {
            Player selectPlayer=GameSystem.getPlayerList().get(cmbPlayerList.getSelectedIndex());
            GameSystem.calculatePlayercontinueNum(selectPlayer.getPid());
            label1.setText("Name: " + selectPlayer.getName());
            label2.setText("Pid: " + selectPlayer.getPid());
            label3.setText("TotalGameNumber: " + selectPlayer.calculatePlayerTotalGameNum());
            label4.setText("WinGameNumber: " + GameSystem.calculatePlayerWinNum(selectPlayer.getPid()));
            label5.setText("LoseGameNumber: " + selectPlayer.calculateLoseGameNum());
            label6.setText("WinRate: " + 100*selectPlayer.calculateWinRate()+"%");
            label7.setText("LoseRate: "+100*selectPlayer.calculateLoseRate()+"%");

            cmbListPlayerGame.removeAllItems();
            cmbListPlayerGame.addItem("--已玩棋局--");
            for(int i=0; i<GameSystem.listPlayerGame(cmbPlayerList.getSelectedIndex()).size(); i++){
                String over="(进行中)";
                if(GameSystem.listPlayerGame(cmbPlayerList.getSelectedIndex()).get(i).getIsOver()){over="(已结束)";}
                cmbListPlayerGame.addItem("Gid: "+GameSystem.listPlayerGame(cmbPlayerList.getSelectedIndex()).get(i).getGid()+" Name: "+GameSystem.listPlayerGame(cmbPlayerList.getSelectedIndex()).get(i).getName()+over);
            }
            cmbListPlayerGame.setVisible(true);
            /*ArrayList<Game> GameList = GameSystem.getGameList();
            String[] gameList = new String[GameList.size() + 1];
            gameList[0] = "--请选择棋局--";
            for (int i = 1; i < GameList.size() + 1; i++) {
                gameList[i] = "Gid: " + GameList.get(i - 1).getGid() + " Name: " + GameList.get(i - 1).getName();
            }*/
        /*String[] gameList=new String[GameList.size()];
        for(int i=0;i<GameList.size();i++){
            gameList[i]="Gid: "+GameList.get(i).getGid()+" Name: "+GameList.get(i).getName();
        }*/

            /*for(int i=0; i<GameSystem.getGameList().size(); i++){
                cmbListPlayerGame.addItem("Gid: "+GameSystem.getGameList().get(i).getGid()+" Name: "+GameSystem.getGameList().get(i).getName());
            }*/
            //cmbListPlayerGame.addItem(gameList);
        }
    }

}
