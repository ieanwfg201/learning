package com.hbhs.tools.media.autosync.media.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class DatePraserTest {

    @Test
    public void testA(){
        assertNotNull(DatePraser.praseDate("2017-03-05"));
        assertNotNull(DatePraser.praseDate("2017-03-05 01:42:49"));
        assertNotNull(DatePraser.praseDate("2017-03-05T01:42:49Z"));
        assertNotNull(DatePraser.praseDate("2017/04/05"));
        assertNotNull(DatePraser.praseDate("2017/03/05 01:42:49"));
        assertNotNull(DatePraser.praseDate("2017/03/05T01:42:49Z"));
    }
}