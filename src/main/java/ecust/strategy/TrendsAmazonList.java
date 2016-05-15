package ecust.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ecust.model.Product;
import ecust.tool.SpiderTool;

public class TrendsAmazonList implements ItemList {

	@Override
	public List<Product> getItemList(String url, String keyword, Map<String,String> data) {
		
		String max=data.get("MAX_NUM");
		List<Product> totolProduct=new ArrayList<>();
		int max_num=1;
		if(!max.isEmpty()){
			 max_num=Integer.parseInt(max);
		}
		url+="?keyword="+keyword;
		String url1;
		for(int i=1;i<=max_num;i++){
			url1=url+"&page="+i;
			try {
				int size=totolProduct.size();
				totolProduct.addAll(getItemList(url1));
				if(size==totolProduct.size())
					break;
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return totolProduct;
	}
	public static List<Product> getItemList(String url) throws IOException{
		SpiderTool tool=new SpiderTool();
		Document doc=tool.Getdoc(url,1);
		Elements items=doc.select("ul.list-results>li");
		Item item=new TrendsAmazonItem();
		List<Product> itemList=new ArrayList<>();
		System.out.println("正在解析页面"+url+",产品数量："+items.size());
		for(Element ele:items){
			Product product=(Product)item.getItem(ele);
			itemList.add(product);
		}
		return itemList;
	}
}