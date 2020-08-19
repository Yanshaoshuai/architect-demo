package com.yan.architect.architectdemo.common.utils;

import com.marcopolo.marcopoloplatform.common.utils.CommonUtil;
import com.marcopolo.marcopoloplatform.common.utils.DateUtils;
import com.marcopolo.marcopoloplatform.config.AppProperties;

import java.sql.*;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DBConfig {
    //测试连接
    public static void main2(String[] args) throws ParseException {
        for(int i=0;i<2000;i++){
            String lotterCode = CommonUtil.getRandomString(8);
            String updateTime = DateUtils.getTimeStampStr(new Date());
            insertLotteryCode(lotterCode,updateTime);
            
            /*try {
                FileWriter fw = new FileWriter("E:/sql.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append("insert into candy_lottery_code(lottery_code,update_time) values ('" + lotterCode + "','" + updateTime + "'); \n");
                bw.close();
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        System.out.println("输入完成");
      
        /*int number = 1;
        for(int i=1;i<15;i++){
            if(i % 7 == 0){
                number ++;
                System.out.println(number-1);
            }
        }*/
        
        //初始化日日奖信息
        /*int number = 0;
        for(int i=0;i<365;i++){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = new Date();
            Calendar c = Calendar.getInstance();  
            c.setTime(sDate);  
            c.add(Calendar.DAY_OF_MONTH, i);
            
            sDate = c.getTime();
            String insertTime = sdf.format(sDate);
            
            if(i % 7 == 0){
                number ++;
            }
            
            insertLotteryCycle(number,insertTime,1);
        }*/
        
        //初始化周周奖信息
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate2 = new Date();
        for(int i=0;i<54;i++){
            Date sDate = sDate2;
            Calendar c = Calendar.getInstance();  
            c.setTime(sDate);  
            c.add(Calendar.DAY_OF_MONTH, 6);
            
            sDate = c.getTime();
            String startDate = sdf.format(sDate2);
            String endDate = sdf.format(sDate);
            
            insertLotteryCycle2((i+1),startDate,endDate,2);
            
            sDate2 = sDate;
            Calendar c1 = Calendar.getInstance();  
            c1.setTime(sDate2);  
            c1.add(Calendar.DAY_OF_MONTH, 1);
            sDate2 = c1.getTime();
        }*/
    }
 
    public static void main(String[] args) throws ParseException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "select * from transaction_detail where txhash = ? limit 0,1";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "111");
            resultSet = preparedStatement.executeQuery();

            String minttxhash = null;
            while (resultSet.next()) {
                minttxhash = resultSet.getString("minttxhash");
            }
            minttxhash = (null == minttxhash) ? "0" : "1";
            System.out.print(minttxhash);
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
    
    //根据交易Hash查询状态
    public static String queryStatusByHash(String transHash,int type) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "";
            if(type == 1){
                sql = "select * from transaction_detail where txhash = ? limit 0,1";
            }else{
                sql = "select * from unlock_detail where txhash = ? limit 0,1";
            }
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transHash);
            resultSet = preparedStatement.executeQuery();

            String minttxhash = null;
            while (resultSet.next()) {
                if(type == 1){
                    minttxhash = resultSet.getString("minttxhash");
                }else{
                    minttxhash = resultSet.getString("unlocktxhash");
                }
            }
            minttxhash = (null == minttxhash) ? "0" : "1";
            return minttxhash;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
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
    
    public static int insertLotteryCycle2(int number,String startDate,String endDate,int cycle_type){
        Connection conn = null;
        PreparedStatement pstm = null;
        
        int nums = 0;
        try {
            conn = getConnection();
            String sql = "insert into candy_lottery_cycle(number, start_date,end_date, cycle_type) values (?,?,?,?)";
            
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, number);
            pstm.setString(2, startDate);
            pstm.setString(3, endDate);
            pstm.setInt(4, cycle_type);
            
            pstm.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeStatement(pstm);
            closeConnection(conn);
        }
        return nums;
    }
    
    public static int insertLotteryCycle(int number,String daily,int cycle_type){
        Connection conn = null;
        PreparedStatement pstm = null;
        
        int nums = 0;
        try {
            conn = getConnection();
            String sql = "insert into candy_lottery_cycle(number, daily, cycle_type) values (?,?,?)";
            
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, number);
            pstm.setString(2, daily);
            pstm.setInt(3, cycle_type);
            
            pstm.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeStatement(pstm);
            closeConnection(conn);
        }
        return nums;
    }
    
    public static int insertLotteryCode(String lotterCode,String updateTime){
        Connection conn = null;
        PreparedStatement pstm = null;
        
        int nums = 0;
        try {
            conn = getConnection();
            String sql = "insert into candy_lottery_code(lottery_code,update_time) values (?,?)";
            
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, lotterCode);
            pstm.setString(2, updateTime);
            
            pstm.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeStatement(pstm);
            closeConnection(conn);
        }
        return nums;
    }
    
    public static Connection getConnection() throws SQLException {
        String driverName = "com.mysql.cj.jdbc.Driver";
        Connection conn = null;
        
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(AppProperties.MYSQL_URL, AppProperties.MYSQL_USERNAME, AppProperties.MYSQL_PASSWORD);
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
