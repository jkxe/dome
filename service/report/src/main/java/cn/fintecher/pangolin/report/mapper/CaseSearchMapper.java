package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.report.model.CaseSearchRequest;
import cn.fintecher.pangolin.report.model.CaseSearchResponse;

import java.util.List;

/**
 * Created by sunyanping on 2019/5/15
 */
public interface CaseSearchMapper {

    List<CaseSearchResponse> caseSearch(CaseSearchRequest request);
}
