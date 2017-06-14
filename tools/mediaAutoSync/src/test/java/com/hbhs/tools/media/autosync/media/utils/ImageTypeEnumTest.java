package com.hbhs.tools.media.autosync.media.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by walter on 17-6-9.
 */
public class ImageTypeEnumTest {
    @Test
    public void getContextTypeByFileName() throws Exception {
        System.out.println(ImageTypeEnum.getContextTypeByFileName(ImageTypeEnum.BMP.getImageType()));
        System.out.println(ImageTypeEnum.getContextTypeByFileName(ImageTypeEnum.JPEG.getImageType()));
        System.out.println(ImageTypeEnum.getContextTypeByFileName(ImageTypeEnum.JPG.getImageType()));
        System.out.println(ImageTypeEnum.getContextTypeByFileName(ImageTypeEnum.PNG.getImageType()));
        System.out.println(ImageTypeEnum.getContextTypeByFileName("JPG"));
    }

}