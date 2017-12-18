package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.Sqlconnection;
import controller.UserHistory;
import model.HistoryUser;
import model.StockApiBean;

public class UserHistoryDAO {
	ArrayList historydetails;
	public ArrayList gettingdetails(String nameofclient) {
		
		String ro = null;
		try{
			Sqlconnection con1 = new Sqlconnection();
			Statement stmt = null;
			Connection connection = con1.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from user where username = '"+nameofclient+"'");
//			System.out.println("after query:" + rs.getString("role"));
			rs.next();
			ro = rs.getString("role");
			stmt.close();
			con1.close(connection);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		HistoryUser historyuser = null;  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		System.out.println("userhistory dao : " +nameofclient) ;
		if(ro.equals("client")){
		try{
			//System.out.println("userhistory getting detials:" +nameofclient);
			historydetails = new ArrayList();
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select stock_symbol,qty,price,amt from history where username = '"+nameofclient+"'");
			//System.out.println("getting history after query");
			while(rs.next())
			{
				historyuser = new HistoryUser();
				
				//stockapi.setTransactionid(rs.getInt("tid"));
				//System.out.println("in getting stocksMystocksdao" +rs.getInt("tid"));
				historyuser.setHqty(rs.getInt("qty"));
				historyuser.setHsymbol(rs.getString("stock_symbol"));
				historyuser.setHprice(rs.getDouble("price"));
				historyuser.setHamt(rs.getDouble("amt"));
				//System.out.println("userhistory getiing amt" + rs.getInt("qty"));
				//user.setFees(rs.getInt("fees"));
				historydetails.add(historyuser);
			}
			stmt.close();
			con.close(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return historydetails;
		}
		else{
			
			try{
				//System.out.println("userhistory getting detials:" +nameofclient);
				historydetails = new ArrayList();
				Connection connection = con.getConnection();
				stmt=connection.createStatement();
				ResultSet rs=stmt.executeQuery("select stock_symbol,qty,price,amt from history where managersell = '"+nameofclient+"'");
				//System.out.println("getting history after query");
				while(rs.next())
				{
					historyuser = new HistoryUser();
					
					//stockapi.setTransactionid(rs.getInt("tid"));
					//System.out.println("in getting stocksMystocksdao" +rs.getInt("tid"));
					historyuser.setHqty(rs.getInt("qty"));
					historyuser.setHsymbol(rs.getString("stock_symbol"));
					historyuser.setHprice(rs.getDouble("price"));
					historyuser.setHamt(rs.getDouble("amt"));
					//System.out.println("userhistory getiing amt" + rs.getInt("qty"));
					//user.setFees(rs.getInt("fees"));
					historydetails.add(historyuser);
				}
				stmt.close();
				con.close(connection);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return historydetails;
			}
		}
	
	public int getbalancee(String nameofclient){
		  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		int userbalance = 0;
		try{
			//System.out.println("userhistory getting detials:" +nameofclient);
		
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select balance from user where username = '"+nameofclient+"'");
			//System.out.println("getting history after query");
			rs.next();
			
				
				userbalance = rs.getInt("balance");
			
			stmt.close();
			con.close(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return userbalance;
	}

}
