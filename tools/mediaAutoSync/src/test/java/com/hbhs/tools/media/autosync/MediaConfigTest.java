package com.hbhs.tools.media.autosync;

import com.hbhs.tools.media.autosync.entity.MediaConfigEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


public class MediaConfigTest extends BaseTest {

    @Autowired
    private MediaConfigEntity mediaConfigEntity;
    @Test
    public void mediaConfigEntity() throws Exception {
        assertNotNull(mediaConfigEntity);
    }

}