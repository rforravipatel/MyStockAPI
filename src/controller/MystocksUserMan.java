package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import dao.AdminDAO;
import dao.MyStocksDAO;
import dao.PurchaseDAO;
import dao.SellStockDAO;
import model.StockApiBean;
import model.User;

@ManagedBean(name = "mystocksman", eager=true)
public class MystocksUserMan {
	private int sellqty;
	private double sellprice;
	private double sellamt;
	private static int transid;
	public int getTransid() {
		System.out.println("in get transid:" + transid);
		return transid;
	}

	public void setTransid(int transid) {
		System.out.println("in set transid:" + transid);
		this.transid = transid;
	}

	private String sellstockname;
	public String getSellstockname() {
		//System.out.println("in sellstockname:" + sellstockname) ;
		return sellstockname;
	}

	public void setSellstockname(String sellstockname) {
		//System.out.println("in set    sellstockname:" + sellstockname) ;
		this.sellstockname = sellstockname;
	}

	public int getSellqty() {
		return sellqty;
	}

	public void setSellqty(int sellqty) {
		this.sellqty = sellqty;
	}

	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	public double getSellamt() {
		return sellamt;
	}

	public void setSellamt(double sellamt) {
		this.sellamt = sellamt;
	}

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
	private String table3Markup;
	
	
	public String getTable3Markup() {
		return table3Markup;
	}

	public void setTable3Markup(String table3Markup) {
		this.table3Markup = table3Markup;
	}

	public StockApiBean getStockApiBean() {
		return stockApiBean;
	}

	public void setStockApiBean(StockApiBean stockApiBean) {
		this.stockApiBean = stockApiBean;
	}

	public ArrayList mystocklist(){
		MyStocksDAO mydao = new MyStocksDAO();
		//System.out.println("in mystocklist:"  + user.getUsername());
		return mydao.gettingstocks(user.getUsername());
	}
	
	
	public void sellingstock(int id) throws MalformedURLException, IOException{
		System.out.println("in sellingstock" +id);
	
		SellStockDAO sell = new SellStockDAO();
		StockApiBean stockApiBean = sell.salestock(id);
		float stockprice = timeseriesstocksell(stockApiBean,id);
		//System.out.println("gettt stock price:" + stockprice);
		SellStockDAO s = new SellStockDAO(); 
    	
    	}
	 public String salingstock(int tranid, String symbol, double price, int qty, double amt){
	    System.out.println("in sallingstock: "+ symbol + price + qty + amt + transid);	
	    int taid = transid;
		 SellStockDAO s = new SellStockDAO(); 
	    	if(s.salestock(transid, symbol, price, qty, amt, user.getUsername()).equals("fails")){
	    		//System.out.println("in createdb");
	    		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Qty is greater than your account",""));
	    		return "sell";
	    	}else{
	    		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully selled stock",""));
			return "sell";

	    	}
	    }

	
	public float timeseriesstocksell(StockApiBean stockApiBean, int id) throws MalformedURLException, IOException {

        //stockApiBean.installAllTrustingManager();
        float s = 0;
        //System.out.println("selectedItem: " + stockApiBean.getSellsymbol()+ user.getUsername());
//        System.out.println("selectedInterval: " + this.selectedInterval);
        String symbol = stockApiBean.getSellsymbol();
        String interval = "1min";
        
        String API_KEY = "AF93E6L5I01EA39O";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table3Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();
        //System.out.println("in timeseries after inputstream");
        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
     
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
          //  	System.out.println("timeseries in if method");
                this.table3Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table3Markup = "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table3Markup += "<table>";
                this.table3Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table3Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table3Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table3Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table3Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table3Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table3Markup += "</table>";
            //    System.out.println("timeseries if end" +jsob.getString("1. Information"));
            } else {
                this.table3Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table3Markup = "<table class='table table-hover'>";
                this.table3Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table3Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    s = Float.parseFloat(subJsonObj.getString("4. close"));
                    
                    this.table3Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>";
                            
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        setTransid(id);
                        System.out.println("asddddddddddddddddddddddddddddd"+ id);
                        this.table3Markup += "<td><a class='btn btn-success' href='" + path + "/faces/sell.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") +"&id="+id+ "'>Sell Stock</a></td>";
                    }
                    this.table3Markup += "</tr>";
                    i++;
                    System.out.println("griing close value:" +s);
                    break;
                }
                this.table3Markup += "</tbody></table>";
            }
        }System.out.println("before return timeseriesstocksell");
        return s;
    }

}
