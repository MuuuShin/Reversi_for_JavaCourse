package logic;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {

    public AI(String name) {
        super(name,-2);
    }
    //随机下棋 返回 数组返回一个坐标 调用可下棋的位置checkStep findStep（返回list 随机挑一个） 返回棋盘
    //参数 int[][] 颜色
    public static int[] lowAI(int[][] board, int color){
        ArrayList<int[]> CanList = new ArrayList<>();
        CanList = CheckStep.findStep(board,color);

        Random r = new Random();
        int selectedNum = r.nextInt(CanList.size());
        int[] selectedXY = {CanList.get(selectedNum)[0],CanList.get(selectedNum)[1]};
        return selectedXY;
    }


}
