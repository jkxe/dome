package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.QTaskScheduleLog;
import cn.fintecher.pangolin.entity.TaskScheduleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface TaskScheduleLogRepository extends QueryDslPredicateExecutor<TaskScheduleLog>,JpaRepository<TaskScheduleLog,String>,QuerydslBinderCustomizer<QTaskScheduleLog>{
    @Override
    default void customize(QuerydslBindings bindings, QTaskScheduleLog root){

    }

    @Transactional
    @Modifying
    @Query(value = "insert into task_schedule_log(exec_key,create_time) values(?1,?2)",nativeQuery = true)
    int addTaskScheduleLog(String execKey,Date createTime);
}
