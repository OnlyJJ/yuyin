<%@page import="com.jiujun.voice.common.utils.RequestUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	RequestUtil.getBasePath(request);
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CDocer Login</title>
<script src="https://cdn.staticfile.org/jquery/3.3.1/jquery.min.js"></script>
<script>
	(function() {
		$('.tests').fadeOut(250).fadeIn(250);
		setTimeout(arguments.callee, 500);
		ti();
	})();
	function ti() {
		setTimeout('ti()', 100);
	}
</script>
<style type="text/css">
input {
	border-top-width: 1px;
	font-weight: bold;
	border-right-color: #dddddd;
	border-left-width: 1px;
	font-size: 11px;
	border-left-color: #dddddd;
	background: white;
	border-bottom-width: 1px;
	border-bottom-color: #dddddd;
	color: #dddddd;
	border-top-color: #dddddd;
	font-family: verdana;
	border-right-width: 1px;
}
</style>
<body style='background:white'>
	<div align='center'>
		<p style='height:50px'></p>
		<span
			style="font-size:48px;font-weight:bold;font-family:微软雅黑;display:inline-block; color:white; text-shadow:1px 0 4px #a5a5a5,0 1px 4px #a5a5a5,0 -1px 4px #a5a5a5,-1px 0 4px #a5a5a5;filter:glow(color=#a5a5a5,strength=3)"
			id='id1'>CDocer Login<span style="display:none"></span></span><br />
		<div class='tests'
			style='background:url(${basePath}assets/img/timg.gif); background-repeat:no-repeat; background-position:center'
			align='center'>
			<p style='height:200px'></p>
		</div>
		<div style='width:400px;padding:32px; align=left'>
			<br>
			<form action='' method='post' id="loginForm">
				<b><span
					style='color:white;display:inline-block;text-shadow:1px 0px 4px #a5a5a5, 0px 1px 4px #a5a5a5, 0px -1px 4px #a5a5a5, -1px 0px 4px #a5a5a5'>PassWord：</span></b>
				<input name='DOCUMENT_LOGINED' id='pass' type='password' size='22'
					onclick="$('#msg').hide()"
					onkeydown="if(event.keyCode==13) ajaxlogin();" /> <input
					type='submit' value='submit' id="Submit" />
				<input name="action" value="login" type="hidden" />
			</form>
			<span id="msg"
				style="font-size:14px;font-family:隶书;color:rgb(242, 242, 242)"></span>
		</div>
	</div>
</body>
</html>