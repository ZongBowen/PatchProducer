package org.zbw.patchproducer.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name="GSPPatchDBParam")
@XmlType(propOrder = {"name", "dbGroups", "patchModule", "patchModuleVersion"})
public class GSPPatchDBParam {
    private String name;
    private List<GSPPatchDBGroup> dbGroups;
    private String patchModule;
    private String patchModuleVersion;

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "DBGroups")
    public List<GSPPatchDBGroup> getDbGroups() {
        return dbGroups;
    }

    public void setDbGroups(List<GSPPatchDBGroup> dbGroups) {
        this.dbGroups = dbGroups;
    }

    @XmlElement(name = "PatchModule", nillable = true)
    public String getPatchModule() {
        return patchModule;
    }

    public void setPatchModule(String patchModule) {
        this.patchModule = patchModule;
    }

    @XmlElement(name = "PatchModuleVersion", nillable = true)
    public String getPatchModuleVersion() {
        return patchModuleVersion;
    }

    public void setPatchModuleVersion(String patchModuleVersion) {
        this.patchModuleVersion = patchModuleVersion;
    }
}
