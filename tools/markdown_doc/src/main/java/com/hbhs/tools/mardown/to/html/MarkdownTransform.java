package com.hbhs.tools.mardown.to.html;


import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.*;
import java.util.Arrays;

public class MarkdownTransform {
    private Parser parser = null;
    private HtmlRenderer htmlRenderer = null;

    private MarkdownTransformBuilder builder;

    private MarkdownTransform(MarkdownTransformBuilder builder){
        this.builder = builder;
    }
    public static class MarkdownTransformBuilder{
        private boolean markdownToHtml;
        private boolean markdownToPdf;
        private String markdownFileSuffix = ".md";
        private String htmlFileSuffix = ".html";
        private String charset = "utf-8";

        public MarkdownTransformBuilder(){
            this(true, false);
        }
        public MarkdownTransformBuilder(boolean pdf){
            this(!pdf, pdf);
        }
        public MarkdownTransformBuilder(boolean html, boolean pdf){
            this.markdownToHtml = html;
            this.markdownToPdf = pdf;
        }
        public MarkdownTransformBuilder markdownFileSuffix(String markdownFileSuffix){
            if (markdownFileSuffix!=null&&!"".equals(markdownFileSuffix))
                this.markdownFileSuffix = markdownFileSuffix;
            return this;
        }
        public MarkdownTransformBuilder htmlFileSuffix(String htmlFileSuffix){
            if (htmlFileSuffix!=null&&!"".equals(htmlFileSuffix.trim()))
                this.htmlFileSuffix = htmlFileSuffix;
            return this;
        }
        public MarkdownTransformBuilder charset(String charset){
            if (charset!=null&&!"".equals(charset.trim()))
                this.charset = charset;
            return this;
        }
        public MarkdownTransform build(){
            return new MarkdownTransform(this);
        }
    }

    public MarkdownTransform(){
        MutableDataHolder options = new MutableDataSet()
                .set(Parser.REFERENCES_KEEP, KeepType.LAST)
                .set(HtmlRenderer.INDENT_SIZE, 2)
                .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
                .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()))
                ;
        parser = Parser.builder(options).build();
        htmlRenderer = HtmlRenderer.builder(options).build();
    }


    public void transMarkdown(File sourceFileOrFolder) throws Exception{
        transMarkdown(sourceFileOrFolder, null);
    }

    public void transMarkdown(File sourceFileOrFolder, String targetFileOrFolder) throws Exception{
        if (sourceFileOrFolder==null||!sourceFileOrFolder.exists()) return ;
        if (targetFileOrFolder==null||"".equalsIgnoreCase(targetFileOrFolder.trim())){
            targetFileOrFolder = sourceFileOrFolder.getParent();
        }
        if (sourceFileOrFolder.isDirectory()){
            File[] subFileArray = sourceFileOrFolder.listFiles();
            if (subFileArray!=null&&subFileArray.length>0) {
                for (File file : subFileArray) {
                    transMarkdown(sourceFileOrFolder,
                            targetFileOrFolder+File.separator+file.getName());
                }
            }
            return ;
        }
        String sourceFileName = sourceFileOrFolder.getName();
        if (!sourceFileName.toLowerCase().endsWith(builder.markdownFileSuffix)) return ;
        String targetFile = targetFileOrFolder + File.separator+sourceFileName.substring(0, sourceFileName.
                indexOf(builder.markdownFileSuffix))
                + builder.htmlFileSuffix;

        transMarkdownFile(sourceFileOrFolder, new File(targetFile));
    }

    public void markdownToOneHtml(File sourceFileOrFolder) throws Exception{
        markdownToOneHtml(sourceFileOrFolder, null);
    }
    public void markdownToOneHtml(File sourceFileOrFolder, String targetFileOrFolder) throws Exception{
        if (sourceFileOrFolder==null||!sourceFileOrFolder.exists()) return ;
        if (sourceFileOrFolder.isDirectory()){
            if (targetFileOrFolder==null||targetFileOrFolder.equals("")){
                targetFileOrFolder = sourceFileOrFolder.getPath()+File.separator+builder.htmlFileSuffix;
            }else{
                targetFileOrFolder += File.separator + sourceFileOrFolder.getPath() + builder.htmlFileSuffix;
            }
            FileWriter writer = null;
            try {
                writer = createFileWriter(new File(targetFileOrFolder), builder.charset);
                File[] subFileArray = sourceFileOrFolder.listFiles();
                if (subFileArray!=null&&subFileArray.length>0) {
                    for (File file : subFileArray) {
                        transMarkdown(sourceFileOrFolder,
                                targetFileOrFolder+File.separator+file.getName());
                    }
                }
            }finally {
                closeFileWriter(writer);
            }
            return ;
        }
        transMarkdown(sourceFileOrFolder, targetFileOrFolder);
    }

    private void transMarkdownFile(File sourceFile, File targetFile) throws Exception{
        FileWriter writer = null;
        try {
            writer = createFileWriter(targetFile, sourceFile.getName().substring(0, sourceFile.getName().indexOf(builder.markdownFileSuffix)));
            transMarkdownToHtml(sourceFile, writer);
        }finally {
            closeFileWriter(writer);
        }
    }

    private void transMarkdownToHtml(File sourceFile, FileWriter writer) throws Exception{
        if (sourceFile==null||writer==null) return ;
        if (!sourceFile.exists()) return ;
        FileReader reader = null;
        try {
            reader = new FileReader(sourceFile);
            Node node = parser.parseReader(reader);
            htmlRenderer.render(node, writer);
        }finally{
            if (reader!=null) reader.close();
        }
    }

    private FileWriter createFileWriter(File file, String title) throws Exception{
        FileWriter writer = new FileWriter(file);
        writer.write(HttpElementGenerator.httpPrefix(title==null||"".equals(title)?file.getName():title, builder.charset));
        writer.write("\n");
        writer.flush();
        return writer;
    }

    private void closeFileWriter(FileWriter writer) throws Exception {
        if (writer!=null) writer.close();
    }
}
