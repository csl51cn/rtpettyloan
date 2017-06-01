package com.global.fems.business.dao.impl;


import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.domain.CoCustomerCycleNode;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.exception.BaseException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *贷款合同信息管理Dao接口实现类
 */
@Repository
public class ContractInfoDaoImpl  extends BaseDaoSupport implements ContractInfoDao{

    /**
     * 保存或更新合同信息
     * @param contractInfoCycleNode
     * @throws DAOException
     */
    public void saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws DAOException {
        super.saveOrUpdate(contractInfoCycleNode);
    }

    /**
     * 根据合同编号查询合同信息集合
     * @param contractNo
     * @return
     * @throws DAOException
     */
    public List<ContractInfoCycleNode> findContractListByContractNo(String contractNo) throws DAOException {
        String  sql = "select * from DC_CONTRACT_INFO where contract_no = ?";
        List<ContractInfoCycleNode> contractInfoCycleNodeList = (List<ContractInfoCycleNode>) super.findForListBySql(sql, new Object[]{contractNo}, ContractInfoCycleNode.class);
        return contractInfoCycleNodeList;
    }

    /**
     * 根据合同编号联合查询合同信息
     * @param contractNo
     * @return
     * @throws DAOException
     */
    public ContractInfoCycleNode findContractByContractNoFromPettyLoanContract(String contractNo) throws DAOException {
        String  sql = "SELECT " +
                "b.Date_Id AS date_id, " +
                "a.contractno AS contract_no, " +
                "a.loancate AS loan_cate, " +
                "a.customername AS customer_name, " +
                "a.customertype AS customer_type, " +
                "a.certificatetype AS certificate_type, " +
                "a.certificateno AS certificate_no, " +
                "ISNULL( " +
                "CASE c.授信主体类型 " +
                "WHEN 1 THEN " +
                "'410001' " +
                "WHEN 2 THEN " +
                "'410004' " +
                "END, " +
                "'' " +
                ") AS loan_object, " +
                "ISNULL( " +
                "CASE c.产品类型名称 " +
                "WHEN '商易贷' THEN " +
                "'280002' " +
                "WHEN '优易贷' THEN " +
                "'280002' " +
                "WHEN '展业贷' THEN " +
                "'280002' " +
                "ELSE " +
                "'280003' " +
                "END, " +
                "'' " +
                ") AS loan_object_size, " +
                "a.contractsigndate AS contract_sign_date, " +
                "b.放款日期 AS contract_begin_date, " +
                "b.到期日期 AS contract_end_date, " +
                "b.放款金额 AS contract_amount, " +
                "b.余额 AS outstanding, " +
                "a.intrate AS int_rate, " +
                "ISNULL( " +
                "CASE c.产品类型名称 " +
                "WHEN '质房贷' THEN " +
                "a.intrate * 1.5 " +
                "ELSE " +
                "a.intrate * 2 " +
                "END, " +
                "null " +
                ") AS pri_plty_rate, " +
                "c.共同借款人详细 AS co_customer_name1 " +
                "FROM " +
                "DC_PETTY_LOAN_CONTRACT a " +
                "LEFT JOIN 已放款客户表 b ON a.contractno = b.合同编号 " +
                "LEFT JOIN Data_WorkInfo c ON a.contractno = c.合同编号 " +
                "WHERE " +
                "contractno = ?";

        return  super.findForObjectBySql(sql, new Object[]{contractNo}, ContractInfoCycleNode.class);
    }

    /**
     * 根据身份证号查询共借人信息
     * @param code
     * @return
     * @throws DAOException
     */
    public CoCustomerCycleNode findCoCustomerInfoFromDataMemberInfo(String code) throws DAOException {
        String  sql = "SELECT 客户名称 as coCustomerName,手机 AS coTelephone FROM Data_MemberInfo where 身份证号码 = ?";
        return  super.findForObjectBySql(sql,new Object[]{code},CoCustomerCycleNode.class);
    }

    /**
     * 根据组织机构代码查询共借人信息
     * @param code
     * @return
     * @throws DAOException
     */
    public CoCustomerCycleNode findCoCustomerInfoFromDataCompanyInfo(String code) throws DAOException {
        String sql = "SELECT 客户名称 as coCustomerName,公司联系电话 AS coTelephone FROM Data_MemberInfo where 组织机构代码证号 = ?";
        return  super.findForObjectBySql(sql,new Object[]{code},CoCustomerCycleNode.class);
    }

    /**
     * 批量保存合同信息
     * @param list
     * @throws DAOException
     */
    public void batchSavePettyLoanContract(List<ContractInfoCycleNode> list) throws DAOException {
        super.batchInsert(list);
    }


    /**
     * 从DC_CONTRACT_INFO中根据合同号查询合同信息
     * @param contractNo
     * @return
     * @throws DAOException
     */
    public ContractInfoCycleNode findContractByContractNoFromDCContractInfo(String contractNo) throws DAOException {
        String sql = "select * from DC_CONTRACT_INFO where contract_no = ? ";
        return super.findForObjectBySql(sql,new Object[]{contractNo},ContractInfoCycleNode.class);

    }

    /**
     * 保存合同信息
     * @param contractInfoCycleNode
     * @throws BaseException
     */
//    public void save(ContractInfoCycleNode contractInfoCycleNode) throws DAOException {
//        StringBuilder sql = new StringBuilder("INSERT INTO DC_CONTRACT_INFO (" +
//                "report_type," +
//                "org_code," +
//                "contract_no," +
//                "loan_cate," +
//                "contract_name," +
//                "customer_type," +
//                "customer_name," +
//                "certificate_type," +
//                "certificate_no," +
//                "linkman," +
//                "telephone," +
//                "loan_object," +
//                "loan_object_size," +
//                "contract_sign_date," +
//                "contract_begin_date," +
//                "contract_end_date," +
//                "contract_amount," +
//                "outstanding," +
//                "guar_type," +
//                "ccy," +
//                "int_rate," +
//                "pri_plty_rate," +
//                "contract_status," +
//                "relation_manager," +
//                "dispute_scheme," +
//                "remark," +
//                "is_real_quota_loan"
//               );
//        ArrayList args = new ArrayList();
//        args.add(contractInfoCycleNode.getReportType());
//        args.add(contractInfoCycleNode.getOrgCode());
//        args.add(contractInfoCycleNode.getContractNo());
//        args.add(contractInfoCycleNode.getLoanCate());
//        args.add(contractInfoCycleNode.getContractName());
//        args.add(contractInfoCycleNode.getCustomerType());
//        args.add(contractInfoCycleNode.getCustomerName());
//        args.add(contractInfoCycleNode.getCertificateType());
//        args.add(contractInfoCycleNode.getCertificateNo());
//        args.add(contractInfoCycleNode.getLinkman());
//        args.add(contractInfoCycleNode.getTelephone());
//        args.add(contractInfoCycleNode.getLoanObject());
//        args.add(contractInfoCycleNode.getLoanObjectSize());
//        args.add(contractInfoCycleNode.getContractSignDate());
//        args.add(contractInfoCycleNode.getContractBeginDate());
//        args.add(contractInfoCycleNode.getContractEndDate());
//        args.add(contractInfoCycleNode.getContractAmount());
//        args.add(contractInfoCycleNode.getOutstanding());
//        args.add(contractInfoCycleNode.getGuarType());
//        args.add(contractInfoCycleNode.getCcy());
//        args.add(contractInfoCycleNode.getIntRate());
//        args.add(contractInfoCycleNode.getPriPltyRate());
//        args.add(contractInfoCycleNode.getContractStatus());
//        args.add(contractInfoCycleNode.getRelationManager());
//        args.add(contractInfoCycleNode.getDisputeScheme());
//        args.add(contractInfoCycleNode.getRemark());
//        String isRealQuotaLoan = contractInfoCycleNode.getIsRealQuotaLoan();
//        args.add(isRealQuotaLoan);
//        if (isRealQuotaLoan == "740001" ){//是额度项下贷款,保存授信额度协议编号
//            sql.append(", real_quota_no");
//            args.add(contractInfoCycleNode.getRealQuotaNo());
//        }
//
//        //委托贷款相关信息
//        if(StringUtils.isNotBlank(contractInfoCycleNode.getConCustomerType())){
//            sql.append(",con_customer_type,con_customer_name,con_certificate_type,con_certificate_no,con_jurisitc,con_telephone,con_locus,con_postalcode,con_fincal_org,con_account_no,ass_customer_name,ass_juristic,ass_telephone,ass_locus,ass_postalcode,con_fee");
//            args.add(contractInfoCycleNode.getConCustomerType());
//            args.add(contractInfoCycleNode.getConCustomerName());
//            args.add(contractInfoCycleNode.getConCertificateType());
//            args.add(contractInfoCycleNode.getConCertificateNo());
//            args.add(contractInfoCycleNode.getConJurisitc());
//            args.add(contractInfoCycleNode.getConTelephone());
//            args.add(contractInfoCycleNode.getConLocus());
//            args.add(contractInfoCycleNode.getConPostalCode());
//            args.add(contractInfoCycleNode.getConFincalOrg());
//            args.add(contractInfoCycleNode.getConAccountNo());
//            args.add(contractInfoCycleNode.getAssCustomerName());
//            args.add(contractInfoCycleNode.getAssJuristic());
//            args.add(contractInfoCycleNode.getAssTelephone());
//            args.add(contractInfoCycleNode.getAssLocus());
//            args.add(contractInfoCycleNode.getAssPostalCode());
//            args.add(contractInfoCycleNode.getConFee());
//        }
//
//        //共借人相关信息
//        List<CoCustomerCycleNode> coCustomerInfo = contractInfoCycleNode.getCoCustomerInfo();
//        if(coCustomerInfo != null && coCustomerInfo.size() > 0){
//            for (int i = 0; i < coCustomerInfo.size();i++){
//                CoCustomerCycleNode coCustomerCycleNode = coCustomerInfo.get(i);
//                int temp = i + 1;
//                sql.append(",co_customer_type"+temp +",co_customer_name"+temp +",co_certificate_type1"+temp +",co_certificate_no"+temp
//                                +",co_certificate_no"+temp +",co_linkman"+temp +",co_telephone"+temp);
//                args.add(coCustomerCycleNode.getCoCustomerType());
//                args.add(coCustomerCycleNode.getCoCustomerName());
//                args.add(coCustomerCycleNode.getCoCertificateType());
//                args.add(coCustomerCycleNode.getCoCertificateNo());
//                args.add(coCustomerCycleNode.getCoLinkman());
//                args.add(coCustomerCycleNode.getCoTelephone());
//            }
//        }
//        sql.append(") VALUES(");
//        for (int i = 0; i < args.size();i++){
//
//        }
//        super.insertBySql(sql.toString(),args.toArray());
//
//    }



}
