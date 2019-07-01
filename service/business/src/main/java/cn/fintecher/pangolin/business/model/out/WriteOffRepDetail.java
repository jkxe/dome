package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class WriteOffRepDetail {

    private String 	vqInAccountDate;//	入账日期
    private String 	vqCustId;//	客户号
    private String 	vqInUnpaidPrincipal;//	录入未偿还本金
    private String 	vqInRemainPrin;//	录入剩余本金
    private String 	vqInUnpaidInterest;//	录入未偿还利息
    private String 	vqInVerifiNobillInt;//	录入未出账单利息
    private String 	vqInOtherInt;//	录入其他累计利息
    private String 	vqInPnltInt;//	录入罚息
    private String 	vqInLpc;//	录入滞纳金
    private String 	vqInMthFee;//	录入月服务费
    private String 	vqInOtherFee;//	录入其他管理费
    private String 	vqTerminationInd;//	核销结清标识(Y-已结清，N-未结清)
    private String 	vqTotal;//	还款总额
    private String 	vqUserField2;//	结清日期
    private String 	vqRequestDate;//	核销请求日期
}
