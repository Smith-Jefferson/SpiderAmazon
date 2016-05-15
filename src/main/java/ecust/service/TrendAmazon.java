package ecust.service;
import java.util.List;

import ecust.model.searchProduct;
import ecust.tool.JsoupLogin;

/////@WebService
public class TrendAmazon {
	/**
	 * 供客户端调用的方法
	 * @param query
	 * @return
	 */
		public List<searchProduct> ServiceSearch(String query){
			
			return null;
		}
		public static void main(String[] args) {
			
	      //  Endpoint.publish("http://localhost:9001/SpiderServer/TrendAmazon", new TrendAmazon());
	        JsoupLogin logintool=new JsoupLogin();
			try {
				logintool.login("xyg177@qq.com ", "1983321");
				System.out.print("success");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
}
