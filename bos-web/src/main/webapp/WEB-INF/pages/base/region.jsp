<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
<script
	src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/customExt.js"
	type="text/javascript"></script>
<script type="text/javascript">
 
	//工具栏
	var toolbar = "#tb";
	// 定义列
	var columns = [ [ 
	{
	   checkbox : true,

	},
	{
		field : 'id',
		title : '编号',
		width : 100,
        align : 'center'
	},{
		field : 'province',
		title : '省',
		width : 160,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 160,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 160,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 160,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [30,50,100],
			pagination : true,
			toolbar : toolbar,
			url : "/region/pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow,
		    onLoadSuccess : function () {
		        $(this).datagrid("fixRownumber");
		    }
		});
		
		// 添加、修改区域窗口
		$('#addRegionWindow,#editRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		      
        $("#button-import").upload({
          action : "/region/importXls.action",
          name   : "regionFile",
          onSubmit: function() {
          	MaskUtil.waiting("上传完毕。正在处理，请稍后。。。");
          },
          onComplete:function(data){
            MaskUtil.close();
            if(data == '1')
                $("#grid").datagrid('reload');
            else
                $.messager.alert("错误","服务器忙，请稍后重试！","error");
          }
        });
        
        $("#edit").click(function(){
             var r = $('#editRegionForm').form('validate');
            //var r = true;
            if(r){
                $.ajax({
                    type:"POST",
                    url:'region/edit.action',
                    data:$('#editRegionForm').serialize(),
                    error:function(){
                        alertServerError();
                    },
                    success:function(response){
                        if(response != "")
                           $.messager.alert('错误', response, 'error');
                        else{
                           $('#editRegionWindow').window("close");
                           $("#grid").datagrid("reload");
                           //parent.$('#tabs').tabs('getSelected').panel('refresh');
                        }
                    }
                });
            }
            return false;
        });
        
        $("#reset").click(function(){
            $("#grid").datagrid('load',{});
        });
	});
	
	function alertServerError(){
	  $.messager.alert('错误','服务器忙，请稍后重试！','error');
	}
	
    function doAdd(){
      	$('#addRegionWindow').window("open");
  	}
  
	function doView(){
	    //alert("查询...");
	    var row = $("#grid").datagrid("getSelections")[0];
	    if(row){
	     $('#editRegionForm').form('load', row);
	     $('#editRegionWindow').window("open");
	    }else{
	        $.messager.alert("警告","请选择要编辑的行或直接双击该行！","warning");
	    }
	}
	
	function doDelete(){
	    alert("删除...");
	}
	
	function doSearch(txt){
		var val = $.trim(txt);
		if(val != ''){
			var json = {
				searchMode : true,
				q : val
			};
	        
	        $("#grid").datagrid('load', 
	            json
	        );
		}
	}
	
	function doDblClickRow(rowIndex, rowData){
		$('#editRegionForm').form('load', rowData);
		$('#editRegionWindow').window("open");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>

	<div id="tb">
		<div region="north" border="false" style="border-bottom:1px solid #ddd;height:28px;padding:2px 2px 2px 2px;background:#fafafa;">
			<div style="float:left"><a href="#" onclick="doView()" id="button-edit" class="easyui-linkbutton" plain="true" icon="icon-edit">修改</a></div>
			<div style="float:left"><a href="#" onclick="doAdd()" id="button-add" class="easyui-linkbutton" plain="true" icon="icon-add">增加</a></div>
			<div style="float:left"><a href="#" onclick="doDelete()" id="button-delete" class="easyui-linkbutton" plain="true" icon="icon-cancel">删除</a></div>
			<div style="float:left"><a href="#"  id="button-import" class="easyui-linkbutton" plain="true" icon="icon-redo">导入</a></div>
			<div class="datagrid-btn-separator"></div>
			<div style="float:left;padding:2px 3px 0 3px;"><input id="keyword" class="easyui-searchbox" data-options="prompt:'省、市、区、邮编、简码或城市编码',searcher:doSearch" style="height:25px;width:300px;border:1px solid #ccc" ></input></div>
			<div class="datagrid-btn-separator"></div>
		    <div style="float:left;padding:0 3px 0 3px;"><a id="reset" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" plain="true">重置</a></div>
		</div>
	</div>
	
	<div class="easyui-window" title="区域添加" id="addRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addRegionForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<div class="easyui-window" title="区域修改" id="editRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editRegionForm">
			    <input type="hidden" name="id"/>
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>