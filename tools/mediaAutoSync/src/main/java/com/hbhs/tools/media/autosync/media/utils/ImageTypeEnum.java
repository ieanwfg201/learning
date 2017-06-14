package com.hbhs.tools.media.autosync.media.utils;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public enum  ImageTypeEnum {
    JPG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    JPEG("jpeg", "image/jpeg"),
    BMP("bmp", "image/bmp");

    private static final Logger LOG = LoggerFactory.getLogger(ImageTypeEnum.class);
    @Getter private String imageType;
    @Getter private String httpContextType;
    private ImageTypeEnum(String imageType, String httpContextType){
        this.imageType = imageType;
        this.httpContextType = httpContextType;
    }

    public static String getContextTypeByFileName(String fileName){
        if (StringUtils.isEmpty(fileName)) return null;
        if (fileName.contains(".")) fileName = fileName.substring(fileName.lastIndexOf(".")+1);
        fileName = fileName.trim();
        for (ImageTypeEnum current : values()) {
            if (fileName.equalsIgnoreCase(current.getImageType())){
                return current.getHttpContextType();
            }
        }
        LOG.error("No image type found for input type: {}", fileName);
        return null;
    }
}
