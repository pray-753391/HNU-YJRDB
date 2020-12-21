package YJRdataBase;
import java.io.*;
import java.util.Vector;

public class AddDeleteInsertSelect implements java.io.Serializable{

    private String sTablename;
    private TableContent tables;
    //表示增删改查哪一个功能 1增 2删 3改 4查
    public int num;
    //对哪个列进行操作
    private Vector<String> colname;
    //增删改的话会有值
    private Vector Values;
    //用来判断约束的GlobalTableInfo
    private Vector global;
    public AddDeleteInsertSelect() {
        sTablename="";
        tables=null;
        colname=new Vector();
        Values=new Vector();
        global = new Vector();
    }

    public void setsTablename(String sTablename) {
        this.sTablename=sTablename;
    }

    public String getsTablename() {
        return sTablename;
    }

    public void setcolname(String colname) {
        this.colname.addElement(colname);
    }

    public Vector getscolname() {
        return colname;
    }

    public void setValues(String values) { this.Values.addElement(values); }

    public Vector getValues() {
        return Values;
    }

    public Vector getForeignKeyLocal(){
        Vector Foreign = new Vector();
        for(int i=0;i<tables.getvColumn().size();i++){
            ColumnDef col=(ColumnDef)(tables.getvColumn().get(i));
            if(col.isPrimaryKey()==true) Foreign.add(i);
        }
        return Foreign;
    }

    public void executeSQL() throws Exception {
        //读取db数据库
        ReadFromFile();
        //读取约束总表
        ReadGlobalFromFile();
        //增加数据
        if(num==1){AddRow();}
        //删除数据
        else if(num==2){DeleteRow();}
        //更改数据
        else if(num==3){ChangeRow();}
        //查询数据
        else if(num==4) {SelectRow();}

        //需要把数据再次写入文件
        tables.WriteToFile();
    }

    //增删改查-增
    public void AddRow() throws Exception {
        Vector<String> Values = InsertValue();
        //直接进行插入
        tables.addRowData(Values);
        System.out.println("表插入数据成功");
        tables.WriteToFile();
    }
    //增删改查-删
    public void Detele() throws Exception {
        //说明此时指定了 where colname=value
        //先判断列名是否存在
        ColNameIsExist();
        //然后找到这个col的位置
        int local=0;
        for(int i=0;i<tables.getvColumn().size();i++){
            ColumnDef col=(ColumnDef)(tables.getvColumn().get(i));
            if(col.getColName().equals(colname.get(0))) local=i;
        }
        //用一个新的向量接收更改后的向量 即不相等的全部存入新向量 然后替换
        Vector row = new Vector<>();
        for(int i=0;i<tables.getvData().size();i++) {
            //用temp保存
            Vector temp = (Vector) tables.getvData().get(i);
            if (!temp.get(local).equals(getValues().get(0))){
                row.addElement(tables.getvData().get(i));
            }
        }
        tables.setvData(row);
        tables.WriteToFile();
    }
    public void DeleteRow() throws Exception {
        //为null 说明删除整个表内的内容 表结构不变
        if(colname.get(0).equals("all")){
            //获取data removeall即可
            tables.getvData().removeAllElements();
            System.out.println("表内数据已全部清空");
        }
        else {
            Detele();
            //此时这张表内的数据已经删除 然后检查该列是否为其他表的外键（被参照表的被参照列） 如果是的话 那参照表的相应行也要删除
            for(Object i :global){
                GlobalTableInfo global = (GlobalTableInfo)i;
                if(sTablename.equals((global.getrtablename())) && colname.get(0).equals(global.getrcolumnname())){
                //这个时候执行删除操作 即将表名定义为参照表 列名不变 值也不变 在那个表进行删除操作
                    this.setsTablename(global.ptablename);
                    ReadFromFile();
                    //执行删除命令
                    Detele();
            }
        }
        }
    }
    //增删改查-改
    public void ChangeRow() throws Exception {
        //首先判断列名是否存在
        ColNameIsExist();
        //local存储的是set的那列的位置 local1存储的是where那列的位置
        int local=0;
        int local1=0;
        //找两个列的位置
        for(int i=0;i<tables.getvColumn().size();i++){
            ColumnDef col=(ColumnDef)(tables.getvColumn().get(i));
            if(col.getColName().equals(colname.get(1))) local1=i;
            else if(col.getColName().equals(colname.get(0))) local=i;
        }
        //在每行的向量里的local1位置查找 如果找到就对set那列的值进行修改
        for(int i=0;i<tables.getvData().size();i++) {
            System.out.println(getValues().get(1));
            //此时已经找到了
            if (((Vector) tables.getvData().get(i)).get(local1).equals(getValues().get(1))){
                ((Vector) tables.getvData().get(i)).set(local, getValues().get(0));
                System.out.println("数据修改成功！");
                tables.WriteToFile();
                System.exit(0);
            }
        }
        //说明没找到
        System.out.println("表内没有"+getscolname().get(1)+"="+getValues().get(1)+"的值，删除失败");
        System.exit(0);
    }
    //增删改查-查
    public void SelectRow(){
        //首先判断长度 如果为零的话直接返回
        if(tables.getvData().size()==0){
            System.out.println("表内数据为空");
            System.exit(0);
        }
        //判断列名在不在
        ColNameIsExist();

        if(colname.get(0).equals("*")){
            //此时打印表的全部数据
            for(int i=0;i<tables.getvData().size();i++){
                //打印每行数据
                System.out.println(tables.getvData().elementAt(i));
            }
        }else { //此时的查询针对选取部分列名
            //思想就是根据列名在列中的位置，每行中取出对应的值并且存入vec中
            Vector<Integer> vec = new Vector<Integer>();
            for(String s:colname){
                for(int i=0;i<tables.getvColumn().size();i++){
                    ColumnDef col=(ColumnDef)(tables.getvColumn().get(i));
                    if(col.getColName().equals(s)){
                        vec.addElement(i);
                    }
                }
            }
            //先把列名打印一遍
            for(int i:vec){
                ColumnDef col=(ColumnDef)(tables.getvColumn().get(i));
                System.out.print(col.getColName()+"\t");
            }
            System.out.println("\n");
            //此时vec中存储的已经是对应列的位置了，从data中取出每行的向量，并且打印出对应位置即可
            for(int i=0;i<tables.getvData().size();i++){
                //那些向量是以vector保存的，于是取出它们
                Vector temp = (Vector)tables.getvData().get(i);
                //打印出对应位置
                for(int j:vec){
                    System.out.print(temp.get(j));
                    System.out.print('\t');
                }
                System.out.print('\n');
            }
        }
    }




    //插入操作时插入的数据
    public Vector InsertValue(){
        //判断长度是否和列的长度一样 不一样则返回错误并且退出程序
        if(Values.size()!=tables.getvColumn().size()){
            System.out.println("输入长度与列长度不符合，请重新输入");
            System.exit(0);
        }
        return Values;
    }


    //对列名进行判断是否存在列中
    public void ColNameIsExist(){
        if(colname.get(0).equals("*")) return;
        //对列名进行判断，如果表中没有该列名，则输出提示并且退出整个程序
        //这个Vector存储表里的整个列名
        Vector<String> TablecolName = new Vector<String>();
        for(int i=0;i<tables.getvColumn().size();i++){
            ColumnDef col=(ColumnDef)(tables.getvColumn().get(i));
            TablecolName.addElement(col.getColName());
        }
        for(String s:colname){
            if(!TablecolName.contains(s)){
                System.out.println("该表中不存在"+s+"列！");
                System.exit(0);
            }
        }
        return;
    }
    public void ReadFromFile() throws Exception{
        String filename=this.getsTablename()+".db";
        String path = "D:\\OneDrive\\IdeaProjects\\YJRDB\\";
        ObjectInputStream oos=new ObjectInputStream(new FileInputStream(path+filename));
        tables= (TableContent)oos.readObject();

        oos.close();
    }
    public void ReadGlobalFromFile() throws Exception{
        String filename="globaltable.txt";
        String path = "D:\\OneDrive\\IdeaProjects\\YJRDB\\";
        ObjectInputStream oos=new ObjectInputStream(new FileInputStream(path+filename));

        this.global= (Vector)oos.readObject();

        oos.close();
    }
}
