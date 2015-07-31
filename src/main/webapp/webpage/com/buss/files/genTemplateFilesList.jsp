<%@ page import="org.jeecgframework.core.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
	String load=request.getParameter("load");
String ispermit=request.getParameter("ispermit");
%>
<c:set var="loadtag" value="<%=load%>" />
<c:set var="ispermit" value="<%=ispermit%>" />
<script type="text/javascript">
	$('#addGenTemplateFilesBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delGenTemplateFilesBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addGenTemplateFilesBtn').bind('click', function(){   
 		 var tr =  $("#add_genTemplateFiles_table_template tr").clone();
	 	 $("#add_genTemplateFiles_table").append(tr);
	 	 resetTrNum('add_genTemplateFiles_table');
	 	 return false;
    });  
	$('#delGenTemplateFilesBtn').bind('click', function(){   
      	$("#add_genTemplateFiles_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_genTemplateFiles_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#genTemplateFiles_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });


	function editcontent(url ,fname,obj){
		var name= $(obj).closest("tr").find(".fname").val();
		if(name!=fname){
			alert("文件名："+name+"以改变，先保存模板再编辑内容！");
			return ;
		}
		gridname="genTemplateFiles_table";
		var did="dg_editcontent";


		<c:choose>
		<c:when test="${loadtag=='detail'}">
		createwindow_detail_my("编辑模板文件",url,800,500,function(){
			iframe = this.iframe.contentWindow;

			windowapi.get(did).oksubmit();
			return false;
		},did);
		</c:when>
		<c:otherwise>
		createwindow_my("编辑模板文件",url,800,500,function(){
			iframe = this.iframe.contentWindow;

			windowapi.get(did).oksubmit();
			return false;
		},did);
		</c:otherwise>
		</c:choose>



		return false;
	}
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addGenTemplateFilesBtn" href="#">添加</a> <a id="delGenTemplateFilesBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="2" cellspacing="0" id="genTemplateFiles_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						文件名
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						内容
				  </td>
	</tr>
	<tbody id="add_genTemplateFiles_table">	
	<c:if test="${fn:length(genTemplateFilesList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="genTemplateFilesList[0].id" type="hidden"/>
					<input name="genTemplateFilesList[0].createName" type="hidden"/>
					<input name="genTemplateFilesList[0].createBy" type="hidden"/>
					<input name="genTemplateFilesList[0].createDate" type="hidden"/>
					<input name="genTemplateFilesList[0].updateName" type="hidden"/>
					<input name="genTemplateFilesList[0].updateBy" type="hidden"/>
					<input name="genTemplateFilesList[0].updateDate" type="hidden"/>
					<input name="genTemplateFilesList[0].groupId" type="hidden"/>
				  <td align="left">
					  	<input name="genTemplateFilesList[0].fileName" maxlength="32"
					  		type="text" class="inputxt"  style="width:120px;"
					               datatype="*"
					               >
					  <label class="Validform_label" style="display: none;">文件名</label>
					</td>
				  <td align="left">
					       	<%--<input name="genTemplateFilesList[0].fileContent" maxlength="4,000"--%>
						  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%-->--%>
								<textarea rows="5" cols="20" name="genTemplateFilesList[0].fileContent" maxlength="4000" class="inputxt"  style="width:350px;"></textarea>
					  <label class="Validform_label" style="display: none;">内容</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(genTemplateFilesList)  > 0 }">
		<c:forEach items="${genTemplateFilesList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="genTemplateFilesList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="genTemplateFilesList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="genTemplateFilesList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="genTemplateFilesList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="genTemplateFilesList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="genTemplateFilesList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="genTemplateFilesList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="genTemplateFilesList[${stuts.index }].groupId" type="hidden" value="${poVal.groupId }"/>
				   <td align="left">
					  	<input name="genTemplateFilesList[${stuts.index }].fileName" maxlength="32"
					  		type="text" class="inputxt fname"  style="width:120px;"
					               datatype="*"
					                value="${poVal.fileName }">
					  <label class="Validform_label" style="display: none;">文件名</label>
				   </td>
				   <td align="left">
					       	<%--<input name="genTemplateFilesList[${stuts.index }].fileContent" maxlength="4000"--%>
						  		<%--type="textarea" class="inputxt"  style="width:350px;"--%>
					               <%----%>
					                <%--value="${poVal.fileContent }">--%>

								<%--<c:if test="${!empty poVal.fileContent}">--%>
								<%--<textarea rows="5" cols="20" name="genTemplateFilesList[${stuts.index }].fileContent"--%>
								 <%--style="width:350px;"  maxlength="4000" >${fn:escapeXml(poVal.fileContent) }</textarea>--%>
					  <%--<label class="Validform_label" style="display: none;">内容1</label>--%>
					   <%--</c:if>--%>
								<c:if test="${!empty ispermit and ispermit==\"true\"}">
									<div style="height:auto;" class="datagrid-cell datagrid-cell-c1-opt">[<a href="#"
																											 onclick="editcontent('genTemplateController.do?goFileUpdate&id=${poVal.id}&load=${loadtag}','${poVal.fileName }',this)">编辑内容</a>]</div>

								</c:if>
								</td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
