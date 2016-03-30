package com.ecust.spider;

public class Constants {
	// 此类保存常数
	public static final String Amazon_MAP_URL = "http://www.amazon.com/gp/site-directory";
	public static final String Amazon_TABLE = "amazonitems";
	public static final String DB_NAME = "amazonproduct";
	//............暂时用不到..................
	public static final String JD_MAP_URL = "http://www.jd.com/allSort.aspx";
	public static final String YHD_MAP_URL = "http://www.yhd.com/marketing/allproduct.html";
	public static final String SN_MAP_URL = "http://www.suning.com/emall/pgv_10052_10051_1_.html";
	public static final String JD_TABLE = "JDitems";
	public static final String YHD_TABLE = "YHDitems";
	public static final String SN_TABLE = "SNitems";
	//................................
	public static final String DB_USER_NAME = "root";
	public static final String DB_USER_PASS = "root";
	public static final int THREAD_SIZE = 90;
	public static final int THREAD_NUM = 10;
	public static final int JD = 0;
	public static final int YHD = 1;
	public static final int SN = 2;
	public static final int Amazon = 3;
	public static final String JD_BLOOM = "<>";
	public static final String YHD_BLOOM = "<>";
	
	//设置线程睡眠时间
	public static final long sleepTime = 1000*60*30L;

}
