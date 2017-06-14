package com.hbhs.tools.media.autosync.media.upload;

import com.hbhs.tools.media.autosync.BaseTest;
import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.upload.parameter.JinRiTouTiaoParameterGenerator;
import com.hbhs.tools.media.autosync.media.utils.FileSerializeUtils;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;
import com.sun.xml.internal.ws.api.message.HeaderList;
import org.apache.commons.httpclient.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JinRiTouTiaoArticleUploaderTest extends BaseTest {

    @Autowired
    private MediaConfigEntity configEntity;

    MediaConfigEntity.MediaConfigInfos config = null;
    JinRiTouTiaoArticleUploader uploader = null;

    @Before
    public void setUp(){
        config = configEntity.getMediaConfig("toutiao");
        uploader = new JinRiTouTiaoArticleUploader(config);
    }

    @Test
    public void testUploadArticle(){
        ArticleInfos article = createArticle();
        uploader.uploadArticle(Arrays.asList(article));
    }

    @Test
    public void initCookie() throws Exception {
        List<Cookie> cookieList = uploader.initCookie(null);
        assertTrue(cookieList!=null&&cookieList.size()>0);
    }

    private ArticleInfos createArticle(){
        return FileSerializeUtils.randomOne();
    }

    @Test
    public void uploadCreative() throws Exception {

        List<Cookie> cookieList = uploader.initCookie(config);
        List<HttpRequestUtils.KeyValuePair> headerList = uploader.initHeader();
//        uploader.uploadCreative(createArticle(), null, headerList);
    }

    @Test
    public void uploadArticle() throws Exception {
        ArticleInfos article = createArticle();
        List<HttpRequestUtils.KeyValuePair> headerList = uploader.initHeader();
//        uploader.uploadCreative(article, null, headerList);
//        uploader.prepareArticleForUploading(article);
//        uploader.uploadArticle(article, null, headerList);
    }

}