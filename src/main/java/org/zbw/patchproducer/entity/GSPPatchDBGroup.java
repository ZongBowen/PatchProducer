package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name="GSPPatchDBGroup")
@XmlType(propOrder = {"name", "groupOrder", "gspPatchDBFile"})
public class GSPPatchDBGroup {
    private String name;
    private Integer groupOrder;
    private List<GSPPatchDBFile> gspPatchDBFile;

    public GSPPatchDBGroup() {

    }

    public GSPPatchDBGroup (String name, Integer groupOrder, List<GSPPatchDBFile> gspPatchDBFile) {
        this.name = name;
        this.groupOrder = groupOrder;
        this.gspPatchDBFile = gspPatchDBFile;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "GroupOrder")
    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }

    @XmlElement(name = "GSPPatchDBFile")
    public List<GSPPatchDBFile> getGspPatchDBFile() {
        return gspPatchDBFile;
    }

    public void setGspPatchDBFile(List<GSPPatchDBFile> gspPatchDBFile) {
        this.gspPatchDBFile = gspPatchDBFile;
    }
}
