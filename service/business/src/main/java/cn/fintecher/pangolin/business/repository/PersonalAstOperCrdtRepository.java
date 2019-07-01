package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.business.model.PersonalAstOperCrdtModel;
import cn.fintecher.pangolin.entity.PersonalAstOperCrdt;
import cn.fintecher.pangolin.entity.QPersonalAstOperCrdt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : hanwannan
 * @Description :
 * @Date : 10:28 2017/7/26
 */

public interface PersonalAstOperCrdtRepository extends QueryDslPredicateExecutor<PersonalAstOperCrdt>, JpaRepository<PersonalAstOperCrdt, String>, QuerydslBinderCustomizer<QPersonalAstOperCrdt> {
    @Override
    default void customize(final QuerydslBindings bindings, final QPersonalAstOperCrdt root) {


    }

    @Query(value = "select * from hy_personal_ast_oper_crdt where customer_id = :customerId",nativeQuery = true)
    List<PersonalAstOperCrdt> findListByCustomerId(@Param("customerId") String customerId);

    @Query(value = "select resource_id, update_time from hy_personal_ast_oper_crdt",nativeQuery = true)
    List<Object[]> findByAllKey();
}
