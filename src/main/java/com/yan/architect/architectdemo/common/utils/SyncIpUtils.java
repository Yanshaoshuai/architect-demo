package com.yan.architect.architectdemo.common.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SyncIpUtils {
    public static Map<String,Object> syncIp(String ipAddr) throws Exception {
        Map<String, Object> res = new HashMap<String, Object>();
        
        try {
            //判断是否为内网IP
            /*Pattern reg = Pattern.compile("^(127\\.0\\.0\\.1)|(localhost)|(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(172\\.((1[6-9])|(2\\d)|(3[01]))\\.\\d{1,3}\\.\\d{1,3})|(192\\.168\\.\\d{1,3}\\.\\d{1,3})$");
            Matcher match = reg.matcher(ipAddr);

            if(match.find()){
                res.put("country", "内网IP");
                res.put("city", "内网IP");
                return res;
            }*/
            
            InetAddress ipAddress = InetAddress.getByName(ipAddr);
            //国家
            ClassPathResource resourceCountry = new ClassPathResource("GeoLite2-Country.mmdb");
            InputStream isCountry = resourceCountry.getInputStream();
            
            DatabaseReader reader = new DatabaseReader.Builder(isCountry).build();
            CountryResponse response = reader.country(ipAddress);

            Country country = response.getCountry();
            String countryInfo = country.getNames().get("zh-CN");
            
            //城市
            ClassPathResource resourceCity = new ClassPathResource("GeoLite2-City.mmdb");
            InputStream isCity = resourceCity.getInputStream();
            
            DatabaseReader readerCity = new DatabaseReader.Builder(isCity).build();
            CityResponse cityResponse = readerCity.city(ipAddress);
            City city = cityResponse.getCity();
            String cityInfo = city.getNames().get("zh-CN");
            
            res.put("country", countryInfo);
            res.put("city", cityInfo);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("country", "未知国家");
            res.put("city", "未知城市");
        }
        
        return res;
    }
}
