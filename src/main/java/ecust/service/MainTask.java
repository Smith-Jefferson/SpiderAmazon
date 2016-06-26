package ecust.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import ecust.data.SaveTrendsAmazon;
import ecust.data.TrendAmazonDataOp;
import ecust.model.Product;
import ecust.strategy.ItemList;
import ecust.strategy.TrendsAmazonItemDetial;
import ecust.strategy.TrendsAmazonList;
import ecust.tool.BloomFilter;
import ecust.tool.JsoupLogin;

public class MainTask {

	private static BloomFilter bloomFilter=new BloomFilter();
	public static void main(String[] args) throws Exception{
		JsoupLogin logintool=new JsoupLogin();
		logintool.login("xyg177@qq.com ", "1983321");
		System.out.println("获取cookies和登录信息");
	//	productTask();
		productDetailTask();
	}
	public static void productDetailTask(){
		initBloomFilter();
	    initBloomFilterById();
		initBloomFilterBydetail();
	    int num=1000000;
	    int start=1;

	    int end=num;
	    int threadNum=20;
//	    TrendsAmazonItemDetial TDetial=new TrendsAmazonItemDetial();
//    	TDetial.setStart(start);
//    	TDetial.setEnd(end);
//    	TDetial.run();
	    CountDownLatch threadSignal = new CountDownLatch(threadNum);//初始化countDown  
	    for(int i=1;i<=threadNum;i++){
	    	end=start+num/threadNum;
	    	TrendsAmazonItemDetial TDetial=new TrendsAmazonItemDetial();   	
	    	TDetial.setStart(start);
	    	TDetial.setEnd(end);
	    	TDetial.setThreadsSignal(threadSignal);
	    	start=end+1;
	    	
	    	new Thread(TDetial).start();
	    	
	    	}
	    try {
			threadSignal.await();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}//等待所有子线程执行完  
	    System.out.println(Thread.currentThread().getName() + "结束.");//打印结束标记 
	}
	public static void productTask(){
		initBloomFilter();
		List<String> queryQueue=new CopyOnWriteArrayList<>();
		ItemList itemList=new TrendsAmazonList();
		String url="http://us.trendsamazon.com/products";
		Map<String,String> data=new HashMap<String, String>();
		data.put("MAX_NUM","500");
		queryQueue.add("bedding");
		for(int i=0;i<queryQueue.size();i++){
			String keyword=queryQueue.get(i);
			if(!bloomFilter.ifNotContainsSet(keyword)){
				List<Product> produts=itemList.getItemList(url, keyword, data);
				SaveTrendsAmazon saveAmazon=new SaveTrendsAmazon();
				saveAmazon.setProducts(produts);
				saveAmazon.run();
			//	new Thread(saveAmazon).start();
			}
			
			List<String> keywords = null;
			try {
				keywords = TrendAmazonDataOp.getKeyWords();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			for(String keyword1:keywords){
				if(!bloomFilter.contains(keyword1)){
					queryQueue.add(keyword1);
				}
			}
		}
		System.out.println("总共有"+queryQueue.size()+"门行业");
//		new Thread(new TrendsAmazonItemDetial()){}.start();
	}
	public static void initBloomFilter(){
		try {
			List<String> asinList=TrendAmazonDataOp.getAsin();
			for(String asin:asinList){
				//			System.out.println(asin);
				if(asin==null)
					continue;
				bloomFilter.add(asin);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public static void initBloomFilterById(){
		System.out.println("初始化系统");
		try {
			List<String> urlList=TrendAmazonDataOp.getUrls();
			System.out.println("当前系统中共有数据"+urlList.size()+"条");
			for(String url:urlList){
				//			System.out.println(asin);
				if(url==null)
					continue;
				bloomFilter.add(url);
			}
			System.out.println("初始化系统完成");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public static void initBloomFilterBydetail(){
		System.out.println("初始化系统");
		try {
			List<String> asinList=TrendAmazonDataOp.getAsinTime();
			System.out.println("当前系统中共有数据详情"+asinList.size()+"条");
			for(String asintime:asinList){
				if(asintime==null)
					continue;
				bloomFilter.add(asintime);
			}
			System.out.println("初始化系统完成");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}