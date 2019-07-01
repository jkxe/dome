package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.entity.CaseInfoDistributed;
import cn.fintecher.pangolin.entity.PersonalContact;
import cn.fintecher.pangolin.report.entity.response.CaseInfoDistributedListResponse;
import cn.fintecher.pangolin.report.model.CaseInfoDistributeQueryParams;
import cn.fintecher.pangolin.report.model.CaseInfoExcelModel;

import java.util.List;

/**
 * 待分配案件
 * Created by sunyanping on 2017/11/6.
 */

public interface CaseInfoDistributeMapper {

    /**
     * 查询待分配案件
     * @param params
     * @return
     */
    List<CaseInfoDistributedListResponse> findCaseInfoDistribute(CaseInfoDistributeQueryParams params);

    /**
     * 查询待分配案件详情
     */
    CaseInfoExcelModel getCaseInfoDistributedDetails(String id);

    /**
     * 查询联系人信息
     */
    List<PersonalContact> getPersonalContact(String personalId);
}
