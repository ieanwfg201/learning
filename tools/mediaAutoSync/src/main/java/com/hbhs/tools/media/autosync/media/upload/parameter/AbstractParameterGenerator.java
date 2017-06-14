package com.hbhs.tools.media.autosync.media.upload.parameter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;

public abstract class AbstractParameterGenerator implements IParameterGenerator {
    private static final String DEFAULT_ENCODER = "UTF-8";
    @Getter@Setter
    private String encode = DEFAULT_ENCODER;
    public String urlEncode(String content){
        if (!StringUtils.isEmpty(content)){
            try {
                content = URLEncoder.encode(content, encode);
            }catch (Exception e){}
        }
        return content;
    }
}
