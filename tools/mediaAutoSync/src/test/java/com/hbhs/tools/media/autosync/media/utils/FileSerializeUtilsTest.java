package com.hbhs.tools.media.autosync.media.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by walter on 17-6-12.
 */
public class FileSerializeUtilsTest {

    private String randomeFile(){
        return "/home/walter/data/"+new Date().getTime()+".seri";
    }
    @Test
    public void jsonTest() throws Exception {
        A data = new A(100, "testing infos.");
        System.out.println("Before: "+data.toString());
        String filePath = randomeFile();
        FileSerializeUtils.toFileByJson(filePath, data);
        A data2 = FileSerializeUtils.readFileByJson(filePath, A.class);
        System.out.println("After: "+data2.toString());
        assertFalse(data == data2);
        assertTrue(data.toString().equals(data2.toString()));
    }

    @Test
    public void javaDefaultTest() throws Exception {
        A data = new A(100, "testing infos.");
        System.out.println("Before: "+data.toString());
        String filePath = randomeFile();
        FileSerializeUtils.toFileByJavaDefault(filePath, data);
        A data2 = FileSerializeUtils.readFileByJavaDefault(filePath, A.class);
        System.out.println("After: "+data2.toString());
        assertFalse(data == data2);
        assertTrue(data.toString().equals(data2.toString()));
    }


    @Getter@Setter@ToString
    private static class A implements Serializable {
        private int id;
        private String name;
        private Map<Integer, String> map = new HashMap<>();
        public A(){}
        public A(int id, String name){
            setId(id); setName(name);
            int random = (int)(Math.random()*1000);
            map.put(random, random+"-name");
        }
    }
}