package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.MissingConnectionInfo;
import cn.fintecher.pangolin.entity.QMissingConnectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * Created by duchao on 2018/6/19.
 */
public interface MissingConnectionInfoRepository extends QueryDslPredicateExecutor<MissingConnectionInfo>, JpaRepository<MissingConnectionInfo, String>, QuerydslBinderCustomizer<QMissingConnectionInfo> {
    @Override
    default void customize(final QuerydslBindings bindings, final QMissingConnectionInfo root) {
    }

}
