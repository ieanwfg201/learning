package com.hbhs.tools.media.autosync.media.upload;

import com.hbhs.tools.media.autosync.BaseTest;
import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;

import static org.junit.Assert.*;

/**
 * Created by walter on 17-6-14.
 */
public class BaiJiaHaoArticleUploaderTest extends BaseTest{

    @Autowired
    private MediaConfigEntity configEntity;
    private MediaConfigEntity.MediaConfigInfos config;
    private BaiJiaHaoArticleUploader uploader = null;


    @Before
    public void setUp(){
        config = configEntity.getMediaConfig("baijia");
        uploader = new BaiJiaHaoArticleUploader(config);
    }

    @Test
    public void uploadArticle(){

    }

    @Test
    public void basic() throws Exception {
        System.out.println(URLDecoder.decode("http%3A%2F%2Fbos.nj.bpc.baidu.com%2Fv1%2Fmediaspot%2F3a7719b8c371b298f884a6ffecabca11.jpeg", "UTF-8"));
    }


    @Test
    public void prepareParametersBeforeUploadCreative() throws Exception {
    }

    @Test
    public void cleanResultAfterUploadCreative() throws Exception {
    }

    @Test
    public void prepareBeforeUploadArticle() throws Exception {
    }

    @Test
    public void cleanupAfterUploadArticle() throws Exception {
    }

}