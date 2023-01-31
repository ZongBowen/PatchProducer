package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PatchImportance")
@XmlType(propOrder = {"impCode", "impName"})
public class PatchImportance {
    private String impCode = "";
    private String impName = "";

    public PatchImportance() {
    }

    public PatchImportance(String impCode, String impName) {
        this.impCode = impCode;
        this.impName = impName;
    }

    @XmlElement(name = "ImpCode")
    public String getImpCode() {
        return impCode;
    }

    public void setImpCode(String impCode) {
        this.impCode = impCode;
    }

    @XmlElement(name = "ImpName")
    public String getImpName() {
        return impName;
    }

    public void setImpName(String impName) {
        this.impName = impName;
    }
}
