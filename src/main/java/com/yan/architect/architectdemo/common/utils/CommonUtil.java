package com.yan.architect.architectdemo.common.utils;

import java.util.Random;

public class CommonUtil {

    /**
     * 获取指定位数的随机数
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static Boolean compareStrInScope(String object, String scope) {
        String[] scopeArr = scope.split("-");
        if (scopeArr.length != 2) {
            return false;
        }
        int first = compareAppVersion(object, scopeArr[0]);
        int second = compareAppVersion(object, scopeArr[1]);

        if (first >= 1 && second <= 1) {
            return true;
        }
        return false;
    }

    public static int compareAppVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            throw new RuntimeException("版本号不能为空");
        }
        
        // 注意此处为正则匹配，不能用.
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        
        int idx = 0;
        // 取数组最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        // 先比较长度，再比较字符
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        
        // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        
        if(diff > 0){
            return 2;
        }else if(diff == 0){
            return 1; 
        }else{
            return 0; 
        }
    }
}
