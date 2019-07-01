package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.util.List;

/**
 * Created by qijigui on 2018-01-08.
 */
/*
 给前端返回值用
 */
@Data
public class Content<T> {
    private List<T> content;
}
