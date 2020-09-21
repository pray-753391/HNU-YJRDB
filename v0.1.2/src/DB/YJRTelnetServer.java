package DB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

//创建socket连接
import java.net.*;
//创建线程池，对客户端进行统一管理
import java.util.concurrent.*;
import java.util.logging.Level;

//导入ClientProcess
import DB.YJRClientProcess;


//有新客户端接入后，就为每一个客户端开启一个新线程
public class YJRTelnetServer {
	//线程池 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
	private final static ExecutorService executor = Executors.newFixedThreadPool(100);
	
	//进行运行
	public static void run() {
		try {
			//创建ServerSocket监听2223端口
			ServerSocket server = new ServerSocket(2223);

			System.out.println("已经开始监听2233端口");
			
			while(true) {
				//等待请求
				Socket socket = server.accept();
				 //接收到访问请求后 建立读写通道
                executor.execute(new YJRClientProcess(socket));
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
