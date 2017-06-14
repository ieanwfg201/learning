package com.hbhs.tools.media.autosync.media;

import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.entity.ArticleLoaderCondition;

import java.util.List;

public interface IArticleDownloader {

    List<ArticleInfos> downloadArticles(ArticleLoaderCondition cond);
}
