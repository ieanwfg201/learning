package com.hbhs.tools.media.autosync.media.upload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import com.hbhs.tools.media.autosync.media.IAritcleUploader;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.upload.parameter.IParameterGenerator;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.httpclient.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractArticleUploader implements IAritcleUploader {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractArticleUploader.class);
    private static final String DEFAULT_ENCODE = "UTF-8";
    @Getter
    @Setter
    private String encode = DEFAULT_ENCODE;
    @Getter
    private MediaConfigEntity.MediaConfigInfos mediaConfig;

    public String getUploadMedia() {
        return mediaConfig.getMedia();
    }

    public String getUploadCreativeUrl() {
        return mediaConfig.getUploadCreativeUrl();
    }

    public String getUploadArticleAsDraftUrl() {
        return mediaConfig.getUploadArticleAsDraftUrl();
    }

    public AbstractArticleUploader(MediaConfigEntity.MediaConfigInfos mediaConfig) {
        if (mediaConfig == null)
            throw new RuntimeException("Init Article uploader faield, reason: null parameter.");
        this.mediaConfig = mediaConfig;
        if (!mediaConfig.mediaCanDownload())
            throw new RuntimeException("Current media: " + getUploadMedia() + " cannot upload articles, maybe because parameter is empty");
    }

    @Override
    public void uploadArticle(List<ArticleInfos> articleList) {
        if (articleList == null || articleList.size() == 0) {
            LOG.debug("No articles found, no need upload articles");
            return;
        }

        List<Cookie> cookieList = initCookie(getMediaConfig());
        if (cookieList == null) cookieList = new ArrayList<>();
        LOG.debug("Success init cookies for media:{}, cookie size: {}", getUploadMedia(), cookieList.size());

        List<HttpRequestUtils.KeyValuePair> headerList = initHeader();
        if (headerList == null) headerList = new ArrayList<>();
        LOG.debug("Success init header for media:{}, header size: {}", getUploadMedia(), headerList.size());

        if (!validateParamsBeforeUploading(cookieList)) {
            LOG.debug("Failed validating parameters for media: {}, so skip uploading...");
            return;
        }

        for (ArticleInfos article : articleList) {
            LOG.info("Start upload article: {} from media: {} to media: {}", article.getOriginalMediaID(),
                    article.getOriginalMedia(), getUploadMedia());
            uploadCreative(article, cookieList, headerList);
            LOG.debug("Success upload article: {} creative to media: {}, total creative size: {}", article.getOriginalMediaID(),
                    getUploadMedia(), article.getCreativeMap().size());
            uploadArticle(article, cookieList, headerList);
            LOG.info("End upload article: {} from media: {} to media: {}, response id: {}", article.getOriginalMediaID(),
                    article.getOriginalMedia(), getUploadMedia(), null);
        }
    }

    protected List<Cookie> initCookie(MediaConfigEntity.MediaConfigInfos mediaConfig){return null;}

    protected List<HttpRequestUtils.KeyValuePair> initHeader() {
        List<HttpRequestUtils.KeyValuePair> headerList = new ArrayList<>();
        if (!StringUtils.isEmpty(mediaConfig.getOrigin()))
            headerList.add(new HttpRequestUtils.KeyValuePair("Origin", getMediaConfig().getOrigin()));
        if (!StringUtils.isEmpty(mediaConfig.getCookieStr()))
            headerList.add(new HttpRequestUtils.KeyValuePair("Cookie", getMediaConfig().getCookieStr()));
        if (!StringUtils.isEmpty(mediaConfig.getHost()))
            headerList.add(new HttpRequestUtils.KeyValuePair("Host", getMediaConfig().getHost()));
        if (!StringUtils.isEmpty(mediaConfig.getUserAgent()))
            headerList.add(new HttpRequestUtils.KeyValuePair("User-Agent", getMediaConfig().getUserAgent()));
        return headerList;
    }

    protected boolean validateParamsBeforeUploading(List<Cookie> cookieList) {
        return true;
    }

    private void uploadCreative(ArticleInfos article, List<Cookie> cookieList,
                                List<HttpRequestUtils.KeyValuePair> headerList) {
        if (article == null || article.getCreativeMap() == null || article.getCreativeMap().size() == 0) return;
        for (Map.Entry<String, ArticleInfos.CreativeInfos> creativeEntry : article.getCreativeMap().entrySet()) {
            if (creativeEntry == null || creativeEntry.getValue() == null ||
                    StringUtils.isEmpty(creativeEntry.getValue().getLocalFilePath()))
                continue;
            String localFilePath = creativeEntry.getValue().getLocalFilePath();
            File localFile = new File(localFilePath);
            if (!localFile.exists() || localFile.isDirectory())
                continue;
            List<HttpRequestUtils.KeyValuePair> parameterList = new ArrayList<>();
            List<HttpRequestUtils.FileValuePair> fileList = new ArrayList<>();

            prepareParametersBeforeUploadCreative(creativeEntry.getValue(), cookieList, headerList,
                    parameterList, fileList);

            HttpRequestUtils.HttpResponse response = HttpRequestUtils.uploadFile(
                    getUploadCreativeUrl(), cookieList, headerList, parameterList, fileList
            );

            cleanResultAfterUploadCreative(creativeEntry.getValue(), response);
        }
    }

    abstract void prepareParametersBeforeUploadCreative(ArticleInfos.CreativeInfos creative, List<Cookie> cookieList,
                                                        List<HttpRequestUtils.KeyValuePair> headerList,
                                                        List<HttpRequestUtils.KeyValuePair> parameterList,
                                                        List<HttpRequestUtils.FileValuePair> fileList);

    abstract void cleanResultAfterUploadCreative(ArticleInfos.CreativeInfos creative, HttpRequestUtils.HttpResponse response);

    private void uploadArticle(ArticleInfos article, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList) {
        if (article == null || StringUtils.isEmpty(article.getArticalName()) || StringUtils.isEmpty(article.getContent()))
            return;
        replaceImageUrlBeforeUploading(article);
        List<HttpRequestUtils.KeyValuePair> parameterList = new ArrayList<>();

        prepareBeforeUploadArticle(article, mediaConfig, cookieList, headerList, parameterList);

        HttpRequestUtils.HttpResponse response = HttpRequestUtils.post(getUploadArticleAsDraftUrl(), cookieList, headerList, parameterList);

        cleanupAfterUploadArticle(article, response);
    }

    private void replaceImageUrlBeforeUploading(ArticleInfos article) {
        if (StringUtils.isEmpty(article.getContent()) || article.getCreativeMap().size() == 0) return;
        String content = article.getContent();
        for (Map.Entry<String, ArticleInfos.CreativeInfos> entry : article.getCreativeMap().entrySet()) {
            content = content.replaceFirst(entry.getValue().getOriginalCreativeID(),
                    entry.getValue().getUploadCreativeUrl());
        }
        article.setUploadContent(content);
    }

    abstract void prepareBeforeUploadArticle(ArticleInfos article, MediaConfigEntity.MediaConfigInfos media,
                                             List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList,
                                             List<HttpRequestUtils.KeyValuePair> parameterList);

    abstract void cleanupAfterUploadArticle(ArticleInfos article, HttpRequestUtils.HttpResponse response);
}
