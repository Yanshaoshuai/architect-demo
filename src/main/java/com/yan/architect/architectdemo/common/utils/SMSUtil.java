package com.yan.architect.architectdemo.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SMSUtil {
    private static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);

    //助通短信服务 群发
    public static String sendVerifyCodeSMSBy253s(String account, String password, String mobile, String content) {
        URL url = null;
        try {
            String sendUrl = "http://intapi.253.com/send";
            Map<String, String> map = new HashMap<String, String>();
            
            map.put("account",account);//API账号
            map.put("password",password);//API密码
            map.put("msg",content);//短信内容
            map.put("mobile",mobile);//手机号
            
            JSONObject js = (JSONObject) JSONObject.toJSON(map);
            
            url = new URL(sendUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(js.toString().getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //253短信服务单发
    public static String sendVerifyCodeSMSBy253(String account, String password, String mobile, String content) {
        URL url = null;
        try {
            String sendUrl = "https://intapi.253.com/send/json";
            Map<String, String> map = new HashMap<String, String>();
            
            map.put("account",account);//API账号
            map.put("password",password);//API密码
            map.put("msg",content);//短信内容
            map.put("mobile",mobile);//手机号
            
            JSONObject js = (JSONObject) JSONObject.toJSON(map);
            
            url = new URL(sendUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(js.toString().getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //助通短信 单发
    public static String sendVerifyCodeByZthy(String username, String password, String mobile, String content) {
        String url = "http://api.zthysms.com/sendSms.do";
        String tkey = SMSTimeUtil.getNowTime("yyyyMMddHHmmss");
        String xh = "";
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("sendVerifyCodeSMS异常", e);
        }
        String param = "url=" + url + "&username=" + username + "&password=" + SMSMD5Gen.getMD5(SMSMD5Gen.getMD5(password) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile + "&content=" + content + "&xh" + xh;
        logger.info("sendVerifyCodeSMS-->param:" + param);
        String ret = sendPost(url, param);
        logger.info("sendVerifyCodeSMS-->result:" + ret);
        return ret;
    }
    
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置相应请求时间
            conn.setConnectTimeout(30000);
            //设置读取超时时间
            conn.setReadTimeout(30000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("sendPost异常", e);
            return "发送 POST 请求出现异常！";
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
