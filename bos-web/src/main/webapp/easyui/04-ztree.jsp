<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()
+":"+request.getServerPort()+path+"/"; 
%>

<html>
 
<head> 
	<title>Accordion</title>
	<!-- base需要放到head中 --> 
	<base href=" <%=basePath%>"> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/ztree/zTreeStyle.css">
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/ztree/jquery.ztree.all-3.5.js"></script>
	
</head>
<body class="easyui-layout">
	<div title="XXX管理系统" style="height: 100px" data-options="region:'north'">北部区域</div>
	<div title="系统面板" style="width:200px" data-options="region:'west'">
		<div class="easyui-accordion" data-options="fit:true">
			<div data-options="iconCls:'icon-add'" title="面板1">
				<a id="addTabs" class="easyui-linkbutton">新增tab</a>
			</div>
			<div data-options="iconCls:'icon-remove'" title="zTree">
				<ul class="ztree" id="tr"></ul>
				<script type="text/javascript">
					$(function(){
						var setting={
							data:{
								simpleData:{
									enable:true
								}
							},
							callback:{
 								onClick:function(event, treeId, treeNode){
									if(treeNode.page){	//json节点中有page属性
										var e = $("#tt").tabs('exists', treeNode.name);
										if(e){
											$("#tt").tabs('select', treeNode.name);
										}else{
											$("#tt").tabs('add',{
												title: treeNode.name,
												closable:true,
												content:'<iframe style="width:100%; height:100%" src="'+treeNode.page+'"></iframe>'
											});
										}
									}
								}
							}
						};
						$.post('json/menu.json',{},function(data){
							$.fn.zTree.init($("#tr"), setting, data);
						},'json');
					});
				</script>
				
			</div>
			<div data-options="iconCls:'icon-edit'" title="面板3">系统配置3</div>
			<div data-options="iconCls:'icon-save'" title="面板4">系统配置4</div>
		</div> 
	</div>
	<div data-options="region:'center'">
		<div id="tt" class="easyui-tabs" data-options="fit:true">
			<div data-options="iconCls:'icon-add'" title="面板1">系统配置1</div>
			<div data-options="iconCls:'icon-remove', closable:true" title="面板2">系统配置2</div>
			<div data-options="iconCls:'icon-edit'" title="面板3">
				
			</div>
			<div data-options="iconCls:'icon-save'" title="面板4">系统配置4<br>
				
			</div>
		</div> 
	
	</div>
	<div style="width:200px" data-options="region:'east'">东部</div>
	<div style="height:50px" data-options="region:'south'">南部</div>

</body>

	<script type="text/javascript">
		var $num = 0;	
		$("#addTabs").click(function(){
			//alert($(".easyui-tabs"));
			$num++;
			if(!$(".easyui-tabs").tabs('exists',"新增面板")){
				$(".easyui-tabs").tabs('add',{
					title:'新增面板',
					closable:true,
					
					content:'<h1>新增面板哈哈哈哈，计数器： '+ $num +'</h1>',
					tools:[{  		
			               iconCls:'icon-add',  		
			               handler:function(){  			
			                    alert('add')  		
			                   }  	
			             },{  		
			               iconCls:'icon-save',
			  		       handler:function(){ 
			 			        alert('save')  	
				               }  	
			             }]  

				});
			}
			else{
				var myTab = $(".easyui-tabs").tabs('getTab', '新增面板');
				$(".easyui-tabs").tabs('update',{
					tab:myTab,
					options:{
						content:'<h1>更新面板哈哈哈哈，计数器： '+ $num +'</h1>'
						
					}
				});
				$(".easyui-tabs").tabs('select','新增面板');
			}
			
		});
	
	</script>
</html>
