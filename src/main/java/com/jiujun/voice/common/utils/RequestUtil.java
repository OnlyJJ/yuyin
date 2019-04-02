package com.jiujun.voice.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @remark HTTP工具类。
 * @author Coody
 * @email 644556636@qq.com
 * @blog 54sb.org
 */
public class RequestUtil {


	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (!isInvialIp(ip)) {
			return ip;
		}
		ip = request.getHeader("Proxy-Client-IP");
		if (!isInvialIp(ip)) {
			return ip;
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (!isInvialIp(ip)) {
			return ip;
		}
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (!isInvialIp(ip)) {
			return ip;
		}
		ip = request.getRemoteAddr();
		if (!isInvialIp(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Real-IP");
		if (!isInvialIp(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!isInvialIp(ip)) {
			return ip;
		}
		if(StringUtil.isNullOrEmpty(ip)){
			return "";
		}
		if (ip.contains(",")) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				String ipTemp = ip.substring(0, index);
				if (!isInvialIp(ipTemp)) {
					return ipTemp;
				}
			}
		}
		return "";
	}

	private static boolean isInvialIp(String ip) {
		if (StringUtil.isNullOrEmpty(ip)) {
			return true;
		}
		if ("127.0.0.1".equals(ip) || ip.equalsIgnoreCase("localhost")) {
			return true;
		}
		if (StringUtil.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			return true;
		}
		return false;
	}

	public static String getRequestUri(HttpServletRequest request) {
		String uri = request.getServletPath();

		String projectName = request.getContextPath();
		if (projectName != null && !projectName.trim().equals("")) {
			uri = uri.replace(projectName, "/");
		}
		uri = uri.replace("../", "/");
		while (uri.indexOf("//") > -1) {
			uri = uri.replace("//", "/");
		}
		return uri;
	}

	public static void keepParas(HttpServletRequest request) {
		Enumeration<String> paras = request.getParameterNames();
		if (StringUtil.isNullOrEmpty(paras)) {
			return;
		}
		while (paras.hasMoreElements()) {
			try {
				String string = (String) paras.nextElement();
				if (StringUtil.isNullOrEmpty(string)) {
					continue;
				}
				request.setAttribute(string.replace(".", "_"), request.getParameter(string));
			} catch (Exception e) {
				PrintException.printException(e);
			}

		}
	}


	public static String getRequestURI(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String projectName = request.getContextPath();
		if (projectName != null && !projectName.trim().equals("")) {
			uri = uri.replace(projectName, "/");
		}
		uri = uri.replace("../", "/");
		while (uri.indexOf("//") > -1) {
			uri = uri.replace("//", "/");
		}
		return uri;
	}

	/**
	 * 获取POST请求参数中数据
	 * 
	 * @param request
	 * @throws IOException
	 */
	public static String getPostContent(HttpServletRequest request) {
		String content = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (request.getHeader("Content-Encoding") != null
					&& request.getHeader("Content-Encoding").contains("gzip")) {
				inputStream = new GZIPInputStream(inputStream);
			}
			content = FileUtils.inputStream2String(inputStream,"UTF-8");
			return content;
		} catch (Exception e) {
			PrintException.printException(e);
		}
		return null;
	}

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + path + "/";
		request.getSession().setAttribute("basePath", basePath);
		request.setAttribute("basePath", basePath);
		return basePath;
	}

	public static void main(String[] args) {

	}

}
