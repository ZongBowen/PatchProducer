package org.zbw.patchproducer.util;

public class MapUtil {
    /**
     * 获取Map初始大小
     *
     * @param size size
     * @return 初始大小
     */
    public static int getCapacity(int size) {
        return (int) (size / 0.75 + 1);
    }
}
