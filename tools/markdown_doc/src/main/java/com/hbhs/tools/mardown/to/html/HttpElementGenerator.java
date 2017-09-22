package com.hbhs.tools.mardown.to.html;


class HttpElementGenerator {
    private static String REPLACEMENT_KEY_CHARSET = "#CHARSET_REPLACEMENT#";
    private static String REPLACEMENT_KEY_TITLE = "#TITLE_REPLACEMENT#";

    private static final String DEFAULT_CHARSET = "utf-8";
    private static String HTTP_STRING_AFTER_BODY =
            "</BODY></HTML>";

    private static String HTTP_STRING_BEFORE_BODY =
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head profile=\"\">\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="+REPLACEMENT_KEY_CHARSET+"\" />\n" +
                    "<title>"+REPLACEMENT_KEY_TITLE+"</title>\n" +
                    "</head><body>";

    static String httpSuffix(){ return HTTP_STRING_AFTER_BODY; }

    static String httpPrefix(String title){
        return httpPrefix(title, DEFAULT_CHARSET);
    }
    static String httpPrefix(String title, String charset){
        if (title==null) title = "";
        if (charset==null||"".equalsIgnoreCase(charset.trim())) charset = DEFAULT_CHARSET;
        return HTTP_STRING_BEFORE_BODY.replace(REPLACEMENT_KEY_CHARSET, charset)
                .replace(REPLACEMENT_KEY_TITLE, title);
    }
}
