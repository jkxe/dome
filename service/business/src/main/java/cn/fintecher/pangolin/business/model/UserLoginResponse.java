package cn.fintecher.pangolin.business.model;


import lombok.Data;

/**
 * Created by ChenChang on 2017/3/7.
 */
@Data
public class UserLoginResponse {
    UserSimpleModel user;
    String token;
    boolean reset; //true是需要修改密码
    String regDay;
}
