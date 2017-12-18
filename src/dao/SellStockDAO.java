package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.Sqlconnection;
import controller.MystocksUserMan;
import model.StockApiBean;

public class SellStockDAO {

	public StockApiBean salestock(int id) {
		Sqlconnection con = new Sqlconnection();
		Statement stmt = null;
		StockApiBean stockApiBean = new StockApiBean();
		try{
//			System.out.println("in try salstock " + id);
			Connection connection = con.getConnection();
			stmt=connection.createStatement();
			ResultSet rs = stmt.executeQuery("select stock_symbol,qty from purchase where tid = '"+id+"'");
			while(rs.next()){
				stockApiBean.setSellsymbol(rs.getString("stock_symbol"));
				stockApiBean.setSellqty(rs.getInt("qty"));
				//System.out.println(rs.getString("stock_symbol"));
				//System.out.println(rs.getInt("qty"));
			}
			//System.out.println("salestock ssssssssssssssssssssssssssssssssss" + rs.getString("stock_symbol"));
			stmt.close();
			con.close(connection);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return stockApiBean;
		
	}
	
	public String salestock(int tranis, String symbol, double price, int qty, double amt, String usernamepur) {
    	Sqlconnection con = new Sqlconnection();
    	String b;		
    	int s;
    	System.out.println("salestock iiasdiasidia"  +tranis);
    	double balanceaccount = 0;
    	try {
            //System.out.println("symbol: " + symbol + ", price: " + price + "\n");
            //System.out.println("qty: " + qty + ", amt: " + amt + "\n");
    		Connection forbalance = con.getConnection();
    		Statement sbal = null;
    		sbal = forbalance.createStatement();
    		ResultSet rs=sbal.executeQuery("select qty,username from purchase where tid ='" +tranis+"'");  
            rs.next();
            b= rs.getString("username");
    		s = rs.getInt("qty");
            sbal.close();
            con.close(forbalance);
//    		System.out.println("in purachase:" +s);
    		if(qty > s){
//    			System.out.println("in fail purchase");
    			return "fails";
    		}else{
    				try{
//    					System.out.println("else" + s);
    					Connection conn = con.getConnection();
    		    		Statement statement = null;
    		    		statement = conn.createStatement();
    		    		int a =  (s - qty);
    		    		statement.executeUpdate("update purchase set qty = '"+a+"' where tid ='" +tranis+"'");
    		    		statement.close();
    		    		con.close(conn);
    				}catch(SQLException e) {
    		            e.printStackTrace();
    		        }
    				try {
//    		            System.out.println("symbol: " + symbol + ", price: " + price + "\n");
//    		            System.out.println("qty: " + qty + ", amt: " + amt + "\n");
    		    		Connection getbalance = con.getConnection();
    		    		Statement sbala = null;
    		    		sbala = getbalance.createStatement();
    		    		ResultSet rs1=sbala.executeQuery("select balance from user where username ='" +b+"'");  
    		            rs1.next();
    		            balanceaccount = rs1.getInt("balance");
    		            sbal.close();
    		            con.close(forbalance);
    				}catch (Exception e) {
						e.printStackTrace();
					}
    					
    					try{
    	    			//		System.out.println("else" + s);
    						balanceaccount = balanceaccount + amt;
    							Connection conn = con.getConnection();
    	    		    		Statement statement = null;
    	    		    		statement = conn.createStatement();
    	    		    		int a =  (s - qty);
    	    		    		statement.executeUpdate("update user set balance = '"+balanceaccount+"' where username ='" +b+"'");
    	    		    		statement.close();
    	    		    		con.close(conn);
    	    				}catch(SQLException e) {
    	    		            e.printStackTrace();
    	    		        }
    		}
    	}catch(SQLException e) {
            e.printStackTrace();
        }
    	
    	
//    	try{
//    		Connection conn = con.getConnection();
//    		Statement statement = null;
//       		statement = conn.createStatement();
//            System.out.println("in createdb: " +usernamepur);
//            System.out.println("symbol:" + symbol);
//            System.out.println("price:" + price);
//            System.out.println("qty:" + qty);
//            System.out.println("amt:" + amt);
//            statement.executeUpdate("INSERT INTO `purchase` (`tid`, `username`, `stock_symbol`, `qty`, `price`, `amt`, activity)"
//                    + "VALUES (NULL,'" + usernamepur + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"', 'buy')");
//            
//            statement.close();
//            con.close(conn);
//            //System.out.println("purchase query2");
//            
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    	
    	try{
    		Connection conn2 = con.getConnection();
            Statement statement2 = null;
            statement2 = conn2.createStatement();
            statement2.executeUpdate("INSERT INTO `history` (`thid`, `username`, `stock_symbol`, `qty`, `price`, `amt`, buysell)"
                    + "VALUES (NULL,'" + usernamepur + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"', 'sell')");
            
            statement2.close();
            con.close(conn2);
            
    	}catch (SQLException e) {
            e.printStackTrace();
        }
        return "success";
    }

	}

