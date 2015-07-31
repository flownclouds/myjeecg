<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
<head>
 <title>数据配置信息</title>
 <t:base type="jquery,easyui,jqueryui-sortable"></t:base>
 <script type="text/javascript" src="${mf:resource("webpage/com/buss/js/common.js")}"></script>
</head>
<body>

<div>
 <div class="dleft1">
  <div class="mg2">
   <div class="mg2">
   <label>1.请选择数据表</label>
   <label class="mg mg3" id="lbl_tb"></label>
   <input class="mg none" type="text" name="tbid"  id="txt_tb" />
   <%--<input class="mg" type="button" value="选择"--%>
          <%--onclick="return tb_search('选择数据表','genManageController.do?goGenSearchTable');"/>--%>
   </div>
   <iframe src="genManageController.do?goGenSearchTable" frameborder="0" style="border:0;width:100%;height:580px;" ></iframe>
  </div>
 </div>
 <div class="dleft2">

  <div class="mg2">
   <div class="mg2">
   <label>2.请选择代码模板</label>
   <select name="db" id="drptdb" onchange="changeTemplate()">
    <option value="-1">请选择</option>
    <c:forEach var="item" items="${infos}">
     <option value="${item["tid"]}" files="${fn:escapeXml(item["files"])}">${item["tmplate_name"]}</option>
    </c:forEach>
   </select>
   <div id="div_fileitem">

   </div>

    </div>
   <div style="clear:both;"></div>
  </div>
  <div class="mg2">
   <label>3.请填写包名</label>
   <input type="text" id="txt_package" maxlength="50" style="width: 150px;">
  </div>

  <div class="mg2">
   <input class="mg" type="button" value="生成代码"
          onclick="return genCode();"/>
  </div>
 </div>
 <div class="dleft3"></div>
</div>







<style type="text/css">
 .mg{ margin: 0 0 0 20px;}
 .none{ display:none;}
 .mg2{margin:20px 0 20px 0; font-size: 16px;}
 .ckmg{margin: 8px 0 0 0; font-size: 16px;}

 .dleft1{float:left;width:600px;height:600px;}
 .dleft2{float:left;height:600px;margin:0 0 0 30px;}
 .dleft3{clear:both;}
 .mg3{ font-size:13pt; zoom: 150%; }



 #nav ul{list-style:none;margin-left:5px; }
 #nav li{display:block;float:left;width:250px;}
</style>
<script type="text/javascript">
 $(document).ready(function(){


 });
 function genCode(){
  var tb=$("#txt_tb").val();
  if(tb==""){
   alert("请选择数据表！");
   return;
  }

  var arr=[];
  $("#div_fileitem").find("input").each(function(i,item){
           if($(item).attr("checked")=="checked") {
            var fid = $(item).attr("file");
            arr.push(fid);
           }
          }
  );
  if(arr.length==0){
   alert("请选择代码模板！");
   return;
  }
  var txt_package=$.trim($("#txt_package").val());

 parent.addTabs(arr,tb,txt_package);



 }


 function changeTemplate() {
  var f=$("#drptdb option:selected").attr("files");
  if(f==undefined){
   $("#div_fileitem").html("");
   return;
  }
  var obj= $.parseJSON(f);

  var chk="<div id='nav'>";

  $.each(obj,function(i,item){
   if((i+1)%2==1){chk+="<ul class='ckmg'>";}
   chk+="<li ><label class='mg3'><input type='checkbox' file='"+item.fileName+"$$$"+item.id+"'/>"+item.fileName+"</label></li> ";
   if((i+1)%2==0||i+1==obj.length){chk+="</ul>";}
  });
  chk+="</div>";
  $("#div_fileitem").html(chk);
 }
 function tb_search(title,url){
  $.dialog({
   content: "url:"+url,
   lock : true,
   title:title,
   opacity : 0.3,
   width:600,
   height:500,
   cache:false,
   cancelVal: '关闭',
   ok:function(){

    var ids=this.iframe.contentWindow.getSelectItem();
    if(ids!="") {
     var arr= ids.split('$$$');

     $("#txt_tb").val(ids);
     $("#lbl_tb").text(arr[0]+"->"+arr[2]);
    }
    return true;
   },
   cancel: true /*为true等价于function(){}*/
  });
 }
 function setTbInfo(ids){
  if(ids!="") {
   var arr= ids.split('$$$');

   $("#txt_tb").val(ids);
   $("#lbl_tb").text(arr[0]+"->"+arr[2]);
  }
 }



</script>

</body>
</html>