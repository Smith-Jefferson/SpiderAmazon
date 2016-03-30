package com.ecust.spider.test;

import java.util.HashSet;
import java.util.Set;

import com.ecust.spider.Constants;
import com.ecust.spider.Value;
import com.ecust.spider.bean.model.Item;
import com.ecust.spider.fetcher.listFetcher.AmazonListFetcher;
import com.ecust.spider.task.DailyTask;
import com.ecust.spider.task.SpiderExecuter;
import com.ecust.spider.util.ItemFetcherFactory;
import com.ecust.spider.util.JsoupUtil;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*SqlUtil mSqlUtil = new SqlUtil(Constants.DB_NAME,Constants.DB_USER_NAME,Constants.DB_USER_PASS);
		Value.setmSqlUtil(mSqlUtil);
		if(!Value.getDbState()&&Value.getmSqlUtil()!=null){
			Value.getmSqlUtil().deleteAll(Constants.JD_TABLE);	//清空数据库
			Value.getmSqlUtil().deleteAll(Constants.YHD_TABLE);
		}*/
//		DailyTask.setmHashSet(JsoupUtil.praseArray(Constants.Amazon_MAP_URL));
		HashSet<String> testset=new HashSet<>();
		testset.add("http://www.amazon.com/home-garden-kitchen-furniture-bedding/b/ref=sd_allcat_home_storefront?ie=UTF8&node=1055398");
		DailyTask.setmHashSet(testset);
		Value.totleNum += DailyTask.getmHashSet().size();
		Value.addQueue(DailyTask.getmHashSet());
		new Thread(new SpiderExecuter() {
		}) {
		}.start();
//		
//		AmazonListFetcher listFetcher=new AmazonListFetcher();
//		for(String url:Value.totalQueue){
//			listFetcher.ExcuteList(url);
//		}
//		Item item = ItemFetcherFactory.getItemFetcher(Constants.Amazon)
//				.getItemInfo("http://www.amazon.com/Vichy-Thermale-Sensitive-Paraben-Free-Alcohol-Free/dp/B000V3ME6Y/ref=lp_12630928011_1_24_s_it/181-1214557-2236551?s=luxury-beauty&ie=UTF8&qid=1459030472&sr=1-24");
//		new Thread(new Runnable()
//		{
//
//			@Override
//			public void run() {
//				QueueFetcher.ExcuteItemQueue("http://list.jd.com/list.html?cat=670,671,672");
//			}}){}.start();
//			new Thread(new Runnable()
//			{
//
//				@Override
//				public void run() {
//					QueueFetcher.ExcuteItemQueue("http://list.yhd.com/c21969-0-0/");
//				}}){}.start();
//				new Thread(new Runnable()
//				{
//
//					@Override
//					public void run() {
//						QueueFetcher.ExcuteItemQueue("http://list.jd.com/list.html?cat=9987,653,655");
//					}}){}.start();
//					new Thread(new Runnable()
//					{
//
//						@Override
//						public void run() {
//							QueueFetcher.ExcuteItemQueue("http://list.yhd.com/c21307-0-0/");
//						}}){}.start();
	}

}
