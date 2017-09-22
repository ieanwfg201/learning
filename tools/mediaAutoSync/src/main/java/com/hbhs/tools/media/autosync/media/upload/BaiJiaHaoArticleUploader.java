package com.hbhs.tools.media.autosync.media.upload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;
import com.hbhs.tools.media.autosync.media.utils.ImageTypeEnum;
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

public class BaiJiaHaoArticleUploader extends AbstractArticleUploader{
    private static final Logger LOG = LoggerFactory.getLogger(BaiJiaHaoArticleUploader.class);
    private static final String CONFIG_KEY_APP_ID = "app_id";
    private String appID = null;
    public BaiJiaHaoArticleUploader(MediaConfigEntity.MediaConfigInfos mediaConfig) {
        super(mediaConfig);
        if (mediaConfig.getExtendMap().get(CONFIG_KEY_APP_ID)==null)
            throw new RuntimeException("No app_id found for baijiahao configuration.");
        this.appID = mediaConfig.getExtendMap().get(CONFIG_KEY_APP_ID);
    }

    @Override
    void prepareParametersBeforeUploadCreative(ArticleInfos.CreativeInfos creative, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList, List<HttpRequestUtils.KeyValuePair> parameterList, List<HttpRequestUtils.FileValuePair> fileList) {
        if (creative == null || StringUtils.isEmpty(creative.getLocalFilePath())) return;
        File localFile = new File(creative.getLocalFilePath());
        if (!localFile.exists() || localFile.isDirectory())
            return;
        // upload file
        fileList.add(new HttpRequestUtils.FileValuePair("media", localFile));
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
        // {"error_code":0,"error_msg":"success","ret":{"id":"1570159407014770","app_id":"1560585747010784","name":"0514192305ZNw.jpg","size":"171929","type":"image","mime":"image\/jpeg","bos_url":"http:\/\/timg01.bdimg.com\/timg?pacompress&imgtype=1&sec=1439619614&autorotate=1&di=4862f2d4a231cfb59179784410b0b7ac&quality=90&size=b870_10000&src=http%3A%2F%2Fbos.nj.bpc.baidu.com%2Fv1%2Fmediaspot%2F3a7719b8c371b298f884a6ffecabca11.jpeg","created_at":"2017-06-14 14:11:39","updated_at":"2017-06-14 14:11:39","deleted_at":"0000-00-00 00:00:00","url_hash":"3025720127194693270","pic_hash":"3a7719b8c371b298f884a6ffecabca11"}}
        if (creative==null||response==null||!response.success()) return ;
        try {
            JsonNode node = new ObjectMapper().readTree(response.getContent());
            if (node.get("error_code").asInt() == 0){
                creative.setUploadCreativeID(node.get("ret").get("id").asText());
                creative.setUploadCreativeUrl(node.get("ret").get("bos_url").asText());
            }
        }catch (Exception e){}
    }

    @Override
    void prepareBeforeUploadArticle(ArticleInfos article, MediaConfigEntity.MediaConfigInfos media, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList, List<HttpRequestUtils.KeyValuePair> parameterList) {

    }

    @Override
    void cleanupAfterUploadArticle(ArticleInfos article, HttpRequestUtils.HttpResponse response) {

    }
}
