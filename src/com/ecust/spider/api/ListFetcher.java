package com.ecust.spider.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ecust.spider.Constants;
import com.ecust.spider.Value;
import com.ecust.spider.bean.model.AmazonItem;
import com.ecust.spider.bean.model.Item;
import com.ecust.spider.util.BloomFilter;
import com.ecust.spider.util.ItemFetcherFactory;
import com.ecust.spider.util.ListFilter;

public abstract class ListFetcher {
	protected static final int MAX_TRY = 3;
	protected String table = "";
	public abstract void ExcuteList(String oneListUrl);

	public abstract String GetNextPage(String nextPageUrl, String page,
			int currentI);
	public abstract int GetMaxNum(Element doc);

	protected void excuteGeneralList(String oneListUrl, String[] Listclass,
			Map<String, String> pageClass, int type, int length) {
		switch (type) {
		case Constants.Amazon:
			table = Constants.Amazon_TABLE;
			break;
		default:
			break;
		}
		try {
			Document doc = Getdoc(oneListUrl, MAX_TRY);
			String[] removeString = { "Previous Page", ".", "Next Page" };
			// 得到当前页面的ItemList
			HashSet<String> itemList = new HashSet<String>();
			try {
				itemList = GetItemList("", doc, Listclass);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("获取当前网页" + oneListUrl + "的item列表失败");
				e.printStackTrace();
			}

			// 获取分页的最大页数和分页符号，初始化下一页
			// HashMap<Element, String>
			// nextMap=GetMaxpageUrl(doc,pageClass,removeString);
			int Urlend = 1;
			String page = "";
			String nextPage = "";
			try {
				if(!pageClass.isEmpty()){
					HashMap<Element, String> nextMap = GetMaxpageUrl(doc,
							pageClass, removeString);
					Urlend = Integer.parseInt(nextMap.entrySet().iterator().next()
							.getKey().text().trim());
					int urlmax=GetMaxNum(doc);
					if(Urlend<urlmax)
						Urlend=urlmax;
					page = nextMap.entrySet().iterator().next().getValue();
					nextPage = nextMap.entrySet().iterator().next().getKey()
							.absUrl("href");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("当前页不存在分页" + oneListUrl);
			}

			// 循环获取当前页所包含的item地址，获得详情后写入数据库
			if(Urlend>=2){
				for (int currentI = 2; currentI <= Urlend; currentI++) {
					// 调用处理item详情页面的方法
					for (Iterator<String> itemurl = itemList.iterator(); itemurl
							.hasNext();) {
						String url = itemurl.next();
						if (!BloomFilter.ifNotContainsSet(url)
								&& url.length() > length) {
							// System.out.println(url);
							Item item = ItemFetcherFactory.getItemFetcher(type)
									.getItemInfo(url);
							if (item == null) {
								continue;
							}
							try {
								if (Value.getmSqlUtil() != null) {
									Value.getmSqlUtil().addAmazonItem((AmazonItem) item, table);
								} else {
									System.out.print("获取数据库实例失败！");
								}
							} catch (Exception e) {
								// TODO: handle exception
								System.out.println("获取item失败");
								e.printStackTrace();
							}

						}

					}
					itemList.clear();
					
					try {
						nextPage = GetNextPage(nextPage, page, currentI);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("获取分页失败");
						e.printStackTrace();
					}
					try {
						itemList = GetItemList(nextPage, null, Listclass);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("获取当前网页" + oneListUrl + "的item列表失败");
						e.printStackTrace();
					}

				}
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("list发生未知错误 " + oneListUrl);
			e.printStackTrace();
		}
	}

	protected HashMap<Element, String> GetMaxpageUrl(Document doc,
			Map<String, String> pageclass, String[] removeString) {
		Iterator<Entry<String, String>> iterator = pageclass.entrySet()
				.iterator();
		Elements elements = null;
		Elements links = null;
		String page = "";
		while (iterator.hasNext()) {
			Entry<String, String> oneEntry = iterator.next();
			elements = doc.getElementsByClass(oneEntry.getKey());

			links = elements.select("a[href]");
			page = oneEntry.getValue();
			if (links != null && !links.isEmpty()) {
				break;
			}

		}
		Elements listlink = new Elements();
		for (int i = 0; i < links.size(); i++) {
			if (!links.get(i).text().contains(removeString[0])
					&& !links.get(i).text().contains(removeString[1])
					&& !links.get(i).text().contains(removeString[2]))
				listlink.add(links.get(i));
		}
		HashMap<Element, String> nextMap = new HashMap<Element, String>();
		nextMap.put(listlink.last(), page);
		return nextMap;
	}

	public Document Getdoc(String oneListUrl, int tryTime) {
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
				e.printStackTrace();
			}
		}
		return doc;
	}

	protected HashSet<String> GetItemList(String url, Document document,
			String[] classString) {
		HashSet<String> Itemlist = new HashSet<String>();
		try {
			Document doc = null;
			if (document == null) {
				if(!url.isEmpty())
					doc = Getdoc(url, MAX_TRY);
				else
					return null;
			} else {
				doc = document;
			}
			//System.out.println(doc.toString());
			Elements links = null;
			for (int j = 0; j < classString.length; j++) {
				try {
					String[] sep = classString[j].split("&");
					for (int temp = 0; temp < sep.length; temp++) {
						if (temp == 0) {
							links = doc.select(sep[temp]);
						} else {
							links = links.select(sep[temp]);
						}
						if (links != null && !links.isEmpty()) {
							break;
						}
					}
					links = links.select("a[href]");
				} catch (Exception e) {
					System.out.println("我也不知道发生了什么神奇的错误...");
					e.printStackTrace();
				}
				
			}
			if (links == null || links.isEmpty()) {
				links = doc.select("a[href]");
				System.out.println("当前页面" + url + "获取不全");
			}
			else{
				for (Element link : links) {
					String shref=link.attr("abs:href");
					if (ListFilter.UrlJudge(shref,
							ListFilter.product) ||ListFilter.UrlJudge(shref,
									ListFilter.ITEM)) {
						Itemlist.add(shref);
					}
				}
			}
			// System.out.println(Itemlist.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return Itemlist;
	}

}
