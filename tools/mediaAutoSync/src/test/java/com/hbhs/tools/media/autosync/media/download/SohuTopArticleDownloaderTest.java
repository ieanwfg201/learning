package com.hbhs.tools.media.autosync.media.download;

import com.hbhs.tools.media.autosync.BaseTest;
import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter on 17-6-19.
 */
public class SohuTopArticleDownloaderTest extends BaseTest{

    @Autowired
    private MediaConfigEntity entity;
    private MediaConfigEntity.MediaConfigInfos config;
    private SohuTopArticleDownloader downloader ;

    @Before
    public void setUp() throws Exception {
        config = entity.getMediaConfig("sohutop");
        downloader = new SohuTopArticleDownloader(config);
    }

    @Test
    public void test(){
        List<ArticleInfos> articleInfosList = downloader.requestAriticleList();
        if (articleInfosList!=null)
            for (ArticleInfos articleInfos : articleInfosList) {
                System.out.println(articleInfos);
            }
    }
}