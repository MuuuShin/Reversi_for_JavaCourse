package gui.controller;

import gui.MainFrame;
import gui.model.ChessPiece;
import gui.view.*;
import logic.Step;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GameController {


    private ChessBoardPanel gamePanel;
    private StatusPanel statusPanel;
    private ChessPiece currentPlayer;
    private int blackScore;
    private int whiteScore;
    private static Boolean isAIStep=true;
    private Boolean isOver=false;
    //private JFrame mainFrame;
    //public void setMainFrame(JFrame mainFrame){
    //    this.mainFrame =mainFrame;
    //}

    public void setOver(Boolean over) {
        isOver = over;
    }

    public Boolean getOver() {
        return isOver;
    }

    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel) {
        this.gamePanel = gamePanel;
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        blackScore = 2;
        whiteScore = 2;

    }

    public void swapPlayer() {
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
    }


    public void countScore() {
        int[] score=gamePanel.calcScore();
        blackScore=score[0];
        whiteScore=score[1];
        //todo: modify the countScore method end

    }


    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(ChessPiece currentPlayer) {
        this.currentPlayer = currentPlayer;
        statusPanel.setPlayerText(currentPlayer.name());
    }

    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }


    public void setGamePanel(ChessBoardPanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void readFileData(String fileName) {
        //todo: read date from file
        List<String> fileData = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
            fileData.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToFile(String fileName) {
        //todo: write data into file
    }

    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }
    public void canClick1(int x,int y) {
        gamePanel.canclick(x,y,currentPlayer);
    }
    public void onAIClick(ChessPiece color,Boolean hasSkip) {
        gamePanel.onAIClick(color,hasSkip);
    }

    public static void setIsAIStep(Boolean x){
        isAIStep=x;
    }
    public static Boolean getIsAIStep() {
        return isAIStep;
    }

    public void addStep(Step step) {
        gamePanel.addStep(step);
    }
}
