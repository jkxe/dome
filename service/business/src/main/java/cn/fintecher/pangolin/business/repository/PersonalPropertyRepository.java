package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.PersonalProperty;
import cn.fintecher.pangolin.entity.QPersonalProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * @Author:  wangzhao
 * @Description
 * @Date  2018/6/15  17:24
 **/
public interface PersonalPropertyRepository extends QueryDslPredicateExecutor<PersonalProperty>, JpaRepository<PersonalProperty, String>, QuerydslBinderCustomizer<QPersonalProperty> {
    @Override
    default void customize(final QuerydslBindings bindings, final QPersonalProperty root) {

    }
}
