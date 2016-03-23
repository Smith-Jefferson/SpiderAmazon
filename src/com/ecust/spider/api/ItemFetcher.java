package com.ecust.spider.api;

import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ecust.spider.Value;
import com.ecust.spider.bean.model.*;

public abstract class ItemFetcher {
	protected final static int MAX_TRY = 3;

	public abstract Item getItemInfo(String url);

	public abstract Item getItemInfo(String url, int tryTime);
	
	protected Item getGeneralItemInfo(String host,String url, int tryTime){
		tryTime--;
		Document doc;
		Item currentItem=new AmazonItem();
		String itemID;
		Connection conn = null;
		try 
		{	
			conn = Jsoup.connect(url);
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");

			doc = conn.timeout(200 * 1000).get();// 如果页面没有抓全，重新抓取
            itemID=getItemID(doc);
            currentItem = new AmazonItem(getItemName(doc),host,getItemPrice(itemID,doc),
            		getItemCategory(doc),url,getItemImageUrl(doc),getItemDescription(doc),getItemDetail(doc),
            		getItemReviews(doc),getItemAnswered(doc),getItemSeller(doc),getItemSale(doc),
            		getItemsave(doc),getItemStar(doc),getItemShipping(doc),getItemStock(doc),getItemShipper(doc)
    				);
            
		}
		catch (Exception e) 
		{
			if(tryTime>=0)
			{
				System.out.println("重新获取item信息,url为"+url+"item"+currentItem.toString()+" 剩余次数："+(tryTime+1));
				return getItemInfo(url,tryTime);
			}
			else
			{
				System.out.println("获取Item失败，url为"+url+"item"+currentItem.toString());
				e.printStackTrace();
				Value.errNum++;
				return null;
			}
		}
		return currentItem;
	}

	public abstract String getItemName(Element doc);

	public abstract String getItemImageUrl(Element doc);
	
	public abstract String getItemID(Element doc);
	
	public	abstract String getItemPrice(String itemID);
	
	public	abstract ArrayList<String> getItemCategory(Element doc);
	
	public	abstract String getItemDetail(Element doc);
	public	abstract String getItemDescription(Element doc);
	public	abstract int getItemReviews(Element doc);
	public	abstract String getItemAnswered(Element doc);
	public	abstract String getItemSeller(Element doc);
	public	abstract String getItemSale(Element doc);
	public	abstract String getItemsave(Element doc);
	public	abstract String getItemShipping(Element doc);
	public	abstract String getItemStock(Element doc);
	public	abstract float getItemStar(Element doc);

	public String getItemPrice(String itemID, Element doc) {
		// TODO Auto-generated method stub
		return getItemPrice(itemID);
	}
	public String getItemShipper(Element doc){
		return null;
	}

}
