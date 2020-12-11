package YJRdataBase;
import java.util.*;
import staticContent.*;
public class Expression implements java.io.Serializable  {

    private Vector elements = new Vector();
    private	String	expstr="";


    //把操作符加入向量
    public void addOperator(Operator op) {
        elements.add(op);
    }
    //ob如果属于操作数或是操作符或是函数或是变量就加入element数组中，否则抛出错误
    public void addElement(Object ob) {
        if (ob instanceof Operand
                || ob instanceof Operator
                || ob instanceof Function
                || ob instanceof Variable) {
            elements.add(ob);
        } else {
            throw new Error("Unknown element type added to expression: " + ob.getClass());
        }
    }

    public Object elementAt(int n) {
        return elements.get(n);
    }
    //设置指定位置的element
    public void setElementAt(int n, Object ob) {
        elements.set(n, ob);
    }
    //返回当前的elements
    public Vector getElements()
    {
        return elements;
    }
    //打印表达式数组中的元素
    public void printElements() {
        Object ob;
        for (int i = 0; i < elements.size(); i++) {
            ob = elements.get(i);
            //打印类型
            System.out.print(ob.getClass().getName() + " ");
            //打印具体数据
            if (ob instanceof Operand)
                System.out.print(((Operand) ob).getString() + " ");
            else if (ob instanceof Operator)
                System.out.print(((Operator) ob).stringRepresentation());
            System.out.println();
        }
    }
    //打印表达式数组中的元素
    public String getDescribe() {
        Object ob;
        String str1="";
        for (int i = 0; i < elements.size(); i++) {
            ob = elements.get(i);
            if (ob instanceof Operand)
                str1+=((Operand) ob).getString() + " ";
            else if(ob instanceof Variable)
                str1+=((Variable)ob).GetName() + " ";
            else if (ob instanceof Operator)
                str1+=((Operator) ob).stringRepresentation() + " ";
        }
        str1+="\n";
        return str1;
    }


    //把传入的expression表达式加入该表达式中
    public void mergerExpression(Expression exp2) {
        int n = exp2.elements.size();
        for (int i = 0; i < n; i++)
            this.elements.add(exp2.elementAt(i));
    }

    //设置expstr
    public void setExpStr(String str)
    {
        expstr=str;
    }

    //返回表达式的字符串
    public String getString()
    {
        return expstr;
    }


}
