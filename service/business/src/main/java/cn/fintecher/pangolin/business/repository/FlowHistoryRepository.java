package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.FlowHistory;
import cn.fintecher.pangolin.entity.QFlowHistory;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlowHistoryRepository extends QueryDslPredicateExecutor<FlowHistory>, JpaRepository<FlowHistory, String>, QuerydslBinderCustomizer<QFlowHistory> {


    @Override
    default void customize(final QuerydslBindings bindings, final QFlowHistory root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }

    /**
     * 根据审批id和案件编号查询历史记录详情
     * @param approvalId
     * @param caseNumber
     * @return
     */
    List<FlowHistory> getFlowHistoriesByApprovalIdAndCaseNumber(String approvalId, String caseNumber);


    /**
     * 根据审批id和节点id查询历史记录
     * @param approvalId
     * @param nodeId
     * @return
     */
    List<FlowHistory> getFlowHistoriesByApprovalIdAndNodeId(String approvalId, String nodeId);

    @Modifying
    @Transactional
    @Query(value = "delete from flow_history where approval_id=:approvalId and case_number=:caseNumber",nativeQuery = true)
    int deleteByApprovalIdAndCaseNumber(@Param("approvalId") String approvalId, @Param("caseNumber") String caseNumber);

    @Query(value = "select * from flow_history where approval_id=:approvalId and case_number=:caseNumber and node_id=:nodeId and node_state=:nodeState",nativeQuery = true)
    List<FlowHistory> queryByapprovalIdCaseNumber(@Param("approvalId") String approvalId,
                                                  @Param("caseNumber") String caseNumber,
                                                  @Param("nodeId") String nodeId,
                                                  @Param("nodeState") String nodeState);



}
