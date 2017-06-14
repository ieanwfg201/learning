package com.hbhs.tools.media.autosync.media.download;


import com.hbhs.tools.media.autosync.media.IArticleDownloader;
import com.hbhs.tools.media.autosync.media.download.ZhihuArticleDownloader;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.entity.ArticleLoaderCondition;
import com.hbhs.tools.media.autosync.media.utils.FileSerializeUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by walter on 17-6-7.
 */
public class ZhihuArticleDownloaderTest {
    String articleListUrl = "https://zhuanlan.zhihu.com/ml123";
    String articleDetailUrl = "https://zhuanlan.zhihu.com";
    String creativeDownloadUrl = "https://pic3.zhimg.com/";
    IArticleDownloader loader = new ZhihuArticleDownloader(articleListUrl, articleDetailUrl, creativeDownloadUrl);

    @Test
    public void testFLow() throws Exception{
        ArticleLoaderCondition cond = new ArticleLoaderCondition();
        cond.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2017-06-06"));
        List<ArticleInfos> list = loader.downloadArticles(cond);
        if (list!=null)
        for (ArticleInfos articalInfos : list) {
            System.out.println(articalInfos);
            FileSerializeUtils.toFileByJavaDefault(articalInfos);
        }
    }

}