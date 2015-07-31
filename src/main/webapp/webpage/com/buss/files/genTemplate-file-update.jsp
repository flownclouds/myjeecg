<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>模板文件</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <%--<script type="text/javascript" src="${mf:resource("plug-in/ckeditor/ckeditor.js")}" ></script>--%>
  <%--<script type="text/javascript" src="${mf:resource("plug-in/ckfinder/ckfinder.js")}"></script>--%>
	 <script type="text/javascript" src="${mf:resource("webpage/com/buss/js/common.js")}"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	  if(location.href.indexOf("loadtag=detail")!=-1) {
		  $(":input").attr("disabled", "true");
		 // windowapi
	  }
  });
	  function oksubmit(){
		  doSubmitForm(null,"formobj");
	  }
//  function alertTip_dialog(msg,title,parent) {
//	  $.dialog.setting.zIndex = 1980;
//	  title = title?title:"提示信息";
//	  $.dialog({
//		  title:title,
//		  icon:'tips.gif',
//		  lock : true,
//		  parent:parent,
//		  content: msg
//	  }).zindex();
//  }
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="genTemplateController.do?doFileUpdate">
					<input id="id" name="id" type="hidden" value="${genTemplateFilePage.id }">
					<input id="createName" name="createName" type="hidden" value="${genTemplateFilePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${genTemplateFilePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${genTemplateFilePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${genTemplateFilePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${genTemplateFilePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${genTemplateFilePage.updateDate }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">文件名称:</label>
			</td>
			<td class="value">
				  <label>${genTemplateFilePage.fileName}</label>
		     	 <%--<input id="fileName" name="fileName" type="text" style="width: 150px" class="inputxt" datatype="*" value='${genTemplateFilePage.templateName}'>--%>
				<%--<span class="Validform_checktip"></span>--%>
				<%--<label class="Validform_label" style="display: none;">文件名称</label>--%>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">文件内容:</label>
			</td>
			<td class="value">

				<textarea id="fileContent" name="fileContent" style="width:700px;height:430px;">${genTemplateFilePage.fileContent}</textarea>
					<%--<input id="fileContent" name="fileContent" type="text" style="width: 350px" class="inputxt" datatype="*" value='${genTemplateFilePage.fileContent}'>--%>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">文件内容</label>
			</td>
		</tr>
			</table>

			</t:formvalid>

 </body>
