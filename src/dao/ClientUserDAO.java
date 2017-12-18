package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.Sqlconnection;
import model.User;

public class ClientUserDAO {
	ArrayList managerlist;
	
	public ArrayList givemanagers() {
		User user = null;  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		//System.out.println("admin dao ");
		try{
			//System.out.println("in try admin");
			managerlist = new ArrayList();
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from user where role = 'manager' and active = 'yes'");
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

	public void getmanager(String managersele, String clientuser) {
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		
		try{
//			System.out.println("in try  getmaanger" + managersele + clientuser);
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			stmt.executeUpdate("update user set managerid = '"+managersele+"' where username = '"+clientuser+"'");
	//		System.out.println("getmanager method");
			stmt.close();
			con.close(connection);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void addmoney(String usern, int ammount){
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		
		try{
//			System.out.println("in try  getmaanger" + managersele + clientuser);
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			stmt.executeUpdate("update user set requbala = '"+ammount+"' where username = '"+usern+"'");
	//		System.out.println("getmanager method");
			stmt.close();
			con.close(connection);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void addmoneysell(String username, int money) {
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		
		try{
//			System.out.println("in try  getmaanger" + managersele + clientuser);
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			stmt.executeUpdate("update user set sellamt = '"+money+"' where username = '"+username+"'");
	//		System.out.println("getmanager method");
			stmt.close();
			con.close(connection);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	}



