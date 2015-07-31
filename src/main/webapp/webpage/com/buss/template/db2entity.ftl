<#list columns as po>
package ${(package=="")?string('default.package',package)}.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
* auto generator by shilin on ${.now?datetime}.
*/

@Entity
@Table(name = "${po.tableName}", schema = "${po.schema}")
public class ${po.javaTableProperty}Entity {
    <#list po.listColumn as c>
    <#if (c.isPKey()?string("true","false")=="true") && c.javaType?index_of("BigDecimal")!=-1>
    private Long ${c.javaProperty};
    <#else>
    private ${c.javaType} ${c.javaProperty};
    </#if>
    </#list>
    <#list po.listColumn as c>
    /**
    * ${c.remarks!""}
    */
    <#if c.isPKey()?string("true","false") == "true">
    @Id
    @SequenceGenerator(name="SEQ_${po.tableName}", sequenceName="SEQ_${po.tableName}", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_${po.tableName}")
    @Column(name ="${c.dbColumnName}")
    <#if c.javaType?index_of("BigDecimal")!=-1>
    public Long get${c.javaProperty?cap_first}(){return ${c.javaProperty};}
    public void set${c.javaProperty?cap_first}(Long ${c.javaProperty}){ this.${c.javaProperty}=${c.javaProperty};}
    <#else>
    public ${c.javaType} get${c.javaProperty?cap_first}(){return ${c.javaProperty};}
    public void set${c.javaProperty?cap_first}(${c.javaType} ${c.javaProperty}){ this.${c.javaProperty}=${c.javaProperty};}
    </#if>
    <#else>
    @Column(name ="${c.dbColumnName}")
    public ${c.javaType} get${c.javaProperty?cap_first}(){return ${c.javaProperty};}
    public void set${c.javaProperty?cap_first}(${c.javaType} ${c.javaProperty}){ this.${c.javaProperty}=${c.javaProperty};}
    </#if>
    </#list>
}
</#list>