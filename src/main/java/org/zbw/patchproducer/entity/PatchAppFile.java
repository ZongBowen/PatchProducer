package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "File")
@XmlType(propOrder = {"name", "folder", "x86Path", "x64Path", "op", "tarFolders"})
public class PatchAppFile {
    private String name;
    private String folder;
    private String x86Path = "";
    private String x64Path = "";
    private String op = "1";
    private String tarFolders = "";

    public PatchAppFile() {

    }

    public PatchAppFile(String name, String folder) {
        this.name = name;
        this.folder = folder;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Folder")
    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @XmlElement(name = "X86Path")
    public String getX86Path() {
        return x86Path;
    }

    public void setX86Path(String x86Path) {
        this.x86Path = x86Path;
    }

    @XmlElement(name = "X64Path")
    public String getX64Path() {
        return x64Path;
    }

    public void setX64Path(String x64Path) {
        this.x64Path = x64Path;
    }

    @XmlElement(name = "Op")
    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @XmlElement(name = "TarFolders")
    public String getTarFolders() {
        return tarFolders;
    }

    public void setTarFolders(String tarFolders) {
        this.tarFolders = tarFolders;
    }
}
