package com.hbhs.tools.media.autosync.media;


import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;

import java.util.List;

public interface IAritcleUploader {

    void uploadArticle(List<ArticleInfos> articleList);
}
