package com.ecust.spider.util;

import com.ecust.spider.api.ListFetcher;
import com.ecust.spider.fetcher.listFetcher.AmazonListFetcher;
import com.ecust.spider.fetcher.listFetcher.JDListFetcher;
import com.ecust.spider.fetcher.listFetcher.SNListFetcher;
import com.ecust.spider.fetcher.listFetcher.YHDListFetcher;

public class ListFetcherFactory {

	public static final String JD = "jd";
	public static final String YHD = "yhd";
	public static final String SN = "suning";

	public static ListFetcher getListFetcher(String oneListUrl) {
		System.out.println(oneListUrl);
		if (oneListUrl.contains(JD)) {
			return new JDListFetcher();
		} else if (oneListUrl.contains(YHD)) {
			return new YHDListFetcher();
		} else if (oneListUrl.contains(SN)) {
			return new SNListFetcher();
		} else {
			return new AmazonListFetcher();
		}
	//	return null;
	}

}
