<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()
+":"+request.getServerPort()+path+"/"; 
%>

<html>
 
<head> 
	<title>Layout</title>
	<!-- base需要放到head中 --> 
	<base href=" <%=basePath%>"> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<title></title>
</head>
<body class="easyui-layout">
	<div title="XXX管理系统" style="height:100px" data-options="region:'north'">北部</div>
	<div style="height:50px" data-options="region:'south'">南部</div>
	<div style="width:200px" data-options="region:'east'">东部</div>
	<div title="系统面板" style="width:200px" data-options="region:'west'">西部</div>
	<div data-options="region:'center'">中部</div>

</body>
</html>
