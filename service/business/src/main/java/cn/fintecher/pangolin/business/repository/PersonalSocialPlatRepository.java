package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.PersonalSocialPlat;
import cn.fintecher.pangolin.entity.QPersonalSocialPlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : xiaqun
 * @Description :
 * @Date : 10:28 2017/7/26
 */

public interface PersonalSocialPlatRepository extends QueryDslPredicateExecutor<PersonalSocialPlat>, JpaRepository<PersonalSocialPlat, String>, QuerydslBinderCustomizer<QPersonalSocialPlat> {
    @Override
    default void customize(final QuerydslBindings bindings, final QPersonalSocialPlat root) {


    }

    @Query(value = "select * from hy_personal_social_plats where customer_id = :customerId", nativeQuery = true)
    List<PersonalSocialPlat> findByCustomerId(@Param("customerId")String customerId);

    @Query(nativeQuery = true, value = "select id,update_time from hy_personal_social_plats")
    List<Object[]> findAllKeyAndUpdateTime();
}
