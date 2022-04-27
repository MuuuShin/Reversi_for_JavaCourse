package logic;

public class Player {

    private int pid;
    private String name;
    private static int playerCnt = 1; // nowPlayerId

    private int totalGameNum;
    private int continueGameNum;
    private int winGameNum;
    private int loseGameNum;
    private double winRate;
    private double loseRate;
    public static void initializePlayerCnt(int playercnt){
        playerCnt= playercnt;
    }
    public Player(String name,int pid) {
        this.name = name;
        this.pid = pid;
    }
    public Player(String name) {
        this.name = name;
        this.pid = playerCnt;
        playerCnt++;
    }

    public int getPid() {
        return this.pid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getPlayerCnt() {
        return playerCnt;
    }

    public String toString() {
        return "\"player\": \"" + this.name + "\", \"pid\": " + this.pid;
    }

    public int getWinGameNum() {
        return winGameNum;
    }

    public int getTotalGameNum() {
        return totalGameNum;
    }

    public int getLoseGameNum() {
        return loseGameNum;
    }

    public double getWinRate() {
        return winRate;
    }

    public double getLoseRate() {
        return loseRate;
    }

    public void setTotalGameNum(int totalGameNum) {
        this.totalGameNum = totalGameNum;
    }

    public void setWinGameNum(int winGameNum) {
        this.winGameNum = winGameNum;
    }

    public void setLoseGameNum(int loseGameNum) {
        this.loseGameNum = loseGameNum;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public void setLoseRate(double loseRate) {
        this.loseRate = loseRate;
    }

    public int calculateLoseGameNum(){
        if(totalGameNum-continueGameNum==0)return 0;
        return (totalGameNum-winGameNum-continueGameNum);
    }

    public double calculateWinRate(){
        if(totalGameNum-continueGameNum==0)return 0;
        return (double)winGameNum/(totalGameNum-continueGameNum);
    }

    public double calculateLoseRate(){
        if(totalGameNum-continueGameNum==0)return 0;
        return (double)loseGameNum/(totalGameNum-continueGameNum);
    }

    public int getContinueGameNum() {
        return continueGameNum;
    }

    public void setContinueGameNum(int continueGameNum) {
        this.continueGameNum = continueGameNum;
    }

    public int calculatePlayerTotalGameNum() {
        for(Player player:GameSystem.getPlayerList()){
            if(player.getPid()==pid){
               totalGameNum = GameSystem.listPlayerGame(pid).size();
                return totalGameNum;
            }
        }
        return 0;
    }
    public static void main(String[] args) {
    }
}
