<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加业务受理单</title>
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
		$("body").css({visibility:"visible"});
		
		// 对save按钮条件 点击事件
		$('#save').click(function(){
			// 对form 进行校验
			if($('#noticebillForm').form('validate')){
				$('#noticebillForm').form('submit',{
					 url:"/noticeBill/add.action",
					 success:function(data){
					 	//alert(data);
					 	var title, msg;
					 	if(data == 1){
					 		title="Info";
					 		msg="工单自动分单成功！";
					 	}else{
					 		title="Warn";
					 		msg="工单自动分单失败，请手动分派！";
					 	}
				 		$.messager.show({
							title:title,
							msg:msg,
							timeout:2000,
							showType:'slide'
						});
					 }
				});
				$('#noticebillForm').form('clear');
			}
		});
		
		$('#assignWindow').window({
            title: '手动分区',
            width: 600,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
		
		$('#assign').click(function(){
            $("#noticeBillGrid").datagrid('reload');
            $("#decidedZoneCombo").combobox('reload');
            $("#assignWindow").window('open');
        });
        
        $("#assignBtn").click(function(){
            $("#assignNoticeBillForm").form('submit',{
                url:'/noticeBill/manualAssignment.action',
                success:function(data){
                    var msg="";
                    if(data == "1"){
                        msg="手动分单成功！";
                        $("#noticeBillGrid").datagrid('reload');
                        $("#decidedZoneCombo").combobox('clear');
                    }else{
                        msg="<font color='red'>手动分单失败！！</font>";
                    }
                    
                    $.messager.show({
                            title:"分单结果",
                            msg:msg,
                            timeout:2000,
                            showType:'slide'
                        });
                }
            });
        });
	});
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false"
		border="false">
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
				plain="true">新单</a>
			<a id="edit" icon="icon-edit" href="${pageContext.request.contextPath }/page_qupai_noticebill.action" class="easyui-linkbutton"
				plain="true">工单操作</a>	
			<a id="assign" icon="icon-edit" href="#" class="easyui-linkbutton"
				plain="true">手动分单</a>	
		</div>
	</div>
	<div region="center" style="overflow:auto;padding:5px;" border="false">
		<form id="noticebillForm">
			<table class="table-edit" width="100%" align="center">
				<tr class="title">
					<td colspan="4">客户信息</td>
				</tr>
				<tr>
					<td>来电号码:</td>
					<td><input type="text" class="easyui-validatebox" name="telephone"
						required="true" />
						<script type="text/javascript">
						  $("input[name=telephone]").blur(function(){
						      var telephone = this.value;
						      if(telephone != ""){
							      $.post("noticeBill/findCustomerByTelephone.action",{'telephone':telephone},function(data){
							          //console.info(data);
							          if(data != null){
								          $("input[name=customerId]").val(data.id);
								          $("input[name=customerName]").val(data.name);
								          $("input[name=delegater]").val(data.name);
								          $("input[name=pickaddress]").val(data.address);
							          }else{
                                          $("input[name=customerId]").val("");
	                                      $("input[name=customerName]").val("");
	                                      $("input[name=delegater]").val("");
	                                      $("input[name=pickaddress]").val("");
							          }
							      });
						      }
						  });
						</script>
						</td>
					<td>客户编号:</td>
					<td><input type="text" class="easyui-validatebox"  name="customerId"/></td>
				</tr>
				<tr>
					<td>客户姓名:</td>
					<td><input type="text" class="easyui-validatebox" name="customerName"/></td>
					<td>联系人:</td>
					<td><input type="text" class="easyui-validatebox" name="delegater"
						required="true" /></td>
				</tr>
				<tr class="title">
					<td colspan="4">货物信息</td>
				</tr>
				<tr>
					<td>品名:</td>
					<td><input type="text" class="easyui-validatebox" name="product" /></td>
					<td>件数:</td>
					<td><input type="text" class="easyui-numberbox" name="num"/></td>
				</tr>
				<tr>
					<td>重量:</td>
					<td><input type="text" class="easyui-numberbox" name="weight"/></td>
					<td>体积:</td>
					<td><input type="text" class="easyui-validatebox" name="volume"/></td>
				</tr>
				<tr>
					<td>取件地址</td>
					<td colspan="3"><input type="text" class="easyui-validatebox" name="pickaddress"
						required="true" size="144"/></td>
				</tr>
				<tr>
					<td>到达城市:</td>
					<td><input type="text" class="easyui-validatebox" name="arrivecity"/></td>
					<td>预约取件时间:</td>
					<td><input type="text" class="easyui-datebox" name="pickdate"
						data-options="editable:false" /></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3"><textarea rows="5" cols="80" type="text" class="easyui-validatebox" name="remark"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
   <div class="easyui-window" id="assignWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
        <div style="height:31px;overflow:hidden;" split="false" border="false" >
            <div class="datagrid-toolbar">
                <a id="assignBtn" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
            </div>
        </div>
        
        <div style="overflow:auto;padding:5px;" border="false">
            <form id="assignNoticeBillForm">
                <table class="table-edit" width="80%" align="center">
                    <tr class="title">
                        <td colspan="2">关联通知单到定区</td>
                    </tr>
                    <tr>
                        <td id="testclick">选择定区</td>
                        <td>
                            <input id="decidedZoneCombo" class="easyui-combobox" name="decidedZone.id" 
                                data-options="valueField:'id',textField:'desc',mode:'remote', delay:500, width:300, url:'/decidedZone/findByQ.action'" />  
                        </td>
                    </tr>
                    <tr height="300">
                        <td valign="top">分派通知单</td>
                        <td>
                            <table id="noticeBillGrid"  class="easyui-datagrid" border="false" style="width:300px;height:300px" data-options="url:'noticeBill/listNotAssigned.action', fitColumns:true,singleSelect:false">
                                <thead>  
                                    <tr>  
                                        <th data-options="field:'noticeBillId',width:30,checkbox:true">编号</th>  
                                        <th data-options="field:'customerName',width:150">用户名</th>  
                                        <th data-options="field:'pickaddress',width:200,align:'right'">取件地址</th>  
                                        <th data-options="field:'ordertype',width:200,align:'right'">分单类型</th>  
                                    </tr>  
                                </thead> 
                            </table>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</body>
</html>