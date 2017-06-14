package com.hbhs.tools.media.autosync.media.download;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.tools.media.autosync.ApplicationConfig;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.utils.DatePraser;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

public class ZhihuArticleDownloader extends AbstractArticleDownloader {
    private static Logger LOG = LoggerFactory.getLogger(ZhihuArticleDownloader.class);
    private static final String MEDIA_NAME = "ZHI-HU";
    private String articleStartTag = "<textarea id=\"preloadedState\" hidden>";
    private String articleEndTag = "</textarea>";
    private ObjectMapper mapper = new ObjectMapper();
    public ZhihuArticleDownloader(String articleListPageUrl, String articlePagePrefixUrl,
                                  String creativeDownloadUrl) {
        super(MEDIA_NAME,null,articleListPageUrl,articlePagePrefixUrl,creativeDownloadUrl);
    }

    @Override
    public boolean login(){return true;}

    @Override
    public List<ArticleInfos> requestAriticleList(){
        String html = HttpRequestUtils.get(getArticleListPageUrl(), getCookieList(), null, null);
        LOG.debug("Loading data inside ZhihuArticleDownloader from url: {},  ", getArticleListPageUrl());

        // find target content
        if (StringUtils.isEmpty(html)) return null;
        if (!StringUtils.isEmpty(articleStartTag)&&html.contains(articleStartTag))
            html = html.substring(html.indexOf(articleStartTag)+articleStartTag.length());
        if (!StringUtils.isEmpty(articleEndTag)&&html.contains(articleEndTag))
            html = html.substring(0, html.indexOf(articleEndTag));

        return transArticalInfosFromJsonStr(html);
    }

    private List<ArticleInfos> transArticalInfosFromJsonStr(String jsonStr){
        if (StringUtils.isEmpty(jsonStr)) return null;
        List<ArticleInfos> list = new ArrayList<>();

        try {
            JsonNode node = mapper.readTree(jsonStr);

            JsonNode dataNode = node.get("database").get("Post");
            Iterator<Map.Entry<String, JsonNode>> keyValueIterator = dataNode.fields();
            while(keyValueIterator.hasNext()){
                Map.Entry<String, JsonNode> keyValue = keyValueIterator.next();
                ArticleInfos a = new ArticleInfos();
                a.setArticalName(keyValue.getValue().get("title").asText());
                a.setOriginalMedia("ZhiHu");
                a.setOriginalMediaID(keyValue.getKey());
                a.setOriginalArticleUrl(super.getArticleDetailPageUrl()+keyValue.getValue().get("url").asText());
                a.setUpdateDate(DatePraser.praseDate(keyValue.getValue().get("publishedTime").asText()));
                // content can get here
                a.setContent(keyValue.getValue().get("content").asText());
                list.add(a);
            }

        } catch (Exception e){
            LOG.error("Error to translate jsonStr: {}",jsonStr, e);
        }
        Collections.sort(list, new Comparator<ArticleInfos>() {
            @Override
            public int compare(ArticleInfos a1, ArticleInfos a2) {
                if (a1==a2) return 0;
                if (a1==null||a1.getUpdateDate()==null) return 1;
                if (a2==null||a2.getUpdateDate()==null) return 1;
                return a2.getUpdateDate().compareTo(a1.getUpdateDate());
            }
        });
        return list;
    }


    @Override
    public void requestAriticleDetails(ArticleInfos article){
        // content already get
    }
    @Override
    public void downloadCretiveInContent(ArticleInfos article){
        if (article==null||article.getContent()==null) return ;
        String content = article.getContent();
        String imageCreativePrefix = getImageCreativePrefix();
        while(content.contains(imageCreativePrefix)){
            content = content.substring(content.indexOf(imageCreativePrefix));
            String creativeAddressKey = getCreativeAddress(content);
            String remoteDownloadUrl = getCreativeDownloadUrl()+creativeAddressKey;
            String localFile = download(remoteDownloadUrl);
            if (localFile!=null){
                ArticleInfos.CreativeInfos creativeInfos = article.getCreativeMap().get(creativeAddressKey);
                if (creativeInfos==null){
                    creativeInfos = new ArticleInfos.CreativeInfos();
                    article.getCreativeMap().put(creativeAddressKey, creativeInfos);
                }
                creativeInfos.setLocalFilePath(localFile);
                creativeInfos.setOriginalCreativeID(creativeAddressKey);
                creativeInfos.setOriginalCreativeUrl(remoteDownloadUrl);
            }
            // ADD prefix
            content = content.substring(imageCreativePrefix.length());
        }
    }

    /**
     * // get 'xxxxx' for string like: <img src="xxxxx" ....
     * @param content <img src="xxxxx" ....
     * @return 'xxxxx'
     */
    private String getCreativeAddress(String content){
        String creativeAddr = null;
        String key = " src=";
        if (content.contains(key)){
            content = content.substring(content.indexOf(key)+key.length());
            if (content.contains(" "))
                content = content.substring(0, content.indexOf(" "));
            creativeAddr = content.replaceAll("\"","")
                    .replaceAll("\'","");
        }
        return creativeAddr;
    }

    private String download(String remoteAddress){
        String currentFile = ApplicationConfig.getTmpFolder()+File.separator+new Date().getTime()+"_"+remoteAddress.substring(remoteAddress.lastIndexOf("/")+1);
        boolean success = HttpRequestUtils.downloadFileToPath(remoteAddress, new File(currentFile), null, null,null);
        if (!success) {
            LOG.warn("Download creative failed from remote address: {}", remoteAddress);
            return null;
        }
        LOG.debug("Success download creative from remote address: {} into local path:{}", remoteAddress, currentFile);
        return currentFile;
    }
}
