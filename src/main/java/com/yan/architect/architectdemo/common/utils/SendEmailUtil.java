package com.yan.architect.architectdemo.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.marcopolo.marcopoloplatform.model.EmailModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
 * 使用SMTP协议发送电子邮件
 */  
public class SendEmailUtil {  
    
    public static void main(String[] args) throws Exception {
        System.out.println("发送结果：" + sendEmail("15101049579@163.com","889900"));        
    }
    
    //发送邮箱验证码
    public static String sendEmail(String to,String verifyCode) throws Exception {
        String publicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwmHtYYGI8VknUl3Uy2TEpZkGgMKciAtQSvaucXx4W4jlfK6NGZQsDp7ztxBgfLvnD4G39LzqsxY4JximVIFI15jGXboGLZMVyN5HEwh4aFXWaWsc2DlXPNRqk7rh1w0B3tBULJ8z2mQdsyUK7MC11lvVdrHI2ZOIxVvbhfH0sCzI5TUw6zUMN0JGr0223PJszF50obVpreKUXqE5fiQ0l5ELe1AEfCe4Kn2RLMKflUmKt+MNcp5aYnC+Yx0wV/JeI539hD7BTz0RXtKJiUGCfKmQYPub6TjnClw2aFH8dGkhZPEwKLXSMW6Cw3bLPgqS6hkogrrOQn4u511qNbwUmwIDAQAB";
        String content = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'> <html xmlns='http://www.w3.org/1999/xhtml' xmlns='http://www.w3.org/1999/xhtml'> <head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8' /> <meta name='viewport' content='width=device-width, initial-scale=1.0' /> <meta content='telephone=no' name='format-detection' /> <title></title> <style type='text/css' data-premailer='ignore'> @import url(https://fonts.googleapis.com/css?family=Open+Sans:300,400,500,600,700); </style> <style data-premailer='ignore'> @media screen and (max-width: 600px) { u+.body { width: 100vw !important; } } a[x-apple-data-detectors] { color: inherit !important; text-decoration: none !important; font-size: inherit !important; font-family: inherit !important; font-weight: inherit !important; line-height: inherit !important; } </style> <!--[if mso]> <style type='text/css'> body, table, td { font-family: Arial, Helvetica, sans-serif !important; } img { -ms-interpolation-mode: bicubic; } .box { border-color: #eee !important; } </style> <![endif]--> <style> body { margin: 0; padding: 0; background-color: #f5f7fb; font-size: 15px; line-height: 160%; mso-line-height-rule: exactly; color: #444444; width: 100%; } body { font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; } a:hover { text-decoration: underline; } .img-hover:hover img { opacity: .64; } </style> </head> <body class='bg-body' style='font-size: 15px; margin: 0; padding: 0; line-height: 160%; mso-line-height-rule: exactly; color: #444444; width: 100%; font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif;' bgcolor='#f5f7fb'> <center> <table class='main bg-body' width='100%' cellspacing='0' cellpadding='0' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: 100%; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;' bgcolor='#f5f7fb'> <tr> <td align='center' valign='top' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif;'> <!--[if (gte mso 9)|(IE)]> <table border='0' cellspacing='0' cellpadding='0'> <tr> <td align='center' valign='top' width='640'> <![endif]--> <span class='preheader' style='font-size: 0; padding: 0; display: none; max-height: 0; mso-hide: all; line-height: 0; color: transparent; height: 0; max-width: 0; opacity: 0; overflow: hidden; visibility: hidden; width: 0;'>This is preheader text. Some clients will show this text as a preview.</span> <table class='wrap' cellspacing='0' cellpadding='0' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: 100%; max-width: 640px; text-align: left;'> <tr> <td class='p-sm' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding: 8px;'> <table cellpadding='0' cellspacing='0' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: 100%;'> <tr> <td class='py-lg' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding-top: 24px; padding-bottom: 24px;'> <a href='https://www.marcopolo.link' style='color: #467fcf; text-decoration: none;'><img src='https://true-static-file.s3.amazonaws.com/image/marcopolo_116b34_logo.png' width='116' height='34' alt='' style=' outline: none; text-decoration: none; vertical-align: baseline; font-size: 0; border: 0 none;' /></a> </td> </tr> </table> <div class='main-content'> <table class='box' cellpadding='0' cellspacing='0' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: 100%; border-radius: 3px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05); box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05); border: 1px solid #f0f0f0;' bgcolor='#ffffff'> <tr> <td style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif;'> <table cellpadding='0' cellspacing='0' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: 100%;'> <tr> <td style='width: 10%;'>&nbsp;</td> <td class='content' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding: 40px 8px;'> <!-- <h1 style='font-weight: 300; font-size: 28px; line-height: 130%; margin: 0 0 .5em;'>Your <strong style='font-weight: 600;'>25$</strong> promo code, Paweł!</h1> --> <p class='text-muted mb-0' style='color: #464747; margin: 0;'>Here is your verification code:</p> </td> <td style='width: 10%;'>&nbsp;</td> </tr> <tr> <td style='width: 10%;'>&nbsp;</td> <td class='content' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding: 10px 8px;'> <table cellspacing='0' cellpadding='0' class='w-auto' align='center' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: auto;'> <tr> <td class='border-dashed border-wide border-dark text-center rounded px-lg py-md' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-radius: 3px; padding: 16px 24px; border: 2px dashed #d1d1d1;' align='center'> <div class='h1 font-strong m-0' style='color: #204bae; font-weight: 600; font-size: 28px; line-height: 130%; margin: 0;'>${code}</div> <div class='text-muted font-sm' style='color: #464747; font-size: 13px;'>30 minutes effective</div> </td> </tr> </table> </td> <td style='width: 10%;'>&nbsp;</td> </tr> <tr> <td style='width: 10%;'>&nbsp;</td> <td class='content text-center' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding: 40px 8px;'> <p class='text-muted mb-0' style='color: #464747; margin: 0;'>You are logging into MarcoPay. The verification code is above. Please don't tell anyone.</p> </td> <td style='width: 10%;'>&nbsp;</td> </tr> <tr> <td style='width: 10%;'>&nbsp;</td> <td class='content text-center' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding: 40px 8px 1px 8px;border-top:#c9c7c7 solid 1px; '> <p class='text-muted mb-0' style='color: #464747; margin: 0;'>MarcoPolo Protocol Team</p> </td> <td style='width: 10%;'>&nbsp;</td> </tr> <tr> <td style='width: 10%;'>&nbsp;</td> <td class='content text-center' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding: 1px 1px 40px 8px;'> <p class='text-muted mb-0' style='color: #204bae; margin: 0;'>https://marcopolo.link</p> </td> <td style='width: 10%;'>&nbsp;</td> </tr> </table> </td> </tr> </table> </div> <table cellspacing='0' cellpadding='0' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: 100%;'> <tr style='padding-top: 28px; padding-bottom: 28px;'> <td class='py-xl' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding-top: 28px; padding-bottom: 28px;'> <table class='font-sm text-center text-muted' cellspacing='0' cellpadding='0' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; border-collapse: collapse; width: 100%; color: #464747; text-align: center; font-size: 13px;'> <tr> <td class='px-lg' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding-right: 24px; padding-left: 24px;'> If you have any questions, feel free to message us at telegram: https://t.me/MarcoPoloMAP. </td> </tr> <tr> <td class='pt-md' style='font-family: Open Sans, -apple-system, BlinkMacSystemFont, Roboto, Helvetica Neue, Helvetica, Arial, sans-serif; padding-top: 16px;'> You are receiving this email because you have bought or downloaded one of the Marcopay APP. </td> </tr> </table> </td> </tr> </table> </td> </tr> </table> <!--[if (gte mso 9)|(IE)]> </td> </tr> </table> <![endif]--> </td> </tr> </table> </center> </body> </html>";
        String title = "Your verification code is: ${code}";
        
        HttpPost httpPost = new HttpPost("https://email.truedapp.net/service/email/send");
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        
        EmailModel email = new EmailModel();
        
        try {
            email.setToAddress(Base64.encodeBase64String(RSAUtils.encryptByPublicKey(to,publicKey)));
            email.setVerifyCode(Base64.encodeBase64String(RSAUtils.encryptByPublicKey(verifyCode,publicKey)));
            email.setContent(content);
            email.setTitle(title);
            email.setSourceType("马可波罗2.0");
            email.setSender("Macropolo");
            email.setFlag("exmac");
            
            String jsonstr = JSON.toJSONString(email);
            
            StringEntity se = new StringEntity(jsonstr,"UTF-8");
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(httpPost);
            
            if(response != null && response.getStatusLine().getStatusCode() == 200) {
                String res = EntityUtils.toString(response.getEntity());
                 
                JSONObject obj = JSONObject.parseObject(res);
                System.out.println("res:" + obj.toString());
                
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
