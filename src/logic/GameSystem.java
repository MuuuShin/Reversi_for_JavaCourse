package logic;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import java.util.ArrayList;

public class GameSystem {

    private static ArrayList<Player> playerList;
    // The list of players
    private static ArrayList<Game> gameList;
    private static ArrayList<Integer> completeList;
    private static ArrayList<Integer> incompleteList;

    // The list of games
    public GameSystem() throws IOException {
        completeList = new ArrayList<>();
        incompleteList = new ArrayList<>();
        playerList = new ArrayList<Player>();
        gameList = new ArrayList<Game>();
        if (!checkPlayer(-2)) {
            AI ai = new AI("AI");
            addPlayer(ai);
        }
        IO.readPlayers();
        IO.readGames();

        listCompleteList();
        listIncompleteList();
    }

    // Constructor. Initialize playerList and gameList. For a list with no elements
    // in it, its size should be 0 and its reference should not be null.
    public static ArrayList<Game> getGameList() {
        return gameList;
    }

    public static ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public static ArrayList<Integer> getOverList() {
        return completeList;
    }

    public static ArrayList<Integer> getNotOverList() {
        return incompleteList;
    }

    public void addOverList(int gid) {
        completeList.add(gid);
    }

    public static boolean checkPlayer(int pid) {
        for (Player players : playerList) {
            if (players.getPid() == pid) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkPlayer(String name) {
        for (Player players : playerList) {
            if (Objects.equals(players.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    public static Player findPlayer(String name) {
        for (Player players : playerList) {
            if (Objects.equals(players.getName(), name)) {
                return players;
            }
        }
        System.out.println("ERROR:not find player");
        return new Player("error");
    }

    public static Player findPlayer(int pid) {
        for (Player players : playerList) {
            if (players.getPid() == pid) {
                return players;
            }
        }
        System.out.println("ERROR:not find player");
        return new Player("error", -1);
    }

    public static Game findGame(int gid) {
        for (Game game : gameList) {
            if (game.getGid() == gid) {
                return game;
            }
        }
        System.out.println("ERROR:not find game");
        return new Game("error", new Player("error", -1), new Player("error", -1), -1);
    }

    // If the player with pid is not in the player list, return false.
    // Otherwise return true.
    public static boolean checkGame(int gid) {
        for (Game games : gameList) {
            if (games.getGid() == gid) {
                return true;
            }
        }
        return false;
    }

    // If the game with gid is not in the game list, return false.
    // Otherwise return true.
    public static boolean addPlayer(Player player) {
        if (checkPlayer(player.getPid())) {
            return false;
        }
        playerList.add(player);
        return true;
    }

    // If the player is in the player list (i.e. his/her pid is in the player list),
    // return false.
    // Otherwise add the pl ayer into the player list and return true.
    public static boolean addGame(Game game) {
        if (checkGame(game.getGid())) {
            return false;
        }
        if (checkPlayer(game.getWhitePlayer().getPid()) && checkPlayer(game.getBlackPlayer().getPid())) {
            gameList.add(game);
            return true;
        }
        return false;
    }


    public static ArrayList<Game> listPlayerGame(int pid) {
        ArrayList<Game> playerGame = new ArrayList<>();
        for (Game games : gameList) {
            if (games.getWhitePlayer().getPid() == pid || games.getBlackPlayer().getPid() == pid) {
                playerGame.add(games);
            }
        }
        return playerGame;
    }

    public static ArrayList<Integer> listCompleteList() {
        ArrayList<Integer> listCompleteList = new ArrayList<>();
        for (Game games : gameList) {
            if (games.getIsOver() == true) {
                listCompleteList.add(games.getGid());
            }
        }
        completeList = listCompleteList;
        return completeList;
    }

    public static ArrayList<Integer> listIncompleteList() {
        ArrayList<Integer> listIncompleteList = new ArrayList<>();
        for (Game games : gameList) {
            if (games.getIsOver() == false) {
                listIncompleteList.add(games.getGid());
            }

        }
        incompleteList = listIncompleteList;
        return incompleteList;
    }

    public static int calculatePlayercontinueNum(int pid) {
        int total = listPlayerGame(pid).size();
        if (total == 0) {
            return 0;
        }

        int continue3 = 0;
        for (Game games : listPlayerGame(pid)) {
            if(games.getIsOver()){continue;}
            continue3++;
        }
        findPlayer(pid).setContinueGameNum(continue3);
        return continue3;
    }
    public static int calculatePlayerWinNum(int pid) {
        int total = listPlayerGame(pid).size();
        if (total == 0) {
            return 0;
        }
        int win = 0;
        for (Game games : listPlayerGame(pid)) {
            if(!games.getIsOver()){continue;}
            int ourScore = 0, oppositeScore = 0;
            int color;
            if (games.getWhitePlayer().getPid() == pid) {
                color = 1;
            } else {
                color = -1;
            }
            int[][] thisGame = games.getBoard();
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    if (color == thisGame[i][j]) {
                        ourScore++;
                    } else if (-color == thisGame[i][j]) {
                        oppositeScore++;
                    }
                }
            }
            if (ourScore > oppositeScore) {
                win++;
            }
        }
        findPlayer(pid).setWinGameNum(win);
        return win;
    }

    public static String toStringGame() {
        String ans = "\"gameCnt\": " + Game.getGameCnt() + ", \"stepCnt\": " + Step.getStepCnt() + ", \"gameList\":[";
        for (Game game : gameList) {
            ans += "{" + game.toString() + "}, ";
        }
        if (ans.length() >= 43) {

            ans = ans.substring(0, ans.length() - 2);
        }
        ans += "], \"overList\":[";
        for (Integer over : completeList) {
            ans += "" + over + ", ";
        }
        ans += "]";
        return ans;
    }

    public static String toStringPlayer() {
        String ans = "\"playerCnt\": " + Player.getPlayerCnt() + ", \"playerList\":[";
        for (Player player : playerList) {
            ans += "{" + player.toString() + "}, ";
        }
        if (ans.length() >= 34) {
            System.out.println("yyy");
            ans = ans.substring(0, ans.length() - 2);
        }
        ans += "]";
        return ans;
    }
    // Return the win rate of the player with pid. The win rate is the ratio of
    // total wins to total games the player has participated in.
    // If the player has not participated in any games, return 0.

    public static void main(String[] args) throws IOException {
        /*GameSystem gameSystem = new GameSystem();
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game1 = new Game("g1", p1, p2);
        Step s1 = new Step(2, 3, 1);
        Step s2 = new Step(3, 3, -1);
        Step s3 = new Step(3, 3, 1);
        Game game2 = new Game("g2", p1, p2);
        Step s4 = new Step(2, 3, 1);
        Step s5 = new Step(3, 3, -1);
        Step s6 = new Step(3, 3, 1);
        GameSystem.addPlayer(p1);
        GameSystem.addPlayer(p2);
        System.out.println(GameSystem.addGame(game1));
        System.out.println(GameSystem.getPlayerList());
        System.out.println(GameSystem.getGameList());
        System.out.println(GameSystem.toStringPlayer());
        GameSystem.addGame(game2);
        game1.addStep(s1);
        game1.addStep(s2);
        game1.addStep(s3);
        game2.addStep(s4);
        game2.addStep(s5);
        game2.addStep(s6);
        System.out.println(game1.addStep(s3));
        System.out.println(game1.checkStep(3));
        System.out.println(GameSystem.toStringGame());
        System.out.println(GameSystem.toStringPlayer());
        IO.writeGames();
        IO.writePlayers();*/
    }
}
