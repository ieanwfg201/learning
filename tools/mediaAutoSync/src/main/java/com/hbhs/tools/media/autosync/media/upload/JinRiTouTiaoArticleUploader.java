package com.hbhs.tools.media.autosync.media.upload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.upload.parameter.JinRiTouTiaoParameterGenerator;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;
import com.hbhs.tools.media.autosync.media.utils.ImageTypeEnum;
import lombok.Getter;
import org.apache.commons.httpclient.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class JinRiTouTiaoArticleUploader extends AbstractArticleUploader {
    private final static Logger LOG = LoggerFactory.getLogger(JinRiTouTiaoArticleUploader.class);

    public JinRiTouTiaoArticleUploader(MediaConfigEntity.MediaConfigInfos mediaConfig) {
        super(mediaConfig);
    }

    @Override
    void prepareParametersBeforeUploadCreative(ArticleInfos.CreativeInfos creative, List<Cookie> cookieList,
                                               List<HttpRequestUtils.KeyValuePair> headerList,
                                               List<HttpRequestUtils.KeyValuePair> parameterList,
                                               List<HttpRequestUtils.FileValuePair> fileList) {
        if (creative == null || StringUtils.isEmpty(creative.getLocalFilePath())) return;
        File localFile = new File(creative.getLocalFilePath());
        if (!localFile.exists() || localFile.isDirectory())
            return;
        // upload file
        fileList.add(new HttpRequestUtils.FileValuePair("upfile", localFile));
        // other form parameters
        parameterList.add(new HttpRequestUtils.KeyValuePair("id", "WU_FILE_0"));
        parameterList.add(new HttpRequestUtils.KeyValuePair("name", localFile.getName()));
        parameterList.add(new HttpRequestUtils.KeyValuePair("type", ImageTypeEnum.getContextTypeByFileName(localFile.getName())));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(localFile.lastModified());
        parameterList.add(new HttpRequestUtils.KeyValuePair("lastModifiedDate", new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(cal.getTime())));
        long fileSize = 0;
        try {
            fileSize = Files.size(localFile.toPath());
        } catch (Exception e) {
        }
        parameterList.add(new HttpRequestUtils.KeyValuePair("size", fileSize + ""));
    }

    @Override
    void cleanResultAfterUploadCreative(ArticleInfos.CreativeInfos creative, HttpRequestUtils.HttpResponse response) {
        if (response == null || !response.success()) {
            LOG.error("Failed upload creative: {} into media: {}, response infos:{}",
                    creative.getOriginalCreativeID(), getUploadMedia(), response);
            return;
        }
        String jsonFormat = response.getContent();
        try {
            JsonNode node = new ObjectMapper().readTree(jsonFormat);
            creative.setUploadCreativeUrl(node.get("url").asText());
            creative.setUploadCreativeID(node.get("original").asText());
        } catch (Exception e) {
        }
    }

    @Override
    void prepareBeforeUploadArticle(ArticleInfos article, MediaConfigEntity.MediaConfigInfos media, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList, List<HttpRequestUtils.KeyValuePair> parameterList) {
        parameterList.addAll(JinRiTouTiaoParameterGenerator.generateSaveAsDraft(article));
    }

    @Override
    void cleanupAfterUploadArticle(ArticleInfos article, HttpRequestUtils.HttpResponse response) {
        if (response.getCode() == HttpRequestUtils.HttpResponse.CODE_SUCCESS &&
                response.getStatusCode() == 200) {
            LOG.debug("Success to upload article:{} to media: {}", article.getOriginalMediaID(), getUploadMedia());
        } else {
            LOG.error("Failed to upload article:{} to media: {}, response data is:{}, parameters as follow", article.getOriginalMediaID(), getUploadMedia(), response);
        }
    }
}
