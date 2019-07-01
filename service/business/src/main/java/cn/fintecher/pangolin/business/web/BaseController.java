package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.service.UserService;
import cn.fintecher.pangolin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: PeiShouWen
 * @Description: 通用Controller
 * @Date 13:26 2017/7/17
 */



public class BaseController {

    @Autowired
    public UserService userService;
    public User getUserByToken(String token) throws Exception {

       return userService.getUserByToken(token);
    }
}
