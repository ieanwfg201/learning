package com.hbhs.tools.mardown.to.html;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by walter on 17-9-1.
 */
public class HttpElementGeneratorTest {
    @Test
    public void httpPrefix() throws Exception {
        System.out.println(HttpElementGenerator.httpPrefix("aaaaaa"));
        System.out.println(HttpElementGenerator.httpPrefix("adsfsfsdfs", "GBK"));
    }

    @Test
    public void httpPrefix1() throws Exception {
        System.out.println(HttpElementGenerator.httpSuffix());
    }

}