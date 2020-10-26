package DB;
import java.io.*;
import java.net.Socket;
import DB.YJRClientManage;
import DB.YJRServerManage;
//为每一个socket连接建立读写通道

//实现Runnable接口
public class YJRClientProcess implements Runnable {
	//连接
	private Socket socket;
	//客户端管理
	private YJRClientManage cm;

	
	public YJRClientProcess(Socket socket,YJRClientManage cm) {
		this.cm = cm;
		this.socket = socket;
	}
	
	public void run() {
		try {
			//创建BufferedReader用于读取数据
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//创建PrintWriter，用于发送数据
			final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			//将自己这个链接存入ClientManage中
			cm.addSocket(socket);
			//可以向访问者发送数据了
			writer.flush();
			writer.println("********** Welcome to YJRDB **********");
			while(true) {
				String line = reader.readLine();
				if(line.equals("logout")) {
					break;
				}
				else{
						cm.addMessage(line);
//		这次不打印了，存入vMessage中			writer.println(line);
				}
				
			}

			
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
