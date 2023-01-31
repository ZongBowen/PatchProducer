package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudPatchAction {
    @JsonProperty("ActionCode")
    private String actionCode;
    @JsonProperty("ActionName")
    private String actionName;
    @JsonProperty("DeployAssemblyName")
    private String deployAssemblyName;
    @JsonProperty("DeployClassName")
    private String deployClassName;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDeployAssemblyName() {
        return deployAssemblyName;
    }

    public void setDeployAssemblyName(String deployAssemblyName) {
        this.deployAssemblyName = deployAssemblyName;
    }

    public String getDeployClassName() {
        return deployClassName;
    }

    public void setDeployClassName(String deployClassName) {
        this.deployClassName = deployClassName;
    }
}