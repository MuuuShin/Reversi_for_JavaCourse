package gui;

import logic.GameSystem;
import logic.Game;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RoomStage extends JPanel {
    ArrayList<JButton> buttonList;

    public RoomStage() {
        setLayout(new FlowLayout());

    }

    public void initRoomStage(ArrayList<Integer> incompleteList) {
        this.removeAll();
        buttonList=new ArrayList<>();
        for (int currentGid : incompleteList) {
            JButton currentBtn = new JButton(printGidInfo(currentGid));
            buttonList.add(currentBtn);
            add(currentBtn);
        }
    }

    public String printGidInfo(int gid) {  //标签上显示的字符串：gid blackPlayer whitePlayer
        Game currentGame = null;
        ArrayList<Game> gameListMid = GameSystem.getGameList();
        for (Game game : gameListMid) {
            if (game.getGid() == gid) {
                currentGame = game;
                break;
            }
        }
        if (currentGame == null) {
            currentGame = new Game("error", new Player("error", -1), new Player("error", -1), -1);
        }
        return "<html>Gid: " + gid + "  " + "Mode:" + currentGame.getGameMode() + "<br>" + "黑方: " + currentGame.getBlackPlayer().getName() + "<br>" + "白方: " + currentGame.getWhitePlayer().getName() + "</html>";
    }

    public ArrayList<JButton>  getButtonList() {
        return buttonList;
    }
}


