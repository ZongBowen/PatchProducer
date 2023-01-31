package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudPatchProduct {
    @JsonProperty("ProductID")
    private String productID;
    @JsonProperty("ProductCode")
    private String productCode;
    @JsonProperty("ProductName")
    private String productName;
    @JsonProperty("ProductVersion")
    private String productVersion;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }
}