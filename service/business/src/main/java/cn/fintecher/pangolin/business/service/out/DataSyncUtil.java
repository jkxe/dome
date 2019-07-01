package cn.fintecher.pangolin.business.service.out;

import java.util.List;

/**
 * Created by sunyanping on 2019/6/11
 */
public class DataSyncUtil {

    private DataSyncUtil() {
    }

    /**
     * 生成联系人唯一标识
     * @param personalId 用户ID
     * @param relation 关系
     * @param name 联系人名称
     * @param phone 联系人电话
     * @return
     */
    public static String personalContactUniqueIdentify(String personalId, String relation, String name, String phone) {
        return String.format("%s-%s-%s-%s", personalId, relation, name, phone);
    }

    /**
     * 计算数据总共分为多少批
     * @param totalSize 总数据量
     * @param batchSize 每一批的数量
     * @return
     */
    public static int calculateTotalBatch(int totalSize, int batchSize) {
        return totalSize % batchSize == 0 ? (totalSize / batchSize) : (totalSize / batchSize) + 1;
    }

    /**
     * 截取的数据
     * @param dataList 总数居
     * @param batchSize 没批截取的数据量
     * @param batchIndex 截取的批次(从第1批开始)
     * @param totalBatch 总批数
     * @param <T>
     * @return
     */
    public static  <T> List<T> sublist(List<T> dataList, int batchSize, int batchIndex, int totalBatch) {
        int fromIndex = (batchIndex - 1) * batchSize;
        int toIndex = batchIndex == totalBatch ? dataList.size() : batchIndex * batchSize;
        return dataList.subList(fromIndex, toIndex);
    }
}
