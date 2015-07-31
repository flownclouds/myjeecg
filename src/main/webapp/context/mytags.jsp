<%@ page import="org.jeecgframework.core.util.ResourceUtil" %>
<%@ taglib prefix="t" uri="/easyui-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib uri="/my-funs" prefix="mf" %>
<%@ taglib uri="/my-tags" prefix="mt" %>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    String version= ResourceUtil.getConfigByName("resources.version");
%>
<c:set var="webRoot" value="<%=basePath%>" />
<c:set var="webVersion" value="<%=version%>" />