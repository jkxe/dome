package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.FlowTask;
import cn.fintecher.pangolin.entity.QFlowTask;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlowTaskRepository extends QueryDslPredicateExecutor<FlowTask>, JpaRepository<FlowTask, String>, QuerydslBinderCustomizer<QFlowTask> {


    @Override
    default void customize(final QuerydslBindings bindings, final QFlowTask root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        bindings.bind(root.taskName).first((path, value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }

    /**
     * 根据任务名称查询信息
     * @param taskName
     * @return
     */
    @Query(value = "select * from flow_task where task_name LIKE %:taskName%",nativeQuery = true)
    List<FlowTask> findByTaskNameLike(@Param("taskName") String taskName);
    /**
     * 查询任务节点展示列表信息
     * @return
     */
    @Query(value = "select a.id as taskId,a.task_name as taskName,b.id as nodeId,b.role_id as roleId,b.step,c.`name` as roleName " +
            "from flow_task a,flow_node b,role c where a.id=b.task_id and b.role_id=c.id",nativeQuery = true)
    Object[] getTaskInfoList();

    /**
     * 查询任务节点展示列表信息
     * @return
     */
    @Query(value = "select a.id as taskId,a.task_name as taskName,b.id as nodeId,b.role_id as roleId,b.step,c.`name` as roleName " +
            "from flow_task a,flow_node b,role c where a.id=b.task_id and b.role_id=c.id and a.id=:taskId order by step asc",nativeQuery = true)
    Object[] getTaskInfoListByTaskId(@Param("taskId") String taskId);

    /**
     * 获取审批链类型
     * @param nodeId
     * @return
     */
    @Query(value = "SELECT task_type from flow_node a LEFT JOIN flow_task b ON b.id = a.task_id WHERE a.id =:nodeId",nativeQuery = true)
    Integer getFlowTaskTypeByNodeId(@Param("nodeId") String nodeId);
}
