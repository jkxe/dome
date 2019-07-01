package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.FlowNode;
import cn.fintecher.pangolin.entity.QFlowNode;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlowNodeRepository extends QueryDslPredicateExecutor<FlowNode>, JpaRepository<FlowNode, String>, QuerydslBinderCustomizer<QFlowNode> {


    @Override
    default void customize(final QuerydslBindings bindings, final QFlowNode root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        //根据角色查询节点信息
        bindings.bind(root.roleId).first((path, value) -> path.eq(StringUtils.trim(value)));
    }

    /**
     * 根据角色查询对应的节点信息
     * @param roleid
     * @return
     */
    List<FlowNode> getFlowNodeByRoleId(String roleid);

    /**
     * 根据角色和任务id查询对应的几点信息
     * @param roleid
     * @param taskId
     * @return
     */
    FlowNode getFlowNodeByRoleIdAndTaskId(String roleid, String taskId);


    /**
     * 根据任务和当前步数查询节点信息
     * @param taskId
     * @param step
     * @return
     */
    FlowNode getFlowNodeByTaskIdAndStep(String taskId, Integer step);

    /**
     * 根据任务id查询最后一个节点
     * @param taskId
     * @return
     */
    @Query(value = "select id,task_id,role_id,step from flow_node where task_id=:taskId and step in" +
            "(select MAX(step)from flow_node where task_id=:taskId)",nativeQuery = true)
    FlowNode getMaxFlowNodeByTaskId(@Param("taskId") String taskId);

    /**
     * 根据任务id获取对应的节点
     * @param taskId
     * @return
     */
    List<FlowNode> getAllByTaskId(String taskId);

    /**
     * 根据任务id查询第一个申请节点
     * @param taskId
     * @return
     */
    @Query(value = "select id,task_id,role_id,step from flow_node where task_id=:taskId and step in" +
            "(select MIN(step)from flow_node where task_id=:taskId)",nativeQuery = true)
    FlowNode getMinFlowNodeByTaskId(@Param("taskId") String taskId);


    /**
     * 根据任务id删除该任务节点信息
     * @param taskId
     */
    @Query(value = "delete from flow_node where task_id=:taskId",nativeQuery = true)
    void deleteFlowNodeByTaskId(@Param("taskId") String taskId);

}
