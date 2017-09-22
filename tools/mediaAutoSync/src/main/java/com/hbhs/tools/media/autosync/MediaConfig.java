package com.hbhs.tools.media.autosync;

import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Properties;

@Configuration
@PropertySource(
        value = {"classpath:media.properties", "file:${external_conf}/media.properties"},
        ignoreResourceNotFound = true
)
public class MediaConfig {
    private static final Logger LOG = LoggerFactory.getLogger(MediaConfig.class);
    private static final String SPLIT_FIELD = ",";
    private static final String EXTEND_FIELD = ".extend.";

    @Bean
    public MediaConfigEntity mediaConfigEntity() {
        Properties props = new Properties();
        try {
            props.load(MediaConfig.class.getClassLoader().getResourceAsStream("media.properties"));
            MediaConfigEntity entity = new MediaConfigEntity();
            if (props.get("media.support.list")!=null) {
                String[] valueArray = props.get("media.support.list").toString().split(SPLIT_FIELD);
                for (String value : valueArray) {
                    value = value.trim();
                    if (!StringUtils.isEmpty(value) && !entity.getSupportMediaList().contains(value)) {
                        entity.getSupportMediaList().add(value);
                    }
                }
            }

            if (props.get("media.source.from")!=null) {
                String[] valueArray = props.get("media.source.from").toString().split(SPLIT_FIELD);
                for (String value : valueArray) {
                    value = value.trim();
                    if (!StringUtils.isEmpty(value) && !entity.getSupportMediaList().contains(value)) {
                        entity.getMediaFromList().add(value);
                    }
                }
            }

            if (props.get("media.target.to")!=null) {
                String[] valueArray = props.get("media.target.to").toString().split(SPLIT_FIELD);
                for (String value : valueArray) {
                    value = value.trim();
                    if (!StringUtils.isEmpty(value) && !entity.getSupportMediaList().contains(value)) {
                        entity.getMediaFromList().add(value);
                    }
                }
            }

            for (String media : entity.getSupportMediaList()) {
                MediaConfigEntity.MediaConfigInfos config = mediaConfigInfos(media, props);
                if (config != null) entity.getMediaMap().put(config.getMedia(), config);
            }
            setExtendProperties(props, entity);
            LOG.info("Success loading media infos. details is as follow: \n{}", entity.toString());
            return entity;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error loading media configurations. please checking if 'media.properties' is under classpath", e);
        }
    }

    private MediaConfigEntity.MediaConfigInfos mediaConfigInfos(String media, Properties props) {
        if (StringUtils.isEmpty(media) || props == null) return null;
        MediaConfigEntity.MediaConfigInfos infos = new MediaConfigEntity.MediaConfigInfos(media);
        String key = media + ".username";
        if (props.get(key)!=null) infos.setUsername(props.get(key).toString().trim());
        key = media + ".password";
        if (props.get(key)!=null) infos.setUsername(props.get(key).toString().trim());
        key = media + ".cookies";
        if (props.get(key)!=null) infos.setCookieStr(props.get(key).toString().trim());
        key = media + ".download.article.list.url";
        if (props.get(key)!=null) infos.setDownloadArticleListUrl(props.get(key).toString().trim());
        key = media + ".download.article.detail.url";
        if (props.get(key)!=null) infos.setDownloadArticleDetailUrl(props.get(key).toString().trim());
        key = media + ".download.creative.url";
        if (props.get(key)!=null) infos.setDownloadCreativeUrl(props.get(key).toString().trim());
        key = media + ".upload.creative.url";
        if (props.get(key)!=null) infos.setUploadCreativeUrl(props.get(key).toString().trim());
        key = media + ".upload.article.upload.draft";
        if (props.get(key)!=null) infos.setUploadArticleAsDraftUrl(props.get(key).toString().trim());
        key = media + ".upload.article.upload.published";
        if (props.get(key)!=null) infos.setUploadArticleAsPublishUrl(props.get(key).toString().trim());

        return infos;
    }

    private void setExtendProperties(Properties props, MediaConfigEntity entity){
        for (Object o : props.keySet()) {
            if (o==null||StringUtils.isEmpty(o.toString())) continue;
            String key = o.toString();

            if (key.contains(EXTEND_FIELD)){
                int index = key.indexOf(EXTEND_FIELD);
                String media = key.substring(0, index);
                String extendKey = key.substring(index+EXTEND_FIELD.length());
                if (entity.getMediaMap().containsKey(media)){
                    entity.getMediaMap().get(media).getExtendMap().put(extendKey, props.get(key).toString());
                }
            }
        }
    }

}
