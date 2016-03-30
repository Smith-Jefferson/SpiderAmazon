package com.ecust.spider.bean.model;

import java.util.ArrayList;

public class Item {
	protected String name; 			//商品名称
	protected String host;			//商品来源
//	protected String id;					//商品ID
	protected String price;			//商品价格
	protected ArrayList<String> catgory;	//商品完整分类结构（从大到小）
	protected String url;				//链接地址
	protected String imageUrl;		//图片链接
	protected String description;		//商品描述
	protected String catFirst;		//商品第一级分类（大）
	protected String catSecond;		//商品第二季分类（中）
	protected String catThird;		//商品第二季分类（小）
	protected String brand = "";			//品牌
	
	public Item(){					//空构造器
		
	}
	
	public Item(String name,String host,String price,	//完整构造器
			ArrayList<String> catgory,String url,String imageUrl,String description){
	
//		for(int i = 0;i<catgory.size();i++){
//			catgory.set(i, catgory.get(i));
//		}
		setName(removeQuo(name));
		setPrice(price);
		setUrl(url);
		setImageUrl(imageUrl);
		setDescription(removeQuo(description));
		setHost(host);
		adaptCatgory(catgory);
		
	}
	
	@Override
	public String toString(){		//获取商品信息
		return new String("该商品："+"名称="+name+" "+
				"来源="+host+" "+
				"价格="+price+" "+
				"分类="+catgory+" "+
				"三级分类="+catFirst+"|"+catSecond+"|"+catThird+" "+
				"链接="+url+" "+
				"图片链接="+imageUrl+" "+
				"描述="+description);
	}
	
	public void adaptCatgory(ArrayList<String> Catgory){
		setCatgory(Catgory);
		switch(Catgory.size()){
		case 0:
			System.out.println("分类信息为空!");
			break;
		case 1:
			setCatFirst(removeQuo(Catgory.get(0)));
			break;
		case 2:
			setCatFirst(removeQuo(Catgory.get(0)));
			setCatSecond(removeQuo(Catgory.get(1)));
			break;
		case 3:
			setCatFirst(removeQuo(Catgory.get(0)));
			setCatSecond(removeQuo(Catgory.get(1)));
			setCatThird(removeQuo(Catgory.get(2)));
			break;
		default:
			setCatFirst(removeQuo(Catgory.get(0)));
			setCatSecond(removeQuo(Catgory.get(1)));
			setCatThird(removeQuo(Catgory.get(2)));
			break;
		}
		setBrand(removeQuo(Catgory.get(Catgory.size()-1)));
	}

	protected String removeQuo(String str){
		if(str!=null &&!str.isEmpty()){
			String rep = new String (str);
			rep = rep.replace("'", "’");
			return rep;
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCatFirst() {
		return catFirst;
	}
	
	public void setCatFirst(String catFirst) {
		this.catFirst = catFirst;
	}

	public String getCatSecond() {
		return catSecond;
	}

	public void setCatSecond(String catSecond) {
		this.catSecond = catSecond;
	}

	public ArrayList<String> getCatgory() {
		return catgory;
	}

	public void setCatgory(ArrayList<String> catgory) {
		this.catgory = catgory;
	}

	public String getCatThird() {
		return catThird;
	}

	public void setCatThird(String catThird) {
		this.catThird = catThird;
	}

	public String getBrand() {
		if(brand.equals("")){
			return null;
		}
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = removeQuo(brand);
	}
}
