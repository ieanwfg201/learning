package com.hbhs.tools.media.autosync.media.upload.parameter;


import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;

import java.util.ArrayList;
import java.util.List;

public class BaiJiaHaoParameterGenerator {

    public static List<HttpRequestUtils.KeyValuePair> generateSaveAsDraft(ArticleInfos article, String appID) {
//        type:news
//        app_id:1560585747010784
//        title:aaaaaaaaaaaaaaa
//        _action:reset
//        content:<p text-correct-nocheck="text-correct-nocheck">asdasdasdasdasda<img src="http://timg01.bdimg.com/timg?pacompress&imgtype=1&sec=1439619614&autorotate=1&di=4862f2d4a231cfb59179784410b0b7ac&quality=90&size=b870_10000&src=http%3A%2F%2Fbos.nj.bpc.baidu.com%2Fv1%2Fmediaspot%2F3a7719b8c371b298f884a6ffecabca11.jpeg" data-w="750" data-h="586"/></p>
//        feed_cat:4
//        original_status:0
//        cover_layout:one
//        cover_images:[{"src":"http://timg01.bdimg.com/timg?pacompress&imgtype=1&sec=1439619614&autorotate=1&di=d6b8c48e0e3f66f808406342f7822037&quality=90&size=b747_10074&cut_x=0&cut_y=0&cut_w=747&cut_h=500&src=http%3A%2F%2Ftimg01.bdimg.com%2Ftimg%3Fpacompress%26imgtype%3D1%26sec%3D1439619614%26autorotate%3D1%26di%3D4862f2d4a231cfb59179784410b0b7ac%26quality%3D90%26size%3Db870_10000%26src%3Dhttp%253A%252F%252Fbos.nj.bpc.baidu.com%252Fv1%252Fmediaspot%252F3a7719b8c371b298f884a6ffecabca11.jpeg"}]
//        _cover_images_map:[{"src":"http://timg01.bdimg.com/timg?pacompress&imgtype=1&sec=1439619614&autorotate=1&di=d6b8c48e0e3f66f808406342f7822037&quality=90&size=b747_10074&cut_x=0&cut_y=0&cut_w=747&cut_h=500&src=http%3A%2F%2Ftimg01.bdimg.com%2Ftimg%3Fpacompress%26imgtype%3D1%26sec%3D1439619614%26autorotate%3D1%26di%3D4862f2d4a231cfb59179784410b0b7ac%26quality%3D90%26size%3Db870_10000%26src%3Dhttp%253A%252F%252Fbos.nj.bpc.baidu.com%252Fv1%252Fmediaspot%252F3a7719b8c371b298f884a6ffecabca11.jpeg","origin_src":"http://timg01.bdimg.com/timg?pacompress&imgtype=1&sec=1439619614&autorotate=1&di=4862f2d4a231cfb59179784410b0b7ac&quality=90&size=b870_10000&src=http%3A%2F%2Fbos.nj.bpc.baidu.com%2Fv1%2Fmediaspot%2F3a7719b8c371b298f884a6ffecabca11.jpeg"}]
//        article_id:1570160337072824
        List<HttpRequestUtils.KeyValuePair> parameterList = new ArrayList<>();
        parameterList.add(new HttpRequestUtils.KeyValuePair("type", "news"));
        parameterList.add(new HttpRequestUtils.KeyValuePair("app_id", appID));
        parameterList.add(new HttpRequestUtils.KeyValuePair("title", article.getArticalName()));
        parameterList.add(new HttpRequestUtils.KeyValuePair("_action", "reset"));
        parameterList.add(new HttpRequestUtils.KeyValuePair("content", article.getUploadContent()));
        parameterList.add(new HttpRequestUtils.KeyValuePair("feed_cat", "4"));
        parameterList.add(new HttpRequestUtils.KeyValuePair("original_status", "0"));
        return parameterList;
    }
}
