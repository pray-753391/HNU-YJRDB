package staticContent;

import java.util.Stack;
import java.util.Vector;

public class StaticMethod {
	public static void main(String[] args) {
	}
	public static int calOd(int od1,int od2,String opstacktopstr) {
		//��������������ļ���
		int num=0;
		if (0 == opstacktopstr.compareTo("+"))
			num=od1+od2;
		if (0 == opstacktopstr.compareTo("-"))
			num=od1-od2;
		if (0 == opstacktopstr.compareTo("*"))
			num=od1*od2;
		if (0 == opstacktopstr.compareTo("/"))
			num=od1/od2;
		//���ؼ���ֵ
		return num;
	}
	//����ջ���洢����ʽ
	public static int calSimpleExp(Vector v) throws Exception {
		//��v��СΪ0ʱ˵���ޱ���ʽ���룬����޷����м���
		if (v.size() == 0)	throw new Exception("�޷�����");
		// ������ջ
		Stack opstack = new Stack();
		// ������ջ
		Stack odstack = new Stack();
		
		//����Object����
		Object o;
		
		//��������������ջ������������
		String opstr, opstacktopstr;
		
		//��������������������������
		int od1, od2;
		
		//������ʽ������ջ
		for (int i = 0; i < v.size(); i++) {
			//��v����Ķ���ֵ��o
			o = v.get(i);
			//�ж�o�����������Ƿ�Ϊ����
			if (o instanceof Integer)
				// ������ֱ��ѹջ
				odstack.push(o);
			else {
				//����������ջΪ�գ���������ֱ����ջ
				if (0 == opstack.size()) {
					opstack.add(o);
					continue;
				}
				//�鿴ջ��Ԫ��
				opstacktopstr = opstack.peek().toString();
				//��o�Ķ����¸�ֵ�Ĳ���������ת��ΪString���͸�ֵ��opstr����
				opstr = o.toString();
				//�Ƚ�ջ��Ԫ�غͽ�Ҫ��ջ��Ԫ�ص����ȼ�
				if (getOpPrecedence(opstr) > getOpPrecedence(opstacktopstr)) {
					//opstr�����ȼ�����opstacktopstr����opstrֱ����ջ
					opstack.push(opstr);
					continue;
				}
				while (true) {
					//��������ջ�Ĵ�СΪ0ʱ�������������������ʱ�����µĲ�����ֱ����ջ
					if (0 == opstack.size()) {
						opstack.push(opstr);
						break;
					}
					//��������ջ��Ԫ�ظ�ֵ��opstacktopstr����
					opstacktopstr = opstack.peek().toString();
					if (getOpPrecedence(opstr) > getOpPrecedence(opstacktopstr)) {
						opstack.push(opstr);
						break;
					}
					od2 = (int) odstack.pop();
					od1 = (int) odstack.pop();
					opstacktopstr = opstack.pop().toString();
					//���в������������������
					odstack.push(calOd(od1, od2, opstacktopstr));
				}
			}
		}
		//��������ջ�Ĵ�С��Ϊ0ʱ�����в������������������
		while(0<opstack.size())
		{
			od2 = (int) odstack.pop();
			od1 = (int) odstack.pop();
			opstacktopstr = opstack.pop().toString();
			odstack.push(calOd(od1, od2, opstacktopstr));
		}
		//���ز�������ջ��Ԫ��
		return (int)odstack.peek();
	}

	public static int getOpPrecedence(String opstr) {
		if (0 == opstr.compareTo("+"))
			return 1;
		if (0 == opstr.compareTo("-"))
			return 1;
		if (0 == opstr.compareTo("*"))
			return 2;
		if (0 == opstr.compareTo("/"))
			return 2;
		return 0;
	}
}