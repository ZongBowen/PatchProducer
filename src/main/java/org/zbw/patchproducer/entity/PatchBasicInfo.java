package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "PatchBasicInfo")
@XmlType(propOrder = {"patchID", "patchName", "patchCode", "patchType", "patchCategory", "patchImportance", "patchGSProductList", "patchGSModule", "defectsCode", "su", "patchNote", "publishDate", "version", "isAppUpdate", "isConfigUpdate", "isDBUpdate", "isDBStructUpdate", "isMetaDataUpdate", "patchAppFiles", "patchGroupOrder", "patchSize", "isModuleInit", "ismatchStandard", "isCommonChange", "patchDepInfo", "patchGroupInfo", "patchAuthorizeInfo", "setContainPatches", "patchReplaceInfo", "problemNote", "funcNote", "bugNote", "configNote", "opNote", "affectFuncNote", "impMetaData_WF", "impMetaData_Print", "ctrlLic"})
public class PatchBasicInfo {
    private String patchId = "";
    private String patchName = "";
    private String patchCode = "";
    private PatchType patchType;
    private PatchType patchCategory;
    private PatchImportance patchImportance;
    private List<GSProduct> patchGSProductList;
    private GSModule patchGSModule;
    private String defectsCode = "";
    private String su;
    private String patchNote = "";
    private Date publishDate = new Date();
    private String version;
    private Boolean isAppUpdate = false;
    private Boolean isConfigUpdate = false;
    private Boolean isDBUpdate = false;
    private Boolean isDBStructUpdate = false;
    private Boolean isMetaDataUpdate = false;
    private String patchAppFiles = "";
    private int patchGroupOrder = 0;
    private int patchSize = 0;
    private Boolean isModuleInit = false;
    private Boolean ismatchStandard = false;
    private Boolean isCommonChange = false;
    private String patchDepInfo;
    private String patchGroupInfo;
    private String patchAuthorizeInfo;
    private String setContainPatches;
    private String patchReplaceInfo;
    private String problemNote = "";
    private String funcNote = "";
    private String bugNote = "";
    private String configNote = "无";
    private String opNote = "无";
    private String affectFuncNote = "无";
    private String impMetaData_WF = "1";
    private String impMetaData_Print = "1";
    private Boolean ctrlLic = false;

    @XmlElement(name = "PatchID")
    public String getPatchID() {
        return patchId;
    }

    public void setPatchId(String patchId) {
        this.patchId = patchId;
    }

    @XmlElement(name = "PatchName")
    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    @XmlElement(name = "PatchCode")
    public String getPatchCode() {
        return patchCode;
    }

    public void setPatchCode(String patchCode) {
        this.patchCode = patchCode;
    }

    @XmlElement(name = "PatchType")
    public PatchType getPatchType() {
        return patchType;
    }

    public void setPatchType(PatchType patchType) {
        this.patchType = patchType;
    }

    @XmlElement(name = "PatchCategory")
    public PatchType getPatchCategory() {
        return patchCategory;
    }

    public void setPatchCategory(PatchType patchCategory) {
        this.patchCategory = patchCategory;
    }

    @XmlElement(name = "PatchImportance")
    public PatchImportance getPatchImportance() {
        return patchImportance;
    }

    public void setPatchImportance(PatchImportance patchImportance) {
        this.patchImportance = patchImportance;
    }

    @XmlElementWrapper(name = "PatchGSProductList")
    @XmlElement(name = "GSProduct")
    public List<GSProduct> getPatchGSProductList() {
        return patchGSProductList;
    }

    public void setPatchGSProductList(List<GSProduct> patchGSProductList) {
        this.patchGSProductList = patchGSProductList;
    }

    @XmlElement(name = "PatchGSModule")
    public GSModule getPatchGSModule() {
        return patchGSModule;
    }

    public void setPatchGSModule(GSModule patchGSModule) {
        this.patchGSModule = patchGSModule;
    }

    @XmlElement(name = "DefectsCode")
    public String getDefectsCode() {
        return defectsCode;
    }

    public void setDefectsCode(String defectsCode) {
        this.defectsCode = defectsCode;
    }

    @XmlElement(name = "Su", nillable = true)
    public String getSu() {
        return su;
    }

    public void setSu(String su) {
        this.su = su;
    }

    @XmlElement(name = "PatchNote")
    public String getPatchNote() {
        return patchNote;
    }

    public void setPatchNote(String patchNote) {
        this.patchNote = patchNote;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "PublishDate")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @XmlElement(name = "Version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement(name = "IsAppUpdate")
    public Boolean getIsAppUpdate() {
        return this.isAppUpdate;
    }

    public void setIsAppUpdate(Boolean isAppUpdate) {
        this.isAppUpdate = isAppUpdate;
    }

    @XmlElement(name = "IsConfigUpdate")
    public Boolean getIsConfigUpdate() {
        return this.isConfigUpdate;
    }

    public void setIsConfigUpdate(Boolean isConfigUpdate) {
        this.isConfigUpdate = isConfigUpdate;
    }

    @XmlElement(name = "IsDBUpdate")
    public Boolean getIsDBUpdate() {
        return this.isDBUpdate;
    }

    public void setIsDBUpdate(Boolean isDBUpdate) {
        this.isDBUpdate = isDBUpdate;
    }

    @XmlElement(name = "IsDBStructUpdate")
    public Boolean getIsDBStructUpdate() {
        return this.isDBStructUpdate;
    }

    public void setIsDBStructUpdate(Boolean IsDBStructUpdate) {
        this.isDBStructUpdate = IsDBStructUpdate;
    }

    @XmlElement(name = "IsMetaDataUpdate")
    public Boolean getIsMetaDataUpdate() {
        return this.isMetaDataUpdate;
    }

    public void setIsMetaDataUpdate(Boolean isMetaDataUpdate) {
        this.isMetaDataUpdate = isMetaDataUpdate;
    }

    @XmlElement(name = "PatchAppFiles")
    public String getPatchAppFiles() {
        return patchAppFiles;
    }

    public void setPatchAppFiles(String patchAppFiles) {
        this.patchAppFiles = patchAppFiles;
    }

    @XmlElement(name = "PatchGroupOrder")
    public int getPatchGroupOrder() {
        return patchGroupOrder;
    }

    public void setPatchGroupOrder(int patchGroupOrder) {
        this.patchGroupOrder = patchGroupOrder;
    }

    @XmlElement(name = "PatchSize")
    public int getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(int patchSize) {
        this.patchSize = patchSize;
    }

    @XmlElement(name = "IsModuleInit")
    public Boolean getIsModuleInit() {
        return this.isModuleInit;
    }

    public void setIsModuleInit(Boolean isModuleInit) {
        this.isModuleInit = isModuleInit;
    }

    @XmlElement(name = "IsmatchStandard")
    public Boolean getIsmatchStandard() {
        return ismatchStandard;
    }

    public void setIsmatchStandard(Boolean ismatchStandard) {
        this.ismatchStandard = ismatchStandard;
    }

    @XmlElement(name = "IsCommonChange")
    public Boolean getIsCommonChange() {
        return this.isCommonChange;
    }

    public void setIsCommonChange(Boolean isCommonChange) {
        this.isCommonChange = isCommonChange;
    }

    @XmlElement(name = "PatchDepInfo", nillable = true)
    public String getPatchDepInfo() {
        return patchDepInfo;
    }

    public void setPatchDepInfo(String patchDepInfo) {
        this.patchDepInfo = patchDepInfo;
    }

    @XmlElement(name = "PatchGroupInfo", nillable = true)
    public String getPatchGroupInfo() {
        return patchGroupInfo;
    }

    public void setPatchGroupInfo(String patchGroupInfo) {
        this.patchGroupInfo = patchGroupInfo;
    }

    @XmlElement(name = "PatchAuthorizeInfo", nillable = true)
    public String getPatchAuthorizeInfo() {
        return patchAuthorizeInfo;
    }

    public void setPatchAuthorizeInfo(String patchAuthorizeInfo) {
        this.patchAuthorizeInfo = patchAuthorizeInfo;
    }

    @XmlElement(name = "SetContainPatches", nillable = true)
    public String getSetContainPatches() {
        return setContainPatches;
    }

    public void setSetContainPatches(String setContainPatches) {
        this.setContainPatches = setContainPatches;
    }

    @XmlElement(name = "PatchReplaceInfo", nillable = true)
    public String getPatchReplaceInfo() {
        return patchReplaceInfo;
    }

    public void setPatchReplaceInfo(String patchReplaceInfo) {
        this.patchReplaceInfo = patchReplaceInfo;
    }

    @XmlElement(name = "ProblemNote")
    public String getProblemNote() {
        return problemNote;
    }

    public void setProblemNote(String problemNote) {
        this.problemNote = problemNote;
    }

    @XmlElement(name = "FuncNote")
    public String getFuncNote() {
        return funcNote;
    }

    public void setFuncNote(String funcNote) {
        this.funcNote = funcNote;
    }

    @XmlElement(name = "BugNote")
    public String getBugNote() {
        return bugNote;
    }

    public void setBugNote(String bugNote) {
        this.bugNote = bugNote;
    }

    @XmlElement(name = "ConfigNote")
    public String getConfigNote() {
        return configNote;
    }

    public void setConfigNote(String configNote) {
        this.configNote = configNote;
    }

    @XmlElement(name = "OpNote")
    public String getOpNote() {
        return opNote;
    }

    public void setOpNote(String opNote) {
        this.opNote = opNote;
    }

    @XmlElement(name = "AffectFuncNote")
    public String getAffectFuncNote() {
        return affectFuncNote;
    }

    public void setAffectFuncNote(String affectFuncNote) {
        this.affectFuncNote = affectFuncNote;
    }

    @XmlElement(name = "ImpMetaData_WF")
    public String getImpMetaData_WF() {
        return impMetaData_WF;
    }

    public void setImpMetaData_WF(String impMetaData_WF) {
        this.impMetaData_WF = impMetaData_WF;
    }

    @XmlElement(name = "ImpMetaData_Print")
    public String getImpMetaData_Print() {
        return impMetaData_Print;
    }

    public void setImpMetaData_Print(String impMetaData_Print) {
        this.impMetaData_Print = impMetaData_Print;
    }

    @XmlElement(name = "CtrlLic")
    public Boolean getCtrlLic() {
        return ctrlLic;
    }

    public void setCtrlLic(Boolean ctrlLic) {
        this.ctrlLic = ctrlLic;
    }
}
