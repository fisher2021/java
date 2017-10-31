package com.upd.common.util.splider; /**
 * Created by Administrator on 2017/4/15.
 */
import java.sql.*;

public class DBHelper {
    public static final String url = "jdbc:mysql://127.0.0.1/sixing?useUnicode=true&characterEncoding=gbk&autoReconnect=true";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "root";

    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper() {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement exeSql(String sql){
        try {
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pst;
    }
    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int findCount(){
// 总记录数
        int count = 0;
// 查询总记录数SQL语句
        String sql = "select count(*) from tb_product";
        try {
// 创建Statement
            Statement stmt = conn.createStatement();
// 查询并获取ResultSet
            ResultSet rs = stmt.executeQuery(sql);
// 光标向后移动，并判断是否有效
            if(rs.next()){
// 对总记录数赋值
                count = rs.getInt(1);
            }
// 关闭ResultSet
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
// 返回总记录数
        return count;

    }
}
