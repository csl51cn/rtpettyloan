package com.global.framework.system.web.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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

import com.global.framework.system.domain.Operate;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.web.common.CacheService;
import com.global.framework.system.web.common.session.SessionManager;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2011-9-29 下午9:44:30
 * @version v1.0
 */
public class CheckRightFilter implements Filter {
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
		
		//不需要检查的权限列表
		List<Operate> noCheckRights = CacheService.getNoCheckRightCacheList();
		for (Operate right : noCheckRights) {
			if(servletPath.equals(right.getReqUrl())){
				flag = false;
				break;
			}
		}
		
		if(flag){
			User user = SessionManager.getSession(request);
			List<RoleRight> rights = user.getRights();
			List<Map<String, String>> rightList = CacheService.getRights(rights);

			boolean isRight = false;
			for (Map<String, String> right : rightList) {
				if (servletPath.equals(right.get("reqUrl"))){
					isRight = true;
					break;
				}
			}
			
			if(!isRight){
				log.info(user.getUserCode()+"，您暂无访问"+servletPath+"的权限！");
				
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
				builder.append("window.location.href=\"");
				builder.append(request.getContextPath()+"/noRight.jsp\";</script>");
				out.print(builder.toString());
				out.close();
			}
		}
		chain.doFilter(req, res);
	}

	
	public void init(FilterConfig cfg) throws ServletException {
		noCheck = cfg.getInitParameter("noCheck");
	}

}
