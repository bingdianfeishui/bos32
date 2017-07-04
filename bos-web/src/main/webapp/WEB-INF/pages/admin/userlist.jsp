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
	// 工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查看',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
        id : 'button-updateRoles',
        text : '编辑角色',
        iconCls : 'icon-edit',
        handler : doEdit
    }];
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
		rowspan : 2
	}, {
		field : 'username',
		title : '名称',
		width : 80,
		rowspan : 2
	} ] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'gender',
		title : '性别',
		width : 60,
		rowspan : 2,
		align : 'center',
		formatter:function(value){
		  if(value == "1")
		      return "男";
		  else if(value == "0")
		      return "女";
		  else
		      return "";
		}
	}, {
		field : 'birthdayStr',
		title : '生日',
		width : 120,
		rowspan : 2,
		align : 'center'
	}, {
		title : '其他信息',
		colspan : 2
	}, {
		field : 'telephone',
		title : '电话',
		width : 180,
		rowspan : 2
	}, {
        field : 'roleNames',
        title : '角色',
        width : 800,
        rowspan : 2
    } ], [ {
		field : 'station',
		title : '单位',
		width : 80,
		align : 'center'
	}, {
		field : 'salary',
		title : '工资',
		width : 80,
		align : 'right'
	} ] ];
	$(function(){
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath }/user/pageQuery.action",
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onClickRow : onClickRow,
			onDblClickRow : doDblClickRow
		});
		
		$("body").css({visibility:"visible"});
		
        $("#editUserWindow").window({
            title: '编辑用户',
            width: 600,
            modal: true,
            shadow: true,
            closed: true,
            height: 600,
            resizable:false
        });
	});
	// 双击
	function doDblClickRow(rowIndex, rowData) {
		var items = $('#grid').datagrid('selectRow',rowIndex);
		doView();
	}
	// 单击
	function onClickRow(rowIndex){

	}
	
	var roleIds = new Array();
	
	function doEdit(){
	       var rows = $("#grid").datagrid("getSelections");
	       if(rows.length == 0){
	           alert("请选择要编辑的行");
	           return false;
	       }
	       
          $.post("user/getRoles.action", function(data){
              for(var i = 0; i < data.length; i++){
                 roleIds.push(data[i].id);
              }
		       $.ajax({
	                url : '${pageContext.request.contextPath}/role/listAjax.action',
	                type : 'POST',
	                dataType : 'text',
	                success : function(data) {
	                    data = eval("("+data+")")
	                    
	                    $("#roleTd").html("");
			            for(var i = 0; i<data.length; i++){
			                var id = data[i].id;
			                var name=data[i].name;
			                var checkStatus = "";
			                if($.inArray(id, roleIds) != -1){
			                  checkStatus = "checked='checked'";
			                }
			                $("#roleTd").append("<input type='checkbox' id='"+
			                    id+"' name='roleIds' value='"+id+"' "+ checkStatus+"><label for='"+
			                    id+"'>"+name+"</label></input>");
			            }
	                },
	                error : function(msg) {
	                    alert('角色信息加载异常!');
	                }
	            });
           });
           $('#userForm').form('load', rows[0]);
           
           //$(".easyui-combotree").combotree('setValue', row.pId);
           $('#editUserWindow').window("open");
	       
	}
	
	function doAdd() {
		//alert("添加用户");
		location.href="${pageContext.request.contextPath}/page_admin_userinfo.action";
	}

	function doView() {
		
		var item = $('#grid').datagrid('getSelected');
		console.info(item);
		//window.location.href = "edit.html";
	}

	function doDelete() {
		alert("删除用户");
		var ids = [];
		var items = $('#grid').datagrid('getSelections');
		for(var i=0; i<items.length; i++){
		    ids.push(items[i].id);	    
		}
			
		console.info(ids.join(","));
		
		$('#grid').datagrid('reload');
		$('#grid').datagrid('uncheckAll');
	}
	
</script>		
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <div region="center" border="false">
    	<table id="grid"></table>
	</div>
   <div class="easyui-window" title="更新用户" id="editUserWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
        <div style="height:31px;overflow:hidden;" split="false" border="false" >
            <div class="datagrid-toolbar">
                <a id="editRole" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
            </div>
        </div>
	    <div style="overflow:auto;padding:5px;" border="false">
	       <form id="userForm" >
	           <table class="table-edit"  width="95%" align="center">
	                <tr class="title"><td colspan="4">基本信息</td></tr>
	                <tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" required="true" /></td>
	                    <td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[5]" /></td></tr>
	                <tr class="title"><td colspan="4">其他信息</td></tr>
	                <tr><td>工资:</td><td><input type="text" name="salary" id="salary" class="easyui-numberbox" /></td>
	                    <td>生日:</td><td><input type="text" name="birthday" id="birthday" class="easyui-datebox" /></td></tr>
	                <tr><td>性别:</td><td>
	                    <select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
	                        <option value="">请选择</option>
	                        <option value="1">男</option>
	                        <option value="0">女</option>
	                    </select>
	                </td>
	                    <td>单位:</td><td>
	                    <select name="station" id="station" class="easyui-combobox" style="width: 150px;">
	                        <option value="">请选择</option>
	                        <option value="总公司">总公司</option>
	                        <option value="分公司">分公司</option>
	                        <option value="厅点">厅点</option>
	                        <option value="基地运转中心">基地运转中心</option>
	                        <option value="营业所">营业所</option>
	                    </select>
	                </td></tr>
	                <tr>
	                    <td>联系电话</td>
	                    <td colspan="3">
	                        <input type="text" name="telephone" id="telephone" class="easyui-validatebox" required="true" />
	                    </td>
	                </tr>
	                <tr><td>备注:</td><td colspan="3"><textarea style="width:80%"></textarea></td></tr>
	                <tr>
	                   <td>选择角色:</td>
	                       <td colspan="3" id="roleTd">
	                       </td>
	                </tr>
	           </table>
	       </form>
	    </div>
    </div>
</body>
</html>