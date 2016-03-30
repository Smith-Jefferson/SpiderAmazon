package com.ecust.spider.util;

import java.util.List;

import com.ecust.spider.Value;

public class QueueFetcher {

	public static void fetchQueue(List<String> mQueue) {
//		if (Value.getDbState()) {
//			for(String url:mQueue){
//				ListFetcherFactory.getListFetcher(url).ExcuteList(url);
//				System.out.println(++Value.doneNum + "/" + Value.totleNum);
//			}
//		} else {
//			System.out.println("数据库未清空！");
//		}
		for(String url:mQueue){
			ListFetcherFactory.getListFetcher(url).ExcuteList(url);
			System.out.println(++Value.doneNum + "/" + Value.totleNum);
		}
	}
}
