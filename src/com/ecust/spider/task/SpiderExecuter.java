package com.ecust.spider.task;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ecust.spider.Value;
import com.ecust.spider.api.Task;
import com.ecust.spider.util.JsoupUtil;
import com.ecust.spider.util.ListFetcherFactory;
import com.ecust.spider.util.QueueFetcher;

public class SpiderExecuter implements Task {
	public static List<String> mQueue=new CopyOnWriteArrayList<>();

	public static void addQueue(Queue<String> queue) {
		mQueue.addAll(queue);
	}
	public static void addUrl(String url) {
		mQueue.add(url);
	}
	public static List<String> getmQueue() {
		return mQueue;
	}

	public static void setmQueue(List<String> mQueue) {
		SpiderExecuter.mQueue = mQueue;
	}

	public SpiderExecuter(Queue<String> mQueue) {
		setmQueue((List<String>) mQueue);
	}

	public SpiderExecuter() {
		mQueue=(List<String>) Value.totalQueue;
	}

	@Override
	public void run() {	
		List<String> sencondqueue=new CopyOnWriteArrayList<>();
		Iterator<String> it= mQueue.iterator();
		while(it.hasNext()){
			String url = it.next();
			System.out.println(url);
			sencondqueue.addAll(JsoupUtil.praseArray(url));
		}
	
		
		Value.totleNum +=sencondqueue.size();
		SpiderExecuter.setmQueue(sencondqueue);
//		Value.addQueue(sencondqueue);
		QueueFetcher.fetchQueue(mQueue);
	}

}
