package connection;
import java.sql.Connection;
import java.sql.Statement;

public class Sqlconnection {
	static int i=0;
	Connection con = null;
	//Statement stmt = null;
	
	public Connection getConnection(){
		//PreparedStatement ps = null;
		//System.out.println("in sqlconnection");
		if(i>0) return null;
		  
		try{  
			//System.out.println("in try");
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(System.getenv("ICSI518_SERVER"));
			ds.setPortNumber(Integer.parseInt(System.getenv("ICSI518_PORT")));
			ds.setDatabaseName(System.getenv("ICSI518_DB").toString());
			ds.setUser(System.getenv("ICSI518_USER").toString());
			ds.setPassword(System.getenv("ICSI518_PASSWORD").toString());
			con = ds.getConnection();
			i++;
			}catch (Exception e) {
	            System.out.println(e);
	        } 
		
		return con;
}	
	public void close(Connection connection)
	{
		//System.out.println("close method");
		i=0;
        try {
        	connection.close();
       //     stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}