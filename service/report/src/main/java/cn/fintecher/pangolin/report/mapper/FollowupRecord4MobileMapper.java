package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.report.entity.CaseFollowupAttachment;
import cn.fintecher.pangolin.report.model.mobile.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.report.mapper
 * @ClassName: cn.fintecher.pangolin.report.mapper.FollowupRecord4MobileMapper
 * @date 2018年10月11日 15:00
 */
public interface FollowupRecord4MobileMapper {

    /**
     * @Description:  APP端 添加案件协催记录，除过附件信息
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/9 0009 下午 4:55
     */
    void addFollowupRecord4Mobile(CaseFollowupRecordParam caseFollowupRecordParam);

    /**
     * @Description:  修改用户信息，personal_contact 表
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/10 0010 下午 6:23
     */
    void repairPersonContactInfo(RepairPersonInfoParams repairPersonInfoParams);

    /**
     * @Description:  修改用户地址信息，personal_address 表
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/10 0010 下午 6:23
     */
    void repairPersonAddressInfo(RepairPersonInfoParams repairPersonInfoParams);


    /**
     * @Description:  获取外访人员首页汇总结果
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/11 0011 下午 5:59
     */
    List<String> getIndexResult4Mobile(@Param("personalId") String personalId);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/24 0024 下午 10:50
     * @Description: 修改 case_assist 表 协催案件状态 为催收中
     */
    void updateAssistStatusIsCollecting(@Param("caseId") String caseId);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/24 0024 下午 10:50
     * @Description: 修改 case_info表 协催案件状态 为催收中
     */
    void updateCollectionStatusIsCollecting(Map<String, String> param);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/25 0025 下午 5:50
     * @Description: 根据案件id查询案件编号
     */
    String getCaseNumberByCaseId(@Param("caseId") String caseId);


    /**
     * 根据部门code获取部门下的所有用户
     * @param departCode
     * @return List<String>
     */
    List<String> getAllUsersByDepartCode4Mobile(@Param("departCode") String departCode);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/27 0027 下午 9:29
     * @Description: 修改经纬度
     */
    void updateLongitudeAndLatitude(LongitudeAndLatitudeModel param);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/28 0028 下午 12:43
     * @Description: 根据部门id查询该部门及子部门下的所有用户id
     */
    List<String> getAllUserIdByDeptCode(@Param("departCode") String departCode);

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/26 0026 上午 11:47
     * @Description: 查询外访案件
     */
    List<VisitModel4Mobile> getVisitCaseInfo(Map<String, String> queryparam);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/26 0026 上午 11:47
     * @Description: 查询协催案件
     */
    List<AssistModel4Mobile> getAssistCaseInfo(Map<String, String> queryparam);

    /**
     * @Description:  APP端 添加案件协催记录中的附件信息
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/9 0009 下午 4:55
     */
    void addCaseFollowupAttachment4Mobile(CaseFollowupAttachment caseFollowupAttachment);

}
