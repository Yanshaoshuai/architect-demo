package com.yan.architect.architectdemo.common.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: amber
 * Date: 2018-05-17
 * Time: 10:51
 */
@Configuration
public class AppInterceptors extends WebMvcConfigurerAdapter {

    public static String REQUEST_TIME = "http_request_time";
    

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        		.allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders(CorsConfiguration.ALL)
                //.allowedHeaders("Token", "Agent", "Content-Type", "Access-Control-Allow-Origin", "origin", "accept", "x-requested-with")
                .allowCredentials(false);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
                return true;
                /*String ipAddress = IPUtils.getRealIP(request);
                
                //2C服务，校验ip地址是否在黑名单中
                ApiService apiService = (ApiService) ApplicationContextProvider.getBean(ApiService.class);
                int count = 0;
                
                String blackRes = stringRedisTemplate.opsForValue().get("blackRes");
                if(null == blackRes){
                    String blackResValue = "";
                    List<IpBlackList> list = apiService.queryIpBlackList();
                    for(IpBlackList ip : list){
                        blackResValue = blackResValue.concat(ip.getIp()).concat(",");
                    }
                    stringRedisTemplate.opsForValue().set("blackRes", blackResValue, 30, TimeUnit.DAYS);
                }
                
                //判断是否在黑名单列表中
                if(null != blackRes && blackRes.contains(ipAddress)){
                    count = 1;
                }
                
                if (count > 0) {
                    response.getWriter().append("403 BlackList");
                    return false;
                } else {
                    // 2C服务，限制多少分钟内只能访问多少次，否则加入黑名单
                    String limitSeconds = stringRedisTemplate.opsForValue().get("limitSeconds");
                    String limitTimes = stringRedisTemplate.opsForValue().get("limitTimes");
                   
                    if(null == limitSeconds || null == limitTimes){
                        limitSeconds = apiService.getvaleByKey("limit_seconds");
                        limitTimes = apiService.getvaleByKey("limit_times");
                        
                        stringRedisTemplate.opsForValue().set("limitSeconds", limitSeconds, 365, TimeUnit.DAYS);
                        stringRedisTemplate.opsForValue().set("limitTimes", limitTimes, 365, TimeUnit.DAYS);
                    }
                    String res = stringRedisTemplate.opsForValue().get(ipAddress);
                    
                    // 第一次请求
                    if (null == res) {
                        //放入到redis中
                        String redisValue = String.valueOf(System.currentTimeMillis()).concat(",").concat("1");
                        stringRedisTemplate.opsForValue().set(ipAddress, redisValue, 60, TimeUnit.MINUTES);
                        
                        return true;
                    } else {
                        String[] resArr = res.split(",");
                        Long createTime = Long.parseLong(resArr[0]);
                        Long chae = (System.currentTimeMillis() - createTime) / 1000;
                        
                        //当前时间离上一次请求时间大于60秒，可以直接通过,保存这次的请求
                        if (chae > Long.parseLong(limitSeconds)) {
                            String redisValue = String.valueOf(System.currentTimeMillis()).concat(",").concat("1");
                            stringRedisTemplate.opsForValue().set(ipAddress, redisValue, 60, TimeUnit.MINUTES);
                            
                            return true;
                        } else {
                            // 小于60秒，并且60秒之内请求了10次，返回提示 
                            Integer counts = Integer.parseInt(resArr[1]);
                            
                            if (counts >= Integer.parseInt(limitTimes)) {
                                response.getWriter().append("403 Forbidden");
                                return false;
                            } else {
                                // 小于60秒，但请求数小于10次，给对象添加
                                counts++;
                                String redisValue = String.valueOf(System.currentTimeMillis()).concat(",").concat(String.valueOf(counts));
                                stringRedisTemplate.opsForValue().set(ipAddress, redisValue, 60, TimeUnit.MINUTES);
                                return true;
                            }
                        }
                    }
                }*/
            }
        }).addPathPatterns("/api/**","/auth/**");
    }
    
    @SuppressWarnings("deprecation")
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("10MB"); //KB,MB
        /// 设置总上传数据总大小
        //factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
