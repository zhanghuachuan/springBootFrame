package com.huachuan.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ConsistentHashingWithVirtualNode {
    /**
     * 集群地址列表
     */
    private static String[] groups = {
            "192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111",
            "192.168.0.3:111", "192.168.0.4:111"
    };

    /**
     * 真实集群列表
     */
    private static List<String> realGroups = new LinkedList<>();

    /**
     * 虚拟节点映射关系
     */
    private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    private static final int VIRTUAL_NODE_NUM = 2000;

    static {
        // 先添加真实节点列表
        realGroups.addAll(Arrays.asList(groups));

        // 将虚拟节点映射到Hash环上
        for (String realGroup: realGroups) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                String virtualNodeName = getVirtualNodeName(realGroup, i);
                int hash = HashUtil.getHash(virtualNodeName);
                log.info("[" + virtualNodeName + "] launched @ " + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
    }

    private static String getVirtualNodeName(String realName, int num) {
        return realName + "&&VN" + String.valueOf(num);
    }

    private static String getRealNodeName(String virtualName) {
        return virtualName.split("&&")[0];
    }

    private static String getServer(String widgetKey) {
        int hash = HashUtil.getHash(widgetKey);
        // 只取出所有大于该hash值的部分而不必遍历整个Tree
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        String virtualNodeName;
        if (subMap == null || subMap.isEmpty()) {
            // hash值在最尾部，应该映射到第一个group上
            virtualNodeName = virtualNodes.get(virtualNodes.firstKey());
        }else {
            virtualNodeName = subMap.get(subMap.firstKey());
        }
        return getRealNodeName(virtualNodeName);
    }

    public static void main(String[] args) {
        // 生成随机数进行测试
        Map<String, Integer> resMap = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            Integer widgetId = i;
            String group = getServer(widgetId.toString());
            resMap.put(group, resMap.getOrDefault(group, 0) + 1);
        }

        resMap.forEach(
                (k, v) -> {
                   log.info("group " + k + ": " + v + "(" + v/1000.0D +"%)");
                }
        );
    }
}