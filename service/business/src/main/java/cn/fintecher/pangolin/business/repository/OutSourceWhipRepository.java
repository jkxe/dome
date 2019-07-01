package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.OutSourceWhip;
import cn.fintecher.pangolin.entity.QOutSourceWhip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface OutSourceWhipRepository extends QueryDslPredicateExecutor<OutSourceWhip>, JpaRepository<OutSourceWhip, String>, QuerydslBinderCustomizer<QOutSourceWhip> {

    @Override
    default void customize(final QuerydslBindings bindings, final QOutSourceWhip root) {

    }
}
