// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CgformCodeFactoryOneToMany.java

package org.jeecgframework.codegenerate.generate.onetomany;

import freemarker.template.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.generate.BaseCodeFactory;
import org.jeecgframework.codegenerate.generate.ICallBack;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;

public class CgformCodeFactoryOneToMany extends BaseCodeFactory
{

    public CgformCodeFactoryOneToMany()
    {
    }

    public void generateFile(String templateFileName, String type, Map data)
        throws TemplateException, IOException
    {
        try
        {
            String entityPackage = data.get("entityPackage").toString();
            String entityName = data.get("entityName").toString();
            String fileNamePath = getCodePath(type, entityPackage, entityName);
            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
            Template template = getConfiguration().getTemplate(templateFileName);
            FileUtils.forceMkdir(new File((new StringBuilder(String.valueOf(fileDir))).append("/").toString()));
            Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
            template.process(data, out);
            out.close();
        }
        catch(TemplateException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getProjectPath()
    {
        return projectPath;
    }

    public String getClassPath()
    {
        String path = getClass().getResource("/").getPath();
        return path;
    }

    public static void main(String args[])
    {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("./").getPath());
    }

    public String getTemplatePath()
    {
        String path = (new StringBuilder(String.valueOf(getClassPath()))).append(CodeResourceUtil.TEMPLATEPATH).toString();
        return path;
    }

    public String getCodePath(String type, String entityPackage, String entityName)
    {
        String path = getProjectPath();
        String codePath = "";
        if(packageStyle != null && CodeResourceUtil.PACKAGE_SERVICE_STYLE.equals(packageStyle))
            codePath = getCodePathServiceStyle(path, type, entityPackage, entityName);
        else
            codePath = getCodePathProjectStyle(path, type, entityPackage, entityName);
        return codePath;
    }

    public void invoke(String templateFileName, String type)
        throws TemplateException, IOException
    {
        Map data = new HashMap();
        data = callBack.execute();
        generateFile(templateFileName, type, data);
    }

    public ICallBack getCallBack()
    {
        return callBack;
    }

    public void setCallBack(ICallBack callBack)
    {
        this.callBack = callBack;
    }

    public void setProjectPath(String projectPath)
    {
        this.projectPath = projectPath;
    }

    private ICallBack callBack;
    private String projectPath;
}
