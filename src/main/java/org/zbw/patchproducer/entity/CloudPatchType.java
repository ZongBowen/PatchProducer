package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudPatchType {
    @JsonProperty("TypeCode")
    private String typeCode;
    @JsonProperty("TypeName")
    private String typeName;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}