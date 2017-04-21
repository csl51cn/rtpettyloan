package com.pactera.fems.message.wg.service;

import com.pactera.fems.message.wg.domain.WGReqMsgHead;
import java.util.Map;
import org.global.framework.xmlbeans.bean.DataCheckException;

public abstract interface WGIndividualLCYService
{
  public abstract Map doQueryIndividualFXSEQuota(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doAddLimitedIndividualFXSEInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doAddUnLimitedIndividualFXSEInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doQueryIndividualFXSEInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doModifyIndividualFXSEInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doDeleteIndividualFXSEInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doMakeUpOtherIndividualFXSEInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doMakeUpIndividualTradeFXSEInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doQueryIndividualFXSAQuota(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doAddLimitedIndividualFXSAInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doAddUnLimitedIndividualFXSAInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doQueryIndividualFXSAInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doModifyIndividualFXSAInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doDeleteIndividualFXSAInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doMakeUpOtherIndividualFXSAInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doMakeUpIndividualTradeFXSAInfo(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doMakeUpSignStatus(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
  
  public abstract Map doQuerySafeExRate(Map paramMap, WGReqMsgHead paramWGReqMsgHead)
    throws DataCheckException;
}


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\service\WGIndividualLCYService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */