package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PatchAction")
@XmlType(propOrder = {"actionCode", "actionName"})
public class PatchAction {
    private String actionCode = "";
    private String actionName = "";

    public PatchAction() {

    }

    public PatchAction(String actionCode, String actionName) {
        this.actionCode = actionCode;
        this.actionName = actionName;
    }

    @XmlElement(name = "ActionCode")
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    @XmlElement(name = "ActionName")
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
