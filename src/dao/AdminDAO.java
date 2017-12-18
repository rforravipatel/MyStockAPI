package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.ManagedBean;

import connection.Sqlconnection;
import model.User;


public class AdminDAO {
	
	ArrayList managerlist;
	
	public ArrayList givelist() {
		User user = null;  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		//System.out.println("admin dao ");
		try{
			//System.out.println("in try admin");
			managerlist = new ArrayList();
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from user where active = 'no'");
			//System.out.println("admindao after query");
			while(rs.next())
			{
				user = new User();
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setFees(rs.getInt("fees"));
				managerlist.add(user);
			}
			stmt.close();
			con.close(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return managerlist;
	}

	public void approve(String namemanager) {
		//User user = null;  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		
		try{
			//System.out.println("in try admin approve");
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			stmt.executeUpdate("update user set active = 'yes' where username = '"+namemanager+"'");
			//System.out.println("done yes admin");
			stmt.close();
			con.close(connection);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void decline(String namemanager) {
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		
		try{
			//System.out.println("in try admin decline");
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			stmt.executeUpdate("delete from user where username = '"+namemanager+"'");
			//System.out.println("done decline admin");
			stmt.close();
			con.close(connection);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
