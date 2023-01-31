package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CloudPatchBasic {
    @JsonProperty("PatchID")
    private String patchID;
    @JsonProperty("PatchName")
    private String patchName;
    @JsonProperty("PatchCode")
    private String patchCode;
    @JsonProperty("PatchType")
    private CloudPatchType patchType;
    @JsonProperty("PatchCategory")
    private CloudPatchType patchCategory;
    @JsonProperty("Su")
    private String su;
    @JsonProperty("GSPPatchImportance")
    private CloudPatchImportance gspPatchImportance;
    @JsonProperty("AppSus")
    private List<String> appSus;
    @JsonProperty("DBSus")
    private List<String> dbSus;
    @JsonProperty("PatchDeployUnit")
    private CloudPatchDeployUnit patchDeployUnit;
    @JsonProperty("PatchProduct")
    private CloudPatchProduct patchProduct;
    @JsonProperty("PatchDepInfo")
    private List<CloudPatchDepPatch> patchDepInfo;
    @JsonProperty("PatchReplaceInfo")
    private Object patchReplaceInfo;
    @JsonProperty("SetContainPatches")
    private Object setContainPatches;
    @JsonProperty("PatchNote")
    private String patchNote;
    @JsonProperty("PatchSize")
    private Integer patchSize;
    @JsonProperty("ToolVersion")
    private String toolVersion;
    @JsonProperty("IsCommonChange")
    private Boolean isCommonChange = false;
    @JsonProperty("PublishDate")
    private String publishDate;
    @JsonProperty("PatchAppFiles")
    private Object patchAppFiles;
    @JsonProperty("PatchDBFiles")
    private Object patchDBFiles;

    public String getPatchID() {
        return patchID;
    }

    public void setPatchID(String patchID) {
        this.patchID = patchID;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public String getPatchCode() {
        return patchCode;
    }

    public void setPatchCode(String patchCode) {
        this.patchCode = patchCode;
    }

    public CloudPatchType getPatchType() {
        return patchType;
    }

    public void setPatchType(CloudPatchType patchType) {
        this.patchType = patchType;
    }

    public CloudPatchType getPatchCategory() {
        return patchCategory;
    }

    public void setPatchCategory(CloudPatchType patchCategory) {
        this.patchCategory = patchCategory;
    }

    public String getSu() {
        return su;
    }

    public void setSu(String su) {
        this.su = su;
    }

    public CloudPatchImportance getGSPPatchImportance() {
        return gspPatchImportance;
    }

    @JsonIgnore
    public void setGSPPatchImportance(CloudPatchImportance gSPPatchImportance) {
        this.gspPatchImportance = gSPPatchImportance;
    }

    public List<String> getAppSus() {
        return appSus;
    }

    public void setAppSus(List<String> appSus) {
        this.appSus = appSus;
    }

    @JsonIgnore
    public List<String> getDBSus() {
        return dbSus;
    }

    public void setDBSus(List<String> dbSus) {
        this.dbSus = dbSus;
    }

    public CloudPatchDeployUnit getPatchDeployUnit() {
        return patchDeployUnit;
    }

    public void setPatchDeployUnit(CloudPatchDeployUnit patchDeployUnit) {
        this.patchDeployUnit = patchDeployUnit;
    }

    public CloudPatchProduct getPatchProduct() {
        return patchProduct;
    }

    public void setPatchProduct(CloudPatchProduct patchProduct) {
        this.patchProduct = patchProduct;
    }

    public List<CloudPatchDepPatch> getPatchDepInfo() {
        return patchDepInfo;
    }

    public void setPatchDepInfo(List<CloudPatchDepPatch> patchDepInfo) {
        this.patchDepInfo = patchDepInfo;
    }

    public Object getPatchReplaceInfo() {
        return patchReplaceInfo;
    }

    public void setPatchReplaceInfo(Object patchReplaceInfo) {
        this.patchReplaceInfo = patchReplaceInfo;
    }

    public Object getSetContainPatches() {
        return setContainPatches;
    }

    public void setSetContainPatches(Object setContainPatches) {
        this.setContainPatches = setContainPatches;
    }

    public String getPatchNote() {
        return patchNote;
    }

    public void setPatchNote(String patchNote) {
        this.patchNote = patchNote;
    }

    public Integer getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(Integer patchSize) {
        this.patchSize = patchSize;
    }

    public String getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }

    public Boolean getIsCommonChange() {
        return isCommonChange;
    }

    public void setIsCommonChange(Boolean isCommonChange) {
        this.isCommonChange = isCommonChange;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Object getPatchAppFiles() {
        return patchAppFiles;
    }

    public void setPatchAppFiles(Object patchAppFiles) {
        this.patchAppFiles = patchAppFiles;
    }

    public Object getPatchDBFiles() {
        return patchDBFiles;
    }

    public void setPatchDBFiles(Object patchDBFiles) {
        this.patchDBFiles = patchDBFiles;
    }
}