package com.yan.architect.architectdemo.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.marcopolo.marcopoloplatform.config.AppProperties;
import com.marcopolo.marcopoloplatform.model.SmsModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsUtils {
    private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);
    public static void main(String[] args) {
        System.out.println(sendMsg("18756936273","888999","86"));
    }

    //发送短信验证码
    public static String sendMsg(String mobile,String verifyCode,String areaCode){
//        HttpPost httpPost = new HttpPost("https://service.truedapp.net/sms2b/sms/sendMsg");
        HttpPost httpPost = new HttpPost(AppProperties.SEND_MSG_URL);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        
        String content = AppProperties.CONTENT_86;
        if(!"86".equals(areaCode)){
            if("84".equals(areaCode)){
                content = "TigViet.vn - Ma xac thuc OTP cua ban la ${code}, ma xac thuc co hieu luc trong thoi gian 5 phut.";
            }else{
                content = "【MarcoPolo】verification code: ${code}, please timely verification, do not leak to others.";
            }
        }
        
        String publicKey = AppProperties.SEND_MSG_PK;
        SmsModel sms = new SmsModel();
        
        try {
            sms.setAreaCode(Base64.encodeBase64String(
                RSAUtils.encryptByPublicKey(areaCode,publicKey)));
            sms.setMobile(Base64.encodeBase64String(
                RSAUtils.encryptByPublicKey(mobile,publicKey)));
            sms.setVerifyCode(Base64.encodeBase64String(
                RSAUtils.encryptByPublicKey(verifyCode,publicKey)));
            sms.setContent(content);
            sms.setMsgType(1);
            sms.setSourceType("TRUE App Server");
            logger.error("SmsModel--->"+sms);

            String jsonstr = JSON.toJSONString(sms);
            
            StringEntity se = new StringEntity(jsonstr,"UTF-8");
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));

            httpPost.setEntity(se);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(httpPost);

            if(response != null && response.getStatusLine().getStatusCode() == 200) {
                String res = EntityUtils.toString(response.getEntity());
                JSONObject obj = JSONObject.parseObject(res);
                System.out.println("sendMsg res:" + obj.toString());
                
                String data = obj.get("data").toString();
                JSONObject obj2 = JSONObject.parseObject(data);
                String status =obj2.get("status").toString();
                if("200".equals(obj.getString("code")) && "1".equals(status)){
                    return "1";
                }else{
                    return "0";
                }
            }else{
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
