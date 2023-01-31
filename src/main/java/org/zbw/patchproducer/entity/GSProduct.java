package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="GSProduct")
@XmlType(propOrder = {"productID", "productCode", "productName", "productVersion"})
public class GSProduct {
    private String productId;
    private String productCode;
    private String productName;
    private String productVersion;

    public GSProduct() {
    }

    public GSProduct(String productId, String productCode, String productName, String productVersion) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.productVersion = productVersion;
    }

    @XmlElement(name = "ProductID")
    public String getProductID() {
        return productId;
    }

    public void setProductID(String productId) {
        this.productId = productId;
    }

    @XmlElement(name = "ProductCode")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @XmlElement(name = "ProductName")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @XmlElement(name = "ProductVersion")
    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }
}
