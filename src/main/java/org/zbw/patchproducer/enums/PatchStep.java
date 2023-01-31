package org.zbw.patchproducer.enums;

import java.util.HashMap;

public enum PatchStep {
    APP("001"), DBO("002"), DATA("003"), SQL("004"), METADATA("006"), IDP("008");

    private final String strValue;
    private static HashMap<String, PatchStep> mappings;

    private PatchStep(String value) {
        strValue = value;
        PatchStep.getMappings().put(value, this);
    }

    public String getValue() {
        return this.strValue;
    }

    private static HashMap<String, PatchStep> getMappings() {
        if (mappings == null) {
            mappings = new HashMap<>();
        }
        return mappings;
    }

    public static PatchStep forValue(String value) {
        if (!getMappings().containsKey(value)) {
            throw new RuntimeException("索引数组中不存在当前值：" + value);
        }
        return getMappings().get(value);
    }
}