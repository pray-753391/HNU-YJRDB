package YJRdataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;
public class DropAndAlter {
    private String sTablename;
    private TableContent tables;
    private String colname;
    //1是删除 2是更改结构
    public int num;
    //增加一列的话 会有数据类型
    private String DataType;

    public DropAndAlter() {
        sTablename = "";
        num = 0;
        DataType="aaa";
        colname="";
        tables=null;
    }

    public void setDataType(String DataType) {
        this.DataType = DataType;
    }

    public String getDataType() {
        return DataType;
    }

    public void setsTablename(String sTablename) {
        this.sTablename = sTablename;
    }

    public String getsTablename() {
        return sTablename;
    }

    public void setcolname(String colname) {
        this.colname = colname;
    }

    public String getcolname() {
        return colname;
    }

    public void executeSQL() throws Exception {
        //先读取数据库
        ReadFromFile();
        //如果num为1 说明是删整个表
        if (num == 1) {
            Vector v = new Vector();//返回向量，第一个元素为返回代码，-1表示出错，第二个元素为返回显示字符串
            //合理检查一：检测表名是否存在
            GlobalTableInfo gtInfo = new GlobalTableInfo();
            if (!gtInfo.fileIsExist(this.sTablename)) {
                v.addElement(-1);
                v.addElement("数据库中不存在此表");
                return;
            }
            //合理性检查二：没有其他表参照引用此表
            //合理性检查三：表中无数据
            //检查完毕，开始删除文件
            try {
                File file = new File(this.sTablename + ".db");
                if (file.delete())
                    System.out.println("数据库表" + this.sTablename + ".db删除成功！");
                else
                    System.out.println("数据库表" + this.sTablename + ".db删除失败！");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }else if(num==2){ //此时说明是改表
            //如果是删除某列 那么此时DataType是aaa
            if(DataType=="aaa"){ //此时说明是删除列
                //遍历所有列 列名相等的就删除该列
                for(int i=0;i<tables.getvColumn().size();i++){
                    ColumnDef col = (ColumnDef)tables.getvColumn().get(i);
                    if(colname.equals(col.getColName())){
                        tables.getvColumn().removeElementAt(i);
                        System.out.println(colname+"列删除成功");
                        //对应的vData也要删除
                        for(int j=0;j<tables.getvData().size();j++){
                            //每一个向量把第i列位置去掉
                            ((Vector)tables.getvData().elementAt(j)).removeElementAt(i);
                        }
                        System.out.println(colname+"列数据删除成功");
                        tables.WriteToFile();
                        System.exit(0);
                    }
                }
                //否则就是没有该列
                System.out.println(sTablename+"表中没有"+colname+"列");
                System.exit(0);
            }
        }
    }
    public void ReadFromFile() throws Exception{
        String filename=this.getsTablename()+".db";
        String path = "/Users/yinjianrong/OneDrive/IdeaProjects/YJRDB/";
        ObjectInputStream oos=new ObjectInputStream(new FileInputStream(path+filename));
        tables= (TableContent)oos.readObject();

        oos.close();
    }
}
