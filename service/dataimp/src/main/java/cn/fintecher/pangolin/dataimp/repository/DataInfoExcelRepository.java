package cn.fintecher.pangolin.dataimp.repository;

import cn.fintecher.pangolin.dataimp.entity.DataInfoExcel;
import cn.fintecher.pangolin.dataimp.entity.QDataInfoExcel;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Iterator;
import java.util.List;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 14:33 2017/7/18
 */
public interface DataInfoExcelRepository extends MongoRepository<DataInfoExcel, String>,QueryDslPredicateExecutor<DataInfoExcel>,
        QuerydslBinderCustomizer<QDataInfoExcel> {
    @Override
    default void customize(final QuerydslBindings bindings, final QDataInfoExcel root) {
        bindings.bind(root.prinCode).first((path, value) -> path.eq(StringUtils.trim(value)));
        bindings.bind(root.batchNumber).first((path, value) -> path.eq(StringUtils.trim(value)));
        bindings.bind(root.personalName).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        bindings.bind(root.overDueDays).all((path, value) -> {
            Iterator<? extends Integer> it=value.iterator();
            Integer firstOverDueDays=it.next();
            if(it.hasNext()){
                Integer secondOverDueDays=it.next();
                return path.between(firstOverDueDays,secondOverDueDays);
            }else{
                return path.goe(firstOverDueDays);
            }
        });
        bindings.bind(root.overdueAmount).all((path, value) -> {
            Iterator<? extends Double> it=value.iterator();
            Double firstOverDueAmount=it.next();
            if(it.hasNext()){
                Double secondOverDueAmount=it.next();
                return path.between(firstOverDueAmount,secondOverDueAmount);
            }else{
                return path.goe(firstOverDueAmount);
            }
        });
        bindings.bind(root.commissionRate).all((path, value) -> {
            Iterator<? extends Double> it=value.iterator();
            Double firstCommissionRate=it.next();
            if(it.hasNext()){
                Double secondCommissionRate=it.next();
                return path.between(firstCommissionRate,secondCommissionRate);
            }else{
                return path.goe(firstCommissionRate);
            }
        });

        bindings.bind(root.mobileNo).first((path, value) -> path.eq(StringUtils.trim(value)));
        bindings.bind(root.idCard).first((path, value) -> path.eq(StringUtils.trim(value)));
        bindings.bind(root.province).first((path, value) -> path.startsWith(StringUtils.trim(value)));
        bindings.bind(root.city).first((path, value) -> path.startsWith(StringUtils.trim(value)));
        bindings.bind(root.color).first((path, value) -> path.eq(value));
    }

    /**
     * 根据案件编号和公司code查找导入案件
     * @return
     */
    List<DataInfoExcel> findByBatchNumberAndCompanyCode(String batchNumber, String companyCode);
}
