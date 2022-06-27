import java.lang.reflect.*;

public abstract class SQLClass {
    private String tableName;
    private String listFields;
    private String listValues;
    private String listFieldsValues;    

    public void setTableName(String tableName){
        this.tableName = tableName;
    }
    
    public String selectSQL(String param){
        return "select * from "+this.tableName+" where "+param;
    }

    private String getValueKey(){
        String value = "";

        try {
            String nomeKey = this.getKey(); 
            Field field = this.getClass().getDeclaredField(nomeKey);    
           value = value + field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }            

        return value;
    }

    private String getKey(){        
        Field[] fields = this.getClass().getDeclaredFields();            

        String keyName = "";

        for (Field field : fields) {
            if (field.isAnnotationPresent(Key.class)) {
                keyName = field.getName();
            }
        }

        return keyName;
    }    

    private String getValueField(Field field) throws Exception{
        Object valueObject = field.get(this);  
        String value = "";
        
        if (field.getType() == String.class) {
            value = "'"+valueObject+"'";
        } else {
            value = value + valueObject;
        }

        return value;
    }

    private void listAllFields(){        
        Field[] fields = this.getClass().getDeclaredFields();
        
        String fieldList = "";
        String valueList = "";

        for (Field field : fields) {
            fieldList = fieldList + ", " + field.getName();
           
            try {
                valueList = valueList + ", " + getValueField(field);    
            } catch (Exception e) {
                e.printStackTrace();
            }            
        }
        this.listFields = fieldList.substring(1);
        this.listValues = valueList.substring(1);
    }

    private void listAllFieldsValues(){
        Field[] fields = this.getClass().getDeclaredFields();
        
        String fieldsValuesList = "";

        for (Field field : fields) {                   
            try {
                fieldsValuesList = 
                    fieldsValuesList + 
                    ", " +field.getName() + " = " +  getValueField(field);
            } catch (Exception e) {
                e.printStackTrace();
            }            
        }
        this.listFieldsValues = fieldsValuesList.substring(1);        
    }

    public String insertSQL(){
        listAllFields();
        String sql;
        
        sql = "insert into "+this.tableName;
        sql = sql + " ("+ this.listFields + ")";
        sql = sql + " values (" + this.listValues + ")";

        return sql;
    }

    public String deleteSQL(){
        return "delete from "+this.tableName +" where "+this.getKey() + " = " + this.getValueKey();
    }

    public String updateSQL(){
        listAllFieldsValues();
        String sql;

        sql = "update "+this.tableName + " set";
        sql = sql + this.listFieldsValues;
        sql = sql + " where "+this.getKey() + " = " + this.getValueKey();
        
        return sql;
    }
}