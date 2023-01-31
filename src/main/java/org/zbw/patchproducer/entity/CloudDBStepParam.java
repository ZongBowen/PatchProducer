package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudDBStepParam {
    @JsonProperty("PatchId")
    private String patchId;
    @JsonProperty("Param")
    private String param;
    @JsonProperty("SetPatchIDs")
    private String setPatchIDs;

    public String getPatchId() {
        return patchId;
    }

    public void setPatchId(String patchId) {
        this.patchId = patchId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSetPatchIDs() {
        return setPatchIDs;
    }

    public void setSetPatchIDs(String setPatchIDs) {
        this.setPatchIDs = setPatchIDs;
    }
}