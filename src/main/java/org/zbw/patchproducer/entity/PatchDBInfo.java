package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="PatchDBInfo")
@XmlType(propOrder = {"patchDBFiles", "patchDBStepList"})
public class PatchDBInfo {
    private List<GSPPatchFile> patchDBFiles;
    private List<GSPPatchStep> patchDBStepList;

    @XmlElementWrapper(name = "PatchDBFiles")
    @XmlElement(name = "GSPPatchFile")
    public List<GSPPatchFile> getPatchDBFiles() {
        return patchDBFiles;
    }

    public void setPatchDBFiles(List<GSPPatchFile> patchDBFiles) {
        this.patchDBFiles = patchDBFiles;
    }

    @XmlElementWrapper(name = "PatchDBStepList")
    @XmlElement(name = "GSPPatchStep")
    public List<GSPPatchStep> getPatchDBStepList() {
        return patchDBStepList;
    }

    public void setPatchDBStepList(List<GSPPatchStep> patchDBStepList) {
        this.patchDBStepList = patchDBStepList;
    }
}
