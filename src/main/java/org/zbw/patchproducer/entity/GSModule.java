package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "GSModule")
@XmlType(propOrder = {"moduleName", "moduleCode", "moduleDepart", "moduleVersionList", "ctrlLic"})
public class GSModule {
    private String moduleName = "";
    private String moduleCode = "";
    private String moduleDepart;
    private List<String> moduleVersionList = new ArrayList<>();
    private Boolean ctrlLic = false;

    public GSModule() {
    }

    public GSModule(String moduleName, String moduleCode, String moduleVersion) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.moduleVersionList.add(moduleVersion);
    }

    @XmlElement(name = "MuduleName")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @XmlElement(name = "ModuleCode")
    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @XmlElement(name = "ModuleDepart")
    public String getModuleDepart() {
        return moduleDepart;
    }

    public void setModuleDepart(String moduleDepart) {
        this.moduleDepart = moduleDepart;
    }

    @XmlElementWrapper(name = "ModuleVersionList")
    @XmlElement(name = "string")
    public List<String> getModuleVersionList() {
        return moduleVersionList;
    }

    public void setModuleVersionList(List<String> moduleVersionList) {
        this.moduleVersionList = moduleVersionList;
    }

    @XmlElement(name = "CtrlLic")
    public Boolean getCtrlLic() {
        return this.ctrlLic;
    }

    public void setCtrlLic(Boolean ctrlLic) {
        this.ctrlLic = ctrlLic;
    }
}
