package YJRdataBase;
import staticContent.DataType;
public class ColumnDef implements java.io.Serializable {
	private String colName;			//����
	private int colType;			//��������
	private boolean isUnique;		//�Ƿ�Ψһ
	private boolean isNotNull;		//�Ƿ�Ϊ��
	private boolean isPrimaryKey;	//�Ƿ�Ϊ�м�����
	private Expression defaultExp;	//ȱʡֵ
	//���캯��
	public ColumnDef()
	{
		colName="";
		colType=DataType.NULL;
		isUnique=false;
		isNotNull=false;
		isPrimaryKey=false;
		defaultExp=new Expression();
	}
	
	//_start Get&Set
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public int getColType() {
		return colType;
	}
	public void setColType(int colType) {
		this.colType = colType;
	}
	public boolean isUnique() {
		return isUnique;
	}
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	public boolean isNotNull() {
		return isNotNull;
	}
	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public Expression getDefaultExp() {
		return defaultExp;
	}
	public void setDefaultExp(Expression defaultExp) {
		this.defaultExp = defaultExp;
	}
	
	//_end
	public String getDescribe()
	{
		String result="";
		result+="����:"+this.getColName()+"\n";
		result+="���ͣ�"+DataType.getDescribe(this.getColType())+"\n";
		result+="�Ƿ�Ψһ��";
		if(this.isUnique)
			result+="��";
		else
			result+="��";
		result+="\n";
		result+="����Ϊ�գ�";
		if(this.isNotNull)
			result+="��";
		else
			result+="��";
		result+="\n";
		result+="�Ƿ�������";
		if(this.isPrimaryKey)
			result+="��";
		else
			result+="��";
		result+="\n";
		if(defaultExp.getElements().size()>0)
			result+="ȱʡֵ���ʽΪ��"+defaultExp.getString();
		else
			result+="��ȱʡֵ���ʽ\n";
		return result;
	}
}
