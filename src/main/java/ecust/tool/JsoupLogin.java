package ecust.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ecust.jdbc.LoginPool;
import ecust.model.Login;

public class JsoupLogin {

	public static int MAX_TRY=1;
	public static String msg=null;
	public void login(String userName,String pwd) throws Exception{
		Login instance=Login.getInstance(null,null);
		instance.setCon("http://us.trendsamazon.com/login");
		Connection con=instance.getCon();
        Response rs= con.execute();//获取响应
        Document d1=Jsoup.parse(rs.body());//转换为Dom树
 	    List<Element> et= d1.select("div.login form");//获取form表单，可以通过查看页面源码代码得知
 	   
  	   //获取，cooking和表单属性，下面map存放post时的数据 
        Map<String, String> datas=new HashMap<>();
        for(Element e:et.get(0).getAllElements()){
	        if(e.attr("name").equals("account[email]")){
	          e.attr("value", userName);//设置用户名
	        }
	        
	         if(e.attr("name").equals("account[password]")){
	          e.attr("value",pwd); //设置用户密码
	        }
	        
	        if(e.attr("name").length()>0){//排除空值表单属性
	          datas.put(e.attr("name"), e.attr("value"));  
	        }
	}
    	instance.setData(datas);
    	instance.setCookies(rs.cookies());
    	LoginPool.cookies=rs.cookies();
    	LoginPool.data=datas;
    	LoginPool.addConnection(instance);
   }
	
}
