package com.jiujun.voice.common.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Coody
 *
 */
public class IgnoreSessionRequestWrapper extends HttpServletRequestWrapper {

	public static HttpSession httpSession = new NoHttpSession();

	public IgnoreSessionRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public HttpSession getSession(boolean create) {
		return httpSession;
	}

	@Override
	public HttpSession getSession() {
		return httpSession;
	}

}
