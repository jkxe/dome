/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.check;

import com.hsjry.lang.common.util.StringUtil;
import net.sf.oval.constraint.CheckWithCheck.SimpleCheck;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 
 * @author huangbb
 * @version $Id: ListStringCheck.java, v 0.1 2018年3月27日 下午3:04:41 huangbb Exp $
 */
public class ListStringCheck implements SimpleCheck {

    private static final long serialVersionUID = 5906051769453853620L;

    /** 
     * @see net.sf.oval.constraint.CheckWithCheck.SimpleCheck#isSatisfied(java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean isSatisfied(Object validatedObject, Object value) {
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) value;
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        for(String str : list){
            if(StringUtil.isBlank(str)){
                return false;
            }
        }
        return true;
    }

}
