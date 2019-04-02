<%@page import="com.jiujun.voice.common.utils.RequestUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	RequestUtil.getBasePath(request);
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>CDocer接口文档 ${title} </title>
<link rel="icon" type="image/png" href="${basePath}assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="${basePath}assets/i/app-icon72x72@2x.png">
<link rel="stylesheet"
	href="//cdn.amazeui.org/amazeui/2.7.2/css/amazeui.min.css" />
<link rel="stylesheet" href="${basePath}assets/css/admin.css">
<link rel="stylesheet" href="${basePath}assets/css/app.css">
<style>
.hide {
	display: none !important;
}

.block {
	display: block !important;
}

.am-panel-bd {
	padding: 0.25rem !important;
}

textarea {
	width: 100% !important;
	font-size: 12px !important;
}

.am-btn {
	padding: .5em 0em !important;
}

.sub-menu {
	font-size: 13px !important;
}

.tpl-left-nav-sub-menu a {
	padding: 4px 14px 3px 30px !important;
}

.tpl-left-nav-item .nav-link {
	padding: 5px 15px !important;
	padding-top: 3px !important;
}

.table-main {
	font-size: 0.9rem !important;
	padding: .3rem !important;
}
.am-table {
    margin-bottom: 0.6rem  !important;
}
.tpl-portlet-components .portlet-title .caption {
    font-size: 14px !important;
    line-height: 14px !important;
    padding: 5px 0 !important;
}
.tpl-portlet-components {
    padding: 12px 15px 5px !important;
    margin-bottom: 5px !important;
}
.am-panel-hd {
    font-size: 14px !important;
}
</style>
</head>
<body data-type="generalComponents">
	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand">
			<a href="javascript:;" class="tpl-logo"> <img
				src="${basePath}assets/img/logo.png" alt="">
			</a>
		</div>
		<div
			class="am-icon-list tpl-header-nav-hover-ico am-fl am-margin-right">
		</div>
		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
			data-am-collapse="{target: '#topbar-collapse'}">
			<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
		</button>
		<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
		</div>
	</header>
	<div class="tpl-page-container tpl-page-header-fixed">
		<div class="tpl-left-nav tpl-left-nav-hover">
			<div class="tpl-left-nav-title">指令列表</div>
			<div class="tpl-left-nav-list">
				<ul class="tpl-left-nav-menu">
					<c:forEach items="${cmdDocuments }" var="line">
						<li class="tpl-left-nav-item"><a href="javascript:;"
							class="nav-link tpl-left-nav-link-list"> <i
								class="am-icon-wpforms"></i> <span>${line.name }</span> <i
								class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right ${line.cmd==cmdDocument.cmd?'tpl-left-nav-more-ico-rotate':'' }"></i>
						</a>
							<ul class="tpl-left-nav-sub-menu"
								style="${line.cmd==cmdDocument.cmd?'display:block;':''}">
								<li><c:forEach items="${line.actions }" var="action">
										<a href="?cmd=${line.cmd }&action=${action.action}"> <i
											class="am-icon-angle-right"></i> <span class="sub-menu"
											style="${action.action==actionDocument.action?'font-weight: bold;':''}">${action.name }</span>
										</a>
									</c:forEach></li>
							</ul></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="tpl-content-wrapper">
			<div class="tpl-content-page-title">接口说明</div>
			<ol class="am-breadcrumb">
				<li><a href="#" class="am-icon-home">${cmdDocument.name }</a></li>
				<li class="am-active">${actionDocument.name }&nbsp;&nbsp;&nbsp;<span
					style="color: red">${actionDocument.needLogin?'(登录鉴权)':'' }</span>：<a
					target="_blank"
					href="${basePath }service/call.do?action=${actionDocument.action }">/service/call.do?action=${actionDocument.action }</a></li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 请求参数
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table
								class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th class="table-title">参数名</th>
										<th class="table-title">参数类型</th>
										<th class="table-title am-hide-sm-only">参数格式</th>
										<th class="table-title am-hide-sm-only">是否可空</th>
										<th class="table-title">描述</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${actionDocument.input.fields }" var="line">
										<tr>
											<td>${line.name }</td>
											<td><c:out value="${line.express }"></c:out></td>
											<td>${line.format }</td>
											<td>${line.allowNull?'可空':'不可空' }</td>
											<td>${line.remark }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 响应参数
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table
								class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th class="table-title">参数名</th>
										<th class="table-title">参数类型</th>
										<th class="table-title am-hide-sm-only">是否可空</th>
										<th class="table-title">描述</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${actionDocument.output.fields }" var="line">
										<tr>
											<td>${line.name }</td>
											<td><c:out value="${line.express }"></c:out></td>
											<td>${line.allowNull?((line.name eq 'code'||line.name eq 'msg')?'不可空':'可空'):'不可空' }</td>
											<td>${line.remark }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${!empty actionDocument.relotions  }">
				<c:forEach items="${actionDocument.relotions }" var="paramDocument">
					<div class="tpl-portlet-components">
						<div class="portlet-title">
							<div class="caption font-green bold">
								<span class="am-icon-code"></span> ${paramDocument.express } 说明
							</div>
							<div class="am-g">
								<div class="am-u-sm-12">
									<table
										class="am-table am-table-striped am-table-hover table-main">
										<thead>
											<tr>
												<th class="table-title">参数名</th>
												<th class="table-title">参数类型</th>
												<th class="table-title am-hide-sm-only">参数格式</th>
												<th class="table-title am-hide-sm-only">是否可空</th>
												<th class="table-title">描述</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${paramDocument.fields }" var="line">
												<tr>
													<td>${line.name }</td>
													<td><c:out value="${line.express }"></c:out></td>
													<td>${line.format }</td>
													<td>${line.allowNull?'可空':'不可空' }</td>
													<td>${line.remark }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>


			<!-- 交易编码 -->
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span>交易编码说明
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table
								class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th class="table-title">交易编码</th>
										<th class="table-title">描述</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${cmdDocument.resultCodes }" var="result">
										<tr>
											<td>${result.code }</td>
											<td>${result.msg }</td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="2"><a style="float: right" href="result.do">交易编码和消息对照表</a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>


			<!-- 报文测试 -->
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span>报文测试
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<form action="invoke.do" method="post" id="dataForm">
								<input type="hidden" name="action"
									value="${actionDocument.action }">
								<div class="am-panel am-panel-default"
									style="width: 40%;float: left;">
									<div class="am-panel-hd">请求报文</div>
									<div class="am-panel-bd">
										<textarea class="" rows="20" id="doc-ta-1" name="context">${actionInputMsgDocument.msg }</textarea>
									</div>
								</div>
								<div class="am-panel am-panel-default"
									style="width: 2%;float: left;">
									<button type="button" class="am-btn am-btn-default"
										onclick="invoke()" style="width: 100%;min-width: 22px">Go</button>
								</div>

								<div class="am-panel am-panel-default"
									style="width: 58%;float: right;">
									<div class="am-panel-hd">响应报文</div>
									<div class="am-panel-bd">
										<textarea class="" id="result" rows="20">${actionOutputMsgDocument.msg }</textarea>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="//cdn.staticfile.org/jquery/3.3.1/jquery.min.js"></script>
	<script src="//cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
	<script src="${basePath}assets/js/app.js"></script>
	<script>
		function invoke() {
			$.ajax({
				type : "POST",
				dataType : 'json',
				data : $("#dataForm").serialize(),
				url : 'invoke.do',
				timeout : 60000,
				success : function(json) {
					$("#result").val(json.datas);
					alert(json.msg);
				},
				error : function() {}
			});
		}
	</script>
</body>
</html>