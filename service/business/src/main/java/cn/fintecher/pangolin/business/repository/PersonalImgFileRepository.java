package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.PersonalImgFile;
import cn.fintecher.pangolin.entity.QPersonalImgFile;
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

public interface PersonalImgFileRepository extends QueryDslPredicateExecutor<PersonalImgFile>, JpaRepository<PersonalImgFile, String>, QuerydslBinderCustomizer<QPersonalImgFile> {
    @Override
    default void customize(final QuerydslBindings bindings, final QPersonalImgFile root) {


    }
    @Query(value = "select * from hy_personal_img_files where customer_id = :customerId",nativeQuery = true)
    List<PersonalImgFile> findByCustomerId(@Param("customerId") String customerId);

    @Query(value = "select resource_id, update_time from hy_personal_img_files",nativeQuery = true)
    List<Object[]> findByAllKey();
}
