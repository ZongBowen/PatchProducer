package org.zbw.patchproducer.entity;

public class FilePath {
    private String fileName;
    private String diskPath;
    private String logicalRoot;
    private String logicalPath;
    private String levelPath;

    public FilePath (String fileName, String diskPath, String logicalRoot, String logicalPath, String levelPath) {
        this.fileName = fileName;
        this.diskPath = diskPath;
        this.logicalRoot = logicalRoot;
        this.logicalPath = logicalPath;
        this.levelPath = levelPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDiskPath() {
        return diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    public String getLogicalRoot() {
        return logicalRoot;
    }

    public void setLogicalRoot(String logicalRoot) {
        this.logicalRoot = logicalRoot;
    }

    public String getLogicalPath() {
        return logicalPath;
    }

    public void setLogicalPath(String logicalPath) {
        this.logicalPath = logicalPath;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }
}
