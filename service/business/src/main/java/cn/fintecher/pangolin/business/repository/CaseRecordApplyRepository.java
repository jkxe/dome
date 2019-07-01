package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CaseRecordApply;
import cn.fintecher.pangolin.entity.QCaseRecordApply;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

public interface CaseRecordApplyRepository extends QueryDslPredicateExecutor<CaseRecordApply>, JpaRepository<CaseRecordApply, String>, QuerydslBinderCustomizer<QCaseRecordApply> {

    @Override
    default void customize(final QuerydslBindings bindings, final QCaseRecordApply root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        bindings.bind(root.caseId).first((path, value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }

    /**
     *
     * 根据案件id查询申请记录
     * @param caseId
     * @return
     */
    List<CaseRecordApply> findAllByCaseId(String caseId);
}
