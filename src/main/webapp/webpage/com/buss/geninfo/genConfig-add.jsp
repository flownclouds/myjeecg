<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>代码生成配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="genConfigController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${genConfigPage.id }">
					<input id="createName" name="createName" type="hidden" value="${genConfigPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${genConfigPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${genConfigPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${genConfigPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${genConfigPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${genConfigPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="typeinfo" name="typeinfo" type="text" style="width: 150px" class="inputxt"
								               datatype="*"
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							键信息:
						</label>
					</td>
					<td class="value">
					     	 <input id="keyinfo" name="keyinfo" type="text" style="width: 150px" class="inputxt"
								               datatype="*"
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">键信息</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							值信息:
						</label>
					</td>
					<td class="value">
					     	 <input id="valueinfo" name="valueinfo" type="text" style="width: 150px" class="inputxt"
								               datatype="*"
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">值信息</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/geninfo/genConfig.js"></script>		