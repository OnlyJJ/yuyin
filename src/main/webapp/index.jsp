<%@page import="ch.qos.logback.classic.Logger"%>
<%@page import="com.jiujun.voice.common.logger.util.LogUtil"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="ch.qos.logback.classic.LoggerContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	Logger logger=loggerContext.getLogger(LogUtil.class.getPackage().getName());
	out.println(logger.getLevel().toString());
%>
