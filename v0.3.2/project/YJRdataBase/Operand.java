package YJRdataBase;
import staticContent.*;
/*
 * 操作数类，每个操作数
 */
public class Operand implements java.io.Serializable{

    private byte[] content; // 内容

    // _start 构造、设置及取值函数
    //构造函数
    public Operand() {
        this.SetNULL();
    }
    //把content设置为空
    public void SetNULL() {
        if (content == null)
            content = new byte[1];
        if (1 != content.length) {
            content = null;
            content = new byte[1];
        }
//		content = new byte[5];
        content[0] = (byte) DataType.NULL;
    }
    /**
     * 构造函数-整数
     *
     * @param i 需要存储的整数
     * @return 操作数类
     */
    public Operand(int i) {
        this.SetINT(i);
    }

    /**
     * 设置值函数-整数
     *
     * @param i 需要存储的整数
     * @return 无
     */
    public void SetINT(int i) {

        if (content == null)
            content = new byte[5];
        if (5 != content.length) {
            content = null;
            content = new byte[5];
        }
//		content = new byte[5];
        content[0] = (byte) DataType.INT;
        content[1] = (byte) ((i & 0xff000000) >> 24);
        content[2] = (byte) ((i & 0x00ff0000) >> 16);
        content[3] = (byte) ((i & 0x0000ff00) >> 8);
        content[4] = (byte) (i & 0x000000ff);

    }

    /**
     * 取值函数-整数
     *
     * @return 对应的整数值，如果不是整数类型则抛出异常
     */
    public int GetIntValue() throws Exception {
        if (5 != content.length)
            throw new Exception("非法数据类型转换：");
        if (content.length > 0) {
            if (DataType.INT != content[0])
                throw new Exception("非法数据类型转换：");
        }

        return (0xff000000 & (content[1] << 24)) | (0x00ff0000 & (content[2] << 16)) | (0x0000ff00 & (content[3] << 8))
                | (0x000000ff & content[4]);

    }

    /**
     * 构造函数-浮点数
     *
     * @param f 需要存储的浮点数
     * @return 操作数类
     */
    public Operand(float f) {
        this.SetFLOAT(f);
    }

    /**
     * 设置值函数-浮点数
     *
     * @param f 需要存储的浮点数
     * @return 无
     */
    public void SetFLOAT(float f) {
        if (content == null)
            content = new byte[5];
        if (5 != content.length) {
            content = null;
            content = new byte[5];
        }
//		content = new byte[5];
        int i = Float.floatToIntBits(f);
        this.SetINT(i);
        content[0] = DataType.FLOAT;
    }

    /**
     * 取值函数-浮数
     *
     * @return 对应的浮点数值，如果不是浮点数类型则抛出异常
     */
    public float GetFloatValue() throws Exception {
        if (5 != content.length)
            throw new Exception("非法数据类型转换：");
        if (content.length > 0) {
            if (DataType.FLOAT != content[0])
                throw new Exception("非法数据类型转换：");
        }

        return Float.intBitsToFloat((0xff000000 & (content[1] << 24)) | (0x00ff0000 & (content[2] << 16))
                | (0x0000ff00 & (content[3] << 8)) | (0x000000ff & content[4]));
    }

    /**
     * 构造函数-布尔值
     *
     * @param b 需要存储的布尔值
     * @return 操作数类
     */
    public Operand(boolean b) {
        this.SetBOOL(b);
    }

    /**
     * 设置值函数-布尔值
     *
     * @param b 需要存储的布尔值，如果是true，则保存为1，如果为false，则保存为0
     * @return 无
     */
    public void SetBOOL(boolean b) {

        if (content == null)
            content = new byte[2];
        if (2 != content.length) {
            content = null;
            content = new byte[2];
        }
//		content = new byte[5];
        content[0] = (byte) DataType.BOOL;
        if (b)
            content[1] = (byte) 1;
        else
            content[1] = (byte) 0;
    }

    /**
     * 取值函数-布尔值
     *
     * @return 对应的布尔值，如果不是布尔值类型则抛出异常
     */
    public boolean GetBoolValue() throws Exception {
        if (2 != content.length)
            throw new Exception("非法数据类型转换：");
        if (content.length > 0) {
            if (DataType.BOOL != content[0])
                throw new Exception("非法数据类型转换：");
        }

        return (content[1] == 1);
    }

    /**
     * 构造函数-字符串
     *
     * @param str 需要存储的字符串
     * @return 操作数类
     */
    public Operand(String str) {
        this.SetSTRING(str);
    }

    /**
     * 设置值函数-字符串
     *
     * @param str 需要存储的字符串
     * @return 无
     */
    public void SetSTRING(String str) {

        if (content == null)
            content = new byte[str.length() + 2];
        if (str.length() + 2 != content.length) {
            content = null;
            content = new byte[str.length() + 2];
        }
//		content = new byte[5];
        content[0] = (byte) DataType.STRING;
        content[1] = (byte) (str.length());// 字符串长度不能超过128
        for (int i = 2; i < str.length() + 2; i++)
            content[i] = (byte) str.charAt(i - 2);
    }

    /**
     * 取值函数-字符串
     *
     * @return 对应的字符串值，如果不是字符串类型则抛出异常
     */
    public String GetStringValue() throws Exception {
        if (content.length < 2)
            throw new Exception("非法数据类型转换：");
        if (DataType.STRING != content[0])
            throw new Exception("非法数据类型转换：");

        String str = "";
        for (int i = 2; i < content[1] + 2; i++)
            str += (char) content[i];
        return str;

    }

    // _end*/

    // _start 辅助函数
    /**
     * 显示操作数的原始内容
     */
    public void showOriginContent() {
        for (int i = 0; i < content.length; i++)
            System.out.print("0x" + byteToHex(content[i]) + " ");
        System.out.println();
    }

    /**
     * 显示操作数的字符串形式
     */
    public String getString() {
        if (content == null)
            return "";
        try {
            switch (content[0]) {
                case DataType.INT:
                    return Integer.toString(GetIntValue());
                case DataType.FLOAT:
                    return Float.toString(GetFloatValue());
                case DataType.BOOL:
                    return Boolean.toString(GetBoolValue());
                case DataType.STRING:
                    return GetStringValue();
                default:
                    return "";
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "";
    }

    /**
     * 字节转十六进制
     *
     * @param b 需要进行转换的byte字节
     * @return 转换后的Hex字符串
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    public float getNumericValue() throws Exception
    {
        if (!(DataType.INT == content[0] || DataType.FLOAT == content[0] ))
            throw new Exception("操作数无法获得数值");
        if (DataType.INT == this.content[0])
            return (float)this.GetIntValue();
        else
            return this.GetFloatValue();
    }
    public int getType()
    {
        return (int)content[0];
    }
    // _end

    // _start 操作数运算函数 加 减 乘 除 取余
    /*
     * 加法运算
     */
    public Operand OperatorAdd(Operand od) throws Exception {
        if (!(DataType.INT == content[0] || DataType.FLOAT == content[0] || DataType.STRING == content[0]))
            throw new Exception("操作数无法做加法运算");
        if (!(DataType.INT == od.content[0] || DataType.FLOAT == od.content[0] || DataType.STRING == od.content[0]))
            throw new Exception("操作数无法做加法运算");
        Operand rod=new Operand();
        // 整数+整数 结果为整数
        if (DataType.INT == this.content[0] && DataType.INT == od.content[0]) {
            rod.SetINT(this.GetIntValue() + od.GetIntValue());
        }
        // 整数+浮点数 结果为浮点数
        else if (DataType.INT == this.content[0] && DataType.FLOAT == od.content[0]) {
            rod.SetFLOAT(this.GetIntValue() + od.GetFloatValue());
        }
        // 浮点数+整数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.INT == od.content[0]) {
            rod.SetFLOAT(this.GetFloatValue() + od.GetIntValue());
        }
        // 浮点数+浮点数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.FLOAT == od.content[0]) {
            rod.SetFLOAT(this.GetFloatValue() + od.GetFloatValue());
        } else if (DataType.STRING == this.content[0] && DataType.STRING == od.content[0]) {
            rod.SetSTRING(this.getString() + od.getString());
        } else
            throw new Exception("操作数无法做加法运算");
        return rod;
    }

    /*
     * 减法运算
     */
    public Operand OperatorSub(Operand od) throws Exception {
        if (!(DataType.INT == this.content[0] || DataType.FLOAT == this.content[0]))
            throw new Exception("操作数无法做减法运算");
        if (!(DataType.INT == od.content[0] || DataType.FLOAT == od.content[0]))
            throw new Exception("操作数无法做减法运算");
        Operand rod=new Operand();
        // 整数-整数 结果为整数
        if (DataType.INT == this.content[0] && DataType.INT == od.content[0]) {
            rod.SetINT(this.GetIntValue() - od.GetIntValue());
        }
        // 整数-浮点数 结果为浮点数
        else if (DataType.INT == this.content[0] && DataType.FLOAT == od.content[0]) {
            rod.SetFLOAT(this.GetIntValue() - od.GetFloatValue());
        }
        // 浮点数-整数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.INT == od.content[0]) {
            rod.SetFLOAT(this.GetFloatValue() - od.GetIntValue());
        }
        // 浮点数-浮点数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.FLOAT == od.content[0]) {
            rod.SetFLOAT(this.GetFloatValue() - od.GetFloatValue());
        }
        return rod;
    }

    /*
     * 乘法运算
     */
    public Operand OperatorMul(Operand od) throws Exception {
        if (!(DataType.INT == this.content[0] || DataType.FLOAT == this.content[0]))
            throw new Exception("操作数无法做乘法运算");
        if (!(DataType.INT == od.content[0] || DataType.FLOAT == od.content[0]))
            throw new Exception("操作数无法做乘法运算");
        Operand rod=new Operand();
        // 整数*整数 结果为整数
        if (DataType.INT == this.content[0] && DataType.INT == od.content[0]) {
            rod.SetINT(this.GetIntValue() * od.GetIntValue());
        }
        // 整数*浮点数 结果为浮点数
        else if (DataType.INT == this.content[0] && DataType.FLOAT == od.content[0]) {
            rod.SetFLOAT(this.GetIntValue() * od.GetFloatValue());
        }
        // 浮点数*整数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.INT == od.content[0]) {
            rod.SetFLOAT(this.GetFloatValue() * od.GetIntValue());
        }
        // 浮点数*浮点数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.FLOAT == od.content[0]) {
            rod.SetFLOAT(this.GetFloatValue() * od.GetFloatValue());
        }
        return rod;
    }

    /*
     * 除法运算(非整除)
     */
    public Operand OperatorFDiv(Operand od) throws Exception {
        if (!(DataType.INT == this.content[0] || DataType.FLOAT == this.content[0]))
            throw new Exception("操作数无法做除法运算");
        if (!(DataType.INT == od.content[0] || DataType.FLOAT == od.content[0]))
            throw new Exception("操作数无法做除法运算");
        float f1, f2;
        Operand rod=new Operand();
        // 整数/整数 结果为浮点数
        if (DataType.INT == this.content[0] && DataType.INT == od.content[0]) {
            f1 = (float) this.GetIntValue();
            f2 = (float) od.GetIntValue();
            rod.SetFLOAT(f1 / f2);
        }
        // 整数/浮点数 结果为浮点数
        else if (DataType.INT == this.content[0] && DataType.FLOAT == od.content[0]) {
            f1 = (float) this.GetIntValue();
            rod.SetFLOAT(f1 / od.GetFloatValue());
        }
        // 浮点数/整数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.INT == od.content[0]) {
            f2 = (float) od.GetIntValue();
            rod.SetFLOAT(this.GetFloatValue() / f2);
        }
        // 浮点数/浮点数 结果为浮点数
        else if (DataType.FLOAT == this.content[0] && DataType.FLOAT == od.content[0]) {
            rod.SetFLOAT(this.GetFloatValue() / od.GetFloatValue());
        }
        return rod;
    }

    /*
     * 取余运算，必须是两个整数才能取余
     */
    public Operand OperatorMod(Operand od) throws Exception {
        if (!(DataType.INT == this.content[0]))
            throw new Exception("操作数无法做取余运算");
        if (!(DataType.INT == od.content[0]))
            throw new Exception("操作数无法做取余运算");
        Operand rod=new Operand();
        // 整数/整数 结果为整数
        rod.SetINT(this.GetIntValue() % od.GetIntValue());
        return rod;
    }

    /*
     * 大于运算
     */
    public Operand OperatorGreat(Operand od) throws Exception {
        if (!(DataType.INT == this.content[0] || DataType.FLOAT == this.content[0]))
            throw new Exception("操作数无法做比较运算");
        if (!(DataType.INT == od.content[0] || DataType.FLOAT == od.content[0]))
            throw new Exception("操作数无法做比较运算");
        float f1, f2;
        Operand rod=new Operand();
        if (DataType.INT == this.content[0])
            f1 = (float) this.GetIntValue();
        else
            f1 = this.GetFloatValue();
        if (DataType.INT == od.content[0])
            f2 = (float) od.GetIntValue();
        else
            f2 = od.GetFloatValue();
        rod.SetBOOL(f1 > f2);
        return rod;
    }

    /*
     * 小于运算
     */
    public Operand OperatorLess(Operand od) throws Exception {
        if (!(DataType.INT == this.content[0] || DataType.FLOAT == this.content[0]))
            throw new Exception("操作数无法做比较运算");
        if (!(DataType.INT == od.content[0] || DataType.FLOAT == od.content[0]))
            throw new Exception("操作数无法做比较运算");
        float f1, f2;
        Operand rod=new Operand();
        if (DataType.INT == this.content[0])
            f1 = (float) this.GetIntValue();
        else
            f1 = this.GetFloatValue();
        if (DataType.INT == od.content[0])
            f2 = (float) od.GetIntValue();
        else
            f2 = od.GetFloatValue();
        rod.SetBOOL(f1 < f2);
        return rod;
    }

    /*
     * 等于运算
     */
    public Operand OperatorEqual(Operand od) throws Exception {
        if (!(DataType.INT == this.content[0] || DataType.FLOAT == this.content[0]))
            throw new Exception("操作数无法做比较运算");
        if (!(DataType.INT == od.content[0] || DataType.FLOAT == od.content[0]))
            throw new Exception("操作数无法做比较运算");
        Operand rod=new Operand();
        if (this.content[0] == od.content[0]) {
            rod.SetBOOL((content[1] == od.content[1]) && (content[2] == od.content[2]) && (content[3] == od.content[3])
                    && (content[4] == od.content[4]));
        } else {
            float f1, f2;
            if (DataType.INT == this.content[0])
                f1 = (float) this.GetIntValue();
            else
                f1 = this.GetFloatValue();
            if (DataType.INT == od.content[0])
                f2 = (float) od.GetIntValue();
            else
                f2 = od.GetFloatValue();
            rod.SetBOOL(Math.abs(f1 - f2) < 0.0000001);
        }
        return rod;
    }

    /*
     * 布尔值的与运算
     */
    public Operand OperatorAnd(Operand od) throws Exception {
        if (!(DataType.BOOL == this.content[0] && DataType.BOOL == od.content[0]))
            throw new Exception("操作数无法做与运算");
        Operand rod=new Operand();
        rod.SetBOOL(this.GetBoolValue() && od.GetBoolValue());
        return rod;
    }

    /*
     * 布尔值的或运算
     */
    public Operand OperatorOr(Operand od) throws Exception {
        if (!(DataType.BOOL == this.content[0] && DataType.BOOL == od.content[0]))
            throw new Exception("操作数无法做或运算");
        Operand rod=new Operand();
        rod.SetBOOL(this.GetBoolValue() || od.GetBoolValue());
        return rod;
    }

    /*
     * 布尔值的非运算
     */
    public Operand OperatorNot(Operand od) throws Exception {
        if (!( DataType.BOOL == od.content[0]))
            throw new Exception("操作数无法做非运算");
        Operand rod=new Operand();
        rod.SetBOOL(!od.GetBoolValue());
        return rod;
    }
    // _end

    public static void main(String args[]) {
        System.out.println("-----操作数类测试----");
        Operand o1 = new Operand(-1);
        Operand o2 = new Operand(7);

        try {
            // o1.OperatorMod(o2);
            System.out.println(o1.GetIntValue());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
