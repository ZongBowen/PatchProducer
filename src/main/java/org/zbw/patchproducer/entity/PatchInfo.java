package org.zbw.patchproducer.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Patch")
@XmlType(propOrder = {"patchBasicInfo", "patchAppInfo", "patchDBInfo"})
public class PatchInfo {
    private PatchBasicInfo patchBasicInfo;

    private PatchAppInfo patchAppInfo;

    private PatchDBInfo patchDBInfo;

    @XmlElement(name = "PatchBasicInfo")
    public PatchBasicInfo getPatchBasicInfo() {
        return patchBasicInfo;
    }

    public void setPatchBasicInfo(PatchBasicInfo patchBasicInfo) {
        this.patchBasicInfo = patchBasicInfo;
    }

    @XmlElement(name = "PatchAppInfo")
    public PatchAppInfo getPatchAppInfo() {
        return patchAppInfo;
    }

    public void setPatchAppInfo(PatchAppInfo patchAppInfo) {
        this.patchAppInfo = patchAppInfo;
    }

    @XmlElement(name = "PatchDBInfo")
    public PatchDBInfo getPatchDBInfo() {
        return patchDBInfo;
    }

    public void setPatchDBInfo(PatchDBInfo patchDBInfo) {
        this.patchDBInfo = patchDBInfo;
    }
}