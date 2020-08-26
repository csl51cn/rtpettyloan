package com.global.fems.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.global.fems.business.dao.DeclareResultDao;
import com.global.fems.business.domain.DeclareResult;
import com.global.fems.business.enums.ReturnMsgCodeEnum;
import com.global.fems.business.service.Observer;
import com.global.fems.business.service.QueryDeclareService;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.framework.dbutils.support.Entity;
import com.global.framework.dbutils.support.PageBean;
import com.global.param.domain.ResultModel;
import com.pactera.fems.message.jrb.domain.JRBRET;
import com.pactera.fems.message.jrb.domain.JRBRespBody;
import com.pactera.fems.message.jrb.domain.JRBRespHeaderMsg;
import com.pactera.fems.message.jrb.domain.JRBRespMsg;
import com.pactera.fems.message.jrb.domain.business.response.QueryDeclaredRtrTx;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 查询上报结果管理Service接口实现类
 */
@Service
public class QueryDeclareServiceImpl implements QueryDeclareService {
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;

    @Autowired
    private DeclareResultDao declareResultDao;
    @Autowired
    private List<Observer> observers;


    /**
     * 向监管平台查询申报状态
     *
     * @param id     记录id
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel queryDeclared(String id, String userId) throws Exception {
        DeclareResult declareResult = declareResultDao.findDeclareResultById(id);
        Map map = jrbBizInfoDeclareManager.packageMsgHeader(declareResult.getRemoteFilePath(), declareResult.getDataType());
        JRBRespMsg respMsg = (JRBRespMsg) map.get("respMsg");
        if (respMsg != null) {
            JRBRespHeaderMsg msg = respMsg.getHeader().getMsg();
            JRBRET ret = msg.getRet();
            String retCode = ret.getRetCode();
            declareResult.setModifiedId(userId);
            declareResult.setGmtModified(new Date());
            if ("000000".equals(retCode)) {
                //查询成功,通过body中的MSG_CODE判断是否成功:000001数据导入中,000000成功
                JRBRespBody body = respMsg.getBody();
                QueryDeclaredRtrTx msgInBody = (QueryDeclaredRtrTx) body.getRtrtx();
                String msgCode = msgInBody.getMsgCode();
                declareResult.setDeclareResultCode(msgCode);
                if (StringUtils.equals("200000", msgCode)) {
                    //上报成功
                    declareResult.setDeclareResult("上报成功");
                    declareResultDao.saveOrUpdate(declareResult);
                    notifyObservers(declareResult);
                    return ResultModel.ok("查询成功:返回结果为" + msgInBody.getMsgInfo());
                } else if (StringUtils.equals("000000", msgCode)) {
                    //数据待解析
                    declareResult.setDeclareResult("数据待解析");
                    return ResultModel.ok("查询成功:返回结果为" + msgInBody.getMsgInfo());
                } else if (StringUtils.equals("100000", msgCode)) {
                    //数据导入中
                    declareResult.setDeclareResult("数据导入中");
                    declareResultDao.saveOrUpdate(declareResult);
                    return ResultModel.ok("查询成功:返回结果为" + msgInBody.getMsgInfo());
                } else {
                    //上报失败
                    declareResult.setDeclareResult("上报失败");
                    declareResultDao.saveOrUpdate(declareResult);
                    notifyObservers(declareResult);
                    return ResultModel.fail("查询成功:返回结果为" + msgInBody.getMsgInfo());
                }

            } else {
                //查询失败
                declareResult.setDeclareResultCode(ret.getRetCode());
                declareResult.setDeclareResult(ret.getRetMsg());
                declareResultDao.saveOrUpdate(declareResult);
                notifyObservers(declareResult);
                return ResultModel.fail("查询失败:" + (ReturnMsgCodeEnum.getValueByCode(retCode) == null ? ret.getRetMsg() : ReturnMsgCodeEnum.getValueByCode(retCode)));
            }
        } else {
            JSONObject respMsgJsonObject = (JSONObject) map.get("respMsgJsonObject");
            JSONObject getTx = respMsgJsonObject.getJSONObject("transaction").getJSONArray("body").getJSONObject(0).getJSONArray("GetTx").getJSONObject(0);
            String systemErrorCode = getTx.getJSONArray("SYS_ERRCODE").getString(0);
            return ResultModel.fail("查询失败:" + ReturnMsgCodeEnum.getValueByCode(systemErrorCode));
        }
    }

    /**
     * 查询在本地数据库中的上报结果
     *
     * @param batchNo         批次号
     * @param transactionType 报文类型
     * @param startDate       开始日期
     * @param endDate         结束日期
     * @param pageBean        分页对象
     * @return
     */
    @Override
    public PageBean queryRawDeclareData(String batchNo, String transactionType, String startDate, String endDate, PageBean pageBean) {

        return declareResultDao.findRawDeclareData(StringUtils.trim(batchNo), transactionType, startDate, endDate, pageBean);
    }

    /**
     * 通过文件名查询上报结果记录
     *
     * @param batchFileName 文件名
     * @return
     */
    @Override
    public DeclareResult findByRemoteFilePath(String batchFileName) {
        return declareResultDao.findByRemoteFilePath(batchFileName);
    }

    /**
     * 更新记录
     *
     * @param declareResult
     */
    @Override
    public void update(DeclareResult declareResult) {
        declareResultDao.saveOrUpdate(declareResult);
    }


    /**
     * 通知观察者
     *
     * @param entity
     */
    @Override
    public void notifyObservers(Entity entity) {
        observers.forEach(observer -> observer.update(entity));
    }
}
