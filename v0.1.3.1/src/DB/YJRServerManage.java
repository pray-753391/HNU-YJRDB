package DB;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import DB.YJRClientManage;

import java.util.Scanner;
import java.util.Vector;
public class YJRServerManage implements Runnable{
	//�����߳�
	private Thread t;
	//����ͻ��˹���
	private YJRClientManage cm;

 
	
	//��ʼ��
	YJRServerManage(YJRClientManage cm){
		this.cm = cm;
	}
		
	public void run() {
		Scanner sn = new Scanner(System.in);
		String line = new String();

		//��ѭ������ �ڿ���̨������ ���������Ӧ������  ����ݲ�ͬ������в�ͬ����
		while(true) {
			if(sn.hasNextLine()) {
				line = sn.nextLine();
				if(line.equals("shutdown")) {}
				else if(line.equals("showclientnum")) {
					System.out.println(cm.GetvSocketsize());
				}
				if(line.equals("showallclientinfo")) {}
			}
		}
	}
	//����һ�����߳�
	public void start() {
		System.out.println("YJRDB�����������߳̿���");
		if(t == null) {
			t = new Thread(this);
			t.start();
		}
	}
}
