package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import dao.MyWatchlistDAO;
import model.StockApiBean;
import model.User;

@ManagedBean(name = "myWatchlist", eager=true)
@SessionScoped
public class MyWatchlist {
	@ManagedProperty(value = "#{stockApiBean}")
	private StockApiBean stockApiBean = new StockApiBean();

	public StockApiBean getStockApiBean() {
		return stockApiBean;
	}

	public void setStockApiBean(StockApiBean stockApiBean) {
		this.stockApiBean = stockApiBean;
	}
	@ManagedProperty(value = "#{user}")
	private User user = new User();
	private String table1Markup;
	private String table2Markup;
	
	public String getTable2Markup() {
		return table2Markup;
	}

	public void setTable2Markup(String table2Markup) {
		this.table2Markup = table2Markup;
	}

	public String getTable1Markup() {
		return table1Markup;
	}

	public void setTable1Markup(String table1Markup) {
		this.table1Markup = table1Markup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String addstock(){
		MyWatchlistDAO myw = new MyWatchlistDAO();
	//	System.out.println("in addstock mywwatchlsit:" + stockApiBean.getSelectedSymbol() + user.getUsername());
		myw.stockadded(stockApiBean.getSelectedSymbol(), user.getUsername());
		String message = "Invalid UserName or Password"; 
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
		return "/MyWatchlist.xhtml?faces-redirect=true";
	}
	
	public ArrayList getwatchliststocks(){
		MyWatchlistDAO mywatchliststock = new MyWatchlistDAO();
		//System.out.println("method call times");
		return mywatchliststock.gettingwatchlist(user.getUsername());
	}
	
//	public ArrayList<String> getwatchliststocks(){
//		MyWatchlistDAO mywatchliststock = new MyWatchlistDAO();
//		System.out.println("method call times");
//		return mywatchliststock.gettingwatchlist(user.getUsername());
//	}
	
    public void timeserieswatchlist(String stocksymbol) throws MalformedURLException, IOException {

        stockApiBean.installAllTrustingManager();

       // System.out.println("selectedItem: " + stocksymbol);
//        System.out.println("selectedInterval: " + this.selectedInterval);
        String symbol = stocksymbol;
        String interval = "1min";
        String API_KEY = "AF93E6L5I01EA39O";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();
        //System.out.println("in timeseries after inputstream");
        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
          //  	System.out.println("timeseries in if method");
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
                        this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/faces/purchase.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Buy Stock</a></td>";
                    }
                    this.table2Markup += "</tr>";
                    i++;
                    break;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
//        System.out.println("before return timeseries");
        return;
    }
}
