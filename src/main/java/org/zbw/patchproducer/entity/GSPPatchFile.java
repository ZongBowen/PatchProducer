package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GSPPatchFile")
@XmlType(propOrder = {"fileName", "isDetail", "diskPath", "logicalPath", "levelPath"})
public class GSPPatchFile {
    private String fileName = "";
    private String isDetail = "";
    private String diskPath;
    private String logicalPath;
    private String levelPath;

    public GSPPatchFile() {

    }

    public GSPPatchFile(String fileName, String isDetail) {
        this.fileName = fileName;
        this.isDetail = isDetail;
    }

    public GSPPatchFile(String fileName, String isDetail, String logicalPath, String levelPath) {
        this.fileName = fileName;
        this.isDetail = isDetail;
        this.logicalPath = logicalPath;
        this.levelPath = levelPath;
    }

    @XmlElement(name = "FileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @XmlElement(name = "IsDetail")
    public String getIsDetail() {
        return isDetail;
    }

    public void setIsDetail(String isDetail) {
        this.isDetail = isDetail;
    }

    @XmlElement(name = "DiskPath", nillable = true)
    public String getDiskPath() {
        return diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    @XmlElement(name = "LogicalPath", nillable = true)
    public String getLogicalPath() {
        return logicalPath;
    }

    public void setLogicalPath(String logicalPath) {
        this.logicalPath = logicalPath;
    }

    @XmlElement(name = "LevelPath", nillable = true)
    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }
}
