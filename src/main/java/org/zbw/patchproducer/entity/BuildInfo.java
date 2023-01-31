package org.zbw.patchproducer.entity;

import java.util.HashMap;

public class BuildInfo {
    private String patchTemp;
    private String patchName;
    HashMap<String, String> issues;
    private String solveDesc;
    private String modifyDesc;
    private String testDesc;
    private Integer dboOrder = 0;
    private Integer dataOrder = 1;
    private Integer metaDataOrder = 2;
    private Integer idpOrder = 3;

    public String getPatchTemp() {
        return patchTemp;
    }

    public void setPatchTemp(String patchTemp) {
        this.patchTemp = patchTemp;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public HashMap<String, String> getIssues() {
        return issues;
    }

    public void setIssues(HashMap<String, String> issues) {
        this.issues = issues;
    }

    public String getSolveDesc() {
        return solveDesc;
    }

    public void setSolveDesc(String solveDesc) {
        this.solveDesc = solveDesc;
    }

    public String getModifyDesc() {
        return modifyDesc;
    }

    public void setModifyDesc(String modifyDesc) {
        this.modifyDesc = modifyDesc;
    }

    public String getTestDesc() {
        return testDesc;
    }

    public void setTestDesc(String testDesc) {
        this.testDesc = testDesc;
    }

    public Integer getDboOrder() {
        return dboOrder;
    }

    public void setDboOrder(Integer dboOrder) {
        this.dboOrder = dboOrder;
    }

    public Integer getDataOrder() {
        return dataOrder;
    }

    public void setDataOrder(Integer dataOrder) {
        this.dataOrder = dataOrder;
    }

    public Integer getMetaDataOrder() {
        return metaDataOrder;
    }

    public void setMetaDataOrder(Integer metaDataOrder) {
        this.metaDataOrder = metaDataOrder;
    }

    public Integer getIdpOrder() {
        return idpOrder;
    }

    public void setIdpOrder(Integer idpOrder) {
        this.idpOrder = idpOrder;
    }
}
