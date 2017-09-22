package com.hbhs.learning.example.common.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * Created by walter on 17-7-21.
 */
public class FileFilter {
    public static void main(String[] args) throws Exception{
        File file = new File("/var/data/kritter");
        String[] fileList = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s!=null&&s.toLowerCase().endsWith(".done");
            }
        });
        System.out.println(Arrays.toString(fileList));
    }
}
