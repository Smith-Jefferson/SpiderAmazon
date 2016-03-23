package com.ecust.spider.task;

import java.util.Queue;

import com.ecust.spider.Value;
import com.ecust.spider.api.Task;
import com.ecust.spider.util.QueueFetcher;

public class SpiderExecuter implements Task {
	private Queue<String> mQueue;

	public SpiderExecuter(Queue<String> mQueue) {
		this.mQueue = mQueue;
	}

	public SpiderExecuter() {
		this.mQueue = Value.totalQueue;
	}

	@Override
	public void run() {
		QueueFetcher.fetchQueue(mQueue);
	}

}
