package controller;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import dao.MyStocksDAO;
import dao.UserHistoryDAO;
import model.HistoryUser;
import model.StockApiBean;
import model.User;

@ManagedBean(name = "userHistory", eager=true)

public class UserHistory {
	private int hbalance;
	public int getHbalance() {
		UserHistoryDAO n = new UserHistoryDAO();
		hbalance = n.getbalancee(user.getUsername());
		return hbalance;
	}

	public void setHbalance(int hbalance) {
		this.hbalance = hbalance;
	}

	@ManagedProperty(value = "#{user}")
	private User user = new User();
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManagedProperty(value = "#{historyUser}")
	private HistoryUser historyUser = new HistoryUser();	
	public HistoryUser getHistoryUser() {
		return historyUser;
	}

	public void setHistoryUser(HistoryUser historyUser) {
		this.historyUser = historyUser;
	}

	public ArrayList showdetails(){
		UserHistoryDAO uh = new UserHistoryDAO();
		//System.out.println("in showdetials of userhistory:"  + user.getUsername());
		return uh.gettingdetails(user.getUsername());
	}
}
