package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CloudPatchInfo {
    @JsonProperty("PatchBasic")
    private CloudPatchBasic patchBasic;
    @JsonProperty("PatchSteps")
    private List<CloudPatchStep> patchSteps;

    public CloudPatchBasic getPatchBasic() {
        return patchBasic;
    }

    public void setPatchBasic(CloudPatchBasic patchBasic) {
        this.patchBasic = patchBasic;
    }

    public List<CloudPatchStep> getPatchSteps() {
        return patchSteps;
    }

    public void setPatchSteps(List<CloudPatchStep> patchSteps) {
        this.patchSteps = patchSteps;
    }
}