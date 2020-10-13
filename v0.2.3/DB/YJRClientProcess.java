package DB;
import java.io.*;
import java.net.Socket;
import DB.YJRClientManage;
import DB.YJRServerManage;
//Ϊÿһ��socket���ӽ�����дͨ��

//ʵ��Runnable�ӿ�
public class YJRClientProcess implements Runnable {
	//����
	private Socket socket;
	//�ͻ��˹���
	private YJRClientManage cm;

	
	public YJRClientProcess(Socket socket,YJRClientManage cm) {
		this.cm = cm;
		this.socket = socket;
	}
	
	public void run() {
		try {
			//����BufferedReader���ڶ�ȡ����
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//����PrintWriter�����ڷ�������
			final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			//���Լ�������Ӵ���ClientManage��
			cm.addSocket(socket);
			//����������߷���������
			writer.flush();
			writer.println("********** Welcome to YJRDB **********");
			while(true) {
				String line = reader.readLine();
				if(line.equals("logout")) {
					break;
				}
				else{
						cm.addMessage(line);
//		��β���ӡ�ˣ�����vMessage��			writer.println(line);
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
