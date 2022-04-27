package gui.view;

import gui.components.ChessGridComponent;
import gui.controller.GameController;
import gui.model.ChessPiece;
import logic.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static gui.model.ChessPiece.WHITE;
import static logic.CheckStep.hasStep;

public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;
    private ChessGridComponent[][] chessGrids;
    private int[] previousStepLo = new int[2];
    Player blackPlayer;
    Player whitePlayer;
    Game game;

    public ChessBoardPanel(int width, int height, Game game, Player blackPlayer, Player whitePlayer) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.game = game;

        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;

        this.setBackground(Color.BLACK);
        int length = Math.min(width, height) + 3;
        this.setSize(length, length);
        ChessGridComponent.gridSize = length / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);
        initialChessGrids();//return empty chessboard
        initialGame();//add initial four chess
        repaint();
    }

    /**
     * set an empty chessboard
     */
    public void initialChessGrids() {
        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];

        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                chessGrids[i][j] = gridComponent;
                this.add(chessGrids[i][j]);
            }
        }
    }
    public void initialChessGrids(int[][] board) {
        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if(board[i][j]==1){
                    chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                }else if(board[i][j]==-1){
                    chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                }
            }
        }
        GameFrame.controller.setCurrentPlayer(game.currentplayer);
        ArrayList<int[]> findStep;
        if(GameFrame.controller.getCurrentPlayer()==ChessPiece.BLACK){
           findStep = CheckStep.findStep(board, 1);
        }else{
            findStep = CheckStep.findStep(board, -1);
        }
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                for (int[] step : findStep) {
                    if (step[0] == i && step[1] == j) {
                        chessGrids[i][j].canStep = true;
                        break;
                    } else {
                        chessGrids[i][j].canStep = false;
                    }

                }
            }
        }
        previousStepLo[1] = game.getStepList().get(game.getStepList().size()-1).getColumnIndex();
        previousStepLo[0] = game.getStepList().get(game.getStepList().size()-1).getRowIndex();
        chessGrids[previousStepLo[0]][previousStepLo[1]].previousStep = true;
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                chessGrids[i][j].repaint();
            }
        }
    }

    /**
     * initial origin four chess
     */
    public void initialGame() {
        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(WHITE);
        chessGrids[4][3].setChessPiece(WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);
        chessGrids[2][4].canStep = true;
        chessGrids[3][5].canStep = true;
        chessGrids[4][2].canStep = true;
        chessGrids[5][3].canStep = true;
    }

public int[][] chessToBoard(){
    int[][] board = new int[8][8];
    for (int i = 0; i < CHESS_COUNT; i++) {
        for (int j = 0; j < CHESS_COUNT; j++) {
            if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
                board[i][j] = 1;
            } else if (chessGrids[i][j].getChessPiece() == WHITE) {
                board[i][j] = -1;
            } else {
                board[i][j] = 0;
            }
        }
    }
    return board;
}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public boolean canClickGrid(int row, int col, ChessPiece currentPlayer) {
        int color = 0;
        if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
            color = 1;
        } else if (GameFrame.controller.getCurrentPlayer() == WHITE) {
            color = -1;
        } else {
            System.out.println("ERROR:not find player color");
        }
        int[][] board = new int[8][8];
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
                    board[i][j] = 1;
                } else if (chessGrids[i][j].getChessPiece() == WHITE) {
                    board[i][j] = -1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
        return hasStep(board, row, col, color);
        //todo: complete this method end

    }

    public void canclick(int x, int y, ChessPiece currentPlayer) {

        int color = 0;
        if (currentPlayer == ChessPiece.BLACK) {
            color = -1;
        } else if (currentPlayer == ChessPiece.WHITE) {
            color = 1;
        } else {
            System.out.println("ERROR:not find player color");
        }
        int[][] board = new int[8][8];
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
                    board[i][j] = 1;
                } else if (chessGrids[i][j].getChessPiece() == WHITE) {
                    board[i][j] = -1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
        System.out.println("X,Y" + x + y);
        board = CheckStep.fanqi(board, x, y, -color);
        //chessGrids[1][2].setChessPiece(currentPlayer);
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if (board[i][j] == 1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                } else if (board[i][j] == -1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                }

            }
        }
        String nextPlayer;
        if (currentPlayer == ChessPiece.BLACK) {
            nextPlayer = "" + ChessPiece.WHITE + "";
        } else {
            nextPlayer = "" + ChessPiece.BLACK + "";
        }
        ArrayList<int[]> findStep = CheckStep.findStep(board, color);

        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                for (int[] step : findStep) {
                    if (step[0] == i && step[1] == j) {
                        chessGrids[i][j].canStep = true;
                        break;
                    } else {
                        chessGrids[i][j].canStep = false;
                    }

                }
            }
        }
        chessGrids[previousStepLo[0]][previousStepLo[1]].previousStep = false;
        chessGrids[x][y].previousStep = true;
        previousStepLo[0] = x;
        previousStepLo[1] = y;
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                chessGrids[i][j].repaint();
            }
        }
        GameController.setIsAIStep(true);
        if (findStep.get(0)[0] == -100 && ((color == -1 && whitePlayer.getPid() == -2) || (color == 1 && blackPlayer.getPid() == -2))) {
            JOptionPane.showMessageDialog(this, "AI必须弃权一次", "提示", 1);
            GameFrame.controller.swapPlayer();
            canclick(-color);
            GameController.setIsAIStep(false);
        } else if (findStep.get(0)[0] == -100) {
            JOptionPane.showMessageDialog(this, nextPlayer + "必须弃权一次", "提示", 1);
            GameFrame.controller.swapPlayer();
            if ((color == -1 && blackPlayer.getPid() == -2) || (color == 1 && whitePlayer.getPid() == -2)) {
                ChessPiece nextPlayer1;
                if (currentPlayer == ChessPiece.BLACK) {
                    nextPlayer1 = ChessPiece.WHITE;
                } else {
                    nextPlayer1 = ChessPiece.BLACK;
                }
                onAIClick(nextPlayer1, true);
            } else {
                canclick(-color);
            }

        }

        //todo: complete this method end

    }

    public void canclick(int color) {

        int[][] board = new int[8][8];
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
                    board[i][j] = 1;
                } else if (chessGrids[i][j].getChessPiece() == WHITE) {
                    board[i][j] = -1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
        //chessGrids[1][2].setChessPiece(currentPlayer);
        ArrayList<int[]> findStep = CheckStep.findStep(board, color);
        System.out.println(findStep.size());

        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                for (int[] step : findStep) {
                    if (step[0] == i && step[1] == j) {
                        chessGrids[i][j].canStep = true;
                        break;
                    } else {
                        chessGrids[i][j].canStep = false;
                    }

                }
            }
        }
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                chessGrids[i][j].repaint();
            }
        }
        if (findStep.get(0)[0] == -100) {
            JOptionPane.showMessageDialog(this, "游戏结束", "提示", 1);
            game.gameOver();
            GameFrame.controller.setOver(true);
            return;
        }
        //todo: complete this method end

    }

    public void onAIClick(ChessPiece color1, Boolean hasSkip) {
        int color = 0;
        if (color1 == ChessPiece.BLACK) {
            color = 1;
        } else if (color1 == WHITE) {
            color = -1;
        } else {
            System.out.println("ERROR:not find player color");
        }
        int[][] board = new int[8][8];
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
                    board[i][j] = 1;
                } else if (chessGrids[i][j].getChessPiece() == WHITE) {
                    board[i][j] = -1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
        onAIClick1(color, board, hasSkip);
    }

    public void onAIClick1(int color, int[][] board, Boolean hasSkip) {
        if (blackPlayer.getPid() == -2 || whitePlayer.getPid() == -2) {
        } else {
            return;
        }
        int[] stepAI = new int[2];
        stepAI = AI.lowAI(board, color);
        System.out.println("step" + stepAI[0] + stepAI[1]);

        if (stepAI[0] == -100) {
            if (hasSkip) {
                JOptionPane.showMessageDialog(this, "游戏结束", "提示", 1);
                game.gameOver();
                GameFrame.controller.setOver(true);
                return;
            }
            JOptionPane.showMessageDialog(this, "AI必须弃权一次", "提示", 1);
            GameFrame.controller.swapPlayer();
            canclick(-color);
        }
        ChessPiece player = ChessPiece.BLACK;
        if (color == 1) {
            player = ChessPiece.BLACK;
        } else if (color == -1) {
            player = ChessPiece.WHITE;
        } else {
            System.out.println("ERROR:not find player color");
        }

        Step s1 = new Step(stepAI[0], stepAI[1], color);
        GameFrame.controller.addStep(s1);

        chessGrids[stepAI[0]][stepAI[1]].setChessPiece(player);
        canclick(stepAI[0], stepAI[1], player);
        GameFrame.controller.swapPlayer();
    }

    public void addStep(Step step) {
        game.addStep(step);
    }

    public void undoStep() {
        game.getStepList().remove(game.getStepList().size() - 1);
    }

    public int[] calcScore() {
        int[] calcScore= {0,0};
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
                    calcScore[0]++;
                } else if (chessGrids[i][j].getChessPiece() == ChessPiece.WHITE) {
                    calcScore[1]++;
                }
            }
        }
        return calcScore;
    }
}
