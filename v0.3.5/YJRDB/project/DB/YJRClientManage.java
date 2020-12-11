package DB;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class YJRClientManage implements Runnable{
    //代表线程
    private Thread t;
    //用死循环 让线程定时执行任务
    //用于保存每个客户端连接
    private Vector vSocket;
    //用于保存每个客户端发送过来的消息
    private Vector vMessage;


    //初始化
    YJRClientManage(){
        vSocket = new Vector();
        vMessage = new Vector();
    }

    //添加客户端socket连接
    public void addSocket(Object o) {
        vSocket.addElement(o);
    }

    //添加客户端发送的信息
    public void addMessage(String str) {
        vMessage.addElement(str);
    }

    public Vector GetvSocket(){
        return vSocket;
    }

    //群发vMessage中的信息
    public void ClientSend() throws IOException {
        //首先打印vMessage里的信息 并且把打印的信息清除出vMessage
        //把每一个vMessage输出到每一个vSocket 所以是vMessage在外层 vSocket在内层
        for(int i = 0;i<vMessage.size();i++) {
            for(int j = 0;j<vSocket.size();j++) {
                Socket temp = (Socket)vSocket.get(j);
                //获取这个socket连接发送的信息
                PrintWriter writer = new PrintWriter(temp.getOutputStream(), true);
                writer.println(vMessage.get(i));
                writer.flush();
            }
        }
        //打印完后 删除所有信息
        vMessage.removeAllElements();
    }

    //对每个socket客户端连接进行检测
    public void LinkTest() {
        //对Vsocket进行遍历
        for(int i = vSocket.size()-1; i>=0 ; i--) {
            //获取其中保存的一个socket
            Socket temp = (Socket)vSocket.get(i);
            //如果关了就移除
            if(temp.isClosed()) {
                vSocket.remove(i);
            }
        }
    }




    public void run() {
        try {
            //用死循环 让线程定时执行任务
            while(true) {
                //先检测线程连接
                this.LinkTest();
                //再群发信息
                this.ClientSend();
                //线程沉睡200毫秒
                Thread.sleep(200);
            }
        }catch(Exception e) {}
    }

    //创建一个新线程
    public void start() {
        System.out.println("YJRDB客户端管理线程开启");
        if(t == null) {
            t = new Thread(this);
            t.start();
        }
    }

}