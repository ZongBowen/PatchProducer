package org.zbw.patchproducer.enums;

import java.util.HashMap;

public enum BuildPattern {
    DELIVERY("0"), LOCALPATH("1");

    private final String strValue;
    private static HashMap<String, BuildPattern> mappings;

    private BuildPattern(String value) {
        strValue = value;
        BuildPattern.getMappings().put(value, this);
    }

    public String getValue() {
        return this.strValue;
    }

    private static HashMap<String, BuildPattern> getMappings() {
        if (mappings == null) {
            mappings = new HashMap<>();
        }
        return mappings;
    }

    public static BuildPattern forValue(String value) {
        if (!getMappings().containsKey(value)) {
            throw new RuntimeException("索引数组中不存在当前值：" + value);
        }
        return getMappings().get(value);
    }
}
