/*    */ package com.pactera.fems.message.wg.support;
/*    */ 
/*    */ import com.pactera.fems.message.wg.domain.WGReqMsgHead;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MQEmulator
/*    */ {
/*    */   public static String request(String reqMsg, String serviceMethod, WGReqMsgHead head)
/*    */   {
/*    */     try
/*    */     {
/* 17 */       System.out.println("发送的请求报文:" + reqMsg);
/* 18 */       System.out.println("-----------------------------MQ处理完毕");
/*    */       
/* 20 */       String retMsg = buildRspMsg(serviceMethod, head);
/* 21 */       System.out.println("MQ响应报文：" + retMsg);
/* 22 */       return retMsg;
/*    */     } catch (Exception e) {
/* 24 */       e.printStackTrace();
/*    */     }
/* 26 */     return null;
/*    */   }
/*    */   
/*    */   private static String buildRspMsg(String serviceMethod, WGReqMsgHead head) throws Exception {
/* 30 */     String retMsg = "";
/* 31 */     if ("IndividualLCYService.doQueryIndividualFXSEQuota".equals(serviceMethod))
/*    */     {
/*    */ 
/* 34 */       retMsg = RspXMLGenerator.buildQueryIndividualFXSEQuotaXML(head);
/* 35 */     } else if ("InfoQueryService.doQuerySafeExRate".equals(serviceMethod))
/*    */     {
/* 37 */       retMsg = RspXMLGenerator.buildQuerySafeExRateXML(head);
/* 38 */     } else if ("InfoQueryService.doMakeUpSignStatus".equals(serviceMethod))
/*    */     {
/* 40 */       retMsg = RspXMLGenerator.buildMakeUpSignStatusXML(head);
/* 41 */     } else if ("IndividualFCYService.doMakeUpIndividualTradeFXSAInfo".equals(serviceMethod))
/*    */     {
/* 43 */       retMsg = RspXMLGenerator.buildMakeUpIndividualTradeFXSAInfoXML(head);
/* 44 */     } else if ("IndividualFCYService.doMakeUpOtherIndividualFXSAInfo".equals(serviceMethod))
/*    */     {
/* 46 */       retMsg = RspXMLGenerator.buildMakeUpOtherIndividualFXSAInfoXML(head);
/* 47 */     } else if ("IndividualFCYService.doDeleteIndividualFXSAInfo".equals(serviceMethod))
/*    */     {
/* 49 */       retMsg = RspXMLGenerator.buildDeleteIndividualFXSAInfoXML(head);
/* 50 */     } else if ("IndividualFCYService.doModifyIndividualFXSAInfo".equals(serviceMethod))
/*    */     {
/* 52 */       retMsg = RspXMLGenerator.buildModifyIndividualFXSAInfoXML(head);
/* 53 */     } else if ("IndividualFCYService.doQueryIndividualFXSAInfo".equals(serviceMethod))
/*    */     {
/* 55 */       retMsg = RspXMLGenerator.buildQueryIndividualFXSAInfoXML(head);
/* 56 */     } else if ("IndividualLCYService.doAddLimitedIndividualFXSEInfo".equals(serviceMethod))
/*    */     {
/* 58 */       retMsg = RspXMLGenerator.buildAddLimitedIndividualFXSEInfoXML(head);
/* 59 */     } else if ("IndividualLCYService.doAddUnLimitedIndividualFXSEInfo".equals(serviceMethod))
/*    */     {
/* 61 */       retMsg = RspXMLGenerator.buildAddUnLimitedIndividualFXSEInfoXML(head);
/* 62 */     } else if ("IndividualLCYService.doQueryIndividualFXSEInfo".equals(serviceMethod))
/*    */     {
/* 64 */       retMsg = RspXMLGenerator.buildQueryIndividualFXSEInfoXML(head);
/* 65 */     } else if ("IndividualLCYService.doModifyIndividualFXSEInfo".equals(serviceMethod))
/*    */     {
/* 67 */       retMsg = RspXMLGenerator.buildModifyIndividualFXSEInfoXML(head);
/* 68 */     } else if ("IndividualLCYService.doDeleteIndividualFXSEInfo".equals(serviceMethod))
/*    */     {
/* 70 */       retMsg = RspXMLGenerator.buildDeleteIndividualFXSEInfoXML(head);
/* 71 */     } else if ("IndividualLCYService.doMakeUpOtherIndividualFXSEInfo".equals(serviceMethod))
/*    */     {
/* 73 */       retMsg = RspXMLGenerator.buildMakeUpOtherIndividualFXSEInfoXML(head);
/* 74 */     } else if ("IndividualLCYService.doMakeUpIndividualTradeFXSEInfo".equals(serviceMethod))
/*    */     {
/* 76 */       retMsg = RspXMLGenerator.buildMakeUpIndividualTradeFXSEInfoXML(head);
/* 77 */     } else if ("IndividualFCYService.doQueryIndividualFXSAQuota".equals(serviceMethod))
/*    */     {
/* 79 */       retMsg = RspXMLGenerator.buildQueryIndividualFXSAQuotaXML(head);
/* 80 */     } else if ("IndividualFCYService.doAddLimitedIndividualFXSAInfo".equals(serviceMethod))
/*    */     {
/* 82 */       retMsg = RspXMLGenerator.buildAddLimitedIndividualFXSAInfoXML(head);
/* 83 */     } else if ("IndividualFCYService.doAddUnLimitedIndividualFXSAInfo".equals(serviceMethod))
/*    */     {
/* 85 */       retMsg = RspXMLGenerator.buildAddUnLimitedIndividualFXSAInfoXML(head);
/*    */     }
/*    */     
/* 88 */     return retMsg;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\support\MQEmulator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */