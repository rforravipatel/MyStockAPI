package controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import connection.Util;
import dao.AdminDAO;
import dao.EditProfileDAO;
import dao.RegisterDAO;
import dao.UserHistoryDAO;
import model.User;

@ManagedBean(name = "registercon", eager=true)

public class RegisterCon {
	@ManagedProperty(value = "#{user}")
	private User user = new User();
	 public String roles;
	 
	//	private String name = user.getName();
//	private String username = user.getUsername();
//	private String email = user.getEmail();
//	private String password = user.getPassword();
//	private int fees = user.getFees();
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String add() {
    	boolean correct = RegisterDAO.insert(user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getFees());
    	if(correct){
    		String message = "Registration done successfully !!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
		    FacesContext.getCurrentInstance().getExternalContext()
            .invalidateSession();
    
    		return "Login";
    	}else{
    		return "unsuccess";
    	}
    }
	
	public String check1(){
	   
    	String valid = RegisterDAO.validate(user.getUsername(), user.getPassword(), roles);
    	roles=valid;
    	if(valid.equals("client")){
    		//System.out.println("in check1" + user.getUsername()+ valid);
    		//HttpSession s=Util.getSession();
			//s.setAttribute("username", firstName);
    		return "/UserDashboard.xhtml?faces-redirect=true";
    	}else if(valid.equals("manager")){
    		return "/ManagerDashboard.xhtml?faces-redirect=true";
    	}else if(valid.equals("admin")){
    		return "/AdminDashboard.xhtml?faces-redirect=true";
    	}else{
    		String message = "Invalid UserName or Password"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    		return "Login";
    	}
    	
    }
	

	public void logout() {
		
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext.getCurrentInstance()
                .getApplication().getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "/Login.xhtml?faces-redirect=true");
    }
	
	public String edituser(String nameofuser){
		//System.out.println("in editusermenthod" +nameofuser+"j");
		if(nameofuser.equals("")){
			return "Login";
		}else{
		EditProfileDAO change = new EditProfileDAO();
		change.editgetdata(nameofuser);
		//System.out.println("in editusermenthod end");
		
		return "EditProfile";
		}
	}
	
	//Admin module methods
	public ArrayList managerslist(){
		 AdminDAO managerlis = new AdminDAO();
		return managerlis.givelist();
	}
	
	public void approvemanager(String namemanager){
		AdminDAO appr = new AdminDAO();
		appr.approve(namemanager);
	}
	
	public void declinemanager(String namemanager){
		AdminDAO dec = new AdminDAO();
		dec.decline(namemanager);
	}
}
