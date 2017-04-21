package com.global.framework.system.web.common.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听器
 * @author cqchenf@qq.com
 * @date 2013-1-27 上午2:28:32
 * @version v1.0
 */
public class SessionListener implements HttpSessionListener {
	
	public void sessionCreated(HttpSessionEvent event) {

	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
//		System.out.println("当前销毁的sessionid="+session.getId());
		if(null != SessionManager.getSessionMap()){
			SessionManager.getSessionMap().get(session.getId());
//			System.out.println("####"+(null != user ? user.getUserId() : "")+"已退出");
			SessionManager.getSessionMap().remove(session.getId());
		}
	}
	
}
