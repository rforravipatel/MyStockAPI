package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import connection.Sqlconnection;
import model.StockApiBean;
import model.User;

public class MyWatchlistDAO {

	

	public void stockadded(String selectedSymbol, String usernam) {
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		try{
//			System.out.println("in mywatchlist stockadded try");
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			stmt.executeUpdate("insert into watchlist (wid, username, stockname) values (null,'"+usernam+"', '"+selectedSymbol+"' )");
			stmt.close();
			con.close(connection);
//			System.out.println("in stockadded mywatchlist");
			
		}catch(Exception e){
			e.printStackTrace();
		}	}

	ArrayList watchliststocks;
	public ArrayList gettingwatchlist(String usernamestocks) {
		
		StockApiBean getstocksuser  = null;  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		//System.out.println("admin dao ");
		try{
//			System.out.println(" getting watchlist for user:" +usernamestocks);
			watchliststocks = new ArrayList<String>();
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select stockname from watchlist where username = '"+usernamestocks+"'");
			//System.out.println("admindao after query");
			while(rs.next())
			{
				getstocksuser = new StockApiBean();
				getstocksuser.setSymbol(rs.getString("stockname"));
//				for (int i = 0;i<=rs.next();i++){
//					
//				}
				//user.setFees(rs.getInt("fees"));
				watchliststocks.add(getstocksuser);
			}
			stmt.close();
			con.close(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return watchliststocks;

	}

}
