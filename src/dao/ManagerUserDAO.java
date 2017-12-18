package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.Sqlconnection;
import model.User;

public class ManagerUserDAO {
	ArrayList clientlist;
	public ArrayList gettingclients(String nameofmanager) {
		
		User user = null;  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		//System.out.println("admin dao ");
		try{
//			System.out.println("in try clientlist:" +nameofmanager);
			clientlist = new ArrayList();
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from user where managerid = '"+nameofmanager+"'");
			//System.out.println("admindao after query");
			while(rs.next())
			{
				user = new User();
				user.setSelluseramt(rs.getInt("sellamt"));
				user.setRequestedamt(rs.getInt("requbala"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				//user.setFees(rs.getInt("fees"));
				clientlist.add(user);
			}
			stmt.close();
			con.close(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return clientlist;

	}

}
