package YJRdataBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class GlobalTableInfo implements java.io.Serializable {
    private static final long serialVersionUID=1L;
    public Vector<GlobalTableInfo> globalConStriant;
    class TableInfo implements java.io.Serializable{

    }

    public String ptablename; //主表名
    public String pcolumnname;  //主表字段名
    public String rtablename;  //引用表名
    public String rcolumnname;  //引用表字段名

    public void setPtablename(String str){ptablename=str;}
    public String getPtablename(){return ptablename;}
    public void setpcolumnname(String str){pcolumnname=str;}
    public String getpcolumnname(){return pcolumnname;}
    public void setrtablename(String str){rtablename=str;}
    public String getrtablename(){return rtablename;}
    public void setrcolumnname(String str){rcolumnname=str;}
    public String getrcolumnname(){return rcolumnname;}


    public GlobalTableInfo(){
        ptablename="";
        pcolumnname="";
        rtablename="";
        rcolumnname="";
        globalConStriant = new Vector<GlobalTableInfo>();
    }


    public boolean fileIsExist(String tname){
        String path="";
        path="D:\\OneDrive\\IdeaProjects\\YJRDB\\"+tname+".db";
        File f=new File(path);
        if(!f.exists())
            return false;
        else
            return true;
    }

    public void WriteToFile() throws Exception{

        String filename="globaltable.txt";
        File f = new File(filename);
        if(!f.exists()){
            globalConStriant.addElement(this);
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(globalConStriant);
            oos.close();
        }else {
            ObjectInputStream ois =new ObjectInputStream(new FileInputStream(filename));
            globalConStriant = (Vector)ois.readObject();
            globalConStriant.addElement(this);
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(globalConStriant);
            oos.close();
        }
    }



//    public GlobalTableInfo ReadFromFile(String tablename) throws IOException,ClassNotFoundException {
//        ObjectInputStream ois =new ObjectInputStream(new FileInputStream(tablename));
//        GlobalTableInfo gti=(GlobalTableInfo)ois.readObject();
//        ois.close();
//        return gti;
//    }

    //外键参照表，tName为本表名，c1为本表所设置的键，tn为参考表名，c2为参考表所参考的键
    public void addReference(String tName, String c1, String tn, String c2) {
        ptablename = tName;
        pcolumnname = c1;
        rtablename = tn;
        rcolumnname = c2;
    }
}
