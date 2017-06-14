package com.hbhs.tools.media.autosync.media.upload.parameter;

import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils.KeyValuePair;
import com.hbhs.tools.media.autosync.media.utils.UrlCode;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JinRiTouTiaoParameterGenerator {


    public static List<HttpRequestUtils.KeyValuePair> generateSaveAsPublished(ArticleInfos article) {
        if (article==null|| StringUtils.isEmpty(article.getArticalName())||StringUtils.isEmpty(article.getContent()))
            return null;
        List<HttpRequestUtils.KeyValuePair> list = new ArrayList<>();
//        title:asfsafsadfas
        list.add(new KeyValuePair("title", article.getArticalName()));
//        abstract:
        list.add(new KeyValuePair("abstract", ""));
//        content:<p>sdfsdfsdfsdfsdfsdfsdf<img onload="editor.fireEvent(&#39;contentchange&#39;)" src="https://p.pstatp.com/large/26e500049c8603f898a0" alt="26e500049c8603f898a0"/></p><p><br/></p>
        list.add(new KeyValuePair("content", article.getContent()));
//                authors:
        list.add(new KeyValuePair("authors", ""));
//        tag:
        list.add(new KeyValuePair("tag", ""));
//        self_appoint:0
        list.add(new KeyValuePair("self_appoint", "0"));
//        save:1
        list.add(new KeyValuePair("save", "1"));
//        video_vid:0
        list.add(new KeyValuePair("video_vid", "0"));
//        video_vu:
        list.add(new KeyValuePair("video_vu", ""));
//        video_vname:
        list.add(new KeyValuePair("video_vname", ""));
//        video_vposter://p3.pstatp.com/large/
        list.add(new KeyValuePair("video_vposter", UrlCode.encode("//p3.pstatp.com/large/")));
//        vids_to_del:[]
        list.add(new KeyValuePair("vids_to_del", "[]"));
//        force_ads:2
        list.add(new KeyValuePair("force_ads", "2"));
//        after_pass_modify:0
        list.add(new KeyValuePair("after_pass_modify", "0"));
//        urgent_push:0
        list.add(new KeyValuePair("urgent_push", "0"));
//        subsidy:0
        list.add(new KeyValuePair("subsidy", "0"));
//        pgc_feed_covers:[{"uri":"26e500049c8603f898a0","origin_uri":"26e500049c8603f898a0"}]
        list.add(new KeyValuePair("pgc_feed_covers", ""));
//        movie_id:
        list.add(new KeyValuePair("movie_id", ""));
//        is_fans_article:0
        list.add(new KeyValuePair("is_fans_article", "0"));
//        recommend_auto_analyse:0
        list.add(new KeyValuePair("recommend_auto_analyse", "0"));
//        from_diagnosis:0
        list.add(new KeyValuePair("from_diagnosis", "0"));
//        govern_forward:0
        list.add(new KeyValuePair("govern_forward", "0"));
//        statistics:{"detalTime":68598,"pasteTimes":0}
        list.add(new KeyValuePair("statistics", UrlCode.encode("{\"detalTime\":68598,\"pasteTimes\":0}")));

        return list;
    }
    // 返回格式为: [{"uri":"26e500049c8603f898a0","origin_uri":"26e500049c8603f898a0"}]
    private static String selectFeedCover(ArticleInfos article){
        StringBuilder str = new StringBuilder();
        str.append("[");
        if (article!=null&&article.getCreativeMap().size()>0){
            int maxPictures = article.getCreativeMap().size()<3?1:3;
            Iterator<ArticleInfos.CreativeInfos> creativeInfosIterator = article.getCreativeMap().values().iterator();
            while (creativeInfosIterator.hasNext()){
                ArticleInfos.CreativeInfos creative = creativeInfosIterator.next();
                str.append("{\"uri\":\"").append(creative.getUploadCreativeID()).append("\"")
                        .append(",")
                        .append("origin_uri\":\"").append(creative.getUploadCreativeID()).append("\"}");

                if (--maxPictures <=0){
                    break;
                }
                str.append(",");
            }
        }
        str.append("]");
        return str.toString();
    }


    public static List<HttpRequestUtils.KeyValuePair> generateSaveAsDraft(ArticleInfos article) {
        if (article==null|| StringUtils.isEmpty(article.getArticalName())||StringUtils.isEmpty(article.getContent()))
            return null;
        List<HttpRequestUtils.KeyValuePair> list = new ArrayList<>();
//        title:asdasdasd
        list.add(new KeyValuePair("title", article.getArticalName()));
//        abstract:
        list.add(new KeyValuePair("abstract", ""));
//        content:<p>asdasdasdas<img onload="editor.fireEvent(&#39;contentchange&#39;)" src="https://p.pstatp.com/large/26e800022dae648c9b13" alt="26e800022dae648c9b13"/></p><p><br/></p>
        list.add(new KeyValuePair("content", article.getUploadContent()));
//                authors:
        list.add(new KeyValuePair("authors", ""));
//        tag:
        list.add(new KeyValuePair("tag", ""));
//        self_appoint:0
        list.add(new KeyValuePair("self_appoint", "0"));
//        save:0
        list.add(new KeyValuePair("save", "0"));
//        video_vid:0
        list.add(new KeyValuePair("video_vid", "0"));
//        video_vu:
        list.add(new KeyValuePair("video_vu", ""));
//        video_vname:
        list.add(new KeyValuePair("video_vname", ""));
//        video_vposter://p3.pstatp.com/large/
        list.add(new KeyValuePair("video_vposter", UrlCode.encode("//p3.pstatp.com/large/")));
//        vids_to_del:[]
        list.add(new KeyValuePair("vids_to_del", "[]"));
//        force_ads:2
        list.add(new KeyValuePair("force_ads", "2"));
//        after_pass_modify:0
        list.add(new KeyValuePair("after_pass_modify", "0"));
//        urgent_push:0
        list.add(new KeyValuePair("urgent_push", "0"));
//        subsidy:0
        list.add(new KeyValuePair("subsidy", "0"));
//        pgc_feed_covers:[{"uri":"26e500049c8603f898a0","origin_uri":"26e500049c8603f898a0"}]
        list.add(new KeyValuePair("pgc_feed_covers", selectFeedCover(article)));
//        movie_id:
        list.add(new KeyValuePair("movie_id", ""));
//        is_fans_article:0
        list.add(new KeyValuePair("is_fans_article", "0"));
//        recommend_auto_analyse:0
        list.add(new KeyValuePair("recommend_auto_analyse", "0"));
//        from_diagnosis:0
        list.add(new KeyValuePair("from_diagnosis", "0"));
//        govern_forward:0
        list.add(new KeyValuePair("govern_forward", "0"));
//        statistics:{"detalTime":68598,"pasteTimes":0}
        int detalTime = (int)(Math.random()*30000+50000);
        list.add(new KeyValuePair("statistics", UrlCode.encode("{\"detalTime\":"+detalTime+",\"pasteTimes\":0}")));

        return list;
    }
}
