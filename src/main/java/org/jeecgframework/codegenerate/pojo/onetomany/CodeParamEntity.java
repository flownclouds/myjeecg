// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CodeParamEntity.java

package org.jeecgframework.codegenerate.pojo.onetomany;

import java.util.List;

public class CodeParamEntity
{

    public CodeParamEntity()
    {
        ftl_mode = "A";
    }

    public List getSubTabParam()
    {
        return subTabParam;
    }

    public void setSubTabParam(List subTabParam)
    {
        this.subTabParam = subTabParam;
    }

    public String getEntityPackage()
    {
        return entityPackage;
    }

    public String getTableName()
    {
        return tableName;
    }

    public String getEntityName()
    {
        return entityName;
    }

    public String getFtlDescription()
    {
        return ftlDescription;
    }

    public void setEntityPackage(String entityPackage)
    {
        this.entityPackage = entityPackage;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public void setFtlDescription(String ftlDescription)
    {
        this.ftlDescription = ftlDescription;
    }

    public String getFtl_mode()
    {
        return ftl_mode;
    }

    public void setFtl_mode(String ftl_mode)
    {
        this.ftl_mode = ftl_mode;
    }

    public String getPrimaryKeyPolicy()
    {
        return primaryKeyPolicy;
    }

    public String getSequenceCode()
    {
        return sequenceCode;
    }

    public void setPrimaryKeyPolicy(String primaryKeyPolicy)
    {
        this.primaryKeyPolicy = primaryKeyPolicy;
    }

    public void setSequenceCode(String sequenceCode)
    {
        this.sequenceCode = sequenceCode;
    }

    private String entityPackage;
    private String tableName;
    private String entityName;
    private String ftlDescription;
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String ftl_mode;
    List subTabParam;
}
