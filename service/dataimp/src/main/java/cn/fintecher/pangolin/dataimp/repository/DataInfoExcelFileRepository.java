package cn.fintecher.pangolin.dataimp.repository;

import cn.fintecher.pangolin.dataimp.entity.DataInfoExcelFile;
import cn.fintecher.pangolin.dataimp.entity.QDataInfoExcelFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 17:17 2017/7/21
 */
public interface DataInfoExcelFileRepository extends MongoRepository<DataInfoExcelFile, String>,QueryDslPredicateExecutor<DataInfoExcelFile>,
        QuerydslBinderCustomizer<QDataInfoExcelFile> {
    @Override
    default void customize(final QuerydslBindings bindings, final QDataInfoExcelFile root) {}

    /**
     * 根据案件编号和公司code查找导入案件附件
     * @return
     */
    List<DataInfoExcelFile> findByBatchNumberAndCompanyCode(String batchNumber, String companyCode);
}
