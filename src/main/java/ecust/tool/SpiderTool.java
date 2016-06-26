package ecust.tool;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ecust.jdbc.LoginPool;
import ecust.model.Login;

public class SpiderTool {
	public  Document Getdoc(String oneListUrl, int tryTime) throws IOException {
		Login login=null;
		try{
			 login=LoginPool.instance().getConnection();
		}catch(Exception e){
			System.out.println("获取浏览器出问题");
		}
		Document doc=null ;
		Connection conn=login.getCon();
		if(!login.LoginStatus)  {
			synchronized (SpiderTool.class) {
				conn.url("http://us.trendsamazon.com/login");
				conn.ignoreContentType(true).method(Method.POST).data(login.getData()).cookies(login.getCookies()).execute();
				login.LoginStatus=true;
				System.out.println("通过验证");
			}
			
		} 
		int mTryTime = --tryTime;

	
		try {
			conn.url(oneListUrl);
			Response rse= conn.ignoreContentType(true).method(Method.POST).execute();//获取响应
			doc = Jsoup.parse(rse.body());//转换为Dom树
			if (doc == null && tryTime >= 0) {
				System.out.println("解析product：" + oneListUrl + "的 DOC 时出错！剩余尝试次数："
						+ tryTime);
				return Getdoc(oneListUrl, mTryTime);
			} else if (isLogin(doc)) {
				login.LoginStatus=false;
				System.out.println("掉线了，重新登录解析");
				return Getdoc(oneListUrl, mTryTime);
			}
		} catch (Exception e) {
			if (tryTime >= 0) {
				System.out.println("解析product：" + oneListUrl + "的时出错！剩余尝试次数："
						+ tryTime);
				return Getdoc(oneListUrl, mTryTime);
			} else {
				System.out.println("解析product：" + oneListUrl + "时出错！");
				e.printStackTrace();
			}
		}finally{
			LoginPool.freeConnection(login);
		}
		
		return doc;
	}
	public  boolean isLogin(Document doc){
		Elements ele=doc.select("input[name=account[password]]");
		if(ele==null)
			return true;
		return false;
		
	}
}