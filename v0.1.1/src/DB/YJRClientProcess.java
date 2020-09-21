package DB;
import java.io.*;
import java.net.Socket;

//Ϊÿһ��socket���ӽ�����дͨ��

//ʵ��Runnable�ӿ�
public class YJRClientProcess implements Runnable {
	private Socket socket;
	
	public YJRClientProcess(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			//����BufferedReader���ڶ�ȡ����
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//����PrintWriter�����ڷ�������
			final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			//����������߷���������
			writer.println("********** Welcome to YJRDB **********");
			writer.println();
			//ˢ��
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
