package cn.fintecher.pangolin.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gaobeibei on 2017/11/1.
 */
@Data
public class PreviewModel {
    private List<CaseInfoInnerDistributeModel> list;
    private List<String> userOrDepartIds = new ArrayList<>();
    private List<String> caseIds = new ArrayList<>();
    private List<Integer> numList = new ArrayList<>();
    private List<OutDistributeInfo> OutList;
    private List<String> outsourceIds= new ArrayList<>();
    private Map<String,BigDecimal> map;
}
