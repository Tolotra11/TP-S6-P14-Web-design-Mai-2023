package com.tpSeo.DAO;

public class Sort {
    private String orderBy;
    private String direction = "ASC";
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public Sort(String orderBy, String direction) {
        this.orderBy = orderBy;
        this.direction = direction;
    }
    
}
