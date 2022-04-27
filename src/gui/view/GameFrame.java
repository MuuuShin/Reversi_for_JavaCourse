package gui.view;


import gui.GameStage;
import gui.MainFrame;
import gui.controller.GameController;
import gui.model.ChessPiece;
import logic.Game;
import logic.GameSystem;
import logic.IO;
import logic.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame {
    public static GameController controller;
    private ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;
    int width, height;
    Game game;
    Player blackPlayer;
    Player whitePlayer;
    JFrame jFrame;

    private void init() throws IOException {
        this.setTitle("2021F CS102A Project Reversi");
        this.setLayout(null);

        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        Insets inset = this.getInsets();
        this.setSize(width + inset.left + inset.right, height + inset.top + inset.bottom);

        this.setLocationRelativeTo(null);
        addBackground();
        JPanel jPanelTop = (JPanel) getContentPane();
        jPanelTop.setOpaque(false);

        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7), game, blackPlayer, whitePlayer);
        chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() - chessBoardPanel.getHeight()) / 3);
        chessBoardPanel.setOpaque(false);

        statusPanel = new StatusPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.1), blackPlayer, whitePlayer);
        statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, 0);
        statusPanel.setOpaque(false);

        controller = new GameController(chessBoardPanel, statusPanel);
        controller.setGamePanel(chessBoardPanel);
        //controller.setMainFrame(jFrame);

        this.add(chessBoardPanel);
        this.add(statusPanel);


        JButton restartBtn = new JButton("再来一局");
        restartBtn.setSize(120, 50);
        restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
        add(restartBtn);
        restartBtn.addActionListener(e -> {
            if (!game.getIsOver()) {
                int c = JOptionPane.showConfirmDialog(this, "确定要新开一局吗？现有棋局仍未结束！", "提示", 0);
                if (c != 0) {
                    return;
                }
            }
            String name = JOptionPane.showInputDialog(this, "请输入本局代号(不输入自动创建)", "新建", 1);
            if (name == null) {
                return;
            }
            if (name.equals("")) {
                name = "game" + Game.getGameCnt();
            }
            System.out.println("add a new game");
            Player a, b;
            String namea, nameb;
            do {
                namea = JOptionPane.showInputDialog(this, "输入A玩家名字(输\"AI\"即为AI)", "新建", 1);
            } while ("".equals(namea));
            if (namea == null) {
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
                nameb = JOptionPane.showInputDialog(this, "输入B玩家名字(输\"AI\"即为AI)", "新建", 1);
                if(nameb.equals(namea)){
                    JOptionPane.showMessageDialog(this, "已在黑方中被选中！", "警告", 2);
                }
            }while("".equals(nameb) || nameb.equals(namea));
            if (nameb == null) {
                return;
            }
            if ((GameSystem.getPlayerList().size() != 0) && GameSystem.checkPlayer(nameb)) {
                b = GameSystem.findPlayer(nameb);
            } else {
                b = new Player(nameb);
                GameSystem.addPlayer(b);
            }
            Game g1 = new Game(name, a, b);
            GameSystem.addGame(g1);

            new GameStage(width, height, g1, a, b, jFrame);
            dispose();
            System.out.println("click restart Btn");
        });

        JButton loadGameBtn = new JButton("返回");
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
        add(loadGameBtn);
        loadGameBtn.addActionListener(e -> {
            System.out.println("clicked Load Btn");
            int c = JOptionPane.showConfirmDialog(this, "确定要返回到主页面吗？(返回时自动保存)", "提示", 0);
            if (c == 0) {
                try {
                    game.currentplayer=controller.getCurrentPlayer();
                    game.setBoard(chessBoardPanel.chessToBoard());
                    IO.writeGames();
                    IO.writePlayers();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                MainFrame.status = 0;
                jFrame.setVisible(true);
                dispose();
            }

        });

        JButton saveGameBtn = new JButton("保存");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(loadGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
        add(saveGameBtn);
        saveGameBtn.addActionListener(e -> {
            System.out.println("clicked Save Btn");
            JOptionPane.showMessageDialog(this, "保存成功！", "提示", JOptionPane.PLAIN_MESSAGE);
            try {
                game.currentplayer=GameFrame.controller.getCurrentPlayer();
                game.setBoard(chessBoardPanel.chessToBoard());
                IO.writeGames();
                IO.writePlayers();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });


        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public GameFrame(int width, int height, Game game, Player blackPlayer, Player whitePlayer, JFrame jFrame) throws IOException {
        this.width = width;
        this.height = height;
        this.game = game;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.jFrame = jFrame;
        init();
        if (blackPlayer.getPid() == -2) {
            controller.onAIClick(ChessPiece.BLACK, false);
        }
    }

    public GameFrame(int width, int height, Game game, JFrame jFrame) throws IOException {
        this.width = width;
        this.height = height;
        this.game = game;
        this.blackPlayer = game.getBlackPlayer();
        this.whitePlayer = game.getWhitePlayer();
        this.jFrame = jFrame;
        init();
        chessBoardPanel.initialChessGrids(game.getBoard());
        GameFrame.controller.setCurrentPlayer(game.currentplayer);

        if (blackPlayer.getPid() == -2) {
            controller.onAIClick(ChessPiece.BLACK, false);
        }
    }


    private void addBackground() throws IOException {
        BufferedImage img;
        img = ImageIO.read(new File(".\\source\\background1.jpg"));
        ImageIcon background = new ImageIcon(img);
        JLabel lbBg = new JLabel(background);
        lbBg.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        getLayeredPane().add(lbBg, new Integer(Integer.MIN_VALUE));
        Dimension d = new Dimension(background.getIconWidth() - 400, background.getIconHeight());
    }
}
