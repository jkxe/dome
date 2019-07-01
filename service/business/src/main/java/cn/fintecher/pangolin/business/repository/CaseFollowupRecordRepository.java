package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CaseFollowupRecord;
import cn.fintecher.pangolin.entity.QCaseFollowupRecord;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author : xiaqun
 * @Description :
 * @Date : 16:15 2017/7/19
 */

public interface CaseFollowupRecordRepository extends QueryDslPredicateExecutor<CaseFollowupRecord>, JpaRepository<CaseFollowupRecord, String>, QuerydslBinderCustomizer<QCaseFollowupRecord> {
    @Override
    default void customize(final QuerydslBindings bindings, final QCaseFollowupRecord root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        bindings.bind(root.collectionFeedback).first(SimpleExpression::eq); //催收反馈
        bindings.bind(root.type).first(SimpleExpression::eq); //跟进方式
        bindings.bind(root.source).first(SimpleExpression::eq); //跟进来源
        //跟进时间
        bindings.bind(root.followTime).all((path, value) -> {
            Iterator<? extends Date> it = value.iterator();
            Date firstDelegationDate = it.next();
            if (it.hasNext()) {
                Date secondDelegationDate = it.next();
                return path.between(firstDelegationDate, secondDelegationDate);
            } else {
                //大于等于
                return path.goe(firstDelegationDate);
            }
        });
        bindings.bind(root.operatorTime).all((DateTimePath<Date> path, Collection<? extends Date> value) -> { //跟进时间
            Iterator<? extends Date> it = value.iterator();
            Date operatorMinTime = it.next();
            if (it.hasNext()) {
                String date = ZWDateUtil.fomratterDate(it.next(), "yyyy-MM-dd");
                date = date + " 23:59:59";
                Date operatorMaxTime = null;
                try {
                    operatorMaxTime = ZWDateUtil.getUtilDate(date, "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return path.between(operatorMinTime, operatorMaxTime);
            } else {
                return path.goe(operatorMinTime);
            }
        });
    }

    /**
     * @Description 获得周跟催榜
     */
    @Query(value = "select count(distinct(c.case_id)) as rank,u.real_name,u.id,u.photo from " +
            "( " +
            "select operator,case_id from case_followup_record " +
            "where operator_time <= :endDate " +
            "and operator_time >= :startDate " +
            ") as c " +
            "inner join " +
            "( " +
            "select u.id,u.real_name,u.photo,u.user_name from user u,department d " +
            "where u.type = :type " +
            "and u.company_code =:companyCode " +
            "and u.dept_id = d.id " +
            "and d.code like concat(:deptCode,'%') " +
            ") as u " +
            "on u.user_name = c.operator " +
            "group by " +
            "u.id " +
            "order by " +
            "rank desc ", nativeQuery = true)
    List<Object[]> getFlowupCaseList(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("type") Integer type, @Param("companyCode") String companyCode, @Param("deptCode") String deptCode);

    /**
     * @Description 获得周催计榜
     */
    @Query(value = "select count(*) as rank,u.real_name,u.id,u.photo from " +
            "( " +
            "select operator from case_followup_record " +
            "where operator_time <= :endDate " +
            "and operator_time >= :startDate " +
            ") as c " +
            "inner join " +
            "( " +
            "select u.id,u.real_name,u.photo,u.user_name from user u,department d " +
            "where u.type = :type " +
            "and u.company_code =:companyCode " +
            "and u.dept_id = d.id " +
            "and d.code like concat(:deptCode,'%') " +
            ") as u " +
            "on u.user_name = c.operator " +
            "group by " +
            "u.id " +
            "order by " +
            "rank desc ", nativeQuery = true)
    List<Object[]> getCollectionList(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("type") Integer type, @Param("companyCode") String companyCode, @Param("deptCode") String deptCode);

    /**
     * @Description 获得指定用户催计数
     */
    @Query(value = "select count(*) from case_followup_record where operator = :name and type = :type and operator_time <= :endDate and operator_time >= :startDate", nativeQuery = true)
    Integer getCollectionNum(@Param("name") String name, @Param("type") int type, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select * from case_followup_record where case_id = :id and promise_flag = :flag order by operator_time desc limit 1", nativeQuery = true)
    CaseFollowupRecord getPayPromise(@Param("id") String id, @Param("flag") Integer flag);

    /**
     * @Description : 中通天鸿 164 双向外呼通话个数统计
     */

    @Query(value = "select count(*) a,operator,operator_name from case_followup_record where operator_time>:startTime and operator_time<:endTime and company_code =:companyCode and call_type =:callType GROUP BY operator,operator_name ORDER BY a DESC", nativeQuery = true)
    List<Object[]> getCountSmaRecord(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("companyCode") String companyCode, @Param("callType") Integer callType);

    /**
     * @Description : 中通天鸿 164 双向外呼通话时长统计
     */

    @Query(value = "select sum(conn_secs) a ,operator,operator_name from case_followup_record where operator_time>:startTime and operator_time<:endTime and company_code =:companyCode and call_type =:callType GROUP BY operator,operator_name ORDER BY a DESC", nativeQuery = true)
    List<Object[]> getCountTimeSmaRecord(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("companyCode") String companyCode, @Param("callType") Integer callType);

    /**
     * 导出跟进记录
     *
     * @param caseNumberList
     * @param companyCode
     * @return
     */
    @Query(value = "SELECT a.case_number,b.batch_number,e.`name` AS pname,a.operator_time,a.type,c.`name` AS cname,c.id_card,a.target,a.target_name,a.contact_phone,a.detail,a.collection_location,a.collection_feedback,a.content,c.number,f.account_number,b.hand_number FROM ( " +
            " SELECT * FROM case_followup_record " +
            " WHERE case_number IN (:caseNumberList) " +
            " AND collection_way != 0 " +
            " AND company_code = :companyCode " +
            ") AS a " +
            "LEFT JOIN case_info b ON a.case_number = b.case_number " +
            "LEFT JOIN personal c ON b.personal_id = c.id " +
            "LEFT JOIN product d ON b.product_id = d.id " +
            "LEFT JOIN principal e ON b.principal_id = e.id " +
            "LEFT JOIN personal_bank f ON c.id = f.personal_id", nativeQuery = true)
    List<Object[]> findFollowup(@Param("caseNumberList") List<String> caseNumberList, @Param("companyCode") String companyCode);

    /**
     * 单案件导出跟进记录
     *
     * @param caseNumber
     * @param companyCode
     * @return
     */
    @Query(value = "SELECT a.case_number,b.batch_number,e.`name` AS pname,a.operator_time,a.type,c.`name` AS cname,c.id_card,a.target,a.target_name,a.contact_phone,a.detail,a.collection_location,a.collection_feedback,a.content,c.number,f.account_number,b.hand_number  FROM ( " +
            " SELECT * FROM case_followup_record " +
            " WHERE case_number = :caseNumber " +
            " AND collection_way != 0 " +
            " AND company_code = :companyCode " +
            ") AS a " +
            "LEFT JOIN case_info b ON a.case_number = b.case_number " +
            "LEFT JOIN personal c ON b.personal_id = c.id " +
            "LEFT JOIN product d ON b.product_id = d.id " +
            "LEFT JOIN principal e ON b.principal_id = e.id " +
            "LEFT JOIN personal_bank f ON c.id = f.personal_id", nativeQuery = true)
    List<Object[]> findFollowupSingl(@Param("caseNumber") String caseNumber, @Param("companyCode") String companyCode);

    /**
     * 导出跟进记录
     *
     * @param caseNumberList
     * @param companyCode
     * @return
     */
    @Query(value = "SELECT b.case_number,a.batch_number,e.`name` AS pname,b.operator_time,b.type,c.`name` AS cname,c.id_card,b.target," +
            " b.target_name,b.contact_phone,b.detail,b.collection_location,b.collection_feedback,b.content FROM case_info a, case_followup_record b," +
            " personal c, product d, principal e WHERE a.case_number = b.case_number AND b.collection_type != 0 AND a.personal_id = c.id " +
            " AND a.product_id = d.id AND a.principal_id = e.id AND a.case_number in (?1) and a.company_code=?2 " +
            " LIMIT ?3 ", nativeQuery = true)
    List<Object[]> findFollowupPage(List<String> caseNumberList, String companyCode, int limit);

    @Query(value = "SELECT count(1) FROM case_info a, case_followup_record b," +
            " personal c, product d, principal e WHERE a.case_number = b.case_number AND b.collection_type != 0 AND a.personal_id = c.id " +
            " AND a.product_id = d.id AND a.principal_id = e.id AND a.case_number in (?1) and a.company_code=?2 ", nativeQuery = true)
    int findFollowupPageTotal(List<String> caseNumberList, String companyCode);
}
