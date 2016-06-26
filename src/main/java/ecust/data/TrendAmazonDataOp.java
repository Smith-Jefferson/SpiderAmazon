package ecust.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ecust.jdbc.ConnectPool;
import ecust.jdbc.SqlUtil;
import ecust.model.Product;
import ecust.model.searchProduct;
import ecust.model.searchProductDetail;
import ecust.tool.BloomFilter;

public class TrendAmazonDataOp  implements SaveData{
	private static int num=1;
	private static int status;
	public static List<String> getKeyWords() throws SQLException{
		String query="SELECT distinct  Icategory from trendsamazon";
		Connection conn=null;
		PreparedStatement pstmt =null;
		ResultSet set= null;
		try{
			conn=ConnectPool.instance().getConnection();
			pstmt=conn.prepareStatement(query);
			set= pstmt.executeQuery();
			List<String> catelist= new ArrayList<String>(set.getRow());
			while(set.next()){
				catelist.add(set.getString("Icategory"));
			}
			return catelist;
		}
		finally{
			SqlUtil.free(set,pstmt,conn);
		}
	}
	
	public static List<String> getAsin() throws SQLException{
		String query="SELECT asin from trendsamazon";
		Connection conn=null;
		PreparedStatement pstmt =null;
		ResultSet set= null;
		try{
			conn=ConnectPool.instance().getConnection();
			pstmt=conn.prepareStatement(query);
			set= pstmt.executeQuery();
			List<String> asinlist= new ArrayList<String>(set.getRow());
			while(set.next()){
				asinlist.add(set.getString("asin"));
			}
			return asinlist;
		}
		finally{
			SqlUtil.free(set,pstmt,conn);
		}
	}
	public static List<String> getUrls() throws SQLException{
		String query="SELECT Iurl from trendsamazon";
		Connection conn=ConnectPool.instance().getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet set= null;
		try{
			set= pstmt.executeQuery();
			List<String> urllist= new ArrayList<String>(set.getRow());
			while(set.next()){
				urllist.add(set.getString("Iurl"));
			}
			return urllist;
		}
		finally{
			SqlUtil.free(set,pstmt,conn);
		}
	}
	public static List<String> getUrl() throws SQLException{
		String query="SELECT Iurl from trendsamazon";
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet set= null;
		try{
			conn=ConnectPool.instance().getConnection();
			pstmt = conn.prepareStatement(query);
			set= pstmt.executeQuery();
			List<String> urllist= new ArrayList<String>(set.getRow());
			while(set.next()){
				urllist.add(set.getString("Iurl"));
			}
			return urllist;
		}
		finally{
			SqlUtil.free(set,pstmt,conn);
		}
	}
	
	public static String getAsinByUrl(String url) throws SQLException{
		String query="SELECT asin from trendsamazon where Iurl="+url;
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet set= null;
		String asin=null;
		try{
			conn=ConnectPool.instance().getConnection();
			pstmt = conn.prepareStatement(query);
			set= pstmt.executeQuery();	
			while(set.next()){
				asin=set.getString("asin");
			}
			return asin;
		}
		finally{
			SqlUtil.free(set,pstmt,conn);
		}
	}
	@Override
	public void saveAll(List<Product> products){
		for(Product product:products){
			try {
				save(product,"trendsamazon");
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
	}
	
	public  void save(Product product,String table) throws SQLException{
		searchProduct product1=(searchProduct)product;
		Connection conn=null;
		PreparedStatement pstmt = null;
		BloomFilter bloomFilter=new BloomFilter();
		if(!bloomFilter.ifNotContainsSet(product1.getAsin())){
			String query="INSERT INTO "
					+ table
					+ "(Iname,Iurl,Iimg_url,Iprice,asin,sellers,Icategory,rank)"
					+ " VALUES('" + product1.getIname() + "','" + product1.getIurl()  + "','"
				    + product1.getIimg_url() + "','"
					+ product1.getIprice()+ "','" + product1.getAsin() + "','"
					+ product1.getSellers() + "','" + product1.getIcategory()+ "','" + product1.getRank()+ "')";
			try{	
				conn=ConnectPool.instance().getConnection();
				pstmt = conn.prepareStatement(query);
				status=pstmt.executeUpdate();
				if(status==1){
					System.out.println(Thread.currentThread().getName()+"第"+(num++)+"个产品"+product+"已入库");
				}else{
					System.out.println(Thread.currentThread().getName()+":产品"+product+"入库失败");
				}
				
			} catch (SQLException e) {
			
				System.out.println(e.toString());
			}
			finally{
				ConnectPool.freeConnection(conn);
			}
			
		}
		
	}
	public  void updateRankByAsin(double price,int rank,String asin) throws SQLException{
		asin=asin.replace("\"", "");
		Connection conn=null;
		PreparedStatement pstmt =null;	
		String update="update trendsamazon SET rank="+rank+",Iprice="+price+" where asin=\""+asin+"\"";
		try{
			 conn=ConnectPool.instance().getConnection();
			pstmt = conn.prepareStatement(update);
			pstmt.executeUpdate();
			System.out.println(Thread.currentThread().getName()+"更新商品"+asin+"排名成功");
		}finally{
			ConnectPool.freeConnection(conn);
		}
	
		
	}
	public static List<String> getAsinTime()throws SQLException{
		String query="SELECT asin,inserttime from trendsdetail ";
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet set= null;
		
		try{
			conn=ConnectPool.instance().getConnection();
			pstmt = conn.prepareStatement(query);
			set= pstmt.executeQuery();	
			List<String> urllist= new ArrayList<String>(set.getRow());
			while(set.next()){
				urllist.add(set.getString("asin")+set.getString("inserttime"));
			}
			return urllist;
		}
		finally{
			SqlUtil.free(set,pstmt,conn);
		}
	}
	public static List<String>  getUrlNoRank() throws SQLException{
		String query="SELECT Iurl from trendsamazon where rank = 0 ";
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet set= null;
		
		try{
			conn=ConnectPool.instance().getConnection();
			pstmt = conn.prepareStatement(query);
			set= pstmt.executeQuery();	
			List<String> urllist= new ArrayList<String>(set.getRow());
			while(set.next()){
				urllist.add(set.getString("Iurl"));
			}
			return urllist;
		}
		finally{
			SqlUtil.free(set,pstmt,conn);
		}
	}
	public  void saveDetail(searchProductDetail detail) throws SQLException{
		
		PreparedStatement pstmt = null;
		Connection conn=null;
		String query="INSERT INTO  trendsdetail"
				+ "(best_sellers_rank,sellers_count,review_count,best_review,rank_up_down,review_up_down,good_review_up_down,"
				+ "asin,price,sellers,review,star4,star5,rank,inserttime)"
				+ " VALUES('" + detail.getBest_sellers_rank()+ "','" + detail.getSellers_count() + "','"
			    + detail.getReview_count()+ "','"+ detail.getBest_review()+ "','"
				+ detail.getRank_up_down()+ "','" + detail.getReview_up_down()+ "','"
				+ detail.getGood_review_up_down() + "','" + detail.getAsin()+"','"
				+ detail.getPrice()+ "','" + detail.getSellers()+"','"
				+ detail.getReview()+ "','" + detail.getStar4()+"','" + detail.getStar5()+"','" 
						+ detail.getRanks()+"','" + detail.getInserttime()+
				"')";
		String asinTime=detail.getAsin()+detail.getInserttime();
		try{
			conn=ConnectPool.instance().getConnection();	
			BloomFilter bloomFilter=new BloomFilter();
			if(!bloomFilter.ifNotContainsSet(asinTime)){
				pstmt = conn.prepareStatement(query);
				status=pstmt.executeUpdate();
				if(status==1){
					System.out.println(Thread.currentThread().getName()+":"+detail+"成功");
				}else{
					System.out.println(detail+"失败");
				}
			}
		}
		catch (SQLException e) {
		
			System.out.println(e.toString());
		}
		finally{
			ConnectPool.freeConnection(conn);
		}
		
		}
}