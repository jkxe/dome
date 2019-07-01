package cn.fintecher.pangolin.util;

public class SnowflakeIdWorker {

    /**
     * 初始时间戳：2017-1-1 0:0:0
     * 一经定义，不可修改
     */
    private static final long initTimeMillis = 1483200000929L;
    /**
     * 进程。
     * 这里也可以手动指定每台实例的ID号；或者通过ZK的临时递增节点自动获取。
     * 固定值
     */
    private static final int pid = 3;
    /**
     * 计数器
     * 需要保证线程安全
     */
    private static volatile long counter;
    private static volatile long currentTimeMillis = System.currentTimeMillis() - initTimeMillis;
    private static volatile long lastTimeMillis = currentTimeMillis;

    public static synchronized long nextId() {
        long series = counter++;
        if (series >= (1 << 12) - 1) {//单位毫秒内计时器满了，需要重新计时
            while (lastTimeMillis == currentTimeMillis) {//等待到下一秒
                currentTimeMillis = System.currentTimeMillis() - initTimeMillis;
            }

            lastTimeMillis = currentTimeMillis;
            counter = 0;
            series = counter++;
        }
        return (currentTimeMillis << 22) | (pid << 12) | series;
    }
}
