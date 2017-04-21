package com.global.framework.system.web.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.framework.system.domain.User;
import com.global.framework.system.web.common.session.SessionManager;

/**
 * URL过滤器
 *
 * @author cqchenf@qq.com
 * @version v1.0
 * @date 2013-1-26 下午8:13:30
 */
public class CheckLoginFilter implements Filter {
    private String noCheck;


    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath().substring(1);

        String[] noValidates = noCheck.split(",");
        boolean flag = false;
        for (int i = 0; i < noValidates.length; i++) {
            if (servletPath.equals(noValidates[i]) || servletPath.startsWith("resources/")) {
                flag = false;
                break;
            } else {
                flag = true;
            }
        }

        if (flag) {
            //获取session
//			User user = SessionManager.getSession(request);
            String sid = request.getRequestedSessionId();
            Map<String, User> map = SessionManager.getSessionMap();
            if (sid == null || null == map || (null != map && null == map.get(sid))) {
                PrintWriter out = response.getWriter();
                StringBuilder builder = new StringBuilder();
                builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
                builder.append("window.parent.location.href=\"");
                builder.append(request.getContextPath() + "/logon.do\";</script>");
                out.print(builder.toString());
                out.close();
                //response.sendRedirect(request.getContextPath()+"/logon");
            } else {
                chain.doFilter(req, resp);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }


    public void init(FilterConfig cfg) throws ServletException {
        noCheck = cfg.getInitParameter("noCheck");
    }

}
