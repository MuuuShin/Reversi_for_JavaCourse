package logic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import static logic.GameSystem.findPlayer;

public class IO {
    public static void readPlayers() throws IOException {
        try (Reader reader = new FileReader(".\\archive-Players.json", StandardCharsets.UTF_8)) {
            char[] buffer = new char[1000];
            int n;
            String s1 = "";
            while ((n = reader.read(buffer)) != -1) {
                System.out.println("read " + n + " chars.");
                s1 += String.valueOf(buffer);
                buffer = new char[1000];
            }
            s1 += String.valueOf(buffer);
            s1=s1.trim();
            s1 = s1.replace(" ", "");
            if(s1.equals(""))return;
            System.out.println(s1);
            JSONObject object = JSONObject.parseObject(s1);
            Player.initializePlayerCnt(object.getInteger("playerCnt"));
            JSONArray playerList = object.getJSONArray("playerList");
            for (Object player1:playerList) {
                JSONObject object1 = JSONObject.parseObject(String.valueOf(player1));
                GameSystem.addPlayer(new Player(object1.getString("player"),object1.getInteger("pid")));
            }
        }
    }
    public static void readGames() throws IOException {
        try (Reader reader = new FileReader(".\\archive-Games.json", StandardCharsets.UTF_8)) {
            char[] buffer = new char[100];
            int n;
            String s1 = "";
            while ((n = reader.read(buffer)) != -1) {
                System.out.println("read " + n + " chars.");
                s1 += String.valueOf(buffer);
                buffer = new char[100];
            }
            s1 += String.valueOf(buffer);
            s1=s1.trim();
            s1 = s1.replace(" ", "");
            if(s1.equals(""))return;
            System.out.println(s1);
            JSONObject object = JSONObject.parseObject(s1);
            Game.initializeGameCnt(object.getInteger("gameCnt"));
            Step.initializeStepCnt(object.getInteger("stepCnt"));
            JSONArray gameList = object.getJSONArray("gameList");
            readGameSystem(gameList);
        }
    }

    public static void readGameSystem(JSONArray gameList)  throws IOException {
        for (Object game1:gameList) {
            JSONObject object1 = JSONObject.parseObject(String.valueOf(game1));
            System.out.println(object1);
            Integer blackPlayerId = object1.getInteger("blackPlayerId");
            Integer whitePlayerId = object1.getInteger("whitePlayerId");
            Player blackPlayer=GameSystem.findPlayer(blackPlayerId);
            Player whitePlayer=GameSystem.findPlayer(whitePlayerId);
            ArrayList<Step> stepList=readStep(object1.getJSONArray("stepList"));
            int[][] board=readBoard(object1.getJSONArray("board"));
            GameSystem.addGame(new Game(object1.getString("name"),whitePlayer,blackPlayer,object1.getInteger("gid"),stepList,board,object1.getInteger("gameMode"),object1.getBoolean("isOver"),object1.getString("currentPlayer")));
        }
    }
    public static ArrayList<Step> readStep(JSONArray stepList)  throws IOException {
        ArrayList<Step> stepList1=new ArrayList<Step>();
        for (Object step:stepList) {
            JSONObject object1 = JSONObject.parseObject(String.valueOf(step));
            System.out.println(object1);
            Step a=new Step(object1.getInteger("sid"),object1.getInteger("rowIndex"),object1.getInteger("columnIndex"),object1.getInteger("color"));
            stepList1.add(a);
        }
        return stepList1;
    }
    public static int[][] readBoard(JSONArray stepList)  throws IOException {
        int[][] board=new int[8][8];
        for(int i=0;i<8;i++){
            JSONArray object1 = (JSONArray) stepList.get(i);
            for(int j=0;j<8;j++) {
                System.out.print(object1.get(j));
            board[i][j]= (int) object1.get(j);

            }
            System.out.println(Arrays.toString(board[i]));
        }
        return board;
    }
    public static void writeGames() throws IOException {
        String jsonGames = "{" + GameSystem.toStringGame() + "}";
        File inFile = new File(".\\archive-Games.json");
        if (!inFile.isFile()) {
            inFile.createNewFile();
        }
        try (OutputStream output = new FileOutputStream(inFile)) {
            output.write(jsonGames.getBytes("UTF-8")); // Hello
        } // 编译器在此自动为我们写入finally并调用close()
    }

    public static void writePlayers() throws IOException {
        String jsonPlayers = "{" + GameSystem.toStringPlayer() + "}";
        File inFile = new File(".\\archive-Players.json");
        if (!inFile.isFile()) {
            inFile.createNewFile();
        }
        try (OutputStream output = new FileOutputStream(inFile)) {
            output.write(jsonPlayers.getBytes("UTF-8")); // Hello
        } // 编译器在此自动为我们写入finally并调用close()
    }

    public static void main(String[] args) throws IOException {
        IO io = new IO();
        int[] str = {4, 5, 2, 6};
        String s1 = "sd";
        char[] c1 = {70, 71, 72, 73, 74};
        s1 = String.valueOf(c1);
        System.out.println(s1);
        io.readGames();
    }
}
