package cn.fintecher.pangolin.dataimp.service;

import cn.fintecher.pangolin.dataimp.entity.CollectionQueue;
import cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.model.request.ObtainTaticsStrategyRequest;
import cn.fintecher.pangolin.entity.User;

import java.util.List;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  案件数据获取策略 service 接口
 * 1 内催，委外，特殊，停催，这些是互斥的。诉讼和反欺诈与 内催，委外，特殊，停催并行。
 * 因此先过滤所有的案件，将诉讼与反欺诈的类型设置到parallelTasksType。
 * 2 然后依次执行停催，特殊，委外，内催的规则，如果执行完内催规则后还有剩余的案件则直接划分到内催中。
 * 暂时没有 停催，特殊 的规则，因此将2个的方法预留出来。
 * @Package cn.fintecher.pangolin.business.service
 * @ClassName: cn.fintecher.pangolin.business.service.ObtainTaticsStrategyService
 * @date 2018年06月19日 15:07
 */
public interface ObtainTaticsStrategyService {


    /**
     * 新增/修改案件数据获取策略
     * @param obtainTaticsStrategyRequest
     * @param token
     * @return
     */
    public String addObtainTaticsStrategy(ObtainTaticsStrategyRequest obtainTaticsStrategyRequest, String token, User user);


    /**
     * 新增/修改催收队列
     * @param collectionQueue
     * @param token
     * @return
     */
    public String addCollectionQueue(CollectionQueue collectionQueue, String token, User user);



    /**
     * 查询分案策略
     * @param user
     * @param companyCode
     * @param name
     * @param caseType
     * @param status
     * @return
     */
    public List<ObtainTaticsStrategy> getObtainTaticsStrategyList(User user, String companyCode, String name,
                                                                  Integer caseType, Integer status);


    /**
     * 每天执行的分案规则，针对逾期天数为T+3的案件
     * @param companyCode
     */
    public void everyDayDivisionCase(String companyCode);


    /**
     * 月初的分案规则，针对除过逾期天数为T+3的案件
     * @return
     */
    public void monthEarlyDivisionCase(String companyCode);

}
