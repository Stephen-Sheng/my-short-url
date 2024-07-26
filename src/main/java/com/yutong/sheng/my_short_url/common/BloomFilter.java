package com.yutong.sheng.my_short_url.common;

import java.nio.charset.Charset;

public class BloomFilter {


    private static final com.google.common.hash.BloomFilter<String> BLOOM_FILTER = com.google.common.hash.BloomFilter.create(
            com.google.common.hash.Funnels.stringFunnel(Charset.defaultCharset()), 1000000, 0.01);

    public static boolean add(String key){
        return BLOOM_FILTER.put(key);
    }

    public static boolean contains(String key)
    {
        return BLOOM_FILTER.mightContain(key);
    }
}
