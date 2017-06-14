package com.hbhs.tools.media.autosync.media.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by walter on 17-6-8.
 */
public class HttpRequestUtilsTest {

    @Test
    public void testGetInfos(){
        System.out.println(HttpRequestUtils.get("https://zhuanlan.zhihu.com/ml123", null,null,null));
    }
}