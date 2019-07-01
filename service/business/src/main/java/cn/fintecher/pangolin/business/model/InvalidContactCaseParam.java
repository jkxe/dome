package cn.fintecher.pangolin.business.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by duchao on 2018/7/7.
 */

@Data
public class InvalidContactCaseParam {
    /**
     * 案件编号
     */
    private String caseNumber;
    /**
     * 客户姓名
     */
    private String personalName;
    /**
     * 手机号
     */
    private String mobileNo;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 合同编号
     */
    private String contractNumber;
}
