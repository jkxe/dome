package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.business.model.PersonalAddressDecideModel;
import cn.fintecher.pangolin.entity.PersonalAddress;
import cn.fintecher.pangolin.entity.QPersonalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

/**
 * @author : gaobeibei
 * @Description :
 * @Date : 10:28 2017/7/28
 */
public interface PersonalAddressRepository extends QueryDslPredicateExecutor<PersonalAddress>, JpaRepository<PersonalAddress, String>, QuerydslBinderCustomizer<QPersonalAddress> {
    @Override
    default void customize(final QuerydslBindings bindings, final QPersonalAddress root) {
    }
    @Query(nativeQuery = true, value = "select id, personal_id, relation, `type`, operator_time from personal_address")
    List<Object[]> findAllKeyAndField();
}
