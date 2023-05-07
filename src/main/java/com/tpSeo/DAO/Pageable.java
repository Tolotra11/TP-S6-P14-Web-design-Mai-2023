package com.tpSeo.DAO;


public class Pageable {
    private int page;
    private int size;
    private Sort sort;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public Pageable() {
    }
    public Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }
    public Sort getSort() {
        return sort;
    }
    public void setSort(Sort sort) {
        this.sort = sort;
    }
    public Pageable(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
    
}
