package cn.fintecher.pangolin.business.repository;


import cn.fintecher.pangolin.entity.CaseRepaymentRecord;
import cn.fintecher.pangolin.entity.QCaseRepaymentRecord;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CaseRepaymentRecordRepository extends QueryDslPredicateExecutor<CaseRepaymentRecord>,JpaRepository<CaseRepaymentRecord,String>,QuerydslBinderCustomizer<QCaseRepaymentRecord> {

    @Override
    default void customize(final QuerydslBindings bindings, final QCaseRepaymentRecord root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }
}
