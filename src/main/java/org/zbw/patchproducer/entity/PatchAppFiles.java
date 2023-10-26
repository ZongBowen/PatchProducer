package org.zbw.patchproducer.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Files")
public class PatchAppFiles {
    private List<PatchAppFile> files;

    @XmlElement(name = "File")
    public List<PatchAppFile> getFiles() {
        return files;
    }

    public void setFiles(List<PatchAppFile> files) {
        this.files = files;
    }
}
