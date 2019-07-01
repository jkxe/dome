package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.FlowApproval;
import cn.fintecher.pangolin.entity.QFlowApproval;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlowApprovalRepository extends QueryDslPredicateExecutor<FlowApproval>, JpaRepository<FlowApproval, String>, QuerydslBinderCustomizer<QFlowApproval> {


    @Override
    default void customize(final QuerydslBindings bindings, final QFlowApproval root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }

    /**
     * 根据节点id查询对应的审批信息
     * @param nodeId
     * @return
     */
    @Query(value = "select id,node_id,case_number,process_state from flow_approval where process_state=0 and node_id=:nodeId",nativeQuery = true)
    List<FlowApproval> getFlowApprovalBynodeId(@Param("nodeId") String nodeId);

    /**
     * 根据案件编号和节点id查询
     * @param caseNumber
     * @param nodeId
     * @return
     */
    List<FlowApproval> getAllByCaseNumberAndNodeId(String caseNumber, String nodeId);


    /**
     * 查询审批流程中是否有某个任务正在审批的案件
     * @param taskId
     * @return
     */
    @Query(value = "select count(*) from flow_node a,flow_approval b where a.id=b.node_id and b.process_state=0 and a.task_id=:taskId",nativeQuery = true)
    Object[] getCountByTaskId(@Param("taskId") String taskId);

}