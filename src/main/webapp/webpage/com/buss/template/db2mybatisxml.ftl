<#list columns as po>
    <#assign pkg="${(package==\"\")?string('default.package',package)}"/>
    <#assign keycoltype=""/>
    <#assign keycolwhere=""/>
    <#list po.getPkeyColumn() as k>
    <#if k.javaType?index_of("BigDecimal")!=-1>
        <#assign keycoltype=keycoltype+"java.lang.Long"+","/>
    <#else>
        <#assign keycoltype=keycoltype+k.javaType+","/>
    </#if>
        <#assign keycolwhere=keycolwhere+k.dbColumnName+r"=#{"+k.javaProperty+",jdbcType="+k.dbType+r"}"+" and"/>
    </#list>
<#if keycoltype!="">
    <#assign keycoltype=keycoltype?substring(0,keycoltype?length-1)/>
    <#assign keycolwhere=keycolwhere?substring(0,keycolwhere?length-3)/>
</#if>
<?xml version="1.0" encoding="UTF-8" ?>
<!--auto generator by shilin on ${.now?datetime}.-->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${pkg}.mapper.${po.javaTableProperty}Mapper" >
<resultMap id="BaseResultMap" type="${pkg}.${po.javaTableProperty}Entity" >
<#list po.listColumn as c>
<#if (c.isPKey()?string("true","false")=="true") >
    <id column="${c.dbColumnName}" property="${c.javaProperty}" jdbcType="${c.dbType}" />
<#else>
    <result column="${c.dbColumnName}" property="${c.javaProperty}" jdbcType="${c.dbType}" />
</#if>
</#list>
</resultMap>
<sql id="Base_Column_List" >
    ${po.getDbColumnsStr()}
</sql>
<select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="${keycoltype}" >
    select
    <include refid="Base_Column_List" />
    from ${po.tableName}
    where ${keycolwhere}
</select>
</mapper>
</#list>