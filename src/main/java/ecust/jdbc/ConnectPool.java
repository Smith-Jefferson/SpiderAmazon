package ecust.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import javax.activation.DataSource;

/**
 * 实现DataSource接口的简单连接池
 */
public class ConnectPool implements DataSource {
	private static final String url = "jdbc:mysql://175.185.8.9:3306/amazonproduct";  
	private static final String user = "root";
	private static final String pswd = "";
 
	// 连接池队列
	private  static Queue<Connection> pool = new ConcurrentLinkedQueue<Connection>();
	private static ConnectPool instance = new ConnectPool();
 
	/**
	 * 获取数据源单例
	 */
	public static ConnectPool instance() {
		if (instance == null)
			instance = new ConnectPool();
		return instance;
	}
 
	/**
	 * 获取一个数据库连接
	 */
	public Connection getConnection() throws SQLException {
		synchronized (pool) {
			if (pool.size() > 0) {
				return pool.poll();
			} else {
				return DriverManager.getConnection(url, user, pswd);
			}
		}
	}
 
	/**
	 * 连接归池，这里的实现思想是使用过的线程入池以备下次使用
	 */
	public static void freeConnection(Connection conn) {
		pool.add(conn);
	}
 
	public Connection getConnection(String username, String password)
			throws SQLException {
		
		return null;
	}
 
	public PrintWriter getLogWriter() throws SQLException {
		
		return null;
	}
 
	public void setLogWriter(PrintWriter out) throws SQLException {
		
 
	}
 
	public void setLoginTimeout(int seconds) throws SQLException {
		
 
	}
 
	public int getLoginTimeout() throws SQLException {
		
		return 0;
	}
 
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		
		return null;
	}
 
	public <T> T unwrap(Class<T> iface) throws SQLException {
		
		return null;
	}
 
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		
		return false;
	}

	@Override
	public String getContentType() {
		
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		
		return null;
	}

	@Override
	public String getName() {
		
		return null;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		
		return null;
	}
}