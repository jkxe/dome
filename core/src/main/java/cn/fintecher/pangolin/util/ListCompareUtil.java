package cn.fintecher.pangolin.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by meteor on 2019/2/25.
 */
public class ListCompareUtil<E> {
    /**
     * 对列表中的数据按指定字段进行排序。要求类必须有相关的方法返回字符串、整型、日期等值以进行比较。
     *
     * @param list        集合
     * @param fieldName   fieldName
     * @param reverseFlag false  是升序，true是降序
     */
    public void sortByMethod(List<E> list, final String fieldName,
                             final boolean reverseFlag) {
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object arg1, Object arg2) {
                int result = 0;
                try {
                    Field f1 = ((E) arg1).getClass().getDeclaredField(fieldName);
                    Field f2 = ((E) arg2).getClass().getDeclaredField(fieldName);
                    f1.setAccessible(true);
                    f2.setAccessible(true);
                    Object obj1 = f1.get(arg1);
                    Object obj2 = f2.get(arg2);
                    if (Objects.nonNull(obj1) && Objects.nonNull(obj2)) {
                        if (obj1 instanceof String) {
                            // 字符串
                            result = obj1.toString().compareTo(obj2.toString());
                        } else if (obj1 instanceof Date) {
                            // 日期
                            long l = ((Date) obj1).getTime() - ((Date) obj2).getTime();
                            if (l > 0) {
                                result = 1;
                            } else if (l < 0) {
                                result = -1;
                            } else {
                                result = 0;
                            }
                        } else if (obj1 instanceof Integer) {
                            // 整型（Method的返回参数可以是int的，因为JDK1.5之后，Integer与int可以自动转换了）
                            result = ((Integer) obj1).compareTo((Integer) obj2);
                        } else if (obj1 instanceof BigDecimal) {
                            result = ((BigDecimal) obj1).compareTo((BigDecimal) obj2);
                        }
                    } else if (Objects.isNull(obj1) && Objects.isNull(obj2)) {
                        result = 0;
                    } else if (Objects.isNull(obj1)) {
                        result = -1;
                    } else {
                        result = 1;
                    }
                    if (reverseFlag) {
                        // 倒序
                        result = -result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
    }
}
