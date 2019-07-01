/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.check;

import net.sf.oval.constraint.CheckWithCheck.SimpleCheck;

/**
 * 
 * @author jiangjd12837
 * @version $Id: UserNameCheck.java, v 1.0 2017年5月9日 下午2:54:10 jiangjd12837 Exp $
 */
public class UserNameCheck implements SimpleCheck {

    /**  */
    private static final long serialVersionUID = 3154120151836152471L;

    /** 
     * @see net.sf.oval.constraint.CheckWithCheck.SimpleCheck#isSatisfied(java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean isSatisfied(Object arg0, Object arg1) {
        String UserName = (String) arg1;
        //数字和字母，或者纯字母
        String regex = "(?![0-9]+$)|(^[a-zA-Z0-9]{0,}$)";
        //不能是身份证号
        String idcard = "(^\\d{18}$)|(^\\d{15}$)|(^\\d{17}(\\d|X|x)$)/";
        //港澳居民来往用户通行证
        String passCheck = "^M\\d{10}$";
        if (!UserName.matches(idcard)) {
            if (UserName.matches(regex)) {
                if (!UserName.matches(passCheck)) {
                    return true;
                }
            }
        }

        return false;
    }
}
