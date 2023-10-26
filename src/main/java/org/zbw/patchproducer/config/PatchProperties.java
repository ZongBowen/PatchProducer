package org.zbw.patchproducer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("patchproducer")
public class PatchProperties {
    private String toolVersion;
    private String product;
    private String productVersion;
    private String deployUnit;
    private String deployUnitName;
    private String deployUnitVersion;
    private String tempPath;
    private String tempZipPath;
    private String patchPath;
    private String serverPath;
    private String webPath;
    private String dboPath;
    private String dataPath;
    private String metaDataPath;
    private String idpPath;
    private String serverRoot;
    private String dbRoot;
    private String deliveryPath;
    private String defaultBegin;
    private Boolean commitFilter;
    private Boolean suFilter;

    public String getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getDeployUnit() {
        return deployUnit;
    }

    public void setDeployUnit(String deployUnit) {
        this.deployUnit = deployUnit;
    }

    public String getDeployUnitName() {
        return deployUnitName;
    }

    public void setDeployUnitName(String deployUnitName) {
        this.deployUnitName = deployUnitName;
    }

    public String getDeployUnitVersion() {
        return deployUnitVersion;
    }

    public void setDeployUnitVersion(String deployUnitVersion) {
        this.deployUnitVersion = deployUnitVersion;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getTempZipPath() {
        return tempZipPath;
    }

    public void setTempZipPath(String tempZipPath) {
        this.tempZipPath = tempZipPath;
    }

    public String getPatchPath() {
        return patchPath;
    }

    public void setPatchPath(String patchPath) {
        this.patchPath = patchPath;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public String getDboPath() {
        return dboPath;
    }

    public void setDboPath(String dboPath) {
        this.dboPath = dboPath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getMetaDataPath() {
        return metaDataPath;
    }

    public void setMetaDataPath(String metaDataPath) {
        this.metaDataPath = metaDataPath;
    }

    public String getIdpPath() {
        return idpPath;
    }

    public void setIdpPath(String idpPath) {
        this.idpPath = idpPath;
    }

    public String getServerRoot() {
        return serverRoot;
    }

    public void setServerRoot(String serverRoot) {
        this.serverRoot = serverRoot;
    }

    public String getDBRoot() {
        return dbRoot;
    }

    public void setDBRoot(String dbRoot) {
        this.dbRoot = dbRoot;
    }

    public String getDeliveryPath() {
        return deliveryPath;
    }

    public void setDeliveryPath(String deliverypath) {
        this.deliveryPath = deliverypath;
    }

    public String getDefaultBegin() {
        return defaultBegin;
    }

    public void setDefaultBegin(String defaultBegin) {
        this.defaultBegin = defaultBegin;
    }

    public Boolean getCommitFilter() {
        return commitFilter;
    }

    public void setCommitFilter(Boolean commitFilter) {
        this.commitFilter = commitFilter;
    }

    public Boolean getSuFilter() {
        return suFilter;
    }

    public void setSuFilter(Boolean suFilter) {
        this.suFilter = suFilter;
    }
}
