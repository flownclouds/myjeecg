<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>模板组</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="genTemplateController.do?doAdd">
					<input id="id" name="id" type="hidden" value="${genTemplatePage.id }">
					<input id="createName" name="createName" type="hidden" value="${genTemplatePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${genTemplatePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${genTemplatePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${genTemplatePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${genTemplatePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${genTemplatePage.updateDate }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">模板名称:</label>
			</td>
			<td class="value">
		     	 <input id="templateName" name="templateName" type="text" style="width: 150px" class="inputxt"datatype="*">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">模板名称</label>
			</td>
		</tr>
	</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="genTemplateController.do?genTemplateFilesList&id=${genTemplatePage.id}" icon="icon-search" title="模板文件" id="genTemplateFiles"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
	<table style="display:none">
	<tbody id="add_genTemplateFiles_table_template">
		<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left">
					  	<input name="genTemplateFilesList[#index#].fileName" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;"
					               datatype="*"
					               >
					  <label class="Validform_label" style="display: none;">文件名</label>
				  </td>
				  <td align="left">
					       	<%--<input name="genTemplateFilesList[#index#].fileContent" maxlength="4,000"--%>
						  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
					  <%--<textarea rows="5" cols="20" name="genTemplateFilesList[#index#].fileContent" maxlength="4000" class="inputxt"  style="width:350px;"></textarea>--%>
					  <%--<label class="Validform_label" style="display: none;">内容</label>--%>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/buss/template/genTemplate.js"></script>	