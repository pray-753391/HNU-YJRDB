package YJRdataBase;

/*
 * 变量
 */
public class Variable implements java.io.Serializable{
    private String name; 	//变量名

    public Variable(String name)
    {
        this.name=name;
    }
    public String GetName()
    {
        return name;
    }

}
