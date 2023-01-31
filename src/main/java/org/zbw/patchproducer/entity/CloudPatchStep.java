package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudPatchStep {
    @JsonProperty("Order")
    private Integer order;
    @JsonProperty("StepType")
    private Integer stepType;
    @JsonProperty("PatchAction")
    private CloudPatchAction patchAction;
    @JsonProperty("StepParam")
    private String stepParam;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getStepType() {
        return stepType;
    }

    public void setStepType(Integer stepType) {
        this.stepType = stepType;
    }

    public CloudPatchAction getPatchAction() {
        return patchAction;
    }

    public void setPatchAction(CloudPatchAction patchAction) {
        this.patchAction = patchAction;
    }

    public String getStepParam() {
        return stepParam;
    }

    public void setStepParam(String stepParam) {
        this.stepParam = stepParam;
    }
}