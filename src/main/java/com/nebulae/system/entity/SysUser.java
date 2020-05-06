package com.nebulae.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SysUser implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userId;

    private String userName;

    private String name;

    private String password;

    private String deptId;
    
    private String deptCode;
    
    private String deptName;

    private String email;

    private String mobile;

    private BigDecimal status;

    private String createId;

    private Date createDate;

    private byte[] photo;
    
  //角色
    private List<String> roleIds;
    
    //部门信息
    private String deptFid;
    private String deptFname;
    private String deptFcode;
    
    
    
    

    public String getDeptFid() {
		return deptFid;
	}

	public void setDeptFid(String deptFid) {
		this.deptFid = deptFid;
	}

	public String getDeptFname() {
		return deptFname;
	}

	public void setDeptFname(String deptFname) {
		this.deptFname = deptFname;
	}

	public String getDeptFcode() {
		return deptFcode;
	}

	public void setDeptFcode(String deptFcode) {
		this.deptFcode = deptFcode;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
    
	
    
}