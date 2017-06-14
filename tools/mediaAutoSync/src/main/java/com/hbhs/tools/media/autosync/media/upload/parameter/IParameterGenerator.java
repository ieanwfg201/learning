package com.hbhs.tools.media.autosync.media.upload.parameter;

import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;

import java.util.List;

public interface IParameterGenerator {

    List<HttpRequestUtils.KeyValuePair> generateSaveAsPublished(ArticleInfos article);

    List<HttpRequestUtils.KeyValuePair> generateSaveAsDraft(ArticleInfos article);
}
