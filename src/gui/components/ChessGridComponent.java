package gui.components;

import gui.controller.GameController;
import gui.model.*;
import gui.view.GameFrame;
import logic.Step;

import java.awt.*;

import static gui.model.ChessPiece.WHITE;

public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public static Color gridColor = new Color(255, 150, 50);
    public static Color canColor = new Color(79, 129, 101);
    public static Color previousColor = new Color(213, 12, 12);
    public boolean canStep = false;
    public boolean previousStep = false;
    private ChessPiece chessPiece;
    private int row;
    private int col;

    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);

        this.row = row;
        this.col = col;
    }

    @Override
    public void onMouseClicked() {
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        if(GameFrame.controller.getOver())return;
        if (GameFrame.controller.canClick(row, col)) {
            if (this.chessPiece == null) {
                int color=0;
                if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
                    color = 1;
                } else if (GameFrame.controller.getCurrentPlayer() == ChessPiece.WHITE){
                    color = -1;
                }
                Step s1=new Step(row, col,color);
                GameFrame.controller.addStep(s1);
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                GameFrame.controller.canClick1(row, col);
                //todo: complete mouse click method END
                GameFrame.controller.swapPlayer();
                System.out.printf("%s clicked \n", GameFrame.controller.getCurrentPlayer());
                if(GameFrame.controller.getOver())return;
                if (GameController.getIsAIStep()) {
                    GameFrame.controller.onAIClick(GameFrame.controller.getCurrentPlayer(), false);
                }

            }
            repaint();
        }
    }


    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void drawPiece(Graphics g) {
        g.setColor(gridColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        } else if (canStep) {
            g.setColor(canColor);
            g.fillOval((gridSize - (int)(chessSize*0.85)) / 2, (gridSize - (int)(chessSize*0.85)) / 2, (int)(chessSize*0.85), (int)(chessSize*0.85));
            //g.setColor(gridColor);
            //g.fillOval((gridSize - (int) (chessSize * 0.6)) / 2, (gridSize - (int) (chessSize * 0.6)) / 2, (int) (chessSize * 0.6), (int) (chessSize * 0.6));
        } else {
            g.setColor(gridColor);
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        }
        if (previousStep) {
            g.setColor(previousColor);
            g.fillOval((gridSize - chessSize / 2) / 2, (gridSize - chessSize / 2) / 2, chessSize / 2, chessSize / 2);
        }

    }


    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        drawPiece(g);
    }


}
