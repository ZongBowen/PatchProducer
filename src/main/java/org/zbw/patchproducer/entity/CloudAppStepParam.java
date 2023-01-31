package org.zbw.patchproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudAppStepParam {
    @JsonProperty("LogicalPath")
    private String logicalPath;
    @JsonProperty("TarSu")
    private String tarSu;
    @JsonProperty("FileOperation")
    private Integer fileOperation;

    public CloudAppStepParam(String logicalPath) {
        this.logicalPath = logicalPath;
        this.fileOperation = 1;
    }

    public String getLogicalPath() {
        return logicalPath;
    }

    public void setLogicalPath(String logicalPath) {
        this.logicalPath = logicalPath;
    }

    public String getTarSu() {
        return tarSu;
    }

    public void setTarSu(String tarSu) {
        this.tarSu = tarSu;
    }

    public Integer getFileOperation() {
        return fileOperation;
    }

    public void setFileOperation(Integer fileOperation) {
        this.fileOperation = fileOperation;
    }
}
