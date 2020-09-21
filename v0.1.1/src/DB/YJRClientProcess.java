package DB;
import java.io.*;
import java.net.Socket;

//为每一个socket连接建立读写通道

//实现Runnable接口
public class YJRClientProcess implements Runnable {
	private Socket socket;
	
	public YJRClientProcess(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			//创建BufferedReader用于读取数据
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//创建PrintWriter，用于发送数据
			final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			//可以向访问者发送数据了
			writer.println("********** Welcome to YJRDB **********");
			writer.println();
			//刷新
			writer.flush();
		}
			catch (IOException e) {
				System.out.println(e.toString());
			} finally {
				try {
					socket.close();
				} catch (IOException e) {

				}
		}
	}
	
}
