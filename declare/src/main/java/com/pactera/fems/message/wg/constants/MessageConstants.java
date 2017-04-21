package com.pactera.fems.message.wg.constants;

public class MessageConstants {
    public static final String MSG_MIX_DEPEND = "证件类型、证件号码、国家/地区三项中如输入一项，其他两项都不能为空。";
    public static final String MSG_ALL_NULL = "查询条件不能全部为空。";
    public static final String MSG_CANCEL_REMARK = "撤销原因为“其他”时, 撤销说明:CANCEL_REMARK 不允许为空。";
    public static final String MSG_MODIFY_REMARK = "修改原因为“其他”时, 修改说明:MODIFY_REMARK 不允许为空。";
    public static final String MSG_BIZ_TYPE_CODE = "业务类型:BIZ_TYPE_CODE 只能是“占用额度的购汇”。";
    public static final String MSG_ADD_IDCODE = "当证件类型为“中国护照”时,补充证件号码:ADD_IDCODE 不允许为空，需填写境外永久居留证号码；补充证件号码:ADD_IDCODE 只允许输入大写字母和数字、中文。";
    public static final String MSG_PAY_ORG_CODE = "当业务类型为“通过支付机构的购汇”时,支付机构组织机构代码:PAY_ORG_CODE 不允许为空。";
    public static final String MSG_AGENT_CORP_NAME = "业务类型为“个人贸易购汇”时,代理企业名称:AGENT_CORP_NAME 可以输入。";
    public static final String MSG_AGENT_CORP_CODE = "业务类型为“个人贸易购汇”时,代理企业组织机构代码:AGENT_CORP_CODE 可以输入;只允许输入数字、字母。";
    public static final String MSG_INDIV_ORG_CODE = "业务类型为“个人贸易购汇”时,个体工商户组织机构代码:INDIV_ORG_CODE 可以输入;只允许输入数字、字母。";
    public static final String MSG_INDIV_ORG_NAME = "业务类型为“个人贸易购汇”时,个体工商户名称:INDIV_ORG_NAME 可以输入。";
    public static final String MSG_CAPITALNO = "业务类型为“资本项目购汇”时, 外汇局批件号/备案表号/业务编号:CAPITALNO 不允许为空。";
    public static final String MSG_TXCCY = "币种:TXCCY 不能为“人民币”。";
    public static final String MSG_PURFX_AMT = "购汇金额:PURFX_AMT 大于0。";
    public static final String MSG_PURFX_AMT_ONE = "购汇提钞金额PURFX_CASH_AMT + 汇出资金（包括外汇票据）金额FCY_REMIT_AMT + 存入个人外汇账户金额FCY_ACCT_AMT + 旅行支票金额TCHK_AMT 必须等于 购汇金额PURFX_AMT";
    public static final String MSG_PURFX_CASH_AMT = "购汇提钞金额: PURFX_CASH_AMT 大于0。";
    public static final String MSG_FCY_REMIT_AMT_ONE = "汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 大于等于0。";
    public static final String MSG_FCY_REMIT_AMT = "汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 大于等于0。当业务类型为“通过支付机构的购汇”时，汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 等于购汇金额。";
    public static final String MSG_LCY_ACCT_NO_ONE = "“存入个人外汇账户金额”大于零时，个人外汇账户账号:LCY_ACCT_NO 不允许为空。 ";
    public static final String MSG_LCY_ACCT_NO = "当“存入个人外汇账户金额”大于零或者业务类型为“个人贸易购汇”时，个人外汇账户账号:LCY_ACCT_NO 不允许为空。 ";
    public static final String MSG_FCY_ACCT_AMT = "存入个人外汇账户金额:FCY_ACCT_AMT 大于0。";
    public static final String MSG_TCHK_AMT = "旅行支票金额:TCHK_AMT 大于0。";
    public static final String MSG_BIZ_TX_CHNL_CODE = "业务类型为“通过支付机构的购汇”时，业务办理渠道BIZ_TX_CHNL_CODE 必须为“32支付机构”";
    public static final String MSG_BIZ_TX_TIME = "业务实际办理日期:BIZ_TX_TIME 不能大于当前日期。";
    public static final String MSG_REIN_REMARK = "当补录原因为“其他”时, 补录说明:REIN_REMARK 不允许为空 ";
    public static final String MSG_REMARK = "单笔提钞超过等值10000美元，请在备注栏:REMARK 注明提钞用途 ";
    public static final String MSG_REMARK_ONE = "境外个人购汇金额SALEFX_AMT单笔超过500美元，备注栏:REMARK 不能为空，需要在备注栏填写凭证信息";
    public static final String MSG_REMARK_TWO = "备注栏:REMARK 不能为空";
}