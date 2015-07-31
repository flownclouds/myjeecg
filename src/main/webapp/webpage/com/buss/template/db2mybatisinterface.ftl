<#list columns as po>
    <#assign keycoltype=""/>
    <#list po.getPkeyColumn() as k>
        <#if k.javaType?index_of("BigDecimal")!=-1>
            <#assign keycoltype=keycoltype+"java.lang.Long "+k.javaProperty+","/>
        <#else>
            <#assign keycoltype=keycoltype+k.javaType +" "+k.javaProperty+","/>
        </#if>
    </#list>
    <#if keycoltype!="">
        <#assign keycoltype=keycoltype?substring(0,keycoltype?length-1)/>
    </#if>
    <#assign pkg="${(package==\"\")?string('default.package',package)}"/>
package ${pkg}.mapper;
import ${pkg}.entity.${po.javaTableProperty}Entity;
import java.math.BigDecimal;
public interface ${po.javaTableProperty}Mapper {
    ${po.javaTableProperty}Entity selectByPrimaryKey(${keycoltype});
}
</#list>