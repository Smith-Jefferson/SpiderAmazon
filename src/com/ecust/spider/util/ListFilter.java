package com.ecust.spider.util;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;



public class ListFilter {
	public final static int LIST =0;
	public final static int ITEM =1;
	public final static int product =2;
	private static Pattern pattern;
//	public static LinkedHashMap<String, String> map2 =new LinkedHashMap<String, String>();
	
	public static Map<String , String> UrlFilter(Map<String, String>  map,int type) {
		Pattern pattern1=null;
		if (type==LIST) {
			 pattern=Pattern.compile("^(http://list|https://list|list){1}[\\w\\.\\-/:]+");
			// pattern1=Pattern.compile("^(http://channel|https://channel|channel){1}[\\w\\.\\-/:]+");
		}

		else if (type==ITEM) {
			 pattern=Pattern.compile("^(http://item|https://item|item){1}[\\w\\.\\-/:]+");
		}
		else if (type==product) {
			pattern=Pattern.compile("^(http://product|https://product|product){1}[\\w\\.\\-/:]+");
		}
		LinkedHashMap<String, String> maplist=new LinkedHashMap<String, String>();
		Iterator<Entry<String, String>> list=map.entrySet().iterator();
		while(list.hasNext()){
			Entry<String, String> oneEntry = list.next();
			String value =oneEntry.getValue();
		    boolean flag= pattern.matcher(value).matches();
		    //System.out.println(flag);
		       if (flag==true) {
		    	   maplist.put(oneEntry.getKey(), oneEntry.getValue());
			} /*else if (pattern1.matcher(value).matches()==true) {
		    	  map2.put(oneEntry.getKey(), oneEntry.getValue());
			}*/
		       
		}
		return maplist;
		
	}
	public static boolean UrlJudge(String url,int type){
		if(url=="")
			return false;
		if(url.matches(".*footer.*"))
			return false;
		boolean flag=false;
		switch(type){
		case LIST:
		    String reg=".*ref.*";
		    flag=url.matches(reg);
			break;
		case ITEM:
			 String reg1=".*ref.*";
			 flag=url.matches(reg1);
			break;
		case product:
			String reg2=".*ref.*";
			 flag=url.matches(reg2);
			break;
		default :
			pattern = null;
			break;
		}
//		if(flag==true){
//			flag=!BloomFilter.ifNotContainsSet(url);
//		}
		return flag;
	}

}
