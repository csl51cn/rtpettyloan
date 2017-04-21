package com.global.framework.system.web.common.session;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.global.framework.system.constants.LoginStatusCodeConstants;
import com.global.framework.system.domain.User;
import com.global.framework.util.SysUtils;

/**
 * Session管理器
 * 
 * @author cqchenf@qq.com
 * @date 2012-1-4 下午10:27:00
 * @version v1.0
 */
public class SessionManager {
	private static final Log log = LogFactory.getLog(SessionManager.class);

	private static Map<String, User> sessionMap = null;

	public static Map<String, User> getSessionMap() {
		return sessionMap;
	}

	/**
	 * 创建session
	 * @param user
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public synchronized static String createSession(User user, HttpServletRequest request) {
		String statusCode = LoginStatusCodeConstants.OK.getCode();
		String sid = request.getRequestedSessionId();
		user.setLoginTime(SysUtils.getNowDateTime());
		user.setLoginIp(getIpAddr(request));
		
		if(null != sessionMap){
			if(null != sessionMap.get(sid)){
				//同一客户端不允许同时登录多个用户，请先退出其他用户后再登录！
				return LoginStatusCodeConstants.REPAT_LOGIN.getCode();
			}else{
				Iterator it = sessionMap.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					User u = sessionMap.get(key);
					if (user.getUserId().equals(u.getUserId())){
						//如果当前用户已登录，后者将前者踢除
						sessionMap.remove(key);
						request.getSession().removeAttribute(key);
						log.info(user.getUserCode()+"用户在"+SysUtils.getNowDateTime()+"其他地方登录！");
						statusCode = LoginStatusCodeConstants.ALREADY_LOGIN.getCode();
						break;
					}
				}
				
				request.getSession(true).setAttribute(sid, user);
				sessionMap.put(sid, user);
			}
		}else{
			request.getSession(true).setAttribute(sid, user);
			sessionMap = new ConcurrentHashMap<String, User>();
			sessionMap.put(sid, user);
		}
		return statusCode;
	}

	/**
	 * 获取session
	 * @param request
	 * @return user
	 */
	public static User getSession(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(request.getRequestedSessionId());
	}
	
	/**
	 * 销毁session
	 * @param request
	 */
	public static void destorySession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session) {
			session.invalidate();
		}
	}
	
	/**
	 * 获取登录IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// localhost的情况
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}
}
