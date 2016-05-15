package ecust.strategy;

import org.jsoup.nodes.Element;

import ecust.model.searchProduct;

public class TrendsAmazonItem implements Item {

	@Override
	public Object getItem(Element ele) {
		String iname=getIname(ele);
		String iimage=getIimage(ele);
		String asin=getAsin(ele);
		double iprice=getIprice(ele);
		int sellers=getSellers(ele);
		String iurl=getIurl(ele);
		String icategory=getIcategory(ele);
		searchProduct item=new searchProduct( iname,  iimage,  asin,  iprice,  sellers,  iurl,icategory);
		return (Object)item;
	}
	public String getIname(Element ele) {
		String name=ele.select("a.list-item").select("span.namer").text();
		if(name!=null&&!name.isEmpty())
			return name.replace("'", "");
		return null;
	}
	public String getIimage(Element ele) {
		String name=ele.select("div.ima").select("img[src]").attr("src");
		if(name!=null&&!name.isEmpty())
			return name;
		return null;
	}
	public String getAsin(Element ele) {
		String name=ele.select("span.namer>small").text();
		String[] tems=name.split(":");
		name=tems[2].replace("Price", "");
		name=name.trim();
		if(name!=null&&!name.isEmpty())
			return name;
		return null;
	}
	public String getIurl(Element ele) {
		
		String url=ele.select("a.list-item").attr("href");
		url="http://us.trendsamazon.com"+url;
		if(url!=null&&!url.isEmpty())
			return url;
		return null;
	}
	public String getIcategory(Element ele) {
		String name=ele.select("span.namer>small").text();
		String[] tems=name.split(":");
		try{
			name=tems[1].replace("ASIN", "");
			name=name.trim();
			return name;
			
		}catch(Exception e){
			return null;
		}
	}
	public double getIprice(Element ele) {
		String name=ele.select("span.namer>small").text();
		String[] tems=name.split(":");
		try{
			tems=tems[3].split(" ");
			name=tems[0];
			name=name.trim();
			return Double.parseDouble(name);
			
		}catch(Exception e){
			return 0;
		}
	
	}
	public int getSellers(Element ele) {
		String name=ele.select("span.namer>small").text();
		String[] tems=name.split(":");
		try{
			tems=tems[3].split(" ");
			name=tems[1];
			name=name.trim();
			return Integer.parseInt(name);
			
		}catch(Exception e){
			return 0;
		}
	}
}
