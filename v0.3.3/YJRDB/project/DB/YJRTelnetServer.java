package DB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

//导入ClientProcess


//有新客户端接入后，就为每一个客户端开启一个新线程
public class YJRTelnetServer {
    //线程池 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    private final static ExecutorService executor = Executors.newFixedThreadPool(100);

    //服务器用户管理cm
    private static YJRClientManage cm;

    //客户端管理sm
    private static YJRServerManage sm;

    //进行运行
    public static void run() {
        try {
            //创建ServerSocket监听2223端口
            ServerSocket server = new ServerSocket(2223);
            System.out.println("已经开始监听2223端口");
            cm = new YJRClientManage();
            cm.start();
            sm = new YJRServerManage(cm);
            sm.start();
            //激活服务器管理
            executor.execute(cm);

            //激活客户端管理
            executor.execute(sm);

            while(true) {
                //等待请求
                Socket socket = server.accept();
                //接收到访问请求后 建立读写通道
                //因为YJRClientProcess是一个线程，相当于执行一个线程
                executor.execute(new YJRClientProcess(socket,cm));
            }


        }catch (IOException e) {
            //出错后
            System.out.println(Level.WARNING+ e.toString()+" Shutting down the server..");
        } finally {
            //最后关闭这些
            executor.shutdown();
        }
    }

}
