package DB;

import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class YJRServerManage implements Runnable{
    //代表线程
    private Thread t;
    //放入客户端管理
    private YJRClientManage cm;



    //初始化
    YJRServerManage(YJRClientManage cm){
        this.cm = cm;
    }

    public void run() {
        Scanner sn = new Scanner(System.in);
        String line = new String();

        //死循环监听 在控制台听命令 如果听到相应的命令  便根据不同命令进行不同操作
        while(true) {
            if(sn.hasNextLine()) {
                line = sn.nextLine();
                if(line.equals("shutdown")) {}
                else if(line.equals("showclientnum")) {
                    System.out.println(cm.GetvSocket().size());
                }
                if(line.equals("showallclientinfo")) {
                    Vector temp = cm.GetvSocket();
                    for(int i = temp.size()-1;i>=0;i--) {
                        Socket so = (Socket)temp.elementAt(i);
                        System.out.println(so);
                    }
                }
            }
        }
    }
    //创建一个新线程
    public void start() {
        System.out.println("YJRDB服务器管理线程开启");
        if(t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}