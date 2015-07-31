//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.generate;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.generate.BaseCodeFactory;
import org.jeecgframework.codegenerate.generate.ICallBack;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.CodeStringUtils;

public class CodeFactory extends BaseCodeFactory {
    private ICallBack callBack;

    public CodeFactory() {
    }

    public void generateFile(String templateFileName, String type, Map data) {
        try {
            String e = data.get("entityPackage").toString();
            String entityName = data.get("entityName").toString();
            String fileNamePath = this.getCodePath(type, e, entityName);
            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
            Template template = this.getConfiguration().getTemplate(templateFileName);
            FileUtils.forceMkdir(new File(fileDir + "/"));
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
            template.process(data, out);
            out.close();
        } catch (TemplateException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

    }

    public static String getProjectPath() {
        String path = System.getProperty("user.dir").replace("\\", "/") + "/";
        return path;
    }

    public String getClassPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
        return path;
    }

    public static void main(String[] args) {
        System.out.println(getProjectPath());
    }

    public String getTemplatePath() {
        String path = this.getClassPath() + CodeResourceUtil.TEMPLATEPATH;
        return path;
    }

    public String getCodePath(String type, String entityPackage, String entityName) {
        String path = getProjectPath();
        StringBuilder str = new StringBuilder();
        if(!StringUtils.isNotBlank(type)) {
            throw new IllegalArgumentException("type is null");
        } else {
            String codeType = ((CodeType)Enum.valueOf(CodeType.class, type)).getValue();
            str.append(path);
            if(!"jsp".equals(type) && !"jspList".equals(type)) {
                str.append(CodeResourceUtil.CODEPATH);
            } else {
                str.append(CodeResourceUtil.JSPPATH);
            }

            if("Action".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("action"));
            } else if("ServiceImpl".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("service/impl"));
            } else if("ServiceI".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("service"));
            } else if(!"List".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase(codeType));
            }

            str.append("/");
            str.append(StringUtils.lowerCase(entityPackage));
            str.append("/");
            if(!"jsp".equals(type) && !"jspList".equals(type)) {
                str.append(StringUtils.capitalize(entityName));
                str.append(codeType);
                str.append(".java");
            } else {
                String jspName = StringUtils.capitalize(entityName);
                str.append(CodeStringUtils.getInitialSmall(jspName));
                str.append(codeType);
                str.append(".jsp");
            }

            return str.toString();
        }
    }

    public void invoke(String templateFileName, String type) {
        new HashMap();
        Map data = this.callBack.execute();
        this.generateFile(templateFileName, type, data);
    }

    public ICallBack getCallBack() {
        return this.callBack;
    }

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    public static enum CodeType {
        serviceImpl("ServiceImpl"),
        service("ServiceI"),
        controller("Controller"),
        entity("Entity"),
        jsp(""),
        jspList("List");

        private String type;

        private CodeType(String type) {
            this.type = type;
        }

        public String getValue() {
            return this.type;
        }
    }
}
