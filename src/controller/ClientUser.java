package controller;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import dao.ClientUserDAO;
import model.User;

@ManagedBean(name = "clientuser", eager=true)
public class ClientUser {
	private int reqamt;
	private int sellamt;
	public int getSellamt() {
		return sellamt;
	}

	public void setSellamt(int sellamt) {
		this.sellamt = sellamt;
	}

	public int getReqamt() {
		return reqamt;
	}

	public void setReqamt(int reqamt) {
		this.reqamt = reqamt;
	}

	@ManagedProperty(value = "#{user}")
	private User user = new User();
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList managerlisting(){
		ClientUserDAO cliuser = new ClientUserDAO();
		return cliuser.givemanagers();
	}
	
	public String selectmanager(String managersele){
		//System.out.println("selectmanager" + managersele + user.getUsername());
		ClientUserDAO managerclient = new ClientUserDAO();
		managerclient.getmanager(managersele, user.getUsername());
		
		return "UserDashboard";
	}
	
	public String sendmoney(int money){
		ClientUserDAO cud = new ClientUserDAO();
		//System.out.println("in sendmoney lsaldsd" + user.getUsername());
		cud.addmoney(user.getUsername(),money);
		return "UserDashboard";
	}
	
	public String sendmoneyforsell(int money){
		ClientUserDAO cud = new ClientUserDAO();
		//System.out.println("in sendmoney lsaldsd" + user.getUsername());
		cud.addmoneysell(user.getUsername(),money);
		return "UserDashboard";
	}
}
