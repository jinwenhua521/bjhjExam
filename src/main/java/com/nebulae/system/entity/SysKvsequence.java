package com.nebulae.system.entity;

import java.math.BigDecimal;

public class SysKvsequence {
    private String k;

    private BigDecimal v;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k == null ? null : k.trim();
    }

    public BigDecimal getV() {
        return v;
    }

    public void setV(BigDecimal v) {
        this.v = v;
    }
}