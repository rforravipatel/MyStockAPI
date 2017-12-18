package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.RequestScoped;

import connection.Sqlconnection;
import controller.RegisterCon;


public class RegisterDAO {
	public static boolean insert(String name, String username, String email, String password, int fees) {
		Sqlconnection con = new Sqlconnection();
		int i = 0;
		PreparedStatement ps = null;
//		System.out.println(fees);
		if(fees == 0){
		try {
			Connection connect = con.getConnection();
			
			if (connect != null) {
                String sql = "INSERT INTO user(name, email, username, password, role, balance) VALUES(?,?,?,?,?,?)";
                ps = connect.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, username);
                ps.setString(4, password);
                ps.setString(5, "client");
                ps.setInt(6, 100000);
                i = ps.executeUpdate();
               // System.out.println("Data Added Successfully");
                i++;
                ps.close();
                con.close(connect);
            }
		} catch (Exception e) {
            System.out.println(e);
        } 
		}else{
			try {
				Connection connect = con.getConnection();
				
				if (connect != null) {
	                String sql = "INSERT INTO user(name, email, username, password, role, fees, active) VALUES(?,?,?,?,?,?,?)";
	                ps = connect.prepareStatement(sql);
	                ps.setString(1, name);
	                ps.setString(2, email);
	                ps.setString(3, username);
	                ps.setString(4, password);
	                ps.setString(5, "manager");
	                ps.setInt(6, fees);
	                ps.setString(7, "no");
	                i = ps.executeUpdate();
	             //   System.out.println("Data Added Successfully");
	                i++;
	                ps.close();
	                con.close(connect);
	            }
			} catch (Exception e) {
	            System.out.println(e);
	        } 
		}
    
    if (i > 0) {
        return true;
    } else
        return false;
	}

	public static String validate(String username, String password, String roles) {
		Sqlconnection con = new Sqlconnection();
		PreparedStatement psstmt = null;
		RegisterCon c= new RegisterCon();
		String active;
		try {
			//System.out.println("in login try");
			Connection connection = con.getConnection();
//			System.out.println("in login after connection");
			psstmt = connection.prepareStatement("Select role,active from user where username = ? and password = ?");
//			System.out.println("in login after query");
			psstmt.setString(1, username);
			psstmt.setString(2, password);
			//psstmt.setString(3, roles);
			//System.out.println("Detais:" + username + roles);
			ResultSet rs = psstmt.executeQuery();
			
			if (rs.next()) {
				//System.out.println("in next");
				roles = (rs.getString("role"));
				if(roles.equals("manager")){
					active = (rs.getString("active"));
					if(active.equals("no")){
						psstmt.close();
						con.close(connection);
						return "fail";
					}
				}
				//c.setRoles(roles);
				//System.out.println("Detais:" + username + roles );
				psstmt.close();
				con.close(connection);
			//	System.out.println("after connection close");
			}else{
				//System.out.println("fail in data");
				psstmt.close();
				con.close(connection);
				return "fail";
			}

			
		} catch (SQLException ex) {
			ex.printStackTrace();
			
		}
		return roles;
		
	}

}
