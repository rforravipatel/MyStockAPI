package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.omg.CORBA.portable.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import dao.ManagerUserDAO;
import dao.PurchaseDAO;
import model.StockApiBean;
import model.User;

@ManagedBean(name = "manageruser", eager=true)
public class ManagerUser {
	@ManagedProperty(value = "#{user}")
	private User user = new User();
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@ManagedProperty(value = "#{stockApiBean}")
	private StockApiBean stockApiBean = new StockApiBean();
	public StockApiBean getStockApiBean() {
		return stockApiBean;
	}

	public void setStockApiBean(StockApiBean stockApiBean) {
		this.stockApiBean = stockApiBean;
	}
	

	private String clientname;
	private String mpursymbol;
	private double mprice;
	private int mqty;
	private double mamt;
	private String minterval;
	private static String selpursymbol;
	private static String selprice;
	public String getSelprice() {
//		System.out.println("getsellprice" + selprice);
		return selprice;
	}

	public void setSelprice(String string) {
//		System.out.println("getsellprice" + string);
		this.selprice = string;
	}

	public String getSelpursymbol() {
//		System.out.println("getelpursymbol:"  + selpursymbol);
		return selpursymbol;
	}

	public void setSelpursymbol(String selpursymbol) {
//		System.out.println("setelpursymbol:"  + selpursymbol);
		this.selpursymbol = selpursymbol;
	}

	
	
	public String getMinterval() {
		return minterval;
	}

	public void setMinterval(String minterval) {
		this.minterval = minterval;
	}
	private String table1Markup;
    public String getTable1Markup() {
		return table1Markup;
	}

	public void setTable1Markup(String table1Markup) {
		this.table1Markup = table1Markup;
	}

	public String getTable2Markup() {
		return table2Markup;
	}

	public void setTable2Markup(String table2Markup) {
		this.table2Markup = table2Markup;
	}

	private String table2Markup;
	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getMpursymbol() {
		return mpursymbol;
	}

	public void setMpursymbol(String mpursymbol) {
		
		this.mpursymbol = mpursymbol;
	}

	public double getMprice() {
		return mprice;
	}

	public void setMprice(double mprice) {
		this.mprice = mprice;
	}

	public int getMqty() {
		return mqty;
	}

	public void setMqty(int mqty) {
		this.mqty = mqty;
	}

	public double getMamt() {
		return mamt;
	}

	public void setMamt(double mamt) {
		this.mamt = mamt;
	}

	
	
	public ArrayList clientlist(){
		ManagerUserDAO clientmanager = new ManagerUserDAO();
		//System.out.println("in clientlist:"  + user.getUsername());
		return clientmanager.gettingclients(user.getUsername());
	}
	
	public void timeseriesmanager() throws MalformedURLException, IOException {

		stockApiBean.installAllTrustingManager();

        System.out.println("selectedItem: " + this.mpursymbol + selprice);
//        System.out.println("selectedInterval: " + this.selectedInterval);
        String symbol = this.mpursymbol;
        setSelpursymbol(mpursymbol);
       
        
        String interval = this.minterval;
        String API_KEY = "AF93E6L5I01EA39O";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        java.io.InputStream inputStream = new URL(url).openStream();
        System.out.println("in timeseries after inputstream");
        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
     
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
            	System.out.println("timeseries in if method");
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup = "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
                System.out.println("timeseries if end" +jsob.getString("1. Information"));
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup = "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/faces/Purchasem.xhtml?symbol=" + mpursymbol + "&price=" + subJsonObj.getString("4. close") + "'>Buy Stock</a></td>";
                        setSelprice(subJsonObj.getString("4. close"));
                    }
                    this.table2Markup += "</tr>";
                    i++;
                }
                this.table2Markup += "</tbody></table>";
            }
        }System.out.println("before return timeseries");
        return;
    }
	
	public String addstockspur(String nameofclient,String symbol, double price, int qty, double amt){
    	PurchaseDAO p = new PurchaseDAO(); 
    	if(p.purchase(nameofclient,symbol, price, qty, amt, user.getUsername()).equals("fails")){
    		//System.out.println("in createdb");
    		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Amount is greater than balance",""));
    		return "purchase";
    	}else{
    		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully purchased stock",""));
		return "purchase";

    	}
    }


}
