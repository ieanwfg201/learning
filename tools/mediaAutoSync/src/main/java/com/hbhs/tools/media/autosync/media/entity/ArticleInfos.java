package com.hbhs.tools.media.autosync.media.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter@Setter
@ToString(exclude = {"content","uploadContent"})
public class ArticleInfos implements Serializable {
    private int id = 0;
    private String originalMedia;
    private String originalMediaID;
    private String originalArticleUrl;
    private String articalName;
    private String content;
    private Map<String, CreativeInfos> creativeMap = new HashMap<>();
    private Date updateDate;

    private String uploadContent;

    public void setArticalName(String articalName){
        this.articalName = articalName;
        this.id = articalName==null?0:articalName.hashCode();
    }
    @Getter@Setter@ToString
    public static class CreativeInfos implements Serializable {
        private String originalCreativeID;
        private String originalCreativeUrl;
        private String localFilePath;
        private String uploadCreativeID;
        private String uploadCreativeUrl;
    }
}
