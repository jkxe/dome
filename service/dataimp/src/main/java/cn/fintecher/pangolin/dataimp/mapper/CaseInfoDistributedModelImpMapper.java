package cn.fintecher.pangolin.dataimp.mapper;

import cn.fintecher.pangolin.dataimp.model.CaseInfo;
import cn.fintecher.pangolin.dataimp.util.MyMapper;

import java.util.List;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.mapper
 * @ClassName: cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedModelImpMapper
 * @date 2018年07月03日 15:01
 */
public interface CaseInfoDistributedModelImpMapper extends MyMapper<CaseInfo> {
    //自动分案标记异常
    int signExceptionCase(List<String> list);
}
