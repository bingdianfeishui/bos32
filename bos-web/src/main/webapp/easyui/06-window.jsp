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
<script type="text/javascript">
	function editPassword(){
		$("#editPwdWindow").window('open');
	}
	
	$(function(){
		$("#btnEdit").click(function(){
			alert("修改密码");
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 180px; padding: 5px;
        background: #fafafa">
		<div class="easyui-layout" fit="true">
			<div data-options="region:'center'">
				新 密 码:<input id='txtNewPwd' name='新 密 码' class='easyui-validatebox' data-options="required:true, validType:'length[3,6]'"/>
				<br>确认密码:<input id='txtRePwd' name='确认密码' class='easyui-validatebox' data-options="required:true, validType:'length[3,6]'"/>
			</div>
			<div data-options="region:'south'">
				<a id='btnEdit' class="easyui-linkbutton">确认</a>
				<a id='btnCancle' class="easyui-linkbutton">取消</a>
			</div>
		</div>
	</div>
	
	<div title="XXX管理系统" style="height: 100px"
		data-options="region:'north'">
		<span>北部区域</span>
		<div style="position: absolute;right: 5px;top:50px;">
			<a class="easyui-menubutton" data-options="iconCls:'icon-help', menu:'#mm'">控制面板</a>
			
			<div id="mm">
				<div onclick="editPassword()">修改密码</div>
				<div>查看个人信息</div>
				<div class="menu-sep"></div>
				<div onclick="javascript:alert('退出');">退出</div>
			</div>
		</div>
		</div>
	<div title="系统面板" style="width:200px" data-options="region:'west'">
		<div class="easyui-accordion" data-options="fit:true">
			<div data-options="iconCls:'icon-add'" title="面板1">
				<a id="addTabs" class="easyui-linkbutton">新增tab</a>
			</div>
			<div data-options="iconCls:'icon-remove'" title="面板2">系统配置2</div>
			<div data-options="iconCls:'icon-edit'" title="面板3">系统配置3</div>
			<div data-options="iconCls:'icon-save'" title="面板4">系统配置4</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-tabs" data-options="fit:true">
			<div data-options="iconCls:'icon-add'" title="面板1">系统配置1</div>
			<div data-options="iconCls:'icon-remove', closable:true" title="面板2">系统配置2</div>
			<div data-options="iconCls:'icon-edit'" title="面板3">系统配置3</div>
			<div data-options="iconCls:'icon-save', selected:true"
				title="Messager">
				<a id="alert1" class="easyui-linkbutton" onclick="javascript:$.messager.alert('标题', '内容', 'error');">alert error</a> 
				<br><a id="alert2" class="easyui-linkbutton" onclick="javascript:$.messager.alert('标题', '内容', 'warning');">alert warning</a> 
				<br><a id="cm" class="easyui-linkbutton">confirm</a>
				<br><a id="show" class="easyui-linkbutton">show</a>
				<br><a id="prompt" class="easyui-linkbutton">prompt</a>
				<br><a id="progress" class="easyui-linkbutton">progress</a>
				<br><br>
				<a id="zh" class="easyui-linkbutton">加载中文语言包</a>
				
				
				<script type="text/javascript">
					$("#zh").click(function() {
						jQuery.getScript("js/easyui/locale/easyui-lang-zh_CN.js").done(function() {
							$.messager.alert("提示","中文语言包加载完成，请重新点击按钮 查看效果","info");
							});
					});
					
					$("#cm").click(function(){
						$.messager.confirm('标题', '内容', function(r){
							alert("点击结果为:"+r);
						});
					});
					
					$("#show").click(function(){
						$.messager.show({
							title:'滑动消息',
							msg:'消息将在5秒后关闭。',
							timeout:5000,
							showType:'slide' //null,slide,fade,show。默认：slide。
						});
						$.messager.show({
							title:'淡入淡出',
							msg:'消息将在5秒后关闭。',
							timeout:5000,
							showType:'fade', 
							style:{
								right:'',
								top:document.body.scrollTop+document.documentElement.scrollTop,
								bottom:''
							}
						});
					});
					$("#prompt").click(function(){
						$.messager.prompt('提示信息', '请输入你的姓名：', function(r){
							if (r){
								alert('你的姓名是：' + r);
							}
						});
					});
					$("#progress").click(function(){
						$.messager.progress();
						$.delay(5000);
						$.messager.progress('close');
					});
					
				</script>
			</div>
		</div>

	</div>
	<div style="width:200px" data-options="region:'east'">东部</div>
	<div style="height:50px" data-options="region:'south'">南部</div>

</body>
<script type="text/javascript">
	var $num = 0;
	$("#addTabs").click(function() {
		//alert($(".easyui-tabs"));
		$num++;
		if (!$(".easyui-tabs").tabs('exists', "新增面板")) {
			$(".easyui-tabs").tabs('add', {
				title : '新增面板',
				closable : true,

				content : '<h1>新增面板哈哈哈哈，计数器： ' + $num + '</h1>',
				tools : [ {
					iconCls : 'icon-add',
					handler : function() {
						alert('add')
					}
				}, {
					iconCls : 'icon-save',
					handler : function() {
						alert('save')
					}
				} ]

			});
		} else {
			var myTab = $(".easyui-tabs").tabs('getTab', '新增面板');
			$(".easyui-tabs").tabs('update', {
				tab : myTab,
				options : {
					content : '<h1>更新面板哈哈哈哈，计数器： ' + $num + '</h1>'

				}
			});
			$(".easyui-tabs").tabs('select', '新增面板');
		}

	});
</script>
</html>
