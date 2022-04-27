package gui;

import javax.swing.*;
import gui.view.GameFrame;
import logic.Game;
import logic.Player;
import java.io.IOException;


public class GameStage {
    public GameStage(int width, int height, Game game, Player blackPlayer, Player whitePlayer, JFrame jFrame) {
        SwingUtilities.invokeLater(() -> {
            GameFrame mainFrame = null;
            try {
                mainFrame = new GameFrame(width, height, game, blackPlayer, whitePlayer,jFrame);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainFrame.setVisible(true);
        });
    }
}
