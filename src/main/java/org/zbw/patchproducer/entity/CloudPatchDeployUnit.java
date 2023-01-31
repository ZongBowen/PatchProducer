package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudPatchDeployUnit {
    @JsonProperty("DeployUnitName")
    private String deployUnitName;
    @JsonProperty("DeployUnitCode")
    private String deployUnitCode;
    @JsonProperty("DeployUnitVersion")
    private String deployUnitVersion;

    public String getDeployUnitName() {
        return deployUnitName;
    }

    public void setDeployUnitName(String deployUnitName) {
        this.deployUnitName = deployUnitName;
    }

    public String getDeployUnitCode() {
        return deployUnitCode;
    }

    public void setDeployUnitCode(String deployUnitCode) {
        this.deployUnitCode = deployUnitCode;
    }

    public String getDeployUnitVersion() {
        return deployUnitVersion;
    }

    public void setDeployUnitVersion(String deployUnitVersion) {
        this.deployUnitVersion = deployUnitVersion;
    }
}