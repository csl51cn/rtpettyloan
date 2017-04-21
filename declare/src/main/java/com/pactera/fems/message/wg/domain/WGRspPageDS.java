/*    */ package com.pactera.fems.message.wg.domain;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGRspPageDS
/*    */   extends WGDS
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String PAGESIZE;
/*    */   private String PAGENUMBER;
/*    */   private String RECORDCOUNT;
/*    */   private List ROWSET;
/*    */   
/*    */   public List getROWSET()
/*    */   {
/* 21 */     return this.ROWSET;
/*    */   }
/*    */   
/*    */   public void setROWSET(List rOWSET) {
/* 25 */     this.ROWSET = rOWSET;
/*    */   }
/*    */   
/* 28 */   public String getPAGESIZE() { return this.PAGESIZE; }
/*    */   
/*    */   public void setPAGESIZE(String pAGESIZE)
/*    */   {
/* 32 */     this.PAGESIZE = pAGESIZE;
/*    */   }
/*    */   
/*    */   public String getPAGENUMBER() {
/* 36 */     return this.PAGENUMBER;
/*    */   }
/*    */   
/*    */   public void setPAGENUMBER(String pAGENUMBER) {
/* 40 */     this.PAGENUMBER = pAGENUMBER;
/*    */   }
/*    */   
/*    */   public String getRECORDCOUNT() {
/* 44 */     return this.RECORDCOUNT;
/*    */   }
/*    */   
/*    */   public void setRECORDCOUNT(String rECORDCOUNT) {
/* 48 */     this.RECORDCOUNT = rECORDCOUNT;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\WGRspPageDS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */