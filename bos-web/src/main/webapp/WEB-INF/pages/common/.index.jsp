<html><script type="text/javascript">
	// 初始化ztree菜单
	$(function() {
		var setting = {
			data : {
				simpleData : { // 简单数据 
					enable : true
				}
			},
			callback : {
				onClick : onClick
			}
		};
		
		// 基本功能菜单加载
		$.ajax({
			url : &apos;${pageContext.request.contextPath}/json/menu.json&apos;,
			type : &apos;POST&apos;,
			dataType : &apos;text&apos;,
			success : function(data) {
				var zNodes = eval(&quot;(&quot; + data + &quot;)&quot;);
				$.fn.zTree.init($(&quot;#treeMenu&quot;), setting, zNodes);
			},
			error : function(msg) {
				alert(&apos;菜单加载异常!&apos;);
			}
		});
		
		// 系统管理菜单加载
		$.ajax({
			url : &apos;${pageContext.request.contextPath}/json/admin.json&apos;,
			type : &apos;POST&apos;,
			dataType : &apos;text&apos;,
			success : function(data) {
				var zNodes = eval(&quot;(&quot; + data + &quot;)&quot;);
				$.fn.zTree.init($(&quot;#adminMenu&quot;), setting, zNodes);
			},
			error : function(msg) {
				alert(&apos;菜单加载异常!&apos;);
			}
		});
		
		// 页面加载后 右下角 弹出窗口
		/**************/
		window.setTimeout(function(){
			$.messager.show({
				title:&quot;消息提示&quot;,
				msg:&apos;欢迎登录，超级管理员！ &lt;a href=&quot;javascript:void&quot; onclick=&quot;top.showAbout();&quot;&gt;联系管理员&lt;/a&gt;&apos;,
				timeout:5000
			});
		},3000);
		/*************/
		
		$(&quot;#btnCancel&quot;).click(function(){
			$(&apos;#editPwdWindow&apos;).window(&apos;close&apos;);
		});
		
		$(&quot;#btnEp&quot;).click(function(){
			alert(&quot;修改密码&quot;);
		});
	});

	function onClick(event, treeId, treeNode, clickFlag) {
		// 判断树菜单节点是否含有 page属性
		if (treeNode.page!=undefined &amp;&amp; treeNode.page!= &quot;&quot;) {
			var content = &apos;&lt;div style=&quot;width:100%;height:100%;overflow:hidden;&quot;&gt;&apos;
					+ &apos;&lt;iframe src=&quot;&apos;
					+ treeNode.page
					+ &apos;&quot; scrolling=&quot;auto&quot; style=&quot;width:100%;height:100%;border:0;&quot; &gt;&lt;/iframe&gt;&lt;/div&gt;&apos;;
			if ($(&quot;#tabs&quot;).tabs(&apos;exists&apos;, treeNode.name)) {// 判断tab是否存在
				$(&apos;#tabs&apos;).tabs(&apos;select&apos;, treeNode.name); // 切换tab
				var tab = $(&apos;#tabs&apos;).tabs(&apos;getSelected&apos;); 
				$(&apos;#tabs&apos;).tabs(&apos;update&apos;, {
				    tab: tab,
				    options: {
				        title: treeNode.name,
				        content: content
				    }
				});
			} else {
				// 开启一个新的tab页面
				$(&apos;#tabs&apos;).tabs(&apos;add&apos;, {
					title : treeNode.name,
					content : content,
					closable : true
				});
			}
		}
	}

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $(&apos;#easyuiTheme&apos;);
		var url = $easyuiTheme.attr(&apos;href&apos;);
		var href = url.substring(0, url.indexOf(&apos;themes&apos;)) + &apos;themes/&apos;
				+ themeName + &apos;/easyui.css&apos;;
		$easyuiTheme.attr(&apos;href&apos;, href);
		var $iframe = $(&apos;iframe&apos;);
		if ($iframe.length &gt; 0) {
			for ( var i = 0; i &lt; $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find(&apos;#easyuiTheme&apos;).attr(&apos;href&apos;, href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		$.messager
		.confirm(&apos;系统提示&apos;,&apos;您确定要退出本次登录吗?&apos;,function(isConfirm) {
			if (isConfirm) {
				location.href = &apos;${pageContext.request.contextPath }/login.jsp&apos;;
			}
		});
	}
	// 修改密码
	function editPassword() {
		$(&apos;#editPwdWindow&apos;).window(&apos;open&apos;);
	}
	// 版权信息
	function showAbout(){
		$.messager.alert(&quot;宅急送 v1.0&quot;,&quot;管理员邮箱: zqx@itcast.cn&quot;);
	}
</script><body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:80px;padding:10px;background:url('./images/header_bg.png') no-repeat right;">
		<div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
			[<strong>超级管理员</strong>]，欢迎你！
		</div>
		<div style="position: absolute; right: 5px; bottom: 10px; ">
			<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
			<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
		</div>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('black');">black</div>
			<div onclick="changeTheme('bootstrap');">bootstrap</div>
			<div onclick="changeTheme('metro');">metro</div>
		</div>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="editPassword();">修改密码</div>
			<div onclick="showAbout();">联系管理员</div>
			<div class="menu-sep"></div>
			<div onclick="logoutFun();">退出系统</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'菜单导航'" style="width:200px">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="基本功能" data-options="iconCls:'icon-mini-add'" style="overflow:auto">
				<ul id="treeMenu" class="ztree"></ul>
			</div>
			<div title="系统管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
				<ul id="adminMenu" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tabs" fit="true" class="easyui-tabs" border="false">
			<div title="消息中心" id="subWarp" style="width:100%;height:100%;overflow:hidden">
				<iframe src="${pageContext.request.contextPath }/page_common_home.action" style="width:100%;height:100%;border:0;"></iframe>
				                             
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;padding:10px;background:url('./images/header_bg.png') no-repeat right;">
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td style="width: 300px;">
						<div style="color: #999; font-size: 8pt;">
							传智播客 | Powered by <a href="http://www.itcast.cn/">itcast.cn</a>
						</div>
					</td>
					<td style="width: *;" class="co1"><span id="online" style="background: url(${pageContext.request.contextPath }/images/online.png) no-repeat left;padding-left:18px;margin-left:3px;font-size:8pt;color:#005590;">在线人数:1</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	             
    <div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false" maximizable="false" icon="icon-save" style="width: 300px; height: 160px; padding: 5px;
        background: #fafafa">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="Password" class="txt01"/></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="Password" class="txt01"/></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a> 
                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
</body></html>