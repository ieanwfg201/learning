package com.hbhs.tools.media.autosync.media.download;

import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SohuTopArticleDownloader extends AbstractArticleDownloader {
    private static final Logger LOG = LoggerFactory.getLogger(SohuTopArticleDownloader.class);

    public SohuTopArticleDownloader(MediaConfigEntity.MediaConfigInfos config) {
        super(config);
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public List<ArticleInfos> requestAriticleList() {
        try {
            Document doc = Jsoup.connect(config.getDownloadArticleListUrl()).get();
            Elements elements = doc.getElementsByAttributeValue("class", "area bg01");
            for (Element element : elements) {
                System.out.println(element);
            }
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    public void requestAriticleDetails(ArticleInfos article) {

    }

    @Override
    public void downloadCretiveInContent(ArticleInfos article) {

    }
}
