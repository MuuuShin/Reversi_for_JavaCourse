package gui.view;

import gui.model.ChessPiece;
import logic.Player;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private JLabel playerLabel;
    private JLabel scoreLabel;
    private JLabel blackPlayerLabel;
    private JLabel whitePlayerLabel;

    public StatusPanel(int width, int height, Player p1,Player p2) {
        this.setSize(width, height);
        this.setLayout(null);
        this.setVisible(true);

        this.playerLabel = new JLabel();
        this.playerLabel.setLocation(0, 10);
        this.playerLabel.setSize((int) (width * 0.4), height);
        this.playerLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        this.setPlayerText(ChessPiece.BLACK.name());
        add(playerLabel);
// Font f2 = new Font("微软雅黑", Font.BOLD,25);
        this.scoreLabel = new JLabel();
        this.scoreLabel.setLocation((int) (width * 0.4), 10);
        this.scoreLabel.setSize((int) (width * 0.5), height);
        this.scoreLabel.setFont(new Font("Calibri", Font.ITALIC, 25));
        this.setScoreText(2,2);
        add(scoreLabel);

        this.blackPlayerLabel = new JLabel();
        blackPlayerLabel.setLocation((int) (width * 0.4), -18);
        blackPlayerLabel.setSize((int) (width * 0.4), height);
        blackPlayerLabel.setFont(new Font("微软雅黑", Font.BOLD,19));
        blackPlayerLabel.setText("黑方:"+p1.getName());
        add(blackPlayerLabel);
//white同
        this.whitePlayerLabel = new JLabel();
        whitePlayerLabel.setLocation(110+(int) (width * 0.4), -18);
        whitePlayerLabel.setSize((int) (width * 0.4), height);
        whitePlayerLabel.setFont(new Font("微软雅黑", Font.BOLD,19));
        whitePlayerLabel.setText("白方:"+p2.getName());
        add(whitePlayerLabel);




    }

    public void setScoreText(int black, int white) {
        this.scoreLabel.setText(String.format("BLACK: %d   WHITE: %d", black, white));
    }

    public void setPlayerText(String playerText) {
        this.playerLabel.setText(playerText + "'s turn");
    }
    
}
