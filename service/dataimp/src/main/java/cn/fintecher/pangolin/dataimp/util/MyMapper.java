package cn.fintecher.pangolin.dataimp.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.util
 * @ClassName: cn.fintecher.pangolin.dataimp.util.MyMapper
 * @date 2018年06月21日 12:07
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
