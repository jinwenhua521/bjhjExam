package com.nebulae.system.entity;

public class DeptTree extends SysDept {

	private boolean isParent;

	private boolean open;


	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
