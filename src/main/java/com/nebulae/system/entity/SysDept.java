package com.nebulae.system.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SysDept {
    private String deptId;

    private String deptName;
    
    private String deptCode;

    private String deptFid;

    private String deptFname;
    
    private String deptFcode;

    private BigDecimal seqenc;

    private String parentId;

    private String createId;

    private Date createDate;

    private String parentName;
    
    //临时存储部门名称，用于更新
    private String oldDeptName;
    private String oldDeptCode;
    
    //
    

    public String getOldDeptName() {
		return oldDeptName;
	}

	public String getOldDeptCode() {
		return oldDeptCode;
	}

	public void setOldDeptCode(String oldDeptCode) {
		this.oldDeptCode = oldDeptCode;
	}

	public String getDeptFcode() {
		return deptFcode;
	}


	public void setDeptFcode(String deptFcode) {
		this.deptFcode = deptFcode == null ? null : deptFcode.trim();
	}


	public void setOldDeptName(String oldDeptName) {
		this.oldDeptName = oldDeptName;
	}

	public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }
    
    

    public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}

	public String getDeptFid() {
        return deptFid;
    }

    public void setDeptFid(String deptFid) {
        this.deptFid = deptFid == null ? null : deptFid.trim();
    }

    public String getDeptFname() {
        return deptFname;
    }

    public void setDeptFname(String deptFname) {
        this.deptFname = deptFname == null ? null : deptFname.trim();
    }

    public BigDecimal getSeqenc() {
        return seqenc;
    }

    public void setSeqenc(BigDecimal seqenc) {
        this.seqenc = seqenc;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }
}