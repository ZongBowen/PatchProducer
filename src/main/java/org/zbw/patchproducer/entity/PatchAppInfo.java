package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "PatchAppInfo")
@XmlType(propOrder = {"patchAppFiles", "patchAppStepList"})
public class PatchAppInfo {
    private List<GSPPatchFile> patchAppFiles;
    private List<GSPPatchStep> patchAppStepList;

    @XmlElementWrapper(name = "PatchAppFiles")
    @XmlElement(name = "GSPPatchFile")
    public List<GSPPatchFile> getPatchAppFiles() {
        return patchAppFiles;
    }

    public void setPatchAppFiles(List<GSPPatchFile> patchAppFiles) {
        this.patchAppFiles = patchAppFiles;
    }

    @XmlElementWrapper(name = "PatchAppStepList", nillable = true)
    @XmlElement(name = "GSPPatchStep")
    public List<GSPPatchStep> getPatchAppStepList() {
        return patchAppStepList;
    }

    public void setPatchAppStepList(List<GSPPatchStep> patchAppStepList) {
        this.patchAppStepList = patchAppStepList;
    }
}
