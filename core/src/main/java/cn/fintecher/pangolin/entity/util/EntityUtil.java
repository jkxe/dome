package cn.fintecher.pangolin.entity.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 10:27 2017/8/26
 */
public class EntityUtil {

    private EntityUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(EntityUtil.class);

    /**
     * 将空值或NULL字符串转为NULL
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static Object emptyValueToNull(Object object)  {
        try {
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for(Field field : declaredFields){
                field.setAccessible(true);
                if(Objects.isNull(field.get(object)) || StringUtils.isEmpty(field.get(object).toString()) ||
                        field.get(object).toString().equalsIgnoreCase("null")){
                    field.set(object,null);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return object;
    }
}
