package com.nebulae.system.entity;

public class MenuTree extends SysMenu{
	
	private boolean isParent;
	
	private boolean open;
	
	private boolean checked;

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
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	

}
