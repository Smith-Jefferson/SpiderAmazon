package com.ecust.spider.test;

import com.ecust.spider.Constants;
import com.ecust.spider.Value;
import com.ecust.spider.bean.model.Item;
import com.ecust.spider.fetcher.listFetcher.AmazonListFetcher;
import com.ecust.spider.task.DailyTask;
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
//		Value.totleNum += DailyTask.getmHashSet().size();
//		Value.addQueue(DailyTask.getmHashSet());
//		
//		AmazonListFetcher listFetcher=new AmazonListFetcher();
//		for(String url:Value.totalQueue){
//			listFetcher.ExcuteList(url);
//		}
		Item item = ItemFetcherFactory.getItemFetcher(Constants.Amazon)
				.getItemInfo("http://www.amazon.com/Guess-V-Neck-Ruched-Halter-Fitted/dp/B01CO42JBM/ref=sr_1_790/191-9055729-8542763?s=apparel&ie=UTF8&qid=1458636789&sr=1-790&nodeID=1040660&refinements=p_89%3AGuess");
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
