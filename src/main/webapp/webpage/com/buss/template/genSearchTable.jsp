<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.jeecgframework.web.cgform.common.CgAutoListConstant" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>数据表信息</title>
    <t:base type="jquery,easyui,jqueryui-sortable"></t:base>
    <script type="text/javascript" src="${mf:resource("webpage/com/buss/js/common.js")}"></script>
</head>
<body>
<div class="easyui-layout" fit="true">

    <div region="center" style="padding: 1px;">

        <form id="reltablesearch">
            <table style="line-height: 35px;">
                <tr>
                    <td><label>数据源：</label></td>
                    <td><select name="db" id="drptdb">
                        <option value="-1">请选择</option>
                        <c:forEach var="item" items="${dsource}">
                            <option value="${item["id"]}">${item["keyinfo"]}</option>
                        </c:forEach>
                    </select></td>
                    <td><input style="margin-left:100px;" type="button" value="查询"
                               onclick="return rel_search();"/></td>
                    <td><input style="margin-left:100px;" type="button" value="确定"
                               onclick="return okselect();"/></td>

                </tr>


            </table>
        </form>

        <table>
            <tr>
                <td>
                    <div style="height: 400px; width: 500px;">
                        <t:datagrid name="reltable" title="数据表" fitColumns="false"
                                    pagination="false"    fit="true" queryMode="group"
                                    actionUrl="genManageController.do?dbdatagrid" idField="id"
                                    sortName="id" autoLoadData="false">
                            <t:dgCol title="表名" field="id" query="false" width="270"></t:dgCol>
                        </t:datagrid>
                    </div>
                </td>

            </tr>
        </table>


    </div>
</div>
<script type="text/javascript">
    var windowapi = frameElement.api; //内容页中调用窗口实例对象接口
var W;
    if(windowapi!=undefined){
        W = windowapi.opener;
    }

    function rel_search() {
        var db = $("#drptdb").val();
        if (db == "-1") {
            alert("请选择数据源！");
            return false;
        }

        var params = $("#reltablesearch").serializeObject();
        var queryParams = $('#reltable').datagrid('options').queryParams;
        queryParams = $.extend(queryParams, params);
        dosearch(JSON.stringify(queryParams));
        return false;
    }
    function getSelectItem(){
        var id="";
        var rows=getSelectRows();
        if(rows.length>0){
             id=rows[0].id;
            var db = $("#drptdb").val();
            var dbName=$("#drptdb option:selected").text();
            id=dbName+"$$$"+db+"$$$"+id;
        }
        return id;
    }
    function  okselect(){
        var id=getSelectItem();
        parent.setTbInfo(id);

    }
</script>
</body>
</html>