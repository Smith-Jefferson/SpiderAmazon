package com.ecust.spider;


import com.ecust.spider.task.DailyTask;
import com.ecust.spider.util.SqlUtil;

public class SpiderMain {
	
	public static void main(String[] args) {
		SqlUtil mSqlUtil = new SqlUtil(Constants.DB_NAME,Constants.DB_USER_NAME,Constants.DB_USER_PASS);
		Value.setmSqlUtil(mSqlUtil);
		
//		System.getProperties().put("http.proxySet","ture");
//		System.getProperties().put("http.proxyHost","192.168.10.20");
//		System.getProperties().put("http.proxyProt","8080");
//		System.getProperties().put("http.proxyHosts","localhost|127.0.0.1");

		
//		new Thread(new SpiderTask(Constants.JD)
//		{}){}.start();
//		
//		new Thread(new SpiderTask(Constants.YHD)
//		{}){}.start();
		
		new Thread(new DailyTask()
		{}){}.start();
		
	}
}
