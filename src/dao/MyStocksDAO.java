package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.Sqlconnection;
import model.StockApiBean;
import model.User;

public class MyStocksDAO {
	ArrayList stocklist;
	public ArrayList gettingstocks(String nameofclient) {
		
		StockApiBean stockapi  = null;  
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		//System.out.println("admin dao ");
		try{
//			System.out.println("in try clientlist:" +nameofclient);
			stocklist = new ArrayList();
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from purchase where username = '"+nameofclient+"'");
//			System.out.println("gettingclients after query");
			while(rs.next())
			{
				stockapi = new StockApiBean();
				stockapi.setTransactionid(rs.getInt("tid"));
				//System.out.println("in getting stocksMystocksdao" +rs.getInt("thid"));
				stockapi.setQty(rs.getInt("qty"));
				stockapi.setSymbol(rs.getString("stock_symbol"));
				stockapi.setPrice(rs.getDouble("price"));
				stockapi.setAmt(rs.getDouble("amt"));
				//user.setFees(rs.getInt("fees"));
				stocklist.add(stockapi);
			}
			stmt.close();
			con.close(connection);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return stocklist;

	}
}
