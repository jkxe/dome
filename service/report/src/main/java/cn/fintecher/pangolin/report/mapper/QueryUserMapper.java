package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.report.model.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : baizhangyu
 * @Description : 管理员首页
 * @Date : 2017/8/8.
 */
public interface QueryUserMapper {


    public List<UserModel> queryUsers(@Param("deptCode") String deptCode,@Param("divisionSwitch")Integer divisionSwitch);

}
