package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.PersonalContact;
import cn.fintecher.pangolin.entity.QPersonalContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

/**
 * @author : xiaqun
 * @Description :
 * @Date : 15:59 2017/7/20
 */

public interface PersonalContactRepository extends QueryDslPredicateExecutor<PersonalContact>, JpaRepository<PersonalContact, String>, QuerydslBinderCustomizer<QPersonalContact> {
    @Override
    default void customize(final QuerydslBindings bindings, final QPersonalContact root) {

    }

    @Query(value = "select id, personal_id, relation,phone,relation_user_id, operator_time, `name` from personal_contact",nativeQuery = true)
    List<Object[]> findByAllKey();
}
