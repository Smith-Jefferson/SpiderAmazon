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
        Response rs= con.execute();//��ȡ��Ӧ
        Document d1=Jsoup.parse(rs.body());//ת��ΪDom��
 	    List<Element> et= d1.select("div.login form");//��ȡform��������ͨ���鿴ҳ��Դ������֪
 	   
  	   //��ȡ��cooking�ͱ����ԣ�����map���postʱ������ 
        Map<String, String> datas=new HashMap<>();
        for(Element e:et.get(0).getAllElements()){
	        if(e.attr("name").equals("account[email]")){
	          e.attr("value", userName);//�����û���
	        }
	        
	         if(e.attr("name").equals("account[password]")){
	          e.attr("value",pwd); //�����û�����
	        }
	        
	        if(e.attr("name").length()>0){//�ų���ֵ������
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
