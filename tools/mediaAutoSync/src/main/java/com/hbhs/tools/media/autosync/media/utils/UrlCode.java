package com.hbhs.tools.media.autosync.media.utils;

import org.springframework.util.StringUtils;

import java.net.URLEncoder;

public class UrlCode {
    private static final String DEFAULT_ENCODER = "UTF-8";

    public static String encode(String content, String charset){
        if (StringUtils.isEmpty(charset)) charset = DEFAULT_ENCODER;
        if (!StringUtils.isEmpty(content)){
            try {
                content = URLEncoder.encode(content, DEFAULT_ENCODER);
            }catch (Exception e){}
        }
        return content;
    }

    public static String encode(String content){
        return encode(content, DEFAULT_ENCODER);
    }
}
