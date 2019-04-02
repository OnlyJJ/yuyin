<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>交易编码一览表</title>
<link href="http://cdn.amazeui.org/amazeui/2.7.2/css/amazeui.min.css"
	rel="stylesheet" type="text/css" />
<link href="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"
	rel="stylesheet" type="text/css" />
<style>
body {
	font-size: 12px !important;
}
</style>
</head>
<body>
	<div style="width: 30%;float:left" class="am-panel am-panel-default">
		<div class="am-panel-hd">交易编码</div>
		<div class="am-panel-bd">
			<table class="am-table">
				<thead>
					<tr>
						<th>交易编码</th>
						<th>交易描述</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${resultCodes }" var="line">
						<tr>
							<td>${line.code }</td>
							<td>${line.msg }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div style="width: 5%;float:left">
		<table class="am-table"></table>
	</div>

	<div style="width: 30%;float:left" class="am-panel am-panel-default">
		<div class="am-panel-hd">消息编码</div>
		<div class="am-panel-bd">
			<table class="am-table">
				<thead>
					<tr>
						<th>消息编码</th>
						<th>消息描述</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${msgCodes }" var="line">
						<tr>
							<td>${line.type }</td>
							<td>${line.remark }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>