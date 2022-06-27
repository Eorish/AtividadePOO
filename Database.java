import java.sql.*;
import java.sql.DriverManager;

public class Database {


    private static Connection connect(){
        String url = "jdbc:sqlite:C:\\Users\\55669\\Desktop\\SQLFactory\\db\\banco.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static boolean inserir(SQLClass registro){
        return executarSQL( registro.insertSQL() );
    }
    public static boolean atualizar(SQLClass registro){
        return executarSQL( registro.updateSQL() );
    }
    public static boolean deletar(SQLClass registro){
        return executarSQL( registro.deleteSQL() );
    }
    
    public static boolean executarSQL( String sql ){
        boolean ok = false;

        Connection currentConn = connect();
        try {
            Statement statement = currentConn.createStatement();
            statement.execute(sql);
            currentConn.close();
            ok = true;
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;            
        }
        
        return ok; 
    }
    public static boolean executarUSER ( String tableName, String param ){
        boolean ok = false;
        Connection currentConn = connect();
        try {
            Statement selectStmt = currentConn.createStatement();
            String retorno =  "select * from "+ tableName +" where id= "+param;
            ResultSet rs = selectStmt.executeQuery(retorno);
            ResultSetMetaData metadata = rs.getMetaData(); 
            int columnCount = metadata.getColumnCount();
            for (int i=1; i<=columnCount; i++) 
            {
                String columnName = metadata.getColumnName(i);
                System.out.print( "\n" + columnName + "\n" );
                System.out.println(rs.getString(columnName));
            }
            currentConn.close();
            ok = true;
        }catch (Exception e) {
            e.printStackTrace();
            ok = false;
        }
        return ok;
    }

    
}
