package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudPatchDepPatch {
    @JsonProperty("DepPatchID")
    private String depPatchId;
    @JsonProperty("DepPatchCode")
    private String depPatchCode;
    @JsonProperty("DepPatchName")
    private String depPatchName;
    @JsonProperty("DepPatchDeployUnit")
    private CloudPatchDeployUnit depPatchDeployUnit;

    public String getDepPatchId() {
        return depPatchId;
    }

    public void setDepPatchId(String depPatchId) {
        this.depPatchId = depPatchId;
    }

    public String getDepPatchCode() {
        return depPatchCode;
    }

    public void setDepPatchCode(String depPatchCode) {
        this.depPatchCode = depPatchCode;
    }

    public String getDepPatchName() {
        return depPatchName;
    }

    public void setDepPatchName(String depPatchName) {
        this.depPatchName = depPatchName;
    }

    public CloudPatchDeployUnit getDepPatchDeployUnit() {
        return depPatchDeployUnit;
    }

    public void setDepPatchDeployUnit(CloudPatchDeployUnit depPatchDeployUnit) {
        this.depPatchDeployUnit = depPatchDeployUnit;
    }
}