package com.hbhs.tools.media.autosync.media.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.tools.media.autosync.media.entity.ArticleInfos;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;

public final class FileSerializeUtils {
    private static final String DEFAULT_PATH = "/home/walter/data";
    private FileSerializeUtils(){}
    public static void toFileByJson(String filePath, Object object){
        if (StringUtils.isEmpty(filePath)||object==null) return ;
        try {
            File targetFile = new File(filePath);
            Files.write(targetFile.toPath(), new ObjectMapper().writeValueAsBytes(object));
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
    public static void toFileByJavaDefault(String filePath, Object object){
        if (StringUtils.isEmpty(filePath)||object==null) return ;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            File targetFile = new File(filePath);
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);

            Files.write(targetFile.toPath(), bos.toByteArray());
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }finally {
            if (oos!=null)try{oos.close();}catch (Exception e){}
            if (bos!=null)try{bos.close();}catch (Exception e){}
        }
    }

    public static <T> T readFileByJson(String filePath, Class<T> clazz){
        if (StringUtils.isEmpty(filePath)||clazz==null) return null;
        try {
            File targetFile = new File(filePath);
            if (!targetFile.exists()) return null;
            return new ObjectMapper().readValue(targetFile, clazz);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
    public static <T> T readFileByJavaDefault(String filePath, Class<T> clazz){
        if (StringUtils.isEmpty(filePath)||clazz==null) return null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            File targetFile = new File(filePath);
            if (!targetFile.exists()) return null;
            byte[] bytes = Files.readAllBytes(targetFile.toPath());
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            return (T)object;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }finally {
            if (bis!=null)try{bis.close();}catch (Exception e){}
            if (ois!=null)try{ois.close();}catch (Exception e){}
        }
    }

    public static void toFileByJavaDefault(ArticleInfos article){
        toFileByJavaDefault(DEFAULT_PATH+"/"+article.getOriginalMedia()+"_"+article.getOriginalMediaID()+
        ".seri", article);
    }

    public static ArticleInfos readFileByJavaDefault(String media, String mediaID){
        return readFileByJavaDefault(DEFAULT_PATH+"/"+media+"_"+mediaID+".seri", ArticleInfos.class);
    }

    public static ArticleInfos randomOne(){
        File directory = new File(DEFAULT_PATH);
        File[] arrays = directory.listFiles();
        for (File file: arrays){
            if (file.getName().contains("_")){
                return readFileByJavaDefault(file.getPath(), ArticleInfos.class);
            }
        }
        return null;
    }
}
