package ecust.model;

import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Login {
	    public  boolean LoginStatus=false;
		private Map<String,String> data;
		private Map<String,String> cookies;
		private 	Connection con;
		public Connection getCon() {
			return con;
		}
		public void setCon(String url) {
			this.con=Jsoup.connect(url);
			this.con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");//≈‰÷√ƒ£ƒ‚‰Ø¿¿∆˜
			this.con.timeout(200 * 1000);
		}
		public Map<String, String> getData() {
			return data;
		}
		public void setData(Map<String, String> data) {
			instance.data=data;
		}
		public Map<String, String> getCookies() {
			return cookies;
		}
		public void setCookies(Map<String, String> cookies) {
			instance.cookies=cookies;
		}
		private static Login instance;
		public Login(String url){	
			setCon(url);	
		};
		public Login(){}
		public static Login getInstance(Map<String,String> data,Map<String,String> cookies){
			if(instance==null)
				instance=new Login();
			if(data!=null)
				instance.setData(data);
			if(cookies!=null)
				instance.setCookies(cookies);		
			return instance;
		}
}
