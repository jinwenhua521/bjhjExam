package com.nebulae.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GridTable implements Serializable {

	private static final long serialVersionUID = 1L;

	// 总记录数
	private long total;
	// 列表数据
	private List<?> rows=new ArrayList<Object>();
	
	private List<?> footer=new ArrayList<Object>();
	
	public GridTable() {  
        super();  
    } 
	
	public GridTable(List<?> list, long total) {
		super();  
		this.rows = list;
		this.total = total;
	}

	public GridTable(List<?> list,List<?> footer, long total) {
		super();  
		this.rows = list;
		this.footer = footer;
		this.total = total;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	
	public List<?> getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}

	@Override
	public String toString() {
		return "PageUtils{" +
				"total=" + total +
				", rows=" + rows +
				'}';
	}

}
