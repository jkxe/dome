package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CaseInfoLearning;
import cn.fintecher.pangolin.entity.QCaseInfoLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-09-26-11:49
 */
public interface CaseInfoLearningRepository extends QueryDslPredicateExecutor<CaseInfoLearning>, JpaRepository<CaseInfoLearning, String>, QuerydslBinderCustomizer<QCaseInfoLearning> {
    @Override
    default void customize(final QuerydslBindings bindings, final QCaseInfoLearning root) {
    }
}
