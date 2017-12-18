package dao;

import java.sql.*;
import java.util.Map;
import connection.*;
import model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean(name = "editprofile")
@RequestScoped

public class EditProfileDAO {
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  

	public String editgetdata(String usernameedit){  
		User user = null;  
		Sqlconnection con = new Sqlconnection();
		//PreparedStatement ps = null;
		Statement stmt = null;
		
		//System.out.println(id);  
		try{  
//			System.out.println("in try" + usernameedit);
			Connection connection = con.getConnection();
	//		System.out.println("after con");
			stmt=connection.createStatement();
			String sql="select * from user where username ='" +usernameedit+"'";
//			System.out.println(sql);
			ResultSet rs=stmt.executeQuery(sql);  
			rs.next();  
			user = new User();  
			user.setEmail(rs.getString("email")); 
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setUsername(rs.getString("username"));
			user.setFees(rs.getInt("fees"));
//			System.out.println(rs.getString("password"));  
			sessionMap.put("editUser", user);  
//			System.out.println("ess");
			stmt.close();
			con.close(connection);
			} catch (Exception e) {
				//System.out.println("in exe");
	            System.out.println(e);
	        } 
			//System.out.println("home");
			return "EditProfile"; 	  
		}
		
	public String editManager(User u){
		Sqlconnection con = new Sqlconnection();
		//PreparedStatement ps = null;
		PreparedStatement stmt = null;
			
		
			try{
//				System.out.println("in editmanager");  
				Connection connection = con.getConnection();
//			System.out.println("before que");
			stmt=connection.prepareStatement("update user set name=?,email=?,password=?,fees=? where username=?");
//			System.out.println("after query");
			stmt.setString(1,u.getName());
			stmt.setString(2,u.getEmail()); 
			stmt.setString(5,u.getUsername());
			stmt.setString(3,u.getPassword());
			stmt.setInt(4,u.getFees());
			stmt.executeUpdate();
			stmt.close();
			con.close(connection);
//			System.out.println("afterconn in editmanager : " + u.getName());
			}catch(Exception e){
				e.printStackTrace();
			}
			FacesContext.getCurrentInstance().getExternalContext()
            .invalidateSession();
    
			return "/Login.xhtml?faces-redirect=true"; 	  
		}

	}

