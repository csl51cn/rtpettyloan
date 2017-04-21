package com.global.framework.system.log;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.OperateLog;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.LogService;
import com.global.framework.system.web.common.session.SessionManager;
import com.global.framework.util.IPUtil;
import com.global.framework.util.SysUtils;

/**
 * 日志记录，添加、删除、修改方法AOP
 * 
 */
@Aspect
public class LogAspect {

	@Autowired
	private LogService logService;// 日志记录Service

	public LogAspect() {
	}

	/**
	 * 添加业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.global..*.*DaoImpl.insert*(..))")
	public void insertServiceCall() {
	}

	/**
	 * 修改业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.global..*.*DaoImpl.update*(..))")
	public void updateServiceCall() {
	}

	/**
	 * 删除业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.global..*.*DaoImpl.delete*(..))")
	public void deleteServiceCall() {
	}

	/**
	 * 管理员添加操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "insertServiceCall()", argNames = "rtv", returning = "rtv")
	public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv)
			throws Throwable {
		saveLog(joinPoint, "1");
	}

	/**
	 * 管理员修改操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "updateServiceCall()", argNames = "rtv", returning = "rtv")
	public void updateServiceCallCalls(JoinPoint joinPoint, Object rtv)
			throws Throwable {
		saveLog(joinPoint, "2");
	}

	/**
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "deleteServiceCall()", argNames = "rtv", returning = "rtv")
	public void deleteFilmCallCalls(JoinPoint joinPoint, Object rtv)
			throws Throwable {
		saveLog(joinPoint, "3");
	}

	private void saveLog(JoinPoint joinPoint, String type) throws Exception,
			BaseException {
		// 获取登录管理员id
		HttpServletRequest request = null;
		try {
			request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			return;
		}
		
		User user = SessionManager.getSession(request);

		String loginName = "";
		String userid = "";
		if (user != null) {
			loginName = user.getUserName();
			userid = user.getUserCode();
		} else {
			userid = "UNKNOWN";
			loginName = "匿名用户";
		}

		String ip = IPUtil.getIpAddr(request);

		// 获取方法名
		String methodName = joinPoint.getSignature().getName();

		// 获取操作内容
		String opContent = adminOptionContent(joinPoint.getArgs(), methodName);

		// 创建日志对象
		OperateLog sysLog = new OperateLog();
		sysLog.setOperateIp(ip);
		sysLog.setUserId(userid);
		sysLog.setUserName(loginName);
		sysLog.setOperateType(type);
		sysLog.setOperateDate(SysUtils.getNowDateTime());
		sysLog.setContent(opContent);

		logService.insertLog(sysLog);// 添加日志
	}

	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 */
	private String adminOptionContent(Object[] args, String mName)
			throws Exception {
		if (args == null) {
			return null;
		}
		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {
			// 获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			rs.append("[参数" + index + "，类型：" + className + "，值：");
			// 获取对象的所有方法
			if (!String.class.equals(info.getClass())) {
				Method[] methods = info.getClass().getDeclaredMethods();
				// 遍历方法，判断get方法
				for (Method method : methods) {
					String methodName = method.getName();
					// 判断是不是get方法
					if (methodName.indexOf("get") == -1) {// 不是get方法
						continue;// 不处理
					}
					Object rsValue = null;
					try {
						// 调用get方法，获取返回值
						rsValue = method.invoke(info);
						if (rsValue == null) {// 没有返回值
							continue;
						}
					} catch (Exception e) {
						continue;
					}
					// 将值加入内容中
					rs.append("(" + methodName + " : " + rsValue + ")");
				}
			} else {
				// 将值加入内容中
				rs.append("(arg : " + info + ")");
			}
			rs.append("]");
			index++;
		}
		return rs.toString();
	}

}
