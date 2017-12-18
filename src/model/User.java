package model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "user", eager=true)
@SessionScoped
public class User {
	private String name;
	private String username;
    private String email;
    private String password;
    private int fees;
    private int requestedamt;
    private String role;
    private int selluseramt;
	public int getSelluseramt() {
		return selluseramt;
	}
	public void setSelluseramt(int selluseramt) {
		this.selluseramt = selluseramt;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getRequestedamt() {
		return requestedamt;
	}
	public void setRequestedamt(int requestedamt) {
		this.requestedamt = requestedamt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	
}
