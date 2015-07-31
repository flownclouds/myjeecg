<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px; ">
   <t:tabs id="tt" iframe="true" tabPosition="top" fit="false" heigth="90%">
    <t:tab  icon="icon-search" cache="true"   title="代码生成配置" id="genConfig"
            href="genTemplateController.do?goGenConfig" ></t:tab>
   </t:tabs>
  </div>
 </div>


<script type="text/javascript">
function addTabs(arr,tb,txt_package){
var nextall=$('.tabs-selected').nextAll();
nextall.each(function(i, n) {
var t = $('a:eq(0) span', $(n)).text();
$('#tt').tabs('close', t);
});



$.each(arr,function(i,item){
var _ar=item.split("$$$");

var fid=_ar[1];
var tid=tb.split("$$$")[2];
var dbid= tb.split("$$$")[1];

    var title=_ar[0];
    var url= "genManageController.do?goGenCodeTemplate&fid="+fid+"&db="+dbid+"&tid="+tid+"&packages="+txt_package;

    var content='<iframe id="' + fid + '" scrolling="no" frameborder="0"  src="'+url+'" width="100%" height="90%"></iframe>';
    //addtt(title,url,fid,'icon-search','false');
$('#tt').tabs('add', {
title : _ar[0],
    content:content,
//href : "genManageController.do?goGenCodeTemplate&fid="+fid+"&db="+dbid+"&tid="+tid+"&packages="+txt_package,
closable : true
//icon : icon
});
});
}

//function addtt(title, url, id, icon, closable) {
//    $('#tt').tabs('add', {id: id,title: title,content: createFramett(id),closable: closable = (closable == 'false') ? false : true,icon: icon});
//}
//function createFramett(id) {
//    var s = '<iframe id="' + id + '" scrolling="no" frameborder="0"  src="about:jeecg" width="100%" height="99.5%"></iframe>';
//    return s;
//}
</script>

