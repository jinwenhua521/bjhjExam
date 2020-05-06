package com.nebulae.system.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SysFile {
    private String sid;

    private String filename;

    private String suffix;

    private String filetype;

    private String fileurl;

    private Date upDate;

    private String createid;

    private String fileAllname;

    private String fileLabel;

    private BigDecimal docType;
    
    private BigDecimal fileSize;
    
    public BigDecimal getFileSize() {
		return fileSize;
	}

	public void setFileSize(BigDecimal fileSize) {
		this.fileSize = fileSize;
	}


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

    public String getFileAllname() {
        return fileAllname;
    }

    public void setFileAllname(String fileAllname) {
        this.fileAllname = fileAllname == null ? null : fileAllname.trim();
    }

    public String getFileLabel() {
        return fileLabel;
    }

    public void setFileLabel(String fileLabel) {
        this.fileLabel = fileLabel == null ? null : fileLabel.trim();
    }

    public BigDecimal getDocType() {
        return docType;
    }

    public void setDocType(BigDecimal docType) {
        this.docType = docType;
    }
}