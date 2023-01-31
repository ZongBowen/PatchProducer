package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="GSPPatchStep")
@XmlType(propOrder = {"order", "action", "stepParams", "stepParamsType"})
public class GSPPatchStep {
    private int order;
    private PatchAction action;
    private String stepParams = "";
    private String stepParamsType;

    public GSPPatchStep() {

    }

    public GSPPatchStep(int order, PatchAction action, String stepParams) {
        this.order = order;
        this.action = action;
        this.stepParams = stepParams;
    }

    @XmlElement(name = "Order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @XmlElement(name = "Action")
    public PatchAction getAction() {
        return action;
    }

    public void setAction(PatchAction action) {
        this.action = action;
    }

    @XmlElement(name = "StepParams")
    public String getStepParams() {
        return stepParams;
    }

    public void setStepParams(String stepParams) {
        this.stepParams = stepParams;
    }

    @XmlElement(name = "StepParamsType", nillable = true)
    public String getStepParamsType() {
        return stepParamsType;
    }

    public void setStepParamsType(String stepParamsType) {
        this.stepParamsType = stepParamsType;
    }
}
