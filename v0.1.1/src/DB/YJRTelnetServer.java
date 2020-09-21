package DB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

//����socket����
import java.net.*;
//�����̳߳أ��Կͻ��˽���ͳһ����
import java.util.concurrent.*;
import java.util.logging.Level;

//����ClientProcess
import DB.YJRClientProcess;


//���¿ͻ��˽���󣬾�Ϊÿһ���ͻ��˿���һ�����߳�
public class YJRTelnetServer {
	//�̳߳� ����һ���ɻ����̳߳أ�����̳߳س��ȳ���������Ҫ���������տ����̣߳����޿ɻ��գ����½��̡߳�
	private final static ExecutorService executor = Executors.newFixedThreadPool(100);
	
	//��������
	public static void run() {
		try {
			//����ServerSocket����2333�˿�
			ServerSocket server = new ServerSocket(2233);

			System.out.println("�Ѿ���ʼ����2233�˿�");
			
			while(true) {
				//�ȴ�����
				Socket socket = server.accept();
				 //���յ���������� ������дͨ��
                executor.execute(new YJRClientProcess(socket));
			}
			

		}catch (IOException e) {
			//�����
        	System.out.println(Level.WARNING+ e.toString()+" Shutting down the server..");
        } finally {
        	//���ر���Щ
            executor.shutdown();
        }
	}

}
