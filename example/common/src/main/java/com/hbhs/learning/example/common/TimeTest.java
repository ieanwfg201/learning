package com.hbhs.learning.example.common;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by walter on 17-4-25.
 */
public class TimeTest {

    @Test
    public void testCase() throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long a = 1493028718;
        long a = 1493388987;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(a*1000);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss s").format(cal.getTime()));
        Date date =df.parse("2017-04-24 18:00:00");
        System.out.println("start:"+date.getTime()/1000);
        date =df.parse("2017-04-24 17:00:00");
        System.out.println("end:"+date.getTime()/1000);
    }
}
