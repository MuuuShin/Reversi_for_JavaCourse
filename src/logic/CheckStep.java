package logic;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckStep {

    public static void find(int[][] board, int color, int x, int y, int xmove, int ymove) {
        //当向某方向挪一次后为对方颜色 则进入
        if (x >= 0 && x < 8 && y >= 0 && y < 8 && x + 2 * xmove >= 0 && x + 2 * xmove < 8 && y + 2 * ymove >= 0 && y + 2 * ymove < 8 && board[x + xmove][y + ymove] == -color) {
            x = x + 2 * xmove;
            y = y + 2 * ymove;
            //若再挪一次 仍为对方颜色 则进入循环（继续挪）
            while (x>=0&&x<8&&y>=0&&y<8&&x+xmove>=0&&x+xmove<8&&y+ymove>=0&&y+ymove<8&&board[x][y] == -color) {
                x = x + xmove;
                y = y + ymove;
            }
            //某次挪完后 不为对方颜色 则结束循环，若该位置为0 则为可下位置 做标记
            if (board[x][y] == 0) {
                board[x][y] = 100;
            }
        }
    }

    public static Boolean has(int[][] board, int color, int x, int y, int xmove, int ymove) {
        int[] ans = {-1, -1};
        //当向某方向挪一次后为对方颜色 则进入
        if (x >= 0 && x < 8 && y >= 0 && y < 8 && x + 2 * xmove >= 0 && x + 2 * xmove < 8 && y + 2 * ymove >= 0 && y + 2 * ymove < 8 && board[x + xmove][y + ymove] == -color) {
            x = x + 2 * xmove;
            y = y + 2 * ymove;
            //若再挪一次 仍为对方颜色 则进入循环（继续挪）
            while (x>=0&&x<8&&y>=0&&y<8&&x+xmove>=0&&x+xmove<8&&y+ymove>=0&&y+ymove<8&&board[x][y] == -color) {
                x = x + xmove;
                y = y + ymove;
            }
            //某次挪完后 不为对方颜色 则结束循环，若该位置为0 则为可下位置 做标记
            if (board[x][y] == color) {
                return true;
            }
        }
        return false;
    }
    public static int[][] hasfanqi(int[][] board, int color, int x, int y, int xmove, int ymove) {
        int x0 = x;
        int y0 = y;
        //当向某方向挪一次后为对方颜色 则进入
        if (x >= 0 && x < 8 && y >= 0 && y < 8 && x + 2 * xmove >= 0 && x + 2 * xmove < 8 && y + 2 * ymove >= 0 && y + 2 * ymove < 8 && board[x + xmove][y + ymove] == -color) {
            x = x + 2 * xmove;
            y = y + 2 * ymove;
            //若再挪一次 仍为对方颜色 则进入循环（继续挪）
            while (x>=0&&x<8&&y>=0&&y<8&&x+xmove>=0&&x+xmove<8&&y+ymove>=0&&y+ymove<8&&board[x][y] == -color) {
                x = x + xmove;
                y = y + ymove;
            }
            //某次挪完后 不为对方颜色 则结束循环，若该位置为0 则为可下位置 做标记
            if (board[x][y] == color) {
                int lx;
                int ly;
                int Num;
                if((x-x0)>=0){
                    lx = x-x0;
                }else{
                    lx = x0-x;
                }
                if((y-y0)>=0){
                    ly = y-y0;
                }else{
                    ly = y0-y;
                }
                Num = Math.max(lx, ly);
                for(int i=0;i<Num; i++){
                    board[x-i*xmove][y-i*ymove] = color;
                }
            }
        }

        return board;
    }

    public static int[][] fanqi(int[][] board, int x, int y, int color) {
        final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        //遍历棋盘上该颜色的棋 每个棋找八个方向
        for (int k = 0; k < 8; k++) {
            board=hasfanqi(board, color,x, y, move[k][0], move[k][1]);
        }

        return board;

    }
    /*    //寻找可下棋的位置 并把可下棋的位置赋值为12345 作为标记
        public static void find(int[][] board,int color,int x,int y,int xmove,int ymove){
            do {
                x += xmove;
                y += ymove;
            }while(x >= 0 && x < 8 && y >= 0 && y < 8 && board[x][y] == -color);
            //只要满足移动的方向是对方的棋子就一直往那边找
            // 直到不是对方的棋子为止
            if(x >= 0 && x < 8 && y >= 0 && y < 8 && board[x][y] == 0 && board[x - xmove][y - ymove] != color) {
                //看看延长到最后之后那个是不是自己的棋子，需要注意的一点是就算第一个满足移动方向的棋子不是对方的棋子，
                //xy也做了相应的处理，所以再检验最后那个是不是0的同时还需要检验最后一次做操作之前的棋子应该不是自己的棋子
                check = true;//如果有这种情况就是true
                board[x][y] = 12345;//标记
            }
        }
    */
    //寻找能下棋的位置并读入一个列表,综合了无棋可下的跳过
    public static ArrayList<int[]> findStep(int[][] board, int color) {
        ArrayList<int[]> CanList = new ArrayList<>();
        final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        //遍历棋盘上该颜色的棋 每个棋找八个方向
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == color) {
                    for (int k = 0; k < 8; k++) {
                        find(board, color, i, j, move[k][0], move[k][1]);
                    }
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 100) {
                    int[] a = {i, j};
                    CanList.add(a);
                }
            }
        }
        if (CanList.size() == 0) {//要skipStep进行的预操作
            CanList.add(new int[]{-100, -100});
        }
        return CanList;
    }

    public static Boolean hasStep(int[][] board, int x, int y, int color) {
        final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        //遍历棋盘上该颜色的棋 每个棋找八个方向
        for (int k = 0; k < 8; k++) {
            if(has(board, color,x, y, move[k][0], move[k][1])){
                return true;
            }
        }
        return false;
    }
//调用此方法的目标应该使用CanList.get(1)[0]==-100来判断是否无棋可下
//若无棋可下（check为false）则为skipstep则返回true；若有棋可下（check为true)则返回false
    /*public static boolean skipStep(int[][] board,int color){
        ArrayList<int[]> canList=findStep(board,color);
        if(canList.size()>0){
            return false;
        }else{
            return true;
        }
    }*/

}
