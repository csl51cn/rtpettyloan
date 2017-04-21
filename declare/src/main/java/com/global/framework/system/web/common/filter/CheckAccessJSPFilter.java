package com.global.framework.system.web.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 禁止直接访问JSP页面
 * @author chen.feng
 * @date 2014-6-27 上午11:32:10
 */
public class CheckAccessJSPFilter implements Filter {

private static final Log log = LogFactory.getLog(CheckRightFilter.class);
	
	private String noCheck;

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;  
		HttpServletResponse response = (HttpServletResponse) res; 
		String servletPath = request.getServletPath().substring(1);
		
		boolean flag = false;
		String[] noValidates = noCheck.split(",");
		for (int i = 0; i < noValidates.length; i++) {
			if(servletPath.equals(noValidates[i])){
				flag = false;
				break;
			}else {
				flag = true;
			}
		}
		
		if (flag) {
			log.error("不允许直接访问JSP页面");
			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
			builder.append("window.location.href=\"");
			builder.append(request.getContextPath()+"/logon.do\";</script>");
			out.print(builder.toString());
			out.close();
		}
		chain.doFilter(req, res);
	}

	
	public void init(FilterConfig cfg) throws ServletException {
		noCheck = cfg.getInitParameter("noCheck");
	}

}
