<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>代码生成信息</title>
    <t:base type="jquery,easyui,jqueryui-sortable"></t:base>
    <script type="text/javascript" src="${mf:resource("webpage/com/buss/js/common.js")}"></script>

    <link rel="stylesheet" type="text/css" href="${mf:resource("plug-in/SyntaxHighlighter/styles/shCore.css")}" />
    <link rel="stylesheet" type="text/css" href="${mf:resource("plug-in/SyntaxHighlighter/styles/shThemeDefault.css")}" />
    <script type="text/javascript" src="${mf:resource("plug-in/SyntaxHighlighter/scripts/shCore.js")}"></script>
    <script type="text/javascript" src="${mf:resource("plug-in/SyntaxHighlighter/scripts/shBrushJava.js")}"></script>
    <script type="text/javascript" src="${mf:resource("plug-in/zclip/js/jquery.zclip.min.js")}"></script>

    <script type="text/javascript">
        $(function(){
            SyntaxHighlighter.all();

            $("#copycode").zclip({
               path:"${mf:resource("plug-in/zclip/js/ZeroClipboard.swf")}",
                copy:$("#hidContent").text()
            });
        });

    </script>
    <style type="text/css">
        .mg{ margin: 0 0 0 20px;}
        .syntaxhighlighter{height:550px;}
    </style>
</head>
<body>

<div class="mg2">
    <input id="copycode" type="button" value="复制代码"/>
    <%--<textarea id="fileContent"--%>
              <%--name="fileContent" style="width:800px;height:450px;">${msg}</textarea>--%>

    <pre class="brush:java;" id="fileContent"
         name="fileContent" >
        ${fn:escapeXml(msg)}
    </pre>

    <textarea id="hidContent" style="display:none;" >${msg}</textarea>
</div>

</body>
</html>
