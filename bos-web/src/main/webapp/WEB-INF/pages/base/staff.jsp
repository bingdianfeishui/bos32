<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/customExt.js"></script>
<script type="text/javascript">
	function doAdd(){
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		$('#searchStaffWindow').window("open");
	}
	
    function doReset(){
        $("#grid").datagrid("load",{});
        //$('#clear').click();
        //$('#search').click();
    };
    
    function doDblClickRow(rowIndex, rowData){
        $('#editStaffForm').form('load', rowData);
        $('#editStaffWindow').window("open");
    }
	
	function doDelete(){
	   var sel = $("#grid").datagrid('getSelections');
	   if(sel.length > 0){

		   $.messager.confirm("确认", "确定作废选中的" + sel.length + "位取派员？", function(r) {
	           if(r){
	               var data = new Array();
		           for(var i = 0; i < sel.length; i++){
		               data.push(sel[i].id);
		           }
		           var ids = data.join(',');
		           //alert(ids);
	               $.ajax({
	                   type:"POST",
	                   url:'staff/batchDelete.action',
	                   data:{"ids":ids},
	                   error:function(){
	                       $.messager.alert('','操作失败，请稍后重试！','error');
	                   },
	                   success:function(data){
	                       if(data == "1"){
	                           $("#grid").datagrid("reload");
	                       }
	                       else
	                           $.messager.alert('','操作失败，请稍后重试！','error');
	                   }
	               });
	           }
	       });
	   }else{
	       $.messager.alert("","请选择需要作废的取派员！","warn");
	   }
	}
	
	function doRestore(){
		var sel = $("#grid").datagrid('getSelections');
       if(sel.length > 0){

           $.messager.confirm("确认", "确定还原选中的" + sel.length + "位取派员？", function(r) {
               if(r){
                   var data = new Array();
                   for(var i = 0; i < sel.length; i++){
                       data.push(sel[i].id);
                   }
                   var ids = data.join(',');
                   //alert(ids);
                   $.ajax({
                       type:"POST",
                       url:'staff/batchRestore.action',
                       data:{"ids":ids},
                       error:function(){
                           $.messager.alert('','操作失败，请稍后重试！','error');
                       },
                       success:function(data){
                           if(data == "1"){
                               $("#grid").datagrid("reload");
                           }
                           else
                               $.messager.alert('','操作失败，请稍后重试！','error');
                       }
                   });
               }
           });
       }else{
           $.messager.alert("","请选择需要还原的取派员！","warn");
       }
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, 
	{
        id : 'button-reset', 
        text : '重置',
        iconCls : 'icon-reload',
        handler : doReset
    }, "-",
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, 
	<shiro:hasPermission name="staff.delete">
	{
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},
	</shiro:hasPermission>
	{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
        checkbox : true,
    },
	{
		field : 'id',
		title : '员工编号',
        width : 120,
        align : 'left'
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="0"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所属单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			ctrlSelect : false,
			pageList: [10,20,50],
			pagination : true,
			toolbar : toolbar,
			url : "staff/pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
	    
		// 修改取派员窗口
		$('#editStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
	    
		// 查询取派员窗口
		$('#searchStaffWindow').window({
	        title: '取派员查询',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
	    
		// 查询取派员，是否作废下拉框
		$('#deltagCombo').combobox({
	        textField: "label",
	        valueField: "value",
	        data :[
		        {
		           "label" : "全部",
		           "value" : "",
		           "selected":true
		        },
		        {
		           "label" : "已作废",
		           "value" : "1"
		        },
		        {
		           "label" : "未作废",
		           "value" : "0"
		        },
	        ]
	    });
	    
		// 查询取派员，是否有pda下拉框
		$('#haspdaCombo').combobox({
	        textField: "label",
	        valueField: "value",
	        data :[
		        {
		           "label" : "全部",
		           "value" : "",
		           "selected":true
		        },
		        {
		           "label" : "有PDA",
		           "value" : "1"
		        },
		        {
		           "label" : "无PDA",
		           "value" : "0"
		        },
	        ]
	    });
	    
	    //手机号校验规则
	    // extend the 'equals' rule    
        $.extend($.fn.validatebox.defaults.rules, {    
            telephone: {    
                validator: function(value){    
                    var regex = /^1[34578][0-9]{9}$/;
                    return regex.test(value);
                },    
                message: '手机号输入有误。'  
            }    
        });
        
        //修改Staff的保存按钮
        $('#save').click(function(){
            var r = $('#addStaffForm').form('validate');
            //var r = true;
            if(r){
                //$('#addStaffForm').form('submit');
                $.ajax({
                    type:"POST",
	                url:'staff/add.action',
	                data:$('#addStaffForm').serialize(),
	                error:function(){
	                    $.messager.alert('错误','服务器忙，请稍后重试！','error');
	                },
	                success:function(response){
	                    if(response != "[]")
	                       $.messager.alert('错误', response, 'error');
	                    else{
	                       $('#addStaffWindow').window("close");
	                       $("#grid").datagrid("reload");
	                       //parent.$('#tabs').tabs('getSelected').panel('refresh');
	                    }
	                }
                });
            }
            return false;
        });
        
        //编辑Staff的保存按钮
        $('#edit').click(function(){
            var r = $('#editStaffForm').form('validate');
            //var r = true;
            if(r){
                $.ajax({
                    type:"POST",
	                url:'staff/edit.action',
	                data:$('#editStaffForm').serialize(),
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
		
        //查询Staff的查询按钮
        $('#search').click(function(){
            var json = $('#searchStaffForm').serializeJson();
            console.info(json);
            $("#grid").datagrid('load', 
                json
            );
            $('#searchStaffWindow').window("close");
            /*
            var r = true;
            if(r){
                $.ajax({
                    type:"POST",
	                url:'staff/pageQuery.action',
	                data:$('#searchStaffForm').serialize(),
	                error:function(){
	                    alertServerError();
	                },
	                success:function(response){
                       $('#searchStaffWindow').window("close");
	                }
                });
            }
            */
            return false;
        });
        //查询Staff的清空按钮
        $('#clear').click(function(){
	        $("#searchStaffForm .easyui-validatebox").val("");
	        $("#searchStaffForm .easyui-combobox").combobox('select','');
            return false;
        });
        
        
		function alertServerError(){
		  $.messager.alert('错误','服务器忙，请稍后重试！','error');
		}
	});

</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	
	<div class="easyui-window" title="对取派员进行添加" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStaffForm" method="post" action="staff/add.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">取派员信息</td>
					</tr>
					<!-- TODO 这里完善取派员添加 table -->
					<%-- 编号自动生成，删除此处
					<tr>
						<td>取派员编号</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					 --%>
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" data-options='"required":true, validType:"telephone"'/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<div class="easyui-window" title="对取派员进行修改" id="editStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStaffForm" method="post" action="staff/edit.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">取派员信息</td>
					</tr>
					<tr>
						<td>取派员编号</td>
						<td><input type="hidden" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" data-options='"required":true, validType:"telephone"'/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					
				</table>
			</form>
		</div>
	</div>
	
	<div class="easyui-window" title="过滤条件" id="searchStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchStaffForm">
				<input type="hidden" name="searchMode" value="true"/>
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">取派员查询过滤条件</td>
					</tr>
					<tr>
						<td>取派员编号</td>
						<td><input type="text" name="id" class="easyui-validatebox"/></td>
					</tr>
					
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox"/>  
						</td>
					</tr>
					<tr>
						<td>是否有PDA</td>
						<td><input id="haspdaCombo" class="easyui-combobox" name="haspda"/></td>
					</tr>
					<tr>
						<td>是否已作废</td>
                        <td><input id="deltagCombo" class="easyui-combobox" name="deltag"/></td>
					</tr>
					<tr>
					   <td colspan="2" align="center">
					       <a id="clear" icon="icon-reload" href="#" class="easyui-linkbutton">清空</a>
					       <a id="search" icon="icon-search" href="#" class="easyui-linkbutton">查询</a>
					   </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>	