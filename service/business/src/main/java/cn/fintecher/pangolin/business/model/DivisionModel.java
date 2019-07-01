package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.util.List;

@Data
public class DivisionModel {
    private String description;
    private List<String> caseIdList;
    private Integer type;//1-分案；2- 流转；3-协催；4-留案
}
