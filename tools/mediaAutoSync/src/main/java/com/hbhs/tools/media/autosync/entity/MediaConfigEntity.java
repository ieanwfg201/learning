package com.hbhs.tools.media.autosync.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.*;

@Getter@Setter
public class MediaConfigEntity {
    private List<String> supportMediaList = new ArrayList<>();
    private List<String> mediaFromList = new ArrayList<>();
    private List<String> mediaToList = new ArrayList<>();
    private String userAgent;
    private Map<String, MediaConfigInfos> mediaMap = new TreeMap<>();

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Below is the media configuration infos. \n");
        str.append("  |- Support media: ").append(Arrays.toString(supportMediaList.toArray())).append("\n");
        str.append("  |- Default useragent: ").append(userAgent).append("\n");
        str.append("  |- Media from: ").append(mediaFromList).append(", and to: ").append(Arrays.toString(mediaToList.toArray())).append("\n");
        str.append("  |- Media configs information as follows \n");
        for (MediaConfigInfos mediaConfigInfos : mediaMap.values()) {
            str.append(mediaConfigInfos.stringValue("       ")).append("\n");
        }
        return str.toString();
    }
    public MediaConfigInfos getMediaConfig(String media){
        if (StringUtils.isEmpty(media)) return null;
        return mediaMap.get(media.trim());
    }
    @Getter
    @Setter
    public static class MediaConfigInfos {
        private String media;
        private String username = "";
        private String password = "";
        private String host = "";
        private String cookieStr = "";
        private String origin = "";
        private String userAgent = "";
        private String downloadArticleListUrl = "";
        private String downloadArticleDetailUrl = "";
        private String downloadCreativeUrl = "";
        private String uploadCreativeUrl = "";
        private String uploadArticleAsPublishUrl = "";
        private String uploadArticleAsDraftUrl = "";
        private int uploadArticleMaxCount;

        public MediaConfigInfos(String media){setMedia(media);}
        public String stringValue(String prefix) {
            if (prefix == null) prefix = "";
            StringBuilder str = new StringBuilder();
            str.append(prefix).append("Media: ").append(media).append(" configuration as follows \n");

            str.append(prefix).append("  |- ").append("username: ").append(username).append(", password: ")
                    .append(password).append(", cookies: ").append(cookieStr).append("\n");
            str.append(prefix).append("  |- ").append("Useragent: ").append(userAgent).append(", Origin: ")
                    .append(origin).append("\n");
            str.append(prefix).append("  |- ").append("download.article.list.url: ").append(downloadArticleListUrl).append("\n");
            str.append(prefix).append("     ").append("download.article.detail.url: ").append(downloadArticleDetailUrl).append("\n");
            str.append(prefix).append("     ").append("download.creative.url: ").append(downloadCreativeUrl).append("\n");
            str.append(prefix).append("  |- ").append("upload.creative.url: ").append(uploadCreativeUrl).append("\n");
            str.append(prefix).append("     ").append("upload.article.as.published.url: ").append(uploadArticleAsPublishUrl).append("\n");
            str.append(prefix).append("     ").append("upload.article.as.draft.url: ").append(uploadArticleAsDraftUrl);
            return str.toString();
        }

        public boolean mediaCanUpload(){
            if ((StringUtils.isEmpty(username)||StringUtils.isEmpty(password))&&
                    StringUtils.isEmpty(cookieStr)){
                return false;
            }
            if (StringUtils.isEmpty(downloadArticleListUrl)||
                    StringUtils.isEmpty(downloadArticleDetailUrl)||
                    StringUtils.isEmpty(downloadCreativeUrl)){
                return false;
            }
            return true;
        }

        public boolean mediaCanDownload(){
            if (StringUtils.isEmpty(uploadCreativeUrl)||
                    StringUtils.isEmpty(uploadArticleAsDraftUrl)||
                    StringUtils.isEmpty(uploadArticleAsPublishUrl)){
                return false;
            }
            return true;
        }

        @Override
        public String toString(){
            return stringValue(null);
        }
    }
}
