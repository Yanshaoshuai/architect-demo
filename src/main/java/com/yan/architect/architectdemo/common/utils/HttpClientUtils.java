package com.yan.architect.architectdemo.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: amber
 * Date: 2018-05-15
 * Time: 17:40
 */
public class HttpClientUtils
    {
        private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 日志记录

        private static RequestConfig requestConfig = null;
        
        private static CloseableHttpClient httpClient;

        static
        {
            try {
                SSLContext sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
                RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
                httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 设置请求和传输超时时间
            requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        }

        /**
         * post请求传输json参数
         * @param url  url地址
         * @param jsonParam json参数
         * @return
         */
        public static JSONObject httpPost(String url, JSONObject jsonParam)
        {
            // post请求返回结果
            JSONObject jsonResult = null;
            HttpPost httpPost = new HttpPost(url);
            // 设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            try
            {
                if (null != jsonParam)
                {
                    // 解决中文乱码问题
                    StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/json");
                    httpPost.setEntity(entity);
                }
                CloseableHttpResponse result = httpClient.execute(httpPost);
                // 请求发送成功，并得到响应
                if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String str = "";
                    try
                    {
                        // 读取服务器返回过来的json字符串数据
                        str = EntityUtils.toString(result.getEntity(), "utf-8");
                        // 把json字符串转换成json对象
                        jsonResult = JSONObject.parseObject(str);
                    }
                    catch (Exception e)
                    {
                        logger.error("post请求提交失败:" + url, e);
                    }
                }
            }
            catch (IOException e)
            {
                logger.error("post请求提交失败:" + url, e);
            }
            finally
            {
                httpPost.releaseConnection();
            }
            return jsonResult;
        }

        /**
         * post请求传输String参数 例如：name=Jack&sex=1&type=2
         * Content-type:application/x-www-form-urlencoded
         * @param url            url地址
         * @param strParam       参数
         * @return
         */
        public static JSONObject httpPost(String url, String strParam)
        {
            // post请求返回结果
            JSONObject jsonResult = null;
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            try
            {
                if (null != strParam)
                {
                    // 解决中文乱码问题
                    StringEntity entity = new StringEntity(strParam, "utf-8");
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/x-www-form-urlencoded");
                    httpPost.setEntity(entity);
                }
                CloseableHttpResponse result = httpClient.execute(httpPost);
                // 请求发送成功，并得到响应
                if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String str = "";
                    try
                    {
                        // 读取服务器返回过来的json字符串数据
                        str = EntityUtils.toString(result.getEntity(), "utf-8");
                        // 把json字符串转换成json对象
                        jsonResult = JSONObject.parseObject(str);
                    }
                    catch (Exception e)
                    {
                        logger.error("post请求提交失败:" + url, e);
                    }
                }
            }
            catch (IOException e)
            {
                logger.error("post请求提交失败:" + url, e);
            }
            finally
            {
                httpPost.releaseConnection();
            }
            return jsonResult;
        }

        public static String httpPostGetStr(String url, String strParam){
            // post请求返回结果
            JSONObject jsonResult = null;
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            try
            {
                if (null != strParam)
                {
                    // 解决中文乱码问题
                    StringEntity entity = new StringEntity(strParam, "utf-8");
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/x-www-form-urlencoded");
                    httpPost.setEntity(entity);
                }
                CloseableHttpResponse result = httpClient.execute(httpPost);
                // 请求发送成功，并得到响应
                if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String str = "";
                    try
                    {
                        // 读取服务器返回过来的json字符串数据
                        str = EntityUtils.toString(result.getEntity(), "utf-8");
                        return str;
                    }
                    catch (Exception e)
                    {
                        logger.error("post请求提交失败:" + url, e);
                    }
                }
            }
            catch (IOException e)
            {
                logger.error("post请求提交失败:" + url, e);
            }
            finally
            {
                httpPost.releaseConnection();
            }
            return null;
        }

        /**
         * 发送get请求
         * @param url 路径
         * @return
         */
        public static JSONObject httpGet(String url)
        {
            // get请求返回结果
            JSONObject jsonResult = null;
            // 发送get请求
            HttpGet request = new HttpGet(url);
            request.setConfig(requestConfig);
            try
            {
                CloseableHttpResponse response = httpClient.execute(request);

                // 请求发送成功，并得到响应
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    // 读取服务器返回过来的json字符串数据
                    HttpEntity entity = response.getEntity();
                    String strResult = EntityUtils.toString(entity, "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(strResult);
                }
                else
                {
                    logger.error("get请求提交失败:" + url);
                }
            }
            catch (IOException e)
            {
                logger.error("get请求提交失败:" + url, e);
            }
            finally
            {
                request.releaseConnection();
            }
            return jsonResult;
        }

}
