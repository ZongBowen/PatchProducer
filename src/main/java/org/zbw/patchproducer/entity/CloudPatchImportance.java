package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudPatchImportance {
    @JsonProperty("ImpCode")
    private String impCode;
    @JsonProperty("ImpName")
    private String impName;

    public String getImpCode() {
        return impCode;
    }

    public void setImpCode(String impCode) {
        this.impCode = impCode;
    }

    public String getImpName() {
        return impName;
    }

    public void setImpName(String impName) {
        this.impName = impName;
    }
}