package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import connection.Sqlconnection;
import model.StockApiBean;

public class PurchaseDAO {
   
	
	public String purchase(String symbol, double price, int qty, double amt, String usernamepur) {
    	Sqlconnection con = new Sqlconnection();
		
		int s;
    	try {
            //System.out.println("symbol: " + symbol + ", price: " + price + "\n");
            //System.out.println("qty: " + qty + ", amt: " + amt + "\n");
    		Connection forbalance = con.getConnection();
    		Statement sbal = null;
    		sbal = forbalance.createStatement();
    		ResultSet rs=sbal.executeQuery("select balance from user where username ='" +usernamepur+"'");  
            rs.next();
            StockApiBean stockapibala = new StockApiBean();
    		stockapibala.setBalance(rs.getInt("balance"));
    		s = rs.getInt("balance");
            sbal.close();
            con.close(forbalance);
    		//System.out.println("in purachase:" +s);
    		if(amt > s){
    			//System.out.println("in fail purchase");
    			return "fails";
    		}else{
    				try{
    			//		System.out.println("else" + s);
    					Connection conn = con.getConnection();
    		    		Statement statement = null;
    		    		statement = conn.createStatement();
    		    		int a = (int) (s - amt);
    		    		statement.executeUpdate("update user set balance = '"+a+"' where username ='" +usernamepur+"'");
    		    		statement.close();
    		    		con.close(conn);
    				}catch(SQLException e) {
    		            e.printStackTrace();
    		        }
    		}
    	}catch(SQLException e) {
            e.printStackTrace();
        }
    	
    	
    	try{
    		Connection conn = con.getConnection();
    		Statement statement = null;
       		statement = conn.createStatement();
//            System.out.println("in createdb: " +usernamepur);
//            System.out.println("symbol:" + symbol);
//            System.out.println("price:" + price);
//            System.out.println("qty:" + qty);
//            System.out.println("amt:" + amt);
            statement.executeUpdate("INSERT INTO `purchase` (`tid`, `username`, `stock_symbol`, `qty`, `price`, `amt`, activity)"
                    + "VALUES (NULL,'" + usernamepur + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"', 'buy')");
            
            statement.close();
            con.close(conn);
            //System.out.println("purchase query2");
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	try{
    		Connection conn2 = con.getConnection();
            Statement statement2 = null;
            statement2 = conn2.createStatement();
            statement2.executeUpdate("INSERT INTO `history` (`thid`, `username`, `stock_symbol`, `qty`, `price`, `amt`, buysell)"
                    + "VALUES (NULL,'" + usernamepur + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"', 'buy')");
            
            statement2.close();
            con.close(conn2);
            
    	}catch (SQLException e) {
            e.printStackTrace();
        }
        return "success";
    }

	public String purchase(String nameofclient, String symbol, double price, int qty, double amt, String username) {
		
		
	    	Sqlconnection con = new Sqlconnection();
	    	
	    	int feee = 0;
	    	int managerbalance = 0;
	    	try {
//	            System.out.println("symbol: " + symbol + ", price: " + price + ", nameofclient: " +nameofclient+"\n");
//	            System.out.println("qty: " + qty + ", amt: " + amt + "\n");
	    		Connection conn1 = con.getConnection();
	    		Statement sbal1 = null;
	    		sbal1 = conn1.createStatement();
	    		ResultSet rsf=sbal1.executeQuery("select fees from user where username ='" +username+"'");  
	            rsf.next();
	            
	    		feee = rsf.getInt("fees");
	    		
	            sbal1.close();
	            con.close(conn1);
	    	}catch(SQLException e) {
	            e.printStackTrace();
	        }
	    	
	    	
			int reqamt,userbal;
	    	try {
	            System.out.println("symbol: " + symbol + ", price: " + price + ", nameofclient: " +nameofclient+"\n");
	            System.out.println("qty: " + qty + ", amt: " + amt + "\n");
	    		Connection forbalance = con.getConnection();
	    		Statement sbal = null;
	    		sbal = forbalance.createStatement();
	    		ResultSet rs=sbal.executeQuery("select requbala,balance from user where username ='" +nameofclient+"'");  
	            rs.next();
	            
	    		userbal = rs.getInt("balance");
	    		reqamt = rs.getInt("requbala");
	            sbal.close();
	            con.close(forbalance);
	    		System.out.println("in purachase:" +userbal);
	    		if(amt > reqamt){
	    			System.out.println("in fail purchase");
	    			return "fails";
	    		}else{
	    				try{
	    					System.out.println("else" + userbal);
	    					Connection conn = con.getConnection();
	    		    		Statement statement = null;
	    		    		statement = conn.createStatement();
	    		    		int re = (int) (reqamt - amt - feee);
	    		    		int ba = (int) (userbal - amt -feee);
	    		    		String sql = "update user set balance ='"+ba+"',requbala='"+re+"' where username ='"+nameofclient+"'";
	    		    		System.out.println("sql query:" +sql);
	    		    		statement.executeUpdate(sql);
	    		    		System.out.println("after update query");
	    		    		statement.close();
	    		    		con.close(conn);
	    				}catch(SQLException e) {
	    		            e.printStackTrace();
	    		        }
	    		}
	    	}catch(SQLException e) {
	            e.printStackTrace();
	        }
	    	try {
//	            System.out.println("symbol: " + symbol + ", price: " + price + ", nameofclient: " +nameofclient+"\n");
//	            System.out.println("qty: " + qty + ", amt: " + amt + "\n");
	    		Connection conn1 = con.getConnection();
	    		Statement sbal1 = null;
	    		sbal1 = conn1.createStatement();
	    		
	    		ResultSet rsf=sbal1.executeQuery("select balance from user where username ='" +username+"'");  
	            rsf.next();
	            
	            managerbalance = rsf.getInt("balance");
	    		
	            sbal1.close();
	            con.close(conn1);
	    	}catch(SQLException e) {
	            e.printStackTrace();
	        }
	            try{
	            Connection conn5 = con.getConnection();
	    		Statement sbal5 = null;
	    		sbal5 = conn5.createStatement();
	    		managerbalance = managerbalance + feee;
	    		sbal5.executeUpdate("update user set balance = '"+managerbalance+"' where username ='" +username+"'");  
	            
	            sbal5.close();
	            con.close(conn5);
	            
	    	}catch(SQLException e) {
	            e.printStackTrace();
	        }
	    	
	    	try{
	    		Connection conn = con.getConnection();
	    		Statement statement = null;
	       		statement = conn.createStatement();
	            System.out.println("in createdb: " +username);
	            System.out.println("symbol:" + symbol);
	            System.out.println("price:" + price);
	            System.out.println("qty:" + qty);
	            System.out.println("amt:" + amt);
	            statement.executeUpdate("INSERT INTO `purchase` (`tid`, `username`, `stock_symbol`, `qty`, `price`, `amt`, managername, activity)"
	                    + "VALUES (NULL,'" + nameofclient + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"', '"+username+"', 'buy')");
	            
	            statement.close();
	            con.close(conn);
	            //System.out.println("purchase query2");
	            
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    	
	    	try{
	    		Connection conn2 = con.getConnection();
	            Statement statement2 = null;
	            statement2 = conn2.createStatement();
	            statement2.executeUpdate("INSERT INTO `history` (`thid`, `username`, `stock_symbol`, `qty`, `price`, `amt`, managersell, buysell)"
	                    + "VALUES (NULL,'" + nameofclient + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"','"+username +"' ,'buy')");
	            
	            statement2.close();
	            con.close(conn2);
	            
	    	}catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return "success";
	    
	}
	
	
}
