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
<script type="text/javascript" src="js/jquery.ocupload-1.1.2.js"></script>
<!-- 	<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script> -->

</head>
<body>
    <div>
        <span>手动实现的不刷新上传文件</span><br>
	    <iframe name="fr"></iframe>
	    <form target="fr" enctype="multipart/form-data" action="xxx" method="post">
	        <input type="file" name="myFile"/>
	        <input type="submit" name="upload" />
	    </form>
    </div>
    <br>
    <hr>
    <div>
        <span>OCUpload实现的不刷新上传文件</span><br>
        <input id="btn" type="button" value="上传"/>
        <script type="text/javascript">
            $(function(){
                $("#btn").upload({
                    action:"xxx",
                    name:"myFile"
                });
                
                /* 取消下面两段的注释可在页面查看具体的实现细节
                $("input[name='myFile']").last().css({
                    opacity:50, 
                    margin:'1px', 
                    width:'100px', 
                    height:'50px',
                    backgroundColor:'#ff0',
                    position:'absolute',
                    display:'inline'
                    });
                $("div").last().css({
                    opacity:50, 
                    width:'400px', 
                    height:'250px',
                    backgroundColor:'#999',
                    });
                */
            });
        </script>
    </div>
</body>

</html>
