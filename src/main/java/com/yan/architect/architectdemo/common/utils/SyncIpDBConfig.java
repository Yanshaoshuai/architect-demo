package com.yan.architect.architectdemo.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SyncIpDBConfig {
    @SuppressWarnings({"rawtypes", "static-access"})
    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "select * from user where ip is not null and country is null";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            Map<String,Object> resMap = new HashMap<String,Object>();
            String uid = null;
            String ip = null;
            while (resultSet.next()) {
                uid = resultSet.getString("uid");
                ip = resultSet.getString("ip");
                
                resMap.put(uid, ip);
            }

            //循环处理
            Iterator it = resMap.entrySet().iterator();
            while(it.hasNext()){
                Entry entry = (Entry) it.next();
                System.out.println(entry.getKey() + " : " + entry.getValue());
                
                String uidStr = entry.getKey().toString();
                String ipStr = entry.getValue().toString();
                
                Map<String,Object> info = getCountryInfoByIp(ipStr);
                
                String country = info.get("country").toString();
                String region = info.get("region").toString();
                String city = info.get("city").toString();
                
                System.out.println(uidStr + " " + ipStr + " " + country + " " + region + " " + city);
                updateInfoByIp(country,region,city,uidStr);
                Thread.sleep(10000);
            }
            System.out.println("处理完了！！！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    //根据ip地址获取国家信息
    @SuppressWarnings("rawtypes")
    public static Map<String,Object> getCountryInfoByIp(String ip){
        String str = sendGet("http://ip.taobao.com/service/getIpInfo.php","ip=" + ip);
        JSONObject obj = JSONObject.parseObject(str);
        Map res = (Map)obj.get("data");
        
        Map<String, Object> data = new HashMap<>();
        
        data.put("country", res.get("country"));
        data.put("region", res.get("region"));
        data.put("city", res.get("city"));
        return data;
    }
    
    public static String sendGet(String url, String param) {
        String result = "";
        String urlName = url + "?" + param;
        try {
            URL realURL = new URL(urlName);
            URLConnection conn = realURL.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String s : map.keySet()) {
                System.out.println(s + "-->" + map.get(s));
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //根据ip修改国家信息
    public static void updateInfoByIp(String country,String region,String city,String uid) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "update user set country=?,region=?,city=? where uid=?";
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, country);
            preparedStatement.setString(2, region);
            preparedStatement.setString(3, city);
            preparedStatement.setInt(4, Integer.parseInt(uid));
            
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public static Connection getConnection() throws SQLException {
        String driverName = "com.mysql.cj.jdbc.Driver";
        Connection conn = null;
        
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(
                "jdbc:mysql://gravityvalidator.mysql.database.azure.com:3306/marcopay?characterEncoding=utf-8&serverTimezone=Asia/Shanghai", 
                "marcopoloadmin@marcopolodata", 
                "M27&21!9P*&^2A");
            conn.setAutoCommit(true);//支持事物
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement stmt){
        try {
            if(stmt != null){
                stmt.close();
            }
            stmt = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
            rs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
