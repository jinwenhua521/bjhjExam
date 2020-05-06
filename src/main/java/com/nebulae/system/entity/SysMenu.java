package com.nebulae.system.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SysMenu {
    private String menuId;

    // 父菜单ID，一级菜单为0
    private String parentId;
   // 菜单名称
    private String label;
 // 菜单URL
    private String surl;
 // 授权(多个用逗号分隔，如：user:list,user:create)
    private String perms;
 // 类型 0：目录 1：菜单 2：按钮
    private BigDecimal type;
 // 菜单图标
    private String icon;
 // 排序
    private BigDecimal seqenc;

    private String createId;

    private Date createDate;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public BigDecimal getSeqenc() {
        return seqenc;
    }

    public void setSeqenc(BigDecimal seqenc) {
        this.seqenc = seqenc;
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
}