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
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>	
<script type="text/javascript">
	$(function(){
        // 先将body隐藏，再显示，不会出现页面刷新效果
        $("body").css({visibility:"visible"});
	
		// 数据表格属性
		$("#grid").datagrid({
			toolbar : [
				{
					id : 'add',
					text : '添加角色',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_role_add.action';
					}
				}           
			],
            ctrlSelect : true,
			pagination:true,
			fit:true,
			url : '${pageContext.request.contextPath}/role/pageQuery.action',
            onDblClickRow: onDblClickRow,
			columns : [[
				{
					field : 'id',
					title : '编号',
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
				} 
			]]
		});
		
		      
        $("#editRoleWindow").window({
            title: '编辑功能权限',
            width: 600,
            modal: true,
            shadow: true,
            closed: true,
            height: 600,
            resizable:false
        });
        
        // 授权树初始化设置
        var setting = {
            data : {
                key : {
                    title : "t"
                },
                simpleData : {
                    enable : true
                }
            },
            check : {
                enable : true,
            }
        };
        
        function onDblClickRow(index, row){
            $.ajax({
	            url : '${pageContext.request.contextPath}/function/listAjax.action',
	            type : 'POST',
	            dataType : 'text',
	            success : function(data) {
	                var zNodes = eval("(" + data + ")");
	                $.fn.zTree.init($("#functionTree"), setting, zNodes);
	                //回显权限
	                var functionIds = new Array();
	                for(var i = 0; i<row.functions.length; i++){
	                   functionIds.push(row.functions[i].id);
	                }
	                var treeObj = $.fn.zTree.getZTreeObj("functionTree");
	                //treeObj.expandAll(true);
                    var nodes = treeObj.transformToArray(treeObj.getNodes());
	                for(var i = 0; i < nodes.length; i++){
	                   var index = $.inArray(nodes[i].id, functionIds);
	                   if(index != -1){
	                       treeObj.checkNode(nodes[i], true, true);
	                       //treeObj.selectNode(nodes[i], false);
	                   }
	                }
	            },
	            error : function(msg) {
	                alert('权限树加载异常!');
	            }
            });
            $('#editRoleForm').form('load', row);
            
            //$(".easyui-combotree").combotree('setValue', row.pId);
            $('#editRoleWindow').window("open");
        }
        
        $("#editRole").click(function(){
            var r = $('#editRoleForm').form('validate');
            //var r = true;
            if(r){
                var nodes = $.fn.zTree.getZTreeObj("functionTree").getCheckedNodes(true);
                if(nodes.length == 0){
                    $.messager.confirm('警告', '您没有为该角色选择任何权限！确定继续吗？', function(r){
                        if (!r){
                            return false;
                        }
                    });
                }
                var ids  = new Array();
                for(var i = 0; i < nodes.length; i++){
                     ids.push(nodes[i].id);
                }
                var functionIds = ids.join(',');
                $("input[name=functionIds]").val(functionIds);
                
                $.ajax({
                    type:"POST",
                    url:'role/edit.action',
                    data:$('#editRoleForm').serialize(),
                    error:function(){
                        alertServerError();
                    },
                    success:function(response){
                        if(response != "[]")
                           $.messager.alert('错误', response, 'error');
                        else{
                           $('#editRoleWindow').window("close");
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
<body class="easyui-layout" style="visibility:hidden;">
	<div data-options="region:'center'">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对角色进行修改" id="editRoleWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
	    <div style="height:31px;overflow:hidden;" split="false" border="false" >
	        <div class="datagrid-toolbar">
	            <a id="editRole" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
	        </div>
	    </div>
	    
	    <div style="overflow:auto;padding:5px;" border="false">
	        <form id="editRoleForm">
	            <input type="hidden" name="id"/>
                <input type="hidden" name="functionIds"/>
                <table class="table-edit" width="80%" align="center">
                    <tr class="title">
                        <td colspan="2">角色信息</td>
                    </tr>
                    <tr>
                        <td width="200">关键字</td>
                        <td>
                            <input type="text" name="code" class="easyui-validatebox" data-options="required:true" />                       
                        </td>
                    </tr>
                    <tr>
                        <td>名称</td>
                        <td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
                    </tr>
                    <tr>
                        <td>描述</td>
                        <td>
                            <textarea name="description" rows="4" cols="60"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>授权</td>
                        <td>
                            <ul id="functionTree" class="ztree"></ul>
                        </td>
                    </tr>
               </table>
	        </form>
	    </div>
	</div>
</body>
</html>