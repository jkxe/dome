package cn.fintecher.pangolin.dataimp.repository;

import cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.entity.QObtainTaticsStrategy;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  案件数据获取策略 接口
 * @Package cn.fintecher.pangolin.dataimp.repository
 * @ClassName: cn.fintecher.pangolin.dataimp.repository.ObtainTaticsStrategyRepository
 * @date 2018年06月13日 15:51
 */
public interface ObtainTaticsStrategyRepository extends MongoRepository<ObtainTaticsStrategy, String>,
        QueryDslPredicateExecutor<ObtainTaticsStrategy>
        ,QuerydslBinderCustomizer<QObtainTaticsStrategy> {

    @Override
    default void customize(final QuerydslBindings bindings, final QObtainTaticsStrategy root) {
        bindings.bind(root.name).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        bindings.bind(root.productType).first((path, value) -> path.eq(StringUtils.trim(value)));
        bindings.bind(root.caseType).first((path, value) -> path.eq(value));
        bindings.bind(root.status).first((path, value) -> path.eq(value));
    }
}
