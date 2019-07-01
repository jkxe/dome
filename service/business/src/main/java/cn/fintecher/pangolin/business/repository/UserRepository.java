package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.QUser;
import cn.fintecher.pangolin.entity.User;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 10:45 2017/7/17
 */
public interface UserRepository extends QueryDslPredicateExecutor<User>, JpaRepository<User, String>, QuerydslBinderCustomizer<QUser> {
    @Override
    default void customize(final QuerydslBindings bindings, final QUser root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(value).concat("%")));
    }

    User findByUserName(String username);

    /**
     * 获取所有催收员
     *
     * @return
     */
    @Query(value = "SELECT a.id AS userId,a.company_code as companyCode,a.type,a.sex,a.status,a.age,a.work_age,a.nation,\n" +
            "count(b.id) AS count,sum(b.overdue_amount) AS sum,count(CASE WHEN (b.collection_status = 24) THEN b.id END) AS closedCount,sum(CASE WHEN (b.collection_status = 24) THEN b.overdue_amount ELSE 0 END) AS closedSum,\n" +
            "count(CASE WHEN (b.promise_time IS NOT NULL) THEN b.id END) AS promiseCount,sum(CASE WHEN (b.promise_time IS NOT NULL) THEN b.promise_amt ELSE 0 END) AS promiseSum,\n" +
            "count(case when (c.collection_type = 15 and c.collection_feedback in (90,91,92,93)) then c.id end) as phoneCount,count(case when (c.collection_type = 16) then c.id end) as visitCount\n" +
            "FROM(SELECT id,company_code,type,sex,status,age,work_age,nation FROM `user` where status = 0) a\n" +
            "LEFT JOIN (SELECT id,overdue_amount,promise_time,real_pay_amount,promise_amt,current_collector,lately_collector,assist_collector,collection_status FROM case_info\n" +
            ") b ON a.id = b.current_collector or a.id = b.lately_collector or a.id = b.assist_collector \n" +
            "LEFT JOIN (SELECT id,case_id,collection_type,collection_feedback FROM case_followup_record) c ON b.id = c.case_id where a.company_code =:companyCode GROUP BY userId", nativeQuery = true)
    Object[] findAllUser(@Param("companyCode")String companyCode);

    /**
     * 根据角色查询用户信息
     * @param roleId
     * @return
     */
    @Query(value = "SELECT a.id,a.user_name,a.real_name,a.dept_id from `user` a,user_role b where a.id=b.user_id and b.role_id=:roleId",nativeQuery = true)
    Object[] getUserInfoByRoleId(@Param("roleId") String roleId);
}
