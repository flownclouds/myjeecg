// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CodeGenerateOneToMany.java

package org.jeecgframework.codegenerate.generate.onetomany;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.codegenerate.database.JeecgReadTable;
import org.jeecgframework.codegenerate.generate.CodeGenerate;
import org.jeecgframework.codegenerate.generate.ICallBack;
import org.jeecgframework.codegenerate.pojo.Columnt;
import org.jeecgframework.codegenerate.pojo.CreateFileProperty;
import org.jeecgframework.codegenerate.pojo.onetomany.CodeParamEntity;
import org.jeecgframework.codegenerate.pojo.onetomany.SubTableEntity;
import org.jeecgframework.codegenerate.util.*;
import org.jeecgframework.codegenerate.util.def.FtlDef;
import org.jeecgframework.codegenerate.util.def.JeecgKey;

// Referenced classes of package org.jeecgframework.codegenerate.generate.onetomany:
//            CodeFactoryOneToMany

public class CodeGenerateOneToMany
    implements ICallBack
{

    public CodeGenerateOneToMany()
    {
        mainColums = new ArrayList();
        originalColumns = new ArrayList();
        subTabFtl = new ArrayList();
    }

    public CodeGenerateOneToMany(String entityPackage, String entityName, String tableName, List subTabParam, String ftlDescription, CreateFileProperty createFileProperty, String primaryKeyPolicy, 
            String sequenceCode)
    {
        mainColums = new ArrayList();
        originalColumns = new ArrayList();
        subTabFtl = new ArrayList();
        entityName = entityName;
        entityPackage = entityPackage;
        tableName = tableName;
        ftlDescription = ftlDescription;
        createFileProperty = createFileProperty;
        subTabParam = subTabParam;
        primaryKeyPolicy = StringUtils.isNotBlank(primaryKeyPolicy) ? primaryKeyPolicy : "uuid";
        sequenceCode = sequenceCode;
    }

    public CodeGenerateOneToMany(CodeParamEntity codeParamEntity)
    {
        mainColums = new ArrayList();
        originalColumns = new ArrayList();
        subTabFtl = new ArrayList();
        entityName = codeParamEntity.getEntityName();
        entityPackage = codeParamEntity.getEntityPackage();
        tableName = codeParamEntity.getTableName();
        ftlDescription = codeParamEntity.getFtlDescription();
        subTabParam = codeParamEntity.getSubTabParam();
        ftl_mode = codeParamEntity.getFtl_mode();
        primaryKeyPolicy = StringUtils.isNotBlank(codeParamEntity.getPrimaryKeyPolicy()) ? codeParamEntity.getPrimaryKeyPolicy() : "uuid";
        sequenceCode = codeParamEntity.getSequenceCode();
    }

    public Map execute()
    {
        Map data = new HashMap();
        data.put("bussiPackage", CodeResourceUtil.bussiPackage);
        data.put("entityPackage", entityPackage);
        data.put("entityName", entityName);
        data.put("tableName", tableName);
        data.put("ftl_description", ftlDescription);
        data.put("jeecg_table_id", CodeResourceUtil.JEECG_GENERATE_TABLE_ID);
        data.put(FtlDef.JEECG_PRIMARY_KEY_POLICY, primaryKeyPolicy);
        data.put(FtlDef.JEECG_SEQUENCE_CODE, sequenceCode);
        data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));
        data.put(FtlDef.FIELD_REQUIRED_NAME, Integer.valueOf(StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) ? Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) : -1));
        data.put(FtlDef.SEARCH_FIELD_NUM, Integer.valueOf(StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) ? Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) : -1));
        data.put(FtlDef.FIELD_ROW_NAME, Integer.valueOf(FIELD_ROW_NUM));
        try
        {
            mainColums = dbFiledUtil.readTableColumn(tableName);
            data.put("mainColums", mainColums);
            data.put("columns", mainColums);
            originalColumns = dbFiledUtil.readOriginalTableColumn(tableName);
            data.put("originalColumns", originalColumns);
            for(Iterator iterator = originalColumns.iterator(); iterator.hasNext();)
            {
                Columnt c = (Columnt)iterator.next();
                if(c.getFieldName().toLowerCase().equals(CodeResourceUtil.JEECG_GENERATE_TABLE_ID.toLowerCase()))
                    data.put("primary_key_type", c.getFieldType());
            }

            subTabFtl.clear();
            SubTableEntity po;
            for(Iterator iterator1 = subTabParam.iterator(); iterator1.hasNext(); subTabFtl.add(po))
            {
                SubTableEntity subTableEntity = (SubTableEntity)iterator1.next();
                po = new SubTableEntity();
                List subColum = dbFiledUtil.readTableColumn(subTableEntity.getTableName());
                po.setSubColums(subColum);
                po.setEntityName(subTableEntity.getEntityName());
                po.setFtlDescription(subTableEntity.getFtlDescription());
                po.setTableName(subTableEntity.getTableName());
                po.setEntityPackage(subTableEntity.getEntityPackage());
                String fkeys[] = subTableEntity.getForeignKeys();
                List foreignKeys = new ArrayList();
                String as[];
                int j = (as = fkeys).length;
                for(int i = 0; i < j; i++)
                {
                    String key = as[i];
                    if(CodeResourceUtil.JEECG_FILED_CONVERT)
                    {
                        foreignKeys.add(JeecgReadTable.formatFieldCapital(key));
                    } else
                    {
                        String keyStr = key.toLowerCase();
                        String field = (new StringBuilder(String.valueOf(keyStr.substring(0, 1).toUpperCase()))).append(keyStr.substring(1)).toString();
                        foreignKeys.add(field);
                    }
                }

                po.setForeignKeys((String[])foreignKeys.toArray(new String[0]));
            }

            data.put("subTab", subTabFtl);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        long serialVersionUID = NonceUtils.randomLong() + NonceUtils.currentMills();
        data.put("serialVersionUID", String.valueOf(serialVersionUID));
        return data;
    }

    public void generateToFile()
    {
        CodeFactoryOneToMany codeFactoryOneToMany = new CodeFactoryOneToMany();
        codeFactoryOneToMany.setCallBack(new CodeGenerateOneToMany());
        if(createFileProperty.isJspFlag())
        {
            codeFactoryOneToMany.invoke("onetomany/jspListTemplate.ftl", "jspList");
            codeFactoryOneToMany.invoke("onetomany/jspTemplate.ftl", "jsp");
        }
        if(createFileProperty.isServiceImplFlag())
            codeFactoryOneToMany.invoke("onetomany/serviceImplTemplate.ftl", "serviceImpl");
        if(createFileProperty.isServiceIFlag())
            codeFactoryOneToMany.invoke("onetomany/serviceITemplate.ftl", "service");
        if(createFileProperty.isActionFlag())
            codeFactoryOneToMany.invoke("onetomany/controllerTemplate.ftl", "controller");
        if(createFileProperty.isEntityFlag())
            codeFactoryOneToMany.invoke("onetomany/entityTemplate.ftl", "entity");
        if(createFileProperty.isPageFlag())
            codeFactoryOneToMany.invoke("onetomany/pageTemplate.ftl", "page");
    }

    public static void main(String args[])
    {
        List subTabParamIn = new ArrayList();
        SubTableEntity po = new SubTableEntity();
        po.setTableName("jeecg_order_custom");
        po.setEntityName("OrderCustom");
        po.setEntityPackage("order");
        po.setFtlDescription("\u8BA2\u5355\u5BA2\u6237\u660E\u7EC6");
        po.setPrimaryKeyPolicy(JeecgKey.UUID);
        po.setSequenceCode(null);
        po.setForeignKeys(new String[] {
            "GORDER_OBID", "GO_ORDER_CODE"
        });
        subTabParamIn.add(po);
        SubTableEntity po2 = new SubTableEntity();
        po2.setTableName("jeecg_order_product");
        po2.setEntityName("OrderProduct");
        po2.setEntityPackage("order");
        po2.setFtlDescription("\u8BA2\u5355\u4EA7\u54C1\u660E\u7EC6");
        po2.setForeignKeys(new String[] {
            "GORDER_OBID", "GO_ORDER_CODE"
        });
        po2.setPrimaryKeyPolicy(JeecgKey.UUID);
        po2.setSequenceCode(null);
        subTabParamIn.add(po2);
        CodeParamEntity codeParamEntityIn = new CodeParamEntity();
        codeParamEntityIn.setTableName("jeecg_order_main");
        codeParamEntityIn.setEntityName("OrderMain");
        codeParamEntityIn.setEntityPackage("order");
        codeParamEntityIn.setFtlDescription("\u8BA2\u5355\u62AC\u5934");
        codeParamEntityIn.setFtl_mode(FTL_MODE_B);
        codeParamEntityIn.setPrimaryKeyPolicy(JeecgKey.UUID);
        codeParamEntityIn.setSequenceCode(null);
        codeParamEntityIn.setSubTabParam(subTabParamIn);
        oneToManyCreate(subTabParamIn, codeParamEntityIn);
    }

    public static void oneToManyCreate(List subTabParamIn, CodeParamEntity codeParamEntityIn)
    {
        log.info((new StringBuilder("----jeecg----Code-----Generation-----[\u4E00\u5BF9\u591A\u6570\u636E\u6A21\u578B\uFF1A")).append(codeParamEntityIn.getTableName()).append("]------- \u751F\u6210\u4E2D\u3002\u3002\u3002").toString());
        CreateFileProperty subFileProperty = new CreateFileProperty();
        subFileProperty.setActionFlag(false);
        subFileProperty.setServiceIFlag(false);
        subFileProperty.setJspFlag(true);
        subFileProperty.setServiceImplFlag(false);
        subFileProperty.setPageFlag(false);
        subFileProperty.setEntityFlag(true);
        subFileProperty.setJspMode("03");
        SubTableEntity sub;
        List foreignKeys;
        for(Iterator iterator = subTabParamIn.iterator(); iterator.hasNext(); (new CodeGenerate(sub.getEntityPackage(), sub.getEntityName(), sub.getTableName(), sub.getFtlDescription(), subFileProperty, StringUtils.isNotBlank(sub.getPrimaryKeyPolicy()) ? sub.getPrimaryKeyPolicy() : "uuid", sub.getSequenceCode(), (String[])foreignKeys.toArray(new String[0]))).generateToFile())
        {
            sub = (SubTableEntity)iterator.next();
            String fkeys[] = sub.getForeignKeys();
            foreignKeys = new ArrayList();
            String as[];
            int j = (as = fkeys).length;
            for(int i = 0; i < j; i++)
            {
                String key = as[i];
                if(CodeResourceUtil.JEECG_FILED_CONVERT)
                {
                    foreignKeys.add(JeecgReadTable.formatFieldCapital(key));
                } else
                {
                    String keyStr = key.toLowerCase();
                    String field = (new StringBuilder(String.valueOf(keyStr.substring(0, 1).toUpperCase()))).append(keyStr.substring(1)).toString();
                    foreignKeys.add(field);
                }
            }

        }

        (new CodeGenerateOneToMany(codeParamEntityIn)).generateToFile();
        log.info((new StringBuilder("----jeecg----Code----Generation------[\u4E00\u5BF9\u591A\u6570\u636E\u6A21\u578B\uFF1A")).append(codeParamEntityIn.getTableName()).append("]------ \u751F\u6210\u5B8C\u6210\u3002\u3002\u3002").toString());
    }

    private static final Log log = LogFactory.getLog(CodeGenerateOneToMany.class);
    private static String entityPackage = "test";
    private static String entityName = "Person";
    private static String tableName = "person";
    private static String ftlDescription = "\u7528\u6237";
    private static String primaryKeyPolicy = "uuid";
    private static String sequenceCode = "";
    private static String ftl_mode;
    public static String FTL_MODE_A = "A";
    public static String FTL_MODE_B = "B";
    private static List subTabParam = new ArrayList();
    private static CreateFileProperty createFileProperty;
    public static int FIELD_ROW_NUM = 4;
    private List mainColums;
    private List originalColumns;
    private List subTabFtl;
    private static JeecgReadTable dbFiledUtil = new JeecgReadTable();

    static 
    {
        createFileProperty = new CreateFileProperty();
        createFileProperty.setActionFlag(true);
        createFileProperty.setServiceIFlag(true);
        createFileProperty.setJspFlag(true);
        createFileProperty.setServiceImplFlag(true);
        createFileProperty.setPageFlag(true);
        createFileProperty.setEntityFlag(true);
    }
}
