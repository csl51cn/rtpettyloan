package com.global.fems.batch.thread;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;

import com.global.fems.business.dao.TaskTimeoutDao;
import com.global.fems.business.domain.TaskTimeout;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYCancel;
import com.global.fems.message.service.impl.IndividualMsgBizManager;
import com.global.framework.exception.BaseException;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.SpringContextUtil;
import com.global.framework.util.SysUtils;

/**
 * 
 * 超时任务处理 
 * 
 * @author Sili Jiang
 * @version 2015-07-12
 * 
 */
public class TimeoutTask extends Thread {

	private static final Logger log = Logger.getLogger(TimeoutTask.class);

	private TaskTimeoutDao taskTimeoutDao;
	
	private SysCommonService sysCommonService;

	private IndividualMsgBizManager  individualMsgBizManager;

	public TimeoutTask() throws DataCheckException {
		super();
		taskTimeoutDao = (TaskTimeoutDao) SpringContextUtil.getBean("taskTimeoutDao");
		sysCommonService = (SysCommonService) SpringContextUtil.getBean("sysCommonService");
		individualMsgBizManager = (IndividualMsgBizManager) SpringContextUtil.getBean("individualMsgBizManager");
	}

	@Override
	public void run() {
		while (true) {
			try {
				List<TaskTimeout> timeouts = taskTimeoutDao.findListByState("0");
				if (timeouts != null && timeouts.size() > 0) {
					for (TaskTimeout to : timeouts) {
						//调用个人结售汇信息查询接口
						Map retDSMap = this.individualMsgBizManager.doQueryIndividualFXSEAInfo(to.getBizType(),
								null, to.getIdTypeCode(), to.getIdCode(),
								to.getCtyCode(), to.getTxDate(), to.getLaunchOperNo(), null);
						
						//解析查询反馈结果
						String refNo = ""; //业务参号
						List rowsets = (List) retDSMap.get("ROWSET");
						if (rowsets != null && rowsets.size() > 0) {
							for (int i = 0; i < rowsets.size(); i++) {
								Map m = (Map) rowsets.get(i);
								String BANK_SELF_NUM = (String) m.get("BANK_SELF_NUM");//银行自身流水号
								if (BANK_SELF_NUM.equals(to.getTxnSerialNo())) {
									refNo = (String) m.get("REFNO");
									break;
								}
							}
						}
						
						String dealState = "1";//处理成功
						String retCode = MsgErrorCodeEnum.ERRCODE_00000.getCode();
						String retMsg = MsgErrorCodeEnum.ERRCODE_00000.getValue();
						if (StringUtils.isNotBlank(refNo)) {
							//业务参号不为空,表示登记成功过,此时需要调用撤消接口
							RecvIndividualLCYCancel cancel = new RecvIndividualLCYCancel();
							cancel.setSEQNO(to.getTxnSerialNo());
							cancel.setTRADE_TYPE(to.getBizType());
							cancel.setCANCEL_REASON("06");//06-其他
							cancel.setCANCEL_REMARK("规定时间内未收到外汇局反馈结果");
							try {
								String newTxSeqNo = sysCommonService.getSeqNoByOperNo("wfl_taskinfo", to.getLaunchOperNo());//生成新撤消交易流水，供调用外管接口使用
								this.individualMsgBizManager.doDeleteIndividualFXSEAInfo(cancel, newTxSeqNo, refNo, to.getLaunchOperNo());
							} catch (DataCheckException e) {
								log.error("定时任务调用个人结售汇信息撤消接口出错:", e);
								retCode = e.getCode();
								retMsg = e.getReason();
								dealState = "2";//处理失败
							}
						} else {
							retMsg += "[未在外汇局系统查询到该笔流水的登记结果,该笔任务状态为处理成功]"; 
						}
						
						//处理完成后
						TaskTimeout timeout = new TaskTimeout();
						timeout.setNguid(to.getNguid());
						timeout.setDealState(dealState);
						timeout.setDealTime(SysUtils.getNowDateTime());
						timeout.setRefNo(refNo);
						timeout.setRetCode(retCode);
						timeout.setRetMsg(retMsg);
						taskTimeoutDao.updateDealState(timeout);
					}
				}
				Thread.sleep(60 * 1000 * 10);//10分钟运行一次
			} catch (BaseException e) {
				log.error("更新超时任务状态信息失败", e);
			} catch (DataCheckException e) {
				log.error("调用外管接口失败", e);
			} catch (InterruptedException e) {
				log.error("线程休眠处理异常", e);
			}
		}
	}

}
