package com.hbhs.tools.media.autosync.media.download;

import com.hbhs.tools.media.autosync.media.IArticleDownloader;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import com.hbhs.tools.media.autosync.media.entity.ArticleLoaderCondition;
import com.hbhs.tools.media.autosync.media.utils.HttpRequestUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.httpclient.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;


public abstract class AbstractArticleDownloader implements IArticleDownloader {
    private static Logger LOG = LoggerFactory.getLogger(AbstractArticleDownloader.class);
    private static final String HTTP_HEADER = "http://";
    private static final String HTTPS_HEADER = "https://";
    @Getter@Setter
    private String imageCreativePrefix = "<img ";

    @Getter
    private String host;
    @Getter@Setter
    private String loginPage;
    @Getter@Setter
    private String articleListPageUrl;
    @Getter@Setter
    private String articleDetailPageUrl;
    @Getter@Setter
    private String mediaName;
    @Getter@Setter
    private String creativeDownloadUrl;

    @Getter
    private List<Cookie> cookieList = null;


    public AbstractArticleDownloader(String mediaName, String loginPage,
                                     String articleListPageUrl, String articlePagePrefixUrl,
                                     String creativeDownloadUrl){
        setMediaName(mediaName);
        setLoginPage(loginPage);
        setArticleListPageUrl(articleListPageUrl);
        setArticleDetailPageUrl(articlePagePrefixUrl);
        setCreativeDownloadUrl(creativeDownloadUrl);
        setHost();
    }


    protected void setHost(){
        String url = getLoginPage();
        if (StringUtils.isEmpty(url)) url = getArticleListPageUrl() ;
        if (StringUtils.isEmpty(url)) url = getArticleDetailPageUrl();
        if (StringUtils.isEmpty(url)) return ;
        url = url.toLowerCase();
        if (url.startsWith(HTTP_HEADER)) url = url.substring(HTTP_HEADER.length());
        if (url.startsWith(HTTPS_HEADER)) url = url.substring(HTTPS_HEADER.length());
        if (url.indexOf("/")>0) url = url.substring(0, url.indexOf("/"));
        this.host =url;
    }



    /**
     * Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,* /*;q=0.8
     * Accept-Encoding:gzip, deflate, sdch, br
     * Accept-Language:en-US,en;q=0.8
     * Cache-Control:no-cache
     * Connection:keep-alive
     * Cookie:l_n_c=1; q_c1=8226a16828e049c8a9e6dc01aedd679d|1496819097000|1496819097000; r_cap_id="N2NlNWI5YmFmNmRlNGQwNzlmOWUzODMzYjU0MWZlOWM=|1496819097|d4f627728b85ea11a5a0a7080c5a63cac2ff0038"; cap_id="NTBlNWI1MDI2YjUzNGJiMTg1NGIyOTUzNzg4ODM5MGU=|1496819097|e9e5de1d538a972a437db31e8ae3c516db2716c4"; l_cap_id="NmFkY2E1YzRlOGU3NDZkOTk3Njk4ZWMxNzE4NDA3Mjg=|1496819097|3e275e3e860f0d6535b0c927987e1c79a5d22dca"; d_c0="AIACXGNr4AuPTqjXsp-OQEEJMndZ0pJ4iAI=|1496819097"; _zap=4b6ecaa8-3365-4681-bca8-d7378218d2b3; __utma=51854390.1991399185.1496819098.1496819098.1496819098.1; __utmb=51854390.0.10.1496819098; __utmc=51854390; __utmz=51854390.1496819098.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=51854390.000--|3=entry_date=20170607=1; n_c=1; aliyungf_tc=AQAAACoiMEU2lw0ADg9zG5L66h3P2avF
     * Host:zhuanlan.zhihu.com
     * Pragma:no-cache
     * Upgrade-Insecure-Requests:1
     * User-Agent:Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36
     *
     * @return header list
     */
    protected List<HttpRequestUtils.KeyValuePair> initRequestHeader() {
        List<HttpRequestUtils.KeyValuePair> headerList = new ArrayList<HttpRequestUtils.KeyValuePair>();
        headerList.add(new HttpRequestUtils.KeyValuePair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,* /*;q=0.8"));
        headerList.add(new HttpRequestUtils.KeyValuePair("Accept-Encoding", "gzip, deflate, sdch, br"));
        headerList.add(new HttpRequestUtils.KeyValuePair("Accept-Language", "en-US,en;q=0.8"));
        headerList.add(new HttpRequestUtils.KeyValuePair("Cache-Control", "no-cache"));
        headerList.add(new HttpRequestUtils.KeyValuePair("Connection", "keep-alive"));
        headerList.add(new HttpRequestUtils.KeyValuePair("Host", host));
        headerList.add(new HttpRequestUtils.KeyValuePair("Pragma", "no-cache"));
        headerList.add(new HttpRequestUtils.KeyValuePair("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36"));
        return headerList;
    }

    /**
     * Init cookies
     * @return null
     */
    protected void initRequestCookies(){
        String address = loginPage;
        if (StringUtils.isEmpty(address)) address = articleListPageUrl;
        this.cookieList = HttpRequestUtils.getCookie(address, null, null);
    }


    @Override
    public List<ArticleInfos> downloadArticles(ArticleLoaderCondition cond){
        // 1. init
        init();
        // 2. do login if need
        LOG.debug("STEP-1: Start do login for media: {}", getMediaName());
        if (!login()){
            LOG.error("Failed to login media: {} inside AbstractArticleDownloader, break next steps", getMediaName());
            return null;
        }
        LOG.debug("Success login into media: {} inside AbstractArticleDownloader, execute next steps", getMediaName());

        // 3. get article list
        LOG.debug("STEP-2: Start request article list from remote url: {}", getArticleListPageUrl());
        List<ArticleInfos> articleList = requestAriticleList();
        debugArticalInfos(articleList);

        // 4. 过滤条件
        LOG.debug("STEP-3: Start to remove articles if not match request conditions: {}", cond);
        filterByCondition(articleList, cond);
        debugArticalInfos(articleList);

        // 5. get article details
        LOG.debug("STEP-4: Start to query content details for each available articles");
        if (articleList!=null&&articleList.size()>0){
            for (ArticleInfos article : articleList) {
                requestAriticleDetails(article);
                downloadCretiveInContent(article);
            }
        }
        // 6. remove invalid articles
        removeInvalidArticle(articleList);
        LOG.info("Success to load all available articles, total size:{}", (articleList==null?null:articleList.size()));
        return articleList;
    }

    public void init(){
        initRequestHeader();
        initRequestCookies();
    }

    abstract boolean login();
    abstract List<ArticleInfos> requestAriticleList();
    abstract void requestAriticleDetails(ArticleInfos article);
    abstract void downloadCretiveInContent(ArticleInfos article);

    protected void filterByCondition(List<ArticleInfos> list, ArticleLoaderCondition cond){
        if (cond==null) return ;
        if (list==null||list.size()==0) return ;
        if (cond.getStartDate()==null){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cond.setStartDate(cal.getTime());
        }

        // ############## start date ######
        Iterator<ArticleInfos> listIterator = list.iterator();
        while(listIterator.hasNext()){
            ArticleInfos one = listIterator.next();
            if (one.getUpdateDate().before(cond.getStartDate()))
                listIterator.remove();
        }
        // ############## end date ######
        if (cond.getEndDate()!=null){
            listIterator = list.iterator();
            while(listIterator.hasNext()){
                ArticleInfos one = listIterator.next();
                if (one.getUpdateDate().after(cond.getEndDate()))
                    listIterator.remove();
            }
        }

        debugArticalInfos(list);
    }

    private void debugArticalInfos(List<ArticleInfos> list){
        if (LOG.isDebugEnabled()){
            StringBuilder str = new StringBuilder();
            str.append("Print current articles infos, total size:")
                    .append((list==null?null:list.size()))
                    .append(", data as follow: \n");
            if (list!=null)
                for (ArticleInfos data : list) {
                    str.append("    ").append(data.getOriginalMediaID()).append(" = ").append(data).append("\n");
                }
            LOG.debug(str.toString());
        }
    }

    protected void removeInvalidArticle(List<ArticleInfos> list){
        if (list==null||list.size()==0) return;
        Iterator<ArticleInfos> listIterator = list.iterator();
        while(listIterator.hasNext()){
            ArticleInfos next = listIterator.next();
            boolean needRemove = false;
            if (StringUtils.isEmpty(next.getArticalName())){
                LOG.debug("Article: {} 's name is empty, remove it.", next.getOriginalMediaID());
                needRemove = true;
            }
            if (StringUtils.isEmpty(next.getContent())){
                LOG.debug("Article: {} 's content is empty, remove it.", next.getOriginalMediaID());
                needRemove = true;
            }
            if (needRemove) listIterator.remove();
        }

        debugArticalInfos(list);
    }
}
