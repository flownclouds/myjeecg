//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.generate.onetomany;

import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.codegenerate.database.JeecgReadTable;
import org.jeecgframework.codegenerate.generate.CgformCodeGenerate;
import org.jeecgframework.codegenerate.generate.ICallBack;
import org.jeecgframework.codegenerate.generate.onetomany.CgformCodeFactoryOneToMany;
import org.jeecgframework.codegenerate.pojo.CreateFileProperty;
import org.jeecgframework.codegenerate.pojo.onetomany.CodeParamEntity;
import org.jeecgframework.codegenerate.pojo.onetomany.SubTableEntity;
import org.jeecgframework.codegenerate.util.CodeDateUtils;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.NonceUtils;
import org.jeecgframework.codegenerate.util.def.FtlDef;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.generate.GenerateEntity;

public class CgformCodeGenerateOneToMany implements ICallBack {
    private static final Log log = LogFactory.getLog(CgformCodeGenerateOneToMany.class);
    private String entityPackage = "test";
    private String entityName = "Person";
    private String tableName = "person";
    private String ftlDescription = "用户";
    private String primaryKeyPolicy = "uuid";
    private String sequenceCode = "";
    private static String ftl_mode;
    public static String FTL_MODE_A = "A";
    public static String FTL_MODE_B = "B";
    private static List<SubTableEntity> subTabParam = new ArrayList();
    private static CreateFileProperty createFileProperty = new CreateFileProperty();
    public static int FIELD_ROW_NUM = 4;
    private List<SubTableEntity> subTabFtl = new ArrayList();
    private CodeParamEntity codeParamEntityIn;
    private GenerateEntity mainG;
    private Map<String, GenerateEntity> subsG;
    private List<SubTableEntity> subTabParamIn;

    static {
        createFileProperty.setActionFlag(true);
        createFileProperty.setServiceIFlag(true);
        createFileProperty.setJspFlag(true);
        createFileProperty.setServiceImplFlag(true);
        createFileProperty.setPageFlag(true);
        createFileProperty.setEntityFlag(true);
    }

    public CgformCodeGenerateOneToMany() {
    }

    public CgformCodeGenerateOneToMany(List<SubTableEntity> subTabParamIn, CodeParamEntity codeParamEntityIn, GenerateEntity mainG, Map<String, GenerateEntity> subsG) {
        this.entityName = codeParamEntityIn.getEntityName();
        this.entityPackage = codeParamEntityIn.getEntityPackage();
        this.tableName = codeParamEntityIn.getTableName();
        this.ftlDescription = codeParamEntityIn.getFtlDescription();
        subTabParam = codeParamEntityIn.getSubTabParam();
        ftl_mode = codeParamEntityIn.getFtl_mode();
        this.primaryKeyPolicy = "uuid";
        this.sequenceCode = codeParamEntityIn.getSequenceCode();
        this.subTabParamIn = subTabParamIn;
        this.mainG = mainG;
        this.subsG = subsG;
        this.codeParamEntityIn = codeParamEntityIn;
    }

    public Map<String, Object> execute() {
        HashMap data = new HashMap();
        data.put("bussiPackage", CodeResourceUtil.bussiPackage);
        data.put("entityPackage", this.entityPackage);
        data.put("entityName", this.entityName);
        data.put("tableName", this.tableName);
        data.put("ftl_description", this.ftlDescription);
        data.put("jeecg_table_id", CodeResourceUtil.JEECG_GENERATE_TABLE_ID);
        data.put(FtlDef.JEECG_PRIMARY_KEY_POLICY, this.primaryKeyPolicy);
        data.put(FtlDef.JEECG_SEQUENCE_CODE, this.sequenceCode);
        data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));
        data.put(FtlDef.FIELD_REQUIRED_NAME, Integer.valueOf(StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM)?Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM):-1));
        data.put(FtlDef.SEARCH_FIELD_NUM, Integer.valueOf(StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM)?Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM):-1));
        data.put(FtlDef.FIELD_ROW_NAME, Integer.valueOf(FIELD_ROW_NUM));

        try {
            HashMap serialVersionUID = new HashMap();
            List columns = this.mainG.deepCopy().getCgFormHead().getColumns();
            Iterator subtables = columns.iterator();

            while(subtables.hasNext()) {
                CgFormFieldEntity pageColumns = (CgFormFieldEntity)subtables.next();
                String subColumnsMap = pageColumns.getType();
                if("string".equalsIgnoreCase(subColumnsMap)) {
                    pageColumns.setType("java.lang.String");
                } else if("Date".equalsIgnoreCase(subColumnsMap)) {
                    pageColumns.setType("java.util.Date");
                } else if("double".equalsIgnoreCase(subColumnsMap)) {
                    pageColumns.setType("java.lang.Double");
                } else if("int".equalsIgnoreCase(subColumnsMap)) {
                    pageColumns.setType("java.lang.Integer");
                } else if("BigDecimal".equalsIgnoreCase(subColumnsMap)) {
                    pageColumns.setType("java.math.BigDecimal");
                } else if("Text".equalsIgnoreCase(subColumnsMap)) {
                    pageColumns.setType("javax.xml.soap.Text");
                } else if("Blob".equalsIgnoreCase(subColumnsMap)) {
                    pageColumns.setType("java.sql.Blob");
                }

                String subPageColumnsMap = pageColumns.getFieldName();
                String subFieldMeta = JeecgReadTable.formatField(subPageColumnsMap);
                pageColumns.setFieldName(subFieldMeta);
                serialVersionUID.put(subFieldMeta, subPageColumnsMap.toUpperCase());
            }

            ArrayList pageColumns1 = new ArrayList();
            Iterator subColumnsMap1 = columns.iterator();

            while(subColumnsMap1.hasNext()) {
                CgFormFieldEntity subtables1 = (CgFormFieldEntity)subColumnsMap1.next();
                if(StringUtils.isNotEmpty(subtables1.getIsShow()) && "Y".equalsIgnoreCase(subtables1.getIsShow())) {
                    pageColumns1.add(subtables1);
                }
            }

            String[] subtables2 = this.mainG.getCgFormHead().getSubTableStr().split(",");
            data.put("cgformConfig", this.mainG);
            data.put("fieldMeta", serialVersionUID);
            data.put("columns", columns);
            data.put("pageColumns", pageColumns1);
            data.put("buttons", this.mainG.getButtons() == null?new ArrayList(0):this.mainG.getButtons());
            data.put("buttonSqlMap", this.mainG.getButtonSqlMap() == null?new HashMap(0):this.mainG.getButtonSqlMap());
            data.put("subtables", subtables2);
            data.put("subTab", this.subTabParamIn);
            HashMap subColumnsMap2 = new HashMap(0);
            HashMap subPageColumnsMap1 = new HashMap(0);
            HashMap subFieldMeta1 = new HashMap(0);
            //HashMap subFieldMeta1 = new HashMap(0);
            Iterator var11 = this.subsG.keySet().iterator();

            while(var11.hasNext()) {
                String key = (String)var11.next();
                GenerateEntity subG = (GenerateEntity)this.subsG.get(key);
                List subColumns = subG.deepCopy().getCgFormHead().getColumns();
                ArrayList subPageColumns = new ArrayList();
                Iterator var16 = subColumns.iterator();

                while(var16.hasNext()) {
                    CgFormFieldEntity cf = (CgFormFieldEntity)var16.next();
                    String type = cf.getType();
                    if("string".equalsIgnoreCase(type)) {
                        cf.setType("java.lang.String");
                    } else if("Date".equalsIgnoreCase(type)) {
                        cf.setType("java.util.Date");
                    } else if("double".equalsIgnoreCase(type)) {
                        cf.setType("java.lang.Double");
                    } else if("int".equalsIgnoreCase(type)) {
                        cf.setType("java.lang.Integer");
                    } else if("BigDecimal".equalsIgnoreCase(type)) {
                        cf.setType("java.math.BigDecimal");
                    } else if("Text".equalsIgnoreCase(type)) {
                        cf.setType("javax.xml.soap.Text");
                    } else if("Blob".equalsIgnoreCase(type)) {
                        cf.setType("java.sql.Blob");
                    }

                    String fieldName = cf.getFieldName();
                    String fieldNameV = JeecgReadTable.formatField(fieldName);
                    cf.setFieldName(fieldNameV);
                    subFieldMeta1.put(fieldNameV, fieldName.toUpperCase());
                    subFieldMeta1.put(fieldName.toUpperCase(), fieldNameV);
                    if(StringUtils.isNotEmpty(cf.getIsShow()) && "Y".equalsIgnoreCase(cf.getIsShow())) {
                        subPageColumns.add(cf);
                    }

                    String mtable = cf.getMainTable();
                    String mfiled = cf.getMainField();
                    if(mtable != null && mtable.equalsIgnoreCase(this.mainG.getTableName())) {
                        data.put(key + "_fk", mfiled);
                    }

                    subColumnsMap2.put(key, subColumns);
                    subPageColumnsMap1.put(key, subPageColumns);
                }

                data.put("subColumnsMap", subColumnsMap2);
                data.put("subPageColumnsMap", subPageColumnsMap1);
                data.put("subFieldMeta", subFieldMeta1);
                data.put("subFieldMeta1", subFieldMeta1);
                data.put("packageStyle", this.mainG.getPackageStyle());
            }
        } catch (Exception var22) {
            var22.printStackTrace();
        }

        long serialVersionUID1 = NonceUtils.randomLong() + NonceUtils.currentMills();
        data.put("serialVersionUID", String.valueOf(serialVersionUID1));
        return data;
    }

    public void generateToFile() throws TemplateException, IOException {
        CgformCodeFactoryOneToMany codeFactoryOneToMany = new CgformCodeFactoryOneToMany();
        codeFactoryOneToMany.setProjectPath(this.mainG.getProjectPath());
        codeFactoryOneToMany.setPackageStyle(this.mainG.getPackageStyle());
        codeFactoryOneToMany.setCallBack(new CgformCodeGenerateOneToMany(this.subTabParamIn, this.codeParamEntityIn, this.mainG, this.subsG));
        if(createFileProperty.isJspFlag()) {
            codeFactoryOneToMany.invoke("onetomany/cgform_jspListTemplate.ftl", "jspList");
            codeFactoryOneToMany.invoke("onetomany/cgform_jspTemplate_add.ftl", "jsp_add");
            codeFactoryOneToMany.invoke("onetomany/cgform_jspTemplate_update.ftl", "jsp_update");
            codeFactoryOneToMany.invoke("onetomany/cgform_jsEnhanceTemplate.ftl", "js");
            codeFactoryOneToMany.invoke("onetomany/cgform_jsListEnhanceTemplate.ftl", "jsList");
        }

        if(createFileProperty.isServiceImplFlag()) {
            codeFactoryOneToMany.invoke("onetomany/cgform_serviceImplTemplate.ftl", "serviceImpl");
        }

        if(createFileProperty.isServiceIFlag()) {
            codeFactoryOneToMany.invoke("onetomany/cgform_serviceITemplate.ftl", "service");
        }

        if(createFileProperty.isActionFlag()) {
            codeFactoryOneToMany.invoke("onetomany/cgform_controllerTemplate.ftl", "controller");
        }

        if(createFileProperty.isEntityFlag()) {
            codeFactoryOneToMany.invoke("onetomany/cgform_entityTemplate.ftl", "entity");
        }

        if(createFileProperty.isPageFlag()) {
            codeFactoryOneToMany.invoke("onetomany/cgform_pageTemplate.ftl", "page");
        }

    }

    public static void oneToManyCreate(List<SubTableEntity> subTabParamIn, CodeParamEntity codeParamEntityIn, GenerateEntity mainG, Map<String, GenerateEntity> subsG) throws TemplateException, IOException {
        log.info("----jeecg----Code-----Generation-----[一对多数据模型：" + codeParamEntityIn.getTableName() + "]------- 生成中。。。");
        CreateFileProperty subFileProperty = new CreateFileProperty();
        subFileProperty.setActionFlag(false);
        subFileProperty.setServiceIFlag(false);
        subFileProperty.setJspFlag(true);
        subFileProperty.setServiceImplFlag(false);
        subFileProperty.setPageFlag(false);
        subFileProperty.setEntityFlag(true);
        subFileProperty.setJspMode("03");
        Iterator var6 = subTabParamIn.iterator();

        while(var6.hasNext()) {
            SubTableEntity sub = (SubTableEntity)var6.next();
            String[] foreignKeys = sub.getForeignKeys();
            GenerateEntity subG = (GenerateEntity)subsG.get(sub.getTableName());
            (new CgformCodeGenerate(sub, subG, subFileProperty, "uuid", foreignKeys)).generateToFile();
        }

        (new CgformCodeGenerateOneToMany(subTabParamIn, codeParamEntityIn, mainG, subsG)).generateToFile();
        log.info("----jeecg----Code----Generation------[一对多数据模型：" + codeParamEntityIn.getTableName() + "]------ 生成完成。。。");
    }
}
