package ecust.jdbc;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import ecust.model.Login;

public class LoginPool {
	public static Map<String,String> data;
	public static Map<String,String> cookies;
	// 连接池队列
		private static Queue<Login> pool = new ConcurrentLinkedQueue<Login>();
		private static LoginPool instance = new LoginPool();
		/**
		 * 获取数据源单例
		 */
		public static LoginPool instance() {
			if (instance == null)
				instance = new LoginPool();
			return instance;
		}
		/**
		 * 获取一个数据库连接
		 */
		public Login getConnection(){
			synchronized (pool) {
				if (pool.size() > 0) {
					return pool.poll();
				} else {
					return Login.getInstance(data,cookies);
				}
			}
		}
		/**
		 * 连接归池，这里的实现思想是使用过的线程入池以备下次使用
		 */
		public static void freeConnection(Login conn) {
			if(conn!=null)
				pool.add(conn);
		}
	 
		public static void addConnection(Login conn){
			if(conn!=null)
				pool.add(conn);
		}
}