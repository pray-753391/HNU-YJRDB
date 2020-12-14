package YJRdataBase;

import java.util.*;

import staticContent.*;

public abstract class Function implements java.io.Serializable{
    // _start 成员变量及函数定义
    public String name; // 函数名称
    public Vector param; // 参数数组

    public abstract Operand eval() throws Exception;// 由参数来计算结果
    //构造函数
    public Function() {
        this.name = "";
        this.param = new Vector();
    }
    public  Function(String name) {
        this.name = name;
        this.param = new Vector();
    }
    //两个参数的set方法
    public void SetName(String name) {
        this.name = name;
    }

    public void SetParam(Vector v) {
        this.param = v;
    }

    //将表达式加入自己的参数中
    public void AddParam(Expression exp) {
        this.param.addElement(exp);
    }
    //打印函数名和参数及个数
    public void PrintFunctionInfo() throws Exception
    {
        Object ob;
        Expression exp;
        System.out.println("函数名称："+name);
        System.out.println("参数个数："+param.size());
        for(int i=0;i<param.size();i++)
        {
            ob=param.get(i);
            System.out.println("第"+i+"个参数类型："+ob.getClass().getName());
//			exp=(Expression)ob;
//			System.out.println("第"+i+"个参数值："+exp.calExpression());
        }
    }
    // _end
    //根据函数名来匹配 返回的是
    public static Function get(String fname) {

        if (fname.toLowerCase().equals("max")) { return max_f; }
        else if (fname.toLowerCase().equals("min")) { return min_f; }
        else if (fname.toLowerCase().equals("length")) { return length_f; }
        else
            throw new Error("Unrecognised operator type: " + fname);
    }
    private final static MaxFunction max_f = new MaxFunction();
    private final static MinFunction min_f = new MinFunction();
    private final static LengthFunction length_f = new LengthFunction();
}


//_start 各函数及定义 都继承Function类
class MaxFunction extends Function {
    public MaxFunction() {
        super("MAX");
    }

    public Operand eval() throws Exception {
        Operand od = new Operand();
        Operand od2;

        if (param.size() == 0)
            throw new Exception("参数为空");
        //存在参数的话，先接收第一个
        od = StaticMethod.calObject(param.get(0));
        //再从后开始寻找
        for (int i = 1; i < param.size(); i++) {
            od2 =StaticMethod.calObject(param.get(i));
            //循环寻找最大值
            if (od2.getNumericValue() > od.getNumericValue())
                od = od2;
        }

        return od;
    }
};

class MinFunction extends Function {
    public MinFunction() {
        super("MIN");
    }

    //参数进行比较
    public Operand eval() throws Exception {
        Operand od = new Operand();
        Operand od2;

        if (param.size() == 0)
            throw new Exception("参数为空");
        od = StaticMethod.calObject(param.get(0));
        for (int i = 1; i < param.size(); i++) {
            od2 =StaticMethod.calObject(param.get(i));

            if (od2.getNumericValue() < od.getNumericValue())
                od = od2;
        }

        return od;
    }
};

class LengthFunction extends Function {
    public LengthFunction() {
        super("LENGTH");
    }

    public Operand eval() throws Exception {
        Operand od = new Operand();
        Operand ods = new Operand();
        String str;
        //自然只有一个参数能求长度
        if (param.size() != 1)
            throw new Exception("参数个数错误");
        od = StaticMethod.calObject(param.get(0));
        if(od.getType()!=DataType.STRING)
            throw new Exception("参数类型错误");
        str=od.GetStringValue();
        ods.SetINT(str.length()-2);


        return ods;
    }
};
// _end