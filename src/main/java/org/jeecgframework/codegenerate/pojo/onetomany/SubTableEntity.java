// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SubTableEntity.java

package org.jeecgframework.codegenerate.pojo.onetomany;

import java.util.List;

public class SubTableEntity
{

    public SubTableEntity()
    {
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

    public List getSubColums()
    {
        return subColums;
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

    public void setSubColums(List subColums)
    {
        this.subColums = subColums;
    }

    public String[] getForeignKeys()
    {
        return foreignKeys;
    }

    public void setForeignKeys(String foreignKeys[])
    {
        this.foreignKeys = foreignKeys;
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
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String ftlDescription;
    private String foreignKeys[];
    private List subColums;
}
