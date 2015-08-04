public static ${info.className}DTO get${info.className}DTO(${info.className}Entity info){
    if(info==null) return null;
    ${info.className}DTO target=new ${info.className}DTO();
<#list info.listField as po>
    target.set${po.name?cap_first}(info.get${po.name?cap_first}());
</#list>
    return target;
}
