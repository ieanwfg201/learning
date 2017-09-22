package com.hbhs.tools.mardown.to.html;

import org.junit.Test;

import java.io.File;


public class MarkdownTransformTest {

    @Test
    public void markdownToHtml() throws Exception {
        String filePath = "/services/git/GIthub/learning/knowledge/java/thread/聊聊并发/聊聊并发（二）Java SE1.6中的Synchronized.md";
        new MarkdownTransform().markdownToHtml(new File(filePath));
    }
}