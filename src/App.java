import gui.*;
import logic.*;
import java.io.*;
public class App {
    public static GameSystem gameSystem;
    public static void main(String[] args) throws IOException {
        //读取文件，初始化系统
        gameSystem=new GameSystem();

        MainFrame mainFrame = new MainFrame();

    }
}
