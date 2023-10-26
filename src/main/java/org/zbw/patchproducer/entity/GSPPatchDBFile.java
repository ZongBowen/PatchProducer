package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GSPPatchDBFile")
@XmlType(propOrder = {"name", "order", "logicPath"})
public class GSPPatchDBFile {
    private String name;
    private Integer order;
    private String logicPath;

    public GSPPatchDBFile() {

    }

    public GSPPatchDBFile(String name, Integer order, String logicPath) {
        this.name = name;
        this.order = order;
        this.logicPath = logicPath;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Order")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @XmlElement(name = "LogicPath")
    public String getLogicPath() {
        return logicPath;
    }

    public void setLogicPath(String logicPath) {
        this.logicPath = logicPath;
    }
}
