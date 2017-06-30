<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		$("#grid").datagrid({
			toolbar : [
				{
					id : 'add',
					text : '添加权限',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_function_add.action';
					}
				},
				{
					id : 'edit',
					text : '编辑权限',
					iconCls : 'icon-edit',
					handler : function(){
						var rows = $("#grid").datagrid('getSelections');
						if(rows.length == 0){
							alert("请先选中要编辑的行！");
							return false;
						}
						onDblClickRow(0, rows[0]);
					}
				}       
			],
			pagination:true,
			fit:true,
			url : '${pageContext.request.contextPath}/function/pageQuery.action',
			onDblClickRow: onDblClickRow,
			ctrlSelect : true,
			columns : [[
			  {
			     field: 'id',
			     checkbox: true
			  },
			  {
				  field : 'code',
				  title : '关键字',
				  width : 200
			  },
			  {
				  field : 'name',
				  title : '名称',
				  width : 200
			  },  
			  {
				  field : 'description',
				  title : '描述',
				  width : 200
			  },  
			  {
				  field : 'generatemenu',
				  title : '是否生成菜单',
				  width : 200,
				  formatter: function(value){
				  	if(value=="1")return "是";
				  	else return "否";
				  }
			  },  
			  {
				  field : 'zindex',
				  title : '优先级',
				  width : 200
			  },  
			  {
				  field : 'page',
				  title : '路径',
				  width : 200
			  }
			]]
		});
		
		$("#editFunctionWindow").window({
			title: '编辑功能权限',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 600,
	        resizable:false
		});
		
		function onDblClickRow(index, row){
			$('#editFunctionForm').form('load', row);
			$(".easyui-combotree").combotree('setValue', row.pId);
        	$('#editFunctionWindow').window("open");
		}
		
		$("#editFunction").click(function(){
			var r = $('#editFunctionForm').form('validate');
            //var r = true;
            if(r){
                $.ajax({
                    type:"POST",
	                url:'function/edit.action',
	                data:$('#editFunctionForm').serialize(),
	                error:function(){
	                    alertServerError();
	                },
	                success:function(response){
	                    if(response != "[]")
	                       $.messager.alert('错误', response, 'error');
	                    else{
	                       $('#editStaffWindow').window("close");
	                       $("#grid").datagrid("reload");
	                       //parent.$('#tabs').tabs('getSelected').panel('refresh');
	                    }
	                }
                });
            }
            return false;
		});
	});
</script>	
</head>
<body class="easyui-layout"  style="visibility:hidden;">
<div data-options="region:'center'">
	<table id="grid"></table>
</div>
<div class="easyui-window" title="对功能权限进行修改" id="editFunctionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
<!-- div class="easyui-window" title="对功能权限进行修改" id="editFunctionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px" -->
	<div style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="editFunction" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
	
	<div style="overflow:auto;padding:5px;" border="false">
		<form id="editFunctionForm">
			<input type="hidden" name="id"/>
			<table class="table-edit" width="80%" align="center">
				<tr class="title">
					<td colspan="2">功能权限信息</td>
				</tr>
				<tr>
					<td>关键字</td>
					<td>
						<input type="text" name="code" class="easyui-validatebox" data-options="required:true" />						
					</td>
				</tr>
				<tr>
					<td>名称</td>
					<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>访问路径</td>
					<td><input type="text" name="page"  /></td>
				</tr>
				<tr>
					<td>是否生成菜单</td>
					<td>
						<select name="generatemenu" class="easyui-combobox">
							<option value="0">不生成</option>
							<option value="1">生成</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>优先级</td>
					<td>
						<input type="text" name="zindex" class="easyui-numberbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>父功能点</td>
					<td>
					  <input class="easyui-combotree" name="parent.id"
                               data-options="id:'id', text:'name', url:'${pageContext.request.contextPath }/function/listAjax.action', panelHeight:300" 
                               style="width:200px;">
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
						<textarea name="description" rows="4" cols="60"></textarea>
					</td>
				</tr>
				</table>
		</form>
	</div>
</div>
</body>
</html>