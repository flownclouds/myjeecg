package com.buss.utils;

import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class UrlRootTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    protected String url = "";

    public void setUrl(String url) {
        this.url = url;
    }

    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }

    public int doEndTag() throws JspException {
        try {
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path;
            JspWriter out = this.pageContext.getOut();
            String result = "";
            if (url == "") {
                result = basePath;
            } else {
                result =basePath+"/"+url;
            }
            out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }


}

