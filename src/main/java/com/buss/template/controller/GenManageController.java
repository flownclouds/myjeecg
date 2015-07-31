package com.buss.template.controller;

import antlr.StringUtils;
import com.buss.files.entity.GenTemplateFilesEntity;
import com.buss.template.entity.GenTemplateEntity;
import com.buss.template.page.GenTemplatePage;
import com.buss.template.service.GenTemplateServiceI;
import com.buss.utils.StringSort;
import com.google.gson.Gson;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jeecgframework.codegenerate.database.JeecgReadTable;
import org.jeecgframework.codegenerate.generate.CgformCodeGenerate_Entity;
import org.jeecgframework.codegenerate.pojo.Columnt;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.dao.jdbc.SimpleJdbcTemplate;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.consts.DataBaseConst;
import org.jeecgframework.web.cgform.entity.generate.GenerateEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.mybatis.generator.api.ColumnInfo;
import org.mybatis.generator.api.MybatisGeneratorHelper;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 模板组
 * @date 2015-07-10 16:31:09
 */
@Scope("prototype")
@Controller
@RequestMapping("/genManageController")
public class GenManageController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(GenManageController.class);


    @Autowired
    private SystemService systemService;


    @RequestMapping(params = "goGenSearchTable")
    public ModelAndView goGenSearchTable(HttpServletRequest req) {
        List<Map<String, Object>> dSource = getDbSource(null);
        req.setAttribute("dsource", dSource);
        return new ModelAndView("com/buss/template/genSearchTable");
    }

    private List<Map<String, Object>> getDbSource(String id) {
        List<Map<String, Object>> result = null;
        String sql = "select id,typeinfo,keyinfo,valueinfo from gen_config where typeinfo='dbinfo'";
        if (!StringUtil.isEmpty(id)) {
            sql += " and id='" + id + "'";
        }
        result = systemService.findForJdbc(sql);
        return result;
    }

    @RequestMapping(params = "dbdatagrid")
    public void dbdatagrid(HttpServletRequest request,
                           HttpServletResponse response, DataGrid dataGrid) {
        String dbId = request.getParameter("db");
        Map<String, Object> dbConfig = getDbSource(dbId).get(0);

        List<String> list = new ArrayList<String>();
        try {
            list = new JeecgReadTable(dbConfig.get("valueinfo").toString())
                    .readAllTableNames();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String html = "";
        Collections.sort(list, new StringSort(dataGrid.getOrder()));
        html = getJson(list, list.size());
        try {
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-store");
            PrintWriter writer = response.getWriter();
            writer.println(html);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getJson(List<String> result, Integer size) {
        JSONObject main = new JSONObject();
        JSONArray rows = new JSONArray();

        for (String m : result) {
            if (m.startsWith("BIN$"))
                continue;
            JSONObject item = new JSONObject();
            item.put("id", m);
            rows.add(item);
        }
        main.put("total", rows.size());
        main.put("rows", rows);
        return main.toString();
    }


    @RequestMapping(params = "goGenCodeTemplate")
    public ModelAndView goGenCodeTemplate(String packages,String db,String tid, String fid, HttpServletRequest req) {
        String msg = "";

        GenTemplateFilesEntity entity = (GenTemplateFilesEntity) systemService.findHql("from GenTemplateFilesEntity where id=? ", fid).get(0);
        String templateStr = entity.getFileContent();

String templateName=entity.getCreateBy()+"_"+ entity.getFileName();
        try {
            //String[] arr = fid.split("$$$");
            String tb = tid;

            Map<String, Object> dbConfig = getDbSource(db).get(0);
            String connStr = dbConfig.get("valueinfo").toString();

            List<ColumnInfo> list= getColumnInfos(tb, connStr);

            Map data=new HashMap();
            data.put("package",packages);
            data.put("columns",list);
            msg= getTemplateString(templateName,templateStr,data);

        } catch (Throwable ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            StringBuffer sb = sw.getBuffer();
            msg = sb.toString();

            try {
                sw.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        req.setAttribute("msg", msg);
        return new ModelAndView("com/buss/template/genCodeTemplate");
    }
    @RequestMapping(params = "goGenCodeTemplateByModel")
    public ModelAndView goGenCodeTemplateByModel(String packages,String modelContent, String fid, HttpServletRequest req) {
        String msg = "";

        GenTemplateFilesEntity entity = (GenTemplateFilesEntity) systemService.findHql("from GenTemplateFilesEntity where id=? ", fid).get(0);
        String templateStr = entity.getFileContent();

        String templateName=entity.getCreateBy()+"_"+ entity.getFileName();
        try {


            Map data=new HashMap();
            data.put("package",packages);
            //data.put("columns",list);
            msg= getTemplateString(templateName,templateStr,data);

        } catch (Throwable ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            StringBuffer sb = sw.getBuffer();
            msg = sb.toString();

            try {
                sw.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        req.setAttribute("msg", msg);
        return new ModelAndView("com/buss/template/genCodeTemplate");
    }

    private List<ColumnInfo> getColumnInfos(String tb, String connStr) {
        Gson gs=new Gson();
        @SuppressWarnings("unchecked")
        HashMap< String,String > hm= gs.fromJson(connStr, HashMap.class);
        String dbUrl=hm.get("url");
        String dbUser=hm.get("user");
        String dbPass=hm.get("pass");
        String dbDriver=hm.get("driver");
        String dbSchema=hm.get("schema");
        JDBCConnectionConfiguration cfg= new JDBCConnectionConfiguration();
        cfg.setUserId(dbUser);
        cfg.setPassword(dbPass);
        cfg.setDriverClass(dbDriver);
        cfg.setConnectionURL(dbUrl);

        HashSet<String> tbs=new HashSet<String>();
        tbs.add(tb);

        List<ColumnInfo> list= MybatisGeneratorHelper.GetDbData(cfg, tbs,dbSchema);
        return list;
    }

    private String getTemplateString(String  templateName,String templateStr, Map data) throws IOException, TemplateException {
        Configuration cfg=new Configuration();
        StringTemplateLoader strLoader=new StringTemplateLoader();
        strLoader.putTemplate(templateName,templateStr);
        cfg.setTemplateLoader(strLoader);
        Template template=cfg.getTemplate(templateName,"utf-8");

        //Template template = Template.getPlainTextTemplate("tm", templateStr, null);
        StringWriter sw = new StringWriter();
        template.process(data, sw);
        return sw.getBuffer().toString();
    }

    private List<Map<String, Object>> getPKeys(String dbConfig, String[] tables) throws Exception {
        String str = "";
        for (String item : tables) {
            if (!StringUtil.isEmpty(item)) {
                str += "'" + item + "',";
            }
        }
        str = str.substring(0, str.length() - 1);

        StringBuilder sql1 = new StringBuilder("");
        sql1.append("select cu.* from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' ");
        sql1.append(" and au.table_name in (" + str + ") ");


        SimpleJdbcTemplate db=getJdbcTemplate(dbConfig);
        List<Map<String, Object>> list= db.findForListMap(sql1.toString(), null);
       // List<Map<String, Object>> list = systemService.findForJdbc(sql1.toString());

        return list;
    }
    private SimpleJdbcTemplate getJdbcTemplate(String dbConfig){
        Gson gs=new Gson();
        @SuppressWarnings("unchecked")
        HashMap< String,String > hm= gs.fromJson(dbConfig, HashMap.class);
        String dbUrl=hm.get("url");
        String dbUser=hm.get("user");
        String dbPass=hm.get("pass");
        String dbDriver=hm.get("driver");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPass);
        SimpleJdbcTemplate db= new SimpleJdbcTemplate(dataSource);
        return db;
    }


}
