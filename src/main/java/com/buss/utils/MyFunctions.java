package com.buss.utils;

import org.jeecgframework.core.util.ResourceUtil;

/**
 * Created by shilin on 2015/7/14.
 */
public class MyFunctions {
    public static String resourceUrl(String content) {
        String version= ResourceUtil.getConfigByName("resources.version");

        String result="";
        if(content.contains("?")){
            result=content+"&vsn="+version;
        }else{
            result=content+"?vsn="+version;
        }
        return result;
    }
}
