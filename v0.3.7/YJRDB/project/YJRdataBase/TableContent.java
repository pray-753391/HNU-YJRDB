package YJRdataBase;

import staticContent.*;

import java.io.*;
import java.util.Vector;

public class TableContent implements java.io.Serializable {
    private static final long serialVersionUID=1L;
    private String tName; //表名
    private Vector vColumn; //列
    private Vector vConstraint; //约束
    private Vector vData; //表中的数据

    public TableContent() {
        tName="";
        vColumn=new Vector();
        vConstraint=new Vector();
        vData=new Vector();
    }

    public void settName(String tName) {
        this.tName=tName;
    }

    public String gettName() {
        return tName;
    }

    public void setvColumn(Vector vColumn) {
        this.vColumn= vColumn;
    }

    public Vector getvColumn() {
        return vColumn;
    }

    public void setvConstraint(Vector vConstraint) {
        this.vConstraint= vConstraint;
    }

    public Vector getvConstraint() {
        return vConstraint;
    }

    public void setvData(Vector vData) {
        this.vData=vData;
    }

    public Vector getvData() {
        return vData;
    }

    //返回向量，第一个元素为返回代码，第二个值为返回显示的字符串

    public Vector executeSQL() throws Exception {
        //建立一个向量接收
        Vector v=new Vector();
        //合理性检查一：表名不能与已有的表重复
        GlobalTableInfo gtInfo=new GlobalTableInfo();
        if(gtInfo.fileIsExist(this.gettName())) {
            v.addElement(-1);
            v.addElement("无法建表，数据库中已有同名表");
            return v;
        }
        //合理性检查二：主键不能重复
        int pknum=0;
        //这个是指primary直接定义在该属性后的检查 即sno int primary key,cno int primary key这类的自增1
        for(int i=0;i<this.vColumn.size();i++) {
            if(((ColumnDef)this.vColumn.get(i)).isPrimaryKey()) {
                pknum++;
            }
            if(pknum>1) {
                v.addElement(-1);
                v.addElement("无法建表，在多个列中有主键定义");
                return v;
            }
        }
        //这个是指primary定义在一行语句最后的主键检查，即sno int primary key,cno int,primary key(sno,cno)这类的自增1
        for(int i=0;i<this.vConstraint.size();i++) {
            if(((TableConstraint)this.vConstraint.get(i)).getTcType()==1)
                pknum++;
            if(pknum>1) {
                v.addElement(-1);
                v.addElement("无法建表，多处有主键定义");
                return v;
            }
        }
        //合理性检查三：表级主键中，每个主键名都必须是表中有的列名
        //编写isNameInColumnVector()来判断某个字段名是否在向量中

        //合理性检查四：外键指定的表和列名存储
        TableConstraint tc;
        for(int i=0;i<vConstraint.size();i++) {
            tc=(TableConstraint)vConstraint.get(i);
            if(2!=tc.getTcType())
                continue;               //外键约束
            //创建一个约束
            GlobalTableInfo global=new GlobalTableInfo();
            //之前已经判断过表名和表列了 只用判断外表名和外表列是否存在 以及该表列是否equal外表列
            TableContent table = ReadFromFile((String)tc.getTcV().get(1));
            if(table.getFieldIndex((String)tc.getTcV().get(2),table.getvColumn())==-1){
                System.out.println((String)tc.getTcV().get(1)+"表中不存在"+(String)tc.getTcV().get(2)+"列，无法创建约束");
                System.exit(0);
            }
            String temp = (String)(tc.getTcV().get(0));
            String temp1 = (String)(tc.getTcV().get(2));
            if(!temp.equals(temp1)){
                System.out.println("做外键的两列名不相等，无法创建约束！");
                System.exit(0);
            }
            //四个参数分别是 该表名 该表列 外表名 外表列 （该表列和外表列一定是相等的，同时外表必须有该列）
            global.addReference(this.tName,(String)tc.getTcV().get(0),
                    (String)tc.getTcV().get(1),(String)tc.getTcV().get(2));

            global.WriteToFile();
        }
        //写入文件
        try {
            this.WriteToFile();
            //gtInfo.WriteToFile();
        }catch(Exception e){
            System.out.println(e.toString());
        }
        //成功时返回的是1
        v.addElement(1);
        v.addElement("执行成功");
        return v;
    }

    public void addRowData(Vector vRowData) {  //将列数据存入数组中
        this.vData.addElement(vRowData);
    }

    //得到主键列，每个元素为一个ColumnDef对象
    public Vector getPrimaryColumn() {
        Vector v=new Vector();
        for(int i=0;i<this.vColumn.size();i++) {
            if(((ColumnDef)this.vColumn.get(i)).isPrimaryKey()) {
                v.addElement(this.vColumn.get(i));
                return v;
            }
        }
        for(int i=0;i<this.vConstraint.size();i++) {
            if(((TableConstraint)this.vConstraint.get(i)).getTcType()==1)
                v.addElement(this.vConstraint.get(i));
            return v;
        }
        return v;
    }


    public int getFieldIndex(String colname,Vector vColumn) {
        ColumnDef coldef=null;
        for(int i=0;i<vColumn.size();i++) {
            coldef=(ColumnDef)vColumn.get(i);
            if(0==colname.compareToIgnoreCase(coldef.getColName()))
                return i;
        }
        return -1;
    }

    public String getDescribe() {
        String result="";
        Vector v;
        result+="表名："+this.gettName()+"\n";
        result+="共有"+this.getvColumn().size()+"个字段\n";
        v=this.getvColumn();
        for(int i=0;i<v.size();i++)
            result+=((ColumnDef) v.get(i)).getDescribe();
        result+="共有"+this.getvConstraint().size()+"个约束\n";
        v=this.getvConstraint();
        for(int i=0;i<v.size();i++)
            result+=((TableConstraint)v.get(i)).getDescribe();
        result+="共有"+this.getvData().size()+"条数据\n";
        v=this.getvColumn();
        for(int i=0;i<v.size();i++)
            result+=((ColumnDef)v.get(i)).getColName()+" ";
        result+="\n";
        Vector vRowData;
        Operand od;
        for(int i=0;i<this.vData.size();i++) {
            vRowData=(Vector)this.vData.get(i);
            for(int j=0;j<vRowData.size();j++) {
                od=(Operand)vRowData.get(j);
                result+=od.getString()+"\t";
            }
            result+="\n";
        }
        return result;
    }

    /*
     * 对象操作流(ObjectOutputStream,ObjectInputStream)可以将一个对象写出，或者读取一个对象到程序中
     * 也就是执行了序列化和反序列化操作。
     */
    public void WriteToFile() throws Exception{
        String filename=this.gettName()+".db";
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(this);
        oos.close();
    }

    public TableContent ReadFromFile(String tablename) throws IOException,ClassNotFoundException {
        ObjectInputStream ois =new ObjectInputStream(new FileInputStream(tablename+".db"));
        TableContent tc=(TableContent)ois.readObject();
        ois.close();
        return tc;
    }

}