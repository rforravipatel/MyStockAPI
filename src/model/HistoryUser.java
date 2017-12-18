package model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "historyUser", eager=true)
@SessionScoped

public class HistoryUser {
	private String hsymbol;
	private int hqty;
	private double hamt;
	private double hprice;
	private String hmanagername;
	
	public String getHsymbol() {
		//System.out.println("setsymbol:" + hsymbol);
		return hsymbol;
	}
	public void setHsymbol(String hsymbol) {
		//System.out.println("gethsymbol:" + hsymbol);
		this.hsymbol = hsymbol;
	}
	public int getHqty() {
		//System.out.println("getHqty :" + hqty);
		return hqty;
	}
	public void setHqty(int hqty) {
		//System.out.println("setHqty : " +hqty);
		this.hqty = hqty;
	}
	public double getHamt() {
		return hamt;
	}
	public void setHamt(double hamt) {
		this.hamt = hamt;
	}
	public double getHprice() {
		return hprice;
	}
	public void setHprice(double hprice) {
		this.hprice = hprice;
	}
	public String getHmanagername() {
		return hmanagername;
	}
	public void setHmanagername(String hmanagername) {
		this.hmanagername = hmanagername;
	}
	
}
