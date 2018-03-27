package com.echain.util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DBHelperUtil{
	
	
	// 声明connection  
    public Connection conn = null;  
    // 声明ResultSet  
    public ResultSet rs;  
    // 声明Statement  
    public Statement stmt;  
    
    //PrepareStatement对象  
    private PreparedStatement pstm = null;  
    
    // Oracle驱动  
    private static String className = "com.mysql.jdbc.Driver";  
    // Oracle连接字符串  
    private static String url = "jdbc:mysql://10.0.0.31:3306/fbi?useUnicode=true&amp;characterEncoding=UTF8";  
    // 数据库用户名  
    private static String userName = "root";  
    // 数据库密码  
    private static String password = "FBI_2016_mycat";  
      
  
    // 声明DBManager的私有对象db  
    private static DBHelperUtil db;  
      
    /** 
     * 创建私有构造函数 
     */  
    private DBHelperUtil() {  
        try {  
            // 加载驱动  
            Class.forName(className);  
         // 获取连接  
            try {
				conn = (Connection) DriverManager.getConnection(url, userName, password);
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 提供一个静态方法 
     * @return 返回本类的实例 
     */  
    public static synchronized DBHelperUtil getDBHelperUtil() {  
        if (db==null) {  
            db=new DBHelperUtil();  
        }  
        return db;  
    }  
      
      
    /** 
     * 获取Statement记录 
     * @return stmt 
     */  
    public Statement getStmt() {  
        try {  
            // 获取Statement记录  
            stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return stmt;  
    }  
    
    
    /** 
     * 关闭连接 
     */  
    public void closed() {  
        try {  
            if (rs != null)  
                rs.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        try {  
            if (stmt != null)  
                stmt.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        try {  
            if (conn != null)  
                conn.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
      
    /** 
     * 执行一句查询的sql语句 
     * @param sql sqi语句 
     * @return 结果集 
     */  
    public  List<String> executeQuery(String sql) {  
    	List<String> crawlKeywordsList = new ArrayList<String>();
        try {  
            rs = getStmt().executeQuery(sql);  
            while (rs.next()){
  	    	  crawlKeywordsList.add(rs.getString("works_name"));
            }
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return crawlKeywordsList;  
    }  
      
    /** 
     * 执行单句INSERT、UPDATE 或 DELETE 语句 
     * @param sql sql语句 
     */  
    public  void executeUpdate(String sql) {  
        //执行SQL语句  
        try {  
            getStmt().executeUpdate(sql);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
      
  
    
    public static void main(String[] args) {
//    	List<String> list = executeQuery("select works_name from fbm_works where id in (2575,2576,2577)");
//    	System.out.println(list);
	}
    
    
    
}
