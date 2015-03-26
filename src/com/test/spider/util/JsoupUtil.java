package com.test.spider.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.test.spider.mConstants;
import com.test.spider.mValue;
import com.test.spider.entity.Item;

public class JsoupUtil {
	public static LinkedHashMap<String,String> prase(String url){ 
		LinkedHashMap<String,String> mMap = new LinkedHashMap<String,String>();
		try {
            Document doc = Jsoup.connect(url).get(); 
            Elements lists = doc.getElementsByClass("mc");
            Elements links = lists.select("a[href]"); 
            for (Element link : links) { 
            	mMap.put(link.text(), link.attr("abs:href"));
            }  
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mMap;
	}
	
	public static LinkedBlockingQueue<String> praseQueue(String url){
		LinkedBlockingQueue<String> mQueue = new LinkedBlockingQueue<String>();
		try {
            Document doc = Jsoup.connect(url).get(); 
            Elements lists = doc.getElementsByClass("mc");
            Elements links = lists.select("a[href]"); 
            for (Element link : links) { 
            	if(ListFilter.UrlJudge(link.attr("abs:href"), ListFilter.LIST)){
            		mQueue.add(link.attr("abs:href"));
            	}
            }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mQueue; 
	}
	
	public static ArrayList<String> praseArray(String url){
		ArrayList<String> mQueue = new ArrayList<String>();
		try {
            Document doc = Jsoup.connect(url).get(); 
            Elements lists = doc.getElementsByClass("mc");
            Elements links = lists.select("a[href]"); 
            for (Element link : links) { 
            	if(ListFilter.UrlJudge(link.attr("abs:href"), ListFilter.LIST)){
            		mQueue.add(link.attr("abs:href"));
            	}
            }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mQueue; 
	}
	
	
	
	public static void ExcuteItemQueue(String oneListUrl){
		//初始化信息，item列表Itemlist，分页div的class，内容列表的div的class
		int a=1;
		LinkedBlockingQueue<String> Itemlist=new LinkedBlockingQueue<String>(Integer.MAX_VALUE);
		String[] classString={"goods-list-v1","plist"};
		String[] removeString={"页","."};
		Map<String, String> pageclass=new HashMap<String, String>();
		pageclass.put("p-num", "page=");
		pageclass.put("pagin", "p=");
		Iterator<Entry<String, String>> iterator=pageclass.entrySet().iterator();
		try {
			//获取item页，总共有多少页
			Document doc = Jsoup.connect(oneListUrl).get();
			String page = "";
			int Urlend = 1;
			Elements elements=null;
			Elements links=null;
			while (iterator.hasNext()) {
				Entry<String, String> oneEntry = iterator.next();
				elements=doc.getElementsByClass(oneEntry.getKey());
				
				links=elements.select("a[href]");
				page=oneEntry.getValue();
				if (links!=null && !links.isEmpty()) {
					break;
				}
				
			}
			
			for (int i=0;i<links.size();i++) {
				if(links.get(i).text().contains(removeString[0])||links.get(i).text().contains(removeString[1]))
					links.remove(i);
			}
			Urlend=Integer.parseInt(links.last().text().trim());
			
			//获得第一页的item的url列表
			Itemlist=GetItemList(null, doc, classString);
			
			//初始化拼接地址
			String nexpage=links.first().absUrl("href");
			
			
			//循环获取当前页所包含的item地址
			for(int currentI=2;currentI<=Urlend;currentI++){
				//调用处理item详情页面的方法
				while (! Itemlist.isEmpty()) {
					String url=Itemlist.poll();
					if (!BloomFilter.ifNotContainsSet(url)) {
						Item item =	FetchItemUtil.getJDItemInfo(url);
//						System.out.print("获取商品："+item.getName()+"\n");
						if(mValue.getmSqlUtil()!=null){
							mValue.getmSqlUtil().addItem(item,mConstants.JD_TABLE);
						}else{
							System.out.print("获取数据库实例失败！");
						}
					}
					
				}
				Itemlist.clear();
				nexpage=GetNextPage(nexpage, page, currentI);
				Itemlist=GetItemList(nexpage, null, classString);
				
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	public static String GetNextPage(String nextPageUrl,String page,int currentI) {
		//拼接地址
		String[] splitString=nextPageUrl.split("&");
		nextPageUrl="";
		for (int i = 0; i < splitString.length-1; i++) {
			if (splitString[i].contains(page)&& splitString[i+1].contains("JL")) {	
				page +=currentI;
				nextPageUrl +=page+"&";
			}
			else {
				nextPageUrl +=splitString[i]+"&";
			}
			
		}
		nextPageUrl +=splitString[splitString.length-1];
		return nextPageUrl;
	}
	//获取item列表
	public static LinkedBlockingQueue<String> GetItemList(String url,Document document,String[] classString){
		LinkedBlockingQueue<String> Itemlist=new LinkedBlockingQueue<String>();
		try {
			Document doc=null;
			if(document == null){
				doc = Jsoup.connect(url).get(); 
			}
			else {
				doc=document;
			}
			int i=0;
			Elements links =null;
			while (i<=classString.length ) {
				++i;
				links= doc.getElementsByClass(classString[i]).select("a[href]");
				if (links==null||links.isEmpty()) {
					links = doc.getElementById("plist").select("a[href]");
				}
				if (links !=null && !links.isEmpty()) {
					break;
				}
			
			}
            for (Element link : links) { 
            	if(ListFilter.UrlJudge(link.attr("abs:href"), ListFilter.ITEM)){
            		Itemlist.add(link.attr("abs:href"));
            	}
            }  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return Itemlist;
	}

}
