package com.ecust.spider.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.TimerTask;

import com.ecust.spider.Constants;
import com.ecust.spider.Value;
import com.ecust.spider.util.JsoupUtil;

public class DailyTask extends TimerTask {

	private static HashSet<String> mHashSet;
	static ArrayList<Queue<String>> treadQueues;
	private int type;
	private String mapUrl;

	public DailyTask(int type) {
		this.type = type;
	}

	public DailyTask() {
	}

	@Override
	public void run() {
		if (!Value.getDbState() && Value.getmSqlUtil() != null) {
			Value.getmSqlUtil().deleteAll(Constants.Amazon_TABLE); // 清空数据库
//			Value.getmSqlUtil().deleteAll(Constants.YHD_TABLE);
//			Value.getmSqlUtil().deleteAll(Constants.SN_TABLE);
		}

		//addMapToQueue(Constants.JD_MAP_URL);
	//	addMapToQueue(Constants.YHD_MAP_URL);
		addMapToQueue(Constants.Amazon_MAP_URL);
		for (int i = 0; i < Constants.THREAD_NUM; i++) {
			new Thread(new SpiderExecuter() {
			}) {
			}.start();
		}
		System.out.println(Value.totalQueue.size());
	}

	private void addMapToQueue(String map) {
		HashSet<String> mapqueue=new HashSet<>();
		mapqueue.add("http://www.amazon.com/home-garden-kitchen-furniture-bedding/b/ref=sd_allcat_home_storefront?ie=UTF8&node=1055398");
		mapqueue.add("http://www.amazon.com/b/ref=s9_acss_bw_ln_test_p?_encoding=UTF8&node=284507&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=merchandised-search-leftnav&pf_rd_r=0R6F31FJG3RBRT2EP4BZ&pf_rd_t=101&pf_rd_p=2408420922&pf_rd_i=1055398");
		//HashSet<String> mapqueue=JsoupUtil.praseArray(map);
		//先要再遍历一遍map页面的链接
		setmHashSet(mapqueue);
		Value.totleNum += getmHashSet().size();
		Value.addQueue(getmHashSet());
	}

	public static HashSet<String> getmHashSet() {
		return mHashSet;
	}

	public static void setmHashSet(HashSet<String> mHashSet) {
		DailyTask.mHashSet = mHashSet;
	}

}
