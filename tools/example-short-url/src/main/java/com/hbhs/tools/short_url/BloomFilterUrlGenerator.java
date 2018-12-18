package com.hbhs.tools.short_url;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

/**
 * @Author: juqi
 * @Date: 2018/2/9
 **/
public class BloomFilterUrlGenerator implements UrlGenerator {

    BloomFilter<String> filter = null;
    public BloomFilterUrlGenerator(){
        filter = BloomFilter.create(UrlIndex.INSTANCE, 100000000, 0.99);
    }
    @Override
    public String generateUrl() {

        return null;
    }

    public static enum UrlIndex implements Funnel<String> {
        INSTANCE;

        @Override
        public void funnel(String from, PrimitiveSink into) {
            if (from==null){return ;}
            into.putUnencodedChars(from);
        }
    }
}
