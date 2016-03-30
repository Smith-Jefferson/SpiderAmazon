package com.ecust.spider.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ecust.spider.Constants;

public class JsoupUtil {

	public static LinkedHashMap<String, String> prase(String url) {
		LinkedHashMap<String, String> mMap = new LinkedHashMap<String, String>();
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

	public static LinkedBlockingQueue<String> praseQueue(String url) {
		LinkedBlockingQueue<String> mQueue = new LinkedBlockingQueue<String>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements lists = doc.getElementsByClass("mc");
			Elements links = lists.select("a[href]");
			for (Element link : links) {
				if (ListFilter.UrlJudge(link.attr("abs:href"), ListFilter.LIST)) {
					mQueue.add(link.attr("abs:href"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mQueue;
	}
	protected static Document Getdoc(String oneListUrl, int tryTime) {
		int mTryTime = --tryTime;

		Document doc = null;
		Connection conn = null;
		try {
			// 获取item页，总共有多少页
			conn = Jsoup.connect(oneListUrl);
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");

			doc = conn.timeout(200 * 1000).get();// 如果页面没有抓全，重新抓取
			if (doc == null && tryTime >= 0) {
				System.out.println("解析list：" + oneListUrl + "的 DOC 时出错！剩余尝试次数："
						+ tryTime);
				return Getdoc(oneListUrl, mTryTime);
			} else if (doc == null) {
				System.out.println("解析list：" + oneListUrl + "的 DOC 时出错！");
			}
		} catch (Exception e) {
			if (tryTime >= 0) {
				System.out.println("解析list：" + oneListUrl + "的时出错！剩余尝试次数："
						+ tryTime);
				return Getdoc(oneListUrl, mTryTime);
			} else {
				System.out.println("解析list：" + oneListUrl + "时出错！");
				return null;
				//e.printStackTrace();
			}
		}
		return doc;
	}

	public static HashSet<String> praseArray(String url) {
//		if (url.equals(Constants.YHD_MAP_URL)) {
//			return praseYHDArray(url);
//		}else if (url.equals(Constants.SN_MAP_URL)) {
//			return praseSNArray(url);
//		}
//		
		HashSet<String> mQueue = new HashSet<String>();
		Document doc = Getdoc(url,3);
//		System.out.println(doc);
		String[] containClass={"div.acs-tiles-wrap","div.nav__container","div.fsdDeptCol","div.a-container","div.fsdContainer","div.fsdDeptBox","div.a-section","div.bxc-grid__container","div.acs-tiles-wrap","div.unified_widget","div#container"};
		Elements lists = null;
		for(String str:containClass){
			lists = doc.select(str);
			if(!lists.isEmpty())
				break;
		}
		Elements links = lists.select("a[href]");
		if(links.isEmpty()){
			links = doc.select("a.a-link-normal").select("a[href]");
		}
		for (Element link : links) {
			if (ListFilter.UrlJudge(link.attr("abs:href"), ListFilter.LIST)) {
				mQueue.add(link.attr("abs:href"));
			}
		}
		return mQueue;
	}

	private static HashSet<String> praseYHDArray(String url) {
		HashSet<String> mQueue = new HashSet<String>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements lists = doc.getElementsByTag("em");
			Elements links = lists.select("span").select("a[href]");
			for (Element link : links) {
				if (link.attr("abs:href").startsWith("http://list.yhd.com")) {
					mQueue.add(link.attr("abs:href"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mQueue;
	}
	
	private static HashSet<String> praseSNArray(String url) {
		HashSet<String> mQueue = new HashSet<String>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("span").select("a.searchCity").select("a[href]");
			for (Element link : links) {
				if (link.attr("abs:href").startsWith("http://list.suning.com")) {
					mQueue.add(link.attr("abs:href"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mQueue;
	}

	public static void main(String[] args) {
//		ArrayList<String> mArrayList = praseYHDArray("http://www.yhd.com/marketing/allproduct.html");
//		System.out.print(mArrayList);
	}

}