package com.hbhs.tools.media.autosync.media.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DatePraser {
    protected static final Logger LOG = LoggerFactory.getLogger(DatePraser.class);

    private static final String PATTERN_1="[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
    private static final String PATTERN_1_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_2="[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}";
    private static final String PATTERN_2_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    private static final String PATTERN_3="[0-9]{4}-[0-9]{2}-[0-9]{2}";
    private static final String PATTERN_3_FORMAT = "yyyy-MM-dd";

    private static final String PATTERN_4="[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
    private static final String PATTERN_4_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final String PATTERN_5="[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}";
    private static final String PATTERN_5_FORMAT = "yyyy/MM/dd HH:mm:ss.S";
    private static final String PATTERN_6="[0-9]{4}/[0-9]{2}/[0-9]{2}";
    private static final String PATTERN_6_FORMAT = "yyyy/MM/dd";

    public static Date praseDate(String str){
        if (StringUtils.isEmpty(str)) return null;
        // format like: 2017-03-05T01:42:49.000Z
        str = str.trim().replace("T"," ").replace("Z","");
        // format like: 2017-03-05T09:42:49+08:00
        if (str.contains("+")) str = str.substring(0, str.indexOf("+"));
        String format = null;
        try {
            if (Pattern.matches(PATTERN_1, str)) format = PATTERN_1_FORMAT;
            else if (Pattern.matches(PATTERN_2, str)) format = PATTERN_2_FORMAT;
            else if (Pattern.matches(PATTERN_3, str)) format = PATTERN_3_FORMAT;
            else if (Pattern.matches(PATTERN_4, str)) format = PATTERN_4_FORMAT;
            else if (Pattern.matches(PATTERN_5, str)) format = PATTERN_5_FORMAT;
            else if (Pattern.matches(PATTERN_6, str)) format = PATTERN_6_FORMAT;
            else if (Pattern.matches(PATTERN_1, str)) format = PATTERN_1_FORMAT;
            if (format!=null){
                return new SimpleDateFormat(format).parse(str);
            }
            LOG.warn("No pattern found for string: {}, maybe it's not a date?", str);
        }catch (Exception e){
            LOG.error("Error to format from str to date for str:{}, format:{}", str, format, e);
        }

        return null;
    }
}
