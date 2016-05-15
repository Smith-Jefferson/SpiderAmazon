package ecust.model;

public class Product {
	private String Iname;
	private String Iimg_url;
	private String Iurl;
	public String getIname() {
		return Iname;
	}
	public void setIname(String iname) {
		Iname = iname;
	}
	public String getIimg_url() {
		return Iimg_url;
	}
	public void setIimg_url(String iimg_url) {
		Iimg_url = iimg_url;
	}
	public String getIurl() {
		return Iurl;
	}
	public void setIurl(String iurl) {
		Iurl = iurl;
	}
	public Product(String iname, String iimg_url, String iurl) {
		super();
		Iname = iname;
		Iimg_url = iimg_url;
		Iurl = iurl;
	}
	
	public String toString() {
		return "Product [Iname=" + Iname + ", Iurl=" + Iurl + "]";
	}
	public Product(){};
}