package com.global.scheduler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.global.fems.business.domain.UsdCvsRate;
import com.global.fems.business.service.SafeExRateService;
import com.global.fems.business.service.SpfeQueryService;
import com.global.fems.interfaces.server.ServerConfigReader;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.SpringContextUtil;
import com.global.framework.util.SysUtils;

public class AutoImportSafeExRateThread implements Runnable {
	
	private static final Log log = LogFactory.getLog(AutoImportSafeExRateThread.class);
	private static SpfeQueryService spfeQueryService = (SpfeQueryService) SpringContextUtil.getBean("spfeQueryService");
	private static SafeExRateService safeExRateService = (SafeExRateService) SpringContextUtil.getBean("safeExRateService");
	private static SysCommonService sysCommonService = (SysCommonService) SpringContextUtil.getBean("sysCommonService");

	public void run() {
		while(true) {
			boolean state = this.execute();
			
			try {
				if (state) {
					System.out.println("休息1分钟...");
					Thread.sleep(60 * 1000);
				} 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void start() {
		AutoImportSafeExRateThread t = new AutoImportSafeExRateThread();
		new Thread(t).start();
	}

	@SuppressWarnings("unchecked")
	public boolean execute() {
		//获取系统时间
		String nowDay = SysUtils.getNowDateTime("dd");
		String nowHour = SysUtils.getNowDateTime("HH");
		String nowMin = SysUtils.getNowDateTime("mm");
		try {
			int exeDay = Integer.parseInt(ServerConfigReader.getInstance().getExeDay());
			String[] exeTimes = ServerConfigReader.getInstance().getExeTime().split(":");
			if (exeDay < 1 || exeDay > 31) {
				log.error("执行周期的日期范围为1-31");
				return false;
			}
			if (exeDay == Integer.parseInt(nowDay) && nowHour.equals(exeTimes[0]) && nowMin.equals(exeTimes[1])) {
				List<CommonOrgUser> list = sysCommonService.getCommonOrgUserList();
				if (list == null || list.size() == 0) {
					log.error("自动导入外管美元折算率执行出错:未配置外管用户信息");
					throw new BaseException("自动导入外管美元折算率执行出错:未配置外管用户信息");
				}

				CommonOrgUser cou = list.get(0);
				User user = new User();
				user.setUserCode(cou.getOPERNO());

				PageBean page = new PageBean();
				page.setPage(1);
				page.setRows(100);
				String yearMonth = SysUtils.getNowDateTime("yyyyMM");
				PageBean pageBean = spfeQueryService.usdCvsRateQuery(null, yearMonth, page, user);
				List<UsdCvsRate> ret = (List<UsdCvsRate>) pageBean.getDataList();
				safeExRateService.insertSafeExRate(yearMonth, ret, user);
				log.error("自动导入外管美元折算率结束....");
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error("自动导入外管美元折算率执行出错...." + e);
			return false;
		}
	}
	
	public static void main(String[] args) {
		start();
	}
}
