<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>

<head>
<title>MenuButton</title>
<!-- base需要放到head中 -->
<base href=" <%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<!-- 	<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script> -->

</head>
<body>
    <h2>表格方式一</h2>
	<table class='easyui-datagrid'>
		<thead>
			<tr>
				<th data-options='field:"id"'>编号</th>
				<th data-options='field:"name"'>姓名</th>
				<th data-options='field:"age"'>年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>张三</td>
				<td>24</td>
			</tr>
			<tr>
				<td>2</td>
				<td>李四</td>
				<td>28</td>
			</tr>
			<tr>
				<td>3</td>
				<td>王五</td>
				<td>21</td>
			</tr>
		</tbody>
	</table>
	<hr>
	<h2>表格方式二 发送ajax请求获取json数据</h2>
	<table class='easyui-datagrid' data-options='url:"../json/datagrid.json"'>
	   <thead>
            <tr>
                <th data-options='field:"id"'>编号</th>
                <th data-options='field:"name"'>姓名</th>
                <th data-options='field:"age"'>年龄</th>
            </tr>
        </thead>
	</table>
	<hr>
    <h2>表格方式三 js使用插件API</h2>
    <table id='dg'>
    </table>
    <script type="text/javascript">
        $('#dg').datagrid({
            columns:[[
            {checkbox:true},
            {title:'序号', field:'id'},
            {title:'姓名', field:'name'},
            {title:'年龄', field:'age'},
            ]],
            url:'json/datagrid.json',
            rownumbers:true,
            toolbar:[{
		        iconCls: 'icon-edit',
		        handler: function(){alert('编辑按钮')}
		      },'-',{
		        iconCls: 'icon-help',
		        text:'帮助',
		        handler: function(){alert('帮助按钮')}
		      }],
		    pageList: [1,2,3],
            pagination : true,

        });
        
    </script>
</body>

</html>
