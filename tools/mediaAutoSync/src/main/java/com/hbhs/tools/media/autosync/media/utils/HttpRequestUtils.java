package com.hbhs.tools.media.autosync.media.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpRequestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestUtils.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    private HttpRequestUtils() {
    }

    public static String get(String url, List<KeyValuePair> headerList, List<HttpRequestUtils.KeyValuePair> parameterList) {
        return get(url, (List)null, headerList, parameterList);
    }

    public static String get(String url, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList, List<HttpRequestUtils.KeyValuePair> parameterList) {
        return get(url, cookieList, headerList, parameterList, 0, DEFAULT_CHARSET);
    }
    public static List<Cookie> getCookie(String url, List<HttpRequestUtils.KeyValuePair> headerList,
                                         List<HttpRequestUtils.KeyValuePair> parameterList) {
        return getCookie(url, headerList, parameterList, 0, null);
    }

    public static List<Cookie> getCookie(String url, List<HttpRequestUtils.KeyValuePair> headerList,
                                         List<HttpRequestUtils.KeyValuePair> parameterList, int timeoutSeconds,
                                         String charset) {

        HttpClient client = createHttpClient(timeoutSeconds, charset);
        url = addParametersForGetMethod(url, parameterList);
        GetMethod get = new GetMethod(url);
        addHeaders(get, headerList);
        try {
            client.executeMethod(get);
            Cookie[] cookies = client.getState().getCookies();
            return cookies==null?null: Arrays.asList(cookies);
        } catch (Exception var9) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + var9.getMessage(), var9);
        }
    }
    private static String addParametersForGetMethod(String url, List<KeyValuePair> parameterList){

        if(parameterList != null && parameterList.size() > 0) {
            StringBuilder queryStr = new StringBuilder();
            Iterator<KeyValuePair> var7 = parameterList.iterator();

            while(var7.hasNext()) {
                KeyValuePair pair = var7.next();
                if(pair != null && pair.checkValid()) {
                    queryStr.append(pair.key).append("=").append(pair.value).append("&");
                }
            }

            if(queryStr.length() > 0) {
                if(!url.contains("?")) {
                    url = url + "?";
                }
                url = url + queryStr.substring(0, queryStr.length() - 1);
            }
        }
        return url;
    }

    private static void addHeaders(HttpMethodBase httpMethodBase, List<KeyValuePair> headerList){
        if(headerList != null && headerList.size() > 0) {
            Iterator<KeyValuePair> var7 = headerList.iterator();
            while(var7.hasNext()) {
                KeyValuePair pair = var7.next();
                if(pair != null && pair.checkValid()) {
                    httpMethodBase.addRequestHeader(pair.key, pair.value);
                }
            }
        }
    }

    private static HttpClient createHttpClient(int timeout, String charset){
        HttpClient client = new HttpClient();
        client.getParams().setSoTimeout(timeout * 1000);
        if (StringUtils.isEmpty(charset))
            charset = "UTF-8";
        client.getParams().setContentCharset("utf-8");
        return client;
    }

    private static void addCookie(HttpClient client, List<Cookie> cookieList){
        if(cookieList != null && cookieList.size() > 0) {
            for (Cookie cookie : cookieList) {
                client.getState().addCookie(cookie);
            }
        }
    }
    public static String get(String url, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList,
                             List<HttpRequestUtils.KeyValuePair> parameterList, int timeoutSeconds,
                             String charset) {
        HttpClient client = createHttpClient(timeoutSeconds, charset);
        addCookie(client, cookieList);
        url = addParametersForGetMethod(url, parameterList);
        GetMethod get = new GetMethod(url);
        addHeaders(get, headerList);

        try {
            client.executeMethod(get);
            return transInputstreamToString(get.getResponseBodyAsStream(), charset);

        } catch (Exception var9) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + var9.getMessage(), var9);
        }
    }

    private static String transInputstreamToString(InputStream is, String charset) {
        if (is==null) return null;
        StringBuilder str = new StringBuilder();
        BufferedReader reader = null;

        try {
            if (charset==null||charset.trim().equals("")) reader = new BufferedReader(new InputStreamReader(is));
            else reader = new BufferedReader(new InputStreamReader(is, charset));
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                str.append(line + "\n");
            }
        } catch (Exception var12) {
            throw new IllegalArgumentException("Failed to create inputstream");
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (Exception var11) {
                    ;
                }
            }
        }

        return str.toString();
    }

    public static HttpResponse post(String url, List<HttpRequestUtils.KeyValuePair> headerList, List<HttpRequestUtils.KeyValuePair> parameterList) {
        return post(url, (List)null, headerList, parameterList);
    }

    public static HttpResponse post(String url, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList, List<HttpRequestUtils.KeyValuePair> parameterList) {
        return post(url, cookieList, headerList, parameterList, 0, DEFAULT_CHARSET);
    }
    private static void addParametersForPostMethod(PostMethod post, List<KeyValuePair> parameterList){
        if(parameterList != null && parameterList.size() > 0) {
            for (KeyValuePair keyValuePair : parameterList) {
                post.setParameter(keyValuePair.key, keyValuePair.value);
            }
        }
    }
    public static HttpResponse post(String url, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList,
                              List<HttpRequestUtils.KeyValuePair> parameterList, int timeoutSeconds,
                              String charset) {
        HttpResponse response = new HttpResponse();
        HttpClient client = createHttpClient(timeoutSeconds, charset);
        addCookie(client, cookieList);

        PostMethod post = new PostMethod(url);

        addHeaders(post, headerList);
        addParametersForPostMethod(post, parameterList);

        try {
            client.executeMethod(post);
            response.setStatusCode(post.getStatusCode());
            response.setContent(transInputstreamToString(post.getResponseBodyAsStream(), charset));
        } catch (Exception var9) {
            LOG.error("Error to call http access for url: {}", url, var9);
            response.setErrorMessage(var9.getMessage());
        }
        return response;
    }

    public static String postJSON(String url, List<HttpRequestUtils.KeyValuePair> headerList, String jsonBody) {
        return postJSON(url, (List)null, headerList, jsonBody, DEFAULT_CHARSET);
    }

    public static String postJSON(String url, List<Cookie> cookieList, List<HttpRequestUtils.KeyValuePair> headerList, String jsonBody,
                                  String charset) {
        HttpClient client = createHttpClient(0, charset);
        addCookie(client, cookieList);

        PostMethod post = new PostMethod(url);
        addHeaders(post, headerList);

        try {
            if(jsonBody != null && !"".equals(jsonBody)) {
                RequestEntity entity = new StringRequestEntity(jsonBody, "application/json", "UTF-8");
                post.setRequestEntity(entity);
            }

            client.executeMethod(post);
            return new String(post.getResponseBody(), charset);
        } catch (Exception var8) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + var8.getMessage(), var8);
        }
    }

    public static class KeyValuePair {
        private String key;
        private String value;

        public KeyValuePair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public boolean checkValid() {
            return this.key != null && !"".equals(this.key.trim()) && this.value != null && !"".equals(this.value.trim());
        }
        public String toString(){
            return key +" = "+value;
        }
    }

    public static class FileValuePair {
        String key;
        File file;
        public FileValuePair(String key, File targetFile){
            this.key = key;
            this.file =targetFile;
        }

        public boolean checkValid(){
            return !StringUtils.isEmpty(key)&&file!=null&&file.exists()&&file.isFile();
        }
        @Override
        public String toString(){
            return key+" = "+(file==null?"":file.getPath());
        }
    }

    public static boolean downloadFileToPath(String url, File targetFile, List<Cookie> cookieList,
                                             List<KeyValuePair> parameterList, List<KeyValuePair> headerList){
        return downloadFileToPath(url,targetFile,cookieList,parameterList,headerList, DEFAULT_CHARSET);
    }
    public static boolean downloadFileToPath(String url, File targetFile, List<Cookie> cookieList,
                                            List<KeyValuePair> parameterList, List<KeyValuePair> headerList, String charset){
        HttpClient client = createHttpClient(0, charset);
        url = addParametersForGetMethod(url, parameterList);

        GetMethod get = new GetMethod(url);
        addHeaders(get, headerList);

        try {
            client.executeMethod(get);
            if(200 == get.getStatusCode()) {
                Files.copy(get.getResponseBodyAsStream(), targetFile.toPath());
                return true;
            }
        } catch (Exception var9) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + var9.getMessage(), var9);
        }
        return false;
    }
    public static HttpResponse uploadFile(String url, List<Cookie> cookieList, List<KeyValuePair> headerList,
                                          List<KeyValuePair> parameterList, List<FileValuePair> fileList){
        return uploadFile(url, cookieList, headerList, parameterList, fileList, DEFAULT_CHARSET);
    }
    public static HttpResponse uploadFile(String url, List<Cookie> cookieList, List<KeyValuePair> headerList,
                                          List<KeyValuePair> parameterList, List<FileValuePair> fileList,
                                          String charset){
        HttpResponse response = new HttpResponse();
        HttpClient client = createHttpClient(0, charset);
        addCookie(client, cookieList);
        PostMethod post = new PostMethod(url);
        addHeaders(post, headerList);

        try {
            int totalSize = (parameterList==null?0:parameterList.size())+
                    (fileList==null?0:fileList.size());
            Part[] parts = new Part[totalSize];
            int index = 0;
            if (parameterList!=null&&parameterList.size()>0){
                for (KeyValuePair param : parameterList) {
                    parts[index++] = new StringPart(param.key, param.value, charset);
                }
            }
            if (fileList!=null&&fileList.size()>0){
                for (FileValuePair file : fileList) {
                    parts[index++] = new FilePart(file.key, file.file.getName(), file.file, "", charset);
                }
            }

            post.setRequestEntity(new MultipartRequestEntity(parts, new HttpClientParams()));
            client.executeMethod(post);
            response.setStatusCode(post.getStatusCode());
            if (!StringUtils.isEmpty(charset)) response.setContent(new String(post.getResponseBody(), charset));
            else response.setContent(new String(post.getResponseBody()));
        } catch (Exception var9) {
            LOG.error("Http access error for url:" + url + ", message=" + var9.getMessage(), var9);
            response.setErrorMessage(var9.getMessage());
        }
        return response;
    }
    @Getter@Setter@ToString
    public static class HttpResponse{
        public static final int CODE_SUCCESS = 1;
        public static final int CODE_ERROR = 0;
        private int code = CODE_SUCCESS;
        private String errorMessage;
        private int statusCode;
        private String content;

        public void setErrorMessage(String errorMessage){
            this.code = CODE_ERROR;
            this.errorMessage = errorMessage;
        }
        public boolean success(){
            return CODE_SUCCESS == code;
        }
    }
}

