package DB;
import java.io.*;
import java.net.Socket;
import java.util.Vector;
public class YJRClientManage implements Runnable{
	//�����߳�
	private Thread t;
	//����ѭ�� ���̶߳�ʱִ������
	//���ڱ���ÿ���ͻ�������
	private Vector vSocket;
	//���ڱ���ÿ���ͻ��˷��͹�������Ϣ
	private Vector vMessage;

	
	//��ʼ��
	YJRClientManage(){
		vSocket = new Vector();
		vMessage = new Vector();
	}
	
	//��ӿͻ���socket����
	public void addSocket(Object o) {
		vSocket.addElement(o);
	}
	
	//��ӿͻ��˷��͵���Ϣ
	public void addMessage(String str) {
		vMessage.addElement(str);
	}
	
	public Vector GetvSocket(){
		return vSocket;
	}
	
	//Ⱥ��vMessage�е���Ϣ
	public void ClientSend() throws IOException {
		//���ȴ�ӡvMessage�����Ϣ ���ҰѴ�ӡ����Ϣ�����vMessage
		//��ÿһ��vMessage�����ÿһ��vSocket ������vMessage����� vSocket���ڲ�
		for(int i = 0;i<vMessage.size();i++) {
			for(int j = 0;j<vSocket.size();j++) {
				Socket temp = (Socket)vSocket.get(j);
				//��ȡ���socket���ӷ��͵���Ϣ
				PrintWriter writer = new PrintWriter(temp.getOutputStream(), true);
				writer.println(vMessage.get(i));
				writer.flush();
			}
		}
		//��ӡ��� ɾ��������Ϣ
		vMessage.removeAllElements();
	}
	
	//��ÿ��socket�ͻ������ӽ��м��
	public void LinkTest() {
		//��Vsocket���б���
		for(int i = vSocket.size()-1; i>=0 ; i--) {
			//��ȡ���б����һ��socket
			Socket temp = (Socket)vSocket.get(i);
			//������˾��Ƴ�
		if(temp.isClosed()) {
			vSocket.remove(i);
		}
	  }
	}
		

		
		
	public void run() {
		try {
			//����ѭ�� ���̶߳�ʱִ������
			while(true) {
				//�ȼ���߳�����
				this.LinkTest();
				//��Ⱥ����Ϣ
				this.ClientSend();
				//�̳߳�˯200����
				Thread.sleep(200);
				}
			}catch(Exception e) {}
		}
	
	//����һ�����߳�
	public void start() {
		System.out.println("YJRDB�ͻ��˹����߳̿���");
		if(t == null) {
			t = new Thread(this);
			t.start();
		}
	}
	
}
