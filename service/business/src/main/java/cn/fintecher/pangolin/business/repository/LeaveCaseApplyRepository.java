package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.LeaveCaseApply;
import cn.fintecher.pangolin.entity.QLeaveCaseApply;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveCaseApplyRepository extends QueryDslPredicateExecutor<LeaveCaseApply>,JpaRepository<LeaveCaseApply,String>,QuerydslBinderCustomizer<QLeaveCaseApply> {

    @Override
    default void customize(final QuerydslBindings bindings, final QLeaveCaseApply root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }

    /**
     * 根据案件id和审批状态(0、代审批，1、审批中，2、同意，3、拒绝)查询正在审批的结果
     * @return
     */
    @Query(value ="select id,approval_id,case_id,approval_status,leave_reason,apply_user,apply_time,left_date from leave_case_apply where case_id=:caseId and approval_status in(0,1)",
            nativeQuery = true)
    List<LeaveCaseApply> queryAllByCaseId(@Param("caseId") String caseId);
}
