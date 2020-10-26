package YJRdataBase;

import java.util.*;

import staticContent.*;

public class Expression implements java.io.Serializable  {

	private Vector elements = new Vector();
	private	String	expstr="";
	public void addOperator(Operator op) {
		elements.add(op);
	}

	public void addElement(Object ob) {
		if (ob instanceof Operand || ob instanceof Operator || ob instanceof Function || ob instanceof Variable) {
			elements.add(ob);
		} else {
			throw new Error("Unknown element type added to expression: " + ob.getClass());
		}
	}

	public Object elementAt(int n) {
		return elements.get(n);
	}

	public void setElementAt(int n, Object ob) {
		elements.set(n, ob);
	}
	public Vector getElements()
	{
		return elements;
	}
	public void printElements() {
		Object ob;
		for (int i = 0; i < elements.size(); i++) {
			ob = elements.get(i);
			System.out.print(ob.getClass().getName() + " ");
			if (ob instanceof Operand)
				System.out.print(((Operand) ob).getString() + " ");
			else if (ob instanceof Operator)
				System.out.print(((Operator) ob).stringRepresentation());
			
			System.out.println();
		}
	}



	public void mergerExpression(Expression exp2) {
		int n = exp2.elements.size();
		for (int i = 0; i < n; i++)
			this.elements.add(exp2.elementAt(i));
	}
	
	public	void	setExpStr(String	str)
	{
		expstr=str;
	}
	
	public String getString()
	{
		return expstr;
	}
	

}

